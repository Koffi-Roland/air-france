package com.afklm.repind.msv.preferences.model.error;

/**
 * Error Type for an API
 *
 * @author m312812
 *
 */
public enum ErrorType {

	ERROR("ERROR"), WARNING("WARNING");

	

	private String type;

	private ErrorType(final String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}

}
