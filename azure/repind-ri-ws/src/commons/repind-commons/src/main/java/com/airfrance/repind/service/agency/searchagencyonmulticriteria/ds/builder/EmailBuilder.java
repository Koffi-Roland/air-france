package com.airfrance.repind.service.agency.searchagencyonmulticriteria.ds.builder;

import com.airfrance.repind.service.agency.searchagencyonmulticriteria.dto.EmailDTO;
import org.springframework.stereotype.Component;


@Component("AgencyEmailBuilder")
public class EmailBuilder {
	/*===============================================*/
	/*               PUBLIC METHODS                  */
	/*===============================================*/
	public EmailDTO build(com.airfrance.repind.entity.adresse.Email emailEntity) {
		EmailDTO emailDTO = null;
		if(emailEntity != null)
		{
			emailDTO = new EmailDTO();
			// email address
			if(emailEntity.getEmail() != null)
			{
				emailDTO.setEmail(emailEntity.getEmail());
			}
			if(emailEntity.getCodeMedium() != null)
			{
				emailDTO.setMediumCode(emailEntity.getCodeMedium());
			}	
			if(emailEntity.getStatutMedium() != null)
			{
				emailDTO.setMediumStatus(emailEntity.getStatutMedium());
			}
			if(emailEntity.getAutorisationMailing() != null)
			{
				emailDTO.setMailingAuthorized(emailEntity.getAutorisationMailing());
			}
		}
		return emailDTO;
	}
}
