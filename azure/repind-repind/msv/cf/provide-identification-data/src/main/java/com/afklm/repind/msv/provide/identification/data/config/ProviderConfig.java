package com.afklm.repind.msv.provide.identification.data.config;

import com.afklm.apim.security.filter.provider.JwtValidationConfig;
import com.afklm.apim.security.filter.provider.JwtValidationSecurityFilter;
import com.afklm.apim.security.filter.provider.JwtValidatorService;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.Ordered;

@Configuration
@Import({JwtValidationConfig.class})
public class ProviderConfig {
    @Bean
    public FilterRegistrationBean<JwtValidationSecurityFilter> apimSecurityFilter(JwtValidatorService jwtValidatorService, JwtValidationConfig jwtValidationConfig) {
        FilterRegistrationBean<JwtValidationSecurityFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new JwtValidationSecurityFilter(jwtValidatorService, jwtValidationConfig));
        registrationBean.addUrlPatterns("/*");
        registrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return registrationBean;
    }
}
