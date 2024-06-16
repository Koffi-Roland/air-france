package com.afklm.rigui.accesskeytransformer;

import java.io.Serializable;


/**
 * Profile AccessKey
 * @author m405991
 *
 */
public class ProfileAccessKey implements Serializable {	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5503139443133039549L;
	
	private String profile = "";
	private AccessKey[] accessKeyLst = null;

	
	
	/**
	 * Default constructor
	 */
	public ProfileAccessKey() {
		//Empty Constructor
	}

	/**
	 * Constructor
	 * @param profile
	 * @param accessKeyLst
	 */
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
