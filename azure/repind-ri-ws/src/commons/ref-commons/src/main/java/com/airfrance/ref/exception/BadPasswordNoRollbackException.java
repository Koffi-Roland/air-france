package com.airfrance.ref.exception;

import com.airfrance.ref.exception.jraf.JrafDomainNoRollbackException;

public class BadPasswordNoRollbackException extends JrafDomainNoRollbackException {
	private static final long serialVersionUID = -1921139985246855603L;
	
	private Integer nbFailure;
	
	public BadPasswordNoRollbackException(String arg0, Integer nbFailure) {
		super(arg0);
		this.nbFailure = nbFailure;
	}
	
	public BadPasswordNoRollbackException(Integer nbFailure, Throwable arg0) {
		super(arg0);
		this.nbFailure = nbFailure;
	}

	public BadPasswordNoRollbackException(Integer nbFailure, String arg0, Throwable arg1) {
		super(arg0, arg1);
		this.nbFailure = nbFailure;
	}

	public Integer getNbFailure() {
		return nbFailure;
	}

	public void setNbFailure(Integer nbFailure) {
		this.nbFailure = nbFailure;
	}

}
