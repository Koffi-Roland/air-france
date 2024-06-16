package com.afklm.spring.security.habile.proxy;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.DefaultApplicationArguments;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

@Configuration
public class SimulTestConfiguration {

    @Bean
    @Primary
    @Profile("test")
    public ApplicationArguments anonymousConfig() {
        return new DefaultApplicationArguments(new String[] {"--config.simul=src/test/resources/anonymousConfig.json"});
    }

    @Bean
    @Primary
    @Profile("single")
    public ApplicationArguments singleConfiguration() {
        return new DefaultApplicationArguments(new String[] {"--config.simul=src/test/resources/singleConfig.json"});
    }

    @Bean
    @Primary
    @Profile("angular")
    public ApplicationArguments angularConfiguration() {
        return new DefaultApplicationArguments(new String[] {"--config.simul=src/test/resources/singleConfig.json"});
    }
}
