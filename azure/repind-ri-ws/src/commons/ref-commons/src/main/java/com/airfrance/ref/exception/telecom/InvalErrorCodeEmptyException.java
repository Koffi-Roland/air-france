package com.airfrance.ref.exception.telecom;

/**
 * Exception : Invalid Error Code : Empty.
 * @author t251684
 *
 */
public class InvalErrorCodeEmptyException extends
		InvalidationTelecomException {

	/**
	 * Version.
	 */
	private static final long serialVersionUID = 4060284655660817981L;

	public InvalErrorCodeEmptyException(String msg) {
		super("Error Code Empty : " + msg);
	}

}
