package com.airfrance.batch.deduplicatecompref.processor;

import com.airfrance.batch.deduplicatecompref.model.MarketLanguageModel;
import org.jetbrains.annotations.NotNull;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class DeleteMarketLanguageProcessor implements ItemProcessor<MarketLanguageModel, MarketLanguageModel> {

    /**
     * Delete market language processor
     * @param marketLanguageModel market language model
     * @return marketLanguageModel market language model
     * @throws Exception exception
     */
    @Override
    public MarketLanguageModel process(@NotNull MarketLanguageModel marketLanguageModel) throws Exception {
        return marketLanguageModel;
    }
}
