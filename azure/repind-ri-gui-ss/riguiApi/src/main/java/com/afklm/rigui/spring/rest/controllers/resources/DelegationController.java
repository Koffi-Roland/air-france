package com.afklm.rigui.spring.rest.controllers.resources;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.afklm.rigui.model.individual.ModelDelegationData;
import com.afklm.rigui.model.individual.requests.ModelDelegationRequest;
import com.afklm.rigui.services.ServiceException;
import com.afklm.rigui.services.resources.DelegationService;
import com.afklm.rigui.wrapper.resources.WrapperResourceRead;
import com.afklm.rigui.wrapper.resources.WrapperResourceUpdate;
import com.afklm.soa.stubs.common.systemfault.v1.SystemException;

@RestController
@RequestMapping("/individual")
public class DelegationController {
	
	@Autowired
	private DelegationService delegationService;

	private static final Logger LOGGER = LoggerFactory.getLogger(AlertController.class);

	@RequestMapping(method = RequestMethod.GET, value = "/{gin}/delegations", produces = "application/json; charset=utf-8")
	@PreAuthorize(value = "hasAnyRole('ROLE_ADMIN','ROLE_READ_DELEG','ROLE_READ')")
	public ResponseEntity<WrapperResourceRead> getDelegationDetails(@PathVariable String gin) throws ServiceException {

		List<ModelDelegationData> delegations = delegationService.getAll(gin);
		
		WrapperResourceRead wrapper = new WrapperResourceRead();
		wrapper.data = delegations;

		return new ResponseEntity<>(wrapper, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.PUT, value = "/{gin}/delegations", produces = "application/json; "
					+ "charset=utf-8")
	@PreAuthorize(value = "hasAnyRole('ROLE_ADMIN','ROLE_UPDATE_DELEG','ROLE_UPDATE')")
	public ResponseEntity<WrapperResourceUpdate> putDelegationDetails(@RequestBody
					ModelDelegationRequest delegation,
					@PathVariable String gin) throws ServiceException, SystemException {

		delegation.setGin(gin);
		boolean success = delegationService.update(delegation , gin);
		WrapperResourceUpdate wrapper = new WrapperResourceUpdate();
		wrapper.success = success;
		wrapper.id = gin;

		if(wrapper.success) {
			LOGGER.info("Delegation has been updated successfully");
		} else {
			LOGGER.error("Something went wrong!");
		}

		return new ResponseEntity<>(wrapper, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/{gin}/delegations/{id}", produces = "application/json; "
					+ "charset=utf-8")
	@PreAuthorize(value = "hasAnyRole('ROLE_ADMIN','ROLE_DELETE_DELEG','ROLE_DELETE')")
	public ResponseEntity<?> deleteDelegation(@PathVariable String gin, @PathVariable String id)
					throws ServiceException, SystemException {

		boolean isSuccess = delegationService.delete(gin, id);

		if(isSuccess) {
			LOGGER.info("Delegation " + id + " has been deleted successfully!");
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			LOGGER.error("Something went wrong.");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

}
