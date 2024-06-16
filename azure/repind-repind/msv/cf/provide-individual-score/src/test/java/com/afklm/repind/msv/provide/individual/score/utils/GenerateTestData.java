package com.afklm.repind.msv.provide.individual.score.utils;

import com.afklm.repind.common.entity.identifier.AccountIdentifier;
import com.afklm.repind.common.entity.identifier.ExternalIdentifier;
import com.afklm.repind.common.entity.individual.DelegationData;
import com.afklm.repind.common.entity.individual.Individu;
import com.afklm.repind.common.entity.individual.ProfilsEntity;
import com.afklm.repind.common.entity.preferences.CommunicationPreferencesEntity;
import com.afklm.repind.common.entity.preferences.PreferenceEntity;
import com.afklm.repind.common.entity.profile.compliance.score.RefPcsFactor;
import com.afklm.repind.common.entity.profile.compliance.score.RefPcsScore;
import com.afklm.repind.common.entity.role.RoleContract;
import com.afklm.repind.msv.provide.individual.score.wrapper.WrapperProvideIndividualScoreResponse;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GenerateTestData {

    public static RefPcsScore buildRefPcsScore() {
        RefPcsScore refPcsScore = new RefPcsScore();
        refPcsScore.setScore(1);
        return refPcsScore;
    }

    public static RefPcsFactor buildRefPcsFactor() {
        RefPcsFactor refPcsFactor = new RefPcsFactor();
        refPcsFactor.setFactor(100);
        return refPcsFactor;
    }

    public static Individu buildIndividual() {
        Individu indiv = new Individu();
        indiv.setGin("123456789012");
        indiv.setCivilite("Mr");
        indiv.setNom("Doe");
        indiv.setPrenom("John");
        indiv.setDateNaissance(Date.from(LocalDate.of(1990, 5, 15).atStartOfDay(ZoneId.systemDefault()).toInstant()));
        indiv.setSexe("");
        return indiv;
    }

    public static ProfilsEntity buildProfil() {
        ProfilsEntity profil = new ProfilsEntity();
        profil.setCodeLangue("FR");
        return profil;
    }

    public static List<RoleContract> buildRoleContractList() {
        List<RoleContract> listRole = new ArrayList<>();
        RoleContract role1 = new RoleContract();
        role1.setTypeContrat("FP");
        RoleContract role2 = new RoleContract();
        role2.setTypeContrat("MA");
        RoleContract role3 = new RoleContract();
        role3.setTypeContrat("GP");
        listRole.add(role1);
        listRole.add(role2);
        listRole.add(role3);
        return listRole;
    }

    public static AccountIdentifier buildAccountIdentifier() {
        AccountIdentifier accountIdentifier = new AccountIdentifier();
        accountIdentifier.setEmailIdentifier("test@test.com");
        return accountIdentifier;
    }

    public static List<ExternalIdentifier> buildExternalIdList() {
        List<ExternalIdentifier> listExtId = new ArrayList<>();
        ExternalIdentifier extId = new ExternalIdentifier();
        extId.setIdentifier("0123012395000");
        extId.setType("FACEBOOK_ID");
        listExtId.add(extId);
        return listExtId;
    }

    public static List<PreferenceEntity> buildPreferenceList() {
        List<PreferenceEntity> listPreferences = new ArrayList<>();
        PreferenceEntity pref1 = new PreferenceEntity();
        pref1.setType("ECC");
        listPreferences.add(pref1);
        PreferenceEntity pref2 = new PreferenceEntity();
        pref2.setType("TPC");
        listPreferences.add(pref2);
        return listPreferences;
    }

    public static List<CommunicationPreferencesEntity> buildComPrefList() {
        List<CommunicationPreferencesEntity> listComPref = new ArrayList<>();
        CommunicationPreferencesEntity comPref1 = new CommunicationPreferencesEntity();
        comPref1.setDomain("F");
        comPref1.setSubscribe("Y");
        listComPref.add(comPref1);
        CommunicationPreferencesEntity comPref2 = new CommunicationPreferencesEntity();
        comPref2.setDomain("S");
        comPref2.setComType("AF");
        comPref2.setSubscribe("Y");
        listComPref.add(comPref2);
        return listComPref;
    }

    public static List<DelegationData> buildDelegationList() {
        List<DelegationData> listDelegation = new ArrayList<>();
        DelegationData comPref1 = new DelegationData();
        comPref1.setType("TM");
        listDelegation.add(comPref1);
        DelegationData comPref2 = new DelegationData();
        comPref2.setType("UM");
        listDelegation.add(comPref2);
        return listDelegation;
    }

    public static List<WrapperProvideIndividualScoreResponse> buildResponse() {
        List<WrapperProvideIndividualScoreResponse> wrapperReponseList = new ArrayList<>();
        WrapperProvideIndividualScoreResponse wrapperReponse = new WrapperProvideIndividualScoreResponse();
        wrapperReponse.setGin("0123012395000");
        wrapperReponse.setScore(20);
        wrapperReponseList.add(wrapperReponse);
        return wrapperReponseList;
    }

}
