package com.afklm.repind.msv.provide.identification.data.it;

import com.afklm.repind.common.entity.identifier.AccountIdentifier;
import com.afklm.repind.common.entity.individual.*;
import com.afklm.repind.common.entity.individual.ComplementaryInformationEntity;
import com.afklm.repind.common.repository.identifier.AccountIdentifierRepository;
import com.afklm.repind.common.repository.individual.*;
import com.afklm.repind.msv.provide.identification.data.utils.GenerateTestData;
import com.afklm.soa.stubs.r000378.v1.model.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProvideIdentificationDataTest {
    @LocalServerPort
    private int port;
    @Autowired
    private AccountIdentifierRepository accountIdentifierRepository;
    @Autowired
    private IndividuRepository individuRepository;
    @Autowired
    private ProfilsRepository profilsRepository;
    @Autowired
    private DelegationDataRepository delegationDataRepository;
    @Autowired
    private ComplementaryInformationRepository complementaryInformationRepository;
    @Autowired
    private UsageClientRepository usageClientRepository;
    @Autowired
    RestTemplate restTemplate;
    String baseUrl = "http://localhost:";
    private ArrayList<AccountIdentifier> accountIdentifierArrayList = null;
    private ArrayList<Individu> individuArrayList = null;
    private ArrayList<ProfilsEntity> profilsEntityArrayList = null;
    private ArrayList<DelegationData> delegationDataArrayList = null;
    private ArrayList<ComplementaryInformationEntity> complementaryInformationEntityArrayList = null;
    private ArrayList<UsageClientEntity> usageClientEntityArrayList = null;
    private final String GIN = "400410574103";

    @BeforeEach
    void setupBase() {
        restTemplate = new RestTemplate();
        baseUrl = baseUrl + port;
        log.info("application started with base URL:{} and port:{}", baseUrl, port);
        log.info("Initializing database..");

        accountIdentifierArrayList = new ArrayList<>();
        individuArrayList = new ArrayList<>();
        profilsEntityArrayList = new ArrayList<>();
        delegationDataArrayList = new ArrayList<>();
        complementaryInformationEntityArrayList = new ArrayList<>();
        usageClientEntityArrayList = new ArrayList<>();

        accountIdentifierArrayList.add(accountIdentifierRepository.save(GenerateTestData.buildAccountIdentifier()));
        accountIdentifierArrayList.add(accountIdentifierRepository.save(GenerateTestData.buildAccountIdentifier2()));
        accountIdentifierArrayList.add(accountIdentifierRepository.save(GenerateTestData.buildAccountIdentifier3()));
        accountIdentifierArrayList.add(accountIdentifierRepository.save(GenerateTestData.buildAccountIdentifier4()));
        accountIdentifierArrayList.add(accountIdentifierRepository.save(GenerateTestData.buildAccountIdentifier5()));

        individuArrayList.add(individuRepository.save(GenerateTestData.buildIndividual()));
        individuArrayList.add(individuRepository.save(GenerateTestData.buildIndividual2()));
        individuArrayList.add(individuRepository.save(GenerateTestData.buildIndividual3()));
        individuArrayList.add(individuRepository.save(GenerateTestData.buildIndividual4()));
        individuArrayList.add(individuRepository.save(GenerateTestData.buildIndividual5()));

        profilsEntityArrayList.add(profilsRepository.save(GenerateTestData.buildProfilEntityForH2DB()));

        for (DelegationData delegationData : GenerateTestData.buildDelegatesList()) {
            delegationDataArrayList.add(delegationDataRepository.save(delegationData));
        }
        for (DelegationData delegationData : GenerateTestData.buildDelegatorsList()) {
            delegationDataArrayList.add(delegationDataRepository.save(delegationData));
        }
        for (ComplementaryInformationEntity complementaryInformationEntity : GenerateTestData.buildComplementaryInformationEntityListForH2Db()) {
            complementaryInformationEntityArrayList.add(complementaryInformationRepository.save(complementaryInformationEntity));
        }
        usageClientEntityArrayList.add(usageClientRepository.save(GenerateTestData.buildUsageClientList().get(0)));
    }

    @AfterEach
    @Sql(scripts = "classpath:/clean.sql")
    void cleanBase() {
        accountIdentifierRepository.deleteAll(accountIdentifierArrayList);
        individuRepository.deleteAll(individuArrayList);
        profilsRepository.deleteAll(profilsEntityArrayList);
        delegationDataRepository.deleteAll(delegationDataArrayList);
        complementaryInformationRepository.deleteAll(complementaryInformationEntityArrayList);
        usageClientRepository.deleteAll(usageClientEntityArrayList);
    }

    @Test
    @Sql(scripts = "classpath:/init.sql")
    void testGeneralController() {
        ResponseEntity<ProvideIdentificationData> result = restTemplate.getForEntity((baseUrl + "/" + GIN), ProvideIdentificationData.class);
        assertAll(
                () -> assertNotNull(result.getBody()),
                () -> assertNotNull(result.getBody().getIdentificationDataReponse()),
                () -> assertNotNull(result.getBody().getAccountDataReponse()),
                () -> assertNotNull(result.getBody().getDelegationDataResponse())
        );
        testIdentificationDataResponseContent(result.getBody().getIdentificationDataReponse());
        testAccountDataResponseContent(result.getBody().getAccountDataReponse());
        testDelegationDataResponseContent(result.getBody().getDelegationDataResponse());
    }

    private void testDelegationDataResponseContent(DelegationDataResponse delegationDataResponse) {
        Signature signatureAttendue = new Signature();

        SignatureCreation signatureCreation = new SignatureCreation();
        signatureCreation.setDate(LocalDate.of(2022, 9, 26));
        signatureCreation.setSite("AMS");
        signatureCreation.setSignature("CustomerAPI");

        SignatureModification signatureModification = new SignatureModification();
        signatureModification.setDate(LocalDate.of(2022, 9, 26));
        signatureModification.setSite("AMS");
        signatureModification.setSignature("CustomerAPI");

        signatureAttendue.setSignatureCreation(signatureCreation);
        signatureAttendue.setSignatureModification(signatureModification);

        for (Delegator delegator : delegationDataResponse.getDelegators()) {
            testDelegetionIndividualDataContent(delegator.getDelegationIndividualData());
            testDelegationStatusDataContent(delegator.getDelegationStatusData());
            testSignatureContent(delegator.getSignature(), signatureAttendue);
            testTelecomsContent(delegator.getTelecoms());
        }

        for (Delegate delegate : delegationDataResponse.getDelegates()) {
            testDelegetionIndividualDataContent(delegate.getDelegationIndividualData());
            testDelegationStatusDataContent(delegate.getDelegationStatusData());
            testSignatureContent(delegate.getSignature(), signatureAttendue);
            testTelecomsContent(delegate.getTelecoms());
            testComplementaryInformationContent(delegate.getComplementaryInformation());
        }
    }

    private void testComplementaryInformationContent(List<com.afklm.soa.stubs.r000378.v1.model.ComplementaryInformation> complementaryInformation) {
        com.afklm.soa.stubs.r000378.v1.model.ComplementaryInformation tmp = complementaryInformation.get(0);

        ComplementaryInformationData phoneNumber = tmp.getComplementaryInformationDatas().get(0);
        ComplementaryInformationData terminalType = tmp.getComplementaryInformationDatas().get(1);
        ComplementaryInformationData countryCode = tmp.getComplementaryInformationDatas().get(2);

        assertAll(
                () -> Assertions.assertEquals(1, complementaryInformation.size()),
                () -> Assertions.assertEquals("TEL", tmp.getType()),
                () -> Assertions.assertEquals(3, tmp.getComplementaryInformationDatas().size()),
                () -> Assertions.assertEquals("phoneNumber", phoneNumber.getKey()),
                () -> Assertions.assertEquals("874586512", phoneNumber.getValue()),
                () -> Assertions.assertEquals("terminalType", terminalType.getKey()),
                () -> Assertions.assertEquals("M", terminalType.getValue()),
                () -> Assertions.assertEquals("countryCode", countryCode.getKey()),
                () -> Assertions.assertEquals("33", countryCode.getValue())
        );
    }

    private void testTelecomsContent(List<Telecom> telecoms) {
        Telecom telecom = telecoms.get(0);

        Assertions.assertAll(
                () -> assertEquals("1", telecom.getCountryCode()),
                () -> assertEquals("D", telecom.getMediumCode()),
                () -> assertEquals("V", telecom.getMediumStatus()),
                () -> assertEquals("7132139009", telecom.getPhoneNumber()),
                () -> assertEquals("M", telecom.getTerminalType()),
                () -> assertEquals("2", telecom.getVersion())
        );
    }

    private void testDelegationStatusDataContent(DelegationStatusData delegationStatusData) {
        Assertions.assertAll(
                () -> Assertions.assertEquals("A", delegationStatusData.getDelegationStatus()),
                () -> Assertions.assertEquals("UM", delegationStatusData.getDelegationType())
        );
    }

    private void testDelegetionIndividualDataContent(DelegationIndividualData delegationIndividualData) {
        Assertions.assertAll(
                () -> Assertions.assertEquals(GenerateTestData.buildAccountIdentifier().getAccountId(), delegationIndividualData.getAccountIdentifier()),
                () -> Assertions.assertEquals(GenerateTestData.buildIndividual().getCivilite(), delegationIndividualData.getCivility()),
                () -> Assertions.assertEquals(GenerateTestData.buildAccountIdentifier().getEmailIdentifier(), delegationIndividualData.getEmailIdentifier()),
                () -> Assertions.assertEquals(GenerateTestData.buildAccountIdentifier().getFbIdentifier(), delegationIndividualData.getFbIdentifier()),
                () -> Assertions.assertEquals(GenerateTestData.buildIndividual().getPrenomTypo1(), delegationIndividualData.getFirstName()),
                () -> Assertions.assertEquals(GenerateTestData.buildIndividual().getPrenomTypo1(), delegationIndividualData.getFirstNameSC()),
                () -> Assertions.assertEquals(GenerateTestData.buildIndividual().getNomTypo1(), delegationIndividualData.getLastName()),
                () -> Assertions.assertEquals(GenerateTestData.buildIndividual().getNomTypo1(), delegationIndividualData.getLastNameSC())
        );
    }

    private void testAccountDataResponseContent(AccountDataResponse accountDataReponse) {
        Assertions.assertEquals(3, accountDataReponse.getIdentifierDatas().size());

        Signature expectedSignature = new Signature();

        SignatureCreation expectedSignatureCreation = new SignatureCreation();
        expectedSignatureCreation.setSite("S09372");
        expectedSignatureCreation.setDate(LocalDate.of(2013, 12, 6));
        expectedSignatureCreation.setSignature("CREATE ACCOUNT");

        SignatureModification expectedSignatureModification = new SignatureModification();
        expectedSignatureModification.setSite("SIC WS");
        expectedSignatureModification.setDate(LocalDate.of(2013, 12, 6));
        expectedSignatureModification.setSignature("AUTHENT MYACCNT");

        expectedSignature.setSignatureCreation(expectedSignatureCreation);
        expectedSignature.setSignatureModification(expectedSignatureModification);

        for (IdentifierData identifierData : accountDataReponse.getIdentifierDatas()) {
            switch (identifierData.getType()) {
                case "A":
                    assertEquals("786442AH", identifierData.getIdentifier());
                    testSignatureContent(identifierData.getSignature(), expectedSignature);
                    break;
                case "E":
                    assertEquals("jsmith@fwellc.com", identifierData.getIdentifier());
                    testSignatureContent(identifierData.getSignature(), expectedSignature);
                    break;
                case "F":
                    assertEquals("002101358216", identifierData.getIdentifier());
                    testSignatureContent(identifierData.getSignature(), expectedSignature);
                    break;
            }
        }
    }

    private void testIdentificationDataResponseContent(IdentificationDataResponse identificationDataReponse) {
        testIndividualInformationContent(identificationDataReponse.getIndividualInformations());
        testUsageClientContent(identificationDataReponse.getUsagesClient());

        Signature signatureAttendue = new Signature();

        SignatureCreation signatureCreation = new SignatureCreation();
        signatureCreation.setDate(LocalDate.of(2013, 12, 6));
        signatureCreation.setSite("ISI");
        signatureCreation.setSignature("WEB");

        SignatureModification signatureModification = new SignatureModification();
        signatureModification.setDate(LocalDate.of(2013, 12, 6));
        signatureModification.setSite("ISI");
        signatureModification.setSignature("WEB");

        signatureAttendue.setSignatureCreation(signatureCreation);
        signatureAttendue.setSignatureModification(signatureModification);
        testSignatureContent(identificationDataReponse.getSignature(), signatureAttendue);
    }

    private void testUsageClientContent(List<UsageClient> usagesClient) {
        Assertions.assertEquals(1, usagesClient.size());

        UsageClient tmp = usagesClient.get(0);

        Assertions.assertAll(
                () -> Assertions.assertEquals("65267858", tmp.getSrin()),
                () -> Assertions.assertEquals("ISI", tmp.getApplicationCode()),
                () -> Assertions.assertEquals("O", tmp.getAuthorizedModification()),
                () -> Assertions.assertEquals(LocalDate.of(2013, 12, 6), tmp.getLastModificationDate())
        );
    }

    private void testIndividualInformationContent(IndividualInformations individualInformations) {
        Assertions.assertAll(
                () -> assertEquals("400410574103", individualInformations.getIdentifier()),
                () -> assertEquals("1020014902", individualInformations.getPersonalIdentifier()),
                () -> assertEquals("3", individualInformations.getVersion()),
                () -> assertEquals("M", individualInformations.getGender()),
                () -> assertEquals("MR", individualInformations.getCivility()),
                () -> assertEquals(LocalDate.of(1960, 2, 1), individualInformations.getBirthdate()),
                () -> assertFalse(individualInformations.getFlagThirdTrap()),
                () -> assertFalse(individualInformations.getFlagNoFusion()),
                () -> assertEquals("I", individualInformations.getPopulationType()),
                () -> assertEquals("V", individualInformations.getStatus()),
                () -> assertEquals("EN", individualInformations.getLanguageCode()),
                () -> assertEquals("CJU", individualInformations.getTitleCode()),
                () -> assertEquals("JOHN", individualInformations.getFirstNameSC()),
                () -> assertEquals("JOHN", individualInformations.getFirstNameNormalized()),
                () -> assertEquals("JOHN", individualInformations.getFirstNamePseudonym()),
                () -> assertEquals("SMITH", individualInformations.getLastNameSC()),
                () -> assertEquals("SMITH", individualInformations.getLastNameNormalized()),
                () -> assertEquals("SMITH", individualInformations.getLastNamePseudonym()),
                () -> assertEquals("MICHAEL", individualInformations.getSecondFirstName())
        );
    }

    private void testSignatureContent(Signature signature, Signature signatureAttendue) {
        Assertions.assertAll(
                () -> assertEquals(signatureAttendue.getSignatureCreation().getSignature(), signature.getSignatureCreation().getSignature()),
                () -> assertEquals(signatureAttendue.getSignatureCreation().getSite(), signature.getSignatureCreation().getSite()),
                () -> assertEquals(signatureAttendue.getSignatureCreation().getDate(), signature.getSignatureCreation().getDate()),

                () -> assertEquals(signatureAttendue.getSignatureModification().getSignature(), signature.getSignatureModification().getSignature()),
                () -> assertEquals(signatureAttendue.getSignatureModification().getSite(), signature.getSignatureModification().getSite()),
                () -> assertEquals(signatureAttendue.getSignatureModification().getDate(), signature.getSignatureModification().getDate())
        );
    }

    @Test
    void testError404NotFoundAccount(){
        HttpClientErrorException thrown = assertThrows(HttpClientErrorException.NotFound.class,
                () -> restTemplate.getForEntity((baseUrl + "/99999999999/account"), IdentificationDataResponse.class)
        );
        assertTrue(thrown.getMessage().contains("business.error.001"));
    }
    @Test
    void testError404NotFoundDelegation(){
        HttpClientErrorException thrown = assertThrows(HttpClientErrorException.NotFound.class,
                () -> restTemplate.getForEntity((baseUrl + "/99999999999/delegation"), IdentificationDataResponse.class)
        );
        assertTrue(thrown.getMessage().contains("business.error.002"));
    }
    @Test
    void testError404NotFoundIdentification(){
        HttpClientErrorException thrown = assertThrows(HttpClientErrorException.NotFound.class,
                () -> restTemplate.getForEntity((baseUrl + "/99999999999/identification"), IdentificationDataResponse.class)
        );
        assertTrue(thrown.getMessage().contains("business.error.003"));
    }
    @Test
    void testError404NotFoundGeneralController(){
        HttpClientErrorException thrown = assertThrows(HttpClientErrorException.NotFound.class,
                () -> restTemplate.getForEntity((baseUrl + "/99999999999"), ProvideIdentificationData.class)
        );
        assertTrue(thrown.getMessage().contains("business.error.004"));
    }

    @Test
    void testError404NotFoundWithLessThan12DigitAccount(){
        HttpClientErrorException thrown = assertThrows(HttpClientErrorException.NotFound.class,
                () -> restTemplate.getForEntity((baseUrl + "/9999999999/account"), IdentificationDataResponse.class)
        );
        assertTrue(thrown.getMessage().contains("business.error.001"));
    }
    @Test
    void testError404NotFoundWithLessThan12DigitDelegation(){
        HttpClientErrorException thrown = assertThrows(HttpClientErrorException.NotFound.class,
                () -> restTemplate.getForEntity((baseUrl + "/9999999999/delegation"), IdentificationDataResponse.class)
        );
        assertTrue(thrown.getMessage().contains("business.error.002"));
    }
    @Test
    void testError404NotFoundWithLessThan12DigitIdentification(){
        HttpClientErrorException thrown = assertThrows(HttpClientErrorException.NotFound.class,
                () -> restTemplate.getForEntity((baseUrl + "/9999999999/identification"), IdentificationDataResponse.class)
        );
        assertTrue(thrown.getMessage().contains("business.error.003"));
    }
    @Test
    void testError404NotFoundWithLessThan12DigitGeneralController(){
        HttpClientErrorException thrown = assertThrows(HttpClientErrorException.NotFound.class,
                () -> restTemplate.getForEntity((baseUrl + "/9999999999"), ProvideIdentificationData.class)
        );
        assertTrue(thrown.getMessage().contains("business.error.004"));
    }

    @Test
    void testError404BadParameterAccount(){
        HttpClientErrorException thrown = assertThrows(HttpClientErrorException.NotFound.class,
                () -> restTemplate.getForEntity((baseUrl + "/9999999999999999999/account"), IdentificationDataResponse.class)
        );
        assertTrue(thrown.getMessage().contains("business.error.005"));
    }
    @Test
    void testError404BadParameterDelegation(){
        HttpClientErrorException thrown = assertThrows(HttpClientErrorException.NotFound.class,
                () -> restTemplate.getForEntity((baseUrl + "/9999999999999999999/delegation"), IdentificationDataResponse.class)
        );
        assertTrue(thrown.getMessage().contains("business.error.005"));
    }
    @Test
    void testError404BadParameterIdentification(){
        HttpClientErrorException thrown = assertThrows(HttpClientErrorException.NotFound.class,
                () -> restTemplate.getForEntity((baseUrl + "/9999999999999999999/identification"), IdentificationDataResponse.class)
        );
        assertTrue(thrown.getMessage().contains("business.error.005"));
    }
    @Test
    void testError404BadParameterGeneralController(){
        HttpClientErrorException thrown = assertThrows(HttpClientErrorException.NotFound.class,
                () -> restTemplate.getForEntity((baseUrl + "/9999999999999999999"), ProvideIdentificationData.class)
        );
        assertTrue(thrown.getMessage().contains("business.error.005"));
    }
}