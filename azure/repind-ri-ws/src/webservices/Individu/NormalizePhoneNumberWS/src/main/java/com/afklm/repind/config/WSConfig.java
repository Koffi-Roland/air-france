package com.afklm.repind.config;

import com.afklm.repind.ws.normalizephonenumber.v1.NormalizePhoneNumberServiceV1Impl;
import com.afklm.soa.jaxws.spring.SpringBindingBuilder;
import com.afklm.soa.stubs.w001070.v1.NormalizePhoneNumberServiceV1;
import com.sun.xml.ws.transport.http.servlet.SpringBinding;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WSConfig {
    
	/*
	 *  NormalizePhoneNumberServiceV1
	 * 
	 */
    @Bean
    public SpringBinding wsNormalizePhoneNumberServiceV1() throws Exception {

        SpringBindingBuilder springBindingBuilder = new SpringBindingBuilder();
        springBindingBuilder.bean(normalizePhoneNumberServiceV1Impl()).url("/ws/passenger/marketing/001070v01");
        return springBindingBuilder.build();
    }

    @Bean
    public NormalizePhoneNumberServiceV1 normalizePhoneNumberServiceV1Impl() {

        return new NormalizePhoneNumberServiceV1Impl();
    }

}
