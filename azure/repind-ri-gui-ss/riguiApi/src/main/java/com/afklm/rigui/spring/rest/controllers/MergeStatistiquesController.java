package com.afklm.rigui.spring.rest.controllers;

import java.io.InputStream;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.afklm.rigui.criteria.merge.MergeStatistiquesCriteria;
import com.afklm.rigui.services.MergeStatistiquesService;
import com.afklm.rigui.services.helper.MergeStatistiquesHelper;
import com.afklm.rigui.wrapper.searches.WrapperIndividualSearch;
import com.afklm.rigui.exception.jraf.JrafDomainException;

@RestController
@RequestMapping("/merge/statistiques")
public class MergeStatistiquesController {

	private static final Logger LOGGER = LoggerFactory.getLogger(MergeStatistiquesController.class);

	@Autowired
	private MergeStatistiquesService mergeStatistiquesService;

	@Autowired
	private MergeStatistiquesHelper mergeStatistiquesHelper;

	/**
	 * @return individu matching to the parameter
	 */

	@RequestMapping(method = RequestMethod.GET, value = "", produces = "application/json; charset=utf-8")
	@PreAuthorize(value = "hasAnyRole('ROLE_ADMIN', 'ROLE_MERGE_FB', 'ROLE_MERGE_GP')")
	public WrapperIndividualSearch getMergeStatistiques() {
		LOGGER.info("Ask for MergeStatistiques - Begin");
		PageRequest pageable = PageRequest.of(0, 500, Direction.DESC, "dateModification");
		MergeStatistiquesCriteria criteria = new MergeStatistiquesCriteria(MergeStatistiquesCriteria.ONE_WEEK_BEFORE);
		WrapperIndividualSearch result = mergeStatistiquesService.getMergeStatistiques(criteria, pageable);
		LOGGER.info("Ask for MergeStatistiques - End");
		return result;
	}

	/**
	 * @return individu matching to the parameter
	 * @throws JrafDomainException
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/export", produces = MediaType.ALL_VALUE)
	@PreAuthorize(value = "hasAnyRole('ROLE_ADMIN', 'ROLE_MERGE_FB', 'ROLE_MERGE_GP')")
	public ResponseEntity<InputStreamResource> getTrackingExportByGin() throws JrafDomainException {

		LOGGER.info("Ask for MergeStatistiques export - Begin");
		List<Object[]> result = mergeStatistiquesService.getMergeStatistiquesExported();

		InputStream stream = mergeStatistiquesHelper.generateCSV(result);
		if (stream == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		LOGGER.info("Ask for MergeStatistiques export - End");
		final HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "text/csv");
		headers.add("Content-Disposition", "attachment; filename=ExportMergeStatistiques-" + new Date() + ".csv");
		return new ResponseEntity<>(new InputStreamResource(stream), headers, HttpStatus.OK);
	}
}
