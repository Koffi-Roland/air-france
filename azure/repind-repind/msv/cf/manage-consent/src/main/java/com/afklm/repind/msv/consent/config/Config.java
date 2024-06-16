package com.afklm.repind.msv.consent.config;


import com.afklm.repind.common.config.documentation.AbstractWebMvcConfigurer;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.afklm.repind.msv.consent.model.GetConsentModel;
import com.afklm.repind.msv.consent.entity.Consent;
import com.afklm.repind.msv.consent.model.GetConsentDataModel;
import com.afklm.repind.msv.consent.entity.ConsentData;

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

		@Mapping(source = "consentData", target = "data")
		@Mapping(source = "siteCreation", target = "signature.creationSite")
		@Mapping(source = "signatureCreation", target = "signature.creationSignature")
		@Mapping(source = "dateCreation", target = "signature.creationDate")
		@Mapping(source = "siteModification", target = "signature.modificationSite")
		@Mapping(source = "signatureModification", target = "signature.modificationSignature")
		@Mapping(source = "dateModification", target = "signature.modificationDate")
		GetConsentModel consentToConsentModel(Consent consent);

		@Mapping(source = "consentDataId", target = "id")
		@Mapping(source = "siteCreation", target = "signature.creationSite")
		@Mapping(source = "signatureCreation", target = "signature.creationSignature")
		@Mapping(source = "dateCreation", target = "signature.creationDate")
		@Mapping(source = "siteModification", target = "signature.modificationSite")
		@Mapping(source = "signatureModification", target = "signature.modificationSignature")
		@Mapping(source = "dateModification", target = "signature.modificationDate")
		GetConsentDataModel consentDataToConsentDataModel(ConsentData consentData);
	}
}
