package com.afklm.rigui.services.builder.w000442;

import com.afklm.soa.stubs.w000442.v8_0_1.xsd2.Telecom;
import com.afklm.soa.stubs.w000442.v8_0_1.xsd3.TelecomRequest;
import com.afklm.soa.stubs.w000442.v8_0_1.xsd4.CreateUpdateIndividualRequest;
import org.springframework.stereotype.Component;

import com.afklm.rigui.model.individual.ModelTelecom;
import com.afklm.rigui.services.builder.RequestType;

@Component
public class TelecomRequestBuilder extends W000442RequestBuilder {

	@Override
	public CreateUpdateIndividualRequest buildCreateRequest(String id, Object model) {

		CreateUpdateIndividualRequest request = null;

		request = mergeCommonRequestElements(id);
		TelecomRequest telecomRequest = getTelecomRequest(RequestType.CREATE, (ModelTelecom) model);
		request.getTelecomRequest().add(telecomRequest);
		
		return request;
		
	}

	@Override
	public CreateUpdateIndividualRequest buildUpdateRequest(String id, Object model) {

		CreateUpdateIndividualRequest request = null;

		request = mergeCommonRequestElements(id);
		TelecomRequest telecomRequest = getTelecomRequest(RequestType.UPDATE, (ModelTelecom) model);
		request.getTelecomRequest().add(telecomRequest);
		
		return request;
		
	}

	@Override
	public CreateUpdateIndividualRequest buildDeleteRequest(String id, Object model) {

		CreateUpdateIndividualRequest request = null;

		request = mergeCommonRequestElements(id);
		TelecomRequest telecomRequest = getTelecomRequest(RequestType.DELETE, (ModelTelecom) model);
		request.getTelecomRequest().add(telecomRequest);
		
		return request;
		
	}
	
	/**
	 * Get the telecom request according to the parameters (the type of request and the telecom's model)
	 * @param type (RequestType)
	 * @param modelTelecom (ModelTelecom)
	 * @return a TelecomRequest object
	 */
	private TelecomRequest getTelecomRequest(RequestType type, ModelTelecom modelTelecom) {
		
		TelecomRequest telecomRequest = new TelecomRequest();
		Telecom telecom = new Telecom();
		
		// The request type is an update so the version should be set in the telecom request
		if (type == RequestType.UPDATE || type == RequestType.DELETE) {
			telecom.setVersion(String.valueOf(modelTelecom.getVersion()));
		}
		
		// Add all the telecom's data to the request
		if (type == RequestType.DELETE) {
			telecom.setMediumStatus(DELETE_STATUS);
			telecom.setPhoneNumber(modelTelecom.getPhoneNumberNotNormalized());
		} else {
			telecom.setMediumStatus(modelTelecom.getStatus());
			telecom.setPhoneNumber(modelTelecom.getPhoneNumber());
		}
		telecom.setMediumCode(modelTelecom.getType());
		telecom.setTerminalType(modelTelecom.getTerminal());
		telecom.setCountryCode(modelTelecom.getCountryCode());
		telecomRequest.setTelecom(telecom);

		return telecomRequest;
		
	}

}
