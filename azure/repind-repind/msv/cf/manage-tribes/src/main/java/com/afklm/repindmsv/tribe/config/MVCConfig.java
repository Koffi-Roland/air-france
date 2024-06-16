package com.afklm.repindmsv.tribe.config;

import java.util.ArrayList;
import java.util.List;

import com.afklm.repind.common.config.documentation.AbstractWebMvcConfigurer;
import com.afklm.repind.common.config.documentation.CommonConfig;
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
import com.fasterxml.jackson.databind.ObjectMapper;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.afklm.repindmsv.tribe.model.TribeModel;
import com.afklm.repindmsv.tribe.entity.node.Tribe;
import com.afklm.repindmsv.tribe.model.MemberModel;
import com.afklm.repindmsv.tribe.entity.node.Member;

/**
 * MVC config
 *
 * @author t528182
 *
 */
@Configuration
@EnableWebMvc
@EnableSwagger2
@PropertySource("classpath:/swagger.properties")
@EnableNeo4jRepositories("com.afklm.repindmsv.tribe.repository")
@EntityScan("com.afklm.repindmsv.tribe.entity")
@Import({BeanValidatorPluginsConfiguration.class, CommonConfig.class})
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})

public class MVCConfig extends AbstractWebMvcConfigurer {

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
	
	/**
	 * Gets the bean mapper.
	 * @return the bean mapper.
	 */


	@Mapper
	public interface BeanMapper {

		@Mapping(source = "creationSite", target = "signature.creationSite")
		@Mapping(source = "creationSignature", target = "signature.creationSignature")
		@Mapping(source = "creationDate", target = "signature.creationDate")
		@Mapping(source = "modificationSite", target = "signature.modificationSite")
		@Mapping(source = "modificationSignature", target = "signature.modificationSignature")
		@Mapping(source = "modificationDate", target = "signature.modificationDate")
		TribeModel tribeToTribeModel(Tribe tribe);

		@Mapping(source = "creationSite", target = "signature.creationSite")
		@Mapping(source = "creationSignature", target = "signature.creationSignature")
		@Mapping(source = "creationDate", target = "signature.creationDate")
		@Mapping(source = "modificationSite", target = "signature.modificationSite")
		@Mapping(source = "modificationSignature", target = "signature.modificationSignature")
		@Mapping(source = "modificationDate", target = "signature.modificationDate")
		MemberModel memberToMemberModel(Member member);
	}
	
}
