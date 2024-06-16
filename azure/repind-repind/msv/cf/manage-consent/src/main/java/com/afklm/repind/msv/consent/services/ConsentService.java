package com.afklm.repind.msv.consent.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.afklm.repind.msv.consent.criteria.ConsentCriteria;
import com.afklm.repind.msv.consent.entity.Consent;
import com.afklm.repind.msv.consent.entity.ConsentData;
import com.afklm.repind.msv.consent.entity.Individual;
import com.afklm.repind.msv.consent.entity.RefConsentDefault;
import com.afklm.repind.msv.consent.model.CreateConsentDataModel;
import com.afklm.repind.msv.consent.model.GetConsentModel;
import com.afklm.repind.msv.consent.model.error.BusinessErrorList;
import com.afklm.repind.msv.consent.repository.ConsentDataRepository;
import com.afklm.repind.msv.consent.repository.ConsentRepository;
import com.afklm.repind.msv.consent.repository.IndividualRepository;
import com.afklm.repind.msv.consent.repository.MoralPersonRepository;
import com.afklm.repind.msv.consent.repository.RefConsentDefaultRepository;
import com.afklm.repind.msv.consent.repository.RefConsentTypeDataTypeRepository;
import com.afklm.repind.msv.consent.services.exception.ServiceException;
import com.afklm.repind.msv.consent.wrapper.WrapperCreateConsentResponse;
import com.afklm.repind.msv.consent.wrapper.WrapperGetConsentResponse;

import com.afklm.repind.msv.consent.config.Config.BeanMapper;

@Component
@Slf4j
public class ConsentService {

	@Autowired
	private ConsentRepository consentRepository;
	
	@Autowired
	private ConsentDataRepository consentDataRepository;

	@Autowired
	private RefConsentTypeDataTypeRepository refConsentTypeDataTypeRepository;

	@Autowired
	private RefConsentDefaultRepository refConsentDefaultRepository;

	@Autowired
	private IndividualRepository individualRepository;

	@Autowired
	private MoralPersonRepository moralPersonRepository;
	
	@Autowired
	private BeanMapper mapper;

	private String repindMSV = "REPINDMSV";
	    
	/**
	 * 
	 * Check if the gin is valid in SIC database
	 * Check if the type is allowed
	 * 
	 * If it is ok, create Consent data and store it in database
	 *
	 * Return the gin of the individual updated and the time to process the storage	
	 *
	 * @return response
	 * @throws ServiceException 
	 */
	public ResponseEntity<WrapperCreateConsentResponse> createConsent(final ConsentCriteria consentCriteria) throws ServiceException {
		
		// Check if customer exists in SIC
		if(!isCustomerInSIC(consentCriteria.getGin())) {
			throw new ServiceException(BusinessErrorList.API_CUSTOMER_NOT_FOUND.getError(), HttpStatus.NOT_FOUND);
		}
		
		// Check if type is allowed
		List<String> consentDataTypeAllowed = checkIfTypeIsAllowed(consentCriteria.getType());
		
		final Date now = new Date();

		// Create consent object
		createConsentDataInDB(consentCriteria.getGin(), consentCriteria.getType(), consentCriteria.getApplication(),
				now, consentCriteria.getData(), consentDataTypeAllowed);
		
		// Build response (only gin and time of the query)
		final WrapperCreateConsentResponse wrapperConsentDataResponse = new WrapperCreateConsentResponse();
		wrapperConsentDataResponse.gin = consentCriteria.getGin();
		
		return new ResponseEntity<>(wrapperConsentDataResponse, HttpStatus.OK);
	}
	
	/**
	 * 
	 * Check if the gin is valid in SIC database Get all consent to create from
	 * DB
	 * 
	 * For each call, use creation method
	 *
	 * Return the gin of the individual updated and the time to process the
	 * storage
	 *
	 * @return response
	 * @throws ServiceException
	 */
	public ResponseEntity<WrapperCreateConsentResponse> createDefaultConsent(
			final ConsentCriteria consentCriteria) throws ServiceException {

		// Check if customer exists in SIC
		if (!isCustomerInSIC(consentCriteria.getGin())) {
			throw new ServiceException(BusinessErrorList.API_CUSTOMER_NOT_FOUND.getError(), HttpStatus.NOT_FOUND);
		}

		// Get all default consent to create
		final List<RefConsentDefault> defaultConsentToCreate = refConsentDefaultRepository.findAll();

		final Date now = new Date();

		//Loop to create all default consent
		for (RefConsentDefault consent : defaultConsentToCreate) {
			// Check if type is allowed
			List<String> consentDataTypeAllowed = checkIfTypeIsAllowed(
					consent.getRefConsentDefaultId().getConsentType());

			List<CreateConsentDataModel> createConsentData = new LinkedList<>();
			createConsentData.add(new CreateConsentDataModel(consent.getRefConsentDefaultId().getConsentDataType(),
					consent.getDefaultConsentValue(), now));

			// Create consent object
			createConsentDataInDB(consentCriteria.getGin(), consent.getRefConsentDefaultId().getConsentType(),
					consentCriteria.getApplication(), now, createConsentData, consentDataTypeAllowed);
		}

		// Build response (only gin and time of the query)
		final WrapperCreateConsentResponse wrapperConsentDataResponse = new WrapperCreateConsentResponse();
		wrapperConsentDataResponse.gin = consentCriteria.getGin();

		return new ResponseEntity<>(wrapperConsentDataResponse, HttpStatus.OK);
	}

	/**
	 * Private method to orchestrate the creation of consent and consent data
	 * 
	 * @param gin
	 * @param type
	 * @param application
	 * @param now
	 * @param datas
	 * @param consentDataTypeAllowed
	 * @return
	 * @throws ServiceException
	 */
	private void createConsentDataInDB(String gin, String type, String application, Date now,
			List<CreateConsentDataModel> datas, List<String> consentDataTypeAllowed) throws ServiceException {

		// Create consent object
		final Consent consent = new Consent();
		consent.setGin(gin);
		consent.setType(type);
		consent.setDateCreation(now);
		consent.setDateModification(now);
		consent.setSiteCreation(repindMSV);
		consent.setSiteModification(repindMSV);
		consent.setSignatureCreation(application);
		consent.setSignatureModification(application);

		// Check and build list of consent data
		try {
			consent.setConsentData(getConsentData(consent, datas, consentDataTypeAllowed, application, now));
			// Store consent data if all is fine
			consentRepository.saveAndFlush(consent);
			log.info("Create OK for CONSENT TYPE " + type);
		} catch (ServiceException e) {
			log.error(e.getMessage());
			throw e;
		}
	}

	/**
	 * 
	 * For each data, check if the type of consent data is allowed for the type
	 * If yes and if the value is not blank, add data to the consent Object
	 *  
	 * @return response
	 * @throws ServiceException 
	 */
	public List<ConsentData> getConsentData(Consent consent, List<CreateConsentDataModel> listConsentDataModel, List<String> consentDataTypeAllowed, String application, Date now) throws ServiceException {
		
		List<ConsentData> listConsentData = new ArrayList<>();

		List<String> consentDataTypeAdded = new ArrayList<>();
		
		for (CreateConsentDataModel consentDataModel : listConsentDataModel) {
			
			String typeDataConsent = consentDataModel.getType();
			String isConsent = consentDataModel.getIsConsent();
			Date dateConsent = consentDataModel.getDateConsent();
			
			// Check if the type of consent data or the value is blank
			if(!StringUtils.isBlank(typeDataConsent) && !StringUtils.isBlank(isConsent)) {
				
				typeDataConsent = StringUtils.upperCase(typeDataConsent);
				isConsent = StringUtils.upperCase(isConsent);

				// Check if consent is allowed
				checkIfConsentIsAllowedForCreation(isConsent);
				
				if(consentDataTypeAdded.contains(typeDataConsent)) {
					
					// If the type of consent data is already set, doublon !
					throw new ServiceException(BusinessErrorList.API_PRESENT_MORE_THAN_ONCE.getError().setDescription("Type : " + consentDataModel.getType()),
							HttpStatus.PRECONDITION_FAILED);
					
				}
				
				// Check if the type of consent data is allowed for this type
				if (consentDataTypeAllowed.contains(typeDataConsent)) {
					ConsentData consentData = new ConsentData();
					consentData.setDateConsent(dateConsent);
					consentData.setType(typeDataConsent);
					
					if("Y".equals(isConsent)) {
						// Set dateConsent to now if field dateConsent is not filled
						if(dateConsent != null) {
							consentData.setDateConsent(dateConsent);
						} else {
							consentData.setDateConsent(now);
						}
					}
					consentData.setIsConsent(isConsent);
					consentData.setDateCreation(now);
					consentData.setDateModification(now);
					consentData.setSiteCreation(repindMSV);
					consentData.setSiteModification(repindMSV);
					consentData.setSignatureCreation(application);
					consentData.setSignatureModification(application);
					consentData.setConsent(consent);
					
					consentDataTypeAdded.add(typeDataConsent);
					
					listConsentData.add(consentData);
					
				} else {
					// If the type of consent data is not allowed for this type, throw an exception
					throw new ServiceException(BusinessErrorList.API_TYPE_CONSENT_DATA_NOT_ALLOWED.getError().setDescription("Type : " + consentDataModel.getType()),
							HttpStatus.PRECONDITION_FAILED);
				}
				
			} else {
				// If the type of consent data, consent and the date of consent is blank, throw an exception Invalid Parameter
				throw new ServiceException(BusinessErrorList.API_INVALID_CONSENT_DATA.getError(),
						HttpStatus.PRECONDITION_FAILED);
			}
		}
		
		return listConsentData;
		
		
	}
	

	/**
	 * 
	 * Check if the gin is valid in SIC database
	 * Check if the type is allowed
	 * 
	 * If it is ok, create Consent data and store it in database
	 *
	 * Return the gin of the individual updated and the time to process the storage	
	 *
	 * @return response
	 * @throws ServiceException 
	 */
	public ResponseEntity<WrapperCreateConsentResponse> updateConsent(final ConsentCriteria consentCriteria) throws ServiceException {
		
		// Check if customer exists in SIC
		if(!isCustomerInSIC(consentCriteria.getGin())) {
			throw new ServiceException(BusinessErrorList.API_CUSTOMER_NOT_FOUND.getError(), HttpStatus.NOT_FOUND);
		}
		
		// Check if consent is allowed
		checkIfConsentIsAllowedForUpdate(consentCriteria.getIsConsent());
		
		final Date now = new Date();

		// Get consent
		final Consent consent = getConsent(consentCriteria.getId());
		
		// Check if the gin is the same in the request and in DB
		if(!consent.getGin().equals(consentCriteria.getGin())) {
			throw new ServiceException(BusinessErrorList.API_INVALID_CUSTOMER.getError(), HttpStatus.NOT_FOUND);
		}

		// Get consentData
		final ConsentData consentData = getConsentData(consentCriteria.getId());
		
		if ("N".equals(consentData.getIsConsent()) && "N".equals(consentCriteria.getIsConsent())) {
			//If consent is equal to N and we want to change for N -> Error Already_Not_Consent
			throw new ServiceException(BusinessErrorList.API_ALREADY_NOT_CONSENT.getError(),
					HttpStatus.PRECONDITION_FAILED);
		} else if ("Y".equals(consentData.getIsConsent()) && "Y".equals(consentCriteria.getIsConsent())) {
			//If consent is equal to Y and we want to change for Y -> Error Already_Consent
			throw new ServiceException(BusinessErrorList.API_ALREADY_CONSENT.getError(),
					HttpStatus.PRECONDITION_FAILED);
		} else {
			//In Other case, we can change it.
			consentData.setIsConsent(consentCriteria.getIsConsent());
			// Set dateConsent to now if field dateConsent is not filled
			if (consentCriteria.getDateConsent() != null) {
				consentData.setDateConsent(consentCriteria.getDateConsent());
			} else {
				consentData.setDateConsent(now);
			}
		}

		// Consent data signature
		consent.setDateModification(now);
		consent.setSiteModification(repindMSV);
		consent.setSignatureModification(consentCriteria.getApplication());
		
		// Consent data signature
		consentData.setDateModification(now);
		consentData.setSiteModification(repindMSV);
		consentData.setSignatureModification(consentCriteria.getApplication());

		// Store consent if all is fine
		consentRepository.saveAndFlush(consent);
		
		// Store consent data if all is fine
		consentDataRepository.saveAndFlush(consentData);
		
		// Build response (only gin and time of the query)
		final WrapperCreateConsentResponse wrapperConsentDataResponse = new WrapperCreateConsentResponse();
		wrapperConsentDataResponse.gin = consentCriteria.getGin();
		
		return new ResponseEntity<>(wrapperConsentDataResponse, HttpStatus.OK);
	}


	/**
	 * Create and return provide service 
	 *
	 * @return service
	 * @throws ServiceException 
	 */
	public ResponseEntity<WrapperGetConsentResponse> provideConsentByGin(ConsentCriteria consentCriteria) throws ServiceException {
		
		final List<Consent> listConsent = consentRepository.findByGin(consentCriteria.getGin());
		
		if(listConsent == null || listConsent.isEmpty()) {
			throw new ServiceException(BusinessErrorList.API_CONSENT_NOT_FOUND.getError(),
					HttpStatus.NOT_FOUND);
		}
		
		final WrapperGetConsentResponse wrapperConsentDataResponse = new WrapperGetConsentResponse();
		wrapperConsentDataResponse.gin = consentCriteria.getGin();
		wrapperConsentDataResponse.consent = convertListToModel(listConsent);
		
		return new ResponseEntity<>(wrapperConsentDataResponse, HttpStatus.OK);
	}
	
	/**
	 * 
	 * Check if customer exists in SIC database:
	 *  - check in individus_all table
	 *  - check in personne_morale table
	 *  
	 * @return boolean
	 */
	public boolean isCustomerInSIC(final String gin) {
		final Optional<Individual> ind = individualRepository.findIndividualNotDeleted(gin);
		if (!ind.isPresent()) {
			return moralPersonRepository.findMoralPersonNotDeleted(gin).isPresent();
		}
		return true;
	}

	/**
	 * 
	 * Check if the type is allowed.
	 * If no, throw an exception
	 *  
	 * @return boolean
	 * @throws ServiceException 
	 */
	public List<String> checkIfTypeIsAllowed(final String type) throws ServiceException {
		
		// Get types allowed for this type
		final List<String> consentDataTypeAllowed = refConsentTypeDataTypeRepository.findByType(type);
		// If there is no type allowed for this type, throw an exception (Type is not allowed)
		if(consentDataTypeAllowed == null || consentDataTypeAllowed.isEmpty()) {
			throw new ServiceException(BusinessErrorList.API_TYPE_NOT_ALLOWED.getError(),
					HttpStatus.PRECONDITION_FAILED);
		}
		
		return consentDataTypeAllowed;
	}
	

	/**
	 * 
	 * Check if the consent is allowed.
	 * If no, throw an exception
	 *  
	 * @throws ServiceException 
	 */
	public void checkIfConsentIsAllowedForCreation(final String consent) throws ServiceException {
		// If the consent is not allowed for the creation, throw an exception (Consent is not allowed)
		if (!"Y".equals(consent) && !"N".equals(consent) && !"P".equals(consent)) {
			throw new ServiceException(BusinessErrorList.API_CONSENT_NOT_ALLOWED.getError(),
					HttpStatus.PRECONDITION_FAILED);
		}
		
	}
	
	/**
	 * 
	 * Check if the consent is allowed.
	 * If no, throw an exception
	 *  
	 * @throws ServiceException 
	 */
	public void checkIfConsentIsAllowedForUpdate(final String consent) throws ServiceException {
		// If the consent is not allowed for the creation, throw an exception (Consent is not allowed)
		if (!"Y".equals(consent) && !"N".equals(consent)) {
			throw new ServiceException(BusinessErrorList.API_CONSENT_NOT_ALLOWED.getError(),
					HttpStatus.PRECONDITION_FAILED);
		}
		
	}

	/**
	 * 
	 * Get consent by ConsentDataId. If not exists, throw a not found exception
	 *  
	 * @return response
	 * @throws ServiceException 
	 */
	public Consent getConsent(Long id) throws ServiceException {
		
		Consent consent = consentRepository.findByConsentDataConsentDataId(id);
		
		if(consent == null) {
			throw new ServiceException(BusinessErrorList.API_CONSENT_NOT_FOUND.getError(), HttpStatus.NOT_FOUND);
		}
		
		return consent;
		
	}
	
	/**
	 * 
	 * Get consentData by Id. If not exists, throw a not found exception
	 *  
	 * @return response
	 * @throws ServiceException 
	 */
	public ConsentData getConsentData(Long id) throws ServiceException {
		
		Optional<ConsentData> consentData = consentDataRepository.findById(id);
		
		if(!consentData.isPresent()) {
			throw new ServiceException(BusinessErrorList.API_CONSENT_NOT_FOUND.getError(), HttpStatus.NOT_FOUND);
		}
		
		return consentData.get();
		
	}


	/**
	 * Add to model 
	 *
	 * @return service
	 * @throws ServiceException 
	 */
	public List<GetConsentModel> convertListToModel(List<Consent> listConsent) {
		
		List<GetConsentModel> listConsentModel = new ArrayList<>();
		
		for (Consent consent : listConsent) {
			listConsentModel.add(mapper.consentToConsentModel(consent));
		}
		
		return listConsentModel;
	}
	

}