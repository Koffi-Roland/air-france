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

import com.afklm.rigui.model.individual.ModelRoleUCCR;
import com.afklm.rigui.services.ServiceException;
import com.afklm.rigui.services.resources.UccrRoleService;
import com.afklm.rigui.wrapper.resources.WrapperResourceRead;

@RestController
@RequestMapping("/individual")
public class UccrRoleController {
	
	@Autowired
	private UccrRoleService uccrRoleService;
	
	@RequestMapping(method = RequestMethod.GET, value = "/{gin}/rolesUCCR", produces = "application/json; charset=utf-8")
	@PreAuthorize(value = "hasAnyRole('ROLE_ADMIN','ROLE_READ_UCCROLES','ROLE_READ')")
	public ResponseEntity<WrapperResourceRead> getRolesUCCRDetails(@PathVariable String gin) throws ServiceException {

		List<ModelRoleUCCR> modelRoleUCCRs = uccrRoleService.getAll(gin);
		
		WrapperResourceRead wrapper = new WrapperResourceRead();
		wrapper.data = modelRoleUCCRs;

		return new ResponseEntity<>(wrapper, HttpStatus.OK);
		
	}

}
