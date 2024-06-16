package com.airfrance.repind.firme.searchfirmonmulticriteria.ds.builders;

import com.airfrance.repind.entity.adresse.PostalAddress;
import com.airfrance.repind.firme.searchfirmonmulticriteria.dto.FirmInformationsDTO;
import com.airfrance.repind.firme.searchfirmonmulticriteria.dto.PostalAddressBlocResponseDTO;
import com.airfrance.repind.firme.searchfirmonmulticriteria.dto.PostalAddressContentDTO;
import com.airfrance.repind.firme.searchfirmonmulticriteria.dto.PostalAddressPropertiesDTO;
import org.springframework.stereotype.Component;

@Component
public class PostalAddressBuilder {
	
	/*===============================================*/
	/*               PUBLIC METHODS                  */
	/*===============================================*/
	
	/**
	 * Creates a PostalAddressBloc instance from an address entity
	 */
	public PostalAddressBlocResponseDTO build(PostalAddress postalAddress, FirmInformationsDTO firmInformations)
	{
		PostalAddressBlocResponseDTO address = new PostalAddressBlocResponseDTO();
		PostalAddressPropertiesDTO postalAddressProperties = new PostalAddressPropertiesDTO();
		PostalAddressContentDTO postalAddressContent = new PostalAddressContentDTO();
	    
		if(postalAddress.getSforcage() != null)
		{
			postalAddressProperties.setBypassNormAddress(postalAddress.getSforcage().equalsIgnoreCase("Y"));
		}
		if(postalAddress.getVersion() != null)
		{
			postalAddressProperties.setVersion(postalAddress.getVersion().toString());
		}
		if(postalAddress.getScode_medium() != null)
		{
			postalAddressProperties.setMediumCode(postalAddress.getScode_medium());
		}
		if(postalAddress.getSstatut_medium() != null)
		{
			postalAddressProperties.setMediumStatus(postalAddress.getSstatut_medium());
		}
		if(postalAddress.getSville() != null)
		{
			postalAddressContent.setCity(postalAddress.getSville());
		}
		if(postalAddress.getScomplement_adresse() != null)
		{
			postalAddressContent.setComplementSends(postalAddress.getScomplement_adresse());
		}
		if(postalAddress.getSno_et_rue() != null)
		{
			postalAddressContent.setStreetNumber(postalAddress.getSno_et_rue());
		}
		if(postalAddress.getSlocalite() != null)
		{
			postalAddressContent.setSaidPlace(postalAddress.getSlocalite());
		}
		if(postalAddress.getScode_postal() != null)
		{
			postalAddressContent.setZipCode(postalAddress.getScode_postal());
		}
		if(postalAddress.getScode_pays() != null)
		{
			postalAddressContent.setCountryCode(postalAddress.getScode_pays());
		}
		if(postalAddress.getScode_province() != null)
		{
			postalAddressContent.setProvinceCode(postalAddress.getScode_province());
		}
		if(postalAddress.getScode_medium() != null && postalAddress.getScode_medium().equalsIgnoreCase("L")){
			if(postalAddress.getScode_postal() != null && postalAddress.getScode_pays() != null){
				firmInformations.getFirmName().setLocation(postalAddress.getScode_postal()+postalAddress.getScode_pays());
			}
		}
		address.setPostalAddressContent(postalAddressContent);
		address.setPostalAddressProperties(postalAddressProperties);
		
		return address;
	}
}
