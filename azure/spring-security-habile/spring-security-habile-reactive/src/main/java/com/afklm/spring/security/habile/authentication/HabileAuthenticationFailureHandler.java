package com.afklm.spring.security.habile.authentication;

import static com.afklm.spring.security.habile.SpringSecurityHabileMessageCode.SS4H_MSG_RT_ERROR;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.ServerAuthenticationFailureHandler;

import reactor.core.publisher.Mono;

/**
 * This class provides a reply in case of an authentication failure.
 *
 * @author TECC-SE
 */
public class HabileAuthenticationFailureHandler implements ServerAuthenticationFailureHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(HabileAuthenticationFailureHandler.class);

    /**
     * @param webFilterExchange the exchange triggering the authentication failure
     * @param e                 exception object related to the authentication failure
     * @return a Mono of Void for further processing in the reactive chain
     */
    @Override
    public Mono<Void> onAuthenticationFailure(WebFilterExchange webFilterExchange, AuthenticationException e) {
        String message = SS4H_MSG_RT_ERROR.format(UUID.randomUUID().toString());
        LOGGER.error(message, e.getCause());

        ServerHttpResponse response = webFilterExchange.getExchange().getResponse();
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        DataBuffer db = response.bufferFactory()
                .allocateBuffer()
                .write(("HTTP status " + response.getStatusCode() + " - " + message).getBytes(StandardCharsets.UTF_8));

        return response.writeWith(Mono.just(db));
    }
}