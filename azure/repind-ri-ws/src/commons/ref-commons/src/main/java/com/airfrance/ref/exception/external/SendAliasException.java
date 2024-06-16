package com.airfrance.ref.exception.external;

public class SendAliasException extends AbstractExternalIdentifierException  {

	private static final long serialVersionUID = 7508966978842243067L;
	
	private static final String ERROR_MESSAGE = "Error during storage";
	
	public SendAliasException() {
		super(ERROR_MESSAGE);
	}
	
	public SendAliasException(String msg) {
		super(ERROR_MESSAGE + " : " + msg);
	}
	
}
