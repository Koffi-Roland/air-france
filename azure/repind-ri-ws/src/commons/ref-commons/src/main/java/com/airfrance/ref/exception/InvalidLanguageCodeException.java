package com.airfrance.ref.exception;


public class InvalidLanguageCodeException extends RefException {

	private static final long serialVersionUID = -7460139991604355509L;

	private static final String ERROR_MESSAGE = "Invalid language code";
	
	public InvalidLanguageCodeException(String languageCode) {
		super(ERROR_MESSAGE, languageCode);
	}
	
}
