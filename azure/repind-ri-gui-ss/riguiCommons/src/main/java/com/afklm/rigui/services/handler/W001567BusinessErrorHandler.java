package com.afklm.rigui.services.handler;

import org.springframework.stereotype.Component;

@Component
public class W001567BusinessErrorHandler {
	
	/**
	 * Manage the exception that can be raised by web services
	 * @param ws (WebService)
	 * @param exception (BusinessErrorBlocBusinessException)
	 * @return the rest error corresponding to the business error in the exception
	 */
	/*public static RestError handleBusinessErrorException(BusinessErrorBlocBusinessException exception) {
		BusinessError businessError = exception.getFaultInfo().getBusinessError();
		RestError restError = handleBusinessError(businessError);
		LOGGER.error(restError.toString());
		return restError;
	}*/
	
	/**
	 * Handle the business error sent by the web service
	 * @param webService (WebService)
	 * @param businessError (BusinessError)
	 * @return an object RestError
	 */
	/*public static RestError handleBusinessError(BusinessError businessError) {
		RestError restError = null;
		String errorCode = computeRestErrorCode(businessError);
		String errorLabel = businessError.getErrorLabel();
		String errorDetails = businessError.getErrorDetail();
		restError = new RestError(errorCode, errorLabel, errorDetails, ErrorType.ERROR);
		return restError;
	}*/
	
	/**
	 * Compute the rest error code according to the business error in parameter
	 * @param webService (WebService)
	 * @param businessError (BusinessError)
	 * @return a String object that represent the business code (business.errorCode.webServiceID.businessErrorCode)
	 */
	/*private static String computeRestErrorCode(BusinessError businessError) {
		if (businessError == null) return null;
		String errorCodeStr = (businessError.getOtherErrorCode() == null) ? businessError.getErrorCode().value() : businessError.getOtherErrorCode();
		int errorCode = getErrorCode(errorCodeStr);
		String restErrorCode = getRestErrorCode(errorCode);
		return restErrorCode;
	}*/
	
	/**
	 * Concatenate the web service code prefix and the code of the business error
	 * @param webService (WebService)
	 * @param code (integer)
	 * @return
	 */
	/*private static String getRestErrorCode(int code) {
		String restErrorCodeStr = "";
		restErrorCodeStr += WebService.CREATE_OR_UPDATE_CONTRACT.getCodePrefix();
		restErrorCodeStr += code;
		return restErrorCodeStr;
	}*/
	
	/**
	 * Parse the business error code to get just the code without "ERROR_"
	 * @param errorCodeStr
	 * @return
	 */
	/*private static int getErrorCode(String errorCodeStr) {
		if (errorCodeStr == null || !errorCodeStr.contains("_")) return -1;
		String[] splittedErrorCodeStr = errorCodeStr.split("_");
		String codeStr = splittedErrorCodeStr[1];
		return Integer.parseInt(codeStr);
	}*/

}
