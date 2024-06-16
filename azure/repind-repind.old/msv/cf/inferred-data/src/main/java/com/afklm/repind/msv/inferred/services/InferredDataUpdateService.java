package com.afklm.repind.msv.inferred.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.afklm.repind.msv.inferred.criteria.InferredDataCriteria;
import com.afklm.repind.msv.inferred.entity.Individual;
import com.afklm.repind.msv.inferred.entity.Inferred;
import com.afklm.repind.msv.inferred.model.error.BusinessErrorList;
import com.afklm.repind.msv.inferred.repository.IndividualRepository;
import com.afklm.repind.msv.inferred.repository.InferredRepository;
import com.afklm.repind.msv.inferred.repository.MoralPersonRepository;
import com.afklm.repind.msv.inferred.repository.RefInfrdStatusRepository;
import com.afklm.repind.msv.inferred.services.exception.ServiceException;
import com.afklm.repind.msv.inferred.wrapper.WrapperInferredDataUpdateResponse;

@Component
public class InferredDataUpdateService {

	@Autowired
	private InferredRepository inferredRepository;

	@Autowired
	private RefInfrdStatusRepository refInfrdStatusRepository;

	@Autowired
	private IndividualRepository individualRepository;

	@Autowired
	private MoralPersonRepository moralPersonRepository;
	    
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
	public ResponseEntity<WrapperInferredDataUpdateResponse> updateInferredData(final InferredDataCriteria inferredDataCriteria) throws ServiceException {
		
		final long startTime = new Date().getTime();

		// Check if customer exists in SIC
		if(!isCustomerInSIC(inferredDataCriteria.getGin())) {
			throw new ServiceException(BusinessErrorList.API_CUSTOMER_NOT_FOUND.getError(), HttpStatus.NOT_FOUND);
		}
		
		// Check if status is allowed
		checkIfStatusIsAllowed(inferredDataCriteria.getStatus());
		
		final Date now = new Date();

		// Create inferred object
		final Inferred inferred = getInferred(inferredDataCriteria.getId());

		// Check if the gin is the same in the request and in DB
		if(!inferred.getGin().equals(inferredDataCriteria.getGin())) {
			throw new ServiceException(BusinessErrorList.API_INVALID_CUSTOMER.getError(), HttpStatus.NOT_FOUND);
		}
		
		inferred.setStatus(inferredDataCriteria.getStatus());
		inferred.setDateModification(now);
		inferred.setSiteModification("REPINDMSV");
		inferred.setSignatureModification(inferredDataCriteria.getApplication());
		
		// Store inferred data if all is fine
		inferredRepository.saveAndFlush(inferred);
		
		// Build response (only gin and time of the query)
		final WrapperInferredDataUpdateResponse wrapperInferredDataResponse = new WrapperInferredDataUpdateResponse();
		wrapperInferredDataResponse.gin = inferred.getGin();
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
	public Inferred getInferred(Long id) throws ServiceException {
		
		Optional<Inferred> inferred = inferredRepository.findById(id);
		
		if(!inferred.isPresent()) {
			throw new ServiceException(BusinessErrorList.API_INFERRED_DATA_NOT_FOUND.getError(), HttpStatus.NOT_FOUND);
		}
		
		return inferred.get();
		
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
	 * Check if the status is allowed for an update.
	 * If no, throw an exception
	 *  
	 * @throws ServiceException 
	 */
	public void checkIfStatusIsAllowed(final String status) throws ServiceException {
		// Get status allowed for the creation
		final List<String> statusAllowed = refInfrdStatusRepository.findStatusForUpdate();
		// If the status is not allowed for the creation, throw an exception (Status is not allowed)
		if (!statusAllowed.contains(status)) {
			throw new ServiceException(BusinessErrorList.API_STATUS_NOT_ALLOWED.getError(),
					HttpStatus.PRECONDITION_FAILED);
		}
		
	}

}