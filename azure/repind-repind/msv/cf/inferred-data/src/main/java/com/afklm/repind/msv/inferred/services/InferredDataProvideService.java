package com.afklm.repind.msv.inferred.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.afklm.repind.msv.inferred.criteria.IndividualCriteria;
import com.afklm.repind.msv.inferred.entity.Inferred;
import com.afklm.repind.msv.inferred.model.GetInferredDataModel;
import com.afklm.repind.msv.inferred.model.error.BusinessErrorList;
import com.afklm.repind.msv.inferred.repository.InferredRepository;
import com.afklm.repind.msv.inferred.services.exception.ServiceException;
import com.afklm.repind.msv.inferred.wrapper.WrapperProvideInferredDataResponse;

import com.afklm.repind.msv.inferred.config.Config.BeanMapper;

@Component
public class InferredDataProvideService {

	@Autowired
	private InferredRepository inferredRepository;
	
	@Autowired
	private BeanMapper mapper;
	    
	/**
	 * Create and return provide service 
	 *
	 * @return service
	 * @throws ServiceException 
	 */
	public ResponseEntity<WrapperProvideInferredDataResponse> provideInferredDataByGin(IndividualCriteria individualCriteria) throws ServiceException {
		
		final long startTime = new Date().getTime();
		
		final List<Inferred> listInferred = inferredRepository.findByGin(individualCriteria.getGin());
		
		if(listInferred == null || listInferred.size() == 0) {
			throw new ServiceException(BusinessErrorList.API_INFERRED_DATA_NOT_FOUND.getError(),
					HttpStatus.NOT_FOUND);
		}
		
		final WrapperProvideInferredDataResponse wrapperInferredDataResponse = new WrapperProvideInferredDataResponse();
		wrapperInferredDataResponse.inferredData = convertListToModel(listInferred);
		wrapperInferredDataResponse.query = new Date().getTime() - startTime + " ms";
		
		return new ResponseEntity<>(wrapperInferredDataResponse, HttpStatus.OK);
	}
	

	/**
	 * Add to model 
	 *
	 * @return service
	 * @throws ServiceException 
	 */
	public List<GetInferredDataModel> convertListToModel(List<Inferred> listInferred) throws ServiceException {
		
		List<GetInferredDataModel> listInferredModel = new ArrayList<GetInferredDataModel>();
		
		for (Inferred inferred : listInferred) {
			listInferredModel.add(mapper.INSTANCE.inferredToInferredModel(inferred));
		}
		
		return listInferredModel;
	}
}