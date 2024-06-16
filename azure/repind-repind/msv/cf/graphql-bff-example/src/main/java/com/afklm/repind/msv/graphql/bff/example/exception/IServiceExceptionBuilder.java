package com.afklm.repind.msv.graphql.bff.example.exception;

import com.afklm.repind.msv.graphql.bff.example.model.error.RestError;
import org.springframework.http.HttpStatus;

/**
 * IServiceExceptionBuilder
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
