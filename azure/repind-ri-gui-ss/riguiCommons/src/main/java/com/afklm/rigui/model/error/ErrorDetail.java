package com.afklm.rigui.model.error;

/**
 * Detail error
 *
 * @author M312812
 *
 */
public class ErrorDetail {

	private String code;
	private String field;
	private String defaultMessage;
	private Object rejectedValue;

	public String getCode() {
		return code;
	}

	public String getDefaultMessage() {
		return defaultMessage;
	}

	public String getField() {
		return field;
	}

	public Object getRejectedValue() {
		return rejectedValue;
	}

	public void setCode(final String code) {
		this.code = code;
	}

	public void setDefaultMessage(final String defaultMessage) {
		this.defaultMessage = defaultMessage;
	}

	public void setField(final String field) {
		this.field = field;
	}

	public void setRejectedValue(final Object rejectedValue) {
		this.rejectedValue = rejectedValue;
	}

}