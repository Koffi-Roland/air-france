package com.airfrance.batch.exportonecrm.processor;

import com.airfrance.batch.common.metric.SummaryService;
import com.airfrance.batch.exportonecrm.model.ComPrefOcp;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ComprefOcpProcessor implements ItemProcessor<ComPrefOcp, ComPrefOcp>{

    @Autowired
    private SummaryService summaryService;

    @Override
    public ComPrefOcp process(@NotNull ComPrefOcp comPrefOcp) {
        summaryService.incrementProcessedCounter();
        // every 100K items print log counter
        if(summaryService.getProcessed().get() % 100000==0){
            log.info("=========> Processed {} for export ComPrefOcp", summaryService.getProcessed().get());
        }
        return comPrefOcp;
    }

}