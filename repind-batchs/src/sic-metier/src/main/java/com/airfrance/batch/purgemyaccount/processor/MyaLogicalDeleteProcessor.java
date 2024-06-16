package com.airfrance.batch.purgemyaccount.processor;

import com.airfrance.batch.common.metric.SummaryService;
import com.airfrance.batch.purgemyaccount.model.MyaLogicalToDelete;
import com.airfrance.batch.purgemyaccount.service.AccountDataService;
import com.airfrance.batch.purgemyaccount.service.PurgeMyAccountService;
import com.airfrance.batch.purgemyaccount.service.RoleContratService;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MyaLogicalDeleteProcessor implements ItemProcessor<MyaLogicalToDelete, MyaLogicalToDelete> {

    @Autowired
    private AccountDataService accountDataService;
    @Autowired
    private RoleContratService roleContratService;
    @Autowired
    private SummaryService summaryService;

    /**
     * Processor that will manage logical deletion for account_data and role_contrats
     * @param myaLogicalToDelete to be processed
     * @return MyaLogicalToDelete
     */
    @Override
    public MyaLogicalToDelete process(@NotNull MyaLogicalToDelete myaLogicalToDelete) {
        summaryService.incrementProcessedCounter();
        // every 1000 items print log counter
        if(summaryService.getProcessed().get() % 1000==0){
            log.info("=========> Processed {} in logical delete Mya", summaryService.getProcessed().get());
        }
        try{
            boolean isAdLogicalDeleted = accountDataService.logicalDeleteAccountData(myaLogicalToDelete);
            boolean isRcLogicalDeleted = roleContratService.logicalDeleteRoleContrat(myaLogicalToDelete);

            if(isAdLogicalDeleted && isRcLogicalDeleted){
                return myaLogicalToDelete;
            }
            return null;
        }catch (Exception e){
            log.error("[-] Exception during process [MyaLogicalDeleteProcessor] : {}", e.getMessage(), e);
            PurgeMyAccountService.writeRejectedData(myaLogicalToDelete.toString());
            return null;
        }
    }

}