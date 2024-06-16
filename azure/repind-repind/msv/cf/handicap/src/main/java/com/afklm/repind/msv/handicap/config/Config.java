package com.afklm.repind.msv.handicap.config;

import com.afklm.repind.common.config.documentation.AbstractWebMvcConfigurer;
import com.afklm.repind.msv.handicap.entity.Handicap;
import com.afklm.repind.msv.handicap.entity.HandicapData;
import com.afklm.repind.msv.handicap.model.HandicapDataModel;
import com.afklm.repind.msv.handicap.model.HandicapModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * MVC config
 *
 * @author t528182
 *
 */
@Configuration
@EnableWebMvc
@PropertySource("classpath:/swagger.properties")
public class Config extends AbstractWebMvcConfigurer {
	
	/**
	 * Gets the bean mapper.
	 * @return the bean mapper.
	 */

	@Mapper
	public interface BeanMapper {

		@Mapping(source = "handicapData", target = "data")
		@Mapping(source = "siteCreation", target = "signature.creationSite")
		@Mapping(source = "signatureCreation", target = "signature.creationSignature")
		@Mapping(source = "dateCreation", target = "signature.creationDate")
		@Mapping(source = "siteModification", target = "signature.modificationSite")
		@Mapping(source = "signatureModification", target = "signature.modificationSignature")
		@Mapping(source = "dateModification", target = "signature.modificationDate")
		HandicapModel handicapToHandicapModel(Handicap handicap);

		@Mapping(source = "siteCreation", target = "signature.creationSite")
		@Mapping(source = "signatureCreation", target = "signature.creationSignature")
		@Mapping(source = "dateCreation", target = "signature.creationDate")
		@Mapping(source = "siteModification", target = "signature.modificationSite")
		@Mapping(source = "signatureModification", target = "signature.modificationSignature")
		@Mapping(source = "dateModification", target = "signature.modificationDate")
		HandicapDataModel handicapDataToHandicapDataModel(HandicapData handicapData);
	}
	
}
