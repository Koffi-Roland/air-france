package com.afklm.cati.common.accesskeytransformer;

import java.io.Serializable;



public class ProfileAccessKey implements Serializable {	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5503139443133039549L;
	
	private String profile = "";
	private AccessKey[] accessKeyLst = null;

	
	
	
	public ProfileAccessKey() {
		//Empty Constructor
	}

	
	public ProfileAccessKey(String profile, AccessKey[] accessKeyLst) {
		this.profile = profile;
		this.accessKeyLst = accessKeyLst;
	}
	public String getProfile() {
		return profile;
	}
	public void setProfile(String profile) {
		this.profile = profile;
	}
	public AccessKey[] getAccessKeyLst() {
		return accessKeyLst;
	}
	public void setAccessKeyLst(AccessKey[] accessKeyLst) {
		this.accessKeyLst = accessKeyLst;
	}	
}
