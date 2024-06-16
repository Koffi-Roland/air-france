package com.afklm.rigui.services.handler;

import com.afklm.soa.stubs.w000442.v8_0_1.BusinessErrorBlocBusinessException;
import com.afklm.soa.stubs.w000442.v8_0_1.xsd1.BusinessError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.afklm.rigui.model.error.ErrorType;
import com.afklm.rigui.model.error.RestError;

@Component
public class W000442BusinessErrorHandler {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(W000442BusinessErrorHandler.class);
	
	/**
	 * Manage the exception that can be raised by web services
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
	 * @param businessError (BusinessError)
	 * @return a String object that represent the business code (business.errorCode.webServiceID.businessErrorCode)
	 */
	private static String computeRestErrorCode(BusinessError businessError) {
		if (businessError == null) return null;
		String errorCodeStr = (businessError.getOtherErrorCode() == null) ? businessError.getErrorCode().value() : businessError.getOtherErrorCode();
		int errorCode = getErrorCode(errorCodeStr);
		String restErrorCode = getRestErrorCode(errorCode);
		return restErrorCode;
	}
	
	/**
	 * Concatenate the web service code prefix and the code of the business error
	 * @param code (integer)
	 * @return
	 */
	private static String getRestErrorCode(int code) {
		String restErrorCodeStr = "";
		restErrorCodeStr += WebService.CREATE_OR_UPDATE_INDIVIDUAL.getCodePrefix();
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
