package com.airfrance.repind.service.agency.searchagencyonmulticriteria.dto;

/**
 * Telecom DTO
 * @author t950700
 *
 */
public class TelecomDTO {

	/*===============================================*/
	/*             INSTANCE VARIABLES                */
	/*===============================================*/
	
	private String ain;
	private String version;
	private String mediumStatus;
	private String mediumCode;
	private String terminalType;
	private String countryCodeNum;
	private String phoneNumber;
	
	/*===============================================*/
	/*                CONSTRUCTORS                   */
	/*===============================================*/
	
	public TelecomDTO() {
		super();
	}


	public TelecomDTO(String ain, String version, String mediumStatus,
			String mediumCode, String terminalType, String countryCodeNum,
			String phoneNumber) {
		super();
		this.ain = ain;
		this.version = version;
		this.mediumStatus = mediumStatus;
		this.mediumCode = mediumCode;
		this.terminalType = terminalType;
		this.countryCodeNum = countryCodeNum;
		this.phoneNumber = phoneNumber;
	}

	/*===============================================*/
	/*                ACCESSORS                      */
	/*===============================================*/

	public String getAin() {
		return ain;
	}
	public void setAin(String ain) {
		this.ain = ain;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getMediumStatus() {
		return mediumStatus;
	}
	public void setMediumStatus(String mediumStatus) {
		this.mediumStatus = mediumStatus;
	}
	public String getMediumCode() {
		return mediumCode;
	}
	public void setMediumCode(String mediumCode) {
		this.mediumCode = mediumCode;
	}
	public String getTerminalType() {
		return terminalType;
	}
	public void setTerminalType(String terminalType) {
		this.terminalType = terminalType;
	}
	public String getCountryCodeNum() {
		return countryCodeNum;
	}
	public void setCountryCodeNum(String countryCodeNum) {
		this.countryCodeNum = countryCodeNum;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
}
