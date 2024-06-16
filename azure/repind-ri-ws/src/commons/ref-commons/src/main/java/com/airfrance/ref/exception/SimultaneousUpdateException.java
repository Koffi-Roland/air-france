package com.airfrance.ref.exception;


public class SimultaneousUpdateException extends RefException {

	private static final long serialVersionUID = 289038773225917738L;

	private static final String ERROR_MESSAGE = "Simultaneous update";
	
	public SimultaneousUpdateException(String msg) {
		super(ERROR_MESSAGE, msg);
	}

	public SimultaneousUpdateException(Throwable root) {
		super(root);
	}

	public SimultaneousUpdateException(String msg, Throwable root) {
		super(msg, root);
	}

}
