package com.afklm.spring.security.habile;

import java.nio.charset.StandardCharsets;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationProvider;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

import com.afklm.spring.security.habile.userdetails.HabileAuthenticationUserDetailsService;

/**
 * Since SpringBoot 2.6, there is an automatic behavior that detects circle dependencies between beans.
 * In order to avoid such error (but what circle was identified???), the beans needed by our SecurityConfig bean
 * have been moved in this class.
 * @Configuration
 * @author m408461
 *
 */
@Configuration
public class SecurityBeans {

    /**
     * PreAuthProvider
     *
     * @return PreAuthenticatedAuthenticationProvider
     */
    @Bean
    public PreAuthenticatedAuthenticationProvider preauthAuthProvider() {
        PreAuthenticatedAuthenticationProvider preauthAuthProvider = new PreAuthenticatedAuthenticationProvider();
        preauthAuthProvider.setPreAuthenticatedUserDetailsService(habileAuthenticationUserDetailsService());
        return preauthAuthProvider;
    }

    /**
     * Bean HabileAuthenticationUserDetailsService
     *
     * @return HabileAuthenticationUserDetailsService
     */
    @Bean
    public AuthenticationUserDetailsService<PreAuthenticatedAuthenticationToken> habileAuthenticationUserDetailsService() {
        return new HabileAuthenticationUserDetailsService();
    }
    
    /**
     * Custom message resource bean in order not to be poisoned if client application is also defining one.
     */
    @Bean(name = "messageResourceSS4H")
    public MessageSource messageResource() {
        ResourceBundleMessageSource messageBundleResrc = new ResourceBundleMessageSource();
        messageBundleResrc.setBasename("messages");
        messageBundleResrc.setDefaultEncoding(StandardCharsets.UTF_8.name());
        return messageBundleResrc;
    }
}
