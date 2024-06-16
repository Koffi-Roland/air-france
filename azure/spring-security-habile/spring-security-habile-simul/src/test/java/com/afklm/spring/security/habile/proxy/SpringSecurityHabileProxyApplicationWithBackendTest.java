package com.afklm.spring.security.habile.proxy;

import static com.afklm.spring.security.habile.GenericHeaders.AF_USER;
import static com.afklm.spring.security.habile.GenericHeaders.SM_AUTHTYPE;
import static com.afklm.spring.security.habile.GenericHeaders.SM_LOGOUT;
import static com.afklm.spring.security.habile.GenericHeaders.SM_USER;
import static com.afklm.spring.security.habile.GenericHeaders.X_ACCESS_TOKEN;
import static com.afklm.spring.security.habile.GenericHeaders.X_DISPLAY_NAME;
import static com.afklm.spring.security.habile.GenericHeaders.X_FORWARDED_AUTHLEVEL;
import static com.afklm.spring.security.habile.GenericHeaders.X_FORWARDED_USER;
import static com.afklm.spring.security.habile.GenericHeaders.X_LOGOUT_URL;
import static com.afklm.spring.security.habile.GlobalConfiguration.EMPTY_STRING;
import static com.afklm.spring.security.habile.proxy.BasicAuthFixture.USERNAME;
import static com.afklm.spring.security.habile.proxy.BasicAuthFixture.getBasicAuthHeader;
import static com.afklm.spring.security.habile.proxy.HabileProxyConstants.HABILE_LOGOUT_VALUE;
import static com.afklm.spring.security.habile.proxy.HabileProxyConstants.X_DISPLAY_NAME_VALUE;
import static com.afklm.spring.security.habile.proxy.HabileProxyConstants.X_FORWARDED_AUTHLEVEL_VALUE;
import static com.afklm.spring.security.habile.proxy.HabileSimulConstants.HABILE_SESSION_COOKIE;
import static com.afklm.spring.security.habile.proxy.HabileSimulConstants.PING_JWT_SCOPES;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.reactive.server.WebTestClient.ResponseSpec;

import com.afklm.spring.security.habile.oidc.JwtUtils;
import com.afklm.spring.security.habile.proxy.security.HabileState;

import mockwebserver3.MockResponse;
import mockwebserver3.MockWebServer;
import mockwebserver3.RecordedRequest;

/**
 * Main test class of the application.
 * This class tests the proxy when no specific configuration is present
 * so only backend default routing.
 *
 * WARNING, this class currently needs to be the first @SpringBootTest executed
 * class, because the System.setProperty(HABILE_PROXY_BACKEND_PORT, ...) must be
 * executed before the Zuul initialization.
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles({"test"}) // Just to trigger another context and avoid habile.proxy.backend.port already consumed
@Configuration
public class SpringSecurityHabileProxyApplicationWithBackendTest {

    /**
     * Name of the system property containing the port of the backend behind the
     * Habile proxy. Used by the proxy application.yml.
     */
    private static final String HABILE_PROXY_BACKEND_URL = "habile.proxy.backend.url";
    /**
     * Name of the system property containing the hostname that has to be used for the issuer value.
     */
    private static final String HABILE_PROXY_HOSTNAME = "habile.proxy.hostname";
    
    private static final String BUSINESS_CONTENT = "{\"msg\": \"Hello\"}";
    private static final String MOCKED_ISSUER_HOSTNAME = "http://my-fake-hostname";

    private static MockWebServer mockWebServer;

    @Autowired
    private WebTestClient WebTestClient;

    @BeforeAll
    public static void startServer() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start(); // Uses a random port
        System.setProperty(HABILE_PROXY_BACKEND_URL, "http://localhost:" + mockWebServer.getPort());
        System.setProperty(HABILE_PROXY_HOSTNAME, MOCKED_ISSUER_HOSTNAME);
        mockWebServer.enqueue(new MockResponse().setBody(BUSINESS_CONTENT));
        mockWebServer.enqueue(new MockResponse().setBody(BUSINESS_CONTENT));
        mockWebServer.enqueue(new MockResponse().setBody(BUSINESS_CONTENT));
        mockWebServer.enqueue(new MockResponse().setBody(BUSINESS_CONTENT));
    }

    @AfterAll
    public static void stopServer() throws IOException {
        mockWebServer.close();
        mockWebServer = null;
    }

    /**
     * With credentials, a business request delegation must succeed 200.
     */
    @Test
    public void testBusinessEndpointAuthenticated() throws Exception {
        ResponseSpec response = WebTestClient.get()
            .uri("/business/foo")
            .cookie(HABILE_SESSION_COOKIE, HabileState.LOGGED.name())
            .header(AUTHORIZATION, getBasicAuthHeader())
            .exchange();

        response.expectStatus()
            .isOk()
            .expectBody()
            .json(BUSINESS_CONTENT);

        RecordedRequest request = mockWebServer.takeRequest();
        String accessToken = request.getHeader(X_ACCESS_TOKEN);
        assertThat(USERNAME).isEqualTo(request.getHeader(X_FORWARDED_USER));
        assertThat(X_DISPLAY_NAME_VALUE).isEqualTo(request.getHeader(X_DISPLAY_NAME));
        assertThat(accessToken).isNotNull();
        assertThat(request.getHeader(X_LOGOUT_URL)).contains(MOCKED_ISSUER_HOSTNAME);
        assertThat(request.getHeader(X_LOGOUT_URL)).endsWith(HABILE_LOGOUT_VALUE);
        assertThat(X_FORWARDED_AUTHLEVEL_VALUE).isEqualTo(request.getHeader(X_FORWARDED_AUTHLEVEL));
        assertThat(request.getHeader(SM_USER)).isNull();
        assertThat(request.getHeader(AF_USER)).isNull();
        assertThat(request.getHeader(SM_AUTHTYPE)).isNull();
        assertThat(request.getHeader(SM_LOGOUT)).isNull();

        Optional<Object> scopes = JwtUtils.getElementFromBody(accessToken, JwtUtils.SCOPE_KEY);
        assertThat(scopes.isPresent()).isTrue();
        assertThat(scopes.get()).isInstanceOf(List.class);
        assertThat((List<String>)scopes.get()).containsExactlyElementsOf(PING_JWT_SCOPES);
        Optional<String> issuer = JwtUtils.getStringElementFromBody(accessToken, JwtUtils.ISSUER_KEY);
        assertThat(issuer.get()).contains(MOCKED_ISSUER_HOSTNAME);

        response.expectCookie()
            .maxAge("MOCKSMSESSION", Duration.ofSeconds(601));
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
        assertThat(EMPTY_STRING).isEqualTo(request.getHeader(X_FORWARDED_USER));
        assertThat(EMPTY_STRING).isEqualTo(request.getHeader(X_DISPLAY_NAME));
        assertThat(EMPTY_STRING).isEqualTo(request.getHeader(X_ACCESS_TOKEN));
        assertThat(EMPTY_STRING).isEqualTo(request.getHeader(X_LOGOUT_URL));
        assertThat(EMPTY_STRING).isEqualTo(request.getHeader(X_FORWARDED_AUTHLEVEL));
        assertThat(request.getHeader(SM_USER)).isNull();
        assertThat(request.getHeader(AF_USER)).isNull();
        assertThat(request.getHeader(SM_AUTHTYPE)).isNull();
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
        assertThat(USERNAME).isEqualTo(request.getHeader(X_FORWARDED_USER));
        assertThat(X_DISPLAY_NAME_VALUE).isEqualTo(request.getHeader(X_DISPLAY_NAME));
        assertThat(request.getHeader(X_ACCESS_TOKEN)).isNotNull();
        assertThat(request.getHeader(X_LOGOUT_URL)).endsWith(HABILE_LOGOUT_VALUE);
        assertThat(X_FORWARDED_AUTHLEVEL_VALUE).isEqualTo(request.getHeader(X_FORWARDED_AUTHLEVEL));
        assertThat(request.getHeader(SM_USER)).isNull();
        assertThat(request.getHeader(AF_USER)).isNull();
        assertThat(request.getHeader(SM_AUTHTYPE)).isNull();
        assertThat(request.getHeader(SM_LOGOUT)).isNull();
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

}