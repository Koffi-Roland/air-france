package com.afklm.spring.security.habile;

import static com.afklm.spring.security.habile.GlobalConfiguration.ANONYMOUS;
import static com.afklm.spring.security.habile.GlobalConfiguration.EMPTY_STRING;
import static com.afklm.spring.security.habile.GlobalConfiguration.ROLE_PREFIX;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.doReturn;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedCredentialsNotFoundException;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.afklm.spring.security.habile.authentication.HabileAuthenticationManager;
import com.afklm.spring.security.habile.user.AbstractUserRetriever;

import reactor.core.publisher.Mono;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureWebTestClient(timeout = "10M")
public class ActuatorSecurityTest {

    private static final String USER_ID = "user";
    private static final String ADMIN_ID = "admin";
    private static final String ADMIN_JWT = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiJzczRoLXByb3h5IiwiaWF0IjoxNjM5NjU5NDE0LCJzdWIiOiJhZG1pbiIsImlzcyI6Imh0dHA6Ly9sb2NhbGhvc3Q6ODAwMSIsImV4cCI6MTYzOTY2MzAxNH0.VKbkFXe7elrEEj407m3vGtReVxeIyzKDbc1kKDyIeEI.eyJqdGkiOiJNT0NLX0lEIiwiaWF0IjoxNTk5NzUzMzYxLCJzdWIiOiJkdW1tbXkiLCJpc3MiOiJodHRwOi8vbG9jYWxob3N0OjgwMDEvIiwiZXhwIjoxNTk5NzUzMzcxfQ.OQdap9Oj5-PtupspF_4ZXcUCB_1wrG9efVO5XO8drms";
    private static final String USER_JWT = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiJzczRoLXByb3h5IiwiaWF0IjoxNjM5NjU5NDUwLCJzdWIiOiJ1c2VyIiwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDo4MDAxIiwiZXhwIjoxNjM5NjYzMDUwfQ.QGKHeUJXsTzF23xVzE2PXdcTB5GErFkYg-4OQpxvvaY";
    
    @MockBean
    private HabileAuthenticationManager manager;

    @MockBean
    private AbstractUserRetriever userRetriever;

    @Autowired
    private WebTestClient client;

    @BeforeEach
    public void setUp() {
        doReturn(Mono.error(new PreAuthenticatedCredentialsNotFoundException("No token provided !!!")))
                .when(manager).authenticate(argThat(auth -> auth.getPrincipal() != null && auth.getCredentials() == null));
        doReturn(Mono.just(new TestingAuthenticationToken(USER_ID, USER_JWT, ROLE_PREFIX + "P_TEST")))
                .when(manager).authenticate(argThat(auth -> USER_ID.equals(auth.getPrincipal()) && USER_JWT.equals(auth.getCredentials())));
        doReturn(Mono.just(new TestingAuthenticationToken(ADMIN_ID, ADMIN_JWT, ROLE_PREFIX + "P_ADMIN")))
                .when(manager).authenticate(argThat(auth -> ADMIN_ID.equals(auth.getPrincipal()) &&  ADMIN_JWT.equals(auth.getCredentials())));
        doReturn(Mono.just(new TestingAuthenticationToken(ANONYMOUS, ANONYMOUS, ROLE_PREFIX + "P_TEST_EMPTY", ROLE_PREFIX + "P_TEST")))
                .when(manager).authenticate(argThat(auth -> ANONYMOUS.equals(auth.getPrincipal()) && ANONYMOUS.equals(auth.getCredentials())));
    }

    /**
     * Unauthenticated users are not allowed to use Actuators.
     */
    @Test
    public void testActuatorAsAnonymous() {
        client.get()
                .uri("/actuator/health")
                .header(GenericHeaders.X_FORWARDED_USER, EMPTY_STRING)
                .header(GenericHeaders.X_ACCESS_TOKEN, EMPTY_STRING)
                .exchange()
                .expectStatus().isForbidden();
    }

    /**
     * Regular users are not allowed to use Actuators.
     */
    @Test
    public void testActuatorAsUser() {
        client.get()
                .uri("/actuator/health")
                .header(GenericHeaders.X_FORWARDED_USER, USER_ID)
                .header(GenericHeaders.X_ACCESS_TOKEN, USER_JWT)
                .exchange()
                .expectStatus().isForbidden();
    }

    /**
     * Admins are allowed to use Actuators.
     */
    @Test
    public void testActuatorAsAdmin() {
        client.get()
                .uri("/actuator/health")
                .header(GenericHeaders.X_FORWARDED_USER, ADMIN_ID)
                .header(GenericHeaders.X_ACCESS_TOKEN, ADMIN_JWT)
                .exchange()
                .expectStatus().isOk()
                .expectBody().jsonPath("$.status").isEqualTo("UP");
    }

    @DisplayName("urls-http-verbs-authorities test case")
    @Nested
    class UrlHttpVerbsAuthoritiesTestCase {

        @Test
        @DisplayName("test get with correct credentials")
        void testGetWithCorrectCredentials() {
            client.get()
                    .uri("/test/health")
                    .header(GenericHeaders.X_FORWARDED_USER, USER_ID)
                    .header(GenericHeaders.X_ACCESS_TOKEN, USER_JWT)
                    .exchange()
                    .expectStatus().isNotFound();
        }

        @Test
        @DisplayName("test get with wrong credentials")
        void testGetWithWrongCredentials() {
            client.get()
                    .uri("/test/health")
                    .header(GenericHeaders.X_FORWARDED_USER, ADMIN_ID)
                    .header(GenericHeaders.X_ACCESS_TOKEN, ADMIN_JWT)
                    .exchange()
                    .expectStatus().isForbidden();
        }

        @Test
        @DisplayName("test not granted verb")
        void testNotGrantedVerb() {
            client.post()
                    .uri("/test/health")
                    .header(GenericHeaders.X_FORWARDED_USER, USER_ID)
                    .header(GenericHeaders.X_ACCESS_TOKEN, USER_JWT)
                    .exchange()
                    .expectStatus().isForbidden();
        }
    }
}
