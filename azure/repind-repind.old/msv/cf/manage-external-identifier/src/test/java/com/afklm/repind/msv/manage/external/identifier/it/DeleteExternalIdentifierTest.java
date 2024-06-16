package com.afklm.repind.msv.manage.external.identifier.it;

import com.afklm.repind.common.entity.identifier.ExternalIdentifier;
import com.afklm.repind.common.repository.identifier.ExternalIdentifierDataRepository;
import com.afklm.repind.common.repository.identifier.ExternalIdentifierRepository;
import com.afklm.repind.msv.manage.external.identifier.utils.BuildData;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class DeleteExternalIdentifierTest {

    @LocalServerPort
    private int port;
    @Autowired
    RestTemplate restTemplate;
    String baseUrl = "http://localhost:";
    @Autowired
    ExternalIdentifierRepository externalIdentifierRepository;
    @Autowired
    ExternalIdentifierDataRepository externalIdentifierDataRepository;

    ExternalIdentifier externalIdentifierEntity;
    Individu individu;

    @BeforeEach
    void setup() {
        restTemplate = new RestTemplate();
        baseUrl = baseUrl + port;
        log.info("application started with base URL:{} and port:{}", baseUrl, port);
        log.info("Initializing database..");

        individu = new Individu();
        individu.setGin("05678912");

        externalIdentifierEntity = BuildData.buildExternalIdentifierEntityList().get(0);
        externalIdentifierEntity.setIdentifierId(1L);
        externalIdentifierEntity.setGin("05678912");

        externalIdentifierRepository.save(externalIdentifierEntity);
        externalIdentifierRepository.saveAll(BuildData.buildExternalIdentifierEntityList());
        externalIdentifierDataRepository.saveAll(BuildData.buildListExternalIdentifierDataEntity());
    }

    @AfterEach
    void cleanBase() {
        externalIdentifierRepository.delete(externalIdentifierEntity);
        externalIdentifierRepository.deleteAll(BuildData.buildExternalIdentifierEntityList());
        externalIdentifierDataRepository.deleteAll(BuildData.buildListExternalIdentifierDataEntity());
    }

    @Test
    void testGeneralController() {

        assertFalse(externalIdentifierRepository.findAllByIdentifierAndType("cust-le", "PNM_ID").isEmpty());

        //Perform the deletion
        ResponseEntity<String> result = restTemplate.exchange(baseUrl + "/?identifier=cust-le&type=PNM_ID", HttpMethod.DELETE, null, String.class);

        assertEquals("\"Deletion completed\"", result.getBody());
        assertTrue(externalIdentifierRepository.findAllByIdentifierAndType("cust-le", "PNM_ID").isEmpty());
    }

    @Test
    void testWithNonExistantData() {
        assertTrue(externalIdentifierRepository.findAllByIdentifierAndType("cust-le123", "PNM_ID").isEmpty());

        assertThrows(HttpClientErrorException.NotFound.class, () -> {
            restTemplate.delete(baseUrl + "/?identifier=cust-le123456789AZERTYUI&type=PNM_ID");
        }) ;
    }

    @Test
    void testWithNonExistantType() {
        assertThrows(HttpClientErrorException.BadRequest.class, () -> {
            restTemplate.delete(baseUrl + "/?identifier=cust-le&type=PNM_ID123456789081687687216GZDHGADHG");
        }) ;
    }

    @Test
    void testWithMissingType() {
        assertThrows(HttpClientErrorException.BadRequest.class, () -> {
            restTemplate.delete(baseUrl + "/?identifier=cust-le");
        }) ;
    }

    @Test
    void testWithMissingIdentifier() {
        assertThrows(HttpClientErrorException.BadRequest.class, () -> {
            restTemplate.delete(baseUrl + "/?type=PNM_ID");
        }) ;
    }

    @Test
    void testWithTooLongGin() {
        assertThrows(HttpClientErrorException.BadRequest.class, () -> {
            restTemplate.delete(baseUrl + "/?identifier=cust-le&type=PNM_ID&gin=1234567890123456789234567893456789");
        }) ;
    }
}
