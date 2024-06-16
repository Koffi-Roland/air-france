package com.afklm.spring.security.habile.proxy.security;

import reactor.core.publisher.Mono;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.web.server.authentication.ServerHttpBasicAuthenticationConverter;
import org.springframework.web.server.ServerWebExchange;

/**
 * Habile AnonymousAuthenticationConverter
 * 
 * @author m405991
 *
 */
public class PublicAuthenticationConverter extends ServerHttpBasicAuthenticationConverter {

    @Override
    public Mono<Authentication> convert(ServerWebExchange exchange) {
        // Create a AnonymousAuthenticationToken only if nothing is available
        return ReactiveSecurityContextHolder.getContext()
            .map(SecurityContext::getAuthentication)
            .defaultIfEmpty(createAuthentication())
            .ofType(PublicAuthenticationToken.class)
            .cast(Authentication.class);
    }

    private Authentication createAuthentication() {
        return new PublicAuthenticationToken();
    }
}
