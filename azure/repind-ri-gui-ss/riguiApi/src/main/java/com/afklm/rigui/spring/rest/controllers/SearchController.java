package com.afklm.rigui.spring.rest.controllers;

import com.afklm.rigui.model.individual.requests.*;
import com.afklm.rigui.services.ServiceException;
import com.afklm.rigui.services.search.IndividualSearchService;
import com.afklm.rigui.wrapper.searches.WrapperIndividualSearch;
import com.afklm.soa.stubs.common.systemfault.v1.SystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;

@RestController
@RequestMapping("/search")
public class SearchController {
	
	@Autowired
	private IndividualSearchService individualSearchService;
	
	@RequestMapping(method = RequestMethod.POST, value = "/multicriteria", produces = "application/json; charset=utf-8")
	@PreAuthorize(value = "hasAnyRole('ROLE_READ')")
    public ResponseEntity<WrapperIndividualSearch> searchByMulticriteria(@RequestBody ModelSearchByMulticriteriaRequest searchRequest, @RequestParam(value="merge", required = false, defaultValue = "false") final boolean merge, HttpServletRequest request)
    		throws ServiceException, ParseException, SystemException {
		
		WrapperIndividualSearch wrapper = individualSearchService.searchIndividualByMulticriteria(searchRequest,merge);
		return new ResponseEntity<>(wrapper, HttpStatus.OK);
		
    }
	
	@RequestMapping(method = RequestMethod.POST, value = "/email", produces = "application/json; charset=utf-8")
	@PreAuthorize(value = "hasAnyRole('ROLE_READ')")
	public ResponseEntity<WrapperIndividualSearch> searchByEmail(@RequestBody ModelSearchByEmailRequest searchRequest, @RequestParam(value="merge", required = false, defaultValue = "false") final boolean merge, HttpServletRequest request) throws ServiceException, SystemException {
		WrapperIndividualSearch wrapper = individualSearchService.searchIndividualByEmail(searchRequest,merge);
		return new ResponseEntity<>(wrapper, HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/telecom", produces = "application/json; charset=utf-8")
	@PreAuthorize(value = "hasAnyRole('ROLE_READ')")
	public ResponseEntity<WrapperIndividualSearch> searchByTelecom(@RequestBody ModelSearchByTelecomRequest searchRequest, @RequestParam(value="merge", required = false, defaultValue = "false") final boolean merge, HttpServletRequest request) throws ServiceException, SystemException {
		WrapperIndividualSearch wrapper = individualSearchService.searchIndividualByTelecom(searchRequest,merge);
		return new ResponseEntity<>(wrapper, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/social", produces = "application/json; charset=utf-8")
	@PreAuthorize(value = "hasAnyRole('ROLE_READ')")
	public ResponseEntity<WrapperIndividualSearch> searchBySocialID(@RequestBody ModelSearchBySocialIDRequest searchRequest, @RequestParam(value="merge", required = false, defaultValue = "false") final boolean merge, HttpServletRequest request) throws ServiceException, SystemException {
		WrapperIndividualSearch wrapper = individualSearchService.searchIndividualBySocialID(searchRequest,merge);
		return new ResponseEntity<>(wrapper, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/account", produces = "application/json; charset=utf-8")
	@PreAuthorize(value = "hasAnyRole('ROLE_READ')")
	public ResponseEntity<WrapperIndividualSearch> searchByAccount(@RequestBody ModelSearchByAccountRequest searchRequest, HttpServletRequest request) throws ServiceException, SystemException {
		WrapperIndividualSearch wrapper = individualSearchService.searchIndividualByAccountIdentifier(searchRequest);
		return new ResponseEntity<>(wrapper, HttpStatus.OK);
	}

}
