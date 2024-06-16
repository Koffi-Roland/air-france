package com.afklm.sicwscustomeridentification.config;


import com.afklm.soa.jaxws.customheader.configuration.ConsumerConfigurationBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;

@Configuration
@ComponentScan(basePackages = "com.afklm.sicwscustomeridentification", 
excludeFilters={@ComponentScan.Filter(type=FilterType.ASPECTJ, pattern = "com.afklm.sicwscustomeridentification.config.*")})
@PropertySource("classpath:/web-config.properties")
@Import({com.airfrance.repind.config.WebConfigRepind.class, WSConfig.class})
public class WebConfigIdentify {

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
