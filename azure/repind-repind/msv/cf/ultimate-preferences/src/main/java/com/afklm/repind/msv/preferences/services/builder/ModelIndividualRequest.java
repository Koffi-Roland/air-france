package com.afklm.repind.msv.preferences.services.builder;

public class ModelIndividualRequest {
	
	private ModelIndividualIdentificationRequest identification;
	private ModelIndividualProfileRequest profile;
	
	public ModelIndividualIdentificationRequest getIdentification() {
		return identification;
	}
	public void setIdentification(ModelIndividualIdentificationRequest identification) {
		this.identification = identification;
	}
	public ModelIndividualProfileRequest getProfile() {
		return profile;
	}
	public void setProfile(ModelIndividualProfileRequest profile) {
		this.profile = profile;
	}

}