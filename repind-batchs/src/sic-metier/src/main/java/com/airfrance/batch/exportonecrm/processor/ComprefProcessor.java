package com.airfrance.batch.exportonecrm.processor;

import com.airfrance.batch.common.metric.SummaryService;
import com.airfrance.batch.exportonecrm.model.ComPref;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ComprefProcessor implements ItemProcessor<ComPref, ComPref>{

    @Autowired
    private SummaryService summaryService;

    @Override
    public ComPref process(@NotNull ComPref comPref) {
        summaryService.incrementProcessedCounter();
        // every 1000 items print log counter
        if(summaryService.getProcessed().get() % 50000==0){
            log.info("=========> Processed {} for export ComPref", summaryService.getProcessed().get());
        }
        return comPref;
    }

}