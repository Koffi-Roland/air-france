package com.afklm.repind.msv.provide.contact.data.integrationtests;

import com.afklm.soa.stubs.r000347.v1.model.ProvideContactData;
import com.afklm.soa.stubs.r000347.v1.model.TelecomResponse;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@TestPropertySource("/application.yml")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TelecomsIntegrationTest {
    @LocalServerPort
    private int port;

    @Autowired
    JdbcTemplate jdbcTemplate;

    String baseUrl = "http://localhost:";
    RestTemplate restTemplate;

    private String GIN = "110000019801";

    @BeforeEach
    void setup() {
        restTemplate = new RestTemplate();
        baseUrl = baseUrl + port;
        log.info("application started with base URL:{} and port:{}", baseUrl, port);
        log.info("Initializing DB..");
    }

    @AfterEach
    void emptyData(){
        Integer totalRecords = jdbcTemplate.queryForObject("Select count(*) from Telecoms", Integer.class);
        log.info("Total Records in DB:{}", totalRecords);
        log.info("Deleting records from table.");
        jdbcTemplate.execute("DELETE FROM Telecoms");
    }

    @Test
    @DisplayName("Test Get Telecoms by GIN")
    @Sql(scripts = "classpath:/initialize-data-telecoms.sql")
    void getTelecomsByGinTest() {
        ResponseEntity<List<TelecomResponse>> response = restTemplate.exchange((baseUrl + "/" + GIN + "/telecoms"), HttpMethod.GET, null, new ParameterizedTypeReference<List<TelecomResponse>>() {}, Collections.emptyMap());
        assertAll(
                () -> assertNotNull(response.getBody()),
                () -> assertNotNull(response.getBody().get(0).getTelecom()),
                () -> assertNotNull(response.getBody().get(0).getSignature()),
                () -> assertNotNull(response.getBody().get(0).getTelecomFlag()),
                () -> assertNotNull(response.getBody().get(0).getTelecomNormalization()),

                () -> assertEquals("D", response.getBody().get(0).getTelecom().getMediumCode()),
                () -> assertEquals(3, response.getBody().get(0).getTelecom().getVersion()),
                () -> assertEquals("V", response.getBody().get(0).getTelecom().getMediumStatus()),

                () -> assertEquals("C", response.getBody().get(0).getSignature().get(0).getSignatureType()),
                () -> assertEquals("Experian", response.getBody().get(0).getSignature().get(0).getSignature()),
                () -> assertEquals("Valbonne", response.getBody().get(0).getSignature().get(0).getSignatureSite()),

                () -> assertEquals("M", response.getBody().get(0).getSignature().get(1).getSignatureType()),
                () -> assertEquals("M402925", response.getBody().get(0).getSignature().get(1).getSignature()),
                () -> assertEquals("QVI", response.getBody().get(0).getSignature().get(1).getSignatureSite()),

                () -> assertEquals(false, response.getBody().get(0).getTelecomFlag().getFlagInvalidFixTelecom()),
                () -> assertEquals(false, response.getBody().get(0).getTelecomFlag().getFlagInvalidMobileTelecom()),
                () -> assertEquals(false, response.getBody().get(0).getTelecomFlag().getFlagNoValidNormalizedTelecom()),

                () -> assertEquals("FR", response.getBody().get(0).getTelecomNormalization().getIsoCountryCode()),
                () -> assertEquals("+33622666200", response.getBody().get(0).getTelecomNormalization().getInternationalPhoneNumber())
        );
    }

    @Test
    @DisplayName("Test 404 for wrong format GIN sup12")
    void testError404WrongFormatGINsup(){
        HttpClientErrorException thrown = assertThrows(HttpClientErrorException.NotFound.class,
                () -> restTemplate.getForEntity((baseUrl + "/1234567891234/telecoms"), ProvideContactData.class));

        assertTrue(thrown.getMessage().contains("business.error.005"));
    }

    @Test
    @DisplayName("Test 404 for non existing GIN")
    void testError404WrongGIN(){
        HttpClientErrorException thrown = assertThrows(HttpClientErrorException.NotFound.class,
                () -> restTemplate.getForEntity((baseUrl + "/000123456789/telecoms"), ProvideContactData.class));

        assertTrue(thrown.getMessage().contains("business.error.003"));
    }

    @Test
    @DisplayName("Test 404 for wrong format GIN less12")
    void testError404WrongFormatGINless(){
        HttpClientErrorException thrown = assertThrows(HttpClientErrorException.NotFound.class,
                () -> restTemplate.getForEntity((baseUrl + "/123456789/telecoms"), ProvideContactData.class));

        assertTrue(thrown.getMessage().contains("business.error.003"));
    }
}
