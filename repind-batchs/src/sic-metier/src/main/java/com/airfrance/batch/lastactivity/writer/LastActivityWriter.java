package com.airfrance.batch.lastactivity.writer;

import com.airfrance.batch.common.entity.lastactivity.LastActivity;
import com.airfrance.batch.lastactivity.model.LastActivityModel;
import com.airfrance.batch.lastactivity.service.LastActivityService;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Batch last activity writer
 */
@Component
@Slf4j
public class LastActivityWriter implements ItemWriter<LastActivityModel> {

    /**
     * Last activity service - inject by spring
     */
    @Autowired
    private LastActivityService lastActivityService;

    /**
     * Write the process data into database
     *
     * @param lastActivityModels of activity model
     */
    @Override
    public void write(@NotNull List<? extends LastActivityModel> lastActivityModels)
    {
        this.handleWriter(lastActivityModels);

    }

    /**
     * Handle writer
     *
     * @param lastActivityModels of activity model
     */
    private void handleWriter(List<? extends LastActivityModel> lastActivityModels)
    {
        log.info("Beginning of writing process...");
        if (!CollectionUtils.isEmpty(lastActivityModels))
        {
            log.info("The list of data not empty.");
            List<LastActivity> lastActivities = new ArrayList<>();
            for (LastActivityModel lastActivityModel : lastActivityModels)
            {
                log.debug("Last activity process: {}", lastActivityModel.toString());
                log.info("Last activity gin process: {}",lastActivityModel.getGin());
                // Check if last activity exist for the current element and try to check as well the modification date below
                Optional<LastActivity> lastActivityOpt = this.lastActivityService.findByGin(lastActivityModel.getGin());
                if (lastActivityOpt.isPresent())
                {
                    LastActivity lastActivity = lastActivityOpt.get();
                    // Check if the current last modification is inferior to modification date from batch table
                    if (lastActivityModel.getDateModification().compareTo(lastActivity.getDateModification()) > 0)
                    {
                        try
                        {
                            this.lastActivityService.updateLastActivity(this.mapToLastActivity(lastActivityModel));
                        }
                        catch (Exception ex)
                        {
                            log.error("Update error: " + ex);
                        }
                    }
                }
                else
                {
                    // Check if gin exist and has modification date
                    if (Objects.nonNull(lastActivityModel.getGin()) && Objects.nonNull(lastActivityModel.getDateModification()))
                    {
                        lastActivities.add(this.mapToLastActivity(lastActivityModel));
                    }
                }
            }
            log.debug("writing in database");
            //Save and flush data
            try
            {
                this.lastActivityService.saveAndFlushLastActivities(lastActivities);
            }
            catch (Exception ex)
            {
                log.error("insert error: " + ex);
            }
        }
        else
        {
            log.info("The list of data is empty.");
            throw new IllegalArgumentException("The list of data is empty...");
        }
        log.info("end of chunk");
    }

    /**
     * Map last activity model to last activity entity
     *
     * @param lastActivityModel last activity model
     * @return LastActivity
     */
    private LastActivity mapToLastActivity(LastActivityModel lastActivityModel)
    {
        LastActivity lastActivity = new LastActivity();
        lastActivity.setGin(lastActivityModel.getGin());
        lastActivity.setDateModification(lastActivityModel.getDateModification());
        lastActivity.setSignatureModification(lastActivityModel.getSignatureModification());
        lastActivity.setSiteModification(lastActivityModel.getSiteModification());
        lastActivity.setSourceModification(lastActivityModel.getSourceModification());
        return lastActivity;
    }
}
