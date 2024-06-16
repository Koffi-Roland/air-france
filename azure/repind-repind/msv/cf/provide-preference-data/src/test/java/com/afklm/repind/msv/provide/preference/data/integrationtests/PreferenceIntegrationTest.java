package com.afklm.repind.msv.provide.preference.data.integrationtests;

import com.afklm.soa.stubs.r000380.v1.model.ProvidePreferencesData;
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
import com.afklm.soa.stubs.r000380.v1.model.PreferenceResponse;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;


@Slf4j
@TestPropertySource("/application.yml")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PreferenceIntegrationTest {
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
        Integer totalRecords = jdbcTemplate.queryForObject("Select count(*) from Preference", Integer.class);
        log.info("Total Records in DB:{}", totalRecords);
        log.info("Deleting records from table.");
        jdbcTemplate.execute("DELETE FROM Preference ; DELETE FROM Preference_data");
    }

    @Test
    @DisplayName("Test Get Preferences by GIN")
    @Sql(scripts = "classpath:/initialize-data-preference.sql")
    void testGetPreferencesByGin() {
        ResponseEntity<PreferenceResponse> response = restTemplate.getForEntity((baseUrl + "/" + GIN + "/preference"), PreferenceResponse.class);
        assertAll(
                () -> assertNotNull(response.getBody()),
                () -> assertNotNull(response.getBody().getPreferences()),
                () -> assertEquals(3,response.getBody().getPreferences().size()),
                () -> assertTrue(response.getBody().getPreferences().stream()
                        .anyMatch(s -> s.getType().equals("UCO"))),
                () -> assertTrue(response.getBody().getPreferences().stream()
                        .anyMatch(s -> s.getType().equals("ULO"))),
                () -> assertTrue(response.getBody().getPreferences().stream()
                        .anyMatch(s -> s.getType().equals("UTS"))),

                () -> assertTrue(response.getBody().getPreferences().stream()
                        .anyMatch(s -> s.getPreferenceData().get(0).getKey().equals("preferredCommunicationChannel"))),
                () -> assertTrue(response.getBody().getPreferences().stream()
                        .anyMatch(s -> s.getPreferenceData().get(0).getKey().equals("customerDetails"))),
                () -> assertTrue(response.getBody().getPreferences().stream()
                        .anyMatch(s -> s.getPreferenceData().get(0).getKey().equals("specificAttention")))
        );
    }

    @Test
    @DisplayName("Test 404 for wrong format GIN sup12")
    void testError404WrongFormatGINsup(){
        HttpClientErrorException thrown = assertThrows(HttpClientErrorException.NotFound.class,
                () -> restTemplate.getForEntity((baseUrl + "/1234567891234/preference"), ProvidePreferencesData.class));

        assertTrue(thrown.getMessage().contains("business.error.004"));
    }

    @Test
    @DisplayName("Test 404 for non existing GIN")
    void testError404WrongGIN(){
        HttpClientErrorException thrown = assertThrows(HttpClientErrorException.NotFound.class,
                () -> restTemplate.getForEntity((baseUrl + "/000123456789/preference"), ProvidePreferencesData.class));

        assertTrue(thrown.getMessage().contains("business.error.002"));
    }

    @Test
    @DisplayName("Test 404 for wrong format GIN less12")
    void testError404WrongFormatGINless() {
        HttpClientErrorException thrown = assertThrows(HttpClientErrorException.NotFound.class,
                () -> restTemplate.getForEntity((baseUrl + "/123456789/preference"), ProvidePreferencesData.class));

        assertTrue(thrown.getMessage().contains("business.error.002"));
    }
}
