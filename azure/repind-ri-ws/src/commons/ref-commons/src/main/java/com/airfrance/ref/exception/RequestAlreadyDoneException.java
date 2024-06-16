package com.airfrance.ref.exception;

public class RequestAlreadyDoneException extends RefException {
	
	private static final long serialVersionUID = -2106759691425042895L;
	private static final String ERROR_MESSAGE = "Request to be forgotten already done";
	
	public RequestAlreadyDoneException(String msg) {
		super(ERROR_MESSAGE, msg);
	}

	public RequestAlreadyDoneException(Throwable root) {
		super(root);
	}

	public RequestAlreadyDoneException(String msg, Throwable root) {
		super(msg, root);
	}
}