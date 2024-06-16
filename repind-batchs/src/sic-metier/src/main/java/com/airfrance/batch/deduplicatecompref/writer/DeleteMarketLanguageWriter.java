package com.airfrance.batch.deduplicatecompref.writer;

import com.airfrance.batch.deduplicatecompref.model.MarketLanguageModel;
import com.airfrance.batch.deduplicatecompref.service.DeduplicateComprefService;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Component
@Slf4j
public class DeleteMarketLanguageWriter implements ItemWriter<MarketLanguageModel> {

    /**
     * Deduplicate communication preferences service - inject by spring
     */
    @Autowired
    private DeduplicateComprefService deduplicateComprefService;

    /**
     * Write process for the market language
     *
     * @param marketLanguageModels Market language model
     * @throws Exception General exception
     */
    @Override
    public void write(@NotNull List<? extends MarketLanguageModel> marketLanguageModels) throws Exception {

        if (!CollectionUtils.isEmpty(marketLanguageModels))
        {
            for (MarketLanguageModel marketLanguageModel : marketLanguageModels)
            {
                log.info("Delete market language :{}",marketLanguageModel.getMarketLanguageId());
                try
                {
                    this.deduplicateComprefService.deleteByComprefAndMarketLanguage(marketLanguageModel.getComPrefId(), marketLanguageModel.getMarketLanguageId());
                }
                catch (Exception e)
                {
                    log.error("An error occurred during the deletion of the market language");
                }
            }

        }
        else
        {
            log.info("The list of data is empty.");
            throw new IllegalArgumentException("The list of data is empty...");
        }
    }
}
