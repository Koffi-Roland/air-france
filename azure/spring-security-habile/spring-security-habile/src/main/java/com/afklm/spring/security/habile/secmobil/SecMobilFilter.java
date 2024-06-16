package com.afklm.spring.security.habile.secmobil;

import com.afklm.secmobilchecker.SecurityCheck;
import com.afklm.spring.security.habile.GenericHeaders;
import com.afklm.spring.security.habile.HabileAuthenticationFailureHandler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * SecMobil filter
 * 
 * Class used to test if the url contains the secMobil urlPattern.
 * If it is the case launch the security test
 * 
 * @author M312697
 *
 */
@Component("habileSecMobilFilter")
@ConditionalOnClass(SecurityCheck.class)
public class SecMobilFilter extends OncePerRequestFilter {
    @Autowired
    private SecMobilChecker secMobilChecker;

    @Autowired
    private HabileAuthenticationFailureHandler handler;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        if (isSecMobil(request)) {
            try {
                secMobilChecker.checkSecMobilHeaders(request);
                // Simulate presence of SM_USER to provide security context
                HttpServletRequestSecMobil wrapper = new HttpServletRequestSecMobil(request,
                    request.getHeader(GenericHeaders.SECGW_USER));
                filterChain.doFilter(wrapper, response);
            } catch (SecurityException e) {
                handler.onAuthenticationFailure(request,
                    response,
                    new AuthenticationServiceException("Error secMobil during header validation", e));
            }
        } else {
            filterChain.doFilter(request, response);
        }

    }

    private boolean isSecMobil(HttpServletRequest request) {
        String secgwUser = request.getHeader(GenericHeaders.SECGW_USER);
        String secgwTs = request.getHeader(GenericHeaders.SECGW_TS);
        String secgwCheck = request.getHeader(GenericHeaders.SECGW_CHECK);

        return (secgwUser != null) && (secgwTs != null) && (secgwCheck != null);
    }
}
