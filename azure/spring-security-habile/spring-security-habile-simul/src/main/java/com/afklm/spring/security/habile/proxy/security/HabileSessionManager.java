package com.afklm.spring.security.habile.proxy.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpCookie;
import org.springframework.http.ResponseCookie;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;

import static com.afklm.spring.security.habile.proxy.HabileSimulConstants.HABILE_LOGGED_COOKIE;
import static com.afklm.spring.security.habile.proxy.HabileSimulConstants.HABILE_SESSION_COOKIE;

/**
 * HabileSessionManager
 * 
 * This mimics the Habile proxy behaviour by using cookies to store Habile state
 * A cookie stores the state of Habile proxy, see {@link HabileState}
 * 
 * 
 * @author M405991
 *
 */
@Component
public class HabileSessionManager implements InitializingBean {

    private static final Logger LOGGER = LoggerFactory.getLogger(HabileSessionManager.class);

    /**
     * Timeout for simulation habile session - by default 10 minutes
     */
    @Value("${afklm.security.habile.simul.session.timeout}")
    private int habileCookieDuration;

    /**
     * Notify that a logout is done
     * 
     * @param response modify response to notify a logout is done
     */
    public void notifyLogout(ServerHttpResponse response) {
        ResponseCookie cookie = ResponseCookie.from(HABILE_SESSION_COOKIE, HabileState.LOGOUT.name())
            .path("/")
            .build();
        // Replace existing cookie
        response.getCookies().remove(HABILE_SESSION_COOKIE);
        response.addCookie(cookie);
    }

    /**
     * Get habile state
     * 
     * @param request request
     * @return value for state
     */
    public HabileState getHabileState(ServerHttpRequest request) {
        HttpCookie cookieSession = request.getCookies().getFirst(HABILE_SESSION_COOKIE);
        HttpCookie cookieLogged = request.getCookies().getFirst(HABILE_LOGGED_COOKIE);
        return cookieSession == null ? (cookieLogged == null ? null : HabileState.EXPIRED) : HabileState.valueOf(cookieSession.getValue());
    }

    /**
     * Notify that a login is expected
     * 
     * @param response modify a response to indicate that a login is in progress
     */
    public void notifyLogin(ServerHttpResponse response) {
        ResponseCookie cookie = ResponseCookie.from(HABILE_SESSION_COOKIE, HabileState.LOGIN.name())
            .path("/")
            .build();
        response.addCookie(cookie);
    }

    /**
     * Notify that a login is done
     * 
     * @param response modify a response to indicate that a login is in progress
     */
    public void notifyLogged(ServerHttpResponse response) {
        ResponseCookie cookie = ResponseCookie.from(HABILE_SESSION_COOKIE, HabileState.LOGGED.name())
            .path("/")
            .maxAge(habileCookieDuration)
            .build();
        response.addCookie(cookie);
        response.addCookie(ResponseCookie.from(HABILE_LOGGED_COOKIE, "LOGGED").build());
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        LOGGER.info("HabileSessionManager initialized with a timeout of {} seconds", habileCookieDuration);
    }
}
