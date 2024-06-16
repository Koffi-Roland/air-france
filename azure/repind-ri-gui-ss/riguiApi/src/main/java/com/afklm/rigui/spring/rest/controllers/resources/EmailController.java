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

import com.afklm.rigui.exception.jraf.JrafDomainException;
import com.afklm.rigui.model.individual.ModelEmail;
import com.afklm.rigui.services.IndividualService;
import com.afklm.rigui.services.ServiceException;
import com.afklm.rigui.services.UrlService;
import com.afklm.rigui.services.resources.EmailService;
import com.afklm.rigui.wrapper.resources.WrapperResourceRead;
import com.afklm.rigui.wrapper.resources.WrapperResourceUpdate;
import com.afklm.soa.stubs.common.systemfault.v1.SystemException;

@RestController
@RequestMapping("/individual")
public class EmailController {

	private static final Logger LOGGER = LoggerFactory.getLogger(EmailController.class);
	
	@Autowired
	public EmailService emailService;
	
	@Autowired
	public IndividualService individualService;
	
	@Autowired
	private UrlService urlService;
	
	@RequestMapping(method = RequestMethod.POST, value = "/{gin}/mail", produces = "application/json; charset=utf-8")
	@PreAuthorize(value = "hasAnyRole('ROLE_ADMIN', 'ROLE_CREATE_EMAILS','ROLE_CREATE')")
	public ResponseEntity<?> create(@RequestBody ModelEmail email, @PathVariable String gin) throws ServiceException, SystemException {
		
		boolean success = emailService.create(email, gin);
		
		if (success) {
			LOGGER.info("Email has been created successfully!");
			return new ResponseEntity<>(success, HttpStatus.OK);
		} else {
			LOGGER.error("Something went wrong!");
			return new ResponseEntity<>(success, HttpStatus.BAD_REQUEST);
		}
		
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/{gin}/mails", produces = "application/json; charset=utf-8")
	@PreAuthorize(value = "hasAnyRole('ROLE_ADMIN','ROLE_READ_EMAILS', 'ROLE_READ')")
	public ResponseEntity<WrapperResourceRead> getAll(@PathVariable String gin) throws ServiceException, JrafDomainException {

		List<ModelEmail> emails = emailService.getAll(gin);
		
		WrapperResourceRead wrapper = new WrapperResourceRead();
		wrapper.data = emails;

		return new ResponseEntity<>(wrapper, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.PUT, value = "/{gin}/mail", produces = "application/json; charset=utf-8")
	@PreAuthorize(value = "hasAnyRole('ROLE_ADMIN', 'ROLE_UPDATE_EMAILS','ROLE_UPDATE')")
	public ResponseEntity<WrapperResourceUpdate> update(@RequestBody ModelEmail email, @PathVariable String gin) throws SystemException, ServiceException {
		
		boolean success = emailService.update(email, gin);
		
		WrapperResourceUpdate wrapper = new WrapperResourceUpdate();
		wrapper.success = success;
		wrapper.id = email.getIdentifiant();
		
		if (wrapper.success) {
			LOGGER.info("Update of email completed successfully!");
			return new ResponseEntity<>(wrapper, HttpStatus.OK);
		} else {
			LOGGER.error("Something went wrong!");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
	}
	
	@RequestMapping(method = RequestMethod.DELETE, value = "/{gin}/mail/{id}", produces = "application/json; charset=utf-8")
	@PreAuthorize(value = "hasAnyRole('ROLE_ADMIN', 'ROLE_DELETE_EMAILS', 'ROLE_DELETE')")
	public ResponseEntity<?> delete(@PathVariable String gin, HttpServletRequest request) throws ServiceException, SystemException {
		
		String id = urlService.extractResourceId(request.getRequestURL().toString());
		
		boolean success = emailService.delete(gin, id);
		
		if (success) {
			LOGGER.info("The email with ID " + id + " has been successfully deleted!");
		} else {
			LOGGER.error("The email with ID " + id + " has not been successfully deleted!");
		}
		
		return new ResponseEntity<>((success) ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
		
	}
	
}
