package com.afklm.spring.security.habile;

import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * Exception logger
 * 
 * @author M405991
 *
 */
public interface ResponseExceptionLogger {

    /**
     * Log exception
     * 
     * @param response response
     * @param status status
     * @param throwable exception
     *            An UUID is generated to enable correlation between server logs and consumer
     * @throws IOException if error occurs
     */
    public void logException(HttpServletResponse response, int status, Throwable throwable) throws IOException;
}
