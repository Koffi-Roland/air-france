package com.airfrance.repind.service.agency.searchagencyonmulticriteria.ds.builder;


import com.airfrance.repind.entity.adresse.Telecoms;
import com.airfrance.repind.service.agency.searchagencyonmulticriteria.dto.TelecomBlocDTO;
import com.airfrance.repind.service.agency.searchagencyonmulticriteria.dto.TelecomDTO;
import com.airfrance.repind.service.agency.searchagencyonmulticriteria.dto.TelecomStandardizationDTO;
import org.springframework.stereotype.Component;

@Component("AgencyTelecomBuilder")
public class TelecomBuilder {

	
	/*===============================================*/
	/*               PUBLIC METHODS                  */
	/*===============================================*/
	public TelecomBlocDTO build(Telecoms telecoms) {
		TelecomBlocDTO telecomBlocDTO = new TelecomBlocDTO();
		if(telecoms != null)
		{
			TelecomDTO telecomDTO = new TelecomDTO();
			TelecomStandardizationDTO telecomStandardizationDTO = new TelecomStandardizationDTO();
			
			// COUNTRY CODE
			if(telecoms.getSnorm_inter_country_code() != null)
			{
				telecomDTO.setCountryCodeNum(telecoms.getSnorm_inter_country_code());
			}
			else if(telecoms.getSindicatif() != null)
			{
				telecomDTO.setCountryCodeNum(telecoms.getSindicatif());
			}
			else
			{
				telecomDTO.setCountryCodeNum("");
			}
			
			//PHONE NUMBER
			if(telecoms.getSnorm_inter_phone_number() != null)
			{
				telecomDTO.setPhoneNumber(telecoms.getSnorm_inter_phone_number());
				//Nécessaire pour le service indentityCustomerCrossReference
				telecomStandardizationDTO.setNormPhoneNumber(telecoms.getSnorm_inter_phone_number());
			}
			else if(telecoms.getSnorm_nat_phone_number() != null)
			{
				telecomDTO.setPhoneNumber(telecoms.getSnorm_nat_phone_number());
			}
			else if(telecoms.getSnorm_nat_phone_number_clean() != null)
			{
				telecomDTO.setPhoneNumber(telecoms.getSnorm_nat_phone_number_clean());
			}
			else if(telecoms.getSnumero() != null)
			{
				telecomDTO.setPhoneNumber(telecoms.getSnumero());
			}
			else
			{
				telecomDTO.setPhoneNumber("");
			}
			
			if(telecoms.getSnumero() != null) {
				//Nécessaire pour le service indentityCustomerCrossReference
				telecomStandardizationDTO.setPhoneNumber(telecoms.getSnumero());
			}
			
			// CODE MEDIUM
			if(telecoms.getScode_medium() != null) {
				telecomDTO.setMediumCode(telecoms.getScode_medium());
			}
			
			// STATUS MEDIUM
			if(telecoms.getSstatut_medium() != null) {
				telecomDTO.setMediumStatus(telecoms.getSstatut_medium());
			}
			
			// TERMINAL
			if(telecoms.getSterminal() != null)
			{
				telecomDTO.setTerminalType(telecoms.getSterminal());
			}
			else
			{
				telecomDTO.setTerminalType("");
			}	
			telecomBlocDTO.setTelecomDTO(telecomDTO);
			telecomBlocDTO.setTelecomStandardizationDTO(telecomStandardizationDTO);
		}
		return telecomBlocDTO;
	}
}
