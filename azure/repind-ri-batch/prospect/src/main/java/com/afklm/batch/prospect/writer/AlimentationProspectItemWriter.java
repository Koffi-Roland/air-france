package com.afklm.batch.prospect.writer;

import com.afklm.batch.prospect.bean.AlimentationProspectItem;
import com.afklm.batch.prospect.service.AlimentationProspectService;
import com.airfrance.repind.service.ws.internal.CreateOrUpdateIndividualDS;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.batch.item.ItemWriter;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Slf4j
public class AlimentationProspectItemWriter implements ItemWriter<AlimentationProspectItem> {

    private final CreateOrUpdateIndividualDS createOrUpdateIndividualDS;
    private final AlimentationProspectService prospectService;

    public AlimentationProspectItemWriter(
            CreateOrUpdateIndividualDS createOrUpdateIndividualDS,
            AlimentationProspectService prospectService) {
        this.createOrUpdateIndividualDS = createOrUpdateIndividualDS;
        this.prospectService = prospectService;
    }

    @Override
    public void write(List<? extends AlimentationProspectItem> items) {
        if (!CollectionUtils.isEmpty(items)) {
            items.forEach(this::manageItemCreationOrUpdate);
        }
    }

    public void manageItemCreationOrUpdate(@NotNull AlimentationProspectItem item) {
        String email = item.getEmail();
        prospectService.addTotalCount();
        try {
            createOrUpdateIndividualDS.CreateOrUpdateProspectFromBatch(item.getRequestWS(), email);
            prospectService.addSuccessCount();
        } catch (RuntimeException e) {
            log.error("Unable to create or update data for customer " + email + " - " + e.getMessage());
            prospectService.addFailedCount();
            prospectService.storeMessage(email + " - " + e.getMessage());
        }
    }
}
