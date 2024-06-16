package com.afklm.spring.security.habile.proxy.security;

import reactor.core.publisher.Mono;

import com.afklm.spring.security.habile.proxy.model.UserInformation;
import com.afklm.spring.security.habile.proxy.service.ConfigurationService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

/**
 * Custom Authentication Provider that accepts any user and any password
 *
 * @author m405991
 *
 */
@Component
public class SimulationAuthenticationProvider implements ReactiveAuthenticationManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(SimulationAuthenticationProvider.class);

    @Autowired
    private ConfigurationService authoritiesConfigurationService;

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        String name = authentication.getName();
        if (authentication.getCredentials() == null) {
            return Mono.error(new BadCredentialsException("Credentials null"));
        }
        String password = authentication.getCredentials().toString();

        // Empty configuration means that any user is accepted
        if (authoritiesConfigurationService.hasNoUser()) {
            LOGGER.info("Authenticating any user '{}'", name);
            return Mono.just(new UsernamePasswordAuthenticationToken(name, password, new ArrayList<>()));
        } else {
            LOGGER.info("Authenticating against configured list of users: {}", name);
            UserInformation userInfo = authoritiesConfigurationService.getUserInformation(name);
            if (userInfo == null) {
                return Mono.error(new AuthenticationCredentialsNotFoundException("User invalid"));
            } else if (password.equals(userInfo.getPassword())) {
                return Mono.just(new UsernamePasswordAuthenticationToken(name, password, new ArrayList<>()));
            } else {
                return Mono.error(new BadCredentialsException("Credentials invalid"));
            }
        }
    }
}
