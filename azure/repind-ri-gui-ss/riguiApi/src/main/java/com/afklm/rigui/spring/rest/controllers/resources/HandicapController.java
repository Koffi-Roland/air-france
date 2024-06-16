package com.afklm.rigui.spring.rest.controllers.resources;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.afklm.rigui.criteria.administrator.AdministratorToolsCriteria;
import com.afklm.rigui.services.ServiceException;
import com.afklm.rigui.services.resources.HandicapService;
import com.afklm.rigui.wrapper.individual.WrapperIndividualHandicapFromMS;
import com.afklm.rigui.wrapper.resources.WrapperResourceRead;

@RestController
@RequestMapping("/individual")
public class HandicapController {

	@Autowired
	private HandicapService handicapService;

	@RequestMapping(method = RequestMethod.GET, value = "/{gin}/handicaps", produces = "application/json; charset=utf-8")
	@PreAuthorize(value = "hasAnyRole('ROLE_ADMIN', 'ROLE_READ_HANDICAP')")
	public ResponseEntity<WrapperResourceRead> handicapsDetails(@PathVariable String gin,
			HttpServletRequest httpServletRequest) throws ServiceException {

		AdministratorToolsCriteria criteria = new AdministratorToolsCriteria(gin, httpServletRequest.getRemoteUser());
		WrapperIndividualHandicapFromMS responseFromMS = handicapService.getHandicapForIndividual(criteria);
		WrapperResourceRead response = new WrapperResourceRead();
		response.data = responseFromMS.handicaps;

		return new ResponseEntity<WrapperResourceRead>(response, HttpStatus.OK);
	}

}
