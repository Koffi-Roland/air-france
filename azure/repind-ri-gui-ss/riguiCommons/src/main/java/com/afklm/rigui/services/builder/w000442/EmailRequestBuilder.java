package com.afklm.rigui.services.builder.w000442;
import com.afklm.soa.stubs.w000442.v8_0_1.xsd2.Email;
import com.afklm.soa.stubs.w000442.v8_0_1.xsd3.EmailRequest;
import com.afklm.soa.stubs.w000442.v8_0_1.xsd4.CreateUpdateIndividualRequest;
import org.springframework.stereotype.Component;

import com.afklm.rigui.model.individual.ModelEmail;
import com.afklm.rigui.services.builder.RequestType;

@Component
public class EmailRequestBuilder extends W000442RequestBuilder {

	@Override
	public CreateUpdateIndividualRequest buildCreateRequest(String id, Object model) {
		
		CreateUpdateIndividualRequest request = null;
		
		request = mergeCommonRequestElements(id);
		EmailRequest emailRequest = getEmailRequest(RequestType.CREATE, (ModelEmail) model);
		request.getEmailRequest().add(emailRequest);
		
		return request;
		
	}
	
	@Override
	public CreateUpdateIndividualRequest buildUpdateRequest(String id, Object model) {
		
		CreateUpdateIndividualRequest request = null;

		request = mergeCommonRequestElements(id);
		EmailRequest emailRequest = getEmailRequest(RequestType.UPDATE, (ModelEmail) model);
		request.getEmailRequest().add(emailRequest);
		
		return request;
		
	}
	
	@Override
	public CreateUpdateIndividualRequest buildDeleteRequest(String id, Object model) {
		
		CreateUpdateIndividualRequest request = null;

		request = mergeCommonRequestElements(id);
		EmailRequest emailRequest = getEmailRequest(RequestType.DELETE, (ModelEmail) model);
		request.getEmailRequest().add(emailRequest);
		
		return request;
		
	}
	
	/**
	 * Get the email request according to the parameters (the type of request and the email's model)
	 * @param type (RequestType)
	 * @param modelEmail (ModelEmail)
	 * @return a EmailRequest object
	 */
	private EmailRequest getEmailRequest(RequestType type, ModelEmail modelEmail) {
		
		EmailRequest emailRequest = new EmailRequest();
		Email email = new Email();
		
		// The request type is an update so the version should be set in the email request
		if (type == RequestType.UPDATE || type == RequestType.DELETE) {
			email.setVersion(String.valueOf(modelEmail.getVersion()));
		}
		
		// Add all the email's data to the request
		email.setMediumStatus((type == RequestType.DELETE) ? DELETE_STATUS : modelEmail.getStatus());
		email.setMediumCode(modelEmail.getType());
		email.setEmail(modelEmail.getEmail());
		email.setEmailOptin(modelEmail.getAuthorizationMailing());
		emailRequest.setEmail(email);
		
		return emailRequest;
		
	}

}
