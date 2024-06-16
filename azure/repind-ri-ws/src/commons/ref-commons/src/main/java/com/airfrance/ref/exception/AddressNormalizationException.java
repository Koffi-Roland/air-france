package com.airfrance.ref.exception;

public class AddressNormalizationException extends RefException {

	private static final long serialVersionUID = -7784882811802508965L;
	
	private static final String ERROR_MESSAGE = "Unable to normalize postal address";
	
	public AddressNormalizationException(String msg) {
		super(ERROR_MESSAGE, msg);
	}

	public AddressNormalizationException(Throwable root) {
		super(root);
	}

	public AddressNormalizationException(String msg, Throwable root) {
		super(msg, root);
	}

}