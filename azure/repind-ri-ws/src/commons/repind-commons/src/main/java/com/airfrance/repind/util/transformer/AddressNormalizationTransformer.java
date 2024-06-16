package com.airfrance.repind.util.transformer;

import com.airfrance.repind.dto.adresse.adh.NormalisationSoftRequestDTO;
import com.airfrance.repind.dto.adresse.adh.NormalisationSoftResponseDTO;
import com.airfrance.repind.dto.individu.createmodifyindividual.*;


public class AddressNormalizationTransformer {

	private static final String SOFTCOMPUTING_3 = "3";
	private static final String FORCAGE_VRAI = "O";
	
	
	// ===== SOFT ==============================================================
	
		public static NormalisationSoftRequestDTO dtoTOSoftdto(AdressePostaleDTO adr) {
			
			NormalisationSoftRequestDTO request = new NormalisationSoftRequestDTO();
			request.setCity(adr.getVille());
			request.setComp(adr.getComplAdr());
			request.setCountry(adr.getCodePays());
			request.setLocal(adr.getLieuDit());
			request.setState(adr.getCodeProvince());
			request.setStreet(adr.getNumeroRue());
			request.setZip(adr.getCodePostal());
			return request;
		}
		
		
		public static PostalAddressResponseDTO dtoTOPostal(AdressePostaleDTO postale) {
			PostalAddressResponseDTO adr = new PostalAddressResponseDTO();
			PostalAddressContentDTO postalAddressContent = new PostalAddressContentDTO();
			PostalAddressPropertiesDTO postalAddressProperties = new PostalAddressPropertiesDTO();

			postalAddressContent.setCountryCode(postale.getCodePays());
			postalAddressContent.setZipCode(postale.getCodePostal());
			postalAddressContent.setProvinceCode(postale.getCodeProvince());
			postalAddressContent.setComplementSends(postale.getComplAdr());
			postalAddressContent.setSaidPlace(postale.getLieuDit());
			postalAddressContent.setStreetNumber(postale.getNumeroRue());
			postalAddressContent.setBusinessName(postale.getRaisonSociale());
			postalAddressContent.setCity(postale.getVille());
			
			postalAddressProperties.setMediumCode(postale.getCodeMedium());

			postalAddressProperties
					.setVersion(String.valueOf(postale.getVersion()));

			postalAddressProperties.setMediumStatus(postale.getStatutMedium());

			if (FORCAGE_VRAI.equalsIgnoreCase(postale.getIndicAdrNorm()))
				postalAddressProperties.setBypassNormAddress(true);
			else
				postalAddressProperties.setBypassNormAddress(false);
			
			adr.setPostalAddressContent(postalAddressContent);
			adr.setPostalAddressProperties(postalAddressProperties);
			
			return adr;
		}
		
	public static PostalAddressResponseDTO softTOPostal(NormalisationSoftResponseDTO soft, PostalAddressResponseDTO par) {
			
			PostalAddressResponseDTO adr = par;

			PostalAddressPropertiesDTO properties = adr.getPostalAddressProperties();
			PostalAddressContentDTO content = adr.getPostalAddressContent();
			
			if(properties==null) {		
				properties = new PostalAddressPropertiesDTO();
				adr.setPostalAddressProperties(properties);
			}
			
			if(content==null) {
				content = new PostalAddressContentDTO();
				adr.setPostalAddressContent(content);
			}
			
			properties.setVersion(null);
			properties.setMediumStatus(null);

			if (soft.getAdrComplement() != null) {
				content.setComplementSends(soft.getAdrComplement());
			}
			else {
				content.setComplementSends(null);
			}
			
			if (soft.getCityR() != null) {
				content.setCity(soft.getCityR());
			}
			else {
				content.setCity(null);
			}
			
			if (soft.getCountry() != null) {
				content.setCountryCode(soft.getCountry());
			} 
			else {
				content.setCountryCode(null);
			}
			
			if (soft.getLocality() != null) {
				content.setSaidPlace(soft.getLocality());
			}
			else {
				content.setSaidPlace(null);
			}
			
			if (soft.getNumAndStreet() != null) {
				content.setStreetNumber(soft.getNumAndStreet());
			}
			else {
				content.setStreetNumber(null);
			}
				
			if (soft.getState() != null) {
				content.setProvinceCode(soft.getState());
			} 
			else {
				content.setProvinceCode(null);
			}
				
			if (soft.getZipCode() != null) {
				content.setZipCode(soft.getZipCode());
			} 
			else {
				content.setZipCode(null);
			}
			
			return adr;
		}

	public static SoftComputingResponseDTO softToError(NormalisationSoftResponseDTO out) {
		SoftComputingResponseDTO scr = new SoftComputingResponseDTO();
		if (out.getReturnCode1() != null)
			scr.setErrorNumberNormail(out.getReturnCode1().toString());
		else if (out.getWsErr() != null && out.getWsErr() == 3)
			scr.setErrorNumberNormail(out.getWsErr().toString());
		else
			scr.setErrorNumberNormail(SOFTCOMPUTING_3);

		if (out.getNumError() != null)
			scr.setErrorNumber(out.getNumError());
		if (out.getLibError() != null)
			scr.setErrorLabel(out.getLibError());

		if (out.getMailingAdrLine1() != null)
			scr.setAdrMailingL1(out.getMailingAdrLine1());
		if (out.getMailingAdrLine2() != null)
			scr.setAdrMailingL2(out.getMailingAdrLine2());
		if (out.getMailingAdrLine3() != null)
			scr.setAdrMailingL3(out.getMailingAdrLine3());
		if (out.getMailingAdrLine4() != null)
			scr.setAdrMailingL4(out.getMailingAdrLine4());
		if (out.getMailingAdrLine5() != null)
			scr.setAdrMailingL5(out.getMailingAdrLine5());
		if (out.getMailingAdrLine6() != null)
			scr.setAdrMailingL6(out.getMailingAdrLine6());
		if (out.getMailingAdrLine7() != null)
			scr.setAdrMailingL7(out.getMailingAdrLine7());
		if (out.getMailingAdrLine8() != null)
			scr.setAdrMailingL8(out.getMailingAdrLine8());
		if (out.getMailingAdrLine9() != null)
			scr.setAdrMailingL9(out.getMailingAdrLine9());

		return scr;
	}
}
