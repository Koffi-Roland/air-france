package com.afklm.repind.msv.doctor.attributes.utils.exception;

import com.afklm.repind.msv.doctor.attributes.model.error.BusinessError;
import com.afklm.repind.msv.doctor.attributes.model.error.IError;
import com.afklm.repind.msv.doctor.attributes.model.error.RestError;
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
		RestError restError = error.getRestError();
		this.restError = restError;
		this.status = restError.getHttpStatus();
	}

	public RestError getRestError() {
		return restError;
	}

	public HttpStatus getStatus() {
		return status;
	}

}
