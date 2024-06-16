package com.airfrance.batch.common.service.pcs;


import com.airfrance.batch.common.wrapper.WrapperProvideIndividualScoreResponse;
import com.airfrance.repind.entity.delegation.DelegationData;
import com.airfrance.repind.entity.external.ExternalIdentifier;
import com.airfrance.repind.entity.individu.AccountData;
import com.airfrance.repind.entity.individu.CommunicationPreferences;
import com.airfrance.repind.entity.individu.Individu;
import com.airfrance.repind.entity.preference.Preference;
import com.airfrance.repind.entity.profil.Profils;
import com.airfrance.repind.entity.reference.RefPcsFactor;
import com.airfrance.repind.entity.reference.RefPcsScore;
import com.airfrance.repind.entity.role.RoleContrats;

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
        indiv.setSgin("123456789012");
        indiv.setCivilite("Mr");
        indiv.setNom("Doe");
        indiv.setPrenom("John");
        indiv.setDateNaissance(Date.from(LocalDate.of(1990, 5, 15).atStartOfDay(ZoneId.systemDefault()).toInstant()));
        indiv.setSexe("");
        return indiv;
    }

    public static Profils buildProfil() {
        Profils profil = new Profils();
        profil.setScode_langue("FR");
        return profil;
    }

    public static List<RoleContrats> buildRoleContractList() {
        RoleContrats role1 = new RoleContrats();
        role1.setTypeContrat("FP");
        RoleContrats role2 = new RoleContrats();
        role2.setTypeContrat("MA");
        RoleContrats role3 = new RoleContrats();
        role3.setTypeContrat("GP");
        return List.of(role1, role2, role3);
    }

    public static AccountData buildAccountIdentifier() {
        AccountData accountIdentifier = new AccountData();
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

    public static List<Preference> buildPreferenceList() {
        Preference pref1 = new Preference();
        pref1.setType("ECC");
        Preference pref2 = new Preference();
        pref2.setType("TPC");
        return List.of(pref1, pref2);
    }

    public static List<CommunicationPreferences> buildComPrefList() {
        CommunicationPreferences comPref1 = new CommunicationPreferences();
        comPref1.setDomain("F");
        comPref1.setSubscribe("Y");
        CommunicationPreferences comPref2 = new CommunicationPreferences();
        comPref2.setDomain("S");
        comPref2.setComType("AF");
        comPref2.setSubscribe("Y");
        return List.of(comPref1, comPref2);
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
