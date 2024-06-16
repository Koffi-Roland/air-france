package com.airfrance.batch.cleanTravelDoc.processor;

import com.airfrance.batch.cleanTravelDoc.model.CleanTravelDocModel;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CleanTravelDocProcessor implements ItemProcessor<CleanTravelDocModel, CleanTravelDocModel> {

    @Override
    public CleanTravelDocModel process(@NotNull CleanTravelDocModel cleanTravelDocModel) throws Exception {
        return cleanTravelDocModel;
    }
}
