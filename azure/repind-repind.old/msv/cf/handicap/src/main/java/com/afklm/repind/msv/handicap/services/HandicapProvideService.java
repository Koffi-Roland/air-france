package com.afklm.repind.msv.handicap.services;

import com.afklm.repind.msv.handicap.criteria.HandicapCriteria;
import com.afklm.repind.msv.handicap.entity.Handicap;
import com.afklm.repind.msv.handicap.model.HandicapModel;
import com.afklm.repind.msv.handicap.model.error.BusinessErrorList;
import com.afklm.repind.msv.handicap.repository.HandicapRepository;
import com.afklm.repind.msv.handicap.services.exception.ServiceException;
import com.afklm.repind.msv.handicap.wrapper.WrapperHandicapProvideResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.ArrayList;
import java.util.List;

import com.afklm.repind.msv.handicap.config.Config.BeanMapper;

@Component
public class HandicapProvideService {

	private static final String CACHE_CONTROL = "Cache-Control";
	private static final String CACHE_CONTROL_VALUE = "no-cache, no-store, max-age=0, must-revalidate";

	@Autowired
	private HandicapRepository handicapRepository;
	
	@Autowired
	private BeanMapper mapper;
	    
	/**
	 * Provide Handicap based on Gin
	 *
	 * @return WrapperProvideHandicapResponse
	 * @throws ServiceException 
	 */
	public ResponseEntity<WrapperHandicapProvideResponse> provideHandicapByGin(HandicapCriteria handicapCriteria) throws ServiceException {

		final List<Handicap> listHandicap = handicapRepository.findByGin(handicapCriteria.getGin());
		
		if (listHandicap == null || listHandicap.isEmpty()) {
			throw new ServiceException(BusinessErrorList.API_HANDICAP_NOT_FOUND.getError(),
					HttpStatus.NOT_FOUND);
		}
		
		final WrapperHandicapProvideResponse wrapperProvideHandicapResponse = new WrapperHandicapProvideResponse();
		wrapperProvideHandicapResponse.setGin(handicapCriteria.getGin());
		wrapperProvideHandicapResponse.setHandicaps(convertListToModel(listHandicap));
		
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.add(CACHE_CONTROL, CACHE_CONTROL_VALUE);
		
		return new ResponseEntity<>(wrapperProvideHandicapResponse, headers, HttpStatus.OK);
	}
	
	/**
	 * Provide Handicap based on Gin and Type
	 *
	 * @return WrapperProvideHandicapResponse
	 * @throws ServiceException 
	 */
	public ResponseEntity<WrapperHandicapProvideResponse> provideHandicapByGinAndType(HandicapCriteria handicapCriteria) throws ServiceException {

		final List<Handicap> listHandicap = handicapRepository.findByGinAndType(handicapCriteria.getGin(), handicapCriteria.getType());
		
		if (listHandicap == null || listHandicap.isEmpty()) {
			throw new ServiceException(BusinessErrorList.API_HANDICAP_NOT_FOUND.getError(),
					HttpStatus.NOT_FOUND);
		}
		
		final WrapperHandicapProvideResponse wrapperProvideHandicapResponse = new WrapperHandicapProvideResponse();
		wrapperProvideHandicapResponse.setGin(handicapCriteria.getGin());
		wrapperProvideHandicapResponse.setHandicaps(convertListToModel(listHandicap));
		
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.add(CACHE_CONTROL, CACHE_CONTROL_VALUE);
		
		return new ResponseEntity<>(wrapperProvideHandicapResponse, headers, HttpStatus.OK);
	}
	
	/**
	 * Provide Handicap based on Gin and Type and Code
	 *
	 * @return WrapperProvideHandicapResponse
	 * @throws ServiceException 
	 */
	public ResponseEntity<WrapperHandicapProvideResponse> provideHandicapByGinAndTypeAndCode(HandicapCriteria handicapCriteria) throws ServiceException {

		final List<Handicap> listHandicap = handicapRepository.findByGinAndTypeAndCode(handicapCriteria.getGin(), handicapCriteria.getType(), handicapCriteria.getCode());
		
		if (listHandicap == null || listHandicap.isEmpty()) {
			throw new ServiceException(BusinessErrorList.API_HANDICAP_NOT_FOUND.getError(),
					HttpStatus.NOT_FOUND);
		}
		
		final WrapperHandicapProvideResponse wrapperProvideHandicapResponse = new WrapperHandicapProvideResponse();
		wrapperProvideHandicapResponse.setGin(handicapCriteria.getGin());
		wrapperProvideHandicapResponse.setHandicaps(convertListToModel(listHandicap));
		
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.add(CACHE_CONTROL, CACHE_CONTROL_VALUE);
		
		return new ResponseEntity<>(wrapperProvideHandicapResponse, headers, HttpStatus.OK);
	}
	
	/**
	 * Add to model 
	 *
	 * @return service
	 * @throws ServiceException 
	 */
	public List<HandicapModel> convertListToModel(List<Handicap> listHandicap) {
		
		List<HandicapModel> listHandicapModel = new ArrayList<>();
		
		for (Handicap handicap : listHandicap) {
			listHandicapModel.add(mapper.handicapToHandicapModel(handicap));
		}
		
		return listHandicapModel;
	}
}