package com.afklm.spring.security.habile.proxy;

import mockwebserver3.MockResponse;
import mockwebserver3.MockWebServer;
import mockwebserver3.RecordedRequest;

import com.afklm.spring.security.habile.proxy.security.HabileState;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.io.IOException;
import java.time.Duration;

import static com.afklm.spring.security.habile.GenericHeaders.SM_AUTHTYPE;
import static com.afklm.spring.security.habile.GenericHeaders.SM_AUTHTYPE_ANONYMOUS;
import static com.afklm.spring.security.habile.GenericHeaders.SM_LOGOUT;
import static com.afklm.spring.security.habile.GenericHeaders.SM_USER;
import static com.afklm.spring.security.habile.GenericHeaders.SM_USERDN;
import static com.afklm.spring.security.habile.GenericHeaders.SM_USER_ANONYMOUS;
import static com.afklm.spring.security.habile.proxy.BasicAuthFixture.getBasicAuthHeader;
import static com.afklm.spring.security.habile.proxy.HabileProxyConstants.HABILE_LOGOUT_VALUE;
import static com.afklm.spring.security.habile.proxy.HabileSimulConstants.HABILE_SESSION_COOKIE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

/**
 * Main test class of the application.
 * This class tests the proxy when the authForm is enabled.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles({"test", "authForm"})
@Configuration
public class SpringSecurityHabileProxyApplicationWithFormAuth {

    /**
     * Name of the system property containing the port of the backend behind the
     * Habile proxy. Used by the proxy application.yml.
     */
    private static final String HABILE_PROXY_BACKEND_URL = "habile.proxy.backend.url";

    private static final String BUSINESS_CONTENT = "{\"msg\": \"Hello\"}";

    private static MockWebServer mockWebServer;

    @Autowired
    private WebTestClient WebTestClient;

    @BeforeAll
    public static void startServer() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start(); // Uses a random port
        System.setProperty(HABILE_PROXY_BACKEND_URL, "http://localhost:" + mockWebServer.getPort());
        mockWebServer.enqueue(new MockResponse().setBody(BUSINESS_CONTENT));
    }

    @AfterAll
    public static void stopServer() throws IOException {
        mockWebServer.close();
        mockWebServer = null;
    }

    /**
     * With credentials as basic auth should not work and redirect
     * on login page.
     */
    // @Test
    public void testBasicAuthDoesNotWork() throws Exception {
        WebTestClient.get()
            .uri("/business/foo")
            .cookie(HABILE_SESSION_COOKIE, HabileState.LOGGED.name())
            .header(AUTHORIZATION, getBasicAuthHeader())
            .exchange()
            .expectStatus()
            .isEqualTo(HttpStatus.OK)
            .expectHeader()
            .valueEquals(HttpHeaders.LOCATION, "/login")
            .expectBody()
            .isEmpty();
    }

    /**
     * Tests that the login page is the one provided by the proxy.
     */
    @Test
    public void testLoginEndpoint() throws Exception {
        WebTestClient.get()
            .uri("/login")
            .cookie(HABILE_SESSION_COOKIE, HabileState.LOGGED.name())
            .header(AUTHORIZATION, getBasicAuthHeader())
            .exchange()
            .expectStatus()
            .isOk()
            .expectBody(String.class)
            .value(containsString("Habile<span class=\"habile-header-subtitle\">Simulation</span>"));
    }

    // @Test
    public void testAnonymousAccess() throws InterruptedException {
        WebTestClient.mutate()
            .responseTimeout(Duration.ofMillis(300000))
            .build()
            .get()
            .uri("/anonymous/foo")
            .exchange()
            .expectStatus()
            .isOk()
            .expectBody()
            .json(BUSINESS_CONTENT);

        RecordedRequest request = mockWebServer.takeRequest();
        assertThat(SM_USER_ANONYMOUS).isEqualTo(request.getHeader(SM_USER));
        assertThat(request.getHeader(SM_USER)).isEqualTo(request.getHeader(SM_USERDN));
        assertThat(SM_AUTHTYPE_ANONYMOUS).isEqualTo(request.getHeader(SM_AUTHTYPE));
        assertThat(request.getHeader(SM_LOGOUT)).endsWith(HABILE_LOGOUT_VALUE);
    }

}
