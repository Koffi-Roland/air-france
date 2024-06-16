package com.airfrance.ref.exception.telecom;

import com.airfrance.ref.exception.jraf.JrafDomainException;

/**
 * Exception relative à l'invalidation Telecom.
 * @author t251684
 *
 */
public class InvalidationTelecomException extends JrafDomainException {

	/**
	 * Version.
	 */
	private static final long serialVersionUID = -3152259672366478980L;

	public InvalidationTelecomException(String msg) {
		super(msg);
	}

}
