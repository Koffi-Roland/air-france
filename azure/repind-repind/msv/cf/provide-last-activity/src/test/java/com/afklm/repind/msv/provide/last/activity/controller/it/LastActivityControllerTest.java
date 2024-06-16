package com.afklm.repind.msv.provide.last.activity.controller.it;

import com.afklm.repind.msv.provide.last.activity.Application;
import com.afklm.repind.msv.provide.last.activity.dto.LastActivityDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.junit.jupiter.api.Test;

import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration Test
 */
@SpringBootTest(classes = Application.class,
        webEnvironment = WebEnvironment.RANDOM_PORT)

public class LastActivityControllerTest {

    /**
     * Local port random
     */
    @LocalServerPort
    private int port;
    /**
     * Http client call - Rest template
     */
    @Autowired
    private TestRestTemplate restTemplate;

    private static final String BASE_URL = "http://localhost:";
    private static final String END_POINT = "/api-mgr/last-activity/";
    /**
     * Individual GIN number
     */
    private static final String GIN = "110000038701";
    /**
     * Table source where modification originated
     */
    private static final String TABLE_MODIFICATION = "Individuals_all";
    /**
     * Signature modification
     */
    private static final String SIGNATURE_MODIFICATION = "REPIND/IHM";
    /**
     * Signature site
     */
    private static final String MODIFICATION_SITE = "KLM";

    @Sql({"classpath:schema.sql", "classpath:data.sql"})
    @Test
    public void testGetLastActivityByGin()
    {
        // When
        LastActivityDto lastActivityDto = this.restTemplate
                .getForObject(BASE_URL + port + END_POINT + GIN, LastActivityDto.class);
        //Then
        assertAll(

                () -> assertEquals(lastActivityDto.getGin(), GIN),
                () -> assertEquals(lastActivityDto.getSourceModification(), TABLE_MODIFICATION),
                () -> assertEquals(lastActivityDto.getSignatureModification(), SIGNATURE_MODIFICATION),
                () -> assertEquals(lastActivityDto.getSiteModification(), MODIFICATION_SITE)

        );
    }

}
