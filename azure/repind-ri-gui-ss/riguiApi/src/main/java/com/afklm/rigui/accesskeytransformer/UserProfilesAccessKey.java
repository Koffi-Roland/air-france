package com.afklm.rigui.accesskeytransformer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * UserProfiles AccessKey
 * @author m405991
 *
 */
public class UserProfilesAccessKey implements Serializable {	
	private static final long serialVersionUID = -395939703331220050L;
	
	private List<ProfileAccessKey> accessKeyConfiguration = new ArrayList<ProfileAccessKey>();
	
	/**
	 * Add ProfileAccessKey
	 * @param profileAccessKey
	 */
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
