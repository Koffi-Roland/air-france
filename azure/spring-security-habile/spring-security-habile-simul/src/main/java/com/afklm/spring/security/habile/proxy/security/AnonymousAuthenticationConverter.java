package com.afklm.spring.security.habile.proxy.security;

import reactor.core.publisher.Mono;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.web.server.authentication.ServerHttpBasicAuthenticationConverter;
import org.springframework.web.server.ServerWebExchange;

import java.util.List;

/**
 * Habile AnonymousAuthenticationConverter
 * 
 * @author m405991
 * @author m408461
 *
 */
public class AnonymousAuthenticationConverter extends ServerHttpBasicAuthenticationConverter {

    @Override
    public Mono<Authentication> convert(ServerWebExchange exchange) {
        // Create a AnonymousAuthenticationToken only if nothing is available
        return ReactiveSecurityContextHolder.getContext()
            .map(SecurityContext::getAuthentication)
            .map(this::convertToAnonymous)
            .defaultIfEmpty(createAuthentication())
            .ofType(AnonymousAuthenticationToken.class)
            .cast(Authentication.class);
    }

    private Authentication convertToAnonymous(Authentication auth) {
        List<GrantedAuthority> auths = AuthorityUtils.createAuthorityList("ROLE_ANONYMOUS");
        if (auth.getAuthorities() != null) {
            auths.addAll(auth.getAuthorities());
        }
        return new AnonymousAuthenticationToken("key", auth.getPrincipal(), auths);
        // return auth;
    }

    private Authentication createAuthentication() {
        return new AnonymousAuthenticationToken("key",
            "principal", AuthorityUtils.createAuthorityList("ROLE_ANONYMOUS"));
    }
}
