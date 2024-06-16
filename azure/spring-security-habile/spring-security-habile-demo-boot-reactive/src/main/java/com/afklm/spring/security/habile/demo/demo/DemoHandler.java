package com.afklm.spring.security.habile.demo.demo;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

/**
 * Demo "business" handler for routes on the /api path, to test the access restrictions
 */
@Component
public class DemoHandler {
    /**
     * User endpoint accessible with VIEW authority
     *
     * @param serverRequest incoming request
     * @return a hardcoded value
     */
    @PreAuthorize("hasAuthority('VIEW')")
    public Mono<ServerResponse> user(ServerRequest serverRequest) {
        return ServerResponse.ok()
                .body(Mono.just("user"), String.class);
    }

    /**
     * Admin endpoint accessible with UPDATE authority
     *
     * @param serverRequest incoming request
     * @return hardcoded value
     */
    @PreAuthorize("hasAuthority('UPDATE')")
    public Mono<ServerResponse> admin(ServerRequest serverRequest) {
        return ServerResponse.ok()
                .body(Mono.just("admin"), String.class);
    }

    /**
     * Update endpoint accessible with UPDATE authority
     *
     * @param serverRequest incoming request
     * @return hardcoded value
     */
    @PreAuthorize("hasAuthority('UPDATE')")
    public Mono<ServerResponse> update(ServerRequest serverRequest) {
        return ServerResponse.ok().build();
    }
}
