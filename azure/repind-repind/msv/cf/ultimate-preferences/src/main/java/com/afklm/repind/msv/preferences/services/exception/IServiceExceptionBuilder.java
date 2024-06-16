package com.afklm.repind.msv.preferences.services.exception;

import com.afklm.repind.msv.preferences.model.error.RestError;
import org.springframework.http.HttpStatus;

/**
 * IServiceExceptionBuilder
 *
 * @author m312812
 *
 */
public interface IServiceExceptionBuilder {

	/**
	 * Get the cause
	 * 
	 * @return Throwable
	 */
	public Throwable getCause();

	/**
	 * Get the description
	 * 
	 * @return the description
	 */
	public String getDescription();

	/**
	 * Get http status
	 * 
	 * @return HttpStatus
	 */
	public HttpStatus getHttpStatus();

	/**
	 * Get the Rest Error
	 * 
	 * @return RestError
	 */
	public RestError getRestError();
}
