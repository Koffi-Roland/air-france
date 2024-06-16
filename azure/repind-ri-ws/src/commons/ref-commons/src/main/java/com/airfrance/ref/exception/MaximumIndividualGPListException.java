package com.airfrance.ref.exception;

import com.airfrance.ref.exception.jraf.JrafDomainRollbackException;

public class MaximumIndividualGPListException extends JrafDomainRollbackException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8959582253971364793L;

	public MaximumIndividualGPListException(String msg) {
		super(msg);
	}
	
	public MaximumIndividualGPListException(Throwable root) {
		super(root);
	}

	public MaximumIndividualGPListException(String msg, Throwable root) {
		super(msg, root);
	}
}