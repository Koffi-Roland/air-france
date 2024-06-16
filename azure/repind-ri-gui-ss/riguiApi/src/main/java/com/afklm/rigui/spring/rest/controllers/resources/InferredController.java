package com.afklm.rigui.spring.rest.controllers.resources;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.afklm.rigui.services.ServiceException;
import com.afklm.rigui.services.resources.InferredService;
import com.afklm.rigui.wrapper.individual.WrapperIndividualInferred;

@RestController
@RequestMapping("/individual")
public class InferredController {
	
	@Autowired
	private InferredService inferredService;
	
	@RequestMapping(method = RequestMethod.GET, value = "/{gin}/inferreds", produces = "application/json; charset=utf-8")
	@PreAuthorize(value = "hasAnyRole('ROLE_ADMIN', 'ROLE_READ_INFERED')")
	public ResponseEntity<WrapperIndividualInferred> inferredsDetails(@PathVariable String gin, HttpServletRequest httpServletRequest) throws ServiceException {

		//WrapperIndividualInferred response = inferredService.getInferredForIndividual(gin);

		//return new ResponseEntity<>(response, HttpStatus.OK);
		return null;
	}

}
