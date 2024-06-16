package com.afklm.spring.security.habile.demo.hello;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

/**
 * Demo "business" handler for routes on the /hello path, to test the access restrictions
 */
@Component
public class HelloHandler {

    /**
     * Endpoint accessible with no special authority
     *
     * @param serverRequest incoming request
     * @return a hardcoded value
     */
    public Mono<ServerResponse> hello(ServerRequest serverRequest) {
        return ServerResponse.ok()
                .body(Mono.just("default hello page"), String.class);
    }

    /**
     * User endpoint accessible with USER authority
     *
     * @param serverRequest incoming request
     * @return a hardcoded value
     */
    @PreAuthorize("hasAuthority('USER')")
    public Mono<ServerResponse> helloUser(ServerRequest serverRequest) {
        return ServerResponse.ok()
                .body(Mono.just("hello there"), String.class);
    }

    /**
     * Admin endpoint accessible with ADMIN authority
     *
     * @param request incoming request
     * @return a hardcoded value
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    public Mono<ServerResponse> helloAdmin(ServerRequest request) {
        return ServerResponse.ok()
                .body(Mono.just("Admin page there"), String.class);
    }

    /**
     * Common endpoint accessible with both ADMIN and USER authorities
     *
     * @param request incoming request
     * @return a hardcoded value
     */
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public Mono<ServerResponse> helloCommon(ServerRequest request) {
        return ServerResponse.ok()
                .body(Mono.just("Common page there"), String.class);
    }
}
