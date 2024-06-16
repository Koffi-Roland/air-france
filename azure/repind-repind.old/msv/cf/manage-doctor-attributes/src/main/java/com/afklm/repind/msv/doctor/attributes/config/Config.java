package com.afklm.repind.msv.doctor.attributes.config;


import com.afklm.repind.common.config.documentation.AbstractWebMvcConfigurer;
import com.afklm.repind.common.config.documentation.CommonConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.ResourceHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import java.util.ArrayList;
import java.util.List;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;

@Configuration
@EnableWebMvc
@EnableSwagger2
@PropertySource("classpath:/swagger.properties")
@EnableNeo4jRepositories("com.afklm.repind.msv.doctor.attributes.repository")
@EntityScan("com.afklm.repind.msv.doctor.attributes.entity")
@Import({BeanValidatorPluginsConfiguration.class , CommonConfig.class})
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})

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
