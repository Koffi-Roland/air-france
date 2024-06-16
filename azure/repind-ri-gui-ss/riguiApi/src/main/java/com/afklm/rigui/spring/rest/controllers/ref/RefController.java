package com.afklm.rigui.spring.rest.controllers.ref;

import java.util.List;

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

import com.afklm.rigui.services.ref.RefService;
import com.afklm.rigui.wrapper.ref.WrapperRefTable;
import com.airfrance.reef.reftable.RefTableChamp;

@RestController
@RequestMapping("/ref")
public class RefController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(RefController.class);
	
	@Autowired
	private RefService refService;
	
	@RequestMapping(method = RequestMethod.GET, value = "/civilities", produces = "application/json; charset=utf-8")
	@PreAuthorize(value = "hasAnyRole('ROLE_ADMIN','ROLE_READ')")
	public ResponseEntity<?> getRefCivilities() {
		LOGGER.info("API endpoint: fetch references for civilities!");
		List<RefTableChamp> fields = refService.findRefCivilities();
		return new ResponseEntity<>(fields, HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/titles", produces = "application/json; charset=utf-8")
	@PreAuthorize(value = "hasAnyRole('ROLE_ADMIN','ROLE_READ')")
	public ResponseEntity<?> getRefTitles() {
		LOGGER.info("API endpoint: fetch references for titles!");
		List<RefTableChamp> fields = refService.findRefTitles();
		return new ResponseEntity<>(fields, HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/genders", produces = "application/json; charset=utf-8")
	@PreAuthorize(value = "hasAnyRole('ROLE_ADMIN','ROLE_READ')")
	public ResponseEntity<?> getRefGenders() {
		LOGGER.info("API endpoint: fetch references for genders!");
		List<RefTableChamp> fields = refService.findRefGenders();
		return new ResponseEntity<>(fields, HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/individual/status", produces = "application/json; charset=utf-8")
	@PreAuthorize(value = "hasAnyRole('ROLE_ADMIN','ROLE_READ')")
	public ResponseEntity<?> getRefStatus() {
		LOGGER.info("API endpoint: fetch references for status!");
		List<RefTableChamp> fields = refService.findRefStatus();
		return new ResponseEntity<>(fields, HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/nat", produces = "application/json; charset=utf-8")
	@PreAuthorize(value = "hasAnyRole('ROLE_ADMIN','ROLE_READ')")
	public ResponseEntity<?> getRefNat() {
		LOGGER.info("API endpoint: fetch references for nat!");
		List<RefTableChamp> fields = refService.findRefNat();
		return new ResponseEntity<>(fields, HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/branches", produces = "application/json; charset=utf-8")
	@PreAuthorize(value = "hasAnyRole('ROLE_ADMIN','ROLE_READ')")
	public ResponseEntity<?> getRefBranches() {
		LOGGER.info("API endpoint: fetch references for branches!");
		List<RefTableChamp> fields = refService.findRefBranches();
		return new ResponseEntity<>(fields, HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/languages", produces = "application/json; charset=utf-8")
	@PreAuthorize(value = "hasAnyRole('ROLE_ADMIN','ROLE_READ')")
	public ResponseEntity<?> getRefLanguages() {
		LOGGER.info("API endpoint: fetch references for languages!");
		List<RefTableChamp> fields = refService.findRefLanguages();
		return new ResponseEntity<>(fields, HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/countries", produces = "application/json; charset=utf-8")
	@PreAuthorize(value = "hasAnyRole('ROLE_ADMIN','ROLE_READ')")
	public ResponseEntity<?> getRefCountryCodes() {
		LOGGER.info("API endpoint: fetch references for country codes!");
		List<RefTableChamp> fields = refService.findRefCountryCodes();
		return new ResponseEntity<>(fields, HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/fbtierlevels", produces = "application/json; charset=utf-8")
	@PreAuthorize(value = "hasAnyRole('ROLE_ADMIN','ROLE_READ')")
	public ResponseEntity<?> getRefFBTierLevels() {
		LOGGER.info("API endpoint: fetch references for FB tier levels!");
		List<RefTableChamp> fields = refService.findRefFBTierLevels();
		return new ResponseEntity<>(fields, HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/statesRoleContract", produces = "application/json; charset=utf-8")
	@PreAuthorize(value = "hasAnyRole('ROLE_ADMIN','ROLE_READ')")
	public ResponseEntity<?> getRefStatesRoleContract() {
		LOGGER.info("API endpoint: fetch references for states of role contract!");
		List<RefTableChamp> fields = refService.findRefStatesRoleContract();
		return new ResponseEntity<>(fields, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/table/{table}", produces = "application/json; charset=utf-8")
	@PreAuthorize(value = "hasAnyRole('ROLE_ADMIN','ROLE_READ')")
	public ResponseEntity<?> getRefStatesRoleContract(@PathVariable("table") String table) {

		WrapperRefTable fields = refService.findRefTable(table);
		return new ResponseEntity<>(fields, HttpStatus.OK);
	}

}
