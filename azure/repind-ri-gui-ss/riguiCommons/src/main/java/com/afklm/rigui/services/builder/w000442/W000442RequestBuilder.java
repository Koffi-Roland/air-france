package com.afklm.rigui.services.builder.w000442;

import java.util.List;

import com.afklm.soa.stubs.w000442.v8_0_1.xsd2.IndividualInformationsV3;
import com.afklm.soa.stubs.w000442.v8_0_1.xsd3.IndividualRequest;
import com.afklm.soa.stubs.w000442.v8_0_1.xsd4.CreateUpdateIndividualRequest;
import com.afklm.soa.stubs.w000442.v8_0_1.xsd8.RequestorV2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.afklm.rigui.repository.IndividuAllRepository;
import com.afklm.rigui.entity.individu.IndividuLight;

@Component
public abstract class W000442RequestBuilder {
	
	protected static final String DELETE_STATUS = "X";
	
	private static final String DEFAULT_CHANNEL = "B2C";
	private static final String DEFAULT_SITE = "QVI";
	private static final String DEFAULT_SIGNATURE = "REPIND/IHM";
	
	private static final String CONTEXT = "B2C_HOME_PAGE";
	
	@Autowired
	private IndividuAllRepository individuAllRepository;
	
	// Build the create request for any object given in parameter (must be a resource model)
	public abstract CreateUpdateIndividualRequest buildCreateRequest(String id, Object model);
	
	// Build the update request for any object given in parameter (must be a resource model)
	public abstract CreateUpdateIndividualRequest buildUpdateRequest(String id, Object model);
	
	// Build the delete request for any object given in parameter (must be a resource model)
	public abstract CreateUpdateIndividualRequest buildDeleteRequest(String id, Object model);
	
	/**
	 * Merge the request parts that are on all W000442 requests
	 * @param id (String)
	 * @return a CreateUpdateIndividualRequest object
	 */
	protected CreateUpdateIndividualRequest mergeCommonRequestElements(String id) {
		CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();
		request.setRequestor(getRequestor());
		request.setIndividualRequest(getIndividualRequest(id));
		completeWSRequest(request, id);
		return request;
	}
	
	/**
	 * Get the individual request depending on the GIN given in parameter (id of the individual)
	 * @param individualID (String)
	 * @return a IndividualRequest object
	 */
	private IndividualRequest getIndividualRequest(String individualID) {
		IndividualRequest individualRequest = new IndividualRequest();
		IndividualInformationsV3 information = new IndividualInformationsV3();
		information.setIdentifier(individualID);
		individualRequest.setIndividualInformations(information);
		return individualRequest;
	}
	
	/**
	 * Get the requestor according to the given parameters
	 * @param channel (String)
	 * @param site (String)
	 * @param signature (String)
	 * @return a RequestorV2 object
	 */
	private RequestorV2 getRequestor(String channel, String site, String signature) {
		RequestorV2 requestor = new RequestorV2();
		requestor.setChannel(channel);
		requestor.setSite(site);
		requestor.setSignature(signature);
		return requestor;
	}
	
	/**
	 * Get the default requestor for the IHM SICv2 application
	 * @return a RequestorV2 object
	 */
	private RequestorV2 getRequestor() {
		return getRequestor(DEFAULT_CHANNEL, DEFAULT_SITE, DEFAULT_SIGNATURE);
	}
	
	/**
	 * Complete the WS request in the case of an individual with type W or T
	 * @param request
	 * @param identifier
	 */
	private void completeWSRequest(CreateUpdateIndividualRequest request, String identifier) {
		
		List<IndividuLight> individuals = individuAllRepository.findBySgin(identifier);
		String individualType = individuals.get(0).getType();
		
		if (individualType.equals("W") || individualType.equals("T")) {
			request.getRequestor().setContext(CONTEXT);
			request.setProcess(individualType);
		}
		
	}

}
