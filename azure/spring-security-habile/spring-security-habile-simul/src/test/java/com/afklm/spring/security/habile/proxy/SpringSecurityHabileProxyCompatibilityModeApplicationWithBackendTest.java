package com.afklm.spring.security.habile.proxy;

import static com.afklm.spring.security.habile.GenericHeaders.AF_USER;
import static com.afklm.spring.security.habile.GenericHeaders.SM_AUTHTYPE;
import static com.afklm.spring.security.habile.GenericHeaders.SM_LOGOUT;
import static com.afklm.spring.security.habile.GenericHeaders.SM_USER;
import static com.afklm.spring.security.habile.GenericHeaders.SM_USERDN;
import static com.afklm.spring.security.habile.GenericHeaders.SM_USER_ANONYMOUS;
import static com.afklm.spring.security.habile.GenericHeaders.X_ACCESS_TOKEN;
import static com.afklm.spring.security.habile.GenericHeaders.X_DISPLAY_NAME;
import static com.afklm.spring.security.habile.GenericHeaders.X_FORWARDED_AUTHLEVEL;
import static com.afklm.spring.security.habile.GenericHeaders.X_FORWARDED_USER;
import static com.afklm.spring.security.habile.GenericHeaders.X_LOGOUT_URL;
import static com.afklm.spring.security.habile.GlobalConfiguration.EMPTY_STRING;
import static com.afklm.spring.security.habile.proxy.BasicAuthFixture.USERNAME;
import static com.afklm.spring.security.habile.proxy.BasicAuthFixture.getBasicAuthHeader;
import static com.afklm.spring.security.habile.proxy.HabileProxyConstants.HABILE_LOGOUT_VALUE;
import static com.afklm.spring.security.habile.proxy.HabileProxyConstants.SM_AUTHTYPE_VALUE;
import static com.afklm.spring.security.habile.proxy.HabileProxyConstants.X_FORWARDED_AUTHLEVEL_VALUE;
import static com.afklm.spring.security.habile.proxy.HabileSimulConstants.HABILE_SESSION_COOKIE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

import java.io.IOException;
import java.time.Duration;
import java.util.Locale;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.afklm.spring.security.habile.proxy.security.HabileState;

import mockwebserver3.MockResponse;
import mockwebserver3.MockWebServer;
import mockwebserver3.RecordedRequest;
import reactor.core.publisher.Flux;

/**
 * Main test class of the application.
 * This class tests the proxy when no specific configuration is present
 * so only backend default routing.
 *
 * WARNING, this class currently needs to be the first @SpringBootTest executed
 * class, because the System.setProperty(HABILE_PROXY_BACKEND_PORT, ...) must be
 * executed before the Zuul initialization.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles({"test", "modeSmCompatibility"}) // Just to trigger another context and avoid habile.proxy.backend.port already consumed
@Configuration
public class SpringSecurityHabileProxyCompatibilityModeApplicationWithBackendTest {

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
        Flux.range(0, 5)
                .subscribe(i -> mockWebServer.enqueue(new MockResponse().setBody(BUSINESS_CONTENT)));
    }

    @AfterAll
    public static void stopServer() throws IOException {
        mockWebServer.close();
        mockWebServer = null;
    }

    /**
     * With credentials, a business request delegation must succeed 200 and adds the
     * "SM_" headers.
     */
    @Test
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

        assertThat(request.getHeader(SM_LOGOUT)).endsWith(HABILE_LOGOUT_VALUE);

        assertThat(USERNAME).isEqualTo(request.getHeader(X_FORWARDED_USER));
        assertThat(request.getHeader(X_DISPLAY_NAME)).isNull();
        assertThat(request.getHeader(X_ACCESS_TOKEN)).isNull();
        assertThat(request.getHeader(X_LOGOUT_URL)).endsWith(HABILE_LOGOUT_VALUE);
        assertThat(X_FORWARDED_AUTHLEVEL_VALUE).isEqualTo(request.getHeader(X_FORWARDED_AUTHLEVEL));
        assertThat(USERNAME).isEqualTo(request.getHeader(SM_USER));
        assertThat(USERNAME).isEqualTo(request.getHeader(AF_USER));
        assertThat(SM_AUTHTYPE_VALUE).isEqualTo(request.getHeader(SM_AUTHTYPE));
        assertThat(request.getHeader(SM_LOGOUT)).endsWith(HABILE_LOGOUT_VALUE);
    }

    @Test
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
        assertThat(SM_USER_ANONYMOUS).isEqualTo(request.getHeader(X_FORWARDED_USER));
        assertThat(request.getHeader(X_DISPLAY_NAME)).isNull();
        assertThat(request.getHeader(X_ACCESS_TOKEN)).isNull();
        assertThat(EMPTY_STRING).isEqualTo(request.getHeader(X_LOGOUT_URL));
        assertThat(request.getHeader(X_FORWARDED_AUTHLEVEL)).isNull();
        assertThat(SM_USER_ANONYMOUS).isEqualTo(request.getHeader(SM_USER));
        assertThat(SM_USER_ANONYMOUS).isEqualTo(request.getHeader(SM_USERDN));
        assertThat(request.getHeader(SM_AUTHTYPE)).isEqualTo(request.getHeader(SM_USERDN));
        assertThat(request.getHeader(SM_LOGOUT)).isNull();
    }

    @Test
    public void testAnonymousAccessWithAuthenticatedUser() throws InterruptedException {
        WebTestClient.mutate()
            .responseTimeout(Duration.ofMillis(300000))
            .build()
            .get()
            .uri("/anonymous/foo")
            .cookie(HABILE_SESSION_COOKIE, HabileState.LOGGED.name())
            .header(AUTHORIZATION, getBasicAuthHeader())
            .exchange()
            .expectStatus()
            .isOk()
            .expectBody()
            .json(BUSINESS_CONTENT);

        RecordedRequest request = mockWebServer.takeRequest();
        assertThat(USERNAME).isEqualTo(request.getHeader(SM_USER));
        assertThat(request.getHeader(SM_LOGOUT)).endsWith(HABILE_LOGOUT_VALUE);
        assertThat(USERNAME).isEqualTo(request.getHeader(X_FORWARDED_USER));
        assertThat(request.getHeader(X_DISPLAY_NAME)).isNull();
        assertThat(request.getHeader(X_ACCESS_TOKEN)).isNull();
        assertThat(request.getHeader(X_LOGOUT_URL)).endsWith(HABILE_LOGOUT_VALUE);
        assertThat(request.getHeader(X_FORWARDED_AUTHLEVEL)).isNull();
        assertThat(USERNAME).isEqualTo(request.getHeader(SM_USER));
        assertThat(request.getHeader(SM_LOGOUT)).endsWith(HABILE_LOGOUT_VALUE);
        assertThat(USERNAME).isEqualTo(request.getHeader(AF_USER));
        assertThat(request.getHeader(SM_AUTHTYPE)).isNull();
    }

    @Test
    public void testPublicWithoutAuthenticatedUser() throws InterruptedException {
        WebTestClient.mutate()
            .responseTimeout(Duration.ofMillis(300000))
            .build()
            .get()
            .uri("/ws/foo")
            .exchange()
            .expectStatus()
            .isOk()
            .expectBody()
            .json(BUSINESS_CONTENT);

        RecordedRequest request = mockWebServer.takeRequest();
        assertThat(request.getHeader(X_FORWARDED_USER)).isNull();
        assertThat(request.getHeader(X_DISPLAY_NAME)).isNull();
        assertThat(request.getHeader(X_ACCESS_TOKEN)).isNull();
        assertThat(request.getHeader(X_LOGOUT_URL)).isNull();
        assertThat(request.getHeader(X_FORWARDED_AUTHLEVEL)).isNull();
        assertThat(request.getHeader(SM_USER)).isNull();
        assertThat(request.getHeader(SM_LOGOUT)).isNull();
        assertThat(request.getHeader(SM_AUTHTYPE)).isNull();
        assertThat(request.getHeader(AF_USER)).isNull();
    }

    /**
     * Ensures that a Websocket upgrade connection leads to an empty SM_USER in ouput of the proxy
     * @throws InterruptedException
     */
    @Test
    @DisplayName("test Websocket upgrade generates correct headers")
    void testWebsocketUpgradeGeneratesCorrectHeaders() throws InterruptedException {
        WebTestClient.mutate()
                .responseTimeout(Duration.ofMillis(300000))
                .build()
                .get()
                .uri("/anonymous/foo")
                .cookie(HABILE_SESSION_COOKIE, HabileState.LOGGED.name())
                .header(AUTHORIZATION, getBasicAuthHeader())
                .header(HttpHeaders.CONNECTION, HttpHeaders.UPGRADE)
                .header(HttpHeaders.UPGRADE, "websocket")
                .header(HttpHeaders.ORIGIN, "http://localhost:8001")
                .header(HttpHeaders.HOST, "localhost:8001")
                .header("Sec-WebSocket-Key", "SGVsbG8sIHdvcmxkIQ==")
                .exchange()
                ;

        System.out.println(mockWebServer.getPort());
        RecordedRequest request = mockWebServer.takeRequest();
        assertThat(HttpHeaders.UPGRADE.toLowerCase(Locale.ROOT)).isEqualTo(request.getHeader(HttpHeaders.CONNECTION));
        assertThat("websocket").isEqualTo(request.getHeader(HttpHeaders.UPGRADE));
        // the Sec-WebSocket-Key is not equal with the one provided because there will be two channels
        // one from client to proxy and one from proxy to backend
        assertThat(request.getHeader("Sec-WebSocket-Key")).isNotNull();
        // same here the origin reprensents the proxy
        assertThat("localhost:"+mockWebServer.getPort()).isEqualTo(request.getHeader(HttpHeaders.HOST));
        assertThat("http://localhost:8001").isEqualTo(request.getHeader(HttpHeaders.ORIGIN));
        assertThat("").isEqualTo(request.getHeader(SM_USER));
    }
}
