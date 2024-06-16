package com.afklm.repind.msv.doctor.attributes.config;

import com.afklm.repind.common.config.documentation.AbstractWebMvcConfigurer;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.ResourceHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.ArrayList;
import java.util.List;

@EnableWebMvc
@Configuration
@PropertySource("classpath:/swagger.properties")
public class Config extends AbstractWebMvcConfigurer {
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
}
