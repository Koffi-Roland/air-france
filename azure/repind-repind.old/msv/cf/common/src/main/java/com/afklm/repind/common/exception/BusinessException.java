package com.afklm.repind.common.exception;

import com.afklm.repind.common.model.error.IError;
import com.afklm.repind.common.model.error.RestError;
import org.springframework.http.HttpStatus;

/**
 * API Exception contents RestError & HttpStatus
 *
 * @author m312812
 *
 */
public class BusinessException extends Exception {

	private static final long serialVersionUID = -8480359077312336045L;

	private final RestError restError;

	private final HttpStatus status;

	/**
	 * Constructor from a RestError and a Http status
	 *
	 * @param error
	 *            RestError
	 */
	public BusinessException(final IError error) {
		super(error.getRestError().getDescription());
		this.restError = error.getRestError();
		this.status = restError.getHttpStatus();
	}

	/**
	 * Constructor from a RestError and a Http status
	 *
	 * @param error
	 *            RestError
	 */
	public BusinessException(final IError error , Throwable iException) {
		super(error.getRestError().getDescription() , iException);
		this.restError = error.getRestError();
		this.status = restError.getHttpStatus();
	}

	public RestError getRestError() {
		return restError;
	}

	public HttpStatus getStatus() {
		return status;
	}

}
