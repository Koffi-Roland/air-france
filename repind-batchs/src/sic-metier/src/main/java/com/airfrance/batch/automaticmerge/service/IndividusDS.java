package com.airfrance.batch.automaticmerge.service;

import com.airfrance.batch.automaticmerge.model.OutputRecord;
import com.airfrance.ref.exception.NotFoundException;
import com.airfrance.repind.dao.individu.IndividuRepository;
import com.airfrance.repind.entity.individu.Individu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.airfrance.batch.automaticmerge.helper.Constant.*;

@Service
public class IndividusDS {

    @Autowired
    private IndividuRepository individuRepository;

    @Transactional
    public void updateIndividuSource(String ginSource, OutputRecord outputRecord) throws NotFoundException {
        Individu individu = getIndividuByGin(ginSource.trim());
        if( individu == null) {
            throw new NotFoundException("Individual with identifier " + ginSource + " is not found");
        }
        individu.setGinFusion(outputRecord.getGinTarget());
        individu.setDateFusion(outputRecord.getMergeDate());
        individu.setStatutIndividu(TRANSFERRED);
        individu.setSignatureModification(SIGNATURE);
        individu.setSiteModification(SITE);
        individu.setDateModification(outputRecord.getMergeDate());
        individuRepository.saveAndFlush(individu);
    }

    public Individu getIndividuByGin(String gin){
        return this.individuRepository.findBySgin(gin);
    }

}
