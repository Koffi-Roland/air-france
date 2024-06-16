package com.afklm.batch.migklsub.helper;

import com.afklm.batch.migklsub.enums.FileNameEnum;
import com.afklm.batch.migklsub.logger.MigrationKLSubscriptionsLogger;
import com.afklm.batch.migklsub.service.MigrationKLSubCounterService;
import com.airfrance.repind.dao.adresse.EmailRepository;
import com.airfrance.repind.dao.individu.IndividuRepository;
import com.airfrance.repind.dao.reference.PaysRepository;
import com.airfrance.repind.dao.reference.RefLanguageRepository;
import com.airfrance.repind.dao.reference.RefMarketCountryLanguageRepositoryCustom;
import com.airfrance.repind.dao.role.RoleContratsRepository;
import com.airfrance.repind.entity.adresse.Email;
import com.airfrance.repind.entity.individu.CommunicationPreferences;
import com.airfrance.repind.entity.individu.Individu;
import com.airfrance.repind.entity.individu.MarketLanguage;
import com.airfrance.repind.entity.role.RoleContrats;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

import static com.airfrance.batch.common.utils.IConstants.SCODE_CONTEXT;

@Service
public class ValidationHelper {

    @Autowired
    private MigrationKLSubscriptionsLogger migrationKLSubscriptionsLogger;

    @Autowired
    private IndividuRepository individuRepository;

    @Autowired
    private RoleContratsRepository roleContratsRepository;

    @Autowired
    private EmailRepository emailRepository;

    @Autowired
    private MigrationKLSubCounterService migrationKLSubCounterService;

    @Autowired
    private PaysRepository paysRepository;

    @Autowired
    private RefLanguageRepository refLanguageRepository;

    @Autowired
    private RefMarketCountryLanguageRepositoryCustom refMarketCountryLanguageRepository;

    @Autowired
    private Map<String , FieldSet> mapContext;

    public boolean isValidToProcess(Individu ioIndividu){
        boolean valid = false;
        if(isValidIndividu(ioIndividu)){
            
            CommunicationPreferences communicationPreferences = (CommunicationPreferences) ioIndividu.getCommunicationpreferences().toArray()[0];
            MarketLanguage marketLanguage = (MarketLanguage) communicationPreferences.getMarketLanguage().toArray()[0];
            mapMarketLanguage(marketLanguage.getMarket(), marketLanguage.getLanguage() , SCODE_CONTEXT, marketLanguage);

            CommunicationPreferences tmpComPref = (CommunicationPreferences) ioIndividu.getCommunicationpreferences().toArray()[0];
            valid = isValidMarket(tmpComPref);
            
        }

        mapContext.remove(returnUnicityCode(ioIndividu));
        return valid;
    }

    /**
     * Check if the individu is correct (existing)
     * @param iIndividu
     * @return true if correct otherwise false
     */
    private boolean isValidIndividu(Individu iIndividu){

        boolean isValid = false;
        RoleContrats roleContrats = (RoleContrats) iIndividu.getRolecontrats().toArray()[0];
        if(!isIndividuExistByGin(roleContrats.getGin())){
            migrationKLSubscriptionsLogger.write(FileNameEnum.GIN_DOESNT_EXIST , getFieldSetData(returnUnicityCode(iIndividu)));
            isValid = false;
        }
        else{
            boolean  isIndividuExistByGinAndCin = isIndividuExistByGinAndCin(roleContrats);
            if (!isIndividuExistByGinAndCin) {
                //Log gin + cin doesnt exist

                migrationKLSubscriptionsLogger.write(FileNameEnum.GIN_AND_CIN_DOESNT_EXIST , getFieldSetData(returnUnicityCode(iIndividu)));
                boolean individuExistByGinAndEmail = isIndividuExistByGinAndEmail((Email) iIndividu.getEmail().toArray()[0]);
                if(!individuExistByGinAndEmail){
                    //Log gin + email doesnt exist
                    migrationKLSubscriptionsLogger.write(FileNameEnum.GIN_AND_EMAIL_DOESNT_EXIST , getFieldSetData(returnUnicityCode(iIndividu)));
                }
                else{
                    isValid = true;
                    migrationKLSubCounterService.addGinEmailSuccessCount();
                }
            }
            else{
                isValid = true;
                migrationKLSubCounterService.addGinCinSuccessCounter();
            }
        }
        return isValid;
    }

    /**

     * Retrieve and set the ISO language
     * @param iMarket Market to search on
     * @param iLanguage Language to search on
     * @param iScodeContext Scode Context to use
     * @param ioMarketLanguage Market language to feed
     */
    private void mapMarketLanguage(String iMarket, String iLanguage, String iScodeContext , MarketLanguage ioMarketLanguage) {
        String codeIsoByMarketLangNotIsoContext = null;
        try {
            codeIsoByMarketLangNotIsoContext = refMarketCountryLanguageRepository.findCodeIsoByMarketLangNotIsoContext(iMarket, iLanguage, iScodeContext);
        }catch (Exception e){
            e.printStackTrace();
        }
        if(codeIsoByMarketLangNotIsoContext != null){
            ioMarketLanguage.setLanguage(codeIsoByMarketLangNotIsoContext);
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
            migrationKLSubscriptionsLogger.write(FileNameEnum.MARKET_DOESNT_EXIST , getFieldSetData(returnUnicityCode(iCommunicationPreferences)));
            return false;
        }
        if(!isLanguageCodeExist(marketLanguage.getLanguage())){
            migrationKLSubscriptionsLogger.write(FileNameEnum.LANGUAGE_DOESNT_EXIST , getFieldSetData(returnUnicityCode(iCommunicationPreferences)));
            return false;
        }
        return true;
    }

    /**
     * Check if the pair(Gin + Cin) exist
     * @param iRoleContrats
     * @return true if role contrat exists otherwise return false
     */
    private boolean isIndividuExistByGinAndCin(RoleContrats iRoleContrats){
        boolean exist = false;
        if(iRoleContrats != null){
            exist =  roleContratsRepository.isGinAndCinFbMaExist(iRoleContrats.getGin() , iRoleContrats.getNumeroContrat());
        }
        return exist;
    }

    /**
     * Check if the pair(Gin + Email) exist
     * @param iEmail
     * @return true if role contrat exists otherwise return false
     */
    private boolean isIndividuExistByGinAndEmail(Email iEmail){
        boolean exist = false;
        if(iEmail != null){
            exist =  emailRepository.isGinEmailExist(iEmail.getSgin() , iEmail.getEmail()) ;
        }
        return exist;
    }

    private boolean isIndividuExistByGin(String iGin){
        return individuRepository.existsById(iGin);
    }

    private String[] getFieldSetData(String iUnicityString){
        FieldSet fieldSet = mapContext.get(iUnicityString);
        return fieldSet !=null ? fieldSet.getValues() : null;
    }

    public FieldSet isIndividuIsPresentInsideContext(Individu iIndividu){
        FieldSet fieldSet = mapContext.get(returnUnicityCode(iIndividu));
        return fieldSet;
    }

    private boolean isCountryCodeExist(String iCountryCode){
        return paysRepository.findCountry(iCountryCode) != null;
    }

    private boolean isLanguageCodeExist(String iLanguageCode){
        return refLanguageRepository.findByLanguageCode(iLanguageCode) != null;
    }

    public String returnUnicityCode(Individu iIndividu){
        String gin = ((RoleContrats)iIndividu.getRolecontrats().toArray()[0]).getGin();
        CommunicationPreferences communicationPreferences = (CommunicationPreferences) iIndividu.getCommunicationpreferences().toArray()[0];
        String comType = communicationPreferences.getComType();
        MarketLanguage marketLanguage = ((MarketLanguage)communicationPreferences.getMarketLanguage().toArray()[0]);
        return gin + comType + marketLanguage.getMarket() + marketLanguage.getLanguage();
    }

    private String returnUnicityCode(CommunicationPreferences iComPref){
        String comType = iComPref.getComType();
        String gin = iComPref.getGin();
        MarketLanguage marketLanguage = ((MarketLanguage)iComPref.getMarketLanguage().toArray()[0]);
        return gin + comType + marketLanguage.getMarket() + marketLanguage.getLanguage();
    }

}
