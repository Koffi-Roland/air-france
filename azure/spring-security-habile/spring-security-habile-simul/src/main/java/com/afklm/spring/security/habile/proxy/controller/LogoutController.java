package com.afklm.spring.security.habile.proxy.controller;

import com.afklm.spring.security.habile.proxy.HabileProxyConstants;
import com.afklm.spring.security.habile.proxy.security.HabileSessionManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.afklm.spring.security.habile.proxy.HabileProxyConstants.LOGOUT_DONE;

/**
 * Logout controller
 * 
 * @author m405991
 *
 */
@RestController
public class LogoutController {
    @Autowired
    private HabileSessionManager habileSessionManager;

    /**
     * Display a message upon logout
     * 
     * @param response
     * @return logout message
     */
    @GetMapping(LOGOUT_DONE)
    public String logoutDone(ServerHttpResponse response) {
        // reset once again habile session since follow redirect may ignore cookie reset
        habileSessionManager.notifyLogout(response);
        return HabileProxyConstants.LOGOUT_BODY;
    }
}
