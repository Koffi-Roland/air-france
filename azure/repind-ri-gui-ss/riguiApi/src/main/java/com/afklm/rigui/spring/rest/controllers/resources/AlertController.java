package com.afklm.rigui.spring.rest.controllers.resources;

import org.omg.CORBA.SystemException;
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

import com.afklm.rigui.model.individual.ModelAlert;
import com.afklm.rigui.model.individual.requests.ModelAlertRequest;
import com.afklm.rigui.services.ServiceException;
import com.afklm.rigui.services.resources.AlertService;
import com.afklm.rigui.wrapper.resources.WrapperResourceRead;
import com.afklm.rigui.wrapper.resources.WrapperResourceUpdate;

@RestController
@RequestMapping("/individual")
public class AlertController {

	private static final Logger LOGGER = LoggerFactory.getLogger(AlertController.class);
	
	@Autowired
	private AlertService alertService;
	
	@RequestMapping(method = RequestMethod.GET, value = "/{gin}/alerts", produces = "application/json; charset=utf-8")
	@PreAuthorize(value = "hasAnyRole('ROLE_ADMIN','ROLE_READ_ALERTS','ROLE_READ')")
	public ResponseEntity<WrapperResourceRead> getAlertDetails(@PathVariable String gin) throws ServiceException {
		
		WrapperResourceRead wrapper = new WrapperResourceRead();
		
		wrapper.data = alertService.getAll(gin);

		return new ResponseEntity<>(wrapper, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.PUT, value="{gin}/alerts", produces = "application/json; charset=utf-8")
	@PreAuthorize(value = "hasAnyRole('ROLE_ADMIN','ROLE_UPDATE_ALERTS','ROLE_UPDATE')")
	public ResponseEntity<WrapperResourceUpdate> updateAlert(@RequestBody ModelAlertRequest alert,
					@PathVariable String gin)
					throws ServiceException,
					SystemException, com.afklm.soa.stubs.common.systemfault.v1.SystemException {
		boolean success = alertService.update(alert, gin);
		WrapperResourceUpdate wrapper = new WrapperResourceUpdate();
		wrapper.success = success;
		wrapper.id = alert.getSgin();

		if(wrapper.success) {
			LOGGER.info("Alert has been updated successfully");
		} else {
			LOGGER.error("Something went wrong!");
		}

		return new ResponseEntity<>(wrapper, HttpStatus.OK);
	}
	
}
