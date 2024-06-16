package com.airfrance.ref.exception.external;

public class InvalidExternalIdentifierTypeException extends AbstractExternalIdentifierException {

	private static final long serialVersionUID = 3647548405196476650L;
	
	private static final String LOG_MESSAGE = "Invalid external identifier data key";
	
	public InvalidExternalIdentifierTypeException(String type) {
		super(LOG_MESSAGE, type);
	}
	
}
