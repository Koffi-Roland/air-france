package com.airfrance.batch.lastactivity.processor;


import com.airfrance.batch.common.entity.lastactivity.LastActivity;
import com.airfrance.batch.lastactivity.model.LastActivityModel;
import com.airfrance.batch.lastactivity.service.LastActivityService;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Last activity processor
 */
@Component
@Slf4j
public class LastActivityProcessor implements ItemProcessor<LastActivityModel, LastActivityModel> {
    /**
     * Last activity service - inject by spring
     */
    @Autowired
    private LastActivityService lastActivityService;

    /**
     * Process last activity
     *
     * @param lastActivityModel last activity model
     * @return Last activity model
     * @throws Exception
     */
    @Override
    public LastActivityModel process(@NotNull LastActivityModel lastActivityModel) throws Exception
    {
        List<LastActivity> lastActivityModelList = this.lastActivityService.findLastActivityByOthers(lastActivityModel.getGin());
        for (LastActivity lastActModel : lastActivityModelList)
        {
            if (lastActModel.getDateModification().compareTo(lastActivityModel.getDateModification()) > 0)
            {
                lastActivityModel.setSignatureModification(lastActModel.getSignatureModification());
                lastActivityModel.setSiteModification(lastActModel.getSiteModification());
                lastActivityModel.setSourceModification(lastActModel.getSourceModification());
                lastActivityModel.setDateModification(lastActModel.getDateModification());
            }
        }

        return lastActivityModel;
    }
}
