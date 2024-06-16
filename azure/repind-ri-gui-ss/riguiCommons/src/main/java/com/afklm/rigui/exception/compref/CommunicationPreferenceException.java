package com.afklm.rigui.exception.compref;

import com.afklm.rigui.exception.jraf.JrafDomainRollbackException;

public class CommunicationPreferenceException extends JrafDomainRollbackException {

	private static final long serialVersionUID = 3942624153043660163L;
	private String gin;
	private String domain;
	private String comGroupType;
	private String comType;

	public CommunicationPreferenceException(String msg, String gin,String domain, String comGroupType, String comType) {
		super(msg);
		this.gin = gin;
		this.domain = domain;
		this.comGroupType = comGroupType;
		this.comType = comType;
	}

	public CommunicationPreferenceException(Throwable root, String gin,String domain, String comGroupType, String comType) {
		super(root);
		this.gin = gin;
		this.domain = domain;
		this.comGroupType = comGroupType;
		this.comType = comType;
	}

	public CommunicationPreferenceException(String msg, Throwable root,String gin, String domain, String comGroupType, String comType) {
		super(msg, root);
		this.gin = gin;
		this.domain = domain;
		this.comGroupType = comGroupType;
		this.comType = comType;
	}

	public String getGin() {
		return gin;
	}

	public String getDomain() {
		return domain;
	}

	public String getComGroupType() {
		return comGroupType;
	}

	public String getComType() {
		return comType;
	}

	public void setGin(String gin) {
		this.gin = gin;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public void setComGroupType(String comGroupType) {
		this.comGroupType = comGroupType;
	}

	public void setComType(String comType) {
		this.comType = comType;
	}

}
