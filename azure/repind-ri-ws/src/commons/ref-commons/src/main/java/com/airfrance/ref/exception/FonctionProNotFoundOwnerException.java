package com.airfrance.ref.exception;

public class FonctionProNotFoundOwnerException extends FonctionProException {

	private static final long serialVersionUID = -3259960912372490535L;

	private static final String ERROR_ON_FONCTION_PRO = "THIS OWNER IS NOT DEFINED TO MANAGE FONCTION PRO";
	
	private String errorCode;
	
	public FonctionProNotFoundOwnerException(String errorCode, String msg) {
		super(ERROR_ON_FONCTION_PRO, msg);
		this.errorCode = errorCode;
	}

	public FonctionProNotFoundOwnerException(String errorCode, Throwable root) {
		super(ERROR_ON_FONCTION_PRO, root);
		this.errorCode = errorCode;
	}

	public FonctionProNotFoundOwnerException(String errorCode, String msg, Throwable root) {
		super(msg, root);
		this.errorCode = errorCode;
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
