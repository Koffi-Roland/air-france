package com.afklm.batch.migklsub.processor;


import com.afklm.batch.migklsub.helper.ValidationHelper;
import com.afklm.batch.migklsub.service.MigrationKLSubCounterService;
import com.airfrance.repind.entity.individu.Individu;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("individuItemProcessor")
public class IndividuItemProcessor implements ItemProcessor<Individu, Individu> {

    @Autowired
    ValidationHelper validationHelper;

    @Autowired
    MigrationKLSubCounterService migrationKLSubCounterService;

    @Transactional(readOnly = true)
    @Override
    public Individu process(Individu iIndividu) {
        if(isHeader(iIndividu)){
            return null;
        }
        migrationKLSubCounterService.addNumberOfLine();
        if(validationHelper.isIndividuIsPresentInsideContext(iIndividu) == null){
            migrationKLSubCounterService.addDuplicateLine();
            return null;
        }
        return validationHelper.isValidToProcess(iIndividu) ? iIndividu : null;
    }

    public boolean isHeader(Individu individu){
        return individu.getCivilite() == null;
    }



}
