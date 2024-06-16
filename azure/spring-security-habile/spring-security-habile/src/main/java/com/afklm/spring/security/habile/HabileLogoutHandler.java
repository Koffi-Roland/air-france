package com.afklm.spring.security.habile;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Habile Logout Handler
 * 
 * A redirect is done based on HTTP header SM_LOGOUT or x-logout-url header
 * 
 * @author tecc
 *
 */
public class HabileLogoutHandler implements LogoutSuccessHandler {
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws UnsupportedEncodingException {
        // Retrieve the header as is since it should always be set
        // If it were not the case spring security will raise an exception for an empty header
        response.setStatus(HttpStatus.OK.value());
        String logoutUrl = request.getHeader(GenericHeaders.SM_LOGOUT);
        if (logoutUrl == null) {
            logoutUrl = request.getHeader(GenericHeaders.X_LOGOUT_URL);
        }
        String urlLogout = URLEncoder.encode(logoutUrl, "UTF-8");
        response.setHeader(GenericHeaders.SM_LOGOUT, urlLogout);
        response.setHeader(GenericHeaders.X_LOGOUT_URL, urlLogout);
    }
}
