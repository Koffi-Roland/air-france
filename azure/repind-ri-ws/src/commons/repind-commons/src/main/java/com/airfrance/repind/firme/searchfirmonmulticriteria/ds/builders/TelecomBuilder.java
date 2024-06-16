package com.airfrance.repind.firme.searchfirmonmulticriteria.ds.builders;

import com.airfrance.repind.entity.adresse.Telecoms;
import com.airfrance.repind.firme.searchfirmonmulticriteria.dto.TelecomDTO;
import org.springframework.stereotype.Component;

@Component
public class TelecomBuilder {

	
	/*===============================================*/
	/*               PUBLIC METHODS                  */
	/*===============================================*/
	public TelecomDTO build(Telecoms telecoms) {
		TelecomDTO telecom = new TelecomDTO();
		if(telecoms != null)
		{
			// COUNTRY CODE
			if(telecoms.getSnorm_inter_country_code() != null)
			{
				telecom.setCountryCode(telecoms.getSnorm_inter_country_code());
			}
			else if(telecoms.getSindicatif() != null)
			{
				telecom.setCountryCode(telecoms.getSindicatif());
			}
			else
			{
				telecom.setCountryCode("");
			}
			
			//PHONE NUMBER
			if(telecoms.getSnorm_inter_phone_number() != null)
			{
				telecom.setPhoneNumber(telecoms.getSnorm_inter_phone_number());
				//INTERNATIONAL NORMALIZED PHONE NUMBER
				telecom.setInterNormPhoneNumber(telecoms.getSnorm_inter_phone_number());
			}
			else if(telecoms.getSnorm_nat_phone_number() != null)
			{
				telecom.setPhoneNumber(telecoms.getSnorm_nat_phone_number());
			}
			else if(telecoms.getSnorm_nat_phone_number_clean() != null)
			{
				telecom.setPhoneNumber(telecoms.getSnorm_nat_phone_number_clean());
			}
			else if(telecoms.getSnumero() != null)
			{
				telecom.setPhoneNumber(telecoms.getSnumero());
			}
			else
			{
				telecom.setPhoneNumber("");
			}
			
			// PHONE NUMBER UNCHANGED
			if(telecoms.getSnumero() != null)
			{
				telecom.setUnchangedPhoneNumber(telecoms.getSnumero());
			}
			
			// TERMINAL
			if(telecoms.getSterminal() != null)
			{
				telecom.setTerminalType(telecoms.getSterminal());
			}
			else
			{
				telecom.setTerminalType("");
			}
			
			if(telecoms.getScode_medium() != null) {
				telecom.setMediumCode(telecoms.getScode_medium());
			}
		}
		return telecom;
	}
}
