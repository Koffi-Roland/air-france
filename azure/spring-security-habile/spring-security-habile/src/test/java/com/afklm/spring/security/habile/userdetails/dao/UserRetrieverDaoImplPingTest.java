package com.afklm.spring.security.habile.userdetails.dao;

import com.afklm.spring.security.habile.HabilePrincipal;
import com.afklm.spring.security.habile.oidc.JwtUtils;
import com.afklm.spring.security.habile.oidc.UserInfo;

import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@ExtendWith(MockitoExtension.class)
public class UserRetrieverDaoImplPingTest {

    @InjectMocks
    private UserRetrieverDaoImplPing userRetrieverDaoImpl;

    @Mock
    private RestTemplate mockRestTemplate;

    final String userId = "dummmy";

    @DisplayName("test Nominal with valid JWT issuers")
    @ParameterizedTest
    @MethodSource("com.afklm.spring.security.habile.userdetails.dao.UserRetrieverDaoImplPingTest#trustedIssuers")
    public void testNominal(String issuerHost) {
        // Setup
        UserInfo invocationResponse = UserInfoFixture.response(userId, "P_TEST");
        Mockito.when(mockRestTemplate.exchange(
            ArgumentMatchers.any(URI.class),
            ArgumentMatchers.any(HttpMethod.class),
            ArgumentMatchers.any(),
            ArgumentMatchers.<Class<UserInfo>> any()))
            .thenReturn(new ResponseEntity<>(invocationResponse, HttpStatus.OK));

        // Invoke
        String jwt = buildJWTWithIssuer(issuerHost.toUpperCase());
        HabilePrincipal habilePrincipal = userRetrieverDaoImpl.getUser(jwt, null);

        assertThat(habilePrincipal.getUserId()).isEqualTo(userId);
        assertThat(habilePrincipal.getFirstName()).isEqualTo(invocationResponse.getGiven_name());
        assertThat(habilePrincipal.getLastName()).isEqualTo(invocationResponse.getFamily_name());
        assertThat(habilePrincipal.getEmail()).isEqualTo(invocationResponse.getEmail());
        assertThat(habilePrincipal.getProfiles()).containsAll(invocationResponse.getProfile());
    }

    @Test
    public void testExceptionWrapped() {
        // Setup
        Throwable exception = UserInfoFixture.restClientException();
        Mockito.when(mockRestTemplate.exchange(
            ArgumentMatchers.any(URI.class),
            ArgumentMatchers.any(HttpMethod.class),
            ArgumentMatchers.any(),
            ArgumentMatchers.<Class<UserInfo>> any()))
            .thenThrow(exception);
        String jwt = buildJWTWithIssuer("localhost");
        assertThatExceptionOfType(UsernameNotFoundException.class).isThrownBy(() ->
                        userRetrieverDaoImpl.getUser(jwt, null)
            )
            .withCause(exception)
            .withMessage(String.format(UserRetrieverDao.PING_ERROR_MESSAGE, jwt));
    }

    @Test
    @DisplayName("test with non trusted issuer")
    void testWithNonTrustedIssuer() {
        String jwt = buildJWTWithIssuer("not_trusted_host");
        assertThatExceptionOfType(UsernameNotFoundException.class).isThrownBy(() ->
                    userRetrieverDaoImpl.getUser(jwt, null)
            )
            .withMessage(String.format(UserRetrieverDao.PING_ERROR_MESSAGE, jwt));
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
