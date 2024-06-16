package com.airfrance.ref.exception;

import com.airfrance.ref.exception.jraf.JrafDomainRollbackException;

public class MaximumEmailGPListException extends JrafDomainRollbackException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1148325234439734927L;

	public MaximumEmailGPListException(String msg) {
		super(msg);
	}
	
	public MaximumEmailGPListException(Throwable root) {
		super(root);
	}

	public MaximumEmailGPListException(String msg, Throwable root) {
		super(msg, root);
	}
}