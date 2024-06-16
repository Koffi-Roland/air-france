package com.afklm.repind.msv.provide.contact.data.integrationtests;

import com.afklm.soa.stubs.r000347.v1.model.PostalAddressResponse;
import com.afklm.soa.stubs.r000347.v1.model.ProvideContactData;
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
import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
@TestPropertySource("/application.yml")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PostalAddressIntegrationTest {
    @LocalServerPort
    private int port;

    @Autowired
    JdbcTemplate jdbcTemplate;

    String baseUrl = "http://localhost:";
    RestTemplate restTemplate;

    private String GIN = "800018352323";

    @BeforeEach
    void setup() {
        restTemplate = new RestTemplate();
        baseUrl = baseUrl + port;
        log.info("application started with base URL:{} and port:{}", baseUrl, port);
        log.info("Initializing DB..");
    }

    @AfterEach
    void emptyData(){
        Integer totalRecords = jdbcTemplate.queryForObject("Select count(*) from Adr_post", Integer.class);
        log.info("Total Records in DB:{}", totalRecords);
    }

    @Test
    @DisplayName("Test Get Postal addresses by GIN")
    @Sql(scripts = "classpath:/initialize-data-postal-addresses.sql")
    void getPostalAddressesByGinTest() {
        ResponseEntity<List<PostalAddressResponse>> response = restTemplate.exchange((baseUrl + "/" + GIN + "/addresses"), HttpMethod.GET, null, new ParameterizedTypeReference<List<PostalAddressResponse>>() {}, Collections.emptyMap());
        assertAll(
                () -> assertNotNull(response.getBody()),
                () -> assertNotNull(response.getBody().get(0).getUsageAddress()),
                () -> assertNotNull(response.getBody().get(0).getSignature()),
                () -> assertNotNull(response.getBody().get(0).getPostalAddressProperties()),
                () -> assertNotNull(response.getBody().get(0).getPostalAddressContent()),

                () -> assertEquals("1", response.getBody().get(0).getUsageAddress().get(0).getUsageNumber()),
                () -> assertEquals("ISI", response.getBody().get(0).getUsageAddress().get(0).getApplicationCode()),

                () -> assertEquals("C", response.getBody().get(0).getSignature().get(0).getSignatureType()),
                () -> assertEquals("WEB/S08920", response.getBody().get(0).getSignature().get(0).getSignature()),
                () -> assertEquals("WEB", response.getBody().get(0).getSignature().get(0).getSignatureSite()),

                () -> assertEquals("M", response.getBody().get(0).getSignature().get(1).getSignatureType()),
                () -> assertEquals("ISI", response.getBody().get(0).getSignature().get(1).getSignature()),
                () -> assertEquals("AjoutUsage", response.getBody().get(0).getSignature().get(1).getSignatureSite()),

                () -> assertEquals("1", response.getBody().get(0).getPostalAddressProperties().getVersion()),
                () -> assertEquals("D", response.getBody().get(0).getPostalAddressProperties().getMediumCode()),
                () -> assertEquals("V", response.getBody().get(0).getPostalAddressProperties().getMediumStatus()),
                () -> assertEquals(true, response.getBody().get(0).getPostalAddressProperties().getIndicAdrNorm()),

                () -> assertEquals("AGIOS STEFANOS", response.getBody().get(0).getPostalAddressContent().getCity()),
                () -> assertEquals("14565", response.getBody().get(0).getPostalAddressContent().getZipCode()),
                () -> assertEquals("GR", response.getBody().get(0).getPostalAddressContent().getCountryCode()),
                () -> assertEquals("SAMPSOUDOS 8", response.getBody().get(0).getPostalAddressContent().getNumberAndStreet())
        );
    }

    @Test
    @DisplayName("Test 404 for wrong format GIN sup12")
    void testError404WrongFormatGINsup(){
        HttpClientErrorException thrown = assertThrows(HttpClientErrorException.NotFound.class,
                () -> restTemplate.getForEntity((baseUrl + "/1234567891234/addresses"), ProvideContactData.class));

        assertTrue(thrown.getMessage().contains("business.error.005"));
    }

    @Test
    @DisplayName("Test 404 for non existing GIN")
    void testError404WrongGIN(){
        HttpClientErrorException thrown = assertThrows(HttpClientErrorException.NotFound.class,
                () -> restTemplate.getForEntity((baseUrl + "/000123456789/addresses"), ProvideContactData.class));

        assertTrue(thrown.getMessage().contains("business.error.002"));
    }

    @Test
    @DisplayName("Test 404 for wrong format GIN less12")
    void testError404WrongFormatGINless(){
        HttpClientErrorException thrown = assertThrows(HttpClientErrorException.NotFound.class,
                () -> restTemplate.getForEntity((baseUrl + "/123456789/addresses"), ProvideContactData.class));

        assertTrue(thrown.getMessage().contains("business.error.002"));
    }
}
