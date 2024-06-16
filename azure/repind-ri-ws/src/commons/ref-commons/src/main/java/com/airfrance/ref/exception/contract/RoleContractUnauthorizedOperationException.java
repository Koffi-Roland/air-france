package com.airfrance.ref.exception.contract;

import com.airfrance.ref.exception.RefException;

public class RoleContractUnauthorizedOperationException extends RefException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5330235682359000772L;
	
	private static final String ERROR_ON_CONTRACT = "Caller cannot modify or create this kind of contract";
	
	private String errorCode;
	
	public RoleContractUnauthorizedOperationException(String errorNumber, String msg) {
		super(ERROR_ON_CONTRACT, msg);
		this.errorCode = errorNumber;
	}
	
	public RoleContractUnauthorizedOperationException(String errorNumber, Throwable root) {
		super(ERROR_ON_CONTRACT, root);
		this.errorCode = errorNumber;
	}
	
	public RoleContractUnauthorizedOperationException(String errorNumber, String msg, Throwable root) {
		super(msg, root);
		this.errorCode = errorNumber;
	}

	/**
	 * @return the errorCode
	 */
	public String getErrorCode() {
		return errorCode;
	}

	/**
	 * @param errorCode the errorCode to set
	 */
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
}