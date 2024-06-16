package com.afklm.repind.msv.provide.preference.data.integrationtests;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import com.afklm.soa.stubs.r000380.v1.model.CommunicationPreferencesResponse;
import com.afklm.soa.stubs.r000380.v1.model.ProvidePreferencesData;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@TestPropertySource("/application.yml")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CommunicationPreferenceIntegrationTest {
    @LocalServerPort
    private int port;

    @Autowired
    JdbcTemplate jdbcTemplate;

    String baseUrl = "http://localhost:";
    RestTemplate restTemplate;

    private String GIN = "110001017463";

    @BeforeEach
    void setup() {
        restTemplate = new RestTemplate();
        baseUrl = baseUrl + port;
        log.info("application started with base URL:{} and port:{}", baseUrl, port);
        log.info("Initializing DB..");
    }

    @AfterEach
    void emptyData(){
        Integer totalRecords = jdbcTemplate.queryForObject("Select count(*) from Communication_preferences", Integer.class);
        log.info("Total Records in DB:{}", totalRecords);
        log.info("Deleting records from table.");
        jdbcTemplate.execute("DELETE FROM Communication_preferences ; DELETE FROM Market_language");
    }

    @Test
    @DisplayName("Test Get Communication Preferences by GIN")
    @Sql(scripts = "classpath:/initialize-data-communicationPreference.sql")
    void testGetPreferencesByGin() {
        ResponseEntity<CommunicationPreferencesResponse> response = restTemplate.getForEntity((baseUrl + "/" + GIN + "/communicationpreference"), CommunicationPreferencesResponse.class);
        assertAll(
                () -> assertNotNull(response.getBody()),
                () -> assertNotNull(response.getBody().getCommunicationPreferences()),
                () -> assertEquals(2,response.getBody().getCommunicationPreferences().size()),
                () -> assertTrue(response.getBody().getCommunicationPreferences().stream()
                        .anyMatch(s -> s.getDomain().equals("U"))),
                () -> assertTrue(response.getBody().getCommunicationPreferences().stream()
                        .anyMatch(s -> s.getDomain().equals("S"))),

                () -> assertTrue(response.getBody().getCommunicationPreferences().stream()
                        .anyMatch(s -> s.getMarketLanguages().get(0).getSignatureCreation().equals("CBS"))),
                () -> assertTrue(response.getBody().getCommunicationPreferences().stream()
                        .anyMatch(s -> s.getMarketLanguages().get(0).getSignatureCreation().equals("ALIM-WW-BATCH")))
        );
    }

    @Test
    @DisplayName("Test 404 for wrong format GIN sup12")
    void testError404WrongFormatGINsup(){
        HttpClientErrorException thrown = assertThrows(HttpClientErrorException.NotFound.class,
                () -> restTemplate.getForEntity((baseUrl + "/1234567891234/communicationpreference"), ProvidePreferencesData.class));

        assertTrue(thrown.getMessage().contains("business.error.004"));
    }

    @Test
    @DisplayName("Test 404 for non existing GIN")
    void testError404WrongGIN(){
        HttpClientErrorException thrown = assertThrows(HttpClientErrorException.NotFound.class,
                () -> restTemplate.getForEntity((baseUrl + "/000123456789/communicationpreference"), ProvidePreferencesData.class));

        assertTrue(thrown.getMessage().contains("business.error.001"));
    }

    @Test
    @DisplayName("Test 404 for wrong format GIN less12")
    void testError404WrongFormatGINless() {
        HttpClientErrorException thrown = assertThrows(HttpClientErrorException.NotFound.class,
                () -> restTemplate.getForEntity((baseUrl + "/123456789/communicationpreference"), ProvidePreferencesData.class));

        assertTrue(thrown.getMessage().contains("business.error.001"));
    }
}
