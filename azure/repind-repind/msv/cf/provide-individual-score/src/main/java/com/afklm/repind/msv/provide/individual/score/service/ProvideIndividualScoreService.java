package com.afklm.repind.msv.provide.individual.score.service;

import com.afklm.repind.common.entity.contact.PostalAddress;
import com.afklm.repind.common.entity.identifier.ExternalIdentifier;
import com.afklm.repind.common.entity.individual.DelegationData;
import com.afklm.repind.common.entity.individual.Individu;
import com.afklm.repind.common.entity.preferences.CommunicationPreferencesEntity;
import com.afklm.repind.common.entity.preferences.PreferenceEntity;
import com.afklm.repind.common.entity.role.RoleContract;
import com.afklm.repind.common.repository.contact.EmailRepository;
import com.afklm.repind.common.repository.contact.PostalAddressRepository;
import com.afklm.repind.common.repository.contact.TelecomsRepository;
import com.afklm.repind.common.repository.identifier.AccountIdentifierRepository;
import com.afklm.repind.common.repository.identifier.ExternalIdentifierRepository;
import com.afklm.repind.common.repository.individual.DelegationDataRepository;
import com.afklm.repind.common.repository.individual.IndividuRepository;
import com.afklm.repind.common.repository.individual.ProfilsRepository;
import com.afklm.repind.common.repository.preferences.CommunicationPreferencesRepository;
import com.afklm.repind.common.repository.preferences.PreferenceRepository;
import com.afklm.repind.common.repository.profileComplianceScore.RefPcsFactorRepository;
import com.afklm.repind.common.repository.profileComplianceScore.RefPcsScoreRepository;
import com.afklm.repind.common.repository.role.RoleContractRepository;
import com.afklm.repind.msv.provide.individual.score.enumeration.Score;
import com.afklm.repind.msv.provide.individual.score.enumeration.TravelPreferenceType;
import com.afklm.repind.msv.provide.individual.score.enumeration.UltimatePreferenceType;
import com.afklm.repind.msv.provide.individual.score.enumeration.Variables;
import com.afklm.repind.msv.provide.individual.score.wrapper.WrapperProvideIndividualScoreResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Stream;

@Service
@Slf4j
@AllArgsConstructor
public class ProvideIndividualScoreService {

    private RefPcsScoreRepository refPcsScoreRepository;

    private RefPcsFactorRepository refPcsFactorRepository;

    private RoleContractRepository roleContractRepository;

    private IndividuRepository individuRepository;

    private ProfilsRepository profilsRepository;

    private EmailRepository emailRepository;

    private AccountIdentifierRepository accountIdentifierRepository;

    private TelecomsRepository telecomsRepository;
    
    private PostalAddressRepository postalAddressRepository;

    private ExternalIdentifierRepository externalIdentifierRepository;

    private PreferenceRepository preferenceRepository;

    private CommunicationPreferencesRepository communicationPreferencesRepository;

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
    @Transactional(readOnly = true)
    public List<WrapperProvideIndividualScoreResponse> calculatePcsScore(List<String> gins) {
        List<WrapperProvideIndividualScoreResponse> listResponse = new ArrayList<>();

        double score = 0;

        gins = gins.stream()
                .filter(gin -> gin.length() > 0)
                .toList();


        for(String gin : gins){

            WrapperProvideIndividualScoreResponse wrapperProvideIndividualScoreResponse = new WrapperProvideIndividualScoreResponse();

            if(individuRepository.getIndividuBySgin(gin) != null){
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
    @Transactional(readOnly = true)
    public double calculateContractScore(String gin){
        long contractScore = 0;
        List<Integer> contractGinScores = new ArrayList<>();

        if(! roleContractRepository.findByGin(gin).isEmpty()){
            List<String> roleContractTypes = roleContractRepository.findByGin(gin).stream()
                    .map(RoleContract::getTypeContrat)
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
    @Transactional(readOnly = true)
    public boolean isSubscriberContract(List<String> contractTypeList){
        return (contractTypeList.contains(Variables.RC.getName()) || contractTypeList.contains(Variables.DP.getName()) || contractTypeList.contains(Variables.RP.getName()));
    }

    /**
     * Checks if individual have other contract than FB, MA, S or subscriber
     *
     * @param contractTypeList, List of contracts of an individual
     *
     */
    @Transactional(readOnly = true)
    public boolean isOtherContract(List<String> contractTypeList){
        return (! (contractTypeList.contains(Variables.RC.getName()) || contractTypeList.contains(Variables.DP.getName()) || contractTypeList.contains(Variables.RP.getName()) || contractTypeList.contains(Score.FP.getName())|| contractTypeList.contains(Score.MA.getName()) || contractTypeList.contains(Score.S.getName())));
    }


    /**
     * Provide the non contract score of a gin
     *
     * @param gin, gin duplicate
     *
     */
    @Transactional(readOnly = true)
    public double calculateNonContractScore(String gin){

        double nonContractScore = calculateIdentityScore(gin)
                +calculateProfileScore(gin)
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
    @Transactional(readOnly = true)
    public double calculateIdentityScore(String gin){
        double identityScore = 0;

        if(individuRepository.getIndividuBySgin(gin) != null ) {
            Individu individu = individuRepository.getIndividuBySgin(gin);
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
    @Transactional
    public long calculateProfileScore(String gin){
        long profileScore = 0;
        if(profilsRepository.getProfilsEntityByGin(gin) != null) {
            String codeLangue = profilsRepository.getProfilsEntityByGin(gin).getCodeLangue();
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
    @Transactional(readOnly = true)
    public double calculateContactScore(String gin) {
        double contactScore = 0;
        if (accountIdentifierRepository.findBySgin(gin) != null) {
            String emailIdentifier = accountIdentifierRepository.findBySgin(gin).getEmailIdentifier();
            if (StringUtils.isNotBlank(emailIdentifier) && refPcsScoreRepository.findByCode(Score.EI.getName()) != null) {
                contactScore += refPcsScoreRepository.findByCode(Score.EI.getName()).getScore();
            }
        }
        if (!emailRepository.findByGinAndCodeMediumAndStatutMedium(gin, CODE_DIRECT, STATUT_VALID).isEmpty() && refPcsScoreRepository.findByCode(Score.ED.getName()) != null) {
            contactScore += refPcsScoreRepository.findByCode(Score.ED.getName()).getScore();
        }
        if (!emailRepository.findByGinAndCodeMediumAndStatutMedium(gin, CODE_PRO, STATUT_VALID).isEmpty() && refPcsScoreRepository.findByCode(Score.EP.getName()) != null) {
            contactScore += refPcsScoreRepository.findByCode(Score.EP.getName()).getScore();
        }
        if (!telecomsRepository.findByGinAndCodeMediumAndStatutMediumAndTerminal(gin, CODE_PRO, STATUT_VALID, MOBILE).isEmpty() && refPcsScoreRepository.findByCode(Score.MP.getName()) != null) {
            contactScore += refPcsScoreRepository.findByCode(Score.MP.getName()).getScore();
        }
        if (!telecomsRepository.findByGinAndCodeMediumAndStatutMediumAndTerminal(gin, CODE_DIRECT, STATUT_VALID, MOBILE).isEmpty() && refPcsScoreRepository.findByCode(Score.MD.getName()) != null) {
            contactScore += refPcsScoreRepository.findByCode(Score.MD.getName()).getScore();
        }
        List<PostalAddress> postalAddressList = postalAddressRepository.findByGinAndStatutMediumIn(gin, Arrays.asList(STATUT_VALID));
        if (!postalAddressList.isEmpty()) {
            List<String> streetList = postalAddressList.stream()
                    .map(PostalAddress::getNoEtRue)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
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
    @Transactional(readOnly = true)
    public double calculateExternalIdScore(String gin){
        double externalIdScore = 0;

        if(! externalIdentifierRepository.findByGin(gin).isEmpty()){
            List<ExternalIdentifier> externalIdentifierList = externalIdentifierRepository.findByGin(gin);
            List<String> pnmIdList = externalIdentifierList.stream()
                    .map(ExternalIdentifier::getType)
                    .filter(t -> t.equals(Variables.PNM.getName()))
                    .toList());
            if (!pnmIdList.isEmpty() && refPcsScoreRepository.findByCode(Score.PN.getName()) != null) {
                externalIdScore += refPcsScoreRepository.findByCode(Score.PN.getName()).getScore();
            }
            List<String> socialIdList = externalIdentifierList.stream()
                    .map(ExternalIdentifier::getType)
                    .filter(t -> ! t.equals(Variables.PNM.getName()))
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
    @Transactional(readOnly = true)
    public double calculatePreferencesScore(String gin){
        double preferenceScore = 0;

        if(! preferenceRepository.getPreferenceEntitiesByGin(gin).isEmpty()){
            List<String> preferenceEntityTypeList = preferenceRepository.getPreferenceEntitiesByGin(gin)
                    .stream()
                    .map(PreferenceEntity::getType)
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
    @Transactional(readOnly = true)
    public double calculateComPrefScore(String gin) {
        double comPrefScore = 0;

        if(! communicationPreferencesRepository.getCommunicationPreferencesByGin(gin).isEmpty()){
            //FB ComPref check
            List<CommunicationPreferencesEntity> comPrefList = communicationPreferencesRepository.getCommunicationPreferencesByGin(gin)
                    .stream()
                    .filter(cm -> cm.getDomain().equals(Variables.F.getName()))
                    .filter(cm -> cm.getSubscribe().equals(Variables.Y.getName()))
                    .toList();

           if(! comPrefList.isEmpty() && refPcsScoreRepository.findByCode(Score.FC.getName()) != null){
               comPrefScore += refPcsScoreRepository.findByCode(Score.FC.getName()).getScore();
           }
            //Privacy ComPref check
            comPrefList = communicationPreferencesRepository.getCommunicationPreferencesByGin(gin)
                    .stream()
                    .filter(cm -> cm.getDomain().equals(Variables.P.getName()))
                    .toList();

            if(! comPrefList.isEmpty() && refPcsScoreRepository.findByCode(Score.PC.getName()) != null){
                comPrefScore += refPcsScoreRepository.findByCode(Score.PC.getName()).getScore();
            }
            //AF Sales ComPref check
             comPrefList = communicationPreferencesRepository.getCommunicationPreferencesByGin(gin)
                     .stream()
                     .filter(cm -> cm.getDomain().equals(Variables.S.getName()))
                     .filter(cm -> cm.getComType().equals(Variables.AF.getName()))
                     .filter(cm -> cm.getSubscribe().equals(Variables.Y.getName()))
                     .toList();
            if(! comPrefList.isEmpty() && refPcsScoreRepository.findByCode(Score.AS.getName()) != null){
                comPrefScore += refPcsScoreRepository.findByCode(Score.AS.getName()).getScore();
            }
            //KL Sales ComPref check
            comPrefList = communicationPreferencesRepository.getCommunicationPreferencesByGin(gin)
                    .stream()
                    .filter(cm -> cm.getDomain().equals(Variables.S.getName()))
                    .filter(cm -> cm.getComType().equals(Variables.KL.getName()))
                    .filter(cm -> cm.getSubscribe().equals(Variables.Y.getName()))
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
     */     @Transactional(readOnly = true)
    public double calculateDelegationScore(String gin) {
        double delegationScore = 0;

        //CASE : GIN is Delegate
        if(! delegationDataRepository.getDelegationDataEntitiesByGinDelegate(gin).isEmpty()) {
            List<String> delegateTypes = delegationDataRepository.getDelegationDataEntitiesByGinDelegate(gin)
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
        //CASE : GIN is Delegator
        if(! delegationDataRepository.getDelegationDataEntitiesByGinDelegator(gin).isEmpty()) {
            List<String> delegatorTypes = delegationDataRepository.getDelegationDataEntitiesByGinDelegator(gin)
                    .stream()
                    .map(DelegationData::getType)
                    .toList();
            if (delegatorTypes.contains(Variables.TM.getName()) && refPcsScoreRepository.findByCode(Score.DF.getName()) != null) {
                delegationScore += refPcsScoreRepository.findByCode(Score.DF.getName()).getScore();
            }
            if (delegatorTypes.contains(Variables.UF.getName()) && refPcsScoreRepository.findByCode(Score.DU.getName()) != null) {
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
     */     @Transactional(readOnly = true)
    public double calculateRoleScore(String gin) {
        double roleScore = 0;

        if(! roleContractRepository.findByGin(gin).isEmpty()) {
            List<String> roleContractTypes = roleContractRepository.findByGin(gin)
                    .stream()
                    .map(RoleContract::getTypeContrat)
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
