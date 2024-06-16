package com.afklm.repind.msv.preferences.services.exception;

import com.afklm.repind.msv.preferences.model.error.RestError;
import com.afklm.repind.msv.preferences.model.error.SystemErrorList;
import org.springframework.http.HttpStatus;
/**
 * ExceptionServiceExceptionBuilder
 *
 * @author m312812
 *
 */
public class ExceptionServiceExceptionBuilder extends AbstractServiceExceptionBuilder {

	/**
	 * Contructor from an Exception
	 * 
	 * @param exception
	 *            Exception
	 */
	public ExceptionServiceExceptionBuilder(final Exception exception) {
		super(exception);
	}

	@Override
	public HttpStatus getHttpStatus() {
		return HttpStatus.INTERNAL_SERVER_ERROR;
	}

	@Override
	public RestError getRestError() {
		return SystemErrorList.API_SYSTEM_UNKNOW_ERROR.getError();
	}

}
