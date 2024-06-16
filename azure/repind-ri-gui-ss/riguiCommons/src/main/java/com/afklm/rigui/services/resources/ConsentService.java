package com.afklm.rigui.services.resources;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;

import com.afklm.rigui.criteria.administrator.AdministratorToolsCriteria;
import com.afklm.rigui.model.error.BusinessErrorList;
import com.afklm.rigui.model.individual.requests.ModelConsentRequest;
import com.afklm.rigui.services.ServiceException;
import com.afklm.rigui.wrapper.individual.WrapperIndividualConsentFromMS;
import com.afklm.rigui.util.service.RestTemplateExtended;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Service
public class ConsentService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ConsentService.class);

	@Autowired
	@Qualifier("manageConsent")
	private RestTemplateExtended manageConsent;

	@Transactional
	public WrapperIndividualConsentFromMS getConsentForIndividual(AdministratorToolsCriteria criteria)
			throws ServiceException {

		ResponseEntity<WrapperIndividualConsentFromMS> responseConsent = null;

		LOGGER.warn("Call Consent from MS by: " + criteria.getMatricule());
		try {
			//Create Headers for request
			HttpHeaders headers = manageConsent.createHeaders();

			// Construct the URL using UriComponentsBuilder
			UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(criteria.getGin())
					.queryParam("api_key", manageConsent.getApiKey())
					.queryParam("sig", manageConsent.getSig());

			// Encode the URL to ensure it's safe and properly formatted
			URI uri = builder.build().encode().toUri();

			responseConsent = manageConsent.exchange(
					uri, HttpMethod.GET, new HttpEntity<Object>(headers), WrapperIndividualConsentFromMS.class);

			/*
			 * responseConsent = manageConsent.getForEntity("?api_key=" +
			 * manageConsent.getApiKey() + "&sig=" + manageConsent.getSig() +
			 * "&gin=" + criteria.getGin(),
			 * WrapperIndividualConsentFromMS.class);
			 */
		} catch (HttpClientErrorException e) {
			handleExceptionFromMS(e);
		}
		LOGGER.info("MS respond with Consent");
		return responseConsent.getBody();
	}

	private void handleExceptionFromMS(final HttpClientErrorException e) throws ServiceException {
		if (e.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
			LOGGER.info("Consent not found for the individual");
			throw new ServiceException(BusinessErrorList.API_BUSINESS_NO_DATA_FOUND.getError(),
					HttpStatus.NOT_FOUND);
		} else if (e.getStatusCode().equals(HttpStatus.FORBIDDEN)) {
			LOGGER.info("Can't access to MS Consent");
			throw new ServiceException(BusinessErrorList.API_FORBIDDEN_MS_ACCESS.getError(), HttpStatus.FORBIDDEN);
		} else {
			LOGGER.info("ERROR: When calling MS manageConsent: " + e.getLocalizedMessage() + ", "
					+ e.getResponseBodyAsString());
			throw new ServiceException(BusinessErrorList.API_MS_TECHNICAL_ERROR.getError(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public WrapperIndividualConsentFromMS update(ModelConsentRequest consent) throws ServiceException {
		ResponseEntity<WrapperIndividualConsentFromMS> responseConsent = null;

		try {
			HttpHeaders headers = manageConsent.createHeaders();

			HttpEntity<ModelConsentRequest> request = new HttpEntity<>(consent, headers);
			String resourceURL = "?api_key=" + manageConsent.getApiKey() + "&sig=" + manageConsent.getSig();

			responseConsent = manageConsent.exchange( resourceURL,
							HttpMethod.PUT, request,
							WrapperIndividualConsentFromMS.class);
		} catch (HttpClientErrorException e) {
			handleExceptionFromMS(e);
		}

		return responseConsent.getBody();

	}

}
