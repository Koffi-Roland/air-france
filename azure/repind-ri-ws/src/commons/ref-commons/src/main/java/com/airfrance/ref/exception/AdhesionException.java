package com.airfrance.ref.exception;

import com.airfrance.ref.exception.jraf.JrafDomainRollbackException;

public class AdhesionException extends JrafDomainRollbackException {

	private static final long serialVersionUID = 9135968408044233779L;
	private String codeErreur; 

	public String getCodeErreur() {
		return codeErreur;
	}

	public void setCodeErreur(String codeErreur) {
		this.codeErreur = codeErreur;
	}

	public AdhesionException(String msg) {
		super(msg);
	}
	
	public AdhesionException(Throwable root) {
		super(root);
	}

	public AdhesionException(String msg, Throwable root) {
		super(msg, root);
	}
}
