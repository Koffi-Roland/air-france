package com.afklm.repind.msv.automatic.merge.config;

import com.afklm.repind.common.config.documentation.AbstractWebMvcConfigurer;
import com.afklm.repind.msv.automatic.merge.config.logging.LogInterceptor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.ResourceHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;

import java.util.ArrayList;
import java.util.List;

@Configuration
@Import({ BeanValidatorPluginsConfiguration.class})
public class Config extends AbstractWebMvcConfigurer {

    @Autowired
    LogInterceptor logInterceptor;

    @Override
    public void configureMessageConverters(final List<HttpMessageConverter<?>> converters) {

        final List<MediaType> mediaTypes = new ArrayList<>();
        mediaTypes.add(MediaType.APPLICATION_JSON);

        final MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setSupportedMediaTypes(mediaTypes);

        final ObjectMapper objectMapper = converter.getObjectMapper();
        configureJSonMapper(objectMapper);

        converters.add(converter);
        converters.add(new ResourceHttpMessageConverter());
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(logInterceptor).excludePathPatterns("/infra/healthcheck");
    }
}
