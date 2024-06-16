package com.afklm.repind.msv.preferences.services.builder;

import com.afklm.repind.msv.preferences.model.error.ErrorType;
import com.afklm.repind.msv.preferences.model.error.RestError;
import com.afklm.soa.stubs.common.systemfault.v1.SystemException;
import com.afklm.soa.stubs.common.systemfault.v1.SystemFault;
import com.afklm.soa.stubs.w000442.v8_0_1.BusinessErrorBlocBusinessException;
import com.afklm.soa.stubs.w000442.v8_0_1.xsd9.BusinessError;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class W000442BusinessErrorHandler {

	/**
	 * Manage the exception that can be raised by web services
	 * @param exception (BusinessErrorBlocBusinessException)
	 * @return the rest error corresponding to the business error in the exception
	 */
	public static RestError handleBusinessErrorException(BusinessErrorBlocBusinessException exception) {
		BusinessError businessError = exception.getFaultInfo().getBusinessError();
		if(businessError == null) return null;
		RestError restError = handleBusinessError(businessError);
		log.error(restError.toString());
		return restError;
	}
	
	/**
	 * Manage the exception that can be raised by web services
	 * @param exception (BusinessErrorBlocBusinessException)
	 * @return the rest error corresponding to the business error in the exception
	 */
	public static RestError handleSystemException(SystemException exception) {
		SystemFault systemError = exception.getFaultInfo();
		RestError restError = handleSystemError(systemError);
		log.error(restError.toString());
		return restError;
	}

	/**
	 * Handle the business error sent by the web service
	 * @param businessError (BusinessError)
	 * @return an object RestError
	 */
	public static RestError handleBusinessError(BusinessError businessError) {
		RestError restError;
		if(businessError == null) return null;
		String errorCode = computeRestErrorCode(businessError);
		String errorLabel = businessError.getErrorLabel();
		String errorDetails = businessError.getErrorDetail();
		restError = new RestError(errorCode, errorLabel, errorDetails, ErrorType.ERROR);
		return restError;
	}

	/**
	 * Handle the business error sent by the web service
	 * @param systemError (SystemFault)
	 * @return an object RestError
	 */
	public static RestError handleSystemError(SystemFault systemError) {
		RestError restError;
		String errorCode = systemError.getErrorCode();
		String errorLabel = systemError.getFaultDescription();
		String errorDetails = "";
		restError = new RestError(errorCode, errorLabel, errorDetails, ErrorType.ERROR);
		return restError;
	}
	
	/**
	 * Compute the rest error code according to the business error in parameter
	 * @param businessError (BusinessError)
	 * @return a String object that represent the business code (business.errorCode.webServiceID.businessErrorCode)
	 */
	private static String computeRestErrorCode(BusinessError businessError) {
		if (businessError.getErrorCode() == null) return getRestErrorCode(-1);
		String errorCodeStr = (businessError.getOtherErrorCode() == null) ? businessError.getErrorCode().value() : businessError.getOtherErrorCode();
		int errorCode = getErrorCode(errorCodeStr);
		return getRestErrorCode(errorCode);
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
		if (!errorCodeStr.contains("_")) return -1;
		String[] splittedErrorCodeStr = errorCodeStr.split("_");
		String codeStr = splittedErrorCodeStr[1];
		return Integer.parseInt(codeStr);
	}

}
