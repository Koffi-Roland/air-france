package com.afklm.cati.common.accesskeytransformer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class UserProfilesAccessKey implements Serializable {	
	/**
	 * 
	 */
	private static final long serialVersionUID = -395939703331220050L;
	
	private List<ProfileAccessKey> accessKeyConfiguration = new ArrayList<ProfileAccessKey>();
	
	public void addProfileAccessKey(ProfileAccessKey profileAccessKey) {
		accessKeyConfiguration.add(profileAccessKey);
	}	

	
	
	public List<ProfileAccessKey> getAccessKeyConfiguration() {
		return accessKeyConfiguration;
	}


	public void setAccessKeyConfiguration(
			List<ProfileAccessKey> accessKeyConfiguration) {
		this.accessKeyConfiguration = accessKeyConfiguration;
	}
}
