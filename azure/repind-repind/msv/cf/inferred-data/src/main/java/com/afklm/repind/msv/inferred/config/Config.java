package com.afklm.repind.msv.inferred.config;

import com.afklm.repind.common.config.documentation.AbstractWebMvcConfigurer;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.afklm.repind.msv.inferred.model.GetInferredDataModel;
import com.afklm.repind.msv.inferred.entity.Inferred;

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
public class Config extends AbstractWebMvcConfigurer {
	
	/**
	 * Gets the bean mapper.
	 * @return the bean mapper.
	 */
	@Mapper
	public interface BeanMapper {

		BeanMapper INSTANCE = Mappers.getMapper(BeanMapper.class);

		@Mapping(source = "inferredData", target = "data")
		GetInferredDataModel inferredToInferredModel(Inferred inferred);
	}
	
}
