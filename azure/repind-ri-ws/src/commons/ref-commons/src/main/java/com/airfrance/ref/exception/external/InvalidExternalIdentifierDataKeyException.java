package com.airfrance.ref.exception.external;


public class InvalidExternalIdentifierDataKeyException extends AbstractExternalIdentifierException {

	private static final long serialVersionUID = -6814058081869914921L;

	private static final String ERROR_MESSAGE = "Invalid external identifier key value";
	
	public InvalidExternalIdentifierDataKeyException(String key) {
		super(ERROR_MESSAGE, key);
	}
	
}
