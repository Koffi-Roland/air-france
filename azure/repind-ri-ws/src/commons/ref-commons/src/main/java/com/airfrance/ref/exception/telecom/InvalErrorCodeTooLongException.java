package com.airfrance.ref.exception.telecom;

/**
 * Exception Invalid Telecom : Error Code Trop Long.
 * @author t251684
 *
 */
public class InvalErrorCodeTooLongException extends
		InvalidationTelecomException {

	/**
	 * Version.
	 */
	private static final long serialVersionUID = 2284391665372297354L;

	public InvalErrorCodeTooLongException(String msg) {
		super("Error Code Too Long : " + msg);
	}

}
