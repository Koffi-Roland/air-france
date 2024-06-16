package com.airfrance.jraf.batch.prospect;

import com.afklm.soa.stubs.w000442.v8.data.CreateUpdateIndividualResponse;
import com.afklm.soa.stubs.w000442.v8.response.InformationResponse;
import com.afklm.soa.stubs.w000442.v8.response.PostalAddressResponse;
import com.afklm.soa.stubs.w000442.v8.siccommontype.Information;
import com.afklm.soa.stubs.w000442.v8.siccommontype.Signature;
import com.afklm.soa.stubs.w000442.v8.sicindividutype.*;
import com.afklm.soa.stubs.w000442.v8.softcomputingtype.SoftComputingResponse;
import com.airfrance.repind.dto.individu.AlertDTO;
import com.airfrance.repind.dto.individu.AlertDataDTO;
import com.airfrance.repind.dto.individu.createmodifyindividual.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class IndividuResponseTransform {	

	public final static int POSTAL_ADDRESS_LIMIT = 2;

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

	// RESPONSEDTO TO RESPONSE
	public static CreateUpdateIndividualResponse transformToCreateUpdateIndividualResponse(CreateModifyIndividualResponseDTO createUpdateIndividualResponseDTO) {
		
		CreateUpdateIndividualResponse cmir = new CreateUpdateIndividualResponse();
		
		if (createUpdateIndividualResponseDTO != null) {
			
			cmir.setGin(createUpdateIndividualResponseDTO.getGin());
			cmir.setSuccess(createUpdateIndividualResponseDTO.getSuccess());
			
			for (InformationResponseDTO ir : createUpdateIndividualResponseDTO.getInformationResponse()) {
				cmir.getInformationResponse().add(transformToInformationResponse(ir));
			}

			for (PostalAddressResponseDTO par : createUpdateIndividualResponseDTO.getPostalAddressResponse()) {
				cmir.getPostalAddressResponse().add(transformToPostalAddressResponse(par));
			}
		}
		
		return cmir;	
	}

	public static PostalAddressResponse transformToPostalAddressResponse(PostalAddressResponseDTO postalAddressResponseDTO) {
		
		PostalAddressResponse pa = new PostalAddressResponse(); 

		pa.setPostalAddressContent(transformToPostalAddressContent(postalAddressResponseDTO.getPostalAddressContent()));		
		pa.setPostalAddressProperties(transformToPostalAddressProperties(postalAddressResponseDTO.getPostalAddressProperties()));
		
		for (SignatureDTO s : postalAddressResponseDTO.getSignature() ) {
			pa.getSignature().add(transformToSignature(s));
		}

		pa.setSoftComputingResponse(transformToSoftComputingResponse(postalAddressResponseDTO.getSoftComputingResponse()));
		
		for (UsageAddressDTO ua : postalAddressResponseDTO.getUsageAddress() ) {
			pa.getUsageAddress().add(transformToUsageAddress(ua));
		}

		return pa;
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

	public static PostalAddressProperties transformToPostalAddressProperties(PostalAddressPropertiesDTO postalAddressPropertiesDTO) {
		
		PostalAddressProperties pa = new PostalAddressProperties(); 
		
		pa.setMediumCode(postalAddressPropertiesDTO.getMediumCode());
		pa.setMediumStatus(postalAddressPropertiesDTO.getMediumStatus());
		pa.setVersion(postalAddressPropertiesDTO.getVersion());
		
		return pa;
	}

	public static PostalAddressContent transformToPostalAddressContent(PostalAddressContentDTO postalAddressContentDTO) {
		
		PostalAddressContent pa = new PostalAddressContent(); 
		
		pa.setAdditionalInformation(postalAddressContentDTO.getComplementSends());
		pa.setCity(postalAddressContentDTO.getCity());
		pa.setCorporateName(postalAddressContentDTO.getBusinessName());		
		pa.setCountryCode(postalAddressContentDTO.getCountryCode());
		pa.setStateCode(postalAddressContentDTO.getProvinceCode());		// TODO : JUSTE ? 
		pa.setNumberAndStreet(postalAddressContentDTO.getStreetNumber());
		pa.setDistrict(postalAddressContentDTO.getSaidPlace());			// TODO : Juste ?
		pa.setZipCode(postalAddressContentDTO.getZipCode());
		
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
	
	public static InformationResponse transformToInformationResponse(InformationResponseDTO informationResponseDTO) {
		
		InformationResponse ir = new InformationResponse(); 
		
		ir.setInformation(transformToInformation(informationResponseDTO.getInformation()));
		
		return ir;
	}

	public static Information transformToInformation(InformationDTO informationDTO) {
		
		Information ir = new Information(); 
		
		ir.setInformationCode(informationDTO.getInformationCode());
		ir.setInformationDetails(informationDTO.getInformationDetails());
		
		return ir;
	}
	
	
	// RESPONSE TO RESPONSEDTO
	public static CreateModifyIndividualResponseDTO transformToCreateUpdateIndividualResponseDTO(CreateUpdateIndividualResponse createUpdateIndividualResponse) {
		
		CreateModifyIndividualResponseDTO cmir = new CreateModifyIndividualResponseDTO();
		
		if (createUpdateIndividualResponse != null) {
			
			cmir.setGin(createUpdateIndividualResponse.getGin());
			cmir.setSuccess(createUpdateIndividualResponse.isSuccess());
			
			Set <InformationResponseDTO> sir = new HashSet<InformationResponseDTO>();
			for (InformationResponse ir : createUpdateIndividualResponse.getInformationResponse()) {
				sir.add(transformToInformationResponseDTO(ir));
			}
			cmir.setInformationResponse(sir);

			Set <PostalAddressResponseDTO> lpar = new HashSet<PostalAddressResponseDTO>();
			for (PostalAddressResponse par : createUpdateIndividualResponse.getPostalAddressResponse()) {
				lpar.add(transformToPostalAddressResponseDTO(par));
			}
			cmir.setPostalAddressResponse(lpar);
		}
		
		return cmir;	
	}

	// POSTAL ADDRESS
	public static PostalAddressResponseDTO transformToPostalAddressResponseDTO(PostalAddressResponse postalAddressResponse) {
		
		PostalAddressResponseDTO pa = new PostalAddressResponseDTO(); 

		pa.setPostalAddressContent(transformToPostalAddressContentDTO(postalAddressResponse.getPostalAddressContent()));		
		pa.setPostalAddressProperties(transformToPostalAddressPropertiesDTO(postalAddressResponse.getPostalAddressProperties()));
		
		List <SignatureDTO> ls = new ArrayList<SignatureDTO>();
		for (Signature s : postalAddressResponse.getSignature() ) {
			ls.add(transformToSignatureDTO(s));
		}
		pa.setSignature(ls);
		pa.setSoftComputingResponse(transformToSoftComputingResponseDTO(postalAddressResponse.getSoftComputingResponse()));
		
		List <UsageAddressDTO> lua = new ArrayList<UsageAddressDTO>();
		for (UsageAddress ua : postalAddressResponse.getUsageAddress() ) {
			lua.add(transformToUsageAddressDTO(ua));
		}
		pa.setUsageAddress(lua);

		return pa;
	}

	public static UsageAddressDTO transformToUsageAddressDTO(UsageAddress usageAddress) {
		
		UsageAddressDTO ua = new UsageAddressDTO(); 
		
		ua.setCodeRoleAdresse(usageAddress.getAddressRoleCode());
		ua.setApplicationCode(usageAddress.getApplicationCode());
		ua.setUsageNumber(usageAddress.getUsageNumber());
		
		return ua;
	}

	public static SoftComputingResponseDTO transformToSoftComputingResponseDTO(SoftComputingResponse softComputingResponse) {
		
		SoftComputingResponseDTO sc = new SoftComputingResponseDTO(); 
		
		sc.setAdrMailingL1(softComputingResponse.getAdrMailingL1());
		sc.setAdrMailingL2(softComputingResponse.getAdrMailingL2());
		sc.setAdrMailingL3(softComputingResponse.getAdrMailingL3());
		sc.setAdrMailingL4(softComputingResponse.getAdrMailingL4());
		sc.setAdrMailingL5(softComputingResponse.getAdrMailingL5());
		sc.setAdrMailingL6(softComputingResponse.getAdrMailingL6());
		sc.setAdrMailingL7(softComputingResponse.getAdrMailingL7());
		sc.setAdrMailingL8(softComputingResponse.getAdrMailingL8());
		sc.setAdrMailingL9(softComputingResponse.getAdrMailingL9());
		
		sc.setErrorLabel(softComputingResponse.getErrorLabel());
		sc.setErrorNumber(softComputingResponse.getErrorNumber());
		sc.setErrorNumberNormail(softComputingResponse.getErrorNumberNormail());
		
		return sc;
	}

	public static PostalAddressPropertiesDTO transformToPostalAddressPropertiesDTO(PostalAddressProperties postalAddressProperties) {
		
		PostalAddressPropertiesDTO pa = new PostalAddressPropertiesDTO(); 
		
		pa.setMediumCode(postalAddressProperties.getMediumCode());
		pa.setMediumStatus(postalAddressProperties.getMediumStatus());
		pa.setVersion(postalAddressProperties.getVersion());
		
		return pa;
	}

	public static PostalAddressContentDTO transformToPostalAddressContentDTO(PostalAddressContent postalAddressContent) {
		
		PostalAddressContentDTO pa = new PostalAddressContentDTO(); 
		
		pa.setComplementSends(postalAddressContent.getAdditionalInformation());
		pa.setCity(postalAddressContent.getCity());
		pa.setBusinessName(postalAddressContent.getCorporateName());		
		pa.setCountryCode(postalAddressContent.getCountryCode());
		pa.setProvinceCode(postalAddressContent.getStateCode());		// TODO : JUSTE ? 
		pa.setStreetNumber(postalAddressContent.getNumberAndStreet());
		pa.setSaidPlace(postalAddressContent.getDistrict());			// TODO : Juste ?
		pa.setZipCode(postalAddressContent.getZipCode());
		
		return pa;
	}

	// INFORMATION RESPONSE
	public static InformationResponseDTO transformToInformationResponseDTO(InformationResponse informationResponse) {
	
		InformationResponseDTO ir = new InformationResponseDTO(); 
		
		ir.setInformation(transformToInformationDTO(informationResponse.getInformation()));
		
		return ir;
	}
	
	public static InformationDTO transformToInformationDTO(Information information) {
		
		InformationDTO ir = new InformationDTO(); 
		
		ir.setInformationCode(information.getInformationCode());
		ir.setInformationDetails(information.getInformationDetails());
		
		return ir;
	}

	// SIGNATURE
	public static SignatureDTO transformToSignatureDTO(Signature signature) {
		
		SignatureDTO s = new SignatureDTO(); 
		
		s.setDate(signature.getDate().toString());		// FIXME : Format ? 
		s.setSignature(signature.getSignature());
		s.setSite(signature.getSignatureSite());
		s.setTypeSignature(signature.getSignatureType());
		
		return s;
	}

}
