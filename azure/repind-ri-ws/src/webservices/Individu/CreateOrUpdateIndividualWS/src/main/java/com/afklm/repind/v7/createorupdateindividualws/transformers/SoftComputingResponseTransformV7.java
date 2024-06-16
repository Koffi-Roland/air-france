package com.afklm.repind.v7.createorupdateindividualws.transformers;

import com.afklm.soa.stubs.w000442.v7.response.PostalAddressResponse;
import com.afklm.soa.stubs.w000442.v7.sicindividutype.PostalAddressContent;
import com.afklm.soa.stubs.w000442.v7.sicindividutype.PostalAddressProperties;
import com.afklm.soa.stubs.w000442.v7.sicindividutype.UsageAddress;
import com.afklm.soa.stubs.w000442.v7.softcomputingtype.SoftComputingResponse;
import com.airfrance.ref.exception.InvalidParameterException;
import com.airfrance.ref.type.OuiNonFlagEnum;
import com.airfrance.repind.dto.adresse.adh.NormalisationSoftResponseDTO;
import com.airfrance.repind.dto.individu.createmodifyindividual.AdressePostaleDTO;
import com.airfrance.repind.dto.individu.createmodifyindividual.RoleAdresseDTO;
import com.airfrance.repind.util.SicStringUtils;

import java.util.List;
import java.util.Set;

public class SoftComputingResponseTransformV7 {

	public static PostalAddressResponse transformToPostalAddressResponse(AdressePostaleDTO dto, NormalisationSoftResponseDTO softDTO) throws InvalidParameterException {
		
		if (dto==null) {
			return null;
		}
	
		PostalAddressResponse ws = new PostalAddressResponse();
		ws.setPostalAddressContent(transformToPostalAddressContent(dto));
		ws.setPostalAddressProperties(transformToPostalAddressProperties(dto));
		ws.setSoftComputingResponse(transformToSoftComputingResponse(softDTO));
		transformToUsageAddress(dto, dto.getRoleadresse(), ws.getUsageAddress());
		updatePostalAddressContent(softDTO, ws.getPostalAddressContent());
		
		return ws;
	}
	
	public static PostalAddressContent transformToPostalAddressContent(AdressePostaleDTO dto) {
		
		if(dto==null) {
			return null;
		}
		
		PostalAddressContent ws = new PostalAddressContent();
		ws.setAdditionalInformation(dto.getComplAdr());
		ws.setCity(dto.getVille());
		ws.setCorporateName(dto.getRaisonSociale());
		ws.setCountryCode(dto.getCodePays());
		ws.setDistrict(dto.getLieuDit());
		ws.setNumberAndStreet(dto.getNumeroRue());
		ws.setStateCode(dto.getCodeProvince());
		ws.setZipCode(dto.getCodePostal());
//		ws.setAdresseKey(dto.getCleAdresse());
//		ws.setUsageNumber(dto.getNumeroUsage());
		
		return ws;
	}
	
	public static PostalAddressProperties transformToPostalAddressProperties(AdressePostaleDTO dto) throws InvalidParameterException {
		
		if(dto==null) {
			return null;
		}
		
		PostalAddressProperties ws = new PostalAddressProperties();
		ws.setIndicAdrNorm(transformToIndicAdrNorm(dto));
		ws.setMediumCode(SicStringUtils.getStringFromObject(dto.getCodeMedium()));
		ws.setMediumStatus(null); // useless for soft response
		ws.setVersion(null); // useless for soft response
		
		return ws;
	}
	
	public static Boolean transformToIndicAdrNorm(AdressePostaleDTO dto) throws InvalidParameterException {
		
		OuiNonFlagEnum flag = OuiNonFlagEnum.getEnum(dto.getIndicAdrNorm());
		
		if(flag==null) {
			return false;
		}
		
		return flag.toBoolean();
	}
	
	public static void transformToUsageAddress(AdressePostaleDTO adresse, Set<RoleAdresseDTO> dtoList, List<UsageAddress> wsList) {
		
		if(dtoList==null) {
			return;
		}
		
		for(RoleAdresseDTO dto : dtoList) {
			wsList.add(transformToUsageAddress(dto, adresse));
		}

	}
	
	public static UsageAddress transformToUsageAddress(RoleAdresseDTO dto, AdressePostaleDTO adresse) {
		
		if (dto == null) {
			return null;
		}
	
		UsageAddress ws = new UsageAddress();
		ws.setAddressRoleCode(dto.getCodeRoleAdresse());
		ws.setApplicationCode(null);
		ws.setUsageNumber(null);
	
		return ws;
	}
	
	public static SoftComputingResponse transformToSoftComputingResponse(NormalisationSoftResponseDTO dto) {
		
		if(dto==null) {
			return null;
		}
		
		SoftComputingResponse ws = new SoftComputingResponse();
		
		ws.setAdrMailingL1(dto.getMailingAdrLine1());
		ws.setAdrMailingL2(dto.getMailingAdrLine2());
		ws.setAdrMailingL3(dto.getMailingAdrLine3());
		ws.setAdrMailingL4(dto.getMailingAdrLine4());
		ws.setAdrMailingL5(dto.getMailingAdrLine5());
		ws.setAdrMailingL6(dto.getMailingAdrLine6());
		ws.setAdrMailingL7(dto.getMailingAdrLine7());
		ws.setAdrMailingL8(dto.getMailingAdrLine8());
		ws.setAdrMailingL9(dto.getMailingAdrLine9());
		ws.setErrorLabel(dto.getLibError());
		ws.setErrorNumber(dto.getNumError());
		ws.setErrorNumberNormail(transformToErrorNumberNormail(dto));
		
		return ws;
	}
	
	public static String transformToErrorNumberNormail(NormalisationSoftResponseDTO dto) {
		
		String errorNumberNormail = "";
		
		if (dto.getReturnCode1()!=null) {
			errorNumberNormail = dto.getReturnCode1().toString();
		}
		else if (dto.getWsErr()!=null) {
			errorNumberNormail = dto.getWsErr().toString();
		}
		else {
			errorNumberNormail = "3";
		}
		
		return errorNumberNormail;
		
	}

	public static void updatePostalAddressContent(NormalisationSoftResponseDTO dto,PostalAddressContent ws){

		if(dto==null) {
			return;
		}
		
		ws.setAdditionalInformation(SicStringUtils.getStringFromObject(dto.getAdrComplement()));
		ws.setCity(SicStringUtils.getStringFromObject(dto.getCityR()));
		ws.setCountryCode(SicStringUtils.getStringFromObject(dto.getCountry()));
		ws.setDistrict(SicStringUtils.getStringFromObject(dto.getLocality()));
		ws.setNumberAndStreet(SicStringUtils.getStringFromObject(dto.getNumAndStreet()));
		ws.setStateCode(SicStringUtils.getStringFromObject(dto.getState()));
		ws.setZipCode(SicStringUtils.getStringFromObject(dto.getZipCode()));
		
	}
	
}
