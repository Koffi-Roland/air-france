package com.afklm.repind.v7.createorupdateindividualws.transformers;

import com.afklm.soa.stubs.w000442.v7.data.CreateUpdateIndividualResponse;
import com.afklm.soa.stubs.w000442.v7.response.PostalAddressResponse;
import com.afklm.soa.stubs.w000442.v7.siccommontype.Signature;
import com.afklm.soa.stubs.w000442.v7.sicindividutype.*;
import com.afklm.soa.stubs.w000442.v7.softcomputingtype.SoftComputingResponse;
import com.airfrance.ref.exception.InvalidParameterException;
import com.airfrance.ref.type.OuiNonFlagEnum;
import com.airfrance.ref.type.SignatureTypeEnum;
import com.airfrance.repind.dto.adresse.PostalAddressDTO;
import com.airfrance.repind.dto.adresse.Usage_mediumDTO;
import com.airfrance.repind.dto.individu.AlertDTO;
import com.airfrance.repind.dto.individu.AlertDataDTO;
import com.airfrance.repind.dto.individu.IndividuDTO;
import com.airfrance.repind.dto.individu.createmodifyindividual.*;
import com.airfrance.repind.util.FilterUtils;
import com.airfrance.repind.util.SicStringUtils;
import org.apache.commons.lang.StringUtils;

import java.sql.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class IndividuResponseTransformV7 {	

	public final static int POSTAL_ADDRESS_LIMIT = 2;
	
	public static CreateUpdateIndividualResponse transformToCreateUpdateIndividualResponse(IndividuDTO dto, boolean isCreated) throws InvalidParameterException {
		
		if(dto==null) {
			return null;
		}
		
		CreateUpdateIndividualResponse ws = new CreateUpdateIndividualResponse();
		
		if(isCreated){
			ws.setGin(dto.getSgin());
		}
		transformToPostalAddressResponse(dto.getPostaladdressdto(), ws.getPostalAddressResponse());
		return ws;	
	}	
	
	public static void transformToPostalAddressResponse(List<PostalAddressDTO> dtoList, List<PostalAddressResponse> wsList) throws InvalidParameterException {
		
		if(dtoList==null || dtoList.isEmpty()) {
			return;
		}
		
		dtoList = FilterUtils.filterPostalAddress(dtoList, POSTAL_ADDRESS_LIMIT);
		
		for(PostalAddressDTO dto : dtoList) {
			wsList.add(transformToPostalAddressResponse(dto));
		}		
	}
	
	public static PostalAddressResponse transformToPostalAddressResponse(PostalAddressDTO dto) throws InvalidParameterException {
		
		if (dto==null) {
			return null;
		}
	
		PostalAddressResponse ws = new PostalAddressResponse();
		ws.setPostalAddressContent(transformToPostalAddressContent(dto));
		ws.setPostalAddressProperties(transformToPostalAddressProperties(dto));		
		transformToSignature(dto,ws.getSignature());
		transformToUsageAddress(dto.getUsage_mediumdto(), ws.getUsageAddress());
		
		return ws;
	}
	
	public static PostalAddressContent transformToPostalAddressContent(PostalAddressDTO dto) {
		
		if(dto==null) {
			return null;
		}
		
		PostalAddressContent ws = new PostalAddressContent();
		ws.setAdditionalInformation(dto.getScomplement_adresse());
		ws.setCity(dto.getSville());
		ws.setCorporateName(dto.getSraison_sociale());
		ws.setCountryCode(dto.getScode_pays());
		ws.setDistrict(dto.getSlocalite());
		ws.setNumberAndStreet(dto.getSno_et_rue());
		ws.setStateCode(dto.getScode_province());
		ws.setZipCode(dto.getScode_postal());
		
		return ws;
	}
	
	public static PostalAddressProperties transformToPostalAddressProperties(PostalAddressDTO dto) throws InvalidParameterException {
		
		if(dto==null) {
			return null;
		}
		
		PostalAddressProperties ws = new PostalAddressProperties();
		
		if(StringUtils.isNotEmpty(dto.getSforcage())) {
			ws.setIndicAdrNorm(OuiNonFlagEnum.getEnum(dto.getSforcage()).toBoolean());
		}
		
		ws.setMediumCode(dto.getScode_medium());
		ws.setMediumStatus(dto.getSstatut_medium());
		ws.setVersion(SicStringUtils.getStringFromObject(dto.getVersion()));
		
		return ws;
	}
	
	
	public static void transformToSignature(PostalAddressDTO dto, List<Signature> wsList) {
		
		Signature creation = new Signature();
		creation.setDate(dto.getDdate_creation());
		creation.setSignature(dto.getSignature_creation());
		creation.setSignatureSite(dto.getSsite_creation());
		creation.setSignatureType(SignatureTypeEnum.CREATION.toString());
		
		Signature modification = new Signature();
		modification.setDate(dto.getDdate_modification());
		modification.setSignature(dto.getSsignature_modification());
		modification.setSignatureSite(dto.getSsite_modification());
		modification.setSignatureType(SignatureTypeEnum.MODIFICATION.toString());
		
		wsList.add(creation);
		wsList.add(modification);
	}
	
	public static void transformToUsageAddress(Set<Usage_mediumDTO> dtoList, List<UsageAddress> wsList) {
		
		if(dtoList==null || dtoList.isEmpty()) {
			return;
		}
		
		for(Usage_mediumDTO dto : dtoList) {
			wsList.add(transformToUsageAddress(dto));
		}		
	}
	
	public static UsageAddress transformToUsageAddress(Usage_mediumDTO dto) {
		
		if (dto == null) {
			return null;
		}
	
		UsageAddress ws = new UsageAddress();
		ws.setAddressRoleCode(dto.getSrole1());
		ws.setApplicationCode(dto.getScode_application());
		ws.setUsageNumber(SicStringUtils.getStringFromObject(dto.getInum()));
	
		return ws;
	}
	

	
	public static Alert transformToAlert(AlertDTO dto) {
		
		if (dto == null) {
			return null;
		}
	
		Alert ws = new Alert();
		ws.setOptIn(dto.getOptIn());
		ws.setAlertId(dto.getAlertId().toString());
		ws.setType(dto.getType());
		if(dto.getAlertDataDTO() != null && dto.getAlertDataDTO().size() > 1){
			ws.getAlertData().addAll(transformToAlertData(dto.getAlertDataDTO()));
		}
	
		return ws;
	}

	public static Set<AlertData> transformToAlertData(Set<AlertDataDTO> dto) {
		if (dto == null || dto.size() == 0) {
			return null;
		}
		Set<AlertData> ws = new HashSet<AlertData>();
		for (AlertDataDTO alertDataDTO : dto) {
			ws.add(transformToAlertData(alertDataDTO));
		}
		
		return ws;
	}

	public static AlertData transformToAlertData(AlertDataDTO dto) {
		
		if (dto == null) {
			return null;
		}
	
		AlertData ws = new AlertData();
		ws.setKey(dto.getKey());
		ws.setValue(dto.getValue());
	
		return ws;
	}

	public static PostalAddressResponse transformToPostalAddressResponse(PostalAddressResponseDTO postalAddressResponseDTO) {

		PostalAddressResponse pa = new PostalAddressResponse();

		pa.setPostalAddressContent(transformToPostalAddressContent(postalAddressResponseDTO.getPostalAddressContent()));
		pa.setPostalAddressProperties(transformToPostalAddressProperties(postalAddressResponseDTO.getPostalAddressProperties()));

		if(postalAddressResponseDTO.getSignature() != null) {
			for (SignatureDTO s : postalAddressResponseDTO.getSignature() ) {
				pa.getSignature().add(transformToSignature(s));
			}
		}

		pa.setSoftComputingResponse(transformToSoftComputingResponse(postalAddressResponseDTO.getSoftComputingResponse()));

		if(postalAddressResponseDTO.getUsageAddress() != null) {
			for (UsageAddressDTO ua : postalAddressResponseDTO.getUsageAddress() ) {
				pa.getUsageAddress().add(transformToUsageAddress(ua));
			}
		}

		return pa;
	}

	public static PostalAddressContent transformToPostalAddressContent(PostalAddressContentDTO postalAddressContentDTO) {

		PostalAddressContent pa = new PostalAddressContent();

		pa.setAdditionalInformation(postalAddressContentDTO.getComplementSends());
		pa.setCity(postalAddressContentDTO.getCity());
		pa.setCorporateName(postalAddressContentDTO.getBusinessName());
		pa.setCountryCode(postalAddressContentDTO.getCountryCode());
		pa.setStateCode(postalAddressContentDTO.getProvinceCode());
		pa.setNumberAndStreet(postalAddressContentDTO.getStreetNumber());
		pa.setDistrict(postalAddressContentDTO.getSaidPlace());
		pa.setZipCode(postalAddressContentDTO.getZipCode());

		return pa;
	}

	public static PostalAddressProperties transformToPostalAddressProperties(PostalAddressPropertiesDTO postalAddressPropertiesDTO) {

		PostalAddressProperties pa = new PostalAddressProperties();

		pa.setMediumCode(postalAddressPropertiesDTO.getMediumCode());
		pa.setMediumStatus(postalAddressPropertiesDTO.getMediumStatus());
		pa.setVersion(postalAddressPropertiesDTO.getVersion());

		return pa;
	}

	public static Signature transformToSignature(SignatureDTO signatureDTO) {

		Signature s = new Signature();

		s.setDate(Date.valueOf(signatureDTO.getDate()));
		s.setSignature(signatureDTO.getSignature());
		s.setSignatureSite(signatureDTO.getSite());
		s.setSignatureType(signatureDTO.getTypeSignature());

		return s;
	}

	public static UsageAddress transformToUsageAddress(UsageAddressDTO usageAddress) {

		UsageAddress ua = new UsageAddress();

		ua.setAddressRoleCode(usageAddress.getCodeRoleAdresse());
		ua.setApplicationCode(usageAddress.getApplicationCode());
		ua.setUsageNumber(usageAddress.getUsageNumber());

		return ua;
	}

	public static SoftComputingResponse transformToSoftComputingResponse(SoftComputingResponseDTO softComputingResponseDTO) {

		SoftComputingResponse sc = new SoftComputingResponse();

		sc.setAdrMailingL1(softComputingResponseDTO.getAdrMailingL1());
		sc.setAdrMailingL2(softComputingResponseDTO.getAdrMailingL2());
		sc.setAdrMailingL3(softComputingResponseDTO.getAdrMailingL3());
		sc.setAdrMailingL4(softComputingResponseDTO.getAdrMailingL4());
		sc.setAdrMailingL5(softComputingResponseDTO.getAdrMailingL5());
		sc.setAdrMailingL6(softComputingResponseDTO.getAdrMailingL6());
		sc.setAdrMailingL7(softComputingResponseDTO.getAdrMailingL7());
		sc.setAdrMailingL8(softComputingResponseDTO.getAdrMailingL8());
		sc.setAdrMailingL9(softComputingResponseDTO.getAdrMailingL9());

		sc.setErrorLabel(softComputingResponseDTO.getErrorLabel());
		sc.setErrorNumber(softComputingResponseDTO.getErrorNumber());
		sc.setErrorNumberNormail(softComputingResponseDTO.getErrorNumberNormail());

		return sc;
	}
}
