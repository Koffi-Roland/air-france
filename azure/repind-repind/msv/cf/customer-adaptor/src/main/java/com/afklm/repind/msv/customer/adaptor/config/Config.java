package com.afklm.repind.msv.customer.adaptor.config;

import com.afklm.repind.common.config.documentation.AbstractWebMvcConfigurer;
import com.afklm.repind.common.config.documentation.CommonConfig;
import com.afklm.repind.msv.customer.adaptor.client.sfmc.individus.global.IndividusResponseModel;
import com.afklm.repind.msv.customer.adaptor.client.sfmc.individus.upsert.UpsertIndividusClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;

import java.time.Duration;

@Configuration
@PropertySource("classpath:/swagger.properties")
@Import({BeanValidatorPluginsConfiguration.class, CommonConfig.class})
public class Config extends AbstractWebMvcConfigurer {

    @Value("${services.request.upsert.individus.url}")
    private String upsertSfmcProfileUrl;

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder){
        return restTemplateBuilder
                .setConnectTimeout(Duration.ofMillis(5000))
                .setReadTimeout(Duration.ofMillis(5000))
                .build();
    }

    @Bean
    public UpsertIndividusClient upsertIndividusClient() {
        return new UpsertIndividusClient(upsertSfmcProfileUrl, HttpMethod.POST, IndividusResponseModel.class);
    }

}
