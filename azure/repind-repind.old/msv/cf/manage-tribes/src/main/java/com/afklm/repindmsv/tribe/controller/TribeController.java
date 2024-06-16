package com.afklm.repindmsv.tribe.controller;

import com.afklm.repind.common.exception.BusinessException;
import com.afklm.repindmsv.tribe.controller.checker.TribeChecker;
import com.afklm.repindmsv.tribe.model.TribeModel;
import com.afklm.repindmsv.tribe.services.TribeService;
import com.afklm.repindmsv.tribe.wrapper.WrapperRetrieveTribeMemberResponse;
import com.afklm.repindmsv.tribe.wrapper.WrapperTribeResponse;
import com.afklm.repindmsv.tribe.wrapper.WrapperTribesResponse;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/manage-tribes")
public class TribeController {
	
	@Autowired
	TribeService tribeService;

	@Autowired
	TribeChecker tribeChecker;
	
	@ApiOperation(value = "Get a tribe by ID", 
			notes = "Find a tribe by its identifier.<br />"
					+ "The application must be allowed to get the tribe.<br />"
					+ "See list of allowed applications.", response = TribeModel.class)
	@GetMapping(value = "tribe")
	@ApiResponses(value = {
            @ApiResponse(code = 400, message = "Bad request :<br />"
            		+ "- business.400.005 : Application is missing<br />"
            		+ "- business.400.006 : TribeId is missing"),
            @ApiResponse(code = 404, message = "Not found :<br />"
            		+ "- business.404.002 : Tribe not found")
            })
	public ResponseEntity<TribeModel> getTribeById(
			@ApiParam(required = true, name = "id", value = "Id of a tribe")
			@RequestParam(value = "id", required = true) final String id,
			@ApiParam(required = true, name = "application", value = "Signature of the request")
			@RequestParam(value = "application", required = true) final String application) 
					throws BusinessException {
		
		return tribeService.findTribeById(tribeChecker.checkRetrieveByIdTribeCriteria(id, application));
	}



	@ApiOperation(value = "Get tribes by member gin",
			notes = "Get the member of a family.<br />"
					+ "The application must be allowed to get the tribe.<br />"
					+ "See list of allowed applications.", response = WrapperTribesResponse.class)
	@GetMapping(value = "tribe-member")
	@ApiResponses(value = {
			@ApiResponse(code = 400, message = "Bad request :<br />"
					+ "- business.400.001 : Gin is missing<br />"
					+ "- business.400.005 : Application is missing"),
			@ApiResponse(code = 404, message = "Not found :<br />"
					+ "- business.404.003 : Member not found"),
			@ApiResponse(code = 412, message = "Precondition failed :<br />"
					+ "- business.412.001 : Invalid value for the 'gin' parameter, length must be equal to 12")
	})
	public ResponseEntity<WrapperRetrieveTribeMemberResponse> getTribesByGin(
			@ApiParam(required = true, name = "gin", value = "Gin of an individual")
			@RequestParam(value = "gin", required = true) final String gin,
			@ApiParam(required = true, name = "application", value = "Signature of the request")
			@RequestParam(value = "application", required = true) final String application) 
					throws BusinessException {
		
		return tribeService.findTribeByGin(tribeChecker.checkRetrieveByGinTribeCriteria(gin, application));
	}

	@ApiOperation(value = "Create a tribe", 
			notes = "Create a tribe and associate it with a manager.<br />"
					+ "The application must be allowed to manage the tribe.<br />"
					+ "See list of allowed applications.", response = WrapperTribeResponse.class)
	@PostMapping(value = "tribe")
	@ApiResponses(value = {
            @ApiResponse(code = 400, message = "Bad request :<br />"
            		+ "- business.400.001 : Manager is mandatory<br />"
            		+ "- business.400.005 : Application is mandatory<br />"
            		+ "- business.400.003 : Type is mandatory<br />"
            		+ "- business.400.002 : Name is mandatory"),
			@ApiResponse(code = 412, message = "Precondition failed :<br />"
					+ "- business.412.001 : Invalid value for the 'gin' parameter, length must be equal to 12")
            })
	public ResponseEntity<WrapperTribeResponse> createTribe(
			@ApiParam(required = true, name = "name", value = "Name of the tribe")
			@RequestParam(value = "name", required = true) final String name,
			@ApiParam(required = true, name = "type", value = "Type of the tribe")
			@RequestParam(value = "type", required = true) final String type,
			@ApiParam(required = true, name = "manager", value = "Gin of the individual who manages the tribe")
			@RequestParam(value = "manager", required = true) final String manager,
			@ApiParam(required = true, name = "application", value = "Signature for the creation")
			@RequestParam(value = "application", required = true) final String application) 
					throws BusinessException {
		
		return tribeService.createTribe(tribeChecker.checkCreateTribeCriteria(name, type, manager, application));
	}


	@ApiOperation(value = "Delete a tribe", 
			notes = "Delete a tribe by its id.<br />"
					+ "The application must be allowed to manage this tribe.<br />"
					+ "See list of allowed applications.", response = WrapperTribeResponse.class)
	@DeleteMapping(value = "tribe")
	@ApiResponses(value = {
			@ApiResponse(code = 400, message = "Bad request :<br />"
					+ "- business.400.005 : Application is missing<br />"
					+ "- business.400.006 : TribeId is missing"),
            @ApiResponse(code = 404, message = "Not found :<br />"
            		+ "- business.404.002 : Tribe not found")
            })
	public ResponseEntity<WrapperTribeResponse> deleteTribe(
			@ApiParam(required = true, name = "id", value = "Id of the tribe to remove")
			@RequestParam(value = "id", required = true) final String id,
			@ApiParam(required = true, name = "application", value = "Signature for the deletion")
			@RequestParam(value = "application", required = true) final String application) 
					throws BusinessException {
		
		return tribeService.deleteTribe(tribeChecker.checkDeleteTribeCriteria(id, application));
	}

}