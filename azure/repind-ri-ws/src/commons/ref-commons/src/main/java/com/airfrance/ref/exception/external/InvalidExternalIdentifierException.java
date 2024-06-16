package com.airfrance.ref.exception.external;

public class InvalidExternalIdentifierException extends AbstractExternalIdentifierException  {

	private static final long serialVersionUID = -4454968567924965319L;
	
	private static final String ERROR_MESSAGE = "Invalid external identifier";
	
	public InvalidExternalIdentifierException(String identifier) {
		super(ERROR_MESSAGE, identifier);
	}
	
}
