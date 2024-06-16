package com.afklm.repind.msv.inferred.controller;

import com.afklm.repind.msv.inferred.criteria.IndividualCriteria;
import com.afklm.repind.msv.inferred.criteria.InferredDataCriteria;
import com.afklm.repind.msv.inferred.model.CreateInferredDataModel;
import com.afklm.repind.msv.inferred.services.InferredDataCreateService;
import com.afklm.repind.msv.inferred.services.InferredDataProvideService;
import com.afklm.repind.msv.inferred.services.InferredDataUpdateService;
import com.afklm.repind.msv.inferred.services.exception.ServiceException;
import com.afklm.repind.msv.inferred.wrapper.WrapperInferredDataCreateResponse;
import com.afklm.repind.msv.inferred.wrapper.WrapperInferredDataUpdateResponse;
import com.afklm.repind.msv.inferred.wrapper.WrapperProvideInferredDataResponse;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
public class InferredDataController {
	
	@Autowired
	InferredDataCreateService inferredDataCreateService;

	@Autowired
	InferredDataProvideService inferredDataProvideService;

	@Autowired
	InferredDataUpdateService inferredDataUpdateService;

	@ApiOperation(value = "Create inferred data for a customer", 
			notes = "Store inferred data for a customer.<br />"
					+ "Inferred data will be created with status 'C' (Calculated) by default", response = WrapperInferredDataCreateResponse.class)
	@PostMapping(value = "/", consumes = "application/json", produces = "application/json; charset=utf-8")
	@ApiResponses(value = {
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 404, message = "Not found"),
            @ApiResponse(code = 412, message = "Invalid parameter")
            })
	public ResponseEntity<WrapperInferredDataCreateResponse> createInferredData(
			@ApiParam(required = true, name = "inferredData",
			value = "<b>gin</b>: Gin of an individual<br />"
					+ "<b>type</b>: Valid values are :<br />"
					+ "&nbsp;&nbsp;&nbsp;- EML : Mail data<br />"
					+ "&nbsp;&nbsp;&nbsp;- TLC : Telecom data<br />"
					+ "&nbsp;&nbsp;&nbsp;- TXT : Text data<br />"
					+ "<b>data</b>: Inferred data content (Key / value form). Valid keys are :<br />"
					+ "&nbsp;&nbsp;&nbsp;- For type TLC :<br />"
					+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- COUNTRY_CODE_NUMBER : Country code number<br />"
					+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- NATIONAL_PHONE_NUMBER : Phone number nat<br />"
					+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- INTERNATIONAL_PHONE_NUMBER : Phone number inter<br />"
					+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- TERMINAL_TYPE : Terminal type<br />"
					+ "&nbsp;&nbsp;&nbsp;- For type EML :<br />"
					+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- EMAIL : Mail<br />"
					+ "&nbsp;&nbsp;&nbsp;- For type TXT :<br />"
					+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- TEXT : Free text<br />"
					+ "<b>application:</b> Application name<br />"
					) @RequestBody(required = true) final CreateInferredDataModel createInferredDataModel) 
					throws ServiceException {
		
		final InferredDataCriteria inferredDataCriteria = new InferredDataCriteria();
		inferredDataCriteria.setGin(createInferredDataModel.getGin());
		inferredDataCriteria.setType(createInferredDataModel.getType());
		inferredDataCriteria.setApplication(createInferredDataModel.getApplication());
		inferredDataCriteria.setData(createInferredDataModel.getData());
		
		return inferredDataCreateService.createInferredData(inferredDataCriteria);
	}

	@ApiOperation(value = "Provide inferred data for a customer", notes = "Get inferred data for a customer", response = WrapperProvideInferredDataResponse.class)
	@GetMapping(value = "/{gin}", produces = "application/json; charset=utf-8")
	public ResponseEntity<WrapperProvideInferredDataResponse> provideInferredDataByGin(
			@ApiParam(required = true, name = "gin", value = "Gin of an individual") @PathVariable final String gin) 
					throws ServiceException {
		
		final IndividualCriteria individualCriteria = new IndividualCriteria();
		individualCriteria.setGin(gin);
		
		return inferredDataProvideService.provideInferredDataByGin(individualCriteria);
	}
	

	@ApiOperation(value = "Update inferred data for a customer", notes = "Update inferred data for a customer", response = WrapperInferredDataUpdateResponse.class)
	@PutMapping(value = "/", consumes = "application/json", produces = "application/json; charset=utf-8")
	@ApiResponses(value = {
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 404, message = "Not found"),
            @ApiResponse(code = 412, message = "Invalid parameter")
            })
	public ResponseEntity<WrapperInferredDataUpdateResponse> updateInferredData(
			@ApiParam(required = true, name = "id", value = "Id for the inferred data to update") @RequestParam final Long id,
			@ApiParam(required = true, name = "gin", value = "Gin of an individual") @RequestParam final String gin,
			@ApiParam(required = true, name = "status", value = "Valid values are :<br />"
					+ "&nbsp;&nbsp;&nbsp;- V : Validated by customer<br />"
					+ "&nbsp;&nbsp;&nbsp;- R : Refused by customer") @RequestParam final String status,
			@ApiParam(required = true, name = "application", value = "Application name") @RequestParam final String application) 
					throws ServiceException {
		
		final InferredDataCriteria inferredDataCriteria = new InferredDataCriteria();
		inferredDataCriteria.setId(id);
		inferredDataCriteria.setGin(gin);
		inferredDataCriteria.setStatus(status);
		inferredDataCriteria.setApplication(application);
		
		return inferredDataUpdateService.updateInferredData(inferredDataCriteria);
	}
}