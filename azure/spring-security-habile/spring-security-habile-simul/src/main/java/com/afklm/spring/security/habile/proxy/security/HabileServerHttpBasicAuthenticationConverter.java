package com.afklm.spring.security.habile.proxy.security;

import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.web.server.authentication.ServerHttpBasicAuthenticationConverter;
import org.springframework.web.server.ServerWebExchange;

/**
 * Habile ServerHttpBasicAuthenticationConverter
 * 
 * @author m405991
 *
 */
public class HabileServerHttpBasicAuthenticationConverter extends ServerHttpBasicAuthenticationConverter {

    private static final Authentication NONE = new UsernamePasswordAuthenticationToken("none", "none");

    @Override
    public Mono<Authentication> convert(ServerWebExchange exchange) {
        Mono<Authentication> def = Mono.just(NONE);

        Mono<Authentication> authSession = ReactiveSecurityContextHolder.getContext()
            .map(SecurityContext::getAuthentication);
        Mono<Authentication> authBasic = super.convert(exchange);

        return authBasic.switchIfEmpty(def)
            .zipWith(authSession.switchIfEmpty(def))
            .map(this::getAuthentication)
            .filter(t -> t != NONE);
    }

    private Authentication getAuthentication(Tuple2<Authentication, Authentication> tuple) {
        return authenticationIsRequired(tuple.getT1(), tuple.getT2()) ? tuple.getT1() : NONE;
    }

    // Picked from org.springframework.security.web.authentication.www.BasicAuthenticationFilter.authenticationIsRequired(String)
    private boolean authenticationIsRequired(Authentication authBasic, Authentication authSession) {
        // Only reauthenticate if username doesn't match SecurityContextHolder and user
        // isn't authenticated
        // (see SEC-53)

        if (authSession == NONE || !authSession.isAuthenticated()) {
            return true;
        }

        // Limit username comparison to providers which use usernames (ie
        // UsernamePasswordAuthenticationToken)
        // (see SEC-348)

        if (authSession instanceof UsernamePasswordAuthenticationToken
                && !authSession.getName().equals(authBasic.getName())) {
            return true;
        }

        // Handle unusual condition where an AnonymousAuthenticationToken is already
        // present
        // This shouldn't happen very often, as BasicProcessingFitler is meant to be
        // earlier in the filter
        // chain than AnonymousAuthenticationFilter. Nevertheless, presence of both an
        // AnonymousAuthenticationToken
        // together with a BASIC authentication request header should indicate
        // reauthentication using the
        // BASIC protocol is desirable. This behaviour is also consistent with that
        // provided by form and digest,
        // both of which force re-authentication if the respective header is detected (and
        // in doing so replace
        // any existing AnonymousAuthenticationToken). See SEC-610.
        return (authSession instanceof AnonymousAuthenticationToken);
    }
}
