package com.afklm.repind.msv.handicap.services;

import java.util.ArrayList;
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
import com.afklm.repind.msv.handicap.entity.RefHandicapTypeCodeData;
import com.afklm.repind.msv.handicap.model.HandicapDataCreateModel;
import com.afklm.repind.msv.handicap.model.error.BusinessErrorList;
import com.afklm.repind.msv.handicap.repository.HandicapDataRepository;
import com.afklm.repind.msv.handicap.repository.HandicapRepository;
import com.afklm.repind.msv.handicap.repository.RefHandicapTypeCodeDataRepository;
import com.afklm.repind.msv.handicap.services.exception.ServiceException;
import com.afklm.repind.msv.handicap.wrapper.WrapperHandicapDeleteResponse;

@Component
public class HandicapDeleteService {

	@Autowired
	private HandicapRepository handicapRepository;
	
	@Autowired
	private HandicapDataRepository handicapDataRepository;
	
	@Autowired
	private RefHandicapTypeCodeDataRepository refHandicapTypeCodeDataRepository;
	
	
	/**
	 * Delete Handicap based on Id andr/or Keys
	 *
	 * @return WrapperHandicapDeleteResponse
	 * @throws ServiceException 
	 */
	public ResponseEntity<WrapperHandicapDeleteResponse> deleteHandicap(HandicapCriteria handicapCriteria) throws ServiceException {
		
		final WrapperHandicapDeleteResponse wrapperHandicapDeleteResponse = new WrapperHandicapDeleteResponse();
		wrapperHandicapDeleteResponse.id = handicapCriteria.getId();
		
		if (handicapCriteria.getHandicapData() == null) {
			Long result = handicapRepository.deleteByHandicapId(handicapCriteria.getId());
			
			if (result == 0) {
				throw new ServiceException(BusinessErrorList.API_HANDICAP_NOT_FOUND.getError().setDescription("Id: " + handicapCriteria.getId()), HttpStatus.NOT_FOUND);
			}
			
		} else {
			
			wrapperHandicapDeleteResponse.keys = new ArrayList<String>();
			
			Optional<Handicap> handicap = handicapRepository.findById(handicapCriteria.getId());
			
			if (handicap.isPresent()) {
				for (HandicapDataCreateModel handicapDataCreateModel : handicapCriteria.getHandicapData()) {
					RefHandicapTypeCodeData refHandicapTypeCodeData = refHandicapTypeCodeDataRepository.findByTypeAndCodeAndKey(handicap.get().getType(), handicap.get().getCode(), handicapDataCreateModel.getKey());
					
					if (refHandicapTypeCodeData == null) {
						throw new ServiceException(BusinessErrorList.API_KEY_NOT_EXISTING.getError().setDescription("Key : " + handicapDataCreateModel.getKey() + ", Type: " + handicap.get().getType() + ", Code: " + handicap.get().getCode()), HttpStatus.PRECONDITION_FAILED);
					}
					
					if ("M".equalsIgnoreCase(refHandicapTypeCodeData.getCondition())) {
						throw new ServiceException(BusinessErrorList.API_KEY_IS_MANDATORY.getError().setDescription("Key : " + handicapDataCreateModel.getKey() + ", Type: " + handicap.get().getType() + ", Code: " + handicap.get().getCode()), HttpStatus.PRECONDITION_FAILED);				
					}
					
					Long result = handicapDataRepository.deleteByHandicapHandicapIdAndKey(handicapCriteria.getId(), handicapDataCreateModel.getKey());
					
					if (result == 0) {
						throw new ServiceException(BusinessErrorList.API_KEY_NOT_FOUND.getError().setDescription("Key : " + handicapDataCreateModel.getKey() + ", Id: " + handicapCriteria.getId()), HttpStatus.NOT_FOUND);
					}
					
					wrapperHandicapDeleteResponse.keys.add(handicapDataCreateModel.getKey());
				}
			} else {
				throw new ServiceException(BusinessErrorList.API_HANDICAP_NOT_FOUND.getError().setDescription("Id: " + handicapCriteria.getId()), HttpStatus.NOT_FOUND);
			}
		}
		
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
		headers.add("Cache-Control", "no-cache, no-store, max-age=0, must-revalidate");

		return new ResponseEntity<>(wrapperHandicapDeleteResponse, headers, HttpStatus.OK);
	}
}