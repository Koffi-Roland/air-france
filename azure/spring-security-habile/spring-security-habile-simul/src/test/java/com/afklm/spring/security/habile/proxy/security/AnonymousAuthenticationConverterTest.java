package com.afklm.spring.security.habile.proxy.security;

import static com.afklm.spring.security.habile.GlobalConfiguration.EMPTY_STRING;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.web.server.ServerWebExchange;

import reactor.util.context.Context;

@ExtendWith(MockitoExtension.class)
public class AnonymousAuthenticationConverterTest {

    @Mock
    private ServerWebExchange exchange;

    private AnonymousAuthenticationConverter converter = new AnonymousAuthenticationConverter();

    @Test
    public void testAnonymousWhenNothingIsAvailable() {
        ReactiveSecurityContextHolder.clearContext();

        Authentication authentication = this.converter.convert(this.exchange).block();

        assertThat(authentication).isInstanceOf(AnonymousAuthenticationToken.class);
        assertThat(authentication.getName()).isEqualTo("principal");
        assertThat(authentication.getCredentials()).isEqualTo(EMPTY_STRING);
        assertThat(authentication.getAuthorities()).hasSize(1);
    }

    @Test
    public void testExistingAuthenticationIsAvailable() {
        Context context = ReactiveSecurityContextHolder.withAuthentication(new PreAuthenticatedAuthenticationToken("user", "password"));
        Authentication authentication = this.converter.convert(this.exchange).contextWrite(context).block();
        assertThat(authentication).isNotNull();
    }
}
