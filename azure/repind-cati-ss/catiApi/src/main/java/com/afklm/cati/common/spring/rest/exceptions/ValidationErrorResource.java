package com.afklm.cati.common.spring.rest.exceptions;

import java.io.Serializable;

public class ValidationErrorResource implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 125342341069367292L;
	
	private String field;
	private String message;
	
	public String getField() {
		return field;
	}
	public void setField(String field) {
		this.field = field;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
}
