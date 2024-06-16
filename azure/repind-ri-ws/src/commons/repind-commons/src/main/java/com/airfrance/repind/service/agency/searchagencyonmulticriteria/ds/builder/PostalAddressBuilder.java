package com.airfrance.repind.service.agency.searchagencyonmulticriteria.ds.builder;


import com.airfrance.repind.entity.adresse.PostalAddress;
import com.airfrance.repind.service.agency.searchagencyonmulticriteria.dto.PostalAddressBlocDTO;
import com.airfrance.repind.service.agency.searchagencyonmulticriteria.dto.PostalAddressContentDTO;
import com.airfrance.repind.service.agency.searchagencyonmulticriteria.dto.PostalAddressPropertiesDTO;
import org.springframework.stereotype.Component;


@Component("AgencyPostalAddressBuilder")
public class PostalAddressBuilder {
	
	/*===============================================*/
	/*               PUBLIC METHODS                  */
	/*===============================================*/
	
	/**
	 * Creates a PostalAddressBlocDTO instance from an address entity
	 */
	public PostalAddressBlocDTO build(PostalAddress postalAddress)
	{
		PostalAddressBlocDTO address = new PostalAddressBlocDTO();
		PostalAddressPropertiesDTO postalAddressPropertiesDTO = new PostalAddressPropertiesDTO();
		PostalAddressContentDTO postalAddressContentDTO = new PostalAddressContentDTO();
	    
		if(postalAddress.getScode_medium() != null)
		{
			postalAddressPropertiesDTO.setMediumCode(postalAddress.getScode_medium());
		}
		if(postalAddress.getSstatut_medium() != null)
		{
			postalAddressPropertiesDTO.setMediumStatus(postalAddress.getSstatut_medium());
		}
		
		if(postalAddress.getSville() != null)
		{
			postalAddressContentDTO.setCity(postalAddress.getSville());
		}
		if(postalAddress.getScomplement_adresse() != null)
		{
			postalAddressContentDTO.setComplementSends(postalAddress.getScomplement_adresse());
		}
		if(postalAddress.getSno_et_rue() != null)
		{
			postalAddressContentDTO.setStreetNumber(postalAddress.getSno_et_rue());
		}
		if(postalAddress.getSlocalite() != null)
		{
			postalAddressContentDTO.setSaidPlace(postalAddress.getSlocalite());
		}
		if(postalAddress.getScode_postal() != null)
		{
			postalAddressContentDTO.setZipCode(postalAddress.getScode_postal());
		}
		if(postalAddress.getScode_pays() != null)
		{
			postalAddressContentDTO.setCountryCode(postalAddress.getScode_pays());
		}
		if(postalAddress.getScode_province() != null)
		{
			postalAddressContentDTO.setProvinceCode(postalAddress.getScode_province());
		}
		
		address.setPostalAddressContentDTO(postalAddressContentDTO);
		address.setPostalAddressPropertiesDTO(postalAddressPropertiesDTO);
		
		return address;
	}
}
