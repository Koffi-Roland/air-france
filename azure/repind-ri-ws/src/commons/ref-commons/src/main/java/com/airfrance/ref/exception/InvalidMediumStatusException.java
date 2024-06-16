package com.airfrance.ref.exception;

public class InvalidMediumStatusException extends RefException {
	
	private static final long serialVersionUID = 8839879195357642416L;

	public InvalidMediumStatusException(String msg) {
		super(msg);
	}
	
	public InvalidMediumStatusException(Throwable root) {
		super(root);
	}

	public InvalidMediumStatusException(String msg, Throwable root) {
		super(msg, root);
	}
}