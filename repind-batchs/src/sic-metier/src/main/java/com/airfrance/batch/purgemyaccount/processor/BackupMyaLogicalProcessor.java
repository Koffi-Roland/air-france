package com.airfrance.batch.purgemyaccount.processor;

import com.airfrance.batch.common.metric.SummaryService;
import com.airfrance.batch.purgemyaccount.model.MyaPursBackup;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class BackupMyaLogicalProcessor implements ItemProcessor<MyaPursBackup, MyaPursBackup> {

    @Autowired
    private SummaryService summaryService;

    /**
     * Processor that will manage logical deletion for account_data and role_contrats
     * @param myaPursBackup to be processed
     * @return MyaLogicalToDelete
     */
    @Override
    public MyaPursBackup process(@NotNull MyaPursBackup myaPursBackup) {
        summaryService.incrementProcessedCounter();
        // every 1000 items print log counter
        if(summaryService.getProcessed().get() % 1000==0){
            log.info("=========> Processed {} in backup logical delete Mya", summaryService.getProcessed().get());
        }
        return myaPursBackup;
    }
}