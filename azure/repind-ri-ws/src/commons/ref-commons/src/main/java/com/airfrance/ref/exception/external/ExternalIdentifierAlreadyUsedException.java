package com.airfrance.ref.exception.external;

public class ExternalIdentifierAlreadyUsedException extends AbstractExternalIdentifierException  {

	private static final long serialVersionUID = -4454968568924965319L;
	
	private static final String ERROR_MESSAGE = "External identifier already used";
	
	public ExternalIdentifierAlreadyUsedException(String identifier) {
		super(ERROR_MESSAGE, identifier);
	}
	
}
