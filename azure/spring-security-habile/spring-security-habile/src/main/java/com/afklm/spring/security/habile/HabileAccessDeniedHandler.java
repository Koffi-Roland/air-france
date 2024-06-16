package com.afklm.spring.security.habile;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * Habile Access Denied Handler
 * 
 * @author M405991
 *
 */
@Component
public class HabileAccessDeniedHandler implements AccessDeniedHandler {

    private ResponseExceptionLogger responseExceptionLogger;

    /**
     * Constructor
     * 
     * @param responseExceptionLogger logger
     */
    public HabileAccessDeniedHandler(ResponseExceptionLogger responseExceptionLogger) {
        this.responseExceptionLogger = responseExceptionLogger;
    }

    @Override
    public void handle(HttpServletRequest request,HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        responseExceptionLogger.logException(response, HttpStatus.FORBIDDEN.value(), accessDeniedException);
    }

}
