package com.afklm.batch.kpicalculation.processor;

import com.afklm.batch.kpicalculation.model.KPIStatModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class KPICalculationProcessor implements ItemProcessor<KPIStatModel, KPIStatModel> {
    @Override
    public KPIStatModel process(KPIStatModel kpiStatModels) throws Exception {
        if(kpiStatModels.getLabel() == null){
            kpiStatModels.setLabel("null");
        }
        return kpiStatModels;
    }
}
