package com.afklm.repind.msv.preferences.services.builder;

import java.util.Date;

public class ModelIndividualIdentificationRequest {
	
	private String gin;
	private String firstname;
	private String lastname;
	private Date birthdate;
	private String civility;
	private String middlename;
	private String aliasFirstname;
	private String aliasLastname;
	private String title;
	private String status;
	private String gender;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getMiddlename() {
		return middlename;
	}
	public void setMiddlename(String middlename) {
		this.middlename = middlename;
	}
	public String getAliasFirstname() {
		return aliasFirstname;
	}
	public void setAliasFirstname(String aliasFirstname) {
		this.aliasFirstname = aliasFirstname;
	}
	public String getAliasLastname() {
		return aliasLastname;
	}
	public void setAliasLastname(String aliasLastname) {
		this.aliasLastname = aliasLastname;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getCivility() {
		return civility;
	}
	public void setCivility(String civility) {
		this.civility = civility;
	}
	public Date getBirthdate() {
		return birthdate;
	}
	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}
	public String getGin() {
		return gin;
	}
	public void setGin(String gin) {
		this.gin = gin;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

}
