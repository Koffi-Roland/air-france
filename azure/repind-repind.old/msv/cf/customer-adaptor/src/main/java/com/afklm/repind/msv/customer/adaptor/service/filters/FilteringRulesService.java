package com.afklm.repind.msv.customer.adaptor.service.filters;

import com.afklm.repind.common.entity.contact.EmailEntity;
import com.afklm.repind.common.entity.contact.PostalAddress;
import com.afklm.repind.common.entity.individual.Individu;
import com.afklm.repind.common.entity.preferences.CommunicationPreferencesEntity;
import com.afklm.repind.common.entity.preferences.MarketLanguageEntity;
import com.afklm.repind.common.entity.role.RoleContract;
import com.afklm.repind.common.repository.contact.EmailRepository;
import com.afklm.repind.common.repository.individual.IndividuRepository;
import com.afklm.repind.common.repository.preferences.CommunicationPreferencesRepository;
import com.afklm.repind.common.repository.preferences.MarketLanguageRepository;
import com.afklm.repind.common.repository.role.RoleContractRepository;
import com.afklm.repind.msv.customer.adaptor.model.repind.Emails;
import com.afklm.repind.msv.customer.adaptor.model.repind.TmpProfile;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.afklm.repind.msv.customer.adaptor.helper.Constant.*;


@Service
public class FilteringRulesService {
    @Autowired
    IndividuRepository individuRepository;
    @Autowired
    CommunicationPreferencesRepository communicationPreferencesRepository;
    @Autowired
    MarketLanguageRepository marketLanguageRepository;
    @Autowired
    RoleContractRepository roleContractRepository;
    @Autowired
    EmailRepository emailRepository;
    @Getter
    private String message;

    private static final Logger LOGGER = LoggerFactory.getLogger(FilteringRulesService.class);


    /**
     *   RULES CustomCAD:
     *        -- is KL Subscribe : type(KL,KL_PART), domain='S', GroupType='N'
     * @return  true  -> exclude from SFMC rules (do not send to SFMC)
     *          false -> do not exclude from SFMC rules
     */
    private boolean isKLSubscribe(CommunicationPreferencesEntity compref) {
        return ("KL".equals(compref.getComType()) || "KL_PART".equals(compref.getComType()))
                && "S".equals(compref.getDomain())
                && "N".equals(compref.getComGroupType());
    }

    public boolean ruleComprefIsNotKLSubscribe(String gin){
        List<CommunicationPreferencesEntity> comPrefList = communicationPreferencesRepository.getCommunicationPreferencesByIndividuGin(gin);

        if (comPrefList == null || comPrefList.isEmpty()) {
            message = "--> ruleComprefIsNotKLSubscribe: -> true";
            LOGGER.info(message);
            return true;
        }

        comPrefList.forEach(compref ->
            LOGGER.info("{} - {} - {}", gin, compref.getIndividu().getGin(), compref.getComType())
        );

        boolean checkComPrefIsKLSubscribe = comPrefList.stream()
                .noneMatch(this::isKLSubscribe);
        message = "--> ruleComprefIsNotKLSubscribe : -> " + checkComPrefIsKLSubscribe;
        LOGGER.info(message);
        return checkComPrefIsKLSubscribe ;
    }

    public boolean ruleComprefIsNotKLSubscribeByComprefId(Long comprefId){
        CommunicationPreferencesEntity comPref = communicationPreferencesRepository.getComPrefByComPrefId(comprefId);

        if (comPref==null) {
            message = "--> ruleComprefIsNotKLSubscribeByComprefId: -> true";
            LOGGER.info(message);
            return true;
        }
        boolean checkComPrefIsKLSubscribe = !isKLSubscribe(comPref);
        message = "--> ruleComprefIsNotKLSubscribeByComprefId : -> " + checkComPrefIsKLSubscribe;
        LOGGER.info(message);
        return checkComPrefIsKLSubscribe;
    }


    /**
     *    RULES CustomCAD:
     *      -- No status for subscription
     *      -- No opt-in/out date (update date) for subscription
     *
     * @return  true  -> exclude from SFMC rules (do not send to SFMC)
     *          false -> do not exclude from SFMC rules
     */
    private boolean isSubscriptionNotPresent(MarketLanguageEntity marketLanguage) {
        return marketLanguage.getOptin() == null || marketLanguage.getDateOptin() == null;
    }

    public boolean ruleComprefSubscriptionNotExist(String gin){
        List<CommunicationPreferencesEntity> comPrefList = communicationPreferencesRepository.getCommunicationPreferencesByIndividuGin(gin);

        if (comPrefList == null || comPrefList.isEmpty()) {
            LOGGER.info("--> ruleComprefSubscriptionNotExist: -> true");
            return true;
        }

        boolean  comPrefSubscription = comPrefList.stream()
                    .filter(this::isKLSubscribe)
                    .map(CommunicationPreferencesEntity::getComPrefId)
                    .map(comPrefId -> marketLanguageRepository.getMarketLanguageEntitiesByComPrefId(comPrefId))
                    .flatMap(List::stream).anyMatch(this::isSubscriptionNotPresent);

        message = "--> ruleComprefSubscriptionNotExist : -> " + comPrefSubscription;
        LOGGER.info(message);
        return comPrefSubscription;
    }

    /**
     *    RULES CustomCAD:
     *      -- No Market for subscription
     *      -- No language for subscription
     *
     * @return  true  -> exclude from SFMC rules (do not send to SFMC)
     *          false -> do not exclude from SFMC rules
     */
    public boolean ruleComprefMarketLanguageNotExist(String gin){
        boolean checkComPrefIsMarketLanguage = communicationPreferencesRepository.checkComPrefMarketLanguageNotExist(gin);
        message = "--> ruleComprefMarketLanguageNotExist : -> " + checkComPrefIsMarketLanguage;
        LOGGER.info(message);
        return checkComPrefIsMarketLanguage;
    }

    /**
     *    RULES CustomCAD:
     *      -- No KL subscription (type 'KL' or 'KL_PART') where optin value 'Y'
     *
     * @return  true  -> exclude from SFMC rules (do not send to SFMC)
     *          false -> do not exclude from SFMC rules
     */
    public boolean ruleOptinValue(String gin){
        boolean checkIfGinIsNotOptin = communicationPreferencesRepository.checkIfGinIsNotOptin(gin);
        message = "--> ruleOptinValue : -> " + checkIfGinIsNotOptin;
        LOGGER.info(message);
        return checkIfGinIsNotOptin;
    }

    /**
     *    RULES CustomCAD:
     *      -- No FB Number present AND FB enrollment date is not null
     *      -- FB/MA contract is present AND no FB/MA enrollment date
     *
     * @return  true  -> exclude from SFMC rules (do not send to SFMC)
     *          false -> do not exclude from SFMC rules
     */
    public boolean ruleRoleContract(String gin){
        boolean checkRoleContrat = roleContractRepository.checkRoleContratIsNotEligibleToSendToSFMCByGin(gin);
        message = "--> ruleRoleContract : -> " + checkRoleContrat;
        LOGGER.info(message);
        return checkRoleContrat;
    }

    /**
     *    RULES CustomCAD:
     *      -- Age should not be less than 16
     *
     * @return  true  -> exclude from SFMC rules (do not send to SFMC)
     *          false -> do not exclude from SFMC rules
     */
    public boolean ruleBirthdateMajor(String gin){
        boolean checkBirthDate = individuRepository.checkBirthDateIsNotEligibleToSendToSFMCByGin(gin);
        message = "--> ruleBirthdateMajor : -> " + checkBirthDate;
        LOGGER.info(message);
        return checkBirthDate;
    }

    /**
     *    RULES CustomCAD:
     *      -- check for no invalid email
     *
     * @return  true  -> exclude from SFMC rules (do not send to SFMC)
     *          false -> do not exclude from SFMC rules
     */
    public boolean ruleEmailValidity(String gin){
       boolean checkEmailValidity = emailRepository.checkEmailIsDirectAndValid(gin);
       message = "--> ruleEmailValidity : -> " + checkEmailValidity;
       LOGGER.info(message);
       return checkEmailValidity;
    }

    /**
     *    RULES CustomCAD:
     *      -- check if email is Professional email
     *
     * @return  true  -> exclude from SFMC rules (do not send to SFMC)
     *          false -> do not exclude from SFMC rules
     */
    public boolean ruleEmailPro(Emails email){
        boolean ruleEmailPro = CODE_MEDIUM_P.equals(email.getMediumCode());
        message = "--> ruleEmailPro : -> " + ruleEmailPro;
        LOGGER.info(message);
        return ruleEmailPro;
    }

    /**
     *    RULES CustomCAD:
     *      -- check if postal address is Professional (corporate)
     *
     * @return  true  -> exclude from SFMC rules (do not send to SFMC)
     *          false -> do not exclude from SFMC rules
     */
    public boolean rulePostalAddressPro(com.afklm.repind.msv.customer.adaptor.model.repind.PostalAddress adrPost){
        boolean checkAdrPostCorporate = CODE_MEDIUM_P.equals(adrPost.getMediumCode());
        message = "--> rulePostalAddressProfessional : -> " + checkAdrPostCorporate;
        LOGGER.info(message);
        return checkAdrPostCorporate;
    }

    public void clearMessage(){
        this.message = null;
    }

    @Transactional
    public TmpProfile fetchTmpProfile(String gin){
        LOGGER.info("[+] Start fetch temporary profile for GIN={} :", gin);
        Individu individuFromDB = individuRepository.getIndividuByGin(gin);

        //init INDIVIDUS_ALL fields
        EmailEntity emailFromDB = individuFromDB.getEmails().stream()
                .filter(email -> CODE_MEDIUM_D.equals(email.getCodeMedium()) && STATUT_MEDIUM_V.equals(email.getStatutMedium()))
                .findFirst().orElse(new EmailEntity());

        PostalAddress adrPostFromDB = individuFromDB.getPostalAddresses().stream()
                .filter(adrPost -> CODE_MEDIUM_D.equals(adrPost.getCodeMedium()) && STATUT_MEDIUM_V.equals(adrPost.getStatutMedium()))
                .findFirst().orElse(new PostalAddress());

        Set<RoleContract> roleContracts = new HashSet<>();
        individuFromDB.getRoleContracts().stream()
                .filter(roleContract -> (TYPE_CONTRAT_FP.equals(roleContract.getTypeContrat()) || TYPE_CONTRAT_MA.equals(roleContract.getTypeContrat()) ) && roleContract.getDateCreation()!=null)
                .forEach (roleContracts::add);

        TmpProfile tmpProfile = new TmpProfile().setEmailsFields(emailFromDB).setIndividusAllFields(individuFromDB).setAdrPostFields(adrPostFromDB);

        roleContracts.forEach(tmpProfile::setRoleContractsFields);

        LOGGER.info("[+] End fetch temporary profile.");
        return tmpProfile;
    }
}
