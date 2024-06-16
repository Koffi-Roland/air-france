package com.afklm.spring.security.habile;

import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import static org.mockito.Mockito.mock;

// @Configuration
public class PingConfiguration {

    @Bean
    public RestTemplate pingRestTemplate() {
        RestTemplate mockedBean = mock(RestTemplate.class);
        return mockedBean;
    }
}
