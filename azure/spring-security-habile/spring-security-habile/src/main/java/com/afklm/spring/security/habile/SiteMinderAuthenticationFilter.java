package com.afklm.spring.security.habile;

import static com.afklm.spring.security.habile.GlobalConfiguration.ANONYMOUS;
import static com.afklm.spring.security.habile.GlobalConfiguration.EMPTY_STRING;
import static com.afklm.spring.security.habile.GlobalConfiguration.NO_CREDENTIAL;
import static com.afklm.spring.security.habile.SpringSecurityHabileMessageCode.SS4H_MSG_PRINCIPAL_MISMATCH;

import java.io.IOException;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.security.web.authentication.preauth.RequestHeaderAuthenticationFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

/**
 * SiteMinder Authentication Filter
 * <p>
 * This is a customization of RequestHeaderAuthenticationFilter which overloads
 * credential retrieval to deal with a SM_SESSION cookie instead of the default
 * behaviour based on an HTTP header
 *
 * @author M405991
 */
public class SiteMinderAuthenticationFilter extends RequestHeaderAuthenticationFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(SiteMinderAuthenticationFilter.class);

    /**
     * Overriding default behavior because we are only triggered in the case of a SiteMinder process (ie. SM_USER header present).
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (isSiteMinderFilterApplicable(request)) {
            LOGGER.debug("'{}' header found, filter activated", GenericHeaders.SM_USER);
            super.doFilter(request, response, chain);
        } else {
            LOGGER.debug("No '{}' header found, skipping this filter", GenericHeaders.SM_USER);
            chain.doFilter(request, response);
        }
    }

    // SiteMinder filter is applicable when we're missing the X_ACCESS_TOKEN header and we're having the SM_USER header
    private static boolean isSiteMinderFilterApplicable(ServletRequest request) {
        HttpServletRequest httpRequest = HttpServletRequest.class.cast(request);
        return httpRequest.getHeader(GenericHeaders.SM_USER) != null &&
               httpRequest.getHeader(GenericHeaders.X_ACCESS_TOKEN) == null; // to give priority to X_ACCESS_TOKEN
    }

    @Override
    protected Object getPreAuthenticatedPrincipal(HttpServletRequest request) {
        Object principal = super.getPreAuthenticatedPrincipal(request);
        if (isSiteMinderAnonymousRequest(request)) {
            LOGGER.debug("Detecting anonymous login for {} ", principal);
            principal = ANONYMOUS;
        } else if (isWebsocketUpgradeFromAFF5(request, principal)) {
            LOGGER.debug("WebSocket detected retrieved from previous context");

            Object auth = SecurityContextHolder.getContext().getAuthentication();
            if (auth instanceof PreAuthenticatedAuthenticationToken) {
                principal = ((PreAuthenticatedAuthenticationToken) auth).getPrincipal();
                LOGGER.debug("Detecting PreAuthenticatedAuthenticationToken previous context for {}", principal);

            } else {
                principal = auth;
                LOGGER.warn(SS4H_MSG_PRINCIPAL_MISMATCH.format(PreAuthenticatedAuthenticationToken.class.getName(), auth.getClass().getName(), principal));
            }
        }
        return principal;
    }

    @Override
    protected String getPreAuthenticatedCredentials(HttpServletRequest request) {
        if (isSiteMinderAnonymousRequest(request)) {
            return ANONYMOUS;
        } else {
            return Arrays.stream(Optional.ofNullable(request.getCookies())
                .orElse(new Cookie[] {}))
                .filter(cookie -> GenericHeaders.SM_SESSION.equals(cookie.getName()))
                .map(Cookie::getValue)
                .findAny()
                .orElse(NO_CREDENTIAL);
        }
    }

    private static boolean isSiteMinderAnonymousRequest(HttpServletRequest request) {
        return (GenericHeaders.SM_USER_ANONYMOUS.equalsIgnoreCase(request.getHeader(GenericHeaders.SM_USER)) &&
                GenericHeaders.SM_USER_ANONYMOUS.equalsIgnoreCase(request.getHeader(GenericHeaders.SM_USERDN))) ||
               (GenericHeaders.SM_AUTHTYPE_ANONYMOUS.equals(request.getHeader(GenericHeaders.SM_AUTHTYPE)) &&
                Optional.ofNullable(request.getHeader(GenericHeaders.SM_USER)).filter(header -> header.equals(request.getHeader(GenericHeaders.SM_USERDN))).isPresent());
    }

    /**
     * Detects if we are in a websocket upgrade with empty SM_USER
     * (because of AF F5 configuration)
     *
     * @param request {@link HttpServletRequest}
     * @param principal {@link Object}
     * @return if it's a websocket upgrade
     */
    private static boolean isWebsocketUpgradeFromAFF5(HttpServletRequest request, Object principal) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Processing request to check Websocket Upgrade with Principal '{}' and headers: {} ", principal, collectHeadersContent(request));
        }
        return principal instanceof String &&
               EMPTY_STRING.equals(principal) &&
               HttpMethod.GET.name().equals(request.getMethod()) &&
               HttpHeaders.UPGRADE.equalsIgnoreCase(request.getHeader(HttpHeaders.CONNECTION)) &&
               GenericHeaders.WEBSOCKET.equalsIgnoreCase(request.getHeader(HttpHeaders.UPGRADE)) &&
               request.getHeader(GenericHeaders.SEC_WEBSOCKET_KEY) != null;
    }

    private static StringBuilder collectHeadersContent(HttpServletRequest request) {
        StringBuilder headersContent = new StringBuilder();
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            headersContent.append("'")
                .append(headerName)
                .append("'='")
                .append(request.getHeader(headerName))
                .append("';");
        }
        return headersContent;
    }
}
