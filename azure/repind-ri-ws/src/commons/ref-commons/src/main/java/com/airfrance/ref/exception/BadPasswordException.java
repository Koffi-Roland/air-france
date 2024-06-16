package com.airfrance.ref.exception;

import com.airfrance.ref.exception.jraf.JrafDomainRollbackException;

public class BadPasswordException extends JrafDomainRollbackException {

	private static final long serialVersionUID = -2208033042442441461L;

	private Integer nbFailure;
	
	public BadPasswordException(String arg0, Integer nbFailure) {
		super(arg0);
		this.nbFailure = nbFailure;
	}
	
	public BadPasswordException(Integer nbFailure, Throwable arg0) {
		super(arg0);
		this.nbFailure = nbFailure;
	}

	public BadPasswordException(Integer nbFailure, String arg0, Throwable arg1) {
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
