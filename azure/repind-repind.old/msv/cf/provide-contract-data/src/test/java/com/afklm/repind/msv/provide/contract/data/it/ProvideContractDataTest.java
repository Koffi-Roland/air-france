package com.afklm.repind.msv.provide.contract.data.it;

import com.afklm.repind.common.enums.ContractType;
import com.afklm.repind.msv.provide.contract.data.helper.GenerateTestData;
import com.afklm.repind.msv.provide.contract.data.models.stubs.Contract;
import com.afklm.repind.msv.provide.contract.data.models.stubs.SignatureElement;
import com.afklm.repind.msv.provide.contract.data.transform.ContractTransform;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
@TestPropertySource("/application.yml")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProvideContractDataTest {
    @LocalServerPort
    private int port;
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    private ContractTransform contractTransform;
    String baseUrl = "http://localhost:";
    String gin = "999999999998";
    String cin = "9876543210";

    @BeforeEach
    void setup() {
        restTemplate = new RestTemplate();
        baseUrl = baseUrl + port;
        log.info("application started with base URL:{} and port:{}", baseUrl, port);
        log.info("Initializing database..");
    }

    @AfterEach
    @Sql(scripts = "classpath:/clean-data.sql")
    void cleanBase() {
    }

    @Test
    @Sql(scripts = "classpath:/initialize-data.sql")
    void testGeneralCaseForGin() {
        ResponseEntity<List<Contract>> response = restTemplate.exchange(baseUrl + "/GIN/" + gin, HttpMethod.GET, null, new ParameterizedTypeReference<List<Contract>>() {
        });
        assertNotNull(response.getBody());
        assertEquals(4, response.getBody().size());
        testContractTraveler(response.getBody().get(0));
        testContractUCCR(response.getBody().get(1));
        testContract(response.getBody().get(2));
        testContractDoctor(response.getBody().get(3));
    }

    @Test
    @Sql(scripts = "classpath:/initialize-data.sql")
    void testGeneralCaseForCin() {
        ResponseEntity<List<Contract>> response = restTemplate.exchange(baseUrl + "/CIN/" + cin, HttpMethod.GET, null, new ParameterizedTypeReference<List<Contract>>() {
        });
        assertNotNull(response.getBody());
        assertEquals(4, response.getBody().size());
        testContractTraveler(response.getBody().get(0));
        testContractUCCR(response.getBody().get(1));
        testContract(response.getBody().get(2));
        testContractDoctor(response.getBody().get(3));
    }

    private void testContractDoctor(Contract contract) {
        Contract expectedDoctor = new Contract();
        expectedDoctor.setContractNumber(GenerateTestData.createBusinessRoleList().get(3).getNumeroContrat());
        expectedDoctor.setContractType(ContractType.CONTRACT_DOCTOR.toString());

        assertAll(
                () -> assertEquals(GenerateTestData.createBusinessRoleList().get(3).getNumeroContrat(), contract.getContractNumber()),
                () -> assertEquals(ContractType.CONTRACT_DOCTOR.toString(), contract.getContractType())
        );
    }

    private void testContract(Contract contract) {
        Contract expectedContract = new Contract();
        expectedContract.setContractNumber(GenerateTestData.createBusinessRoleList().get(2).getNumeroContrat());
        expectedContract.setContractType("MA");
        expectedContract.setValidityStartDate(LocalDate.of(2023,6,15));
        expectedContract.setValidityEndDate(LocalDate.of(2023,6,15));
        expectedContract.setContractStatus("C");
        expectedContract.setIataCode("012345678");
        expectedContract.setCompanyCode("AF");

        SignatureElement signatureCreation = new SignatureElement(LocalDate.of(2023,6,15), "RI", "QVI");
        expectedContract.setSignatureCreation(signatureCreation);
        SignatureElement signatureModification = new SignatureElement(LocalDate.of(2023,6,15), "RI", "QVI");
        expectedContract.setSignatureModification(signatureModification);

        assertAll(
                () -> assertEquals(expectedContract.getValidityEndDate(), contract.getValidityEndDate()),
                () -> assertEquals(expectedContract.getValidityStartDate(), contract.getValidityStartDate()),
                () -> assertEquals(expectedContract.getContractNumber(), contract.getContractNumber()),
                () -> assertEquals(expectedContract.getContractStatus(), contract.getContractStatus()),
                () -> assertEquals(expectedContract.getContractType(), contract.getContractType()),
                () -> assertEquals(expectedContract.getCorporateEnvironmentID(), contract.getCorporateEnvironmentID())
        );
        testSignatureBetweenTwoContract(expectedContract, contract);

    }

    private void testContractUCCR(Contract contract) {
        Contract expectedUCCR = new Contract();
        expectedUCCR.setContractNumber(GenerateTestData.createBusinessRoleList().get(1).getNumeroContrat());
        expectedUCCR.setContractType(ContractType.ROLE_UCCR.toString());
        expectedUCCR.setCorporateEnvironmentID("876543210");
        expectedUCCR.setValidityStartDate(LocalDate.of(2023,6,15));
        expectedUCCR.setValidityEndDate(LocalDate.of(2023,6,15));
        expectedUCCR.setContractStatus("X");

        SignatureElement signatureCreation = new SignatureElement(LocalDate.of(2023,6,15), "RI", "QVI");
        expectedUCCR.setSignatureCreation(signatureCreation);
                SignatureElement signatureModification = new SignatureElement(LocalDate.of(2023,6,15), "RI", "QVI");
        expectedUCCR.setSignatureModification(signatureModification);

        assertAll(
                () -> assertEquals(expectedUCCR.getValidityEndDate(), contract.getValidityEndDate()),
                () -> assertEquals(expectedUCCR.getValidityStartDate(), contract.getValidityStartDate()),
                () -> assertEquals(expectedUCCR.getContractNumber(), contract.getContractNumber()),
                () -> assertEquals(expectedUCCR.getContractStatus(), contract.getContractStatus()),
                () -> assertEquals(expectedUCCR.getContractType(), contract.getContractType()),
                () -> assertEquals(expectedUCCR.getCorporateEnvironmentID(), contract.getCorporateEnvironmentID())
        );
        testSignatureBetweenTwoContract(expectedUCCR, contract);

    }

    private void testContractTraveler(Contract contract) {
        Contract expectedTraveler = new Contract();
        expectedTraveler.setContractNumber(GenerateTestData.createBusinessRoleList().get(0).getNumeroContrat());
        expectedTraveler.setContractType(ContractType.ROLE_TRAVELERS.toString());
        expectedTraveler.setMatchingRecognition(GenerateTestData.createRoleTravelers().getMatchingRecognitionCode());
        expectedTraveler.setLastRecognitionDate(GenerateTestData.createRoleTravelers().getLastRecognitionDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());

        SignatureElement signatureCreation = new SignatureElement(LocalDate.of(2023,6,15), "RI", "QVI");
        expectedTraveler.setSignatureCreation(signatureCreation);
        SignatureElement signatureModification = new SignatureElement(LocalDate.of(2023,6,15), "RI", "QVI");
        expectedTraveler.setSignatureModification(signatureModification);

        assertAll(
                () -> assertEquals(expectedTraveler.getValidityEndDate(), contract.getValidityEndDate()),
                () -> assertEquals(expectedTraveler.getValidityStartDate(), contract.getValidityStartDate()),
                () -> assertEquals(expectedTraveler.getIataCode(), contract.getIataCode()),
                () -> assertEquals(expectedTraveler.getContractStatus(), contract.getContractStatus()),
                () -> assertEquals(expectedTraveler.getContractType(), contract.getContractType()),
                () -> assertEquals(expectedTraveler.getContractSubType(), contract.getContractSubType()),
                () -> assertEquals(expectedTraveler.getCompanyCode(), contract.getCompanyCode())
        );
        testSignatureBetweenTwoContract(expectedTraveler, contract);
    }

    void testSignatureBetweenTwoContract(Contract a, Contract b) {
        assertAll(
                () -> assertEquals(a.getSignatureCreation().getSignature(), b.getSignatureCreation().getSignature()),
                () -> assertEquals(a.getSignatureCreation().getDate(), b.getSignatureCreation().getDate()),
                () -> assertEquals(a.getSignatureCreation().getSite(), b.getSignatureCreation().getSite()),
                () -> assertEquals(a.getSignatureModification().getSignature(), b.getSignatureModification().getSignature()),
                () -> assertEquals(a.getSignatureModification().getDate(), b.getSignatureModification().getDate()),
                () -> assertEquals(a.getSignatureModification().getSite(), b.getSignatureModification().getSite())
        );
    }

    @Test
    void testError404NotFound() {
        HttpClientErrorException thrown = assertThrows(HttpClientErrorException.NotFound.class,
                () -> restTemplate.exchange(baseUrl + "/GIN/" + "999999999999", HttpMethod.GET, null, new ParameterizedTypeReference<List<Contract>>() {
                })
        );
        assertNotNull(thrown.getMessage());
        assertTrue(thrown.getMessage().contains("business.error.001"));
    }

    @Test
    void testErrorWrongType() {
        HttpClientErrorException thrown = assertThrows(HttpClientErrorException.Forbidden.class,
                () -> restTemplate.exchange(baseUrl + "/aze/" + "999999999999", HttpMethod.GET, null, new ParameterizedTypeReference<List<Contract>>() {
                })
        );
        assertNotNull(thrown.getMessage());
        assertTrue(thrown.getMessage().contains("business.error.003"));
    }

    @Test
    void testErrorWrongCin() {
        HttpClientErrorException thrown = assertThrows(HttpClientErrorException.Forbidden.class,
                () -> restTemplate.exchange(baseUrl + "/CIN/" + "9999999999999999", HttpMethod.GET, null, new ParameterizedTypeReference<List<Contract>>() {
                })
        );
        assertNotNull(thrown.getMessage());
        assertTrue(thrown.getMessage().contains("business.error.004"));
    }

    @Test
    void testErrorWrongGin() {
        HttpClientErrorException thrown = assertThrows(HttpClientErrorException.Forbidden.class,
                () -> restTemplate.exchange(baseUrl + "/GIN/" + "9999999999999999", HttpMethod.GET, null, new ParameterizedTypeReference<List<Contract>>() {
                })
        );
        assertNotNull(thrown.getMessage());
        assertTrue(thrown.getMessage().contains("business.error.005"));
    }
}
