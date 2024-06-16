package com.afklm.rigui.exception;


public class TooLongPhoneNumberException extends NormalizedPhoneNumberException {

	private static final long serialVersionUID = -8580280604710949730L;

	private static final String ERROR_MESSAGE = "Too long phone number";
	
	public TooLongPhoneNumberException(String phoneNumber) {
		super(ERROR_MESSAGE, phoneNumber);
	}
	
}
