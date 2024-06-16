package com.afklm.spring.security.habile.proxy.security;

import reactor.core.publisher.Mono;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.logout.RedirectServerLogoutSuccessHandler;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * HabileLogoutSuccessHandler
 * 
 * Makes both a cookie update and a forward
 * 
 * @author M405991
 *
 */
public class HabileLogoutSuccessHandler extends RedirectServerLogoutSuccessHandler {

    private HabileSessionManager habileSessionManager;

    /**
     * Constructor
     * 
     * @param targetUrl target URL
     * @param habileSessionManager HabileSessionManager
     * @throws URISyntaxException if an error occurs
     */
    public HabileLogoutSuccessHandler(String targetUrl, HabileSessionManager habileSessionManager) throws URISyntaxException {
        super.setLogoutSuccessUrl(new URI(targetUrl));
        this.habileSessionManager = habileSessionManager;
    }

    @Override
    public Mono<Void> onLogoutSuccess(WebFilterExchange exchange, Authentication authentication) {
        habileSessionManager.notifyLogout(exchange.getExchange().getResponse());
        return super.onLogoutSuccess(exchange, authentication);
    }

}
