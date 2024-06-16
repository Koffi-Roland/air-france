package com.afklm.repind.msv.mapping.config;

import com.afklm.repind.common.config.documentation.AbstractWebMvcConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.afklm.repind.msv.mapping.entity.RefMarketCountryLanguage;
import com.afklm.repind.msv.mapping.model.MappingLanguageModel;

/**
 * MVC config
 *
 * @author t412211
 *
 */
@Configuration
@EnableWebMvc
@PropertySource("classpath:/swagger.properties")
@PropertySource("classpath:/web-config.properties")
public class Config extends AbstractWebMvcConfigurer {

	@Autowired
	Environment env;

	/**
	 * Gets the bean mapper.
	 * 
	 * @return the bean mapper.
	 */
	@Mapper
	public interface BeanMapper {

		@Mapping(source = "refMarketCountryLanguageId.scodeMarket", target = "market")
		@Mapping(source = "refMarketCountryLanguageId.scodeLanguageNOISO", target = "codeLanguageNoISO")
		@Mapping(source = "refMarketCountryLanguageId.scodeLanguageISO", target = "codeLanguageISO")
		@Mapping(source = "refMarketCountryLanguageId.scodeContext", target = "context")
		MappingLanguageModel refMarketCountryLanguageToMappingLanguageModel(RefMarketCountryLanguage refMarketCountryLanguage);
	}
}
