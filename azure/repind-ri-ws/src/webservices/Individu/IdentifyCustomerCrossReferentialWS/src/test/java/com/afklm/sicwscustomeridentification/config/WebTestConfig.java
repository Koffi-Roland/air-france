package com.afklm.sicwscustomeridentification.config;

import com.afklm.sicwscustomeridentification.impl.IdentifyCustomerCrossReferentialServiceV1Impl;
import com.afklm.soa.stubs.w001345.v1.IdentifyCustomerCrossReferentialServiceV1;
import com.airfrance.repind.config.WebRepindTestConfig;
import org.springframework.context.annotation.*;

@Profile("test")

@Configuration
@ComponentScan(basePackages = "com.afklm.sicwscustomeridentification", 
excludeFilters={@ComponentScan.Filter(type=FilterType.ASPECTJ, pattern = "com.afklm.sicwscustomeridentification.config.*")})
@Import(WebRepindTestConfig.class)
public class WebTestConfig {
	
	@Bean(name = "passenger_IdentifyCustomerCrossReferentialService-v1Bean")
    public IdentifyCustomerCrossReferentialServiceV1 identifyCustomerCrossReferentialServiceV1Impl() {

        return new IdentifyCustomerCrossReferentialServiceV1Impl();
    }
}
