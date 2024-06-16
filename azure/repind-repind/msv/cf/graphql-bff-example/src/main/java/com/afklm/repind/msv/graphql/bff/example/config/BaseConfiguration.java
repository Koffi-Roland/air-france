package com.afklm.repind.msv.graphql.bff.example.config;

import com.afklm.repind.msv.graphql.bff.example.helper.RestTemplateExtended;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;

/**
 * A base configuration bootstrapping the application context.
 */

@Configuration
@RequiredArgsConstructor
public class BaseConfiguration {

    /**
     * Spring core environment - inject by spring
     */
    private final Environment env;

    /**
     * Rest template provide last activity  client configuration
     *
     * @return rest template
     */
    @Bean(name = "lastActivityRestTemplate")
    public RestTemplateExtended lastActivityRestTemplate()
    {
        RestTemplateExtended restTemplate = new RestTemplateExtended();
        restTemplate.setApiKey(env.getProperty("mashery.key.provide-last-activity"));
        restTemplate.setApiSecret(env.getProperty("mashery.secret.provide-last-activity"));
        return restTemplate;
    }

    /**
     * Rest template provide contact data  client configuration
     *
     * @return rest template
     */
    @Bean(name = "emailRestTemplate")
    public RestTemplateExtended emailRestTemplate()
    {
        RestTemplateExtended restTemplate = new RestTemplateExtended();
        restTemplate.setApiKey(env.getProperty("mashery.key.provide-contact-data"));
        restTemplate.setApiSecret(env.getProperty("mashery.secret.provide-contact-data"));
        return restTemplate;
    }

}
