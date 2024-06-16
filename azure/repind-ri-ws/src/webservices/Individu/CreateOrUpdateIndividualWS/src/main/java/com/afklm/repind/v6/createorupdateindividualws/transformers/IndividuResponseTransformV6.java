package com.afklm.repind.v6.createorupdateindividualws.transformers;

import com.afklm.soa.stubs.w000442.v6.data.CreateUpdateIndividualResponse;
import com.afklm.soa.stubs.w000442.v6.response.PostalAddressResponse;
import com.afklm.soa.stubs.w000442.v6.siccommontype.Signature;
import com.afklm.soa.stubs.w000442.v6.sicindividutype.PostalAddressContent;
import com.afklm.soa.stubs.w000442.v6.sicindividutype.PostalAddressProperties;
import com.afklm.soa.stubs.w000442.v6.sicindividutype.UsageAddress;
import com.airfrance.ref.exception.InvalidParameterException;
import com.airfrance.ref.type.OuiNonFlagEnum;
import com.airfrance.ref.type.SignatureTypeEnum;
import com.airfrance.repind.dto.adresse.PostalAddressDTO;
import com.airfrance.repind.dto.adresse.Usage_mediumDTO;
import com.airfrance.repind.dto.individu.IndividuDTO;
import com.airfrance.repind.util.FilterUtils;
import com.airfrance.repind.util.SicStringUtils;
import org.apache.commons.lang.StringUtils;

import java.util.List;
import java.util.Set;

public class IndividuResponseTransformV6 {	

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
}
