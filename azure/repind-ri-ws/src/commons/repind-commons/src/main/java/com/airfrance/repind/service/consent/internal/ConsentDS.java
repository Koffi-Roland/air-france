package com.airfrance.repind.service.consent.internal;

import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.repind.dao.consent.ConsentRepository;
import com.airfrance.repind.dto.consent.ConsentDTO;
import com.airfrance.repind.dto.consent.ConsentTransform;
import com.airfrance.repind.entity.consent.Consent;
import com.airfrance.repind.model.consent.ConsentDataModel;
import com.airfrance.repind.model.consent.ConsentDefaultModel;
import com.airfrance.repind.model.consent.ConsentModel;
import com.airfrance.repind.model.consent.ResponseConsentModel;
import com.airfrance.repind.util.service.RestTemplateExtended;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ConsentDS {
	
	private static final Log log = LogFactory.getLog(ConsentDS.class);
    
	@Autowired
    private ApplicationContext context;
	
	@Autowired
	private ConsentRepository consentRepository;
	
	@Transactional
    public ConsentDTO findById(Long id) throws JrafDomainException {
		Optional<Consent> consent = consentRepository.findById(id);
		
		if (!consent.isPresent()) return null;
		
    	return ConsentTransform.bo2DtoLight(consent.get());
    }
	
    /**
	 * REPIND-1962: Method for creating default Consents when a new Individual
	 * is created
	 * 
	 * @param gin
	 * @param application
	 * @throws JrafDomainException
	 */
    public void createDefaultConsents(String gin, String application) throws JrafDomainException {
    	
		try {
			postDefaultConsent(new ConsentDefaultModel(gin, application));
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new JrafDomainException("Error during creation of default consents for gin: " + gin);
		}
	}
    
    /**
	 * REPIND-1626 : Create the Model Consent before calling Micro Service
	 * 
	 * @param gin
	 * @param consentType
	 * @param consentDataType
	 * @param consentOptin
	 * @param signatureApplication
	 * @throws JrafDomainException
	 */
    public void createConsent(String gin, String consentType, String consentDataType, String consentOptin, String signatureApplication) throws JrafDomainException {
    	
    	List<ConsentDataModel> data = new ArrayList<ConsentDataModel>();
		ConsentDataModel consentDataModel = new ConsentDataModel();
		consentDataModel.setDateConsent(new Date());
		consentDataModel.setIsConsent(consentOptin);
		consentDataModel.setType(consentDataType);
		data.add(consentDataModel);
    	
    	ConsentModel consentModel = new ConsentModel();
		consentModel.setApplication(signatureApplication);
		consentModel.setGin(gin);
		consentModel.setType(consentType);
		consentModel.setData(data);
		
		try {
			postConsent(consentModel);
		} catch (Exception e) {
			throw new JrafDomainException(e.getMessage());
		}
    }

	/**
	 * Create a Consent via the MicroService using REST POST Method
	 * 
	 * @param consentModel
	 * @throws JrafDomainException
	 */
    private void postConsent(ConsentModel consentModel) throws JrafDomainException {
    	
		RestTemplateExtended manageConsent = (RestTemplateExtended) context.getBean("manageConsent");
		String apiKey = manageConsent.getApiKey();
    	try {
    		
    		//We check if API_SECRET and API_KEY are set, if not we do not call the micro service, that will be the case for RC2
    		if (StringUtils.isNotEmpty(manageConsent.getApiKey()) && StringUtils.isNotEmpty(manageConsent.getApiSecret())) {

				//Create Headers for request
				HttpHeaders headers = this.createHeadersConsent(apiKey);

        		//Add Params for Mashery Calls
				String requestParams = "/?api_key=" + apiKey + "&sig="
						+ getSig(apiKey, manageConsent.getApiSecret());
        		
				HttpEntity<ConsentModel> request = new HttpEntity<ConsentModel>(consentModel, headers);
				ResponseEntity<ResponseConsentModel> responseConsent = manageConsent.postForEntity(
						manageConsent.getUriTemplateHandler().expand(requestParams), request,
						ResponseConsentModel.class);

        		//If StatusCode different than OK, we throw an exception saying that an error occured
        		if (responseConsent != null && !HttpStatus.OK.equals(responseConsent.getStatusCode())) {
            		log.error("Error on gin " + consentModel.getGin() + " for consent " + consentModel.getType() + ": " + responseConsent.getStatusCodeValue());
            		throw new JrafDomainException("Error microservice consent, status code: " + responseConsent.getStatusCodeValue());
        		}
    		} else {
    			log.warn("Micro Service Consent seems to be disabled. API_KEY and API_SECRET are missing.");
    		}
    		
    	//We catch any error coming from the MicroService
    	} catch (Exception e) {
    		log.error("Error on gin " + consentModel.getGin() + " for consent " + consentModel.getType() + ": " + e.getMessage());
    		throw new JrafDomainException(e);
    	}
	}
    
    /**
	 * REPIND-1626: Call 1 time MS to create default with new ressource Create a
	 * Consent via the MicroService using REST POST Method
	 * 
	 * @param consentDefaultModel
	 * @throws JrafDomainException
	 */
	private void postDefaultConsent(ConsentDefaultModel consentDefaultModel) throws JrafDomainException {

		RestTemplateExtended manageConsent = (RestTemplateExtended) context.getBean("manageConsent");
		String apiKey = manageConsent.getApiKey();
		try {

			//We check if API_SECRET and API_KEY are set, if not we do not call the micro service, that will be the case for RC2
			if (StringUtils.isNotEmpty(manageConsent.getApiKey())
					&& StringUtils.isNotEmpty(manageConsent.getApiSecret())) {

				//Create Headers for request
				HttpHeaders headers = this.createHeadersConsent(apiKey);

				//Add Params for Mashery Calls
				String requestParams = "/default/?api_key=" + apiKey + "&sig="
						+ getSig(apiKey, manageConsent.getApiSecret());

				HttpEntity<ConsentDefaultModel> request = new HttpEntity<ConsentDefaultModel>(consentDefaultModel,
						headers);
				ResponseEntity<ResponseConsentModel> responseConsent = manageConsent.postForEntity(
						manageConsent.getUriTemplateHandler().expand(requestParams), request,
						ResponseConsentModel.class);

				//If StatusCode different than OK, we throw an exception saying that an error occured
				if (responseConsent != null && !HttpStatus.OK.equals(responseConsent.getStatusCode())) {
					log.error("Error on gin " + consentDefaultModel.getGin() + " for consent create default consent");
					throw new JrafDomainException(
							"Error microservice consent, status code: " + responseConsent.getStatusCodeValue());
				}
			} else {
				log.warn("Micro Service Consent seems to be disabled. API_KEY and API_SECRET are missing.");
			}

			//We catch any error coming from the MicroService
		} catch (Exception e) {
			log.error("Critical unknowed error on gin " + consentDefaultModel.getGin()
					+ " for consent create default consent");
			throw new JrafDomainException(e);
		}
	}

	private HttpHeaders createHeadersConsent(String apiKey) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		List<MediaType> accept = new ArrayList<MediaType>();
		accept.add(MediaType.APPLICATION_JSON);
		headers.setAccept(accept);
		headers.add("api_key", apiKey);
		return headers;
	}

	/**
	 * For Mashery call, a sig request param must be provided
	 * 
	 * @return String
	 */
    private String getSig(String apiKey, String apiSecret) {
        return DigestUtils.sha256Hex(apiKey + apiSecret + System.currentTimeMillis() / 1000);
    }


	public boolean ginHasNoConsents(String gin){
		return consentRepository.findByGin(gin).isEmpty() || consentRepository.findByGin(gin).size() == 0;
	}
}
