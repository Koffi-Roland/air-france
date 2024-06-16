package com.afklm.repind.config;

import com.afklm.repind.enrollmyaccountcustomerws.EnrollMyAccountCustomerImpl;
import com.afklm.repind.v2.enrollmyaccountcustomerws.EnrollMyAccountCustomerV2Impl;
import com.afklm.repind.v3.enrollmyaccountcustomerws.EnrollMyAccountCustomerV3Impl;
import com.afklm.soa.jaxws.spring.SpringBindingBuilder;
import com.afklm.soa.stubs.w000431.v1.EnrollMyAccountCustomerServiceV1;
import com.afklm.soa.stubs.w000431.v2.EnrollMyAccountCustomerServiceV2;
import com.afklm.soa.stubs.w000431.v3.EnrollMyAccountCustomerServiceV3;
import com.sun.xml.ws.transport.http.servlet.SpringBinding;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WSConfig {
    
	/*
	 *  EnrollMyAccountCustomerV1
	 * 
	 */
    @Bean
    public SpringBinding wsEnrollMyAccountCustomerServiceV1() throws Exception {

        SpringBindingBuilder springBindingBuilder = new SpringBindingBuilder();
        springBindingBuilder.bean(enrollMyAccountCustomerImpl()).url("/ws/passenger/marketing/000431v01");
        return springBindingBuilder.build();
    }

    @Bean
    public EnrollMyAccountCustomerServiceV1 enrollMyAccountCustomerImpl() {

        return new EnrollMyAccountCustomerImpl();
    }

    /*
	 *  EnrollMyAccountCustomerV2
	 * 
	 */
    @Bean
    public SpringBinding wsEnrollMyAccountCustomerServiceV2() throws Exception {

        SpringBindingBuilder springBindingBuilder = new SpringBindingBuilder();
        springBindingBuilder.bean(enrollMyAccountCustomerV2Impl()).url("/ws/passenger/marketing/000431v02");
        return springBindingBuilder.build();
    }

    @Bean
    public EnrollMyAccountCustomerServiceV2 enrollMyAccountCustomerV2Impl() {

        return new EnrollMyAccountCustomerV2Impl();
    }

    /*
	 *  EnrollMyAccountCustomerV3
	 * 
	 */
    @Bean
    public SpringBinding wsEnrollMyAccountCustomerServiceV3() throws Exception {

        SpringBindingBuilder springBindingBuilder = new SpringBindingBuilder();
        springBindingBuilder.bean(enrollMyAccountCustomerV3Impl()).url("/ws/passenger/marketing/000431v03");
        return springBindingBuilder.build();
    }

    @Bean
    public EnrollMyAccountCustomerServiceV3 enrollMyAccountCustomerV3Impl() {

        return new EnrollMyAccountCustomerV3Impl();
    }
}
