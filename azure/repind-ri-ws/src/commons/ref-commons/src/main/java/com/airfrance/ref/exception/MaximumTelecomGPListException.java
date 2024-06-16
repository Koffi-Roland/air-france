package com.airfrance.ref.exception;

import com.airfrance.ref.exception.jraf.JrafDomainRollbackException;

public class MaximumTelecomGPListException extends JrafDomainRollbackException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1474556749039986734L;

	public MaximumTelecomGPListException(String msg) {
		super(msg);
	}
	
	public MaximumTelecomGPListException(Throwable root) {
		super(root);
	}

	public MaximumTelecomGPListException(String msg, Throwable root) {
		super(msg, root);
	}
}