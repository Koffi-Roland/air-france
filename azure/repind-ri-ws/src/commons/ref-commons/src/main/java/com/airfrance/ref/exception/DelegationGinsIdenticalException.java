package com.airfrance.ref.exception;

public class DelegationGinsIdenticalException extends DelegationException {
	
	private static final long serialVersionUID = -3469740387969245938L;

	public DelegationGinsIdenticalException(String gin) {
		super("Delegator's and delegate's gins identical: '"+gin+"'");
	}

}
