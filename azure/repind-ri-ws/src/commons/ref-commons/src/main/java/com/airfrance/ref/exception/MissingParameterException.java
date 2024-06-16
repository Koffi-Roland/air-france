package com.airfrance.ref.exception;


public class MissingParameterException extends RefException {

	private static final long serialVersionUID = -1432337699035001046L;

	private static final String ERROR_MESSAGE = "Missing parameter exception";
	
	public MissingParameterException(String msg) {
		super(ERROR_MESSAGE, msg);
	}

	public MissingParameterException(Throwable root) {
		super(root);
	}

	public MissingParameterException(String msg, Throwable root) {
		super(msg, root);
	}
	
}
