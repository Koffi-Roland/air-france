package com.airfrance.ref.exception;

public class FonctionProUnauthorizedOperationException extends FonctionProException {

	private static final long serialVersionUID = 5447203329925164108L;
	
	private static final String ERROR_ON_FONCTION_PRO = "THIS FONCTION PRO CANNOT BE MANAGED BY THIS CONSUMER";
	
	private String errorCode;
	
	public FonctionProUnauthorizedOperationException(String errorCode, String msg) {
		super(ERROR_ON_FONCTION_PRO, msg);
		this.errorCode = errorCode;
	}

	public FonctionProUnauthorizedOperationException(String errorCode, Throwable root) {
		super(ERROR_ON_FONCTION_PRO, root);
		this.errorCode = errorCode;
	}

	public FonctionProUnauthorizedOperationException(String errorCode, String msg, Throwable root) {
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
