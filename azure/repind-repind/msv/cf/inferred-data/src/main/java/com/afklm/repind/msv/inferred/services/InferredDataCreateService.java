package com.afklm.repind.msv.inferred.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.afklm.repind.msv.inferred.criteria.InferredDataCriteria;
import com.afklm.repind.msv.inferred.entity.Individual;
import com.afklm.repind.msv.inferred.entity.Inferred;
import com.afklm.repind.msv.inferred.entity.InferredData;
import com.afklm.repind.msv.inferred.model.InferredDataModel;
import com.afklm.repind.msv.inferred.model.error.BusinessErrorList;
import com.afklm.repind.msv.inferred.repository.IndividualRepository;
import com.afklm.repind.msv.inferred.repository.InferredRepository;
import com.afklm.repind.msv.inferred.repository.MoralPersonRepository;
import com.afklm.repind.msv.inferred.repository.RefInfrdKeyTypeRepository;
import com.afklm.repind.msv.inferred.services.exception.ServiceException;
import com.afklm.repind.msv.inferred.wrapper.WrapperInferredDataCreateResponse;

@Component
public class InferredDataCreateService {

	@Autowired
	private InferredRepository inferredRepository;

	@Autowired
	private RefInfrdKeyTypeRepository refInfrdKeyTypeRepository;

	@Autowired
	private IndividualRepository individualRepository;

	@Autowired
	private MoralPersonRepository moralPersonRepository;
	
	private final static String STATUS_CALCULATED = "C";
	    
	/**
	 * 
	 * Check if the gin is valid in SIC database
	 * Check if the type is allowed
	 * 
	 * If it is ok, create Inferred data and store it in database
	 *
	 * Return the gin of the individual updated and the time to process the storage	
	 *
	 * @return response
	 * @throws ServiceException 
	 */
	public ResponseEntity<WrapperInferredDataCreateResponse> createInferredData(final InferredDataCriteria inferredDataCriteria) throws ServiceException {
		
		final long startTime = new Date().getTime();

		// Check if customer exists in SIC
		if(!isCustomerInSIC(inferredDataCriteria.getGin())) {
			throw new ServiceException(BusinessErrorList.API_CUSTOMER_NOT_FOUND.getError(), HttpStatus.NOT_FOUND);
		}
		
		// Check if type is allowed
		List<String> keysAllowed = checkIfTypeIsAllowed(inferredDataCriteria.getType());
		
		final Date now = new Date();

		// Create inferred object
		final Inferred inferred = new Inferred();
		inferred.setGin(inferredDataCriteria.getGin());
		inferred.setType(inferredDataCriteria.getType());
		inferred.setStatus(STATUS_CALCULATED);
		inferred.setDateCreation(now);
		inferred.setDateModification(now);
		inferred.setSiteCreation("REPINDMSV");
		inferred.setSiteModification("REPINDMSV");
		inferred.setSignatureCreation(inferredDataCriteria.getApplication());
		inferred.setSignatureModification(inferredDataCriteria.getApplication());
		
		// Check and build list of inferred data
		inferred.setInferredData(getInferredData(inferred, inferredDataCriteria.getData(), keysAllowed, inferredDataCriteria.getApplication(), now));
		
		// Store inferred data if all is fine
		inferredRepository.saveAndFlush(inferred);
		
		// Build response (only gin and time of the query)
		final WrapperInferredDataCreateResponse wrapperInferredDataResponse = new WrapperInferredDataCreateResponse();
		wrapperInferredDataResponse.gin = inferredDataCriteria.getGin();
		wrapperInferredDataResponse.query = new Date().getTime() - startTime + " ms";
		
		return new ResponseEntity<>(wrapperInferredDataResponse, HttpStatus.OK);
	}
	

	/**
	 * 
	 * For each data, check if the key is allowed for the type
	 * If yes and if the value is not blank, add data to the inferred Object
	 *  
	 * @return response
	 * @throws ServiceException 
	 */
	public List<InferredData> getInferredData(Inferred inferred, List<InferredDataModel> listInferredDataModel, List<String> keysAllowed, String application, Date now) throws ServiceException {
		
		List<InferredData> listInferredData = new ArrayList<InferredData>();

		List<String> keysAdded = new ArrayList<String>();
		
		for (InferredDataModel inferredDataModel : listInferredDataModel) {
			
			String key = inferredDataModel.getKey();
			String value = inferredDataModel.getValue();
			
			// Check if the key or the value is blank
			if(!StringUtils.isBlank(key) && !StringUtils.isBlank(value)) {
				
				key = StringUtils.upperCase(key);
				
				if(value.length() > 254) {
					
					// Value max length
					throw new ServiceException(BusinessErrorList.API_VALUE_TOO_LONG.getError().setDescription("Value : " + value),
							HttpStatus.PRECONDITION_FAILED);
					
				}
				
				if(keysAdded.contains(key)) {
					
					// If the key is already set, doublon !
					throw new ServiceException(BusinessErrorList.API_PRESENT_MORE_THAN_ONCE.getError().setDescription("Key : " + inferredDataModel.getKey()),
							HttpStatus.PRECONDITION_FAILED);
					
				}
				
				// Check if the key is allowed for this type
				if (keysAllowed.contains(key)) {
					InferredData inferredData = new InferredData();
					inferredData.setKey(key);
					inferredData.setValue(value);
					inferredData.setDateCreation(now);
					inferredData.setDateModification(now);
					inferredData.setSiteCreation("REPINDMSV");
					inferredData.setSiteModification("REPINDMSV");
					inferredData.setSignatureCreation(application);
					inferredData.setSignatureModification(application);
					inferredData.setInferred(inferred);
					
					keysAdded.add(key);
					
					listInferredData.add(inferredData);
					
				} else {
					// If the key is not allowed for this type, throw an exception
					throw new ServiceException(BusinessErrorList.API_INVALID_KEY.getError().setDescription("Key : " + inferredDataModel.getKey()),
							HttpStatus.PRECONDITION_FAILED);
				}
				
			} else {
				// If the key or the value is blank, throw an exception Invalid Parameter
				throw new ServiceException(BusinessErrorList.API_INVALID_INFERRED_DATA.getError(),
						HttpStatus.PRECONDITION_FAILED);
			}
		}
		
		return listInferredData;
		
		
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
		
		// Get keys allowed for this type
		final List<String> keysAllowed = refInfrdKeyTypeRepository.findByType(type);
		// If there is no key allowed for this type, throw an exception (Type is not allowed)
		if(keysAllowed == null || keysAllowed.size() == 0) {
			throw new ServiceException(BusinessErrorList.API_TYPE_NOT_ALLOWED.getError(),
					HttpStatus.PRECONDITION_FAILED);
		}
		
		return keysAllowed;
	}

}