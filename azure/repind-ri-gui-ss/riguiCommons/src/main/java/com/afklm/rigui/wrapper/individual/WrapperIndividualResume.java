package com.afklm.rigui.wrapper.individual;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class WrapperIndividualResume {

	private String gin;
	private String type;
	private String civility;
	private String lastname;
	private String firstname;
	private String birthDate;
	private String status;
	public String contractNumberFP;
	public int numberContractsNoFP;
	public int numberContractsFP;
	public boolean isMyAccount;
	public boolean isFlyingBlue;
	public int numberAddressesPerso;
	public int numberAddressesPro;
	public int numberEmailsPerso;
	public int numberEmailsPro;
	public int numberTelecomsPerso;
	public int numberTelecomsPro;
	public int numberExternalIdentifiers;
	public int numberOptInCommPref;
	public int numberOptOutCommPref;
	public int numberAlertsOptIn;
	public int numberAlertsOptOut;
	public int numberCompaniesLinked;
	public int numberAgenciesLinked;
	public int numberPreferences;
	public int numberDelegate;
	public int numberDelegator;
	public int numberGPRoles;
	public int numberUCCRRoles;
	
	public int getNumberUCCRRoles() {
		return numberUCCRRoles;
	}
	public void setNumberUCCRRoles(int numberUCCRRoles) {
		this.numberUCCRRoles = numberUCCRRoles;
	}
	
	public int getNumberGPRoles() {
		return numberGPRoles;
	}
	public void setNumberGPRoles(int numberGPRoles) {
		this.numberGPRoles = numberGPRoles;
	}
	public String getGin() {
		return gin;
	}
	public void setGin(String gin) {
		this.gin = gin;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getCivility() {
		return civility;
	}
	public void setCivility(String civility) {
		this.civility = civility;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
	
}
