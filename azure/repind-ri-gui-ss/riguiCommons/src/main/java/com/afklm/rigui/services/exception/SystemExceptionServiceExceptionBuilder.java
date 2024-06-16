package com.afklm.rigui.services.exception;

import org.springframework.http.HttpStatus;

import com.afklm.rigui.model.error.RestError;
import com.afklm.rigui.model.error.SystemErrorList;
import com.afklm.soa.stubs.common.systemfault.v1.SystemException;

/**
 * SystemExceptionServiceExceptionBuilder
 *
 * @author m312812
 *
 */
public class SystemExceptionServiceExceptionBuilder extends BusinessExceptionServiceExceptionBuilder {

	/**
	 * constructor from a SystemException
	 * 
	 * @param systemException
	 */
	public SystemExceptionServiceExceptionBuilder(final SystemException systemException) {
		super(systemException);
	}

	@Override
	protected String getFaultCode() {
		return ((SystemException) getCause()).getFaultInfo().getErrorCode();
	}

	@Override
	public HttpStatus getHttpStatus() {
		return HttpStatus.INTERNAL_SERVER_ERROR;
	}

	@Override
	public RestError getRestError() {
		return SystemErrorList.API_SYSTEM_FAULT.getError();
	}

}
