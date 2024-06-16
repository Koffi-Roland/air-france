package com.afklm.rigui.services.exception;

import org.springframework.http.HttpStatus;

import com.afklm.rigui.model.error.RestError;

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
