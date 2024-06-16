package com.afklm.batch.fixafcompref.helper;

import com.afklm.batch.fixafcompref.enums.FileNameEnum;
import com.afklm.batch.fixafcompref.logger.FixAfComPrefLogger;
import com.afklm.batch.fixafcompref.service.FixAfComPrefCounterService;
import com.airfrance.repind.dao.individu.IndividuRepository;
import com.airfrance.repind.dao.reference.PaysRepository;
import com.airfrance.repind.dao.reference.RefLanguageRepository;
import com.airfrance.repind.entity.individu.CommunicationPreferences;
import com.airfrance.repind.entity.individu.Individu;
import com.airfrance.repind.entity.individu.MarketLanguage;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ValidationHelper {

    @Autowired
    private FixAfComPrefCounterService fixAfComPrefCounterService;

    @Autowired
    private IndividuRepository individuRepository;

    @Autowired
    private FixAfComPrefLogger fixAfComPrefLogger;

    @Autowired
    private PaysRepository paysRepository;

    @Autowired
    private RefLanguageRepository refLanguageRepository;

    @Autowired
    private Map<String , FieldSet> mapContext;

    public boolean isValidToProcess(Individu ioIndividu) {
        boolean valid = false;
        if (isValidIndividu(ioIndividu)) {
            CommunicationPreferences tmpComPref = (CommunicationPreferences) ioIndividu.getCommunicationpreferences().toArray()[0];
            valid = isValidMarket(tmpComPref);
        }

        return valid;
    }

    private boolean isValidIndividu(Individu iIndividu) {
        {
            boolean isValid = false;
            CommunicationPreferences communicationPreferences = (CommunicationPreferences) iIndividu.getCommunicationpreferences().toArray()[0];
            if(!isIndividuExistByGin(communicationPreferences.getGin())){
                fixAfComPrefLogger.write(FileNameEnum.GIN_DOESNT_EXIST , getFieldSetData(returnUnicityCode(iIndividu)));
            }
            else{
                fixAfComPrefCounterService.addGinSuccessCounter();
                isValid = true;
            }
            return isValid;
        }
    }

    /**
     * Check if the Market + Language are correct
     * @param iCommunicationPreferences
     * @return true if valid otherwise false
     */
    private boolean isValidMarket(CommunicationPreferences iCommunicationPreferences){
        MarketLanguage marketLanguage = (MarketLanguage) iCommunicationPreferences.getMarketLanguage().toArray()[0];
        if(!isCountryCodeExist(marketLanguage.getMarket())){
            fixAfComPrefLogger.write(FileNameEnum.MARKET_DOESNT_EXIST , getFieldSetData(returnUnicityCode(iCommunicationPreferences)));
            return false;
        }
        if(!isLanguageCodeExist(marketLanguage.getLanguage())){
            fixAfComPrefLogger.write(FileNameEnum.LANGUAGE_DOESNT_EXIST , getFieldSetData(returnUnicityCode(iCommunicationPreferences)));
            return false;
        }
        return true;
    }


    private boolean isCountryCodeExist(String iCountryCode){
        return paysRepository.findCountry(iCountryCode) != null;
    }

    private boolean isLanguageCodeExist(String iLanguageCode){
        return refLanguageRepository.findByLanguageCode(iLanguageCode) != null;
    }

    private boolean isIndividuExistByGin(String iGin){
        return individuRepository.existsById(iGin);
    }

    public String[] getFieldSetData(String iUnicityString){
        FieldSet fieldSet = mapContext.get(iUnicityString);
        return fieldSet !=null ? fieldSet.getValues() : null;
    }

    private String returnUnicityCode(CommunicationPreferences iComPref){
        String comType = iComPref.getComType();
        String gin = iComPref.getGin();
        MarketLanguage marketLanguage = ((MarketLanguage)iComPref.getMarketLanguage().toArray()[0]);
        return gin + comType + marketLanguage.getMarket() + marketLanguage.getLanguage();
    }

    public String returnUnicityCode(Individu iIndividu){
        CommunicationPreferences communicationPreferences = (CommunicationPreferences) iIndividu.getCommunicationpreferences().toArray()[0];
        String gin = communicationPreferences.getGin();
        String comType = communicationPreferences.getComType();
        MarketLanguage marketLanguage = ((MarketLanguage)communicationPreferences.getMarketLanguage().toArray()[0]);
        return gin + comType + marketLanguage.getMarket() + marketLanguage.getLanguage();
    }
}
