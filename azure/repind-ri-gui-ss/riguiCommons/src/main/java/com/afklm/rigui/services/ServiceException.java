package com.afklm.rigui.services;

import org.springframework.http.HttpStatus;

import com.afklm.rigui.model.error.RestError;
import com.afklm.rigui.services.exception.IServiceExceptionBuilder;

/**
 * API Exception contents RestError & HttpStatus
 *
 * @author m312812
 *
 */
public class ServiceException extends Exception {

	private static final long serialVersionUID = -8480359077312336045L;

	private final RestError restError;

	private final HttpStatus status;

	/**
	 * Constructor from the Builder
	 * 
	 * @param pBuilder
	 *            IServiceExceptionBuilder
	 */
	public ServiceException(final IServiceExceptionBuilder pBuilder) {
		super(pBuilder.getCause());
		final RestError error = pBuilder.getRestError();
		error.setDescription(pBuilder.getDescription());
		restError = error;
		// setRestError(error);
		// setStatus(pBuilder.getHttpStatus());
		status = pBuilder.getHttpStatus();
	}

	/**
	 * Constructor from a RestError and a Http status
	 * 
	 * @param error
	 *            RestError
	 * @param status
	 *            HttpStatus
	 */
	public ServiceException(final RestError error, final HttpStatus status) {
		restError = error;
		this.status = status;
	}

	/**
	 * Constructor from a RestError and a Http status and the Exception
	 * 
	 * @param error
	 *            RestError
	 * @param status
	 *            HttpStatus
	 * @param cause
	 *            Throwable
	 */
	public ServiceException(final RestError error, final HttpStatus status, final Throwable cause) {
		super(cause);
		restError = error;
		this.status = status;
	}

	public RestError getRestError() {
		return restError;
	}

	public HttpStatus getStatus() {
		return status;
	}

}
