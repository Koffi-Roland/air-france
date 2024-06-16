package com.afklm.spring.security.habile;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * Habile Access Unauthorized Handler
 * 
 * This handler is used whenever the filter needs to emit a 401 HTTP error and thus putting more context related to the error
 * 
 * @author m405991
 *
 */
@Component
public class HabileAccessUnauthorizedHandler implements AuthenticationEntryPoint {

    private ResponseExceptionLogger responseExceptionLogger;

    /**
     * Constructor
     * 
     * @param responseExceptionLogger logger
     */
    public HabileAccessUnauthorizedHandler(ResponseExceptionLogger responseExceptionLogger) {
        this.responseExceptionLogger = responseExceptionLogger;
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,AuthenticationException authException) throws IOException, ServletException {
        // Add header required by HTTP HttpStatus.UNAUTHORIZED
        response.addHeader(HttpHeaders.WWW_AUTHENTICATE, "HabileAuth realm=\"Habile\", type=1, title=\"Login to habile\",");
        responseExceptionLogger.logException(response, HttpStatus.UNAUTHORIZED.value(), authException);
    }

}
