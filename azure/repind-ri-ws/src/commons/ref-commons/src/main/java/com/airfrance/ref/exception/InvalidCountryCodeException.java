package com.airfrance.ref.exception;


public class InvalidCountryCodeException extends NormalizedPhoneNumberException {

	private static final long serialVersionUID = 5302582003021372082L;
	
	private static final String ERROR_MESSAGE = "Invalid country code";
	
	public InvalidCountryCodeException(String countryCode) {
		super(ERROR_MESSAGE, countryCode);
	}
	
}
