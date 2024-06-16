package com.airfrance.ref.exception;

public class DelegationGinWithoutAccountException extends DelegationException {

	private static final long serialVersionUID = 3977486495489240866L;

	public DelegationGinWithoutAccountException(String gin) {
		super("No FB or MyA related to the gin: '"+gin+"'");
	}

}
