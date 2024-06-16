package com.afklm.spring.security.habile.proxy.security;

import reactor.util.context.Context;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.http.server.reactive.MockServerHttpRequest;
import org.springframework.mock.web.server.MockServerWebExchange;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.util.Base64Utils;
import org.springframework.web.server.ServerWebExchange;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@ExtendWith(MockitoExtension.class)
public class HabileServerHttpBasicAuthenticationConverterTest {

    private static final String JUNIT_PASSWORD = "junit-password";

    private static final String USER = "USER";

    private static final String PASSWORD = "password";

    private static final String JUNIT_USER_ID = "junit-userId";

    @Mock
    private ServerWebExchange exchange;

    private HabileServerHttpBasicAuthenticationConverter converter = new HabileServerHttpBasicAuthenticationConverter();

    @Test
    public void testNoAuthenticationWhenNothingIsAvailable() {
        ReactiveSecurityContextHolder.clearContext();

        MockServerHttpRequest request = MockServerHttpRequest.get("/").build();
        exchange = MockServerWebExchange.from(request);

        Authentication authentication = this.converter.convert(this.exchange).block();

        assertThat(authentication).isNull();
    }

    @Test
    public void testBasicAuthWhenNoExistingAuthenticationIsAvailable() {
        MockServerHttpRequest request = MockServerHttpRequest.get("/").header(AUTHORIZATION, getConfigAuthHeader()).build();
        exchange = MockServerWebExchange.from(request);
        Authentication authentication = this.converter.convert(this.exchange).block();

        assertThat(authentication.getName()).isEqualTo(JUNIT_USER_ID);
        assertThat(authentication.getCredentials()).isEqualTo(JUNIT_PASSWORD);
        assertThat(authentication.getAuthorities()).isEmpty();
    }

    @Test
    public void testBasicAuthWhenExistingBasicAuthenticationIsAvailable() {
        Context context = ReactiveSecurityContextHolder.withAuthentication(new UsernamePasswordAuthenticationToken(JUNIT_USER_ID, PASSWORD, AuthorityUtils.createAuthorityList(USER)));

        MockServerHttpRequest request = MockServerHttpRequest.get("/").header(AUTHORIZATION, getConfigAuthHeader()).build();
        exchange = MockServerWebExchange.from(request);
        Authentication authentication = this.converter.convert(this.exchange).contextWrite(context).block();

        assertThat(authentication).isNull();
    }

    @Test
    public void testBasicAuthWhenExistingUnauthenticatedBasicAuthenticationIsAvailable() {
        Context context = ReactiveSecurityContextHolder.withAuthentication(new UsernamePasswordAuthenticationToken(JUNIT_USER_ID, PASSWORD));

        MockServerHttpRequest request = MockServerHttpRequest.get("/").header(AUTHORIZATION, getConfigAuthHeader()).build();
        exchange = MockServerWebExchange.from(request);
        Authentication authentication = this.converter.convert(this.exchange).contextWrite(context).block();

        assertThat(authentication.getName()).isEqualTo(JUNIT_USER_ID);
        assertThat(authentication.getCredentials()).isEqualTo(JUNIT_PASSWORD);
        assertThat(authentication.getAuthorities()).isEmpty();
    }

    @Test
    public void testBasicAuthWhenExistingDifferentBasicAuthenticationIsAvailable() {
        Context context = ReactiveSecurityContextHolder.withAuthentication(new UsernamePasswordAuthenticationToken("junit-userIdOther", PASSWORD, AuthorityUtils.createAuthorityList(USER)));

        MockServerHttpRequest request = MockServerHttpRequest.get("/").header(AUTHORIZATION, getConfigAuthHeader()).build();
        exchange = MockServerWebExchange.from(request);
        Authentication authentication = this.converter.convert(this.exchange).contextWrite(context).block();

        assertThat(authentication.getName()).isEqualTo(JUNIT_USER_ID);
        assertThat(authentication.getCredentials()).isEqualTo(JUNIT_PASSWORD);
        assertThat(authentication.getAuthorities()).isEmpty();
    }

    @Test
    public void testBasicAuthWhenExistingAnonymousAuthenticationIsAvailable() {
        Context context = ReactiveSecurityContextHolder.withAuthentication(new AnonymousAuthenticationToken(JUNIT_USER_ID + "Other", PASSWORD, AuthorityUtils.createAuthorityList(USER)));

        MockServerHttpRequest request = MockServerHttpRequest.get("/").header(AUTHORIZATION, getConfigAuthHeader()).build();
        exchange = MockServerWebExchange.from(request);
        Authentication authentication = this.converter.convert(this.exchange).contextWrite(context).block();

        assertThat(authentication.getName()).isEqualTo(JUNIT_USER_ID);
        assertThat(authentication.getCredentials()).isEqualTo(JUNIT_PASSWORD);
        assertThat(authentication.getAuthorities()).isEmpty();
    }

    private String getConfigAuthHeader() {
        return "Basic " + Base64Utils.encodeToString((JUNIT_USER_ID + ":" + JUNIT_PASSWORD).getBytes());
    }
}
