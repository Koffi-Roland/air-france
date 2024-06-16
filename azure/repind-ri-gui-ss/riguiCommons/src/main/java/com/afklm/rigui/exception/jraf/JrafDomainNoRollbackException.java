package com.afklm.rigui.exception.jraf;

public class JrafDomainNoRollbackException extends JrafDomainException {


	private static final long serialVersionUID = 570981665592656918L;

	/**
	 * 
	 * <p>Title : JrafDomainNoRollbackException.java</p>
	 * <p>Description : Exception de la couche domaine</p>
	 * <p>Copyright : Copyright (c) 2009</p>
	 * <p>Company : AIRFRANCE</p>
	 * Exception used when a rollback is not necessary
	 */


	public JrafDomainNoRollbackException(String msg) {
		super(msg);
	}
	
	public JrafDomainNoRollbackException(Throwable root) {
            super(root);
	}
	
	public JrafDomainNoRollbackException(String msg, Throwable root) {
		super(msg, root);			
	}

}
