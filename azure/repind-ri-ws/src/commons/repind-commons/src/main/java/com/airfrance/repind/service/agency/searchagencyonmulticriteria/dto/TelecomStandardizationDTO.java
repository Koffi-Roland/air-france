package com.airfrance.repind.service.agency.searchagencyonmulticriteria.dto;

public class TelecomStandardizationDTO {
	
	/*===============================================*/
	/*             INSTANCE VARIABLES                */
	/*===============================================*/
	private String normPhoneNumber;
	private String normCountryCode;
	private String phoneNumber;
	
	/*===============================================*/
	/*                CONSTRUCTORS                   */
	/*===============================================*/
	public TelecomStandardizationDTO() {
		super();
	}
	public TelecomStandardizationDTO(String normPhoneNumber,
			String normCountryCode) {
		super();
		this.normPhoneNumber = normPhoneNumber;
		this.normCountryCode = normCountryCode;
	}
	
	/*===============================================*/
	/*                ACCESSORS                      */
	/*===============================================*/
	
	public String getNormPhoneNumber() {
		return normPhoneNumber;
	}
	public void setNormPhoneNumber(String normPhoneNumber) {
		this.normPhoneNumber = normPhoneNumber;
	}
	public String getNormCountryCode() {
		return normCountryCode;
	}
	public void setNormCountryCode(String normCountryCode) {
		this.normCountryCode = normCountryCode;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	
}
