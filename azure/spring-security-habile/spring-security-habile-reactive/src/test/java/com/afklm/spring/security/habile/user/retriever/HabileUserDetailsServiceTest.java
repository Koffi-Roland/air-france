package com.afklm.spring.security.habile.user.retriever;

import static com.afklm.spring.security.habile.GlobalConfiguration.ANONYMOUS;
import static com.afklm.spring.security.habile.GlobalConfiguration.NO_CREDENTIAL;
import static org.mockito.ArgumentMatchers.anyString;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import com.afklm.spring.security.habile.GenericHeaders;
import com.afklm.spring.security.habile.oidc.JwtUtils;
import io.jsonwebtoken.Jwts;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedCredentialsNotFoundException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.reactive.function.client.WebClient;

import com.afklm.spring.security.habile.GlobalConfiguration;
import com.afklm.spring.security.habile.HabileUserDetails;
import com.afklm.spring.security.habile.oidc.UserInfo;
import com.afklm.spring.security.habile.user.AbstractUserRetriever;
import com.afklm.spring.security.habile.user.HabileUserDetailsService;
import com.afklm.spring.security.habile.userdetails.NoOpCustomDetailsServiceConfiguration;
import com.afklm.spring.security.habile.utils.StreamHelper;

import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {NoOpCustomDetailsServiceConfiguration.class, HabileUserDetailsService.class, AbstractUserRetriever.class})
public class HabileUserDetailsServiceTest {
    
    private static final String ADMIN_JWT = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiJzczRoLXByb3h5IiwiaWF0IjoxNjM5NjU5NDE0LCJzdWIiOiJhZG1pbiIsImlzcyI6Imh0dHA6Ly9sb2NhbGhvc3Q6ODAwMSIsImV4cCI6MTYzOTY2MzAxNH0.VKbkFXe7elrEEj407m3vGtReVxeIyzKDbc1kKDyIeEI.eyJqdGkiOiJNT0NLX0lEIiwiaWF0IjoxNTk5NzUzMzYxLCJzdWIiOiJkdW1tbXkiLCJpc3MiOiJodHRwOi8vbG9jYWxob3N0OjgwMDEvIiwiZXhwIjoxNTk5NzUzMzcxfQ.OQdap9Oj5-PtupspF_4ZXcUCB_1wrG9efVO5XO8drms";
    private static final String USER_JWT = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiJzczRoLXByb3h5IiwiaWF0IjoxNjM5NjU5NDUwLCJzdWIiOiJ1c2VyIiwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDo4MDAxIiwiZXhwIjoxNjM5NjYzMDUwfQ.QGKHeUJXsTzF23xVzE2PXdcTB5GErFkYg-4OQpxvvaY";
    private static final String USERNAME = "m_USER";
    private static final String CREDENTIAL = "some creds";
    private static final String FIRST_NAME = "Jean Michel";
    private static final String LAST_NAME = "Testage";
    private static final String LOGOUT_URL = "logouturl";
    private static final String EMAIL = USERNAME + "@airfrance.fr";
    private static final List<String> ROLES = Arrays.asList("P_ROLE_1", "P_ROLE_2");
    private static final UserInfo MOCKED_REPLY = new UserInfo(USERNAME, LOGOUT_URL, ROLES, LAST_NAME, FIRST_NAME, EMAIL);

    @Autowired
    private HabileUserDetailsService userDetailsService;

    @MockBean(name = "abstractUserRetriever", answer = Answers.CALLS_REAL_METHODS)
    private PingAccessUserRetriever spiedRetriever;

    @Mock
    private WebClient webClient;
    @Mock
    private WebClient.RequestHeadersSpec requestHeadersMock;
    @Mock
    private WebClient.RequestHeadersUriSpec requestHeadersUriMock;
    @Mock
    private WebClient.RequestBodySpec requestBodyMock;
    @Mock
    private WebClient.RequestBodyUriSpec requestBodyUriMock;
    @Mock
    private WebClient.ResponseSpec responseMock;

    private HabileUserDetails expectedUserDetails;
    private HabileUserDetails anonymousUser;
    
    private static boolean mockitoInitDone = false;

    @BeforeEach
    public void setup() {
        if(!mockitoInitDone) {
            Mockito.when(webClient.get()).thenReturn(requestHeadersUriMock);
            Mockito.when(requestHeadersUriMock.uri(anyString())).thenReturn(requestHeadersMock);
            Mockito.when(requestHeadersMock.header(anyString(), anyString())).thenReturn(requestHeadersMock);
            Mockito.when(requestHeadersMock.retrieve()).thenReturn(responseMock);
            Mockito.when(responseMock.bodyToMono(UserInfo.class)).thenReturn(Mono.just(MOCKED_REPLY));
            spiedRetriever.setClient(webClient);
            mockitoInitDone = true;
        }
        expectedUserDetails = new HabileUserDetails(USERNAME, LAST_NAME, FIRST_NAME, EMAIL, StreamHelper.transform(ROLES, SimpleGrantedAuthority::new), USER_JWT);
        anonymousUser = new HabileUserDetails(ANONYMOUS, ANONYMOUS, ANONYMOUS, StreamHelper.transform(GlobalConfiguration.getAnonymousRoles(), SimpleGrantedAuthority::new), null);
    }

    @Test
    public void retrieveUser() {
        StepVerifier.create(userDetailsService.findByUsername(USERNAME, USER_JWT))
                    .expectNext(expectedUserDetails)
                    .verifyComplete();
    }

    @Test
    public void retrieveWithoutCredentials() {
        Assertions.assertThrows(NullPointerException.class, () -> userDetailsService.findByUsername(USERNAME, null));
    }

    @Test
    public void retrieveWithInvalidCredentials() {
        StepVerifier.create(userDetailsService.findByUsername(USERNAME, NO_CREDENTIAL))
                    .verifyError(UsernameNotFoundException.class);
    }

    @Test
    @DisplayName("Test non existent user when issuer not trusted")
    void testNonExistentUserWhenIssuerNotTrusted() {
        StepVerifier.create(userDetailsService.findByUsername(USERNAME, buildJWTWithIssuer("http://notTrusted:0000")))
                    .verifyErrorMatches(th -> th instanceof UsernameNotFoundException && th.getMessage().equals("This user could not be retrieved"));
    }

    @DisplayName("test Nominal with valid JWT issuers")
    @ParameterizedTest
    @MethodSource("trustedIssuers")
    public void testNominal(String issuerHost) {
        StepVerifier.create(userDetailsService.findByUsername(USERNAME, buildJWTWithIssuer(issuerHost.toUpperCase())))
                .expectNext(expectedUserDetails)
                .verifyComplete();
    }

    @Test
    public void retrieveAnonymousUserTest() {
        StepVerifier.create(userDetailsService.findByUsername(ANONYMOUS, ANONYMOUS))
                .expectNext(anonymousUser)
                .verifyComplete();
    }

    /**
     * Creates a JWT where the issuer matches the host parameter
     * @param issuerHost - the host of the issuer
     * @return
     */
    private String buildJWTWithIssuer(String issuerHost) {
        String issuer = "http://" + issuerHost + ":0000";
        Map<String, Object> claims = new HashMap<>();
        claims.put("fake", "fake");
        return Jwts.builder()
                .setClaims(claims)
                .setIssuer(issuer)
                .compact();
    }

    /**
     * Creates a Stream with the trusted issuers.
     * @return
     */
    private static Stream<String> trustedIssuers() {
        return JwtUtils.TRUSTED_ISSUERS.stream();
    }
}