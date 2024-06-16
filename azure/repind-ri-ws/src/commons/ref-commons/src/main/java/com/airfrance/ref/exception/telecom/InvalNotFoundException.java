package com.airfrance.ref.exception.telecom;

public class InvalNotFoundException extends InvalidationTelecomException {

	/**
	 * Version.
	 */
	private static final long serialVersionUID = -1102624387981705646L;

	public InvalNotFoundException(String msg) {
		super("Error Not Found : " + msg);
	}

}
