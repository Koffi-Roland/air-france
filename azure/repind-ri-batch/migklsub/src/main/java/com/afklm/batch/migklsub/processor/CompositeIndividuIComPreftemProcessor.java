package com.afklm.batch.migklsub.processor;

import com.afklm.batch.migklsub.helper.ValidationHelper;
import com.afklm.batch.migklsub.service.MigrationKLSubCounterService;
import com.airfrance.repind.entity.individu.CommunicationPreferences;
import com.airfrance.repind.entity.individu.Individu;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service("compositeIndividuComPrefItemProcessor")
public class CompositeIndividuIComPreftemProcessor implements ItemProcessor<Individu, CommunicationPreferences> {

    @Autowired
    private ValidationHelper validationHelper;

    @Autowired
    private MigrationKLSubCounterService migrationKLSubCounterService;

    @Transactional(readOnly = true)
    @Override
    public CommunicationPreferences process(Individu iIndividu) {
        if(isHeader(iIndividu)){
            return null;
        }
        migrationKLSubCounterService.addNumberOfLine();
        if(validationHelper.isIndividuIsPresentInsideContext(iIndividu) == null){
            migrationKLSubCounterService.addDuplicateLine();
            return null;
        }

        return validationHelper.isValidToProcess(iIndividu) ? (CommunicationPreferences) iIndividu.getCommunicationpreferences().toArray()[0] : null;
    }

    public boolean isHeader(Individu individu){
        return individu.getCivilite() == null;
    }
}
