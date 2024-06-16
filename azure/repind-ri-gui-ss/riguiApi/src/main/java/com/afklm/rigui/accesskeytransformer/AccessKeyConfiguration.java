package com.afklm.rigui.accesskeytransformer;

import java.io.Serializable;

/**
 * AccessKey Configuration
 * @author m405991
 *
 */
public class AccessKeyConfiguration implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private ProfileAccessKey[] akc = null;
	
	/**
	 * Default constructor
	 */
	public AccessKeyConfiguration() {
		//Empty Constructor
	}
	
	/**
	 * Get AccessKeyConfiguration
	 * @return ProfileAccessKey
	 */
	public ProfileAccessKey[] getAccessKeyConfiguration() {
		return akc;
	}

	/**
	 * Set AccessKeyConfiguration
	 * @param accessKeyConfiguration
	 */
	public void setAccessKeyConfiguration(ProfileAccessKey[] accessKeyConfiguration) {
		this.akc = accessKeyConfiguration;
	}
}
