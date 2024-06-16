package com.airfrance.ref.exception;


public class TooShortPhoneNumberException extends NormalizedPhoneNumberException {

	private static final long serialVersionUID = -8580280604710949730L;

	private static final String ERROR_MESSAGE = "Too short phone number";
	
	public TooShortPhoneNumberException(String phoneNumber) {
		super(ERROR_MESSAGE, phoneNumber);
	}

}
