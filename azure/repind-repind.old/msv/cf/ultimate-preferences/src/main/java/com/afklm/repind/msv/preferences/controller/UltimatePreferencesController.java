package com.afklm.repind.msv.preferences.controller;

import com.afklm.repind.msv.preferences.criteria.IndividualCriteria;
import com.afklm.repind.msv.preferences.criteria.RequestorCriteria;
import com.afklm.repind.msv.preferences.criteria.UltimatePreferencesCriteria;
import com.afklm.repind.msv.preferences.model.CreateUltimatePreferencesModel;
import com.afklm.repind.msv.preferences.model.error.RestError;
import com.afklm.repind.msv.preferences.services.UltimatePreferencesCreateService;
import com.afklm.repind.msv.preferences.services.UltimatePreferencesProvideService;
import com.afklm.repind.msv.preferences.services.builder.W000442BusinessErrorHandler;
import com.afklm.repind.msv.preferences.services.exception.ServiceException;
import com.afklm.repind.msv.preferences.wrapper.WrapperCreateUltimatePreferencesResponse;
import com.afklm.repind.msv.preferences.wrapper.WrapperProvideUltimatePreferencesResponse;
import com.afklm.soa.stubs.common.systemfault.v1.SystemException;
import com.afklm.soa.stubs.common.systemfault.v1.SystemFault;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;


@RestController
@Slf4j
public class UltimatePreferencesController {
	
	@Autowired
	UltimatePreferencesProvideService ultimatePreferencesProvideService;

	@Autowired
	UltimatePreferencesCreateService ultimatePreferencesCreateService;
	
	@Operation(summary = "Map the Provide Ultimate Preferences for CBS", description = "Get ultimate preferences data for a customer"/*, response = WrapperProvideUltimatePreferencesResponse.class*/)
	@GetMapping(value = "/{gin}", produces = "application/json; charset=utf-8")
	public ResponseEntity<WrapperProvideUltimatePreferencesResponse> provideUltimatePreferencesByGin(
			@Parameter(required = true, name = "gin", description = "Gin of an individual") @PathVariable final String gin)
					throws ServiceException {
		
		log.info("GET for " + gin);

		final IndividualCriteria individualCriteria = new IndividualCriteria();
		individualCriteria.setGin(gin);

		return ultimatePreferencesProvideService.provideUltimatePreferencesByGin(individualCriteria);
	}
	
	
	@Operation(summary = "Map the Create Ultimate Preferences for CBS", description = "Store ultimate preferences data for a customer"/*, response = WrapperCreateUltimatePreferencesResponse.class*/)
	@PostMapping(value = "/", consumes = "application/json", produces = "application/json; charset=utf-8")
	@ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "412", description = "Invalid parameter")
            })
	public ResponseEntity<WrapperCreateUltimatePreferencesResponse> createUltimatePreferences(
			@Parameter(required = true, name = "ultimatePreferences",
			description = "Go and See SSD of <b>ManageExtendedPreferences</b><br />"
					) @RequestBody(required = true) final CreateUltimatePreferencesModel createUltimatePreferencesModel) 
					throws ServiceException, SystemException {

		log.info("POST for " + createUltimatePreferencesModel.getGin());
		
		final UltimatePreferencesCriteria ultimatePreferencesCriteria = new UltimatePreferencesCriteria();
		ultimatePreferencesCriteria.setGin(createUltimatePreferencesModel.getGin());
		ultimatePreferencesCriteria.setType(createUltimatePreferencesModel.getType());						// ExtendedPreference.preference
		ultimatePreferencesCriteria.setData(createUltimatePreferencesModel.getData());						// ExtendedPreference.subPreference
		ultimatePreferencesCriteria.setActionCode(createUltimatePreferencesModel.getActionCode());
		
		
		final RequestorCriteria requestor = new RequestorCriteria();
		if(createUltimatePreferencesModel.getRequestor() != null) {
			requestor.setApplication(checkCodeForNullAndSize(createUltimatePreferencesModel.getRequestor().getApplication(), 3));
			requestor.setChannel(checkCodeForNullAndSize(createUltimatePreferencesModel.getRequestor().getChannel(), 5));
			requestor.setCompany(checkCodeForNullAndSize(createUltimatePreferencesModel.getRequestor().getCompany(), 3));
			requestor.setIpAddress(checkCodeForNullAndSize(createUltimatePreferencesModel.getRequestor().getIpAddress(), 45));
			requestor.setMatricule(checkCodeForNullAndSize(createUltimatePreferencesModel.getRequestor().getMatricule(), 15));
			requestor.setOfficeId(checkCodeForNullAndSize(createUltimatePreferencesModel.getRequestor().getOfficeId(), 9));
			requestor.setSignature(checkCodeForNullAndSize(createUltimatePreferencesModel.getRequestor().getSignature(), 16));
			requestor.setSite(checkCodeForNullAndSize(createUltimatePreferencesModel.getRequestor().getSite(), 10));
		}
		ultimatePreferencesCriteria.setRequestor(requestor);
		
		return ultimatePreferencesCreateService.manageUltimatePreferences(ultimatePreferencesCriteria);
	}
	
	private String checkCodeForNullAndSize(String valeur, int taille) throws ServiceException {
		
		if (valeur == null) {
			return valeur;
		}
		
		if (!valeur.equals("") && valeur.length() <= taille) {
			return valeur;
		}
		
		log.error("checkCodeForNullAndSize failed for '" + valeur + "'");
		
		RestError restError = W000442BusinessErrorHandler.handleSystemError(new SystemFault());
		throw new ServiceException(restError, HttpStatus.BAD_REQUEST);
	}

}
