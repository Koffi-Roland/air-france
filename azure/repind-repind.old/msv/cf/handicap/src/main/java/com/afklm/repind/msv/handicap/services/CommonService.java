package com.afklm.repind.msv.handicap.services;

import java.util.List;
import java.util.Optional;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.afklm.repind.msv.handicap.entity.Handicap;
import com.afklm.repind.msv.handicap.entity.Individual;
import com.afklm.repind.msv.handicap.entity.RefHandicapType;
import com.afklm.repind.msv.handicap.entity.RefHandicapTypeCodeData;
import com.afklm.repind.msv.handicap.model.error.BusinessErrorList;
import com.afklm.repind.msv.handicap.repository.HandicapRepository;
import com.afklm.repind.msv.handicap.repository.IndividualRepository;
import com.afklm.repind.msv.handicap.repository.RefHandicapTypeCodeDataRepository;
import com.afklm.repind.msv.handicap.repository.RefHandicapTypeCodeRepository;
import com.afklm.repind.msv.handicap.repository.RefHandicapTypeRepository;
import com.afklm.repind.msv.handicap.services.exception.ServiceException;

@Component
public class CommonService {

	@Autowired
	private HandicapRepository handicapRepository;

	@Autowired
	private IndividualRepository individualRepository;

	@Autowired
	private RefHandicapTypeCodeDataRepository refHandicapTypeCodeDataRepository;

	@Autowired
	private RefHandicapTypeCodeRepository refHandicapTypeCodeRepository;

	@Autowired
	private RefHandicapTypeRepository refHandicapTypeRepository;

	/**
	 * 
	 * For each data, check if the key is allowed for the type
	 * If yes and if the value is not blank, add data to the handicap Object
	 *  
	 * @return response
	 * @throws ServiceException 
	 */
	public Handicap getHandicap(Long id) throws ServiceException {
		
		Optional<Handicap> handicap = handicapRepository.findById(id);
		
		if(!handicap.isPresent()) {
			throw new ServiceException(BusinessErrorList.API_HANDICAP_NOT_FOUND.getError(), HttpStatus.NOT_FOUND);
		}
		
		return handicap.get();
		
	}


	/**
	 * 
	 * Check if customer exists in SIC database:
	 *  - check in individus_all table
	 *  
	 * @return boolean
	 */
	public boolean isCustomerInSIC(final String gin) {
		//return individualRepository.findIndividualNotDeleted(gin).isPresent();
		Optional<Individual> tt = individualRepository.findIndividualNotDeleted(gin);
		return tt.isPresent();
	}

	/**
	 * 
	 * Get allowed type from database
	 *  
	 * @return list of allowed type
	 * @throws ServiceException 
	 */
	public List<RefHandicapType> getAllowedType() throws ServiceException {
		
		return refHandicapTypeRepository.findAll();
		
	}

	/**
	 * 
	 * Get handicap from database for one gin
	 *  
	 * @return list of handicap from database
	 * @throws ServiceException 
	 */
	public List<Handicap> getHandicapByGin(final String gin) throws ServiceException {
		
		return handicapRepository.findByGin(gin);
		
	}

	/**
	 * 
	 * Check if the type is allowed.
	 * If no, throw an exception
	 *  
	 * @return list of RefHandicapTypeCodeData
	 * @throws ServiceException 
	 */
	public List<RefHandicapTypeCodeData> checkIfTypeAndCodeIsAllowed(final String type, final String code) throws ServiceException {
		
		if(refHandicapTypeCodeRepository.countByTypeAndCode(type, code) == 0) {
			throw new ServiceException(BusinessErrorList.API_TYPE_OR_CODE_NOT_ALLOWED.getError(),
					HttpStatus.PRECONDITION_FAILED);
		}
		
		// Get keys allowed for this type
		return refHandicapTypeCodeDataRepository.findByTypeAndCode(type, code);
		
	}

}