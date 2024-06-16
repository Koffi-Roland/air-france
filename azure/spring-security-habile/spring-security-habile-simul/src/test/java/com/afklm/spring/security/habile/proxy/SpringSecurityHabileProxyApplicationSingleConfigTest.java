package com.afklm.spring.security.habile.proxy;

import com.afklm.spring.security.habile.proxy.security.HabileState;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.util.Base64Utils;

import static com.afklm.spring.security.habile.proxy.HabileSimulConstants.HABILE_SESSION_COOKIE;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

/**
 * Main test class of the application.
 *
 * WARNING, this class currently needs to be the first @SpringBootTest executed class,
 * because the System.setProperty(HABILE_PROXY_BACKEND_PORT, ...) must be executed
 * before the Zuul initialization.
 */
@ActiveProfiles("single")
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

// @WebFluxTest
// @Import({ConfigurationService.class, HabileSessionManager.class,
// ProvideUserRightsAccessV10Impl.class, ProvideUserRightsAccessV20Impl.class})
public class SpringSecurityHabileProxyApplicationSingleConfigTest {

    @Autowired
    private WebTestClient WebTestClient;

    @Test
    public void testMockMeWithConfigAndValidCredentials() throws Exception {
        WebTestClient.get()
            .uri("/mock/me")
            .cookie(HABILE_SESSION_COOKIE, HabileState.LOGGED.name())
            .header(AUTHORIZATION, getConfigAuthHeader())
            .exchange()
            .expectStatus()
            .isOk()
            .expectBody()
            .json("{\"username\": \"" + "junit-userId" + "\"}");
    }

    @Test
    public void testMockMeWithConfigAndInvalidUsername() throws Exception {
        WebTestClient.get()
            .uri("/mock/me")
            .cookie(HABILE_SESSION_COOKIE, HabileState.LOGGED.name())
            .header(AUTHORIZATION, getConfigAuthHeaderWithInvalidUsername())
            .exchange()
            .expectStatus()
            .isUnauthorized();
    }

    @Test
    public void testMockMeWithConfigAndInvalidPassword() throws Exception {
        WebTestClient.get()
            .uri("/mock/me")
            .cookie(HABILE_SESSION_COOKIE, HabileState.LOGGED.name())
            .header(AUTHORIZATION, getConfigAuthHeaderWithInvalidPassword())
            .exchange()
            .expectStatus()
            .isUnauthorized();
    }

    private String getConfigAuthHeader() {
        return "Basic " + Base64Utils.encodeToString(("junit-userId" + ":" + "junit-password").getBytes());
    }

    private String getConfigAuthHeaderWithInvalidPassword() {
        return "Basic " + Base64Utils.encodeToString(("junit-userId" + ":" + "junit-invalid-password").getBytes());
    }

    private String getConfigAuthHeaderWithInvalidUsername() {
        return "Basic " + Base64Utils.encodeToString(("junit-invalid-userId" + ":" + "junit-password").getBytes());
    }
}
