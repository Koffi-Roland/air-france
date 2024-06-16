package com.afklm.batch.event.individu;

import com.airfrance.batch.common.dto.BlockDTO;
import com.airfrance.repind.dto.tracking.TriggerChangeIndividusDTO;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
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
    private LinkedHashMap<String, List<BlockDTO>> ginAssociatedToBlockDTOListHm;
    private LinkedHashMap<String, List<Long>> ginLinkedToTriggerChangeIndIdListHm;
    private List<BlockDTO> blockDTOList;
    private List<Long> triggerChangeIdList;

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
        ginAssociatedToBlockDTOListHm = new LinkedHashMap<String, List<BlockDTO>>();
        ginLinkedToTriggerChangeIndIdListHm = new LinkedHashMap<String, List<Long>>();
        blockDTOList = new ArrayList<BlockDTO>();
        triggerChangeIdList = new ArrayList<Long>();
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
            ginAssociatedToBlockDTOListHm.put(currentSgin, blockDTOList);
            ginLinkedToTriggerChangeIndIdListHm.put(currentSgin, triggerChangeIdList);
            BatchIndividualUpdateNotification.addBlocksToSend(ginAssociatedToBlockDTOListHm, ginLinkedToTriggerChangeIndIdListHm);
        }
        BatchIndividualUpdateNotificationLogBatch("End "+taskId, null);
    }

    private void prepareBlockDto(TriggerChangeIndividusDTO dto){
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
                    ginAssociatedToBlockDTOListHm.put(currentSgin, blockDTOList);

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
                if(blockMapping.get(dto.getChangeTable()) != null)
                    blockDTO.setName(blockMapping.get(dto.getChangeTable()));
                else
                    blockDTO.setName(dto.getChangeTable());

                blockDTO.setNotificationType(dto.getChangeType());

                // Check that the current block does not exist yet in the block list
                if(!blockDTOList.contains(blockDTO)) {
                    blockDTOList.add(blockDTO);
                }
            }
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
