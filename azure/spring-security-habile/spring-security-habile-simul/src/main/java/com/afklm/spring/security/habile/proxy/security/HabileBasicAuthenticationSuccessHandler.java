package com.afklm.spring.security.habile.proxy.security;

import reactor.core.publisher.Mono;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.WebFilterChainServerAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

/**
 * HabileBasicAuthenticationSuccessHandler
 * 
 * @author m405991
 *
 */
@Component
public class HabileBasicAuthenticationSuccessHandler extends WebFilterChainServerAuthenticationSuccessHandler {

    private HabileSessionManager habileSessionManager;

    /**
     * Constructor
     * 
     * @param habileSessionManager HabileSessionManager
     */
    public HabileBasicAuthenticationSuccessHandler(HabileSessionManager habileSessionManager) {
        this.habileSessionManager = habileSessionManager;
    }

    @Override
    public Mono<Void> onAuthenticationSuccess(WebFilterExchange webFilterExchange, Authentication authentication) {
        habileSessionManager.notifyLogged(webFilterExchange.getExchange().getResponse());
        return super.onAuthenticationSuccess(webFilterExchange, authentication);
    }

}
