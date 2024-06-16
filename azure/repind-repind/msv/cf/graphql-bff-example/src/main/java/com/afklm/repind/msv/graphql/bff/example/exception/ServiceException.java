package com.afklm.repind.msv.graphql.bff.example.exception;

import com.afklm.repind.msv.graphql.bff.example.model.error.RestError;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * API Exception contents RestError & HttpStatus
 *
 */
@Getter
public class ServiceException extends Exception {

	private static final long serialVersionUID = -8480359077312336045L;

	private final RestError restError;

	private final HttpStatus status;

	/**
	 * Constructor from the Builder
	 * 
	 * @param pBuilder IServiceExceptionBuilder
	 *
	 */
	public ServiceException(final IServiceExceptionBuilder pBuilder) {
		super(pBuilder.getCause());
		final RestError error = pBuilder.getRestError();
		error.setDescription(pBuilder.getDescription());
		restError = error;
		status = pBuilder.getHttpStatus();
	}

	/**
	 * Constructor from a RestError and a Http status
	 * 
	 * @param error RestError
	 * @param status HttpStatus
	 *
	 */
	public ServiceException(final RestError error, final HttpStatus status) {
		restError = error;
		this.status = status;
	}

	/**
	 * Constructor from a RestError and a Http status and the Exception
	 * 
	 * @param error RestError
	 * @param status HttpStatus
	 * @param cause Throwable
	 *
	 */
	public ServiceException(final RestError error, final HttpStatus status, final Throwable cause) {
		super(cause);
		restError = error;
		this.status = status;
	}

}
