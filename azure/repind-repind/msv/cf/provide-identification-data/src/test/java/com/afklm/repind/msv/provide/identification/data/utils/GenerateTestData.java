package com.afklm.repind.msv.provide.identification.data.utils;

import com.afklm.repind.common.entity.contact.Telecoms;
import com.afklm.repind.common.entity.identifier.AccountIdentifier;
import com.afklm.repind.common.entity.individual.ComplementaryInformationEntity;
import com.afklm.repind.common.entity.individual.DelegationData;
import com.afklm.repind.common.entity.individual.Individu;
import com.afklm.repind.common.entity.individual.ProfilsEntity;
import com.afklm.repind.common.entity.individual.UsageClientEntity;
import com.afklm.soa.stubs.r000378.v1.model.ComplementaryInformation;
import com.afklm.soa.stubs.r000378.v1.model.ComplementaryInformationData;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GenerateTestData {

    private static Individu delegateA = new Individu();
    private static Individu delegatorA = new Individu();
    private static Individu delegateB = new Individu();
    private static Individu delegatorB = new Individu();

    public static AccountIdentifier buildAccountIdentifier() {
        Date date = Date.from(LocalDate.of(2013, 12, 6).atStartOfDay(ZoneId.systemDefault()).toInstant());
        AccountIdentifier accountIdentifier = new AccountIdentifier();

        accountIdentifier.setAccountId("786442AH");
        accountIdentifier.setFbIdentifier("002101358216");
        accountIdentifier.setEmailIdentifier("jsmith@fwellc.com");
        accountIdentifier.setId(1);
        accountIdentifier.setSgin("400410574103");
        accountIdentifier.setLastSocialNetworkId(null);
        accountIdentifier.setSocialNetworkId(null);
        accountIdentifier.setId(1);
        accountIdentifier.setDateCreation(date);
        accountIdentifier.setDateModification(date);
        accountIdentifier.setSignatureCreation("CREATE ACCOUNT");
        accountIdentifier.setSiteCreation("S09372");
        accountIdentifier.setSignatureModification("AUTHENT MYACCNT");
        accountIdentifier.setSiteModification("SIC WS");

        return accountIdentifier;
    }

    public static AccountIdentifier buildAccountIdentifier2() {
        AccountIdentifier res = buildAccountIdentifier();
        res.setSgin("400210364791");
        return res;
    }

    public static AccountIdentifier buildAccountIdentifier3() {
        AccountIdentifier res = buildAccountIdentifier();
        res.setSgin("400210364756");
        return res;
    }

    public static AccountIdentifier buildAccountIdentifier4() {
        AccountIdentifier res = buildAccountIdentifier();
        res.setSgin("400410598103");
        return res;
    }

    public static AccountIdentifier buildAccountIdentifier5() {
        AccountIdentifier res = buildAccountIdentifier();
        res.setSgin("400410574833");
        return res;
    }

    public static List<DelegationData> buildDelegatesList() {
        List<DelegationData> delegateDataEntityList = new ArrayList<>();
        delegateA.setGin("400210364791");
        delegatorA.setGin("400410574103");

        DelegationData delegate1 = new DelegationData();

        delegate1.setDelegationDataId("111905");
        delegate1.setStatus("A");
        delegate1.setDelegate(delegateA);
        delegate1.setDelegator(delegatorA);
        delegate1.setType("UM");

        delegate1.setDateCreation(Date.from(LocalDate.of(2022, 9, 26).atStartOfDay(ZoneId.systemDefault()).toInstant()));
        delegate1.setSiteCreation("AMS");
        delegate1.setSignatureCreation("CustomerAPI");

        delegate1.setDateModification(Date.from(LocalDate.of(2022, 9, 26).atStartOfDay(ZoneId.systemDefault()).toInstant()));
        delegate1.setSiteModification("AMS");
        delegate1.setSignatureModification("CustomerAPI");

        delegateB.setGin("400210364756");
        delegatorB.setGin("400410574103");
        DelegationData delegate2 = new DelegationData();

        delegate2.setDelegationDataId("111908");
        delegate2.setStatus("A");
        delegate2.setDelegate(delegateB);
        delegate2.setDelegator(delegatorB);
        delegate2.setType("UM");

        delegate2.setDateCreation(Date.from(LocalDate.of(2022, 9, 26).atStartOfDay(ZoneId.systemDefault()).toInstant()));
        delegate2.setSiteCreation("AMS");
        delegate2.setSignatureCreation("CustomerAPI");

        delegate2.setDateModification(Date.from(LocalDate.of(2022, 9, 26).atStartOfDay(ZoneId.systemDefault()).toInstant()));
        delegate2.setSiteModification("AMS");
        delegate2.setSignatureModification("CustomerAPI");

        delegateDataEntityList.add(delegate1);
        delegateDataEntityList.add(delegate2);
        return delegateDataEntityList;
    }

    public static List<DelegationData> buildDelegatorsList() {
        List<DelegationData> delegatorDataEntityList = new ArrayList<>();

        delegateA.setGin("400410574103");
        delegatorA.setGin("400410598103");
        DelegationData delegator1 = new DelegationData();

        delegator1.setDelegationDataId("111909");
        delegator1.setStatus("A");
        delegator1.setDelegate(delegateA);
        delegator1.setDelegator(delegatorB);
        delegator1.setType("UM");

        delegator1.setDateCreation(Date.from(LocalDate.of(2022, 9, 26).atStartOfDay(ZoneId.systemDefault()).toInstant()));
        delegator1.setSiteCreation("AMS");
        delegator1.setSignatureCreation("CustomerAPI");

        delegator1.setDateModification(Date.from(LocalDate.of(2022, 9, 26).atStartOfDay(ZoneId.systemDefault()).toInstant()));
        delegator1.setSiteModification("AMS");
        delegator1.setSignatureModification("CustomerAPI");

        delegateB.setGin("400410574103");
        delegatorB.setGin("400410574833");
        DelegationData delegator2 = new DelegationData();

        delegator2.setDelegationDataId("111910");
        delegator2.setStatus("A");
        delegator2.setDelegate(delegateB);
        delegator2.setDelegator(delegatorB);
        delegator2.setType("UM");

        delegator2.setDateCreation(Date.from(LocalDate.of(2022, 9, 26).atStartOfDay(ZoneId.systemDefault()).toInstant()));
        delegator2.setSiteCreation("AMS");
        delegator2.setSignatureCreation("CustomerAPI");

        delegator2.setDateModification(Date.from(LocalDate.of(2022, 9, 26).atStartOfDay(ZoneId.systemDefault()).toInstant()));
        delegator2.setSiteModification("AMS");
        delegator2.setSignatureModification("CustomerAPI");

        delegatorDataEntityList.add(delegator1);
        delegatorDataEntityList.add(delegator2);
        return delegatorDataEntityList;
    }

    public static DelegationData buildDelegationDataEntity() {

        return buildDelegatesList().get(0);
    }

    public static List<ComplementaryInformationEntity> buildComplementaryInformationList() {
        List<ComplementaryInformationEntity> complementaryInformationEntityList = new ArrayList<>();

        ComplementaryInformationEntity complementaryInformationEntity1 = new ComplementaryInformationEntity();
        complementaryInformationEntity1.setType("TEL");
        complementaryInformationEntity1.setKey("phoneNumber");
        complementaryInformationEntity1.setValue("874586512");
        complementaryInformationEntity1.setDelegationDataId("111905");

        ComplementaryInformationEntity complementaryInformationEntity2 = new ComplementaryInformationEntity();
        complementaryInformationEntity2.setType("TEL");
        complementaryInformationEntity2.setKey("terminalType");
        complementaryInformationEntity2.setValue("M");
        complementaryInformationEntity2.setDelegationDataId("111905");

        ComplementaryInformationEntity complementaryInformationEntity3 = new ComplementaryInformationEntity();
        complementaryInformationEntity3.setType("TEL");
        complementaryInformationEntity3.setKey("countryCode");
        complementaryInformationEntity3.setValue("33");
        complementaryInformationEntity3.setDelegationDataId("111905");

        complementaryInformationEntityList.add(complementaryInformationEntity1);
        complementaryInformationEntityList.add(complementaryInformationEntity2);
        complementaryInformationEntityList.add(complementaryInformationEntity3);
        return complementaryInformationEntityList;
    }

    public static List<ComplementaryInformationEntity> buildComplementaryInformationEntityListForH2Db() {
        List<ComplementaryInformationEntity> complementaryInformationEntityList = new ArrayList<>();

        ComplementaryInformationEntity complementaryInformationEntity1 = new ComplementaryInformationEntity();
        complementaryInformationEntity1.setDelegationDataInfoId("10001");
        complementaryInformationEntity1.setType("TEL");
        complementaryInformationEntity1.setKey("phoneNumber");
        complementaryInformationEntity1.setValue("874586512");
        complementaryInformationEntity1.setDelegationDataId("111905");

        ComplementaryInformationEntity complementaryInformationEntity2 = new ComplementaryInformationEntity();
        complementaryInformationEntity2.setDelegationDataInfoId("10002");
        complementaryInformationEntity2.setType("TEL");
        complementaryInformationEntity2.setKey("terminalType");
        complementaryInformationEntity2.setValue("M");
        complementaryInformationEntity2.setDelegationDataId("111905");

        ComplementaryInformationEntity complementaryInformationEntity3 = new ComplementaryInformationEntity();
        complementaryInformationEntity3.setDelegationDataInfoId("10003");
        complementaryInformationEntity3.setType("TEL");
        complementaryInformationEntity3.setKey("countryCode");
        complementaryInformationEntity3.setValue("33");
        complementaryInformationEntity3.setDelegationDataId("111905");

        ComplementaryInformationEntity complementaryInformationEntity4 = new ComplementaryInformationEntity();
        complementaryInformationEntity4.setDelegationDataInfoId("10004");
        complementaryInformationEntity4.setType("TEL");
        complementaryInformationEntity4.setKey("phoneNumber");
        complementaryInformationEntity4.setValue("874586512");
        complementaryInformationEntity4.setDelegationDataId("111908");

        ComplementaryInformationEntity complementaryInformationEntity5 = new ComplementaryInformationEntity();
        complementaryInformationEntity5.setDelegationDataInfoId("10005");
        complementaryInformationEntity5.setType("TEL");
        complementaryInformationEntity5.setKey("terminalType");
        complementaryInformationEntity5.setValue("M");
        complementaryInformationEntity5.setDelegationDataId("111908");

        ComplementaryInformationEntity complementaryInformationEntity6 = new ComplementaryInformationEntity();
        complementaryInformationEntity6.setDelegationDataInfoId("10006");
        complementaryInformationEntity6.setType("TEL");
        complementaryInformationEntity6.setKey("countryCode");
        complementaryInformationEntity6.setValue("33");
        complementaryInformationEntity6.setDelegationDataId("111908");

        ComplementaryInformationEntity complementaryInformationEntity7 = new ComplementaryInformationEntity();
        complementaryInformationEntity7.setDelegationDataInfoId("10007");
        complementaryInformationEntity7.setType("TEL");
        complementaryInformationEntity7.setKey("phoneNumber");
        complementaryInformationEntity7.setValue("874586512");
        complementaryInformationEntity7.setDelegationDataId("111909");

        ComplementaryInformationEntity complementaryInformationEntity8 = new ComplementaryInformationEntity();
        complementaryInformationEntity8.setDelegationDataInfoId("10008");
        complementaryInformationEntity8.setType("TEL");
        complementaryInformationEntity8.setKey("terminalType");
        complementaryInformationEntity8.setValue("M");
        complementaryInformationEntity8.setDelegationDataId("111909");

        ComplementaryInformationEntity complementaryInformationEntity9 = new ComplementaryInformationEntity();
        complementaryInformationEntity9.setDelegationDataInfoId("10009");
        complementaryInformationEntity9.setType("TEL");
        complementaryInformationEntity9.setKey("countryCode");
        complementaryInformationEntity9.setValue("33");
        complementaryInformationEntity9.setDelegationDataId("111909");

        ComplementaryInformationEntity complementaryInformationEntity10 = new ComplementaryInformationEntity();
        complementaryInformationEntity10.setDelegationDataInfoId("10010");
        complementaryInformationEntity10.setType("TEL");
        complementaryInformationEntity10.setKey("phoneNumber");
        complementaryInformationEntity10.setValue("874586512");
        complementaryInformationEntity10.setDelegationDataId("111910");

        ComplementaryInformationEntity complementaryInformationEntity11 = new ComplementaryInformationEntity();
        complementaryInformationEntity11.setDelegationDataInfoId("10011");
        complementaryInformationEntity11.setType("TEL");
        complementaryInformationEntity11.setKey("terminalType");
        complementaryInformationEntity11.setValue("M");
        complementaryInformationEntity11.setDelegationDataId("111910");

        ComplementaryInformationEntity complementaryInformationEntity12 = new ComplementaryInformationEntity();
        complementaryInformationEntity12.setDelegationDataInfoId("10012");
        complementaryInformationEntity12.setType("TEL");
        complementaryInformationEntity12.setKey("countryCode");
        complementaryInformationEntity12.setValue("33");
        complementaryInformationEntity12.setDelegationDataId("111910");

        complementaryInformationEntityList.add(complementaryInformationEntity1);
        complementaryInformationEntityList.add(complementaryInformationEntity2);
        complementaryInformationEntityList.add(complementaryInformationEntity3);

        complementaryInformationEntityList.add(complementaryInformationEntity4);
        complementaryInformationEntityList.add(complementaryInformationEntity5);
        complementaryInformationEntityList.add(complementaryInformationEntity6);

        complementaryInformationEntityList.add(complementaryInformationEntity7);
        complementaryInformationEntityList.add(complementaryInformationEntity8);
        complementaryInformationEntityList.add(complementaryInformationEntity9);

        complementaryInformationEntityList.add(complementaryInformationEntity10);
        complementaryInformationEntityList.add(complementaryInformationEntity11);
        complementaryInformationEntityList.add(complementaryInformationEntity12);

        return complementaryInformationEntityList;
    }

    public static List<ComplementaryInformation> buildTransformedComplementaryInformationList() {
        List<ComplementaryInformation> complementaryInformationList = new ArrayList<>();
        com.afklm.soa.stubs.r000378.v1.model.ComplementaryInformation complementaryInformation = new com.afklm.soa.stubs.r000378.v1.model.ComplementaryInformation();
        complementaryInformation.setType("TEL");

        ComplementaryInformationData complementaryInformationData1 = new ComplementaryInformationData();
        complementaryInformationData1.setValue("874586512");
        complementaryInformationData1.setKey("phoneNumber");

        ComplementaryInformationData complementaryInformationData2 = new ComplementaryInformationData();
        complementaryInformationData2.setValue("M");
        complementaryInformationData2.setKey("terminalType");

        ComplementaryInformationData complementaryInformationData3 = new ComplementaryInformationData();
        complementaryInformationData3.setValue("33");
        complementaryInformationData3.setKey("countryCode");

        List<ComplementaryInformationData> complementaryInformationDataList = new ArrayList<>();
        complementaryInformationDataList.add(complementaryInformationData1);
        complementaryInformationDataList.add(complementaryInformationData2);
        complementaryInformationDataList.add(complementaryInformationData3);

        complementaryInformation.setComplementaryInformationDatas(complementaryInformationDataList);
        complementaryInformationList.add(complementaryInformation);

        return complementaryInformationList;
    }

    public static List<Telecoms> TelecomsList() {
        List<Telecoms> telecomList = new ArrayList<>();
        Individu individu = new Individu();
        individu.setGin("400410574103");

        Telecoms telecoms = new Telecoms();
        telecoms.setTerminal("M");
        telecoms.setNormInterCountryCode("1");
        telecoms.setVersion(2);
        telecoms.setCodeMedium("D");
        telecoms.setNumero("7132139009");
        telecoms.setStatutMedium("V");
        telecoms.setAin("1");
        telecoms.setIndividu(individu);

        telecomList.add(telecoms);
        return telecomList;
    }

    public static String buildLanguageCode() {
        return "EN";
    }

    public static List<UsageClientEntity> buildUsageClientList() {
        List<UsageClientEntity> res = new ArrayList<>();

        UsageClientEntity tmp = new UsageClientEntity();
        tmp.setSrin("65267858");
        tmp.setCode("ISI");
        tmp.setGin("400410574103");
        tmp.setAuthorizedModification("O");
        tmp.setDateModification(Date.from(LocalDate.of(2013, 12, 6).atStartOfDay(ZoneId.systemDefault()).toInstant()));

        res.add(tmp);
        return res;
    }

    public static Individu buildIndividual() {
        Date date = Date.from(LocalDate.of(2013, 12, 6).atStartOfDay(ZoneId.systemDefault()).toInstant());
        Individu res = new Individu();

        res.setDateNaissance(Date.from(LocalDate.of(1960, 2, 1).atStartOfDay(ZoneId.systemDefault()).toInstant()));
        res.setCivilite("MR");
        res.setNonFusionnable("N");
        res.setTierUtiliseCommePiege("N");
        res.setPrenomTypo1("JOHN");
        res.setPrenom("JOHN");
        res.setNomTypo1("SMITH");
        res.setNom("SMITH");
        res.setAliasPrenom("JOHN");
        res.setSecondPrenom("MICHAEL");
        res.setAliasNom1("SMITH");
        res.setSexe("M");
        res.setGin("400410574103");
        res.setType("I");
        res.setStatutIndividu("V");
        res.setCodeTitre("CJU");
        res.setVersion(3);
        res.setIdentifiantPersonnel("1020014902");
        res.setDateCreation(date);
        res.setDateModification(date);
        res.setSignatureCreation("WEB");
        res.setSignatureModification("WEB");
        res.setSiteCreation("ISI");
        res.setSiteModification("ISI");

        return res;
    }

    public static Individu buildIndividual2() {
        Individu res = buildIndividual();
        res.setGin("400210364791");
        return res;
    }

    public static Individu buildIndividual3() {
        Individu res = buildIndividual();
        res.setGin("400210364756");
        return res;
    }

    public static Individu buildIndividual4() {
        Individu res = buildIndividual();
        res.setGin("400410598103");
        return res;
    }

    public static Individu buildIndividual5() {
        Individu res = buildIndividual();
        res.setGin("400410574833");
        return res;
    }

    public static ProfilsEntity buildProfilEntityForH2DB(){
        ProfilsEntity profilsEntity = new ProfilsEntity();
        profilsEntity.setGin("400410574103");
        profilsEntity.setCodeLangue(buildLanguageCode());
        return profilsEntity;
    }


}
