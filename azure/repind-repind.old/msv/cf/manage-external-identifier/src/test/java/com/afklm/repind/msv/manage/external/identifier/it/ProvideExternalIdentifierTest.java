package com.afklm.repind.msv.manage.external.identifier.it;

import com.afklm.repind.common.entity.individual.IndividualEntityScan;
import com.afklm.repind.common.repository.identifier.ExternalIdentifierDataRepository;
import com.afklm.repind.common.repository.identifier.ExternalIdentifierRepository;
import com.afklm.repind.common.repository.individual.IndividuRepository;
import com.afklm.repind.common.repository.individual.IndividualRepositoryScan;
import com.afklm.repind.msv.manage.external.identifier.models.tmp.stubs.ExternalIdentifier;
import com.afklm.repind.msv.manage.external.identifier.models.tmp.stubs.ExternalIdentifierData;
import com.afklm.repind.msv.manage.external.identifier.models.tmp.stubs.ExternalIdentifierResponse;
import com.afklm.repind.msv.manage.external.identifier.models.tmp.stubs.Signature;
import com.afklm.repind.msv.manage.external.identifier.utils.BuildData;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Slf4j
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EntityScan(basePackageClasses = {
        IndividualEntityScan.class
})
@EnableJpaRepositories(basePackageClasses = {
        IndividualRepositoryScan.class
})
class ProvideExternalIdentifierTest {
    @LocalServerPort
    private int port;
    @Autowired
    RestTemplate restTemplate;
    String baseUrl = "http://localhost:";
    private final String GIN = "12345678912";

    @Autowired
    ExternalIdentifierRepository externalIdentifierRepository;

    @Autowired
    ExternalIdentifierDataRepository externalIdentifierDataRepository;

    @Autowired
    IndividuRepository individuRepository;

    @BeforeEach
    void setup() {
        restTemplate = new RestTemplate();
        baseUrl = baseUrl + port;
        log.info("application started with base URL:{} and port:{}", baseUrl, port);
        log.info("Initializing database..");

        individuRepository.save(BuildData.buildExternalIdentifierEntityList().get(0).getIndividu());
        externalIdentifierRepository.saveAll(BuildData.buildExternalIdentifierEntityList());
        externalIdentifierDataRepository.saveAll(BuildData.buildListExternalIdentifierDataEntity());
    }

    @AfterEach
    void cleanBase() {
        individuRepository.delete(BuildData.buildExternalIdentifierEntityList().get(0).getIndividu());
        externalIdentifierRepository.deleteAll(BuildData.buildExternalIdentifierEntityList());
        externalIdentifierDataRepository.deleteAll(BuildData.buildListExternalIdentifierDataEntity());
    }

    @Test
    void testGeneralController() {
        ResponseEntity<ExternalIdentifierResponse> result = restTemplate.getForEntity((baseUrl + "/" + GIN), ExternalIdentifierResponse.class);
        assertAll(
                () -> assertNotNull(result.getBody()),
                () -> assertNotNull(Objects.requireNonNull(result.getBody()).getExternalIdentifierList()),
                () -> assertEquals(1, Objects.requireNonNull(result.getBody()).getExternalIdentifierList().size())
        );
        testExternalIdentifier(result.getBody().getExternalIdentifierList().get(0));
    }

    private void testExternalIdentifier(ExternalIdentifier externalIdentifier) {
        assertAll(
                () -> assertEquals("cust-le",externalIdentifier.getIdentifier()),
                () -> assertEquals("PNM_ID", externalIdentifier.getType()),
                () -> assertEquals(1,externalIdentifier.getExternalIdentifierData().size())
        );
        testSignature(externalIdentifier.getSignature());
        testExternalIdentifierData(externalIdentifier.getExternalIdentifierData().get(0));
    }

    private void testExternalIdentifierData(ExternalIdentifierData externalIdentifierData) {
        assertAll(
                () -> assertEquals("key", externalIdentifierData.getKey()),
                () -> assertEquals("value", externalIdentifierData.getValue())
        );
        testSignature(externalIdentifierData.getSignature());
    }

    private void testSignature(Signature signature) {
        Date date = Date.from(LocalDate.of(2013, 12, 6).atStartOfDay(ZoneId.systemDefault()).toInstant());
        assertAll(
                () -> assertEquals("QVI", signature.getCreation().getSite()),
                () -> assertEquals("RI_TEAM", signature.getCreation().getSignature()),
                () -> assertEquals(date, signature.getCreation().getDate()),
                () -> assertEquals("QVI", signature.getModification().getSite()),
                () -> assertEquals("RI_TEAM", signature.getModification().getSignature()),
                () -> assertEquals(date, signature.getModification().getDate())
        );
    }

    @Test
    void testWithTooLongGin() {
        assertThrows(HttpClientErrorException.BadRequest.class, () -> restTemplate.getForEntity((baseUrl + "/123123131321331231312323"), ExternalIdentifierResponse.class));
    }

    @Test
    void testWithInexistantGin() {
        assertThrows(HttpClientErrorException.NotFound.class, () -> restTemplate.getForEntity((baseUrl + "/a123123123aa"), ExternalIdentifierResponse.class));
    }
}
