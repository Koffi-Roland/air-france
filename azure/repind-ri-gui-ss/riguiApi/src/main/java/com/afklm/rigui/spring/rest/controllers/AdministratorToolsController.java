package com.afklm.rigui.spring.rest.controllers;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.afklm.rigui.criteria.administrator.AdministratorToolsCriteria;
import com.afklm.rigui.services.AdministratorToolsService;
import com.afklm.rigui.services.ServiceException;
import com.afklm.soa.stubs.common.systemfault.v1.SystemException;
import com.afklm.rigui.exception.jraf.JrafDomainException;

/**
 * @author AF-KLM
 *
 */

@RestController
@RequestMapping("/administrator-tools")
public class AdministratorToolsController {
	
	@SuppressWarnings("unused")
	private static final Logger LOGGER = LoggerFactory.getLogger(AdministratorToolsController.class);
		
	@Autowired
	private AdministratorToolsService administratorToolsService;

	/**
	 * Method call to unmerge an individual. The only information required is
	 * USER ONLY ADMIN CAN CALL THIS METHOD
	 * 
	 * @param httpServletRequest
	 *            the Http servlet request
	 * @return individu matching to the parameter
	 * @throws JrafDomainException
	 * @throws JrafDaoException
	 * @throws Sic2CommonsException
	 */
	@RequestMapping(method = RequestMethod.PUT, value = "unmerge/{gin}", produces = "application/json; charset=utf-8")
	@PreAuthorize(value = "hasAnyRole('ROLE_ADMIN')")
	public ResponseEntity<?> unmergeGin(@PathVariable("gin") String sgin, HttpServletRequest httpServletRequest)
			throws SystemException, ServiceException {
		AdministratorToolsCriteria unmergeCriteria = new AdministratorToolsCriteria(sgin,
				httpServletRequest.getRemoteUser());
		administratorToolsService.unmergeIndividual(unmergeCriteria);
		LOGGER.info("Unmege successful for GIN " + sgin + " called by " + unmergeCriteria.getMatricule());
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	/**
	 * Method call to reactivate the account data of an individual. The only information required is the gin
	 * USER ONLY ADMIN CAN CALL THIS METHOD
	 * 
	 * @param httpServletRequest
	 *            the Http servlet request
	 * @return individu matching to the parameter
	 * @throws JrafDomainException
	 * @throws JrafDaoException
	 * @throws Sic2CommonsException
	 */
	@RequestMapping(method = RequestMethod.PUT, value = "reactivate/{gin}", produces = "application/json; charset=utf-8")
	@PreAuthorize(value = "hasAnyRole('ROLE_ADMIN')")
	public ResponseEntity<?> reactivateAccountGin(@PathVariable("gin") String sgin, HttpServletRequest httpServletRequest)
			throws SystemException, ServiceException {
		AdministratorToolsCriteria reactivateAccountCriteria = new AdministratorToolsCriteria(sgin,
				httpServletRequest.getRemoteUser());
		administratorToolsService.reactivateAccountGin(reactivateAccountCriteria);
		LOGGER.info("Reactivate Account successful for GIN " + sgin + " called by " + reactivateAccountCriteria.getMatricule());
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
