package com.afklm.repind.config;

import com.afklm.soa.jaxws.customheader.configuration.ConsumerConfigurationBean;
import org.springframework.context.annotation.*;

@Configuration
@ComponentScan(basePackages = "com.afklm.repind", 
excludeFilters={@ComponentScan.Filter(type=FilterType.ASPECTJ, pattern = "com.afklm.repind.config.*")})
@PropertySource("classpath:/web-config.properties")
@Import({com.airfrance.repind.config.WebConfigRepind.class, WSConfig.class})
public class WebConfigNormalize {

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
