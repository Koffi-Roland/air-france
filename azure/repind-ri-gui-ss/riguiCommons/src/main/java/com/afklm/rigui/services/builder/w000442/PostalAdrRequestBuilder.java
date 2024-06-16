package com.afklm.rigui.services.builder.w000442;

import com.afklm.soa.stubs.w000442.v8_0_1.xsd2.PostalAddressContent;
import com.afklm.soa.stubs.w000442.v8_0_1.xsd2.PostalAddressProperties;
import com.afklm.soa.stubs.w000442.v8_0_1.xsd3.PostalAddressRequest;
import com.afklm.soa.stubs.w000442.v8_0_1.xsd4.CreateUpdateIndividualRequest;
import org.springframework.stereotype.Component;

import com.afklm.rigui.model.individual.ModelAddress;
import com.afklm.rigui.services.builder.RequestType;

@Component
public class PostalAdrRequestBuilder extends W000442RequestBuilder {

	@Override
	public CreateUpdateIndividualRequest buildCreateRequest(String id, Object model) {
		
		CreateUpdateIndividualRequest request = null;

		request = mergeCommonRequestElements(id);
		PostalAddressRequest postalAddressRequest = getPostalAddressRequest(RequestType.CREATE, (ModelAddress) model);
		request.getPostalAddressRequest().add(postalAddressRequest);
		
		return request;
		
	}

	@Override
	public CreateUpdateIndividualRequest buildUpdateRequest(String id, Object model) {

		CreateUpdateIndividualRequest request = null;

		request = mergeCommonRequestElements(id);
		PostalAddressRequest postalAddressRequest = getPostalAddressRequest(RequestType.UPDATE, (ModelAddress) model);
		request.getPostalAddressRequest().add(postalAddressRequest);
		
		return request;
		
	}

	@Override
	public CreateUpdateIndividualRequest buildDeleteRequest(String id, Object model) {

		CreateUpdateIndividualRequest request = null;

		request = mergeCommonRequestElements(id);
		PostalAddressRequest postalAddressRequest = getPostalAddressRequest(RequestType.DELETE, (ModelAddress) model);
		request.getPostalAddressRequest().add(postalAddressRequest);
		
		return request;
		
	}

	/**
	 * Get the address request according to the parameters (the type of request and the address' model)
	 * @param type (RequestType)
	 * @param model (ModelAddress)
	 * @return a EmailRequest object
	 */
	private PostalAddressRequest getPostalAddressRequest(RequestType type, ModelAddress model) {

		PostalAddressRequest postalAddressRequest = new PostalAddressRequest();
		
		addPostalAddressProperties(type, model, postalAddressRequest);
		addPostalAddressContent(type, model, postalAddressRequest);
		
		return postalAddressRequest;
		
	}

	/**
	 * Get the properties of the address and if the type is a delete, we set the status to 'X'
	 * @param type (RequestType)
	 * @param model (ModelAddress)
	 * @param request (PostalAddressRequest)
	 */
	private void addPostalAddressProperties(RequestType type, ModelAddress model, PostalAddressRequest request) {
		
		PostalAddressProperties properties = new PostalAddressProperties();
		
		// If the request type is UPDATE or DELETE, then add the version of the resource
		if (type == RequestType.UPDATE || type == RequestType.DELETE) {
			properties.setVersion(model.getVersion());
		}
		
		// Set the medium code (i.e. type of the resource)
		properties.setMediumCode(model.getType());
		
		// If the request type is DELETE, then force the status to be 'X'
		if (type == RequestType.DELETE) {
			// Force to not normalize
			properties.setIndicAdrNorm(true);
			properties.setMediumStatus(DELETE_STATUS);
		} else {
			properties.setMediumStatus(model.getStatus());
		}
		
		// Set the normalization indicator
		if (type == RequestType.UPDATE || type == RequestType.CREATE) {
			boolean normalizationFlag = (!("O".equals(model.getForced())));
			properties.setIndicAdrNorm(normalizationFlag);
		}
		
		// Set the properties to the postal address request
		request.setPostalAddressProperties(properties);
		
	}
	
	/**
	 * Get the content of the postal address and put it in the postal address request
	 * @param type (RequestType)
	 * @param model (ModelAddress)
	 * @param postalAddressRequest (PostalAddressRequest)
	 */
	private void addPostalAddressContent(RequestType type, ModelAddress model, PostalAddressRequest postalAddressRequest) {
		
		PostalAddressContent content = new PostalAddressContent();
		
		content.setNumberAndStreet(model.getNumberAndStreet());
		content.setCity(model.getCity());
		content.setZipCode(model.getZipCode());
		content.setCountryCode(model.getCountry());
		
		if (type == RequestType.CREATE || type == RequestType.UPDATE) {
			content.setCorporateName(model.getCorporateName());
			content.setAdditionalInformation(model.getAddressComplement());
			content.setStateCode(model.getState());
		}
		
		postalAddressRequest.setPostalAddressContent(content);
		
	}

}
