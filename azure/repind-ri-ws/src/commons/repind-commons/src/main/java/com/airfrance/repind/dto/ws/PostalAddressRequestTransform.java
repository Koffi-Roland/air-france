package com.airfrance.repind.dto.ws;

import com.airfrance.repind.dto.adresse.PostalAddressDTO;
import com.airfrance.repind.dto.adresse.Usage_mediumDTO;
import com.airfrance.repind.dto.individu.SignatureDTO;

import java.util.HashSet;
import java.util.Set;

public class PostalAddressRequestTransform {
	
	public static PostalAddressDTO transformRequestToDto(PostalAddressRequestDTO ws, SignatureDTO signatureAPP) {
		PostalAddressDTO dto = null;
		
		if (ws == null) {
			return dto;
		}
		
		dto = new PostalAddressDTO();
		
		if (ws.getPostalAddressContentDTO() != null) {
			PostalAddressContentDTO pac = ws.getPostalAddressContentDTO();
			if (pac.getAdditionalInformation() != null) {
				dto.setScomplement_adresse(pac.getAdditionalInformation());
			}
			if (pac.getNumberAndStreet() != null) {
				dto.setSno_et_rue(pac.getNumberAndStreet());
			}
			if (pac.getCorporateName() != null) {
				dto.setSraison_sociale(pac.getCorporateName());
			}
			if (pac.getDistrict() != null) {
				dto.setSlocalite(pac.getDistrict());
			}
			if (pac.getStateCode() != null) {
				dto.setScode_province(pac.getStateCode());
			}
			if (pac.getZipCode() != null) {
				dto.setScode_postal(pac.getZipCode());
			}
			if (pac.getCity() != null) {
				dto.setSville(pac.getCity());
			}
			if (pac.getCountryCode() != null) {
				dto.setScode_pays(pac.getCountryCode());
			}
			
			dto.setSsignature_fonctionnel(signatureAPP.getSignature());
			dto.setSsite_fonctionnel(signatureAPP.getSite());
			dto.setDdate_fonctionnel(signatureAPP.getDate());
		}
		
		if (ws.getPostalAddressPropertiesDTO() != null) {
			PostalAddressPropertiesDTO pap = ws.getPostalAddressPropertiesDTO();
			if (pap.getIndicAdrNorm() != null && pap.getIndicAdrNorm()) {
				dto.setSforcage("O");
			}
			else {
				dto.setSforcage("N");
			}
			if (pap.getVersion() != null) {
				dto.setVersion(Integer.valueOf(pap.getVersion()));
			}
			else {
				dto.setVersion(0);
			}
			if (pap.getMediumCode() != null) {
				dto.setScode_medium(pap.getMediumCode());
			}
			if (pap.getMediumStatus() != null) {
				dto.setSstatut_medium(pap.getMediumStatus());
			}
		}
		
		if (ws.getUsageAddressDTO() != null) {
			Set<Usage_mediumDTO> usageList = new HashSet<Usage_mediumDTO>();
			UsageAddressDTO ua = ws.getUsageAddressDTO();
			Usage_mediumDTO umDTO = new Usage_mediumDTO();
			if (ua.getApplicationCode() != null) {
				umDTO.setScode_application(ua.getApplicationCode());
			}
			if (ua.getUsageNumber() != null) {
				umDTO.setInum(Integer.valueOf(ua.getUsageNumber()));
			}
			if (ua.getAddressRoleCode() != null) {
				umDTO.setSrole1(ua.getAddressRoleCode());
			}
			usageList.add(umDTO);
			dto.setUsage_mediumdto(usageList);
		}
		
		return dto;
	}
}
