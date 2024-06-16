package com.afklm.repind.msv.provide.contact.data.integrationtests;

import com.afklm.soa.stubs.r000347.v1.model.ProvideContactData;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@TestPropertySource("/application.yml")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class GeneralIntegrationTest {
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

    @Test
    @DisplayName("Test 404 for wrong format GIN sup12")
    void testError404WrongFormatGINsup(){
        HttpClientErrorException thrown = assertThrows(HttpClientErrorException.NotFound.class,
                () -> restTemplate.getForEntity((baseUrl + "/1234567891234"), ProvideContactData.class));

        assertTrue(thrown.getMessage().contains("business.error.005"));
    }

    @Test
    @DisplayName("Test 404 for wrong GIN")
    void testError404WrongGIN(){
        HttpClientErrorException thrown = assertThrows(HttpClientErrorException.NotFound.class,
                () -> restTemplate.getForEntity((baseUrl + "/123456789123"), ProvideContactData.class));

        assertTrue(thrown.getMessage().contains("business.error.004"));
    }
}

