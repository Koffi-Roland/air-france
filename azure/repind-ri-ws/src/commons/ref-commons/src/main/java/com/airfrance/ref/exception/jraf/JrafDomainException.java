package com.airfrance.ref.exception.jraf;

/**
 * 
 * <p>Title : JrafDomainException.java</p>
 * <p>Description : Exception de la couche domaine</p>
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
public class JrafDomainException extends JrafException {

	private static final long serialVersionUID = -187185419114121297L;

	public JrafDomainException(String msg) {
		super(msg);
	}
	
	public JrafDomainException(Throwable root) {
            super(root);
	}
	
	public JrafDomainException(String msg, Throwable root) {
		super(msg, root);			
	}

}
