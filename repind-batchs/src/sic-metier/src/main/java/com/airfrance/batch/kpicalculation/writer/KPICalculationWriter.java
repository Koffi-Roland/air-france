package com.airfrance.batch.kpicalculation.writer;

import com.airfrance.batch.common.entity.kpi.HistorizationKPIEntity;
import com.airfrance.batch.kpicalculation.model.KPIStatModel;
import com.airfrance.batch.common.repository.kpi.HistorizationKpiRepository;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
@Slf4j
public class KPICalculationWriter implements ItemWriter<KPIStatModel> {

    @Autowired
    private HistorizationKpiRepository historizationKpiRepository;

    @Override
    public void write(@NotNull List<? extends KPIStatModel> list) throws IllegalArgumentException {
        log.info("Beginning of writing process...");
        if (CollectionUtils.isEmpty(list)) {
            throw new IllegalArgumentException("The list of data is empty...");
        }
        log.info("The list of data not empty.");
        List<HistorizationKPIEntity> res = new ArrayList<>();
        for (KPIStatModel element : list) {
            HistorizationKPIEntity tmp = new HistorizationKPIEntity();
            tmp.setKpi(element.getKpi());
            tmp.setLabel(element.getLabel());
            tmp.setValue(element.getValue());
            tmp.setOptionalData(element.getOptionalData());
            tmp.setDate(new Date());
            res.add(tmp);
        }

        historizationKpiRepository.saveAllAndFlush(res);
        log.info("Data uploaded in database successfully !");
    }
}
