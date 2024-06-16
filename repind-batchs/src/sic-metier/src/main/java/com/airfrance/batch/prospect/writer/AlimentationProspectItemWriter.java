package com.airfrance.batch.prospect.writer;

import com.airfrance.batch.prospect.bean.AlimentationProspectItem;
import com.airfrance.batch.prospect.helper.AlimentationProspectLogger;
import com.airfrance.batch.prospect.service.AlimentationProspectService;
import com.airfrance.repind.dto.individu.createmodifyindividual.CreateModifyIndividualResponseDTO;
import com.airfrance.repind.service.ws.internal.CreateOrUpdateIndividualDS;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jetbrains.annotations.NotNull;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.List;

import static com.airfrance.batch.prospect.helper.AlimentationProspectConstant.REPORT_FILENAME;

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
