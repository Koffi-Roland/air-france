package com.airfrance.ref.exception;

import com.airfrance.ref.exception.jraf.JrafDomainRollbackException;

public class MaximumRoleGPListException extends JrafDomainRollbackException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3301784196054749075L;

	public MaximumRoleGPListException(String msg) {
		super(msg);
	}
	
	public MaximumRoleGPListException(Throwable root) {
		super(root);
	}

	public MaximumRoleGPListException(String msg, Throwable root) {
		super(msg, root);
	}
}