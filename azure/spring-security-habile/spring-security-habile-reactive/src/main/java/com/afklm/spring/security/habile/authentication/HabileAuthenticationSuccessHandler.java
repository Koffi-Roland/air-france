package com.afklm.spring.security.habile.authentication;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.WebFilterChainServerAuthenticationSuccessHandler;
import org.springframework.security.web.server.csrf.CsrfToken;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

/**
 * This {@link WebFilterChainServerAuthenticationSuccessHandler} ensures that
 * any user, on successful authentication, is provided with a CSRF token for
 * later use with any requiring endpoint.
 *
 * The trick consists in subscribing to the {@link Mono} holding the delayed
 * reference CSRF token so that it is internally generated (when not already
 * available) and later returned in the response.
 */
public class HabileAuthenticationSuccessHandler extends WebFilterChainServerAuthenticationSuccessHandler {
    @Override
    public Mono<Void> onAuthenticationSuccess(WebFilterExchange webFilterExchange, Authentication authentication) {
        ServerWebExchange exchange = webFilterExchange.getExchange();
        Mono<CsrfToken> csrfTokenMono = exchange.getAttributeOrDefault(CsrfToken.class.getName(), Mono.empty());
        // Calling then(...) is necessary so that the CSRF cookie is written before
        // the response is committed while calling super.onAuthenticationSuccess(...)
        return csrfTokenMono.then(super.onAuthenticationSuccess(webFilterExchange, authentication));
    }
}