package com.afklm.spring.security.habile.proxy.controller;

import com.afklm.spring.security.habile.proxy.model.UserInformation;
import com.afklm.spring.security.habile.proxy.service.ConfigurationService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

/**
 * Authorities REST Controller.
 *
 * @author TECCSE
 *
 */
@RestController
@RequestMapping("/mock/authorities")
public class AuthoritiesConfigurationController {

    private final ConfigurationService authoritiesConfigurationService;

    /**
     * Constructor
     * 
     * @param authoritiesConfigurationService
     */
    public AuthoritiesConfigurationController(ConfigurationService authoritiesConfigurationService) {
        this.authoritiesConfigurationService = authoritiesConfigurationService;
    }

    /**
     * Get authorities
     * 
     * @return authorities
     */
    @GetMapping
    public Collection<UserInformation> getAuthorities() {
        return authoritiesConfigurationService.getUserInformationList();
    }
}
