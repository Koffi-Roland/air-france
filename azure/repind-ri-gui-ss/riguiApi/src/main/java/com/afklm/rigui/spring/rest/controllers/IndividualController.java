package com.afklm.rigui.spring.rest.controllers;

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
import com.afklm.rigui.model.individual.requests.ModelIndividualRequest;
import com.afklm.rigui.services.IndividualService;
import com.afklm.rigui.services.ServiceException;
import com.afklm.rigui.wrapper.individual.WrapperIndividual;
import com.afklm.rigui.wrapper.individual.WrapperIndividualResult;
import com.afklm.soa.stubs.common.systemfault.v1.SystemException;
/**
 * @author AF-KLM
 *
 */

@RestController // IN CASE YOU WONDER THE APPLICATION START THE SERVER THANKS TO THE FILE IN WEB-INF/web.xml. It was so obvious... I love Spring
@RequestMapping("/individual")
public class IndividualController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(IndividualController.class);
	
	@Autowired
	private IndividualService individualService;
	
	@RequestMapping(method = RequestMethod.PUT, produces = "application/json; charset=utf-8")
	@PreAuthorize(value = "hasAnyRole('ROLE_ADMIN','ROLE_UPDATE', 'ROLE_UPDATE_INDIVIDUAL')")
	public ResponseEntity<?> updateIndividual(@RequestBody ModelIndividualRequest individualRequest) throws SystemException, ServiceException {
		LOGGER.info("API endpoint: update individual data");
		boolean isSuccess = individualService.updateIndividual(individualRequest);
		if (isSuccess) {
			LOGGER.info("Update of individual completed successfully!");
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			LOGGER.error("Something went wrong!");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	
	@RequestMapping(method = RequestMethod.GET, value = "/{identifiant}/details", produces = "application/json; charset=utf-8")
	@PreAuthorize(value = "hasAnyRole('ROLE_ADMIN','ROLE_READ')")
	public ResponseEntity<WrapperIndividual> getIndividualDetails(@PathVariable("identifiant") String identifiant,
			HttpServletRequest httpServletRequest) throws ServiceException, JrafDomainException {
		
		WrapperIndividual response = individualService.getIndividualDetailsByIdentifiant(identifiant);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	/**
	 * @param httpServletRequest
	 *            the Http servlet request
	 * @return Integer to know if an individual gin is the same as a contract number
	 * @throws ServiceException 
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/existMultiple/{gin}", produces = "application/json; charset=utf-8")
	@PreAuthorize(value = "hasAnyRole('ROLE_ADMIN','ROLE_READ')")
	public ResponseEntity<Integer> isContractAndIndividualHaveSameNumber(@PathVariable("gin") String gin,
            HttpServletRequest httpServletRequest) throws ServiceException {

        Integer response = individualService.isContractAndIndividualHaveSameNumber(gin);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

    /**
     * @param httpServletRequest
     *            the Http servlet request
     * @return Integer to know if an individual gin is the same as a contract number
     * @throws ServiceException 
     * @throws  
     */
    @RequestMapping(method = RequestMethod.GET, value = "/multipleByGinOrContract/{identifiant}", produces = "application/json; charset=utf-8")
    @PreAuthorize(value = "hasAnyRole('ROLE_ADMIN','ROLE_READ')")
    public ResponseEntity<WrapperIndividualResult> individualsList(@PathVariable("identifiant") String identifiant,
                               HttpServletRequest httpServletRequest) throws ServiceException {

    	WrapperIndividualResult response = individualService.getListIndividualResult(identifiant);

		return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
}
