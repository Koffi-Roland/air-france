package com.airfrance.batch.common.service;

import com.airfrance.batch.common.enums.pcs.Score;
import com.airfrance.batch.common.enums.pcs.TravelPreferenceType;
import com.airfrance.batch.common.enums.pcs.UltimatePreferenceType;
import com.airfrance.batch.common.enums.pcs.Variables;
import com.airfrance.batch.common.wrapper.WrapperProvideIndividualScoreResponse;
import com.airfrance.repind.dao.adresse.EmailRepository;
import com.airfrance.repind.dao.adresse.PostalAddressRepository;
import com.airfrance.repind.dao.adresse.TelecomsRepository;
import com.airfrance.repind.dao.delegation.DelegationDataRepository;
import com.airfrance.repind.dao.external.ExternalIdentifierRepository;
import com.airfrance.repind.dao.individu.AccountDataRepository;
import com.airfrance.repind.dao.individu.CommunicationPreferencesRepository;
import com.airfrance.repind.dao.individu.IndividuRepository;
import com.airfrance.repind.dao.preference.PreferenceRepository;
import com.airfrance.repind.dao.profil.ProfilsRepository;
import com.airfrance.repind.dao.reference.RefPcsFactorRepository;
import com.airfrance.repind.dao.reference.RefPcsScoreRepository;
import com.airfrance.repind.dao.role.RoleContratsRepository;
import com.airfrance.repind.entity.adresse.PostalAddress;
import com.airfrance.repind.entity.delegation.DelegationData;
import com.airfrance.repind.entity.external.ExternalIdentifier;
import com.airfrance.repind.entity.individu.AccountData;
import com.airfrance.repind.entity.individu.CommunicationPreferences;
import com.airfrance.repind.entity.individu.Individu;
import com.airfrance.repind.entity.preference.Preference;
import com.airfrance.repind.entity.profil.Profils;
import com.airfrance.repind.entity.role.RoleContrats;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Stream;

@Service
@Slf4j
public class ProvideIndividualScoreService {

    @Autowired
    private RefPcsScoreRepository refPcsScoreRepository;
    @Autowired
    private RefPcsFactorRepository refPcsFactorRepository;
    @Autowired
    private RoleContratsRepository roleContractRepository;
    @Autowired
    private IndividuRepository individuRepository;
    @Autowired
    private ProfilsRepository profilsRepository;
    @Autowired
    private EmailRepository emailRepository;
    @Autowired
    private AccountDataRepository accountDataRepository;
    @Autowired
    private TelecomsRepository telecomsRepository;
    @Autowired
    private PostalAddressRepository postalAddressRepository;
    @Autowired
    private ExternalIdentifierRepository externalIdentifierRepository;
    @Autowired
    private PreferenceRepository preferenceRepository;
    @Autowired
    private CommunicationPreferencesRepository communicationPreferencesRepository;
    @Autowired
    private DelegationDataRepository delegationDataRepository;



    private static final String CONTRACT = "C";
    private static final String NON_CONTRACT = "NC";
    private static final String STATUT_VALID = "V";
    private static final String CODE_PRO = "P";
    private static final String CODE_DIRECT = "D";
    private static final String MOBILE = "M";


    /**
     * Provide the profile completeness score of all the duplicated gins
     *
     * @param gins, List of duplicated gins
     *
     */
    public List<WrapperProvideIndividualScoreResponse> calculatePcsScore(List<String> gins) {
        List<WrapperProvideIndividualScoreResponse> listResponse = new ArrayList<>();

        double score = 0;

        gins = gins.stream()
                .filter(gin -> gin.length() > 0)
                .toList();


        for(String gin : gins){

            WrapperProvideIndividualScoreResponse wrapperProvideIndividualScoreResponse = new WrapperProvideIndividualScoreResponse();

            if(individuRepository.findBySgin(gin) != null){
                score = calculateContractScore(gin) + calculateNonContractScore(gin);
                wrapperProvideIndividualScoreResponse.setGin(gin);
                wrapperProvideIndividualScoreResponse.setScore(score);
                listResponse.add(wrapperProvideIndividualScoreResponse);
            }
        }

        return listResponse.stream()
                .sorted(Comparator.comparing(WrapperProvideIndividualScoreResponse::getScore).reversed())
                .toList();
    }

    /**
     * Provide the contract score of a gin
     *
     * @param gin, gin duplicate
     *
     */
    public double calculateContractScore(String gin){
        long contractScore = 0;
        List<Integer> contractGinScores = new ArrayList<>();
        List<RoleContrats> roleContracts = roleContractRepository.findByGin(gin);

        if(! roleContracts.isEmpty()){
            List<String> roleContractTypes = roleContracts.stream()
                    .map(RoleContrats::getTypeContrat)
                    .toList();
            //Take only max score depending on contract type : FP > MA > S > ABonné
            if(isSubscriberContract(roleContractTypes) && refPcsScoreRepository.findByCode(Score.AB.getName()) != null ){
                contractGinScores.add(refPcsScoreRepository.findByCode(Score.AB.getName()).getScore());
            }
            if (roleContractTypes.contains(Score.S.getName()) && refPcsScoreRepository.findByCode(Score.S.getName()) != null) {
                contractGinScores.add(refPcsScoreRepository.findByCode(Score.S.getName()).getScore());
            }
            if (roleContractTypes.contains(Score.MA.getName()) && refPcsScoreRepository.findByCode(Score.MA.getName()) != null) {
                contractGinScores.add(refPcsScoreRepository.findByCode(Score.MA.getName()).getScore());
            }
            if (roleContractTypes.contains(Score.FP.getName()) && refPcsScoreRepository.findByCode(Score.FP.getName()) != null) {
                contractGinScores.add(refPcsScoreRepository.findByCode(Score.FP.getName()).getScore());
            }
            if (isOtherContract(roleContractTypes) && refPcsScoreRepository.findByCode(Score.OC.getName()) != null) {
                contractGinScores.add(refPcsScoreRepository.findByCode(Score.OC.getName()).getScore());
            }
            if(! contractGinScores.isEmpty()){
                contractScore = (Collections.max(contractGinScores) * refPcsFactorRepository.findByCode(CONTRACT).getFactor()) / 100;
            }
        }
        return  contractScore;
    }

    /**
     * Checks if individual have subscriber contract
     *
     * @param contractTypeList, List of contracts of an individual
     *
     */
    public boolean isSubscriberContract(List<String> contractTypeList){
        return (contractTypeList.contains(Variables.RC.getName()) || contractTypeList.contains(Variables.DP.getName()) || contractTypeList.contains(Variables.RP.getName()));
    }

    /**
     * Checks if individual have other contract than FB, MA, S or subscriber
     *
     * @param contractTypeList, List of contracts of an individual
     *
     */
    public boolean isOtherContract(List<String> contractTypeList){
        return (! (contractTypeList.contains(Variables.RC.getName()) || contractTypeList.contains(Variables.DP.getName()) || contractTypeList.contains(Variables.RP.getName()) || contractTypeList.contains(Score.FP.getName())|| contractTypeList.contains(Score.MA.getName()) || contractTypeList.contains(Score.S.getName())));
    }


    /**
     * Provide the non contract score of a gin
     *
     * @param gin, gin duplicate
     *
     */
    public double calculateNonContractScore(String gin){

        double nonContractScore = calculateIdentityScore(gin)
                + calculateProfileScore(gin)
                + calculateContactScore(gin)
                + calculateExternalIdScore(gin)
                + calculatePreferencesScore(gin)
                + calculateComPrefScore(gin)
                + calculateDelegationScore(gin)
                + calculateRoleScore(gin);

        return  (nonContractScore * refPcsFactorRepository.findByCode(NON_CONTRACT).getFactor()) / 100;
    }

    /**
     * Calculate the score of identity fields  of a gin
     *
     * @param gin, gin duplicate
     *
     */
    public double calculateIdentityScore(String gin){
        double identityScore = 0;

        if(individuRepository.findBySgin(gin) != null ) {
            Individu individu = individuRepository.findBySgin(gin);
            if (StringUtils.isNotBlank(individu.getNom()) && refPcsScoreRepository.findByCode(Score.LN.getName()) != null) {
                identityScore += refPcsScoreRepository.findByCode(Score.LN.getName()).getScore();
            }
            if (StringUtils.isNotEmpty(individu.getPrenom()) && refPcsScoreRepository.findByCode(Score.FN.getName()) != null) {
                identityScore += refPcsScoreRepository.findByCode(Score.FN.getName()).getScore();
            }
            if (individu.getDateNaissance() != null && refPcsScoreRepository.findByCode(Score.DB.getName()) != null) {
                identityScore += refPcsScoreRepository.findByCode(Score.DB.getName()).getScore();
            }
            if (StringUtils.isNotBlank(individu.getSexe()) && refPcsScoreRepository.findByCode(Score.GE.getName()) != null) {
                identityScore += refPcsScoreRepository.findByCode(Score.GE.getName()).getScore();
            }
            if (StringUtils.isNotBlank(individu.getCivilite()) && refPcsScoreRepository.findByCode(Score.CI.getName()) != null) {
                identityScore += refPcsScoreRepository.findByCode(Score.CI.getName()).getScore();
            }
        }
        return identityScore;
    }

    /**
     * Calculate the score of language code field  of a gin
     *
     * @param gin, gin duplicate
     *
     */
    public long calculateProfileScore(String gin){
        long profileScore = 0;
        Profils profil = profilsRepository.findBySgin(gin);
        if(profil != null) {
            String codeLangue = profil.getScode_langue();
            if (StringUtils.isNotBlank(codeLangue) && refPcsScoreRepository.findByCode(Score.SL.getName()) != null) {
                profileScore += refPcsScoreRepository.findByCode(Score.SL.getName()).getScore();
            }
        }
        return profileScore;
    }

    /**
     * Calculate the score of contact fields  of a gin
     *
     * @param gin, gin duplicate
     *
     */
    public double calculateContactScore(String gin) {
        double contactScore = 0;
        AccountData accountData = accountDataRepository.findBySgin(gin);
        if (accountData != null) {
            String emailIdentifier = accountData.getEmailIdentifier();
            if (StringUtils.isNotBlank(emailIdentifier) && refPcsScoreRepository.findByCode(Score.EI.getName()) != null) {
                contactScore += refPcsScoreRepository.findByCode(Score.EI.getName()).getScore();
            }
        }
        if (!emailRepository.findBySginAndCodeMediumAndStatutMedium(gin, CODE_DIRECT, STATUT_VALID).isEmpty() && refPcsScoreRepository.findByCode(Score.ED.getName()) != null) {
            contactScore += refPcsScoreRepository.findByCode(Score.ED.getName()).getScore();
        }
        if (!emailRepository.findBySginAndCodeMediumAndStatutMedium(gin, CODE_PRO, STATUT_VALID).isEmpty() && refPcsScoreRepository.findByCode(Score.EP.getName()) != null) {
            contactScore += refPcsScoreRepository.findByCode(Score.EP.getName()).getScore();
        }
        if (!telecomsRepository.findBySginAndScodeMediumAndStatutMediumAndSterminal(gin, CODE_PRO, STATUT_VALID, MOBILE).isEmpty() && refPcsScoreRepository.findByCode(Score.MP.getName()) != null) {
            contactScore += refPcsScoreRepository.findByCode(Score.MP.getName()).getScore();
        }
        if (!telecomsRepository.findBySginAndScodeMediumAndStatutMediumAndSterminal(gin, CODE_DIRECT, STATUT_VALID, MOBILE).isEmpty() && refPcsScoreRepository.findByCode(Score.MD.getName()) != null) {
            contactScore += refPcsScoreRepository.findByCode(Score.MD.getName()).getScore();
        }
        List<PostalAddress> postalAddressList = postalAddressRepository.findByGinAndStatus(gin, List.of(STATUT_VALID));
        if (!postalAddressList.isEmpty()) {
            List<String> streetList = postalAddressList.stream()
                    .map(PostalAddress::getSno_et_rue)
                    .filter(Objects::nonNull)
                    .toList();
            if (!streetList.isEmpty()  && refPcsScoreRepository.findByCode(Score.PA.getName()) != null) {
                contactScore += refPcsScoreRepository.findByCode(Score.PA.getName()).getScore();
            }
        }
        return contactScore;
    }

    /**
     * Calculate the score of External Identifier fields  of a gin
     *
     * @param gin, gin duplicate
     *
     */
    public double calculateExternalIdScore(String gin){
        double externalIdScore = 0;

        List<ExternalIdentifier> externalIdentifierList = externalIdentifierRepository.findByGin(gin);
        if(! externalIdentifierList.isEmpty()){

            List<String> pnmIdList = externalIdentifierList.stream()
                    .map(ExternalIdentifier::getType)
                    .filter(t -> Variables.PNM.getName().equals(t))
                    .toList();
            if (!pnmIdList.isEmpty() && refPcsScoreRepository.findByCode(Score.PN.getName()) != null) {
                externalIdScore += refPcsScoreRepository.findByCode(Score.PN.getName()).getScore();
            }
            List<String> socialIdList = externalIdentifierList.stream()
                    .map(ExternalIdentifier::getType)
                    .filter(t -> ! Variables.PNM.getName().equals(t))
                    .toList();
            if (!socialIdList.isEmpty() && refPcsScoreRepository.findByCode(Score.SI.getName()) != null) {
                externalIdScore += refPcsScoreRepository.findByCode(Score.SI.getName()).getScore();
            }
        }
        return externalIdScore;
    }

    /**
     * Calculate the score of preference fields  of a gin
     *
     * @param gin, gin duplicate
     *
     */
    public double calculatePreferencesScore(String gin){
        double preferenceScore = 0;

        List<Preference> preferences = preferenceRepository.findByGin(gin);
        if(! preferences.isEmpty()){
            List<String> preferenceEntityTypeList = preferences
                    .stream()
                    .map(Preference::getType)
                    .toList();

            //Travel Preference Check
            List<String> travelPreferenceTypes = Stream.of(TravelPreferenceType.values())
                    .map(TravelPreferenceType::name)
                    .toList();
            if(!Collections.disjoint(preferenceEntityTypeList, travelPreferenceTypes) && refPcsScoreRepository.findByCode(Score.TP.getName()) != null){
                preferenceScore += refPcsScoreRepository.findByCode(Score.TP.getName()).getScore();
            }
            //Ultimate Preference check
            List<String> ultimatePreferenceTypes = Stream.of(UltimatePreferenceType.values())
                    .map(UltimatePreferenceType::name)
                    .toList();
            if(!Collections.disjoint(preferenceEntityTypeList, ultimatePreferenceTypes) && refPcsScoreRepository.findByCode(Score.UP.getName()) != null){
                preferenceScore += refPcsScoreRepository.findByCode(Score.UP.getName()).getScore();
            }
        }
        return preferenceScore;
    }

    /**
     * Calculate the score of communication preference fields  of a gin
     *
     * @param gin, gin duplicate
     *
     */
    public double calculateComPrefScore(String gin) {
        double comPrefScore = 0;

        List<CommunicationPreferences> comPrefs = communicationPreferencesRepository.findByGin(gin);
        if(! comPrefs.isEmpty()){
            //FB ComPref check
            List<CommunicationPreferences> comPrefList = comPrefs
                    .stream()
                    .filter(cm -> Variables.F.getName().equals(cm.getDomain()))
                    .filter(cm -> Variables.Y.getName().equals(cm.getSubscribe()))
                    .toList();

           if(! comPrefList.isEmpty() && refPcsScoreRepository.findByCode(Score.FC.getName()) != null){
               comPrefScore += refPcsScoreRepository.findByCode(Score.FC.getName()).getScore();
           }
            //Privacy ComPref check
            comPrefList = comPrefs
                    .stream()
                    .filter(cm -> Variables.P.getName().equals(cm.getDomain()))
                    .toList();

            if(! comPrefList.isEmpty() && refPcsScoreRepository.findByCode(Score.PC.getName()) != null){
                comPrefScore += refPcsScoreRepository.findByCode(Score.PC.getName()).getScore();
            }
            //AF Sales ComPref check
             comPrefList = comPrefs
                     .stream()
                     .filter(cm -> Variables.S.getName().equals(cm.getDomain()))
                     .filter(cm -> Variables.AF.getName().equals(cm.getComType()))
                     .filter(cm -> Variables.Y.getName().equals(cm.getSubscribe()))
                     .toList();
            if(! comPrefList.isEmpty() && refPcsScoreRepository.findByCode(Score.AS.getName()) != null){
                comPrefScore += refPcsScoreRepository.findByCode(Score.AS.getName()).getScore();
            }
            //KL Sales ComPref check
            comPrefList = comPrefs
                    .stream()
                    .filter(cm -> Variables.S.getName().equals(cm.getDomain()))
                    .filter(cm -> Variables.KL.getName().equals(cm.getComType()))
                    .filter(cm -> Variables.Y.getName().equals(cm.getSubscribe()))
                    .toList();
            if(! comPrefList.isEmpty() && refPcsScoreRepository.findByCode(Score.KS.getName()) != null){
                comPrefScore += refPcsScoreRepository.findByCode(Score.KS.getName()).getScore();
            }
        }
        return comPrefScore;
    }

    /**
     * Calculate the score of delegation fields  of a gin
     *
     * @param gin, gin duplicate
     *
     */
    public double calculateDelegationScore(String gin) {
        double delegationScore = 0;

        //CASE : GIN is Delegate
        List<DelegationData> delegateList = delegationDataRepository.findDelegatesByGin(gin);
        delegationScore = getDelegationScore(delegationScore, delegateList);
        //CASE : GIN is Delegator
        List<DelegationData> delegatorList = delegationDataRepository.findDelegatorsByGin(gin);
        delegationScore = getDelegationScore(delegationScore, delegatorList);
        return delegationScore;
    }

    private double getDelegationScore(double delegationScore, List<DelegationData> delegateList) {
        if(! delegateList.isEmpty()) {
            List<String> delegateTypes = delegateList
                    .stream()
                    .map(DelegationData::getType)
                    .toList();
            if (delegateTypes.contains(Variables.TM.getName()) && refPcsScoreRepository.findByCode(Score.DF.getName()) != null) {
                delegationScore += refPcsScoreRepository.findByCode(Score.DF.getName()).getScore();
            }
            if (delegateTypes.contains(Variables.UF.getName()) && refPcsScoreRepository.findByCode(Score.DU.getName()) != null) {
                delegationScore += refPcsScoreRepository.findByCode(Score.DU.getName()).getScore();
            }
        }
        return delegationScore;
    }

    /**
     * Calculate the score of roles fields  of a gin
     *
     * @param gin, gin duplicate
     *
     */
    public double calculateRoleScore(String gin) {
        double roleScore = 0;

        List<RoleContrats> roleContrats = roleContractRepository.findByGin(gin);
        if(! roleContrats.isEmpty()) {
            List<String> roleContractTypes = roleContrats
                    .stream()
                    .map(RoleContrats::getTypeContrat)
                    .toList();
            if (roleContractTypes.contains(Score.GP.getName()) && refPcsScoreRepository.findByCode(Score.GP.getName()) != null) {
                roleScore += refPcsScoreRepository.findByCode(Score.GP.getName()).getScore();
            }
            if (roleContractTypes.contains(Score.UC.getName()) && refPcsScoreRepository.findByCode(Score.UC.getName()) != null) {
                roleScore += refPcsScoreRepository.findByCode(Score.UC.getName()).getScore();
            }
        }
        return roleScore;
    }

}
