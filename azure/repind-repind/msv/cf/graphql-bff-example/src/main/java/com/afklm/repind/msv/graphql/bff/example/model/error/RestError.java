package com.afklm.repind.msv.graphql.bff.example.model.error;

import lombok.ToString;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

/**
 * Part of Full rest error
 *
 */

@ToString
public class RestError implements Serializable {

	private  String code;
	private  String name;
	private  String description;
	private  ErrorType severity;

	private  String status;

	private HttpStatus httpStatus;

	/**
	 * Default constructor from
	 *
	 * @param code  error code
	 * @param name error name
	 * @param description error description
	 * @param severity  error severity
	 */
	public RestError(final String code, final String name, final String description, final ErrorType severity) {
		this.code = code;
		this.name = name;
		this.description = description;
		this.severity = severity;

	}

	public RestError() {
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public ErrorType getSeverity() {
		return severity;
	}

	public void setSeverity(ErrorType severity) {
		this.severity = severity;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

	public void setHttpStatus(HttpStatus httpStatus) {
		this.httpStatus = httpStatus;
	}



	public RestError setDescription(final String description) {
		this.description = description;
		return this;
	}
}
