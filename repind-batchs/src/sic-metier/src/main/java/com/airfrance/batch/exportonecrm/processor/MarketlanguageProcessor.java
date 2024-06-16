package com.airfrance.batch.exportonecrm.processor;

import com.airfrance.batch.common.metric.SummaryService;
import com.airfrance.batch.exportonecrm.model.MarketLanguage;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MarketlanguageProcessor implements ItemProcessor<MarketLanguage, MarketLanguage>{

    @Autowired
    private SummaryService summaryService;

    @Override
    public MarketLanguage process(@NotNull MarketLanguage marketLanguage) {
        summaryService.incrementProcessedCounter();
        // every 100K items print log counter
        if(summaryService.getProcessed().get() % 50000==0){
            log.info("=========> Processed {} for export MarketLanguage", summaryService.getProcessed().get());
        }
        return marketLanguage;
    }

}