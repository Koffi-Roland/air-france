package com.afklm.spring.security.habile.proxy.security;

import reactor.core.publisher.Mono;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.core.Authentication;

/**
 * Anonymous Authentication Provider
 *
 * @author m405991
 *
 */
public class AnonymousAuthenticationProvider implements ReactiveAuthenticationManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(AnonymousAuthenticationProvider.class);

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        LOGGER.info("Authenticating anonymous");
        return Mono.just(authentication);
    }
}
