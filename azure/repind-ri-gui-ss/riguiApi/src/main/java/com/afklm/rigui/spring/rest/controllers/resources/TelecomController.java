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

import com.afklm.rigui.model.individual.ModelTelecom;
import com.afklm.rigui.services.ServiceException;
import com.afklm.rigui.services.UrlService;
import com.afklm.rigui.services.resources.TelecomService;
import com.afklm.rigui.wrapper.resources.WrapperResourceRead;
import com.afklm.rigui.wrapper.resources.WrapperResourceUpdate;
import com.afklm.soa.stubs.common.systemfault.v1.SystemException;

@RestController
@RequestMapping("/individual")
public class TelecomController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(TelecomController.class);
	
	@Autowired
	private TelecomService telecomService;
	
	@Autowired
	private UrlService urlService;
	
	@RequestMapping(method = RequestMethod.POST, value = "/{gin}/telecom", produces = "application/json; charset=utf-8")
	@PreAuthorize(value = "hasAnyRole('ROLE_ADMIN', 'ROLE_CREATE_TELECOMS','ROLE_CREATE')")
	public ResponseEntity<?> createTelecom(@RequestBody ModelTelecom telecom, @PathVariable String gin) throws SystemException, ServiceException {
		
		boolean isSuccess = telecomService.createTelecom(telecom, gin);
		
		if (isSuccess) {
			LOGGER.info("Ceation of telecom successfully done !");
			return new ResponseEntity<>(isSuccess, HttpStatus.OK);
		} else {
			LOGGER.info("Something went wrong!");
			return new ResponseEntity<>(isSuccess, HttpStatus.BAD_REQUEST);
		}
		
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/{gin}/telecoms", produces = "application/json; charset=utf-8")
	@PreAuthorize(value = "hasAnyRole('ROLE_ADMIN','ROLE_READ_TELECOMS', 'ROLE_READ')")
	public ResponseEntity<WrapperResourceRead> getAll(@PathVariable String gin) throws ServiceException {

		List<ModelTelecom> telecoms = telecomService.getAll(gin);
		
		WrapperResourceRead wrapper = new WrapperResourceRead();
		wrapper.data = telecoms;

		return new ResponseEntity<>(wrapper, HttpStatus.OK);
		
	}
	
	@RequestMapping(method = RequestMethod.PUT, value = "/{gin}/telecom", produces = "application/json; charset=utf-8")
	@PreAuthorize(value = "hasAnyRole('ROLE_ADMIN', 'ROLE_UPDATE_TELECOMS','ROLE_UPDATE')")
	public ResponseEntity<WrapperResourceUpdate> updateTelecom(@RequestBody ModelTelecom providedTelecom, @PathVariable String gin) throws ServiceException, SystemException {
		
		boolean success = telecomService.update(providedTelecom, gin);
		
		WrapperResourceUpdate wrapper = new WrapperResourceUpdate();
		wrapper.success = success;
		wrapper.id = providedTelecom.getIdentifiant();
		
		if (wrapper.success) {
			LOGGER.info("Update of telecom successfully done !");
			return new ResponseEntity<>(wrapper, HttpStatus.OK);
		} else {
			LOGGER.info("KO!");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

	}
	
	@RequestMapping(method = RequestMethod.DELETE, value = "/{gin}/telecom/{id}", produces = "application/json; charset=utf-8")
	@PreAuthorize(value = "hasAnyRole('ROLE_ADMIN', 'ROLE_DELETE_TELECOMS', 'ROLE_DELETE')")
	public ResponseEntity<?> delete(@PathVariable String gin, HttpServletRequest request) throws SystemException, ServiceException {
		
		String id = urlService.extractResourceId(request.getRequestURL().toString());
		
		boolean success = telecomService.delete(gin, id);

		if (success) {
			LOGGER.info("Deletion of telecom successfully done !");
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			LOGGER.info("Something went wrong!");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

}
