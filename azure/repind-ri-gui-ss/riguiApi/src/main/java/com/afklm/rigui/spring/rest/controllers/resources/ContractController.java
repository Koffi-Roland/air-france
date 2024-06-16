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

import com.afklm.rigui.model.individual.ModelContract;
import com.afklm.rigui.services.ServiceException;
import com.afklm.rigui.services.resources.ContractService;
import com.afklm.rigui.wrapper.resources.WrapperResourceRead;

@RestController
@RequestMapping("/individual")
public class ContractController {
	
	@Autowired
	private ContractService contractService;

	
	@RequestMapping(method = RequestMethod.GET, value = "/{gin}/contracts", produces = "application/json; charset=utf-8")
	@PreAuthorize(value = "hasAnyRole('ROLE_ADMIN','ROLE_READ_CONTRACTS','ROLE_READ')")
	public ResponseEntity<WrapperResourceRead> getContractsDetails(@PathVariable String gin) throws ServiceException {

		List<ModelContract> contracts = contractService.getAll(gin);
		
		WrapperResourceRead wrapper = new WrapperResourceRead();
		wrapper.data = contracts;
		
		return new ResponseEntity<>(wrapper, HttpStatus.OK);
		
	}

}
