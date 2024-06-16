package com.afklm.spring.security.habile.proxy.controller;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.afklm.spring.security.habile.proxy.JwtBuilder;
import com.afklm.spring.security.habile.proxy.model.UserInformation;
import com.afklm.spring.security.habile.proxy.service.ConfigurationService;

/**
 * Test for OidcController
 * 
 * @author m408461
 *
 */
@WebFluxTest(OidcController.class)
public class OidcControllerTest {
    
    private static final int jwtTtl = 3600;
    private static final String issuerUrl = "http://localhost:8001";

    @Autowired
    private WebTestClient webClient;

    @MockBean
    private ConfigurationService authoritiesConfigurationService;

    @Test
    @WithMockUser
    public void testNominal() throws Exception {
        UserInformation response = new UserInformation();
        response.setFirstName("john");
        response.setLastName("doe");
        response.setProfiles(Arrays.asList("ROLE_TEST"));
        response.setEmail("john.doe@anonymous.org");
        Mockito.when(authoritiesConfigurationService.getUserInformation("john")).thenReturn(response );
        webClient.get()
            .uri("/idp/userinfo.openid")
            .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
            .header(HttpHeaders.AUTHORIZATION, "Bearer: " + JwtBuilder.createJWT("john", issuerUrl, jwtTtl))
            .exchange()
            .expectStatus().isOk()
            .expectBody()
            .jsonPath("$.name").isEqualTo("doe john")
            .jsonPath("$.profile.size()").isEqualTo(1)
            .jsonPath("$.profile").isEqualTo("ROLE_TEST");
    }
    
    @Test
    @WithMockUser
    public void testUnknownUser() throws Exception {
        Mockito.when(authoritiesConfigurationService.getUserInformation("john")).thenReturn(null);
        webClient.get()
            .uri("/idp/userinfo.openid")
            .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
            .header(HttpHeaders.AUTHORIZATION, "Bearer: " + JwtBuilder.createJWT("john", issuerUrl, jwtTtl))
            .exchange()
            .expectStatus().isUnauthorized();
    }
}
