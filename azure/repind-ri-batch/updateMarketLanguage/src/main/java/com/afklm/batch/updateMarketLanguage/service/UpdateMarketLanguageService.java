package com.afklm.batch.updateMarketLanguage.service;

import com.afklm.batch.updateMarketLanguage.model.InputFileModel;
import com.airfrance.ref.exception.jraf.JrafDaoException;
import com.airfrance.repind.dao.individu.CommunicationPreferencesRepository;
import com.airfrance.repind.dao.individu.MarketLanguageRepository;
import com.airfrance.repind.entity.individu.CommunicationPreferences;
import com.airfrance.repind.entity.individu.MarketLanguage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class UpdateMarketLanguageService {

    private static final String DOMAIN = "F";
    private int ginsSuccess = 0;
    private int ginsFail = 0;

    @Autowired
    @Lazy
    MarketLanguage marketLanguage;

    @Autowired
    @Lazy
    List<MarketLanguage>  marketLanguageList;

    @Autowired
    @Lazy
    CommunicationPreferences communicationPreferences;

    @Autowired
    @Lazy
    InputFileModel inputFileModel;

    @Autowired
    MarketLanguageRepository marketLanguageRepository;

    @Autowired
    private CommunicationPreferencesRepository communicationPreferencesRepository;

    @Transactional
    public void updateCorrections(InputFileModel inputFileModel) throws JrafDaoException {
        if(checkGinExists(inputFileModel.getGin())){
            List<CommunicationPreferences> communicationPreferencesList = communicationPreferencesRepository.findComPrefIdByDomain(inputFileModel.getGin(), DOMAIN);
            Date today = new Date();
            for (CommunicationPreferences c : communicationPreferencesList){
                //Updat compref modification block
                c.setModificationDate(today);
                c.setModificationSite("BATCH_QVI");
                c.setModificationSignature("FIX-2279-BATCH");
                communicationPreferencesRepository.saveAndFlush(c);

                MarketLanguage marketLanguageTemp = new MarketLanguage();
                marketLanguageTemp.setComPrefId(c.getComPrefId());
                List<MarketLanguage> l =  marketLanguageRepository.findAll(Example.of(marketLanguageTemp));
                for ( MarketLanguage ml : l){
                    ml.setLanguage(inputFileModel.getNewLanguageCode());
                    ml.setMarket(inputFileModel.getNewMarketCode());
                    ml.setModificationDate(today);
                    ml.setModificationSignature("FIX-2279-BATCH");
                    ml.setModificationSite("BATCH_QVI");
                    marketLanguageRepository.saveAndFlush(ml);
                }
            }
        }
    }

    @Transactional
    public boolean checkGinExists(String gin){
        if (!communicationPreferencesRepository.findByGin(gin).isEmpty()){
            ginsSuccess++;
            return true;
        }
        ginsFail++;
        return false;
    }

    public int getGinsSuccess() {
        return ginsSuccess;
    }

    public int getGinsFail() {
        return ginsFail;
    }
}
