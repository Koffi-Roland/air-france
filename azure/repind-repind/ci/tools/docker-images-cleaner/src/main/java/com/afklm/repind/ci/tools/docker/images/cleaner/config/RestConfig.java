package com.afklm.repind.ci.tools.docker.images.cleaner.config;

import com.afklm.repind.ci.tools.docker.images.cleaner.rest.RestTemplateExtended;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.util.DefaultUriBuilderFactory;

@Configuration
public class RestConfig {
    @Value("${harbor.url}")
    private String harborUrl;

    @Value("${harbor.project}")
    private String harborProject;

    @Value("${harbor.account.username}")
    private String username;

    @Value("${harbor.account.pass}")
    private String pass;

    @Bean(name = "harborApi")
    public RestTemplateExtended harborApi() {
        RestTemplateExtended restTemplate = new RestTemplateExtended();
        restTemplate.setUsername(username);
        restTemplate.setPass(pass);
        restTemplate.setProject(harborProject);
        restTemplate.setUriTemplateHandler(new DefaultUriBuilderFactory(harborUrl));
        return restTemplate;
    }

}
