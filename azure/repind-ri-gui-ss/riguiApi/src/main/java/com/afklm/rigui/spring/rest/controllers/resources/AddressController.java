package com.afklm.rigui.spring.rest.controllers.resources;

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

import com.afklm.rigui.model.individual.ModelAddress;
import com.afklm.rigui.services.ServiceException;
import com.afklm.rigui.services.UrlService;
import com.afklm.rigui.services.resources.PostalAddressService;
import com.afklm.rigui.wrapper.resources.WrapperResourceRead;
import com.afklm.rigui.wrapper.resources.WrapperResourceUpdate;
import com.afklm.soa.stubs.common.systemfault.v1.SystemException;

@RestController
@RequestMapping("/individual")
public class AddressController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AddressController.class);
	
	@Autowired
	private PostalAddressService postalAddressService;
	
	@Autowired
	private UrlService urlService;
	
	@RequestMapping(method = RequestMethod.POST, value = "/{gin}/address", produces = "application/json; charset=utf-8")
	@PreAuthorize(value = "hasAnyRole('ROLE_ADMIN', 'ROLE_CREATE_ADDR', 'ROLE_CREATE')")
	public ResponseEntity<?> createAddress(@RequestBody ModelAddress address, @PathVariable String gin) throws ServiceException, SystemException {
		
		boolean success = postalAddressService.create(address, gin);
		
		if (success) {
			LOGGER.info("Address has been created successfully!");
			return new ResponseEntity<>(success, HttpStatus.OK);
		} else {
			LOGGER.error("Something went wrong!");
			return new ResponseEntity<>(success, HttpStatus.OK);
		}
		
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/{gin}/addresses", produces = "application/json; charset=utf-8")
	@PreAuthorize(value = "hasAnyRole('ROLE_ADMIN','ROLE_READ_ADDR', 'ROLE_READ')")
	public ResponseEntity<WrapperResourceRead> getAddresses(@PathVariable String gin) throws ServiceException {

		WrapperResourceRead wrapper = new WrapperResourceRead();
	
		wrapper.data = postalAddressService.getAll(gin);

		return new ResponseEntity<>(wrapper, HttpStatus.OK);
		
	}
	
	@RequestMapping(method = RequestMethod.PUT, value = "/{gin}/address", produces = "application/json; charset=utf-8")
	@PreAuthorize(value = "hasAnyRole('ROLE_ADMIN', 'ROLE_UPDATE_ADDR','ROLE_UPDATE')")
	public ResponseEntity<WrapperResourceUpdate> updateAddress(@RequestBody ModelAddress address, @PathVariable String gin) throws ServiceException, SystemException {

		boolean success = postalAddressService.update(address, gin);
		
		WrapperResourceUpdate wrapper = new WrapperResourceUpdate();
		wrapper.success = success;
		wrapper.id = address.getIdentifiant();

		if (wrapper.success) {
			LOGGER.info("Address has been updated successfully!");
			return new ResponseEntity<>(wrapper, HttpStatus.OK);
		} else {
			LOGGER.error("Something went wrong (normalization process)!");
			return new ResponseEntity<>(wrapper, HttpStatus.OK);
		}
		
	}
	
	@RequestMapping(method = RequestMethod.DELETE, value = "/{gin}/address/{id}", produces = "application/json; charset=utf-8")
	@PreAuthorize(value = "hasAnyRole('ROLE_ADMIN', 'ROLE_DELETE_ADDR', 'ROLE_DELETE')")
	public ResponseEntity<?> deleteAddress(@PathVariable String gin, HttpServletRequest request) throws ServiceException, SystemException {
		
		String id = urlService.extractResourceId(request.getRequestURL().toString());
		
		boolean isSuccess = postalAddressService.delete(gin, id);
		
		if (isSuccess) {
			LOGGER.info("Address has been deleted successfully!");
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			LOGGER.error("Something went wrong!");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
	}

}
