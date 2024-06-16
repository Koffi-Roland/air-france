package com.afklm.spring.security.habile.proxy;

import com.afklm.spring.security.habile.proxy.controller.FourOFourController;
import com.afklm.spring.security.habile.proxy.security.HabileState;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.FluxExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;

import static com.afklm.spring.security.habile.proxy.BasicAuthFixture.USERNAME;
import static com.afklm.spring.security.habile.proxy.BasicAuthFixture.getBasicAuthHeader;
import static com.afklm.spring.security.habile.proxy.HabileProxyConstants.LOGOUT_DONE;
import static com.afklm.spring.security.habile.proxy.HabileProxyConstants.HABILE_LOGOUT_VALUE;
import static com.afklm.spring.security.habile.proxy.HabileSimulConstants.HABILE_SESSION_COOKIE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.http.HttpHeaders.ACCEPT;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

/**
 * Main test class of the application.
 *
 */
// @WebFluxTest(controllers = {AuthoritiesConfigurationController.class, UserController.class})
// @ActiveProfiles("single")
// @Import({ SimulTestConfiguration.class,
// SecurityConfig.class, SimulationAuthenticationProvider.class,
// HabileBasicAuthenticationSuccessHandler.class, AFKLFormAuthenticationSuccessHandler.class,
// ConfigurationService.class, HabileSessionManager.class,
// ProvideUserRightsAccessV10Impl.class, ProvideUserRightsAccessV20Impl.class})

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("single")
@Configuration
public class SpringSecurityHabileProxyApplicationTest {

    @Autowired
    private WebTestClient WebTestClient;

    /**
     * By default a 401 with some html to redirect is returned.<br/>
     * In test slicing the port randomly affected is not injected in configuration
     * so it is defaulted to 0.<br/>
     * Otherwise we could have used to get it and build the url
     */
    @Test
    public void testMockMeRequestingXml() {
        // @formatter:off
        WebTestClient.get()
            .uri("/mock/me")
            .header(ACCEPT, "application/xml")
            .exchange()
            .expectStatus()
            .isEqualTo(HttpStatus.UNAUTHORIZED)
            .expectBody(String.class).value(containsString("window.location = 'http:\\/\\/localhost:0\\/login';"));
        // @formatter:on
    }

    /**
     * By default a 401 with no content is returned
     */
    @Test
    public void testMockMeRequestingJson() {
        // @formatter:off
        WebTestClient.get()
            .uri("/mock/me")
            .header(ACCEPT, "application/json")
            .exchange()
            .expectStatus()
            .isEqualTo(HttpStatus.UNAUTHORIZED)
            .expectBody().isEmpty();
        // @formatter:on
    }

    /**
     * With credentials, "/mock/me" must return 200 and the user name.
     */
    @Test
    public void testMockMeUnprotectedEndpointAuthenticated() {
        // @formatter:off
        WebTestClient.get()
            .uri("/mock/me")
            .cookie(HABILE_SESSION_COOKIE, HabileState.LOGIN.name())
            .header(AUTHORIZATION, getBasicAuthHeader())
            .exchange()
            .expectStatus().isOk()
            .expectBody().json("{\"username\": \"" + USERNAME + "\"}");
        // @formatter:on
    }

    /**
     * Without credentials and configuration file, "/mock/authorities" must return
     * 200 and one entry.
     */
    @Test
    public void testMockAuthoritiesUnprotectedEndpointAnonymous() {
        WebTestClient.get()
            .uri("/mock/authorities")
            .exchange()
            .expectStatus()
            .isOk()
            .expectBody()
            .json("[{\"userId\":\"junit-userId\",\"password\":\"junit-password\",\"firstName\":\"junit-firstName\",\"lastName\":\"junit-lastName\",\"email\":\"junit-email\",\"profiles\":[\"junit-profile\"]}]");
    }

    /**
     * Without credentials, a business request delegation must fail with 401.
     */
    @Test
    public void testBusinessEndpointAnonymous() {
        WebTestClient.get().uri("/business/foo").exchange().expectStatus().isUnauthorized();
    }

    @Test
    public void testRoutingWithMissingServer() {
        WebTestClient.get()
            .uri("/business/foo")
            .cookie(HABILE_SESSION_COOKIE, HabileState.LOGGED.name())
            .header(AUTHORIZATION, getBasicAuthHeader())
            .exchange()
            .expectStatus()
            .isEqualTo(HttpStatus.OK)
            .expectBody(String.class)
            .value(containsString("<html><h1><font color='red'>Error contacting the backend</font></h1>"
                  + "Make sure it is available on:<ul><li>http://localhost:"))
            ;
    }

    // @Test
    public void testLogoutTriggersMockSessionLogout() {
        FluxExchangeResult<String> result = WebTestClient.post()
            .uri(HABILE_LOGOUT_VALUE)
            .cookie(HABILE_SESSION_COOKIE, HabileState.LOGGED.name())
            .header(AUTHORIZATION, getBasicAuthHeader())
            .exchange()
            .expectStatus()
            .isEqualTo(HttpStatus.FOUND)
            .expectHeader()
            .value("Location", is(LOGOUT_DONE))
            .returnResult(String.class);
        assertThat(result.getResponseCookies().getFirst(HABILE_SESSION_COOKIE).getValue()).isEqualTo(HabileState.LOGOUT.name());
    }

    // @Test
    public void testMockMeRequiresBasicAuthAfterLogoutWithMockSessionLogout() {
        // @formatter:off
        WebTestClient.get()
            .uri("/mock/me")
            .cookie(HABILE_SESSION_COOKIE, HabileState.LOGOUT.name())
            .header(AUTHORIZATION, getBasicAuthHeader())
            .exchange()
            .expectStatus().isUnauthorized()
            .expectBody(String.class).value(containsString("window.location = 'http:\\/\\/localhost:8001\\/login';"));
        // @formatter:on
    }

    /**
     * In test slicing the port randomly affected is not injected in configuration
     * so it is defaulted to 0.<br/>
     * Otherwise we could have used to get it and build the url
     */
    @Test
    public void testMockMeRequiresReconnectionAfterLogout() {
        // @formatter:off
        WebTestClient.get()
            .uri("/mock/me")
            .cookie(HABILE_SESSION_COOKIE, HabileState.LOGOUT.name())
            .exchange()
            .expectStatus().isUnauthorized()
            .expectBody(String.class).value(containsString("window.location = 'http:\\/\\/localhost:0\\/login';"));
        // @formatter:on
    }

}
