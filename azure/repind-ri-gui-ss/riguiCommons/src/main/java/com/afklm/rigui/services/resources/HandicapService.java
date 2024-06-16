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
import com.afklm.rigui.services.ServiceException;
import com.afklm.rigui.wrapper.individual.WrapperIndividualHandicapFromMS;
import com.afklm.rigui.util.service.RestTemplateExtended;

@Service
public class HandicapService {

	private static final Logger LOGGER = LoggerFactory.getLogger(HandicapService.class);

	@Autowired
	@Qualifier("manageHandicap")
	private RestTemplateExtended manageHandicap;

	@Transactional
	public WrapperIndividualHandicapFromMS getHandicapForIndividual(AdministratorToolsCriteria criteria)
			throws ServiceException {

		ResponseEntity<WrapperIndividualHandicapFromMS> responseConsent = null;

		LOGGER.warn("Call HDC from MS by: " + criteria.getMatricule());
		try {
			//Create Headers for request
			HttpHeaders headers = manageHandicap.createHeaders();

			responseConsent = manageHandicap.exchange(
					"?api_key=" + manageHandicap.getApiKey() + "&sig=" + manageHandicap.getSig() + "&gin="
							+ criteria.getGin(),
					HttpMethod.GET, new HttpEntity<Object>(headers), WrapperIndividualHandicapFromMS.class);

		} catch (HttpClientErrorException e) {
			if (e.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
				LOGGER.info("HDC not found for the individual");
				throw new ServiceException(BusinessErrorList.API_BUSINESS_NO_DATA_FOUND.getError(),
						HttpStatus.NOT_FOUND);
			} else if (e.getStatusCode().equals(HttpStatus.FORBIDDEN)) {
				LOGGER.info("Can't access to MS HDC");
				throw new ServiceException(BusinessErrorList.API_FORBIDDEN_MS_ACCESS.getError(), HttpStatus.FORBIDDEN);
			} else {
				LOGGER.info("ERROR: When calling MS manageHandicap: " + e.getLocalizedMessage() + ", "
						+ e.getResponseBodyAsString());
				throw new ServiceException(BusinessErrorList.API_MS_TECHNICAL_ERROR.getError(),
						HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		LOGGER.info("MS respond with HDC");
		return responseConsent.getBody();
	}

}
