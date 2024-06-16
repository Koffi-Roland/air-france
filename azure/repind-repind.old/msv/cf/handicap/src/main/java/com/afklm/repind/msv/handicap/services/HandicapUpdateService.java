package com.afklm.repind.msv.handicap.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.afklm.repind.msv.handicap.criteria.HandicapCriteria;
import com.afklm.repind.msv.handicap.entity.Handicap;
import com.afklm.repind.msv.handicap.entity.HandicapData;
import com.afklm.repind.msv.handicap.entity.RefHandicapTypeCodeData;
import com.afklm.repind.msv.handicap.model.error.BusinessErrorList;
import com.afklm.repind.msv.handicap.repository.HandicapRepository;
import com.afklm.repind.msv.handicap.services.exception.ServiceException;
import com.afklm.repind.msv.handicap.services.helper.HandicapHelper;
import com.afklm.repind.msv.handicap.wrapper.WrapperHandicapUpdateResponse;

@Component
public class HandicapUpdateService {

	@Autowired
	private HandicapRepository handicapRepository;

	@Autowired
	private HandicapHelper handicapHelper;
	
	@Autowired
	private CommonService commonService;
	    
	/**
	 * 
	 * Check if the gin is valid in SIC database
	 * 
	 * If it is ok, create Handicap and store it in database
	 *
	 * Return the gin of the individual updated and the time to process the storage	
	 *
	 * @return response
	 * @throws ServiceException 
	 */
	public ResponseEntity<WrapperHandicapUpdateResponse> updateHandicap(final HandicapCriteria handicapCriteria) throws ServiceException {
		
		// Check if customer exists in SIC
		if(!commonService.isCustomerInSIC(handicapCriteria.getGin())) {
			throw new ServiceException(BusinessErrorList.API_CUSTOMER_NOT_FOUND.getError(), HttpStatus.NOT_FOUND);
		}
		
		// Create handicap object
		final Handicap handicap = commonService.getHandicap(handicapCriteria.getId());

		// Check if the gin is the same in the request and in DB
		if(!handicap.getGin().equals(handicapCriteria.getGin())) {
			throw new ServiceException(BusinessErrorList.API_INVALID_CUSTOMER.getError(), HttpStatus.NOT_FOUND);
		}

		final Date now = new Date();

		// Check if type is allowed
		List<RefHandicapTypeCodeData> keysAllowed = commonService.checkIfTypeAndCodeIsAllowed(handicap.getType(), handicap.getCode());
		
		List<HandicapData> listHandicapData = handicapHelper.getHandicapData(handicap, handicapCriteria.getHandicapData(), keysAllowed, handicapCriteria.getApplication(), now, true);
		
		updateHandicapData(listHandicapData, handicap);
		
		handicap.setDateModification(now);
		handicap.setSiteModification("REPINDMSV");
		handicap.setSignatureModification(handicapCriteria.getApplication());
		
		// Update handicap if all is fine
		handicapRepository.saveAndFlush(handicap);
		
		// Build response (only gin and time of the query)
		final WrapperHandicapUpdateResponse wrapperHandicapUpdateResponse = new WrapperHandicapUpdateResponse();
		wrapperHandicapUpdateResponse.gin = handicap.getGin();
		
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
		headers.add("Cache-Control", "no-cache, no-store, max-age=0, must-revalidate");
		
		return new ResponseEntity<>(wrapperHandicapUpdateResponse, headers, HttpStatus.OK);
	}
	
	public void updateHandicapData(final List<HandicapData> listHandicapData, final Handicap handicap) throws ServiceException {
		
		List<HandicapData> listHandicapDataFromDB = handicap.getHandicapData();
		
		for (HandicapData handicapData : listHandicapData) {
			
			Optional<HandicapData> handicapDataFromDB = listHandicapDataFromDB.stream()
					.filter(attr -> handicapData.getKey().equalsIgnoreCase(attr.getKey()))
					.findFirst();
			if(handicapDataFromDB.isPresent()) {
				
				handicapDataFromDB.get().setValue(handicapData.getValue());
				handicapDataFromDB.get().setDateModification(handicapData.getDateModification());
				handicapDataFromDB.get().setSiteModification(handicapData.getSiteModification());
				handicapDataFromDB.get().setSignatureModification(handicapData.getSignatureModification());
				
			} else {
				listHandicapDataFromDB.add(handicapData);
			}
			
		}
		
	}

}