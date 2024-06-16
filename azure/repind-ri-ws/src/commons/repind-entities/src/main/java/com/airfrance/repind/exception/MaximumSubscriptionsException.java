package com.airfrance.repind.exception;

import com.airfrance.ref.exception.jraf.JrafDomainRollbackException;

public class MaximumSubscriptionsException extends JrafDomainRollbackException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3323978683387924578L;

	public MaximumSubscriptionsException(String msg) {
		super(msg);
	}
	
	public MaximumSubscriptionsException(Throwable root) {
		super(root);
	}

	public MaximumSubscriptionsException(String msg, Throwable root) {
		super(msg, root);
	}
}
