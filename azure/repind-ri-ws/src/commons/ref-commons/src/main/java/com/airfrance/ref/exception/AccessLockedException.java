package com.airfrance.ref.exception;

import com.airfrance.ref.exception.jraf.JrafDomainRollbackException;

public class AccessLockedException extends JrafDomainRollbackException {

	private static final long serialVersionUID = -8657627578076392034L;

	public AccessLockedException(String msg) {
		super(msg);
	}
	
	public AccessLockedException(Throwable root) {
		super(root);
	}

	public AccessLockedException(String msg, Throwable root) {
		super(msg, root);
	}
}
