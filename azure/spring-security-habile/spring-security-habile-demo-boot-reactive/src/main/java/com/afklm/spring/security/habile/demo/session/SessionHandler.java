package com.afklm.spring.security.habile.demo.session;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Mono;

/**
 * Demo "business" handler for routes on the /hello path, to test the access restrictions
 */
@Component
public class SessionHandler {

    /**
     * Endpoint accessible with no special authority
     *
     * @param serverRequest incoming request
     * @return a hardcoded value
     */
    public Mono<ServerResponse> setData(ServerRequest serverRequest) {
        Mono<String> a = serverRequest.session().map(session -> {
            session.getAttributes().put("SS4H-DATA", serverRequest.pathVariable("what"));
            return "ok";});
        return ServerResponse.ok()
                .body(a, String.class);
    }
    
    /**
     * Endpoint accessible with no special authority
     *
     * @param serverRequest incoming request
     * @return a hardcoded value
     */
    public Mono<ServerResponse> getData(ServerRequest serverRequest) {
        Mono<String> a = serverRequest.session().map(session -> session.getAttributeOrDefault("SS4H-DATA", "NoData"));
        return ServerResponse.ok()
                .body(a, String.class);
    }

}
