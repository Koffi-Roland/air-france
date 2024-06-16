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

import com.afklm.rigui.model.individual.ModelCommunicationPreference;
import com.afklm.rigui.services.ServiceException;
import com.afklm.rigui.services.resources.CommunicationPreferenceService;
import com.afklm.rigui.wrapper.resources.WrapperResourceRead;
import com.afklm.rigui.wrapper.resources.WrapperResourceUpdate;
import com.afklm.soa.stubs.common.systemfault.v1.SystemException;

@RestController
@RequestMapping("/individual")
public class CommunicationPreferenceController {

private static final Logger LOGGER = LoggerFactory.getLogger(EmailController.class);
	
	@Autowired
	public CommunicationPreferenceService communicationPreferenceService;

	@RequestMapping(method = RequestMethod.GET, value = "/{gin}/communicationPreferences", produces = "application/json; charset=utf-8")
	@PreAuthorize(value = "hasAnyRole('ROLE_ADMIN','ROLE_READ_COMMPREF','ROLE_READ')")
	public ResponseEntity<WrapperResourceRead> getCommPrefDetails(@PathVariable String gin) throws ServiceException {

		List<ModelCommunicationPreference> commPrefs = communicationPreferenceService.getAll(gin);
		
		WrapperResourceRead wrapper = new WrapperResourceRead();
		wrapper.data = commPrefs;

		return new ResponseEntity<>(wrapper, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.PUT, value = "/{gin}/communicationPreference", produces = "application/json; charset=utf-8")
	@PreAuthorize(value = "hasAnyRole('ROLE_ADMIN', 'ROLE_UPDATE_COMMPREF','ROLE_UPDATE')")
	public ResponseEntity<WrapperResourceUpdate> updateCommunicationPreference(@RequestBody ModelCommunicationPreference commPref, @PathVariable String gin) throws SystemException, ServiceException {
		
		boolean success = communicationPreferenceService.update(commPref, gin);
		
		WrapperResourceUpdate wrapper = new WrapperResourceUpdate();
		wrapper.success = success;
		wrapper.id = commPref.getComPrefId().toString();
		
		if (wrapper.success) {
			LOGGER.info("Update of communication preference completed successfully!");
			return new ResponseEntity<>(wrapper, HttpStatus.OK);
		} else {
			LOGGER.error("Something went wrong!");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
	}
	
}
