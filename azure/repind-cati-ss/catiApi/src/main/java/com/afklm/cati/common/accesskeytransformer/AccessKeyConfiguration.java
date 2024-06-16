package com.afklm.cati.common.accesskeytransformer;

import java.io.Serializable;

public class AccessKeyConfiguration implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private ProfileAccessKey[] akc = null;
	
	public AccessKeyConfiguration() {
		//Empty Constructor
	}
	
	public AccessKeyConfiguration(ProfileAccessKey[] accessKeyConfiguration) {
		this.akc = accessKeyConfiguration;
	}

	
	
	public ProfileAccessKey[] getAccessKeyConfiguration() {
		return akc;
	}

	public void setAccessKeyConfiguration(ProfileAccessKey[] accessKeyConfiguration) {
		this.akc = accessKeyConfiguration;
	}
}
