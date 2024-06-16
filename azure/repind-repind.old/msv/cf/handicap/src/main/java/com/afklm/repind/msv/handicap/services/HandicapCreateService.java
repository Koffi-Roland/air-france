package com.afklm.repind.msv.handicap.services;

import com.afklm.repind.msv.handicap.criteria.HandicapCriteria;
import com.afklm.repind.msv.handicap.entity.Handicap;
import com.afklm.repind.msv.handicap.entity.RefHandicapTypeCodeData;
import com.afklm.repind.msv.handicap.model.CreateHandicapModel;
import com.afklm.repind.msv.handicap.model.error.BusinessErrorList;
import com.afklm.repind.msv.handicap.repository.HandicapRepository;
import com.afklm.repind.msv.handicap.services.exception.ServiceException;
import com.afklm.repind.msv.handicap.services.helper.HandicapHelper;
import com.afklm.repind.msv.handicap.wrapper.WrapperHandicapCreateResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class HandicapCreateService {

	@Autowired
	private HandicapRepository handicapRepository;

	@Autowired
	private HandicapHelper handicapHelper;
	
	@Autowired
	private CommonService commonService;
	
	/**
	 * 
	 * Check if the gin is valid in SIC database
	 * Check if the type is allowed
	 * 
	 * If it is ok, create Handicap and store it in database
	 *
	 * Return the gin of the individual updated
	 *
	 * @return response
	 * @throws ServiceException 
	 */
	public ResponseEntity<WrapperHandicapCreateResponse> createHandicap(final HandicapCriteria handicapCriteria) throws ServiceException {
		
		// Check if customer exists in SIC
		if(!commonService.isCustomerInSIC(handicapCriteria.getGin())) {
			throw new ServiceException(BusinessErrorList.API_CUSTOMER_NOT_FOUND.getError(), HttpStatus.NOT_FOUND);
		}

		handicapHelper.controlTypeAndCode(handicapCriteria.getHandicap(), commonService.getHandicapByGin(handicapCriteria.getGin()), commonService.getAllowedType());
		
		List<Handicap> handicapToAdd = new ArrayList<>();
		
		for (CreateHandicapModel handicapDataModel : handicapCriteria.getHandicap()) {
			
			// Check if type is allowed
			List<RefHandicapTypeCodeData> keysAllowed = commonService.checkIfTypeAndCodeIsAllowed(handicapDataModel.getType(), handicapDataModel.getCode());
			
			final Date now = new Date();
			
			// Create handicap object
			final Handicap handicap = new Handicap();
			handicap.setGin(handicapCriteria.getGin());
			handicap.setType(handicapDataModel.getType());
			handicap.setCode(handicapDataModel.getCode());
			handicap.setDateCreation(now);
			handicap.setDateModification(now);
			handicap.setSiteCreation("REPINDMSV");
			handicap.setSiteModification("REPINDMSV");
			handicap.setSignatureCreation(handicapCriteria.getApplication());
			handicap.setSignatureModification(handicapCriteria.getApplication());
			
			// Check and build list of handicap data
			handicap.setHandicapData(
					handicapHelper.getHandicapData(handicap, handicapDataModel.getData(), keysAllowed,
							handicapCriteria.getApplication(), now, false)
					);
			
			handicapToAdd.add(handicap);
		
		}

		// Store handicap data if all is fine
		for (Handicap handicap : handicapToAdd) {
			
			handicapRepository.saveAndFlush(handicap);
			
		}
		
		// Build response
		final WrapperHandicapCreateResponse wrapperHandicapCreateResponse = new WrapperHandicapCreateResponse();
		wrapperHandicapCreateResponse.gin = handicapCriteria.getGin();
		
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.add("Cache-Control", "no-cache, no-store, max-age=0, must-revalidate");
		
		return new ResponseEntity<>(wrapperHandicapCreateResponse, headers, HttpStatus.OK);
	}
}