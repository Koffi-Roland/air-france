package com.airfrance.ref.exception;


public class NormalizedPhoneNumberException extends RefException {

	private static final long serialVersionUID = 1351960823577812516L;
	
	private static final String ERROR_MESSAGE = "Not normalized phone number exception";
	
	public NormalizedPhoneNumberException(String msg) {
		super(ERROR_MESSAGE, msg);
	}
	
	public NormalizedPhoneNumberException(String errorMsg, String errorVal) {
		super(errorMsg, errorVal);
	}

}
