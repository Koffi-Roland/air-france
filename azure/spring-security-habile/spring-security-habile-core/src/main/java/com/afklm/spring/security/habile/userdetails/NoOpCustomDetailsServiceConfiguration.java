package com.afklm.spring.security.habile.userdetails;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * No-op {@link CustomDetailsService} configuration used when no {@link CustomDetailsService}
 * bean is detected.
 */
@Configuration
@ConditionalOnMissingBean(CustomUserDetailsService.class)
public class NoOpCustomDetailsServiceConfiguration {

    @Bean
    public CustomUserDetailsService noOpUserDetailsService() {
        return new NoOpCustomUserDetailsService();
    }
}