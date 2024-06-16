package com.afklm.repind.v8.createorupdateindividualws.transformers;

import com.afklm.soa.stubs.w000442.v8.data.CreateUpdateIndividualRequest;
import com.afklm.soa.stubs.w000442.v8.request.IndividualRequest;
import com.afklm.soa.stubs.w000442.v8.request.TelecomRequest;
import com.afklm.soa.stubs.w000442.v8.siccommontype.RequestorV2;
import com.afklm.soa.stubs.w000442.v8.sicindividutype.IndividualInformationsV3;
import com.afklm.soa.stubs.w000442.v8.sicindividutype.Telecom;
import com.airfrance.ref.exception.InvalidParameterException;
import com.airfrance.repind.dto.ws.IndividualInformationsDTO;
import com.airfrance.repind.dto.ws.IndividualRequestDTO;
import com.airfrance.repind.dto.ws.RequestorDTO;
import com.airfrance.repind.dto.ws.TelecomDTO;
import com.airfrance.repind.dto.ws.createupdateindividual.CreateUpdateIndividualRequestDTO;

public class CreateUpdateIndividualRequestTransform {

	
	public static CreateUpdateIndividualRequestDTO transformCreateUpdateIndividualRequest(CreateUpdateIndividualRequest request) throws InvalidParameterException{
				
		if(request == null){
			throw new InvalidParameterException("Request is null");
		}
		
		CreateUpdateIndividualRequestDTO dto = new CreateUpdateIndividualRequestDTO();
		
		dto.setStatus(request.getStatus());
		dto.setRequestorDTO(transformRequestorV2(request.getRequestor()));
		dto.setIndividualRequestDTO(transformIndividualRequest(request.getIndividualRequest()));
		/**
		 * TODO: complete the mapping 
		 */
		
		return dto;
	}
		
	private static IndividualInformationsDTO transformIndividualInformations(IndividualInformationsV3 individualInformations) throws InvalidParameterException{
		if(individualInformations == null){
			throw new InvalidParameterException("Request is null");
		}
		
		IndividualInformationsDTO individualInformationsDTO = new IndividualInformationsDTO();
		
		individualInformationsDTO.setIdentifier(individualInformations.getIdentifier());
		/**
		 * TODO: complete the mapping
		 */
		
		return individualInformationsDTO;
	}
	
	public static TelecomDTO transformTelecomRequestToTelecomDTO(TelecomRequest request) {
		if (request != null && request.getTelecom() != null) {
			Telecom tel = request.getTelecom();
			TelecomDTO telDto = new TelecomDTO();
			telDto.setVersion(tel.getVersion());
			telDto.setMediumCode(tel.getMediumCode());
			telDto.setMediumStatus(tel.getMediumStatus());
			telDto.setTerminalType(tel.getTerminalType());
			telDto.setCountryCode(tel.getCountryCode());
			telDto.setPhoneNumber(tel.getPhoneNumber());
			
			return telDto;
		} 
		else {
			return null;
		}		
	}
	private static IndividualRequestDTO transformIndividualRequest(IndividualRequest individualRequest) throws InvalidParameterException{
		if(individualRequest == null){
			throw new InvalidParameterException("Request is null");
		}
		
		IndividualRequestDTO individualRequestDTO = new IndividualRequestDTO();
		
		individualRequestDTO.setIndividualInformationsDTO(transformIndividualInformations(individualRequest.getIndividualInformations()));
		
		return individualRequestDTO;
	}
	
	private static RequestorDTO transformRequestorV2(RequestorV2 requestorV2) throws InvalidParameterException{
		if(requestorV2 == null){
			throw new InvalidParameterException("Request is null");
		}
		
		RequestorDTO requestorDTO = new RequestorDTO();
		
		requestorDTO.setContext(requestorV2.getContext());
		requestorDTO.setApplicationCode(requestorV2.getApplicationCode());
		requestorDTO.setSignature(requestorV2.getSignature());
		requestorDTO.setSite(requestorV2.getSite());
		
		/**
		 * TODO: complete the mapping
		 */
		
		return requestorDTO;
	}
	
}
