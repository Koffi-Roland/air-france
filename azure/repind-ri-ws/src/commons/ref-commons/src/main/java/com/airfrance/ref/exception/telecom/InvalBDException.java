package com.airfrance.ref.exception.telecom;

public class InvalBDException extends InvalidationTelecomException {

	/**
	 * Version.
	 */
	private static final long serialVersionUID = 4996707229537488472L;

	public InvalBDException(String msg) {
		super("Error Unable to invalidate in DB : " + msg);

	}

}
