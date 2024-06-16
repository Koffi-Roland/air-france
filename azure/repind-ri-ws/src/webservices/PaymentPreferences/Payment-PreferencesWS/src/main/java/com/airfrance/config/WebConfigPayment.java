package com.airfrance.config;

import com.afklm.soa.jaxws.customheader.configuration.ConsumerConfigurationBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;

@Configuration
@ComponentScan(basePackages = "com.afklm.repindpp",
excludeFilters={@ComponentScan.Filter(type=FilterType.ASPECTJ, pattern = "com.afklm.repind.config.*")})
@PropertySource("classpath:/web-config.properties")
@Import({com.airfrance.repind.config.WebConfig.class, WSConfig.class})
public class WebConfigPayment {

    @Autowired
    private Environment env;
	
    /**
     * Common bean for consumer identification
     * @return ConsumerConfigurationBean
     */
    @Bean
    public ConsumerConfigurationBean wsConsumerIdentification() {

        ConsumerConfigurationBean result = new ConsumerConfigurationBean();
        result.setConsumerID("w04776476");
        result.setConsumerLocation("QVI");
        result.setConsumerType("A");
        result.setPartyID("AF");
        return result;
    }
    
}
