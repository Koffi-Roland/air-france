package com.airfrance.ref.exception.telecom;

/**
 * Exception Invalid CodeApp ( 'C', 'R').
 * @author t251684
 *
 */
public class InvalErrorCodeAppException extends InvalidationTelecomException {

	/**
	 * Version.
	 */
	private static final long serialVersionUID = -51959087279295047L;

	public InvalErrorCodeAppException(String msg) {
		super("Error Code Application : " + msg);
	}

}
