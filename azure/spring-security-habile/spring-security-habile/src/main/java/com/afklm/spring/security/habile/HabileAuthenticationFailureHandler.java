package com.afklm.spring.security.habile;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * Strategy used to handle a failed authentication attempt within AFKL.<br/>
 * The idea is to provide a uuid that can be used to easily identify the root
 * cause of the authentication failure.
 * 
 * @author TECC
 *
 */
@Component
public class HabileAuthenticationFailureHandler implements AuthenticationFailureHandler {

    private ResponseExceptionLogger responseExceptionLogger;

    /**
     * Constructor
     * 
     * @param responseExceptionLogger logger
     */
    public HabileAuthenticationFailureHandler(ResponseExceptionLogger responseExceptionLogger) {
        this.responseExceptionLogger = responseExceptionLogger;
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        responseExceptionLogger.logException(response, HttpStatus.UNAUTHORIZED.value(), exception);
    }
}
