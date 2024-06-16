package com.afklm.spring.security.habile;

import static com.afklm.spring.security.habile.SpringSecurityHabileMessageCode.SS4H_MSG_RT_ERROR;

import java.io.IOException;
import java.util.UUID;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletResponse;

/**
 * Habile Exception logger
 * 
 * @author M405991
 *
 */
@Component
public class HabileExceptionLogger implements ResponseExceptionLogger {

    private static final Log LOG = LogFactory.getLog(HabileExceptionLogger.class);

    @Override
    public void logException(HttpServletResponse response, int status, Throwable throwable) throws IOException {
        String id = UUID.randomUUID().toString();

        String message = SS4H_MSG_RT_ERROR.format(id);

        LOG.error(message, throwable);

        response.setStatus(status);
        response.addHeader(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_HTML_VALUE);
        response.getWriter().write("HTTP status " + status + " - " + message);
        // Do not commit the message since a session may be created by spring security
    }
}
