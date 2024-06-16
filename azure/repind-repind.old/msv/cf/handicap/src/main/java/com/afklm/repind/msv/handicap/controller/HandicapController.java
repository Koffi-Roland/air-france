package com.afklm.repind.msv.handicap.controller;

import com.afklm.repind.msv.handicap.criteria.HandicapCriteria;
import com.afklm.repind.msv.handicap.model.CreateHandicapModel;
import com.afklm.repind.msv.handicap.model.HandicapDataCreateModel;
import com.afklm.repind.msv.handicap.services.HandicapCreateService;
import com.afklm.repind.msv.handicap.services.HandicapDeleteService;
import com.afklm.repind.msv.handicap.services.HandicapProvideService;
import com.afklm.repind.msv.handicap.services.HandicapUpdateService;
import com.afklm.repind.msv.handicap.services.exception.ServiceException;
import com.afklm.repind.msv.handicap.wrapper.WrapperHandicapCreateResponse;
import com.afklm.repind.msv.handicap.wrapper.WrapperHandicapDeleteResponse;
import com.afklm.repind.msv.handicap.wrapper.WrapperHandicapProvideResponse;
import com.afklm.repind.msv.handicap.wrapper.WrapperHandicapUpdateResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
public class HandicapController {
	
	@Autowired
	HandicapCreateService handicapCreateService;

	@Autowired
	HandicapProvideService handicapProvideService;

	@Autowired
	HandicapUpdateService handicapUpdateService;
	
	@Autowired
	HandicapDeleteService handicapDeleteService;

	@Operation(summary  = "Create Handicap for a customer",
			description  = "Create Handicap and Handicap data for a customer"/*, response = WrapperHandicapCreateResponse.class*/)
	@PostMapping(value = "/", consumes = "application/json", produces = "application/json; charset=utf-8")
	@ApiResponses(value = {
            @ApiResponse(responseCode  = "400", description = "Bad request"),
            @ApiResponse(responseCode  = "404", description = "Not found"),
            @ApiResponse(responseCode  = "412", description = "Invalid parameter")
            })
	public ResponseEntity<WrapperHandicapCreateResponse> createHandicap(
			@Parameter(required = true, name = "gin", description = "Gin of the individual")
			@RequestParam(value = "gin") final String gin,
			@Parameter(required = true, name = "application", description = "Signature for the creation")
			@RequestParam(value = "application") final String application,
			@Parameter(required = true, name = "listHandicap",
					description = "List of Handicaps to be created. Each Handicap must have the following information <br />"
					+ "&nbsp;&nbsp;&nbsp;- <b>type</b>: Type of Handicap to create : HCP, MAT, SUP, DOG, OTH, OXG (See above for more details) <br />"
					+ "&nbsp;&nbsp;&nbsp;- <b>code</b>: Code associated to the Type of Handicap to create (See above for more details) <br />"
					+ "&nbsp;&nbsp;&nbsp;- <b>data</b>: Contains a list of key/value used for adding more informations to an Handicap (See above the list of data available for a specific Type <br />"
					) @RequestBody final List<CreateHandicapModel> listHandicap)
					throws ServiceException {
		
		final HandicapCriteria handicapCriteria = new HandicapCriteria();
		handicapCriteria.setGin(gin);
		handicapCriteria.setApplication(application);
		handicapCriteria.setHandicap(listHandicap);
		
		return handicapCreateService.createHandicap(handicapCriteria);
	}
	
	@Operation(summary  = "Provide Handicap for a customer",
			description  = "Provide Handicap or Material for a customer in 3 ways:<br />"
			+ "&nbsp;&nbsp;&nbsp;- All Handicaps<br>"
			+ "&nbsp;&nbsp;&nbsp;- Filtered out by Type<br>"
			+ "&nbsp;&nbsp;&nbsp;- Filtered out by Type + Code"/*, response = WrapperHandicapProvideResponse.class*/)
	@GetMapping(value = "/", produces = "application/json; charset=utf-8")
	@ApiResponses(value = {
            @ApiResponse(responseCode  = "400", description  = "Bad request"),
            @ApiResponse(responseCode  = "404", description  = "Not found"),
            @ApiResponse(responseCode  = "412", description  = "Invalid parameter")
            })
	public ResponseEntity<WrapperHandicapProvideResponse> provideHandicap(
			@Parameter(required = true, name = "gin", description = "Gin of the individual")
			@RequestParam(value = "gin", required = true) final String gin,
			@Parameter(required = false, name = "type", description = "Type of the handicap")
			@RequestParam(value = "type", required = false) final String type,
			@Parameter(required = false, name = "code", description = "Code of the handicap")
			@RequestParam(value = "code", required = false) final String code) throws ServiceException {
		
		final HandicapCriteria handicapCriteria = new HandicapCriteria();
		handicapCriteria.setGin(gin);
		
		ResponseEntity<WrapperHandicapProvideResponse> response = null;
		
		if (StringUtils.isEmpty(type) && StringUtils.isEmpty(code)) {
			response = handicapProvideService.provideHandicapByGin(handicapCriteria);
		} else if (StringUtils.isNotEmpty(type) && StringUtils.isEmpty(code)) {
			handicapCriteria.setType(type);
			response = handicapProvideService.provideHandicapByGinAndType(handicapCriteria);
		} else if (StringUtils.isNotEmpty(code)) {
			handicapCriteria.setType(type);
			handicapCriteria.setCode(code);
			response = handicapProvideService.provideHandicapByGinAndTypeAndCode(handicapCriteria);
		}
		
		return response;
	}
	
	@Operation(summary  = "Delete Handicap for a customer",
			description  = "Delete a Handicap or Material for a customer in 2 ways:<br />"
			+ "&nbsp;&nbsp;&nbsp;- Delete a complete Handicap<br>"
			+ "&nbsp;&nbsp;&nbsp;- Delete a list of keys for a Handicap<br>"/*, response = WrapperHandicapDeleteResponse.class*/)
	@DeleteMapping(value = "/", consumes = "application/json", produces = "application/json; charset=utf-8")
	@ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "412", description = "Invalid parameter")
            })
	public ResponseEntity<WrapperHandicapDeleteResponse> deleteHandicap(
			@Parameter(required = true, name = "id", description = "Id of the Handicap", example = "123")
			@RequestParam(value = "id", required = true) final Long id,
			@Parameter(required = false, name = "keys", description = "List of Keys of Handicap Data")
			@RequestBody(required = false) final List<String> keys)  throws ServiceException {
		
		final HandicapCriteria handicapCriteria = new HandicapCriteria();
		handicapCriteria.setId(id);
		
		if (keys != null && !keys.isEmpty()) {
			handicapCriteria.setHandicapData(new ArrayList<>());
			
			for (String key : keys) {
				HandicapDataCreateModel handicapDataCreateModel = new HandicapDataCreateModel();
				handicapDataCreateModel.setKey(key);
				handicapCriteria.getHandicapData().add(handicapDataCreateModel);
			}
		}
		
		return handicapDeleteService.deleteHandicap(handicapCriteria);
	}

	@Operation(summary = "Update handicap for a customer", description  = "Update handicap for a customer"/*, response = WrapperHandicapUpdateResponse.class*/)
	@PutMapping(value = "/", consumes = "application/json", produces = "application/json; charset=utf-8")
	@ApiResponses(value = {
            @ApiResponse(responseCode  = "400", description  = "Bad request"),
            @ApiResponse(responseCode  = "404", description  = "Not found"),
            @ApiResponse(responseCode  = "412", description  = "Invalid parameter")
            })
	public ResponseEntity<WrapperHandicapUpdateResponse> updateHandicap(
			@Parameter(required = true, name = "id", description = "Id for the handicap to update", example = "123") @RequestParam final Long id,
			@Parameter(required = true, name = "gin", description = "Gin of an individual") @RequestParam final String gin,
			@Parameter(required = true, name = "application", description = "Application name") @RequestParam final String application,
			@Parameter(required = true, name = "handicap",
					description = "List of data (key/value) to be updated for this Handicap Id. <br />"
					+ "This method can only update data (key/value) or create a new one. If you want to delete a data, you have to use the Delete Method. </br >"
					+ "See above for more details concerning the key to use."
					) @RequestBody(required = true) final List<HandicapDataCreateModel> listHandicapData) 
					throws ServiceException {
		
		final HandicapCriteria handicapCriteria = new HandicapCriteria();
		handicapCriteria.setId(id);
		handicapCriteria.setGin(gin);
		handicapCriteria.setApplication(application);
		handicapCriteria.setHandicapData(listHandicapData);
		
		return handicapUpdateService.updateHandicap(handicapCriteria);
	}
}
