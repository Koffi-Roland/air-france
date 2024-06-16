package com.afklm.rigui.spring.rest.controllers.resources;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

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

import com.afklm.rigui.model.individual.ModelRoleGP;
import com.afklm.rigui.services.ServiceException;
import com.afklm.rigui.services.UrlService;
import com.afklm.rigui.services.resources.RoleGPService;
import com.afklm.rigui.wrapper.resources.WrapperResourceRead;
import com.afklm.rigui.wrapper.resources.WrapperResourceUpdate;
import com.afklm.soa.stubs.common.systemfault.v1.SystemException;

@RestController
@RequestMapping("/individual")
public class RoleGPController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(RoleGPController.class);
	
	@Autowired
	private RoleGPService roleGPService;
	
	@Autowired
	private UrlService urlService;
	
	@RequestMapping(method = RequestMethod.GET, value = "/{gin}/rolesGP", produces = "application/json; charset=utf-8")
	@PreAuthorize(value = "hasAnyRole('ROLE_ADMIN','ROLE_READ_GPROLES','ROLE_READ')")
	public ResponseEntity<WrapperResourceRead> getRoleGPDetails(@PathVariable String gin) throws ServiceException {

		List<ModelRoleGP> modelRoleGPs = roleGPService.getAll(gin);
		
		WrapperResourceRead wrapper = new WrapperResourceRead();
		wrapper.data = modelRoleGPs;

		return new ResponseEntity<>(wrapper, HttpStatus.OK);
		
	}
	
	@RequestMapping(method = RequestMethod.PUT, value = "/{gin}/rolegp", produces = "application/json; charset=utf-8")
	@PreAuthorize(value = "hasAnyRole('ROLE_ADMIN', 'ROLE_UPDATE_GP','ROLE_UPDATE')")
	public ResponseEntity<WrapperResourceUpdate> updateRoleGP(@RequestBody ModelRoleGP modelRoleGP, @PathVariable String gin) throws ServiceException, SystemException {
		
		boolean success = roleGPService.update(modelRoleGP, gin);
		
		WrapperResourceUpdate wrapper = new WrapperResourceUpdate();
		wrapper.success = success;
		wrapper.id = modelRoleGP.getCleRole().toString();
		
		if (wrapper.success) {
			LOGGER.info("Update of role GP successfully done !");
			return new ResponseEntity<>(wrapper, HttpStatus.OK);
		} else {
			LOGGER.info("KO!");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

	}
	
	@RequestMapping(method = RequestMethod.DELETE, value = "/{gin}/rolegp/{id}", produces = "application/json; charset=utf-8")
	@PreAuthorize(value = "hasAnyRole('ROLE_ADMIN', 'ROLE_DELETE_GP', 'ROLE_DELETE')")
	public ResponseEntity<?> deleteRoleGP(@PathVariable String gin, HttpServletRequest request) throws SystemException, ServiceException {
		
		String id = urlService.extractResourceId(request.getRequestURL().toString());
		
		boolean success = roleGPService.delete(gin, id);
		
		if (success) {
			LOGGER.info("Deletion of roleGP successfully done !");
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			LOGGER.info("Something went wrong!");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

	}

}
