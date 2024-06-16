package com.airfrance.repind.firme.searchfirmonmulticriteria.ds.builders;

import com.airfrance.repind.firme.searchfirmonmulticriteria.dto.EmailDTO;
import org.springframework.stereotype.Component;

@Component
public class EmailBuilder {
	/*===============================================*/
	/*               PUBLIC METHODS                  */
	/*===============================================*/
	public EmailDTO build(com.airfrance.repind.entity.adresse.Email emailEntity) {
		EmailDTO email = null;
		if(emailEntity != null)
		{
			email = new EmailDTO();
			// email address
			if(emailEntity.getEmail() != null)
			{
				email.setEmail(emailEntity.getEmail());
			}
			if(emailEntity.getCodeMedium() != null)
			{
				email.setMediumCode(emailEntity.getCodeMedium());
			}	
			if(emailEntity.getStatutMedium() != null)
			{
				email.setMediumStatus(emailEntity.getStatutMedium());
			}
			if(emailEntity.getAutorisationMailing() != null)
			{
				email.setMailingAuthorized(emailEntity.getAutorisationMailing());
			}
		}
		return email;
	}
}
