package com.airfrance.ref.exception;

public class DelegationActionException extends DelegationException {
	
	private static final long serialVersionUID = -1132440956547347319L;

	public DelegationActionException(String gin1, String gin2) {
		super("Invalid delegation action for : '"+gin1+"' '"+gin2+"'");
	}

}
