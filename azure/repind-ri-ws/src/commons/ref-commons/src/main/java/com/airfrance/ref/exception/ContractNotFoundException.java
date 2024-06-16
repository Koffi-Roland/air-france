package com.airfrance.ref.exception;

public class ContractNotFoundException extends  RefException {

	private static final long serialVersionUID = -1432337699035001046L;

	private static final String ERROR_MESSAGE = "Contract was not found with provided contract number";
	
	public ContractNotFoundException(String msg) {
		super(ERROR_MESSAGE, msg);
	}

	public ContractNotFoundException(Throwable root) {
		super(root);
	}

	public ContractNotFoundException(String msg, Throwable root) {
		super(msg, root);
	}
}
