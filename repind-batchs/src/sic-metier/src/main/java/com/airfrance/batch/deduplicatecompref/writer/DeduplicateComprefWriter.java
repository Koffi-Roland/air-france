package com.airfrance.batch.deduplicatecompref.writer;

import com.airfrance.batch.deduplicatecompref.model.CommunicationPreferencesModel;
import com.airfrance.batch.deduplicatecompref.service.DeduplicateComprefService;
import com.airfrance.repind.bean.CommunicationPreferencesBean;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Component
@Slf4j
public class DeduplicateComprefWriter implements ItemWriter<CommunicationPreferencesModel> {

    /**
     * Deduplicate communication preferences service - inject by spring
     */
    @Autowired
    private DeduplicateComprefService deduplicateComprefService;

    /**
     * Write process for the deduplication communication preferences
     *
     * @param communicationPreferencesModels communication preference model
     * @throws Exception exception
     */
    @Override
    public void write(@NotNull List<? extends CommunicationPreferencesModel> communicationPreferencesModels) throws Exception {

        if (!CollectionUtils.isEmpty(communicationPreferencesModels)) {
            for (CommunicationPreferencesModel communicationPreferencesModel : communicationPreferencesModels)
            {
                log.debug("Communication preferences process: {}", communicationPreferencesModel.toString());
                List<CommunicationPreferencesBean> communicationPreferences = this.deduplicateComprefService.findCommunicationPreferences(communicationPreferencesModel);

              for(CommunicationPreferencesBean communicationPreference : communicationPreferences)
                {
                    try {
                        log.info("Communication preferences: {}", communicationPreference.getComPrefId());
                        this.deduplicateComprefService.updateMarketLanguage(communicationPreferencesModel.getComPrefId(), communicationPreference.getComPrefId());
                        this.deduplicateComprefService.deleteCommunicationPreferences(communicationPreferencesModel.getComPrefId(), communicationPreference.getComPrefId(), communicationPreference.getGin(), communicationPreferencesModel.getComType());
                    }
                    catch (Exception e)
                    {
                        log.info("Unable to deduplicate communication preferences: "+ e.getMessage());
                    }
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
