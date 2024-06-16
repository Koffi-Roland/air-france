package com.afklm.rigui.spring.rest.controllers.resources;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.afklm.rigui.criteria.administrator.AdministratorToolsCriteria;
import com.afklm.rigui.model.individual.requests.ModelConsentRequest;
import com.afklm.rigui.services.ServiceException;
import com.afklm.rigui.services.resources.ConsentService;
import com.afklm.rigui.wrapper.individual.WrapperIndividualConsentFromMS;
import com.afklm.rigui.wrapper.resources.WrapperResourceRead;
import com.afklm.rigui.wrapper.resources.WrapperResourceUpdate;

@RestController
@RequestMapping("/individual")
public class ConsentController {

	@Autowired
	private ConsentService consentService;

	@RequestMapping(method = RequestMethod.GET, value = "/{gin}/consents", produces = "application/json; charset=utf-8")
	@PreAuthorize(value = "hasAnyRole('ROLE_ADMIN', 'ROLE_READ_CONSENT')")
	public ResponseEntity<WrapperResourceRead> consentDetails(@PathVariable String gin,
			HttpServletRequest httpServletRequest) throws ServiceException {

		AdministratorToolsCriteria criteria = new AdministratorToolsCriteria(gin, httpServletRequest.getRemoteUser());
		WrapperIndividualConsentFromMS responseFromMS = consentService.getConsentForIndividual(criteria);
		WrapperResourceRead response = new WrapperResourceRead();
		response.data = responseFromMS.consent;

		return new ResponseEntity<WrapperResourceRead>(response, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.PUT, value = "/{gin}/consents", produces = "application/json; charset=utf-8")
	@PreAuthorize(value = "hasAnyRole('ROLE_ADMIN', 'ROLE_UPDATE')")
	public ResponseEntity<WrapperResourceUpdate> consentDetails(@PathVariable String gin,
					@RequestBody ModelConsentRequest consent)
					throws ServiceException {
		consent.setGin(gin);
		consent.setDateConsent();
		WrapperIndividualConsentFromMS responseFromMS = consentService.update(consent);
		WrapperResourceUpdate response = new WrapperResourceUpdate();
		response.id = responseFromMS.gin;
		response.success = consent != null;

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

}
