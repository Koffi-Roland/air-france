package com.airfrance.jraf.batch.event.individu;

import com.airfrance.jraf.batch.common.BlockDTO;
import com.airfrance.jraf.batch.common.type.BatchSicUpdateNotificationChangedBlockNameEnum;
import com.airfrance.ref.type.MediumStatusEnum;
import com.airfrance.repind.dto.role.RoleContratsDTO;
import com.airfrance.repind.dto.tracking.TriggerChangeIndividusDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;

@DependsOn("notifyEventIndividual-v1")
@Component
public class PrepareNotifyEventIndividualTask implements Runnable {

    private HashMap<String,String> blockMapping = new HashMap<String,String>();

    final static Log log = LogFactory.getLog(PrepareNotifyEventIndividualTask.class);
    private final static String COMPANY = "COMPANY";

    private String taskName;
    private LinkedBlockingQueue<TriggerChangeIndividusDTO> queue;

    private String currentSgin;
    private LinkedHashMap<String, ChangeData> ginAssociatedToBlockDTOListHm;
    private LinkedHashMap<String, List<Long>> ginLinkedToTriggerChangeIndIdListHm;
    private List<BlockDTO> blockDTOList;
    private List<Long> triggerChangeIdList;
    private ChangeData changeData;

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public void setQueue(LinkedBlockingQueue<TriggerChangeIndividusDTO> queue) {
        this.queue = queue;
    }


    private void initBlockMapping()
    {
        /* Specific agency/company blocks */
        blockMapping.put("PERS_MORALE", COMPANY);
        blockMapping.put("PM_ZONE", "COMMERCIAL ZONE");
        blockMapping.put("SYNONYME", "SYNONYM");
        blockMapping.put("ZONE_DECOUP", "COMMERCIAL ZONE");
        blockMapping.put("NUMERO_IDENT", "KEY NUMBER");
        blockMapping.put("ETABLISSEMENT", COMPANY);
        blockMapping.put("ENTREPRISE", COMPANY);
        blockMapping.put("AGENCE", "AGENCY");

        /* Specific member blocks */
        blockMapping.put("MEMBRE", "MEMBER");
        blockMapping.put("FONCTION", "FUNCTION");

        /* Common blocks */
        blockMapping.put("EMAILS", "EMAIL");
        blockMapping.put("TELECOMS", "TELECOM");
        blockMapping.put("ADR_POST", "POSTAL ADDRESS");

        /* Specific individual */
        blockMapping.put("ACCOUNT_DATA", "ACCOUNT DATA");
        blockMapping.put("COMMUNICATION_PREFERENCES", "COMMUNICATION PREFERENCES");
        blockMapping.put("DELEGATION_DATA", "DELEGATION DATA");
        blockMapping.put("EXTERNAL_IDENTIFIER", "EXTERNAL IDENTIFIER");
        blockMapping.put("EXTERNAL_IDENTIFIER_DATA", "EXTERNAL IDENTIFIER DATA");
        blockMapping.put("PREFILLED_NUMBERS", "PREFILLED NUMBERS");
        blockMapping.put("ROLE_CONTRATS", "CONTRACTS");
        blockMapping.put("SOCIAL_NETWORK_DATA", "SOCIAL NETWORK DATA");
        blockMapping.put("MEMBRE_RESEAU", "MEMBRE RESEAU");
        blockMapping.put("CONSENT", "CONSENT");
        blockMapping.put("MARKET_LANGUAGE", "MARKET LANGUAGE");


    }

    @Override
    public void run() {
        String taskId = taskName;
        BatchIndividualUpdateNotificationLogBatch("Start "+taskId, null);
        // Init block mapping
        initBlockMapping();
        ginAssociatedToBlockDTOListHm = new LinkedHashMap<String, ChangeData>();
        ginLinkedToTriggerChangeIndIdListHm = new LinkedHashMap<String, List<Long>>();
        blockDTOList = new ArrayList<BlockDTO>();
        triggerChangeIdList = new ArrayList<Long>();
        changeData = new ChangeData();
        while (queue.size()>0) {
            TriggerChangeIndividusDTO triggerChangeIndividusDTO = null;
            try {
                triggerChangeIndividusDTO = queue.poll();
                if(triggerChangeIndividusDTO!=null) {
                    prepareBlockDto(triggerChangeIndividusDTO);
                }
            } catch (Exception e) {
                BatchIndividualUpdateNotification.setHasError(true);
                BatchIndividualUpdateNotificationLogBatch(taskId + "--> " + BatchIndividualUpdateNotification.ERROR + " Error during prepareNotifyEventIndividualTask for triggerChange = " + triggerChangeIndividusDTO.getId() + " : ", e);
            }
        }

        if(StringUtils.isNotBlank(currentSgin) && !blockDTOList.isEmpty()) {
            ginAssociatedToBlockDTOListHm.put(currentSgin, changeData);
            ginLinkedToTriggerChangeIndIdListHm.put(currentSgin, triggerChangeIdList);
            BatchIndividualUpdateNotification.addBlocksToSend(ginAssociatedToBlockDTOListHm, ginLinkedToTriggerChangeIndIdListHm);
        }
        BatchIndividualUpdateNotificationLogBatch("End "+taskId, null);
    }

    private void prepareBlockDto(TriggerChangeIndividusDTO dto) throws JsonProcessingException {
        if(dto != null && StringUtils.isNotBlank(dto.getGin())) {

            if(dto.getGin() != null)
            {
                // First occurence
                if(currentSgin == null){
                    currentSgin = dto.getGin();
                }

                // new GIN
                if(!currentSgin.equalsIgnoreCase(dto.getGin()))
                {
                    // Add the last occurence
                    ginAssociatedToBlockDTOListHm.put(currentSgin, changeData);

                    // Save the TRIGGER_CHANGE_INDIVIDUS ids by GIN to update the status
                    ginLinkedToTriggerChangeIndIdListHm.put(currentSgin, triggerChangeIdList);

                    // reset ibDTOList & triggerChaneId
                    blockDTOList = new ArrayList<BlockDTO>();
                    triggerChangeIdList = new ArrayList<Long>();

                    currentSgin = dto.getGin();
                }

                // Add the TRIGGER_CHANGE_INDIVIDUS id to the list of records that will be updated
                triggerChangeIdList.add(dto.getId());

                // bo to dto
                BlockDTO blockDTO = new BlockDTO();
                blockDTO.setId(dto.getId());
                blockDTO.setIdentifier(dto.getChangeTableId());
                if(blockMapping.get(dto.getChangeTable()) != null) {
                    blockDTO.setName(blockMapping.get(dto.getChangeTable()));
                }
                else {
                    blockDTO.setName(dto.getChangeTable());
                }

                blockDTO.setNotificationType(dto.getChangeType());

                // Process to identify a deletion of contract
                checkContractDeletion(dto, blockDTO);

                // Check that the current block does not exist yet in the block list
                if(!blockDTOList.contains(blockDTO)) {
                    blockDTOList.add(blockDTO);
                }
            }
            changeData.setBlockDTOList(blockDTOList);
        }
    }

    private void checkContractDeletion(@NotNull TriggerChangeIndividusDTO dto, BlockDTO blockDTO) throws JsonProcessingException {
        if (isContractDeletion(dto)) {
            RoleContratsDTO rc = retrieveContractInfo(dto.getChangeAfter());
            if (rc != null) {
                changeData.setContractData(rc);
                //blockDTO.setIdentifier(rc.getNumeroContrat());
            }
        }
    }

    private boolean isContractDeletion(TriggerChangeIndividusDTO dto) {
        return (BatchSicUpdateNotificationChangedBlockNameEnum.ROLE_CONTRATS.toString().equalsIgnoreCase(dto.getChangeTable())
                && "D".equalsIgnoreCase(dto.getChangeType()));
    }

    private RoleContratsDTO retrieveContractInfo(String data) throws JsonProcessingException {

        if (StringUtils.isNotEmpty(data)) {
            RoleContratsDTO rc = new RoleContratsDTO();

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(data);
            String srin = rootNode.get("identifier").asText();
            String contractNumber = rootNode.get("contractNumber").asText();
            String contractType = rootNode.get("contractType").asText();
            String gin = rootNode.get("gin").asText();

            rc.setSrin(srin);
            rc.setNumeroContrat(contractNumber);
            rc.setTypeContrat(contractType);
            rc.setEtat(MediumStatusEnum.REMOVED.toString());
            rc.setGin(gin);

            //Signature

            rc.setSignatureModification("DELETED");
            rc.setSiteModification("QVI");
            rc.setDateModification(new Date());

            return rc;
        }
        else {
            return null;
        }

    }

    private void BatchIndividualUpdateNotificationLogBatch(String messageToLog , Exception exception) {
        if (exception == null) {
            if(BatchIndividualUpdateNotification.debugLogActivated){
                BatchIndividualUpdateNotification.LogBatchOutput(messageToLog);
                try {
                    BatchIndividualUpdateNotification.bfwOutput.flush();
                } catch (IOException eio) {
                    BatchIndividualUpdateNotification.log.error(eio);
                }
            }
        } else {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            exception.printStackTrace(pw);
            log.error(exception);
            BatchIndividualUpdateNotification.LogBatchError(messageToLog, sw);
            try {
                BatchIndividualUpdateNotification.bfwError.flush();
            } catch (IOException eio) {
                BatchIndividualUpdateNotification.log.error(eio);
            }
        }
    }

}
