package com.airfrance.batch.purgemyaccount.writer;

import com.airfrance.batch.purgemyaccount.model.PrPhysicalDelete;
import com.airfrance.batch.purgemyaccount.service.PreferenceService;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Component
@Slf4j
public class PrPhysicalDeleteWriter implements ItemWriter<PrPhysicalDelete> {

    @Autowired
    private PreferenceService preferenceService;

    /**
     * Write process for delete physically preferences and associated preference_data
     *
     * @param prPhysicalDeleteItems preference model
     * @throws Exception exception
     */
    @Override
    public void write(@NotNull List<? extends PrPhysicalDelete> prPhysicalDeleteItems) throws Exception {
        if (!CollectionUtils.isEmpty(prPhysicalDeleteItems)) {
            prPhysicalDeleteItems.forEach(item -> {
                this.preferenceService.physicalDeletePreference(item.getPreferenceId());
            });
        }
        else
        {
            log.error("The list of data is empty.");
            throw new IllegalArgumentException("The list of data is empty...");
        }
    }
}
