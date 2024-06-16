package com.afklm.repind.config;

import com.afklm.repind.ws.provideginforuserid.ProvideGinForUserIdImpl;
import com.afklm.soa.jaxws.spring.SpringBindingBuilder;
import com.afklm.soa.stubs.w000422.v1.ProvideGinForUserIdServiceV1;
import com.sun.xml.ws.transport.http.servlet.SpringBinding;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WSConfig {
    
	/*
	 *  ProvideGinForUserIdServiceV1
	 * 
	 */
    @Bean
    public SpringBinding wsProvideGinForUserIdServiceV1() throws Exception {

        SpringBindingBuilder springBindingBuilder = new SpringBindingBuilder();
        springBindingBuilder.bean(provideGinForUserIdImpl()).url("/ws/passenger/marketing/000422v01");
        return springBindingBuilder.build();
    }

    @Bean
    public ProvideGinForUserIdServiceV1 provideGinForUserIdImpl() {

        return new ProvideGinForUserIdImpl();
    }
}
