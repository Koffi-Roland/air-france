package com.airfrance.ref.exception.telecom;

/**
 * Exception Invalid Telecom : Error Code Trop Court.
 * @author t251684
 *
 */
public class InvalErrorCodeTooShortException extends
		InvalidationTelecomException {

	/**
	 * Version.
	 */
	private static final long serialVersionUID = 947100093726192719L;

	public InvalErrorCodeTooShortException(String msg) {
		super("Error Code Too Short : " + msg);
	}

}
