package com.afklm.rigui.spring.rest.controllers.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.afklm.rigui.model.individual.ModelExternalIdentifier;
import com.afklm.rigui.services.ServiceException;
import com.afklm.rigui.services.resources.ExternalIdService;
import com.afklm.rigui.wrapper.resources.WrapperResourceRead;

@RestController
@RequestMapping("/individual")
public class ExternalIdController {
	
	@Autowired
	private ExternalIdService externalIdService;

	@RequestMapping(method = RequestMethod.GET, value = "/{gin}/externalIdentifiers", produces = "application/json; charset=utf-8")
	@PreAuthorize(value = "hasAnyRole('ROLE_ADMIN','ROLE_READ_EXTID','ROLE_READ')")
	public ResponseEntity<WrapperResourceRead> getExternalIdDetails(@PathVariable String gin) throws ServiceException {
		
		List<ModelExternalIdentifier> modelExternalIdentifiers = externalIdService.getAll(gin);
		
		WrapperResourceRead wrapper = new WrapperResourceRead();
		wrapper.data = modelExternalIdentifiers;

		return new ResponseEntity<>(wrapper, HttpStatus.OK);
	}

}
