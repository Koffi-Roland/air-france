package com.afklm.spring.security.habile;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.logout.ServerLogoutSuccessHandler;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

/**
 * Habile Logout Handler
 * 
 * A redirect is done based on HTTP header X_LOGOUT_URL
 * 
 * @author tecc
 *
 */
public class HabileLogoutHandler implements ServerLogoutSuccessHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(HabileLogoutHandler.class);

    @Override
    public Mono<Void> onLogoutSuccess(WebFilterExchange exchange, Authentication authentication) {
        // Retrieve the header as is since it should always be set
        // If it were not the case spring security will raise an exception for an empty
        // header
        return Mono.fromRunnable(() -> fillResponse(exchange.getExchange()));
    }

    private void fillResponse(ServerWebExchange serverWebExchange) {
        serverWebExchange.getResponse().setStatusCode(HttpStatus.OK);
        String logoutHeader = serverWebExchange.getRequest().getHeaders().getFirst(GenericHeaders.X_LOGOUT_URL);
        try {
            serverWebExchange
                    .getResponse()
                    .getHeaders()
                    .add(GenericHeaders.X_LOGOUT_URL, URLEncoder.encode(logoutHeader, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            LOGGER.error("Cannot encode urlLogout " + logoutHeader, e);
        }
    }
}
