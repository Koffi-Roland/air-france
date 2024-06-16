package com.afklm.spring.security.habile.proxy.security;

import reactor.core.publisher.Mono;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.RedirectServerAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

/**
 * SuccessHandler to be injected into a RedirectServer process ie with login page
 * 
 * @author m408461
 *
 */
@Component
public class AFKLFormAuthenticationSuccessHandler extends RedirectServerAuthenticationSuccessHandler {

    private HabileSessionManager habileSessionManager;

    /**
     * Constructor
     * 
     * @param habileSessionManager HabileSessionManager
     */
    public AFKLFormAuthenticationSuccessHandler(HabileSessionManager habileSessionManager) {
        this.habileSessionManager = habileSessionManager;
    }

    @Override
    public Mono<Void> onAuthenticationSuccess(WebFilterExchange webFilterExchange, Authentication authentication) {
        habileSessionManager.notifyLogged(webFilterExchange.getExchange().getResponse());
        return super.onAuthenticationSuccess(webFilterExchange, authentication);
    }

}
