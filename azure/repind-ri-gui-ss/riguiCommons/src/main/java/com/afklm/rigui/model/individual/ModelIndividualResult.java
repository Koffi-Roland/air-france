package com.afklm.rigui.model.individual;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ModelIndividualResult {

	private String gin;
	private String type;
	private String civility;
	private String status;
    private String lastName;
    private String firstName;
    private String birthDate;
    private String contractNumber;
    private String contracType;
    private String address;
    
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getContractNumber() {
		return contractNumber;
	}
	public void setContractNumber(String contractNumber) {
		this.contractNumber = contractNumber;
	}
	public String getContracType() {
		return contracType;
	}
	public void setContracType(String contracType) {
		this.contracType = contracType;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}
	
}
