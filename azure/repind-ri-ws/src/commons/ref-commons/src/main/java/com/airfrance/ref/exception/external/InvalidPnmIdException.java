package com.airfrance.ref.exception.external;

public class InvalidPnmIdException extends AbstractExternalIdentifierException {

	private static final long serialVersionUID = -1605571051892112625L;

	private static final String ERROR_MESSAGE = "Invalid PNM ID";
	
	public InvalidPnmIdException(String pnmId) {
		super(ERROR_MESSAGE, pnmId);
	}
	
}
