package com.afklm.batch.kpicalculation.processor;

import com.airfrance.batch.common.entity.kpi.HistorizationKPIEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class OldDataProcessor implements ItemProcessor<HistorizationKPIEntity, HistorizationKPIEntity> {
    @Override
    public HistorizationKPIEntity process(HistorizationKPIEntity historizationKPIEntity) throws Exception {
        return historizationKPIEntity;
    }
}
