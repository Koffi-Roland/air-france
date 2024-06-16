package com.airfrance.ref.exception;

import com.airfrance.ref.exception.jraf.JrafDomainRollbackException;

public class MaximumAddressNumberException extends JrafDomainRollbackException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1975291735827357644L;

	/**
	 * 
	 */

	public MaximumAddressNumberException(String msg) {
		super(msg);
	}
	
	public MaximumAddressNumberException(Throwable root) {
		super(root);
	}

	public MaximumAddressNumberException(String msg, Throwable root) {
		super(msg, root);
	}
}