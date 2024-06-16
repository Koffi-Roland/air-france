package com.afklm.rigui.services.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.afklm.rigui.model.error.ErrorType;
import com.afklm.rigui.model.error.RestError;
import com.afklm.soa.stubs.w001271.v2.BusinessErrorBlocBusinessException;
import com.afklm.soa.stubs.w001271.v2.response.BusinessError;

@Component
public class W001271BusinessErrorHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(W001271BusinessErrorHandler.class);
	
	/**
	 * Manage the exception that can be raised by web services
	 * @param ws (WebService)
	 * @param exception (BusinessErrorBlocBusinessException)
	 * @return the rest error corresponding to the business error in the exception
	 */
	public static RestError handleBusinessErrorException(BusinessErrorBlocBusinessException exception) {
		BusinessError businessError = exception.getFaultInfo().getBusinessError();
		RestError restError = handleBusinessError(businessError);
		LOGGER.error(restError.toString());
		return restError;
	}
	
	/**
	 * Handle the business error sent by the web service
	 * @param webService (WebService)
	 * @param businessError (BusinessError)
	 * @return an object RestError
	 */
	public static RestError handleBusinessError(BusinessError businessError) {
		RestError restError = null;
		String errorCode = computeRestErrorCode(businessError);
		String errorLabel = businessError.getErrorLabel();
		String errorDetails = businessError.getErrorDetail();
		restError = new RestError(errorCode, errorLabel, errorDetails, ErrorType.ERROR);
		return restError;
	}
	
	/**
	 * Compute the rest error code according to the business error in parameter
	 * @param webService (WebService)
	 * @param businessError (BusinessError)
	 * @return a String object that represent the business code (business.errorCode.webServiceID.businessErrorCode)
	 */
	private static String computeRestErrorCode(BusinessError businessError) {
		if (businessError == null) return null;
		int errorCode = getErrorCode(businessError.getErrorCode().value());
		String restErrorCode = getRestErrorCode(errorCode);
		return restErrorCode;
	}
	
	/**
	 * Concatenate the web service code prefix and the code of the business error
	 * @param webService (WebService)
	 * @param code (integer)
	 * @return
	 */
	private static String getRestErrorCode(int code) {
		String restErrorCodeStr = "";
		restErrorCodeStr += WebService.SEARCH_INDIVIDUAL_BY_MULTICRITERIA.getCodePrefix();
		restErrorCodeStr += code;
		return restErrorCodeStr;
	}
	
	/**
	 * Parse the business error code to get just the code without "ERROR_"
	 * @param errorCodeStr
	 * @return
	 */
	private static int getErrorCode(String errorCodeStr) {
		if (errorCodeStr == null || !errorCodeStr.contains("_")) return -1;
		String[] splittedErrorCodeStr = errorCodeStr.split("_");
		String codeStr = splittedErrorCodeStr[1];
		return Integer.parseInt(codeStr);
	}
	
}
