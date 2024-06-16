package com.afklm.repind.msv.preferences.config;

import com.afklm.repind.common.config.documentation.AbstractWebMvcConfigurer;
import com.afklm.repind.msv.preferences.entity.Preference;
import com.afklm.repind.msv.preferences.model.GetUltimatePreferencesModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * MVC config
 *
 * @author t412211
 *
 */
@Configuration
@PropertySource("classpath:/swagger.properties")
@PropertySource("classpath:/web-config.properties")
public class Config extends AbstractWebMvcConfigurer {

	/**
	 * Gets the bean mapper.
	 * @return the bean mapper.
	 */
	@Mapper
	public interface BeanMapper {

		BeanMapper INSTANCE = Mappers.getMapper(BeanMapper.class);

		@Mapping(source = "preferenceData", target = "data")
		GetUltimatePreferencesModel preferenceToGetUltimatePreferencesModel(Preference preference);
	}

}
