package com.afklm.spring.security.habile.proxy.controller;

import com.afklm.spring.security.habile.proxy.HabileProxyConstants;
import com.afklm.spring.security.habile.proxy.security.HabileSessionManager;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.reactive.server.WebTestClient;

import static com.afklm.spring.security.habile.proxy.HabileProxyConstants.LOGOUT_DONE;

/**
 * Test for LogoutController
 * 
 * @author m405991
 *
 */
@WebFluxTest(LogoutController.class)
public class LogoutControllerTest {

    @Autowired
    private WebTestClient webClient;

    @MockBean
    private HabileSessionManager habileSessionManager;

    @Test
    @WithMockUser
    public void testEndpoint() throws Exception {
        webClient.get()
            .uri(LOGOUT_DONE)
            .exchange()
            .expectStatus()
            .isOk()
            .expectBody(String.class)
                .isEqualTo(HabileProxyConstants.LOGOUT_BODY);
    }

}
