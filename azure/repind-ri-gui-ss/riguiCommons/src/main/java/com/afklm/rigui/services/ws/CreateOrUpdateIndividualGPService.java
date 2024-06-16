package com.afklm.rigui.services.ws;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.afklm.rigui.model.error.RestError;
import com.afklm.rigui.services.ServiceException;
import com.afklm.rigui.services.handler.W002008BusinessErrorHandler;
import com.afklm.soa.stubs.common.systemfault.v1.SystemException;
import com.afklm.soa.stubs.w002008.v1.BusinessErrorBlocBusinessException;
import com.afklm.soa.stubs.w002008.v1.CreateUpdateIndividualGPServiceV1;
import com.afklm.soa.stubs.w002008.v1.data.CreateUpdateIndividualGPRequest;
import com.afklm.soa.stubs.w002008.v1.data.CreateUpdateIndividualGPResponse;

@Service
public class CreateOrUpdateIndividualGPService {
	
	@Autowired
	@Qualifier("consumerW002008v01")
	private CreateUpdateIndividualGPServiceV1 createUpdateIndividualGPService;
	
	public boolean callWebService(CreateUpdateIndividualGPRequest request) throws ServiceException, SystemException {
		
		CreateUpdateIndividualGPResponse webServiceResponse;
		
		try {
			webServiceResponse = createUpdateIndividualGPService.manageIndividualGP(request);
		} catch (BusinessErrorBlocBusinessException e) {
			RestError restError = W002008BusinessErrorHandler.handleBusinessErrorException(e);
			throw new ServiceException(restError, HttpStatus.BAD_REQUEST);
		}
		
		return webServiceResponse != null;
		
	}

}
