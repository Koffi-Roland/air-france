package com.afklm.spring.security.habile.demoweb;

import com.afklm.soa.spring.consumer.WebServiceConsumer;
import com.afklm.soa.spring.consumer.WebServiceConsumerBuilder;
import com.afklm.soa.stubs.w000479.v1.ProvideUserRightsAccessV10;
import com.afklm.soa.stubs.w000479.v1.ProvideUserRightsAccessV10_Service;
import com.afklm.spring.security.habile.EntryPointConfiguration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * Application configuration
 * 
 * @author m405991
 *
 */
@Configuration
@Import(EntryPointConfiguration.class)
@PropertySource("classpath:/ws.properties")
@PropertySource("classpath:/application.properties")
@EnableConfigurationProperties
@EnableWebMvc
public class AppConfig {
    @Autowired
    Environment env;

    /**
     * W000479 consumer bean
     * 
     * @return
     * @throws Exception
     */
    @Bean
    public WebServiceConsumer<ProvideUserRightsAccessV10> wsProvideUserRightsAccessV10() throws Exception {
        WebServiceConsumerBuilder<ProvideUserRightsAccessV10> webServiceConsumerBuilder = new WebServiceConsumerBuilder<>();
        webServiceConsumerBuilder.service(ProvideUserRightsAccessV10_Service.class)
            .wsdlLocation("classpath:/META-INF/wsdl/w000479-v01/w000479-v01.wsdl")
            .endpointAddress(env.getProperty("wsdl.address.w000479-v01"));
        return webServiceConsumerBuilder.getWebServiceConsumer();
    }
}
