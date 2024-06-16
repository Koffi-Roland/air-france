package com.afklm.spring.security.habile;

import static com.afklm.spring.security.habile.SpringSecurityHabileMessageCode.SS4H_MSG_MISSING_AUTH_HEADER;

import java.io.IOException;
import java.util.Enumeration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;
import org.springframework.security.web.util.UrlUtils;
import org.springframework.util.Assert;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * AFKLM Authentication Filter
 * <p>
 * This is a filter whose role is only to check if one of the authentication
 * header pushed by our reverse proxy is present. It checks if either <b>SM_USER</b>
 * in case of Habile/SiteMinder protection, <b>x-access-token</b> in case of Ping protection or
 * <b>secgw_user</b> in case of SecMobile scenario is present. If none is detected then this filter
 * triggers an {@link AccessDeniedException} exception.
 *
 * @author M408461
 */
public class AFKLMAuthenticationFilter extends OncePerRequestFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(AFKLMAuthenticationFilter.class);
    private AccessDeniedHandler accessDeniedHandler = new AccessDeniedHandlerImpl();

    private final static String AUTHENTICATION_ERROR_MESSAGE = "AF/KLM authentication header error";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        request.setAttribute(HttpServletResponse.class.getName(), response);

        if (hasExpectedAuthenticationHeader(request)) {
            filterChain.doFilter(request, response);
        } else {
            LOGGER.debug(SS4H_MSG_MISSING_AUTH_HEADER.format(UrlUtils.buildFullRequestUrl(request)));
            logHeaders(request);
            this.accessDeniedHandler.handle(request, response, new AccessDeniedException(AUTHENTICATION_ERROR_MESSAGE));
        }
    }

    public void setAccessDeniedHandler(AccessDeniedHandler accessDeniedHandler) {
        Assert.notNull(accessDeniedHandler, "accessDeniedHandler cannot be null");
        this.accessDeniedHandler = accessDeniedHandler;
    }

    private static boolean hasExpectedAuthenticationHeader(HttpServletRequest request) {
        return request.getHeader(GenericHeaders.SM_USER) != null ||
               request.getHeader(GenericHeaders.X_ACCESS_TOKEN) != null ||
               request.getHeader(GenericHeaders.SECGW_USER) != null;
    }

    private static void logHeaders(HttpServletRequest request) {
        if (LOGGER.isTraceEnabled()) {
            Enumeration<String> headerNames = request.getHeaderNames();
            if (headerNames != null) {
                LOGGER.trace("Dumping HTTP headers");
                while (headerNames.hasMoreElements()) {
                    String headerName = headerNames.nextElement();
                    LOGGER.trace("Header Name '{}' with value '{}'", headerName, request.getHeader(headerName));
                }
            }
        }
    }
}
