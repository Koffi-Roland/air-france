package com.airfrance.batch.subscriptiondata;

import com.airfrance.batch.common.IBatch;
import com.airfrance.batch.subscriptiondata.config.BatchCollectSharepointSubscriptionDataConfig;
import com.airfrance.batch.subscriptiondata.model.*;
import com.airfrance.repind.service.environnement.internal.VariablesDS;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
@Service
public class BatchCollectSharepointSubscriptionData implements IBatch, AutoCloseable {

    @Autowired
    protected VariablesDS variablesDS;

    private static final String DDAY = new SimpleDateFormat("ddMMyyyy").format(new Date());
    private static final String CSM_DATA_LIST_PREFIX = "CSM_";
    private static final String CSM_SYNC_SUFFIX = "_SYNCHRO";
    private static final String PATH = "/app/REPIND/ftp/INJEST_ADHOC_DATA/";
    private static final String JSON = ".json";
    private static final String CSM_FILE = PATH + "CSM_FILE_" + DDAY + JSON;

    @Inject
    SharepointClient sharepointClient;


    public static void main(String[] args) throws Exception {
        log.info("Start of BatchCollectSharepointSubscriptionData.");
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(BatchCollectSharepointSubscriptionDataConfig.class);
        try (BatchCollectSharepointSubscriptionData batch = (BatchCollectSharepointSubscriptionData) ctx.getBean("batchCollectSharepointSubscriptionData")) {
            batch.execute();
            log.info("End BatchCollectSharepointSubscriptionData.");
        } catch (Exception exception) {
            log.error("BatchCollectSharepointSubscriptionData failed", exception);
            System.exit(2);
        }
        System.exit(0);
    }

    @Override
    public void execute() throws Exception {

        int NB_ITERATIONS_5K_LINES = Integer.parseInt(
                variablesDS.getEnv("BATCH_SHAREPOINT_RI_NB_ITERATIONS", "1"));

        Map<SharePointList, List<Individual>> allIndividuals = new HashMap<>();
        for (int i = 1; i <= NB_ITERATIONS_5K_LINES; i++) {
            log.info("GetList to have lists (iteration " + i + ")");
            Map<SharePointList, List<Individual>> individuals = new HashMap<>();
            SharePointGetListResponse sharepointGetListResponse = sharepointClient.getList();

            if (sharepointGetListResponse != null && sharepointGetListResponse.getValue() != null) {
                log.info("if loop");
                List<SharePointList> dataLists = getDataLists(sharepointGetListResponse.getValue());
                dataLists.forEach(list -> {
                            String listId = list.getId();
                            String listName = list.getName();
                            SharePointList synchroList = getSynchroList(sharepointGetListResponse.getValue(), listName);

                            if (canProcessList(synchroList)) {
                                log.info("Entering canProcessList");
                                updateImportFlag(synchroList, "NOK");

                                SharePointGetItemsResponse sharePointGetItemsResponse = sharepointClient.getListItems(listId);
                                if (sharePointGetItemsResponse != null) {
                                    sharePointGetItemsResponse.getValue().stream()
                                            .map(SharePointGetItemstItem::getFields).forEach(item -> {
                                                Individual individual = new Individual();
                                                individual.setId(item.getId());
                                                individual.setEmailAddress(item.getTitle());
                                                individual.setGin(item.getGin());
                                                individual.setCin(item.getCin());
                                                individual.setFirstname(item.getFirstname());
                                                individual.setSurname(item.getSurname());
                                                individual.setCivility(item.getCivility());
                                                individual.setBirthdate(item.getBirthdate());
                                                individual.setCountryCode(item.getCountryCode());
                                                individual.setLanguageCode(item.getLanguageCode());
                                                individual.setSubscriptionType(item.getSubscriptionType());
                                                individual.setDomain(item.getDomain());
                                                individual.setGroupType(item.getGroupType());
                                                individual.setStatus(item.getStatut());
                                                individual.setSource(item.getSources());
                                                individual.setDateOfConsent(item.getDateOfConsent());
                                                individual.setPreferredDepartureAirport(item.getPreferredDepartureAirport());
                                                individuals.computeIfAbsent(list, ind -> new ArrayList<>()).add(individual);
                                            });
                                }
                                updateImportFlag(synchroList, "OK");
                            } else {
                                //trigger retry mechanism
                                log.error(listName + " synchro list has EXPORT NOK Flag. We cannot export data now, please retry later.");
                                System.exit(1);
                            }
                        }
                );

                //delete Items
                if (!individuals.isEmpty()) {
                    log.info("Put all lists in NOK import flag for deletion");
                    dataLists.forEach(list -> {
                                String listName = list.getName();
                                SharePointList synchroList = getSynchroList(sharepointGetListResponse.getValue(), listName);
                                updateImportFlag(synchroList, "NOK");
                            }
                    );

                    log.info("Deleting the items");
                    individuals.forEach((list, listItems) -> {
                                listItems.stream().map(Individual::getId).forEach(itemId ->
                                        sharepointClient.deleteItemListId(list.getId(), itemId)
                                );
                                SharePointList synchroList = getSynchroList(sharepointGetListResponse.getValue(), list.getName());
                                updateImportFlag(synchroList, "OK");
                            }
                    );


                    log.info("Put all lists in OK import flag for end of deletion");
                    dataLists.forEach(list -> {
                                String listName = list.getName();
                                SharePointList synchroList = getSynchroList(sharepointGetListResponse.getValue(), listName);
                                updateImportFlag(synchroList, "OK");
                            }
                    );
                }
            }

            // accumulate individuals into allIndividuals
            allIndividuals.putAll(individuals);
        }

        ObjectMapper mapper = new ObjectMapper();
        try {
            log.info("Writing the json file");
            // Writing to a file
            mapper.writeValue(new File(CSM_FILE), allIndividuals.values().stream().flatMap(Collection::stream).toList());
        } catch (IOException e) {
            log.error(e.getMessage());
            throw e;
        }
    }


    private List<SharePointList> getDataLists(List<SharePointList> lists) {
        return lists.stream()
                .filter(list -> list.getName().startsWith(CSM_DATA_LIST_PREFIX) && !list.getName().endsWith(CSM_SYNC_SUFFIX))
                .toList();
    }

    private SharePointList getSynchroList(List<SharePointList> lists, String listName) {
        return lists.stream()
                .filter(list -> list.getName().equals(listName + CSM_SYNC_SUFFIX))
                .findFirst().orElseThrow(() -> new RuntimeException("Synchro List not found for list " + listName));
    }

    private boolean canProcessList(SharePointList synchroList) {
        SharePointGetItemsResponse listItems = sharepointClient.getListItems(synchroList.getId());
        SharePointItemsFields exportItem = listItems.getValue().stream()
                .map(SharePointGetItemstItem::getFields)
                .filter(field -> field.getTitle().equals("EXPORT")).findFirst().orElse(null);
        return exportItem != null && "OK".equals(exportItem.getFlag());
    }

    private void updateImportFlag(SharePointList synchroList, String flag) {
        SharePointGetItemsResponse listItems = sharepointClient.getListItems(synchroList.getId());
        SharePointItemsFields importItem = listItems.getValue().stream()
                .map(SharePointGetItemstItem::getFields)
                .filter(field -> field.getTitle().equals("IMPORT")).findFirst().orElse(null);

        if (importItem != null) {
            Map<String, String> fieldsToUpdate = new HashMap<>();
            fieldsToUpdate.put("Flag", flag);
            sharepointClient.updateItemListId(synchroList.getId(), importItem.getId(), fieldsToUpdate);
        }
    }

    @Override
    public void close() throws Exception {
        log.info("END BATCH PROCESS");
    }
}
