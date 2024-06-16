package com.afklm.repind.config;

import com.afklm.repind.searchindividualbymulticriteriaws.SearchIndividualByMulticriteriaImpl;
import com.afklm.soa.jaxws.spring.SpringBindingBuilder;
import com.afklm.soa.stubs.w001271.v2.SearchIndividualByMulticriteriaServiceV2;
import com.sun.xml.ws.transport.http.servlet.SpringBinding;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WSConfig {
    
	/*
	 *  SearchIndividualByMulticriteriaServiceV2
	 * 
	 */
    @Bean
    public SpringBinding wsSearchIndividualByMulticriteriaServiceV2() throws Exception {

        SpringBindingBuilder springBindingBuilder = new SpringBindingBuilder();
        springBindingBuilder.bean(searchIndividualByMulticriteriaImpl()).url("/ws/passenger/marketing/001271v02");
        return springBindingBuilder.build();
    }

    @Bean
    public SearchIndividualByMulticriteriaServiceV2 searchIndividualByMulticriteriaImpl() {

        return new SearchIndividualByMulticriteriaImpl();
    }

	
    
}
