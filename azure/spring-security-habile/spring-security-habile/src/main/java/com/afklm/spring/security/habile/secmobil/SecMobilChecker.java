package com.afklm.spring.security.habile.secmobil;

import com.afklm.secmobilchecker.SecurityCheck;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;

/**
 * SecMobil Checker
 * 
 * @author m405991
 *
 */
@Component("habileSecMobilChecker")
@ConditionalOnClass(SecurityCheck.class)
public class SecMobilChecker {

    /**
     * Check secmobil headers
     * 
     * Delegate to security
     * 
     * @param request request
     * @throws SecurityException
     */
    public void checkSecMobilHeaders(HttpServletRequest request) throws SecurityException {
        SecurityCheck.check(request::getHeader);
    }
}