package com.airfrance.ref.exception;

import com.airfrance.ref.exception.jraf.JrafDomainRollbackException;

public class FBRecoErrorException extends JrafDomainRollbackException {

	private static final long serialVersionUID = 1887368075357506246L;

	private String faultInfo;
	
	public FBRecoErrorException(String msg, String faultInfo) {
		super(msg);
		this.faultInfo = faultInfo;
	}

	public FBRecoErrorException(String faultInfo, Throwable root) {
		super(root);
		this.faultInfo = faultInfo;
	}

	public FBRecoErrorException(String faultInfo, String msg, Throwable root) {
		super(msg, root);
		this.faultInfo = faultInfo;
	}

	public String getFaultInfo() {
		return faultInfo;
	}

	public void setFaultInfo(String faultInfo) {
		this.faultInfo = faultInfo;
	}

}
