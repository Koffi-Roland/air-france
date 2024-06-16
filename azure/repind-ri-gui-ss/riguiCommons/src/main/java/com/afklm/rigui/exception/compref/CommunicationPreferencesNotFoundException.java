package com.afklm.rigui.exception.compref;

public class CommunicationPreferencesNotFoundException extends
		CommunicationPreferenceException {

	private static final long serialVersionUID = 7369255651685241591L;

	public CommunicationPreferencesNotFoundException(String msg, String gin,String domain, String comGroupType, String comType) {
		super(msg, gin, domain, comGroupType, comType);		
	}
}
