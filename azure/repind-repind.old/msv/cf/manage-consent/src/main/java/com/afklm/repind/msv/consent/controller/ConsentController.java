package com.afklm.repind.msv.consent.controller;

import com.afklm.repind.msv.consent.criteria.ConsentCriteria;
import com.afklm.repind.msv.consent.model.CreateConsentModel;
import com.afklm.repind.msv.consent.model.CreateDefaultConsentModel;
import com.afklm.repind.msv.consent.model.UpdateConsentModel;
import com.afklm.repind.msv.consent.services.ConsentService;
import com.afklm.repind.msv.consent.services.exception.ServiceException;
import com.afklm.repind.msv.consent.wrapper.WrapperCreateConsentResponse;
import com.afklm.repind.msv.consent.wrapper.WrapperGetConsentResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
public class ConsentController {
	
	@Autowired
	ConsentService consentService;

	@Operation(summary = "Create all default consent for a customer", description = "Store all default consent for a customer based on reference table.<br />This ressource must be used only for individual creation.<br />"/*, response = WrapperCreateConsentResponse.class*/)
	@PostMapping(value = "/default/", consumes = "application/json", produces = "application/json; charset=utf-8")
	@ApiResponses(value = { @ApiResponse(responseCode = "400", description = "Bad request"),
			@ApiResponse(responseCode = "404", description = "Not found"), @ApiResponse(responseCode = "412", description = "Invalid parameter") })
	public ResponseEntity<WrapperCreateConsentResponse> createDefaultConsent(
			@Parameter(required = true, name = "default consent", description = "<b>gin</b>: Gin of an individual<br /><b>application:</b> Application name") @RequestBody(required = true) final CreateDefaultConsentModel consent)
			throws ServiceException {

		final ConsentCriteria consentCriteria = new ConsentCriteria();
		consentCriteria.setGin(consent.getGin());
		consentCriteria.setApplication(consent.getApplication());

		return consentService.createDefaultConsent(consentCriteria);
	}

	@Operation(summary = "Create consent for a customer",
			description = "Store consent for a customer.<br />"/*,responses = WrapperCreateConsentResponse.class*/)
	@PostMapping(value = "/", consumes = "application/json", produces = "application/json; charset=utf-8")
	@ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "412", description = "Invalid parameter")
            })
	public ResponseEntity<WrapperCreateConsentResponse> createConsent(
			@Parameter(required = true, name = "consent",
					description = "<b>gin</b>: Gin of an individual<br />"
					+ "<b>type</b>: Valid values are :<br />"
					+ "&nbsp;&nbsp;&nbsp;- DATA_PROCESSING : Recommendation for Paid Upgrade (B, S, U, M, L)<br />"
					+ "<b>data</b>: Consent data content.<br />"
					+ "&nbsp;&nbsp;&nbsp;<b>type</b> : Valid type are :<br />"
					+ "&nbsp;&nbsp;&nbsp;- For type DATA_PROCESSING :<br />"
					+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- GLOBAL<br />"
					+ "&nbsp;&nbsp;&nbsp;<b>isConsent</b> : Y or N<br />"
					+ "&nbsp;&nbsp;&nbsp;<b>dateConsent</b>: Date of consent<br />"
					+ "<b>application:</b> Application name<br />"
					) @RequestBody(required = true) final CreateConsentModel consent) 
					throws ServiceException {
		
		final ConsentCriteria consentCriteria = new ConsentCriteria();
		consentCriteria.setGin(consent.getGin());
		consentCriteria.setType(consent.getType());
		consentCriteria.setApplication(consent.getApplication());
		consentCriteria.setData(consent.getData());

		return consentService.createConsent(consentCriteria);
	}
	
	@Operation(summary = "Update consent for a customer", description = "Update consent for a customer" /*, response = WrapperGetConsentResponse.class*/)
	@PutMapping(value = "/", consumes = "application/json", produces = "application/json; charset=utf-8")
	@ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "412", description = "Invalid parameter")
            })
	public ResponseEntity<WrapperCreateConsentResponse> createConsentData(
			@Parameter(required = true, name = "consent",
					description = "<b>id</b>: Id for the consent to update<br />"
					+ "<b>gin</b>: Gin of an individual<br />"
					+ "<b>consent:</b> Valid values are :<br />"
					+ "&nbsp;&nbsp;&nbsp;- Y : Yes<br />"
					+ "&nbsp;&nbsp;&nbsp;- N : No<br />"
					+ "<b>application:</b> Application name<br />"
					) @RequestBody(required = true) final UpdateConsentModel consent) 
					throws ServiceException {
		
		final ConsentCriteria consentCriteria = new ConsentCriteria();
		consentCriteria.setId(consent.getId());
		consentCriteria.setGin(consent.getGin());
		consentCriteria.setIsConsent(consent.getIsConsent());
		consentCriteria.setDateConsent(consent.getDateConsent());
		consentCriteria.setApplication(consent.getApplication());

		return consentService.updateConsent(consentCriteria);
	}

	@Operation(summary = "Provide consent for a customer", description = "Get consent for a customer"/*, response = WrapperGetConsentResponse.class*/)
	@GetMapping(value = "/{gin}", produces = "application/json; charset=utf-8")
	public ResponseEntity<WrapperGetConsentResponse> provideInferredDataByGin(
			@Parameter(required = true, name = "gin", description = "Gin of an individual") @PathVariable final String gin)
					throws ServiceException {
		
		final ConsentCriteria consentCriteria = new ConsentCriteria();
		consentCriteria.setGin(gin);

		return consentService.provideConsentByGin(consentCriteria);
	}
}