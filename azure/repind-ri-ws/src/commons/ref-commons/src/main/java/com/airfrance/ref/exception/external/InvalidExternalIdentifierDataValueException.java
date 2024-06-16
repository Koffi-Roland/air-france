package com.airfrance.ref.exception.external;


public class InvalidExternalIdentifierDataValueException extends AbstractExternalIdentifierException {

	private static final long serialVersionUID = 4402383892567216453L;

	private static final String ERROR_MESSAGE = "Invalid external identifier data value";
	
	public InvalidExternalIdentifierDataValueException(String value) {
		super(ERROR_MESSAGE, value);
	}
	
}
