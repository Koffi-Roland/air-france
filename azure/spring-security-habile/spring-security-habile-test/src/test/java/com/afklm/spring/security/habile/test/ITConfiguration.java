package com.afklm.spring.security.habile.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;

import java.io.IOException;

@Configuration
@ComponentScan("com.afklm.spring.security.habile.test")
public class ITConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(ITConfiguration.class);

    @Value("${server.http.port.simul:8001}")
    private String simulPort;

    @Value("${server.http.port.demo:8080}")
    private String demoPort;

    @Autowired
    private SessionInterceptor sessionInterceptor;

    @Autowired
    private AuthenticationInterceptor authenticationInterceptor;

    private static class NoOpResponseErrorHandler extends DefaultResponseErrorHandler {

        @Override
        public void handleError(ClientHttpResponse response) throws IOException {}

    }

    @Bean(name = "simulRestTemplate")
    public RestTemplate simulRestTemplate() {
        RestTemplate result = new RestTemplate();
        LOGGER.info("Targeting simulRestTemplate with http://localhost:{}", simulPort);
        result.setUriTemplateHandler(new DefaultUriBuilderFactory("http://localhost:" + simulPort));
        result.getInterceptors().add(sessionInterceptor);
        result.setErrorHandler(new NoOpResponseErrorHandler());
        return result;
    }

    @Bean(name = "demoRestTemplate")
    public RestTemplate demoRestTemplate() {
        RestTemplate result = new RestTemplate();
        LOGGER.info("Targeting demoRestTemplate with http://localhost:{}", demoPort);
        result.setUriTemplateHandler(new DefaultUriBuilderFactory("http://localhost:" + demoPort));
        result.getInterceptors().add(authenticationInterceptor);
        result.getInterceptors().add(sessionInterceptor);
        result.setErrorHandler(new NoOpResponseErrorHandler());
        return result;
    }

    @Bean
    public ProcessChecker processChecker() {
        return new ProcessChecker(demoPort, simulPort);
    }
}
