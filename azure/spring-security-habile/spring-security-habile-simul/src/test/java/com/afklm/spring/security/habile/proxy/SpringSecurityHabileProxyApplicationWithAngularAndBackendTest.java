package com.afklm.spring.security.habile.proxy;

import com.afklm.spring.security.habile.proxy.controller.FourOFourController;
import com.afklm.spring.security.habile.proxy.security.HabileState;
import mockwebserver3.MockResponse;
import mockwebserver3.MockWebServer;
import mockwebserver3.RecordedRequest;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.io.IOException;

import static com.afklm.spring.security.habile.GenericHeaders.X_FORWARDED_USER;
import static com.afklm.spring.security.habile.GenericHeaders.X_LOGOUT_URL;
import static com.afklm.spring.security.habile.proxy.BasicAuthFixture.USERNAME;
import static com.afklm.spring.security.habile.proxy.BasicAuthFixture.getBasicAuthHeader;
import static com.afklm.spring.security.habile.proxy.HabileProxyConstants.HABILE_LOGOUT_VALUE;
import static com.afklm.spring.security.habile.proxy.HabileSimulConstants.HABILE_SESSION_COOKIE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN;
import static org.springframework.security.web.server.header.XFrameOptionsServerHttpHeadersWriter.X_FRAME_OPTIONS;

/**
 * Class that will test the proxy when an angular cs present, so we have
 * 2 different routes to check: biz backend and angular.<br/>
 * It also checks the Location URL is rewritten in order to target 
 * proxy instead of the backend server.
 * 
 * @author TECC
 *
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles({"withAngular", "angular"})
@Configuration
@TestMethodOrder(OrderAnnotation.class)
public class SpringSecurityHabileProxyApplicationWithAngularAndBackendTest {

    /**
     * Name of the system property containing the port of the backend behind the
     * Habile proxy. Used by the proxy application.yml.
     */
    private static final String HABILE_PROXY_BACKEND_URL = "habile.proxy.backend.url";

    /**
     * Name of the system property containing the port of the angular ss behind the
     * Habile proxy. Used by the proxy application.yml.
     */
    private static final String HABILE_PROXY_ANGULAR_URL = "habile.proxy.angular.url";

    private static final String ANGULAR_CONTENT = "{\"msg\": \"Hello from Angular\"}";
    private static final String BUSINESS_CONTENT = "{\"msg\": \"Hello\"}";

    private static MockWebServer mockWebServer;
    private static MockWebServer mockAngularServer;
    
    @LocalServerPort
    private int serverPort;

    @Autowired
    private WebTestClient WebTestClient;

    @BeforeAll
    public static void startServer() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start(); // Uses a random port
        mockAngularServer = new MockWebServer();
        mockAngularServer.start(); // Uses a random port
        System.setProperty(HABILE_PROXY_BACKEND_URL, "http://localhost:" + mockWebServer.getPort());
        System.setProperty(HABILE_PROXY_ANGULAR_URL, "http://localhost:" + mockAngularServer.getPort());
        mockWebServer.enqueue(new MockResponse().setBody(BUSINESS_CONTENT).setResponseCode(200));
        mockWebServer.enqueue(new MockResponse().setHeader("Location", "http://localhost:8080/toto").setResponseCode(302));
        mockAngularServer.enqueue(new MockResponse().setBody(ANGULAR_CONTENT));
    }

    @AfterAll
    public static void stopServer() throws IOException {
        mockWebServer.close();
        mockWebServer = null;
        mockAngularServer.close();
        mockAngularServer = null;
    }

    /**
     * With credentials, a business request delegation must succeed 200 and adds the
     * "x-" headers.
     */
    @Test
    @DisplayName("Test backend endpoint authenticated")
    @Order(1)
    public void testBusinessEndpointAuthenticated() throws Exception {
        WebTestClient.get()
            .uri("/business/foo")
            .cookie(HABILE_SESSION_COOKIE, HabileState.LOGGED.name())
            .header(AUTHORIZATION, getBasicAuthHeader())
            .exchange()
            .expectStatus()
            .isOk()
            .expectBody()
            .json(BUSINESS_CONTENT);

        RecordedRequest request = mockWebServer.takeRequest();
        assertThat(USERNAME).isEqualTo(request.getHeader(X_FORWARDED_USER));
        assertThat(request.getHeader(X_LOGOUT_URL)).endsWith(HABILE_LOGOUT_VALUE);
    }
    
    /**
     * Test the location header is overwritten in order to point to the simul proxy
     */
    @Test
    @DisplayName("Test Location header is rewritten and x-frame-options header")
    @Order(2)
    public void testLocationAndXFrameHeaderIsRewritenAndXFrameOptionsHeader() {
        WebTestClient.get()
                .uri("/business/foo")
                .cookie(HABILE_SESSION_COOKIE, HabileState.LOGGED.name())
                .header(AUTHORIZATION, getBasicAuthHeader())
                .exchange()
                .expectStatus()
                .is3xxRedirection()
                .expectHeader().location("http://localhost:" + serverPort + "/toto")
                .expectHeader().valueEquals(X_FRAME_OPTIONS, SAMEORIGIN.toString());
    }

    /**
     * With credentials, a business request delegation must succeed 200 and adds the
     * "x-" headers.
     */
    @Test
    @DisplayName("Test angular endpoint authenticated")
    @Order(2)
    public void testAngularEndpointAuthenticated() throws Exception {
        WebTestClient.get()
            .uri("/")
            .cookie(HABILE_SESSION_COOKIE, HabileState.LOGGED.name())
            .header(AUTHORIZATION, getBasicAuthHeader())
            .exchange()
            .expectStatus()
            .isOk()
            .expectBody()
            .json(ANGULAR_CONTENT);

        RecordedRequest request = mockAngularServer.takeRequest();
        assertThat(USERNAME).isEqualTo(request.getHeader(X_FORWARDED_USER));
        assertThat(request.getHeader(X_LOGOUT_URL)).endsWith(HABILE_LOGOUT_VALUE);
    }

    @Test
    @DisplayName("Test the fallback URLs")
    @Order(3)
    void testTheFallbackUrLs() throws IOException {
        mockWebServer.shutdown();
        mockAngularServer.shutdown();

        WebTestClient.get()
                .uri("/business/foo")
                .cookie(HABILE_SESSION_COOKIE, HabileState.LOGGED.name())
                .header(AUTHORIZATION, getBasicAuthHeader())
                .exchange()
                .expectStatus()
                .isEqualTo(HttpStatus.OK)
                .expectBody()
                .xml(FourOFourController.NOT_FOUND_HTML_TEMPLATE
                        .replaceAll("#target_system#", "backend")
                        .replaceAll("#exposed_endpoints#", "<ul><li>http://localhost:" + mockWebServer.getPort() + "/business/foo</li></ul>"));

        WebTestClient.get()
                .uri("/")
                .cookie(HABILE_SESSION_COOKIE, HabileState.LOGGED.name())
                .header(AUTHORIZATION, getBasicAuthHeader())
                .exchange()
                .expectStatus()
                .isEqualTo(HttpStatus.OK)
                .expectBody()
                .xml(FourOFourController.NOT_FOUND_HTML_TEMPLATE
                        .replaceAll("#target_system#", "frontend")
                        .replaceAll("#exposed_endpoints#", "<ul><li>http://localhost:" + mockAngularServer.getPort() + "</li></ul>"));
    }
}
