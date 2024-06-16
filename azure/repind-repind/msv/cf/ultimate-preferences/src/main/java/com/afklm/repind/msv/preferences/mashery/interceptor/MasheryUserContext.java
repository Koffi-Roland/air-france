package com.afklm.repind.msv.preferences.mashery.interceptor;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Mashery UserContext header contains a String that looks like: { "customerId":
 * "1000000001", "membertype": "myaccount", "authLevel": "NORMAL" }
 *
 * @author x074151 (Suresh van Rookhuizen)
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class MasheryUserContext {
	private String customerId;
	private String authLevel;

	/**
	 * @return the authLevel
	 */
	public String getAuthLevel() {
		return authLevel;
	}

	/**
	 * @return the customerId
	 */
	public String getCustomerId() {
		return customerId;
	}

	/**
	 * @param authLevel
	 *            the authLevel to set
	 */
	public void setAuthLevel(final String authLevel) {
		this.authLevel = authLevel;
	}

	/**
	 * @param customerId
	 *            the customerId to set
	 */
	public void setCustomerId(final String customerId) {
		this.customerId = customerId;
	}
}
