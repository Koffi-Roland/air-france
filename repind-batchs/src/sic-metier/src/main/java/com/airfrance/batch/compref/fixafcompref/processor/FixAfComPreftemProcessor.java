package com.airfrance.batch.compref.fixafcompref.processor;

import com.airfrance.batch.compref.fixafcompref.helper.ValidationHelper;
import com.airfrance.batch.compref.fixafcompref.service.FixAfComPrefCounterService;
import com.airfrance.repind.entity.individu.Individu;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service("fixAfComPreftemProcessor")
public class FixAfComPreftemProcessor implements ItemProcessor<Individu, Individu> {

    @Autowired
    private FixAfComPrefCounterService fixAfComPrefCounterService;

    @Autowired
    private ValidationHelper validationHelper;


    @Transactional(readOnly = true)
    @Override
    public Individu process(Individu iIndividu) {
        if(isHeader(iIndividu)){
            return null;
        }
        fixAfComPrefCounterService.addNumberOfLineCounter();
        return validationHelper.isValidToProcess(iIndividu) ? iIndividu : null;
    }

    public boolean isHeader(Individu individu){
        return individu.getCommunicationpreferences() == null;
    }



}
