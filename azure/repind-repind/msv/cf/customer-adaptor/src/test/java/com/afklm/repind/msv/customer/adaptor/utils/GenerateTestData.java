package com.afklm.repind.msv.customer.adaptor.utils;

import com.afklm.repind.common.entity.contact.EmailEntity;
import com.afklm.repind.common.entity.contact.PostalAddress;
import com.afklm.repind.common.entity.individual.Individu;
import com.afklm.repind.common.entity.role.RoleContract;
import com.afklm.repind.msv.customer.adaptor.model.DataModel;
import com.afklm.repind.msv.customer.adaptor.model.criteria.UpsertIndividusRequestCriteria;
import com.afklm.repind.msv.customer.adaptor.model.repind.Emails;
import com.afklm.repind.msv.customer.adaptor.model.repind.Individus;
import com.afklm.repind.msv.customer.adaptor.model.repind.IndividusDataModel;
import com.afklm.repind.msv.customer.adaptor.model.repind.TmpProfile;
import com.afklm.repind.msv.customer.adaptor.model.salesforce.Profiles;

import java.util.*;

public class GenerateTestData {
    private static final String SGIN = "400424668522";

    // Mocking kafka event messages
    public static String buildMockedConsumerRecordIndividus(){
        return """
                {
                    "AIN": "BHc=",
                    "ACTION_DATE": 1693320343000,
                    "GIN": "400424668522",
                    "ACTION_TYPE": "UPDATE",
                    "TABLE_NAME": "INDIVIDUS",
                    "CONTENT_DATA": {"identifier":"400424668522","civility":"M.","lastname":"TOMPONIONY","firstname":"LOHARANO","gender":"M","birthdate":"03-JAN-86","secondFirstname":"","status":"V","ginFusion":"","lastnameSC":"TOMPONIONY","firstnameSC":"LOHARANO","nationality":"","creationDate":"21-APR-14","creationSignature":"AF","creationSite":"GMMAL","modificationDate":"29-AUG-23","modificationSignature":"REPIND/IHM","modificationSite":"QVI","type":"I"}
                }
                """;
    }

    public static String buildMockedConsumerRecordIndividus(String gin, String contentData){
        return String.format("""
                {
                    "AIN": "BHc=",
                    "ACTION_DATE": 1693320343000,
                    "GIN": %s,
                    "ACTION_TYPE": "UPDATE",
                    "TABLE_NAME": "INDIVIDUS",
                    "CONTENT_DATA": %s
                }
                """, gin, contentData);
    }

    public static String buildMockedConsumerRecordEmail(){
        return """
                {
                    "AIN": "B2Y=",
                    "ACTION_DATE": 1693325093000,
                    "GIN": "400754213892",
                    "ACTION_TYPE": "INSERT",
                    "TABLE_NAME": "EMAILS",
                    "CONTENT_DATA": {"identifier":"98212637","gin":"400754213892","mediumCode":"D","mediumStatus":"V","email":"la@la.com","description":"","creationDate":"29-AUG-23","creationSignature":"icare","creationSite":"QVI","modificationDate":"29-AUG-23","modificationSignature":"icare","modificationSite":"QVI"}
                }
                """;
    }

    public static String buildMockedConsumerRecordEmail(String gin, String contentData){
        return String.format("""
                {
                    "AIN": "B2Y=",
                    "ACTION_DATE": 1693325093000,
                    "GIN": %s,
                    "ACTION_TYPE": "INSERT",
                    "TABLE_NAME": "EMAILS",
                    "CONTENT_DATA": %s
                }
                """, gin, contentData);
    }

    public static String buildMockedConsumerRecordContract(){
        return """
                {
                    "AIN": "B2Y=",
                    "ACTION_DATE": 1693325093000,
                    "GIN": "400754213892",
                    "ACTION_TYPE": "INSERT",
                    "TABLE_NAME": "ROLE_CONTRATS",
                    "CONTENT_DATA":  "{"identifier":"85845589","gin":"400754213892","number":"474559AR","type":"MA","subType":null,"company":"BB","status":"C","validityStartDate":"2023-09-08T14:47:11","validityEndDate":"2026-09-30T16:48:52","creationDate":"2023-09-08T14:47:11","creationSignature":"TEST","creationSite":"QVI","modificationDate":"2023-09-08T14:49:04","modificationSignature":"TEST","modificationSite":"QVI"}",
                    "CONTENT_JSON":  "{"identifier":"85845589","gin":"400754213892","number":"474559AR","type":"MA","subType":null,"company":"BB","status":"C","validityStartDate":"2023-09-08T14:47:11","validityEndDate":"2026-09-30T16:48:52","creationDate":"2023-09-08T14:47:11","creationSignature":"TEST","creationSite":"QVI","modificationDate":"2023-09-08T14:49:04","modificationSignature":"TEST","modificationSite":"QVI"}"
                }
                """;
    }
    public static String buildMockedConsumerRecordContract(String gin, String contentData){
        return String.format("""
                {
                    "AIN": "B2Y=",
                    "ACTION_DATE": 1693325093000,
                    "GIN": %s,
                    "ACTION_TYPE": "INSERT",
                    "TABLE_NAME": "ROLE_CONTRATS",
                    "CONTENT_DATA": %s
                }
                """, gin, contentData);
    }

    public static String buildMockedConsumerRecordPreference(){
        return """
                {
                    "AIN": "B2Y=",
                    "ACTION_DATE": 1693325093000,
                    "GIN": "400754213892",
                    "ACTION_TYPE": "INSERT",
                    "TABLE_NAME": "PREFERENCE",
                    "CONTENT_DATA":  "{"identifier":34814263,"gin":"400754213892","type":"TPC","linkIdentifier":null,"preferenceDatas":[{"id":136031175,"key":"arrivalAirport","value":"FCO"},{"id":136031182,"key":"seat","value":"A"},{"id":136031181,"key":"departureAirport","value":"CDG"},{"id":136031180,"key":"arrivalAirport","value":"SJU"},{"id":136031179,"key":"meal","value":"LSML"},{"id":136031178,"key":"seat","value":"A"},{"id":136031177,"key":"departureAirport","value":"CDG"},{"id":136031176,"key":"meal","value":"LSML"}]}"

                }
                """;
    }
    public static String buildMockedConsumerRecordPreference(String gin, String contentData){
        return String.format("""
                {
                    "AIN": "B2Y=",
                    "ACTION_DATE": 1693325093000,
                    "GIN": %s,
                    "ACTION_TYPE": "INSERT",
                    "TABLE_NAME": "PREFERENCE",
                    "CONTENT_DATA": %s
                }
                """, gin, contentData);
    }

    public static String buildMockedConsumerRecordMarketLanguage(){
        return """
                {
                    "AIN": "B2Y=",
                    "ACTION_DATE": 1693325093000,
                    "GIN": "400392779786",
                    "ACTION_TYPE": "UPDATE",
                    "TABLE_NAME": "MARKET_LANGUAGE",
                    "CONTENT_DATA": {"id":57,"comPrefId":10240351,"market":"NL","language":"NL","optin":"N","optinDate":"2013-06-25T09:19:02.508000"}
                }
                """;
    }
    public static String buildMockedConsumerRecordMarketLanguage(String gin, String contentData){
        return String.format("""
                {
                    "AIN": "B2Y=",
                    "ACTION_DATE": 1693325093000,
                    "GIN": %s,
                    "ACTION_TYPE": "UPDATE",
                    "TABLE_NAME": "MARKET_LANGUAGE",
                    "CONTENT_DATA": %s
                }
                """, gin, contentData);
    }

    public static String buildMockedConsumerRecordPostalAddress(){
        return """
                {
                    "AIN": "B2Y=",
                    "ACTION_DATE": 1693325093000,
                    "GIN": "400424668522",
                    "ACTION_TYPE": "INSERT",
                    "TABLE_NAME": "ADR_POST",
                    "CONTENT_DATA": {"identifier":"107182726","gin":"400424668522","mediumCode":"D","mediumStatus":"X","streetNumber":"141 WEST 54TH STREET","additionalInformation":null,"district":null,"city":"BERLIN","zipCode":null,"stateCode":null,"countryCode":"DE","creationDate":"2022-05-09T16:47:20","creationSignature":"CustomerAPI","creationSite":"AMS","modificationDate":"2023-09-08T12:15:09","modificationSignature":"REPIND/IHM","modificationSite":"QVI"}
                }
                """;
    }
    public static String buildMockedConsumerRecordPostalAddress(String gin, String contentData){
        return String.format("""
                {
                    "AIN": "B2Y=",
                    "ACTION_DATE": 1693325093000,
                    "GIN": %s,
                    "ACTION_TYPE": "INSERT",
                    "TABLE_NAME": "ADR_POST",
                    "CONTENT_DATA": %s
                }
                """, gin, contentData);
    }


    // Mocking kafka event messages but extract as model
    public static Individus buildMockedKafkaIndividus(){
        return Individus.builder()
                .identifier(SGIN)
                .lastname("TOMPONIONY")
                .firstname("LOHARANO")
                .secondFirstname("")
                .gender("M")
                .birthdate("03-JAN-86")
                .civility("M.")
                .status("V")
                .creationSite("GMMAL")
                .creationSignature("AF")
                .creationDate("21-APR-14")
                .modificationSite("QVI")
                .modificationSignature("REPIND/IHM")
                .modificationDate("29-AUG-23")
                .type("I")
                .build();
    }

    public static Emails buildMockedKafkaEmail(){
        return Emails.builder()
                .identifier("98212637")
                .gin("400754213892")
                .mediumCode("D")
                .mediumStatus("V")
                .email("la@la.com")
                .creationDate("29-AUG-23")
                .creationSignature("icare")
                .creationSite("QVI")
                .modificationDate("29-AUG-23")
                .modificationSignature("icare")
                .modificationSite("QVI")
                .build();
    }

    public static TmpProfile buildMockedTmpProfile(){
        return TmpProfile.builder()
                .gin("1123456789")
                .firstname("Mohamed")
                .lastname("JADAR")
                .birthdate("1999-06-05")
                .codeLanguage("JA")
                .gender("M")
                .civility("MR")
                .email("mjadar@repind.com")
                .emailStatus("V")
                .departureAirportKl("AMS")
                .preferredAirport("CDG")
                .zipCode("06000")
                .city("NICE")
                .country("FR")
                .cin(null)
                .fbEnrollmentDate(null)
                .myAccountId(null)
                .maEnrollmentDate(null)
                .build();
    }

    // Mocking IndividusDataModel
    public static IndividusDataModel buildMockedIndividusDataModelForIndividus(boolean eligible){
        IndividusDataModel individusDataModel = new IndividusDataModel();
        Individus individus = buildMockedKafkaIndividus();
        individusDataModel.setGin(individus.getIdentifier());
        individusDataModel.setIndividus(individus);
        individusDataModel.setEligible(eligible);
        return individusDataModel;
    }

    public static IndividusDataModel buildMockedIndividusDataModelForEmail(boolean eligible){
        IndividusDataModel individusDataModel = new IndividusDataModel();
        Emails email = buildMockedKafkaEmail();
        individusDataModel.setGin(email.getGin());
        individusDataModel.setEmails(email);
        individusDataModel.setEligible(eligible);
        return individusDataModel;
    }

    // Mocking UpsertIndividusRequestCriteria
    public static UpsertIndividusRequestCriteria buildMockedUpsertIndividusRqCriteriaForIndividus(IndividusDataModel individusDataModel, String uuid){
        UpsertIndividusRequestCriteria upsertIndividusRequestCriteria = new UpsertIndividusRequestCriteria();
        Individus individus = individusDataModel.getIndividus();
        DataModel dataModel = new DataModel();

        Map<String,String> keys = new HashMap<>();
        keys.put(Profiles.GIN.name(), individusDataModel.getGin());
        keys.put(Profiles.Guid.name(), uuid);

        Map<String,String> values = new HashMap<>();
        values.put(Profiles.Firstname.name(), individus.getFirstname());
        values.put(Profiles.Surname.name(), individus.getLastname());
        values.put(Profiles.Gender.name(), individus.getGender());
        values.put(Profiles.Birthdate.name(), individus.getBirthdate());
        values.put(Profiles.Civility.name(), individus.getCivility());
        values.put(Profiles.Language.name(), "FR");


        dataModel.setKeys(keys);
        dataModel.setValues(values);

        upsertIndividusRequestCriteria.setEligible(individusDataModel.isEligible());
        upsertIndividusRequestCriteria.setIndividusList(List.of(dataModel));

        return upsertIndividusRequestCriteria;
    }

    public static UpsertIndividusRequestCriteria buildMockedUpsertIndividusRqCriteriaForEmails(IndividusDataModel individusDataModel, String uuid){
        UpsertIndividusRequestCriteria upsertIndividusRequestCriteria = new UpsertIndividusRequestCriteria();
        Emails email = individusDataModel.getEmails();
        DataModel dataModel = new DataModel();

        Map<String,String> keys = new HashMap<>();
        keys.put(Profiles.GIN.name(), individusDataModel.getGin());
        keys.put(Profiles.Guid.name(), uuid);

        Map<String,String> values = new HashMap<>();
        values.put(Profiles.Email_Address.name(), email.getEmail());
        values.put(Profiles.Email_Address_status.name(), email.getMediumStatus());


        dataModel.setKeys(keys);
        dataModel.setValues(values);

        upsertIndividusRequestCriteria.setEligible(individusDataModel.isEligible());
        upsertIndividusRequestCriteria.setIndividusList(List.of(dataModel));

        return upsertIndividusRequestCriteria;
    }

    public static EmailEntity getMockedEmailFromDB(){
        EmailEntity emailFromDB = new EmailEntity();
        emailFromDB.setEmail("mohamed@repind.com");
        emailFromDB.setCodeMedium("D");
        emailFromDB.setStatutMedium("V");
        return emailFromDB;
    }

    public static PostalAddress getMockedAdrPostFromDB(){
        PostalAddress adrPost = new PostalAddress();
        adrPost.setVille("NICE");
        adrPost.setCodePostal("06000");
        adrPost.setCodePays("FR");
        adrPost.setCodeMedium("D");
        adrPost.setStatutMedium("V");
        return adrPost;
    }

    public static RoleContract getMockedRoleContractFpFromDB(){
        RoleContract roleContract = new RoleContract();
        roleContract.setNumeroContrat("001123456");
        roleContract.setTypeContrat("FP");
        Date dateCreationContrat = new Date();
        roleContract.setDateCreation(dateCreationContrat);
        return roleContract;
    }
    public static RoleContract getMockedRoleContractMaFromDB(){
        RoleContract roleContract = new RoleContract();
        roleContract.setNumeroContrat("123a45");
        roleContract.setTypeContrat("MA");
        Date dateCreationContrat = new Date();
        roleContract.setDateCreation(dateCreationContrat);
        return roleContract;
    }

    public static Individu buildMockedIndividusForTmpProfile(){
        Individu idv = new Individu();
        idv.setGin(SGIN);
        idv.setNom("Jadar");
        idv.setPrenom("Mohamed");
        idv.setSexe("M");
        idv.setCivilite("MR");
        idv.setDateNaissance(new Date());
        idv.setEmails(Set.of(getMockedEmailFromDB()));
        idv.setPostalAddresses(Set.of(getMockedAdrPostFromDB()));
        idv.setRoleContracts(Set.of(getMockedRoleContractFpFromDB()));
        idv.setPreferences(Collections.emptySet());
        return idv;
    }
}
