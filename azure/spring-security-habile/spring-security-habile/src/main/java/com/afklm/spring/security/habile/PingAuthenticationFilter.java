package com.afklm.spring.security.habile;

import static com.afklm.spring.security.habile.GlobalConfiguration.ANONYMOUS;
import static com.afklm.spring.security.habile.GlobalConfiguration.EMPTY_STRING;
import static com.afklm.spring.security.habile.SpringSecurityHabileMessageCode.SS4H_MSG_RT_INCONSISTANCY_NULL_CREDENTIALS_ON_CHANGE;
import static com.afklm.spring.security.habile.SpringSecurityHabileMessageCode.SS4H_MSG_RT_INCONSISTANCY_WRONG_CREDENTIALS_ON_CHANGE;
import static com.afklm.spring.security.habile.SpringSecurityHabileMessageCode.SS4H_MSG_RT_INCONSISTANCY_WRONG_PRINCIPAL_ON_CHANGE;
import static com.afklm.spring.security.habile.oidc.JwtUtils.JWT_PREFIX;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.preauth.RequestHeaderAuthenticationFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;

/**
 * Ping Authentication Filter
 * <p>
 * This is a customization of RequestHeaderAuthenticationFilter which overloads
 * credential retrieval to deal with a SM_SESSION cookie instead of the default
 * behavior based on an HTTP header.<br/>
 * </p>
 * 
 * <p>
 * Here the principal is extracted from x-forwarded-user HTTP header. Another
 * option would be to extract the Principal from the JWT itself. It is more
 * complicated and slower because we need to parse the token just like that.
 * 
 * <pre>
 * {@code
 * &#64;Override
 * protected Object getPreAuthenticatedPrincipal(HttpServletRequest request) {
 *     String token = (String)super.getPreAuthenticatedPrincipal(request);
 *     return JwtUtils.getElementFromBody(token, "sub");
 * }
 * </pre>
 *
 * @author M405991
 * 
 * @author M408461
 */
public class PingAuthenticationFilter extends RequestHeaderAuthenticationFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(PingAuthenticationFilter.class);

    public PingAuthenticationFilter() {
        super();
        setPrincipalRequestHeader(GenericHeaders.X_FORWARDED_USER);
        setCredentialsRequestHeader(GenericHeaders.X_ACCESS_TOKEN);
    }

    /**
     * Overriding default behavior because we are only triggered in the case of a Ping process (ie.
     * x-access-token header present).
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (isPingAccessFilterApplicable(request)) {
            // I only interact when the X-ACCESS-TOKEN header is present
            LOGGER.debug("'{}' header found, filter activated", GenericHeaders.X_ACCESS_TOKEN);
            super.doFilter(request, response, chain);
        } else {
            // no X-ACCESS-TOKEN header I skip this filter
            LOGGER.debug("No '{}' header found, skipping this filter", GenericHeaders.X_ACCESS_TOKEN);
            chain.doFilter(request, response);
        }
    }

    // PingAccess filter is applicable when we're having the X_ACCESS_TOKEN header
    private static boolean isPingAccessFilterApplicable(ServletRequest request) {
        return HttpServletRequest.class.cast(request).getHeader(GenericHeaders.X_ACCESS_TOKEN) != null;
    }

    @Override
    protected Object getPreAuthenticatedPrincipal(HttpServletRequest request) {
        Object principal;
        if (isHabileAnonymousRequest(request)) {
            principal = ANONYMOUS;
        } else {
            principal = super.getPreAuthenticatedPrincipal(request);
        }
        return principal;
    }

    @Override
    protected Object getPreAuthenticatedCredentials(HttpServletRequest request) {
        // prefixed with jwt for performance purpose (way faster than to check
        // the format when in HabileAuthenticationUserDetails)
        Object credentials;
        if (isHabileAnonymousRequest(request)) {
            LOGGER.debug("Detecting anonymous login");
            credentials = ANONYMOUS;
        } else {
            credentials = JWT_PREFIX + super.getPreAuthenticatedCredentials(request);
        }

        return credentials;
    }

    private static boolean isHabileAnonymousRequest(HttpServletRequest request) {
        return EMPTY_STRING.equals(request.getHeader(GenericHeaders.X_FORWARDED_USER)) &&
               EMPTY_STRING.equals(request.getHeader(GenericHeaders.X_ACCESS_TOKEN));
    }

    /**
     * The idea here is to make sure the JWT is updated in case it has expired and been replaced by an updated one. The
     * user behind this JWT has not changed (nor the X_FORWARDED_USER token), so there is no Principal change to be
     * applied but if the application needs the JWT in requests to third parties, we have to make sure that the one in
     * the Principal is the up2date one. We do not change the result obtained by the invocation of super.principalChanged().
     * @param request
     * @param currentAuthentication
     * @return
     */
    @Override
    protected boolean principalChanged(HttpServletRequest request, Authentication currentAuthentication) {
        boolean principalChanged = super.principalChanged(request, currentAuthentication);
        if (!principalChanged) {
            Object credentials = getPreAuthenticatedCredentials(request);
            // do not throw exception in this method, they are not caught by any component
            // we could simplify the tests, but the point here is to track and identify inconsistency
            if (credentials==null) {
                LOGGER.warn(SS4H_MSG_RT_INCONSISTANCY_NULL_CREDENTIALS_ON_CHANGE.format());
            } else if (!(credentials instanceof String)) {
                LOGGER.warn(SS4H_MSG_RT_INCONSISTANCY_WRONG_CREDENTIALS_ON_CHANGE.format(credentials.getClass().getName()));
            } else if (!(currentAuthentication.getPrincipal() instanceof HabileUserDetails)) {
                LOGGER.warn(SS4H_MSG_RT_INCONSISTANCY_WRONG_PRINCIPAL_ON_CHANGE.format(currentAuthentication.getPrincipal().getClass().getName()));
            } else {
                String currentToken = ((HabileUserDetails) currentAuthentication.getPrincipal()).getIdToken();
                if (!(JWT_PREFIX + currentToken).equals(credentials)) {
                    String newToken = String.class.cast(credentials).substring(JWT_PREFIX.length());
                    LOGGER.debug("JWT updated from {} to {}", HabileUserDetails.class.cast(currentAuthentication.getPrincipal()).getIdToken(), newToken);
                    HabileUserDetails.class.cast(currentAuthentication.getPrincipal()).setIdToken(newToken);
                }
            }
        }
        return principalChanged;
    }
}
