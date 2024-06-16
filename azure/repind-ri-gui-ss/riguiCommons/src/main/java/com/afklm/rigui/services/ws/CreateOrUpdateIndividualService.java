package com.afklm.rigui.services.ws;

import com.afklm.soa.stubs.w000442.v8_0_1.BusinessErrorBlocBusinessException;
import com.afklm.soa.stubs.w000442.v8_0_1.CreateUpdateIndividualServiceV8;
import com.afklm.soa.stubs.w000442.v8_0_1.xsd4.CreateUpdateIndividualRequest;
import com.afklm.soa.stubs.w000442.v8_0_1.xsd4.CreateUpdateIndividualResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.afklm.rigui.model.error.RestError;
import com.afklm.rigui.services.ServiceException;
import com.afklm.rigui.services.handler.W000442BusinessErrorHandler;
import com.afklm.soa.stubs.common.systemfault.v1.SystemException;

@Service
public class CreateOrUpdateIndividualService {
	
	@Autowired
	@Qualifier("consumerW000442v8")
	private CreateUpdateIndividualServiceV8 createOrUpdateService;
	
	/**
	 * This method is used to call the web service (w000442) and returns a boolean saying 
	 * if the action was successfully done or not.
	 * @param request - a CreateUpdateIndividualRequest
	 * @return a boolean
	 * @throws SystemException
	 * @throws ServiceException
	 */
	public boolean callWebService(CreateUpdateIndividualRequest request) throws SystemException, ServiceException {
		
		CreateUpdateIndividualResponse webServiceResponse = null;
		
		try {
			webServiceResponse = createOrUpdateService.createIndividual(request);
		} catch (BusinessErrorBlocBusinessException e) {
			RestError restError = W000442BusinessErrorHandler.handleBusinessErrorException(e);
			throw new ServiceException(restError, HttpStatus.BAD_REQUEST);
		}
		
		if (webServiceResponse == null) {
			return false;
		} else {
			return webServiceResponse.isSuccess();
		}
		
	}

}
