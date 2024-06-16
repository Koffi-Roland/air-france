package com.airfrance.batch.kpicalculation.writer;

import com.airfrance.batch.common.entity.kpi.HistorizationKPIEntity;
import com.airfrance.batch.common.repository.kpi.HistorizationKpiRepository;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Component
@Slf4j
public class OldDataWriter implements ItemWriter<HistorizationKPIEntity> {

    @Autowired
    private HistorizationKpiRepository historizationKpiRepository;

    @Override
    public void write(@NotNull List<? extends HistorizationKPIEntity> list) {
        log.info("Beginning of deletion process process...");
        if (CollectionUtils.isEmpty(list)) {
            log.info("No old data found, end of deletion process !");
        }
        historizationKpiRepository.deleteAllById(list.stream().map(HistorizationKPIEntity::getId).toList());
        log.info("Old data deleted from database successfully !");
    }
}
