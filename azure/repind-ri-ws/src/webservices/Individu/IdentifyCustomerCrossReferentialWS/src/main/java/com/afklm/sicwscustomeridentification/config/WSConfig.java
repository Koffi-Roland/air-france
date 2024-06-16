package com.afklm.sicwscustomeridentification.config;

import com.afklm.sicwscustomeridentification.impl.IdentifyCustomerCrossReferentialServiceV1Impl;
import com.afklm.soa.jaxws.spring.SpringBindingBuilder;
import com.afklm.soa.stubs.w001345.v1.IdentifyCustomerCrossReferentialServiceV1;
import com.sun.xml.ws.transport.http.servlet.SpringBinding;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WSConfig {
    
	/*
	 *  IdentifyCustomerCrossReferentialServiceV1
	 * 
	 */
    @Bean
    public SpringBinding wsIdentifyCustomerCrossReferentialServiceV1() throws Exception {

        SpringBindingBuilder springBindingBuilder = new SpringBindingBuilder();
        springBindingBuilder.bean(identifyCustomerCrossReferentialServiceV1Impl()).url("/ws/passenger/marketing/001345v01");
        return springBindingBuilder.build();
    }

    @Bean
    public IdentifyCustomerCrossReferentialServiceV1 identifyCustomerCrossReferentialServiceV1Impl() {

        return new IdentifyCustomerCrossReferentialServiceV1Impl();
    }

}
