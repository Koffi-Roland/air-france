package com.afklm.rigui.model.error;

import org.springframework.http.HttpStatus;

import java.io.Serializable;

/**
 * Part of Full rest error
 *
 * @author M312812
 *
 */
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
	 * @param code
	 * @param name
	 * @param description
	 * @param severity
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

	@Override
	public String toString() {
		String str = "Rest error information: \n";
		str += "- Code: " + this.getCode() + "\n";
		str += "- Label: " + this.getName() + "\n";
		str += "- Description: " + this.getDescription() + "\n";
		return str;
	}

	public RestError setDescription(final String description) {
		this.description = description;
		return this;
	}
}
