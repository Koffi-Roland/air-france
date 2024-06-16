package com.afklm.spring.security.habile;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.server.authorization.HttpStatusServerAccessDeniedHandler;
import org.springframework.security.web.server.csrf.CsrfException;
import org.springframework.security.web.server.csrf.CsrfWebFilter;
import org.springframework.security.web.server.csrf.ServerCsrfTokenRepository;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

/**
 * This class is intended to replace the default {@link ServerAccessDeniedHandler} set in
 * the {@link CsrfWebFilter} so that any {@link CsrfException}, raised due to a missing
 * CSRF token in the internal repository, triggers the generation and saving of a new token
 * that can be used on subsequent requests.
 */
public class CsrfServerAccessDeniedHandler extends HttpStatusServerAccessDeniedHandler {

    private ServerCsrfTokenRepository csrfTokenRepository;

    public CsrfServerAccessDeniedHandler(ServerCsrfTokenRepository csrfTokenRepository) {
        super(HttpStatus.FORBIDDEN);
        this.csrfTokenRepository = csrfTokenRepository;
    }

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, AccessDeniedException ex) {
        Mono<?> mono;
        if (ex instanceof CsrfException) {
            mono = csrfTokenRepository
                    .loadToken(exchange)
                    .switchIfEmpty(csrfTokenRepository // Generate a new token only if none was present
                            .generateToken(exchange)
                            .flatMap(token -> csrfTokenRepository.saveToken(exchange, token))
                            .then(Mono.empty()));
        } else {
            mono = Mono.empty();
        }
        return mono.then(super.handle(exchange, ex));
    }

}
