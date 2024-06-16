package com.airfrance.batch.deduplicatecompref.processor;

import com.airfrance.batch.deduplicatecompref.model.CommunicationPreferencesModel;
import org.jetbrains.annotations.NotNull;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class DeduplicateComprefProcessor implements ItemProcessor<CommunicationPreferencesModel, CommunicationPreferencesModel> {

    /**
     * Deduplicate Communication preferences processor
     *
     * @param communicationPreferencesModel Communication preference model
     * @return CommunicationPreferencesModel Communication preference model
     * @throws Exception  exception
     */
    @Override
    public CommunicationPreferencesModel process(@NotNull CommunicationPreferencesModel communicationPreferencesModel) throws Exception {
        return communicationPreferencesModel;
    }
}
