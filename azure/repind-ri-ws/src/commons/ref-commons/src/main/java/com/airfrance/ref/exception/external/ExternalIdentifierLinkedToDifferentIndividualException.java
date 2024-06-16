package com.airfrance.ref.exception.external;

public class ExternalIdentifierLinkedToDifferentIndividualException extends AbstractExternalIdentifierException  {

	private static final long serialVersionUID = -4454968569924965319L;
	
	private static final String ERROR_MESSAGE = "External identifier linked to different individual";
	
	public ExternalIdentifierLinkedToDifferentIndividualException(String identifier) {
		super(ERROR_MESSAGE, identifier);
	}
	
}
