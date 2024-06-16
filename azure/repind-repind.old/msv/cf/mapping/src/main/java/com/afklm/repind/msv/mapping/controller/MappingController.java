package com.afklm.repind.msv.mapping.controller;

import com.afklm.repind.msv.mapping.criteria.MappingLanguageCriteria;
import com.afklm.repind.msv.mapping.criteria.MappingTableForContextCriteria;
import com.afklm.repind.msv.mapping.model.MappingLanguageModel;
import com.afklm.repind.msv.mapping.services.MappingLanguageService;
import com.afklm.repind.msv.mapping.services.exception.ServiceException;
import com.afklm.repind.msv.mapping.wrapper.WrapperMappingTableForContext;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@Slf4j
public class MappingController {
	
	@Autowired
	MappingLanguageService mappingLanguageService;

	@ApiResponses(value = { @ApiResponse(responseCode = "400", description = "Bad request"),
			@ApiResponse(responseCode = "404", description = "404.002 : No Language mapping found for this context, market and language!"),
			@ApiResponse(responseCode = "412", description = "412.002 : Context should be alphanumeric between 1 and 10 char! \n 412.003 : NoneIsoLanguage or IsoLanguage should be alphanumeric between 1 and 5 char! \n 412.004 : Market should be alphanumeric between 1 and 5 char!") })
	@Operation(summary = "Map the none ISO Language used by an application to an ISO Language used by AF Referential"/*, notes = "Provide the ISO 639-1 language code associated to a none ISO language code used by application", response = MappingLanguageModel.class*/)
	@GetMapping(value = "/language/provideMappingLanguageFromNoneISOLanguage/{market}/{noneIsoLanguage}/{context}", produces = "application/json; charset=utf-8")
	public ResponseEntity<MappingLanguageModel> provideMappingFromNoneISOLanguage(
			@Parameter(required = true, name = "market", description = "Code Market") @PathVariable final String market,
			@Parameter(required = true, name = "noneIsoLanguage", description = "None ISO Language used by context application") @PathVariable final String noneIsoLanguage,
			@Parameter(required = true, name = "context", description = "Code Context") @PathVariable final String context
			) 
					throws ServiceException {
		
		log.info("MAP None ISO Language :" + noneIsoLanguage + " for market : " + market + " and context : " + context);

		final MappingLanguageCriteria mappingLanguageCriteria = new MappingLanguageCriteria(market, noneIsoLanguage,
				context, false);

		return new ResponseEntity<>(mappingLanguageService.provideMappingFromLanguage(mappingLanguageCriteria),
				HttpStatus.OK);
	}
	
	@ApiResponses(value = { @ApiResponse(responseCode = "400", description = "Bad request"),
			@ApiResponse(responseCode = "404", description = "404.002 : No Language mapping found for this context, market and language!"),
			@ApiResponse(responseCode = "412", description = "412.002 : Context should be alphanumeric between 1 and 10 char! \n 412.003 : NoneIsoLanguage or IsoLanguage should be alphanumeric between 1 and 5 char! \n 412.004 : Market should be alphanumeric between 1 and 5 char!") })
	@Operation(summary = "Map the ISO Language used by AF Referential to a none ISO Language used by an application"/*, notes = "Provide the none ISO language code associated to the ISO 639-1 language code used by AF Referential", response = MappingLanguageModel.class*/)
	@GetMapping(value = "/language/provideMappingLanguageFromISOLanguage/{market}/{isoLanguage}/{context}", produces = "application/json; charset=utf-8")
	public ResponseEntity<MappingLanguageModel> provideMappingLanguageFromISOLanguage(
			@Parameter(required = true, name = "market", description = "Code Market") @PathVariable final String market,
			@Parameter(required = true, name = "isoLanguage", description = "ISO 639-1 language code used by AF Referential") @PathVariable final String isoLanguage,
			@Parameter(required = true, name = "context", description = "Code Context") @PathVariable final String context)
					throws ServiceException {
		
		log.info("MAP ISO Language :" + isoLanguage + " for market : " + market + " and context : " + context);

		final MappingLanguageCriteria mappingLanguageCriteria = new MappingLanguageCriteria(market, isoLanguage,
				context, true);

		return new ResponseEntity<>(mappingLanguageService.provideMappingFromLanguage(mappingLanguageCriteria),
				HttpStatus.OK);
	}
	
	@ApiResponses(value = { @ApiResponse(responseCode = "400", description = "Bad request"),
			@ApiResponse(responseCode = "404", description = "404.001 : No Reference mapping found for this context!"),
			@ApiResponse(responseCode = "412", description = "412.001 : Context should be alphanumeric between 1 and 10 char or equals to ALLCONTEXTS!") })
	@Operation(summary = "Return all mapping language for a specific context"/*, notes = "Provide all mapping language associated to a context, each language is map to a AF ISO 639-1 language code.", response = WrapperMappingTableForContext.class, responseContainer = "List"*/)
	@GetMapping(value = "/language/{context}", produces = "application/json; charset=utf-8")
	public ResponseEntity<List<WrapperMappingTableForContext>> provideMappingLanguageTable(
			@Parameter(required = true, name = "context", description = "Context to get table mapping language associated, to get data for all context use : ALLCONTEXTS") @PathVariable final String context)
			throws ServiceException {

		log.info("Get table mapping : " + context);

		final MappingTableForContextCriteria mappingTableForContextCriteria = new MappingTableForContextCriteria(
				context);

		return new ResponseEntity<>(
				mappingLanguageService.provideMappingTableByContext(mappingTableForContextCriteria),
				HttpStatus.OK);
	}

}