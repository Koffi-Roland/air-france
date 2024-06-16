package com.airfrance.ref.exception;

public class DelegationGinNotFoundException extends DelegationException {

	private static final long serialVersionUID = -3998814438164747934L;

	public DelegationGinNotFoundException(String gin) {
		super("Gin not found: '"+gin+"'");
	}

}
