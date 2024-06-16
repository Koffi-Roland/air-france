package com.afklm.rigui.exception;


public class InvalidPhoneNumberException extends NormalizedPhoneNumberException {

	private static final long serialVersionUID = 5302582003021372082L;
	
	private static final String ERROR_MESSAGE = "Invalid phone number";
	
	public InvalidPhoneNumberException(String phoneNumber) {
		super(ERROR_MESSAGE, phoneNumber);
	}
	
}
