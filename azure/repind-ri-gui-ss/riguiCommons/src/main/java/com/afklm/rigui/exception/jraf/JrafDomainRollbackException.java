package com.afklm.rigui.exception.jraf;

public class JrafDomainRollbackException extends JrafDomainException {


	/**
	 * 
	 * <p>Title : JrafDomainRollbackException.java</p>
	 * <p>Description : Exception de la couche domaine</p>
	 * <p>Copyright : Copyright (c) 2009</p>
	 * <p>Company : AIRFRANCE</p>
	 * Exception used when a rollback is necessary
	 */

	private static final long serialVersionUID = 7998543808624078889L;

	public JrafDomainRollbackException(String msg) {
		super(msg);
	}
	
	public JrafDomainRollbackException(Throwable root) {
            super(root);
	}
	
	public JrafDomainRollbackException(String msg, Throwable root) {
		super(msg, root);			
	}

}
