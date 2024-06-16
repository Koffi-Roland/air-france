package com.afklm.rigui.model.individual;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ModelUsageMedium {
	
	private String applicationCode;
	private Integer number;
	private String role1;
	private String role2;
	private String role3;
	private String role4;
	private String role5;
	
	public String getApplicationCode() {
		return applicationCode;
	}
	public void setApplicationCode(String applicationCode) {
		this.applicationCode = applicationCode;
	}
	public Integer getNumber() {
		return number;
	}
	public void setNumber(Integer number) {
		this.number = number;
	}
	public String getRole1() {
		return role1;
	}
	public void setRole1(String role1) {
		this.role1 = role1;
	}
	public String getRole2() {
		return role2;
	}
	public void setRole2(String role2) {
		this.role2 = role2;
	}
	public String getRole3() {
		return role3;
	}
	public void setRole3(String role3) {
		this.role3 = role3;
	}
	public String getRole4() {
		return role4;
	}
	public void setRole4(String role4) {
		this.role4 = role4;
	}
	public String getRole5() {
		return role5;
	}
	public void setRole5(String role5) {
		this.role5 = role5;
	}

}
