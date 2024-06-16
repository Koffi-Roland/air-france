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

import com.afklm.rigui.model.individual.ModelPreference;
import com.afklm.rigui.services.ServiceException;
import com.afklm.rigui.services.UrlService;
import com.afklm.rigui.services.resources.PreferenceService;
import com.afklm.rigui.wrapper.resources.WrapperResourceRead;
import com.afklm.rigui.wrapper.resources.WrapperResourceUpdate;
import com.afklm.soa.stubs.common.systemfault.v1.SystemException;

@RestController
@RequestMapping("/individual")
public class PreferenceController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(PreferenceController.class);
	
	@Autowired
	public PreferenceService preferenceService;
	
	@Autowired
	public UrlService urlService;
	
	@RequestMapping(method = RequestMethod.POST, value = "/{gin}/preference", produces = "application/json; charset=utf-8")
	@PreAuthorize(value = "hasAnyRole('ROLE_ADMIN', 'ROLE_CREATE_PREF','ROLE_CREATE')")
	public ResponseEntity<?> createPreference(@RequestBody ModelPreference preference, @PathVariable String gin) throws ServiceException, SystemException {
		
		boolean isSuccess = preferenceService.create(preference, gin);
		
		if (isSuccess) {
			LOGGER.info("Preference has been created successfully!");
			return new ResponseEntity<>(isSuccess, HttpStatus.OK);
		} else {
			LOGGER.error("Something went wrong!");
			return new ResponseEntity<>(isSuccess, HttpStatus.BAD_REQUEST);
		}
		
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/{gin}/preferences", produces = "application/json; charset=utf-8")
	@PreAuthorize(value = "hasAnyRole('ROLE_ADMIN','ROLE_READ_PREF','ROLE_READ')")
	public ResponseEntity<WrapperResourceRead> getPreferencesDetails(@PathVariable String gin) throws ServiceException {

		List<ModelPreference> modelPreferences = preferenceService.getAll(gin);
		
		WrapperResourceRead wrapper = new WrapperResourceRead();
		wrapper.data = modelPreferences;

		return new ResponseEntity<>(wrapper, HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.PUT, value = "/{gin}/preference", produces = "application/json; charset=utf-8")
	@PreAuthorize(value = "hasAnyRole('ROLE_ADMIN', 'ROLE_UPDATE_PREF','ROLE_UPDATE')")
	public ResponseEntity<WrapperResourceUpdate> updatePreference(@RequestBody ModelPreference preference, @PathVariable String gin) throws ServiceException, SystemException {
		
		boolean success = preferenceService.update(preference, gin);
		
		WrapperResourceUpdate wrapper = new WrapperResourceUpdate();
		wrapper.success = success;
		wrapper.id = preference.getPreferenceId().toString();
		
		if (wrapper.success) {
			LOGGER.info("Preference has been updated successfully!");
			return new ResponseEntity<>(wrapper, HttpStatus.OK);
		} else {
			LOGGER.error("Something went wrong!");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
	}
	
	@RequestMapping(method = RequestMethod.DELETE, value = "/{gin}/preference/{id}", produces = "application/json; charset=utf-8")
	@PreAuthorize(value = "hasAnyRole('ROLE_ADMIN', 'ROLE_DELETE_PREF', 'ROLE_DELETE')")
	public ResponseEntity<?> deletePreference(@PathVariable String gin, HttpServletRequest request) throws ServiceException, SystemException {
		
		String id = urlService.extractResourceId(request.getRequestURL().toString());
		
		boolean success = preferenceService.delete(gin, id);
		
		if (success) {
			LOGGER.info("Preference has been deleted successfully!");
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			LOGGER.error("Something went wrong!");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
	}

}
