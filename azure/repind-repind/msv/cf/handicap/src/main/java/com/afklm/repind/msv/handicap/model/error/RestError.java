package com.afklm.repind.msv.handicap.model.error;

/**
 * Part of Full rest error
 *
 * @author M312812
 *
 */
public class RestError {

	private final String code;
	private final String name;
	private String description;
	private final ErrorType severity;

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

	public String getCode() {
		return code;
	}

	public String getDescription() {
		return description;
	}

	public String getName() {
		return name;
	}

	public ErrorType getSeverity() {
		return severity;
	}

	public RestError setDescription(final String description) {
		this.description = description;
		return this;
	}

}