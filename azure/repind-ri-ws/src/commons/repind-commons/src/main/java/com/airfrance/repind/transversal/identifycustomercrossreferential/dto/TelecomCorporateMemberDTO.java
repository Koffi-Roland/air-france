package com.airfrance.repind.transversal.identifycustomercrossreferential.dto;

/**
 * IdentifyCustomerCrossReferential - TelecomCorporateMemberDTO
 * @author t950700
 *
 */
public class TelecomCorporateMemberDTO {

	/*==========================================*/
	/*                                          */
	/*           INSTANCE VARIABLES             */
	/*                                          */
	/*==========================================*/
	private String terminalType;
	private String mediumCode;
	private String countryCode;
	private String phoneNumber;
	private String internationalNormalizedPhoneNumber;
	
	/*==========================================*/
	/*                                          */
	/*              CONSTRUCTORS                */
	/*                                          */
	/*==========================================*/
	
	public TelecomCorporateMemberDTO() {
		super();
	}
	
	public TelecomCorporateMemberDTO(String terminalType, String mediumCode,
			String countryCode, String phoneNumber, String internationalNormalizedPhoneNumber) {
		super();
		this.terminalType = terminalType;
		this.mediumCode = mediumCode;
		this.countryCode = countryCode;
		this.phoneNumber = phoneNumber;
		this.setInternationalNormalizedPhoneNumber(internationalNormalizedPhoneNumber);
	}
	
	/*==========================================*/
	/*                                          */
	/*                ACCESSORS                 */
	/*                                          */
	/*==========================================*/
	
	public String getTerminalType() {
		return terminalType;
	}
	public void setTerminalType(String terminalType) {
		this.terminalType = terminalType;
	}
	public String getMediumCode() {
		return mediumCode;
	}
	public void setMediumCode(String mediumCode) {
		this.mediumCode = mediumCode;
	}
	public String getCountryCode() {
		return countryCode;
	}
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getInternationalNormalizedPhoneNumber() {
		return internationalNormalizedPhoneNumber;
	}
	public void setInternationalNormalizedPhoneNumber(
			String internationalNormalizedPhoneNumber) {
		this.internationalNormalizedPhoneNumber = internationalNormalizedPhoneNumber;
	}
}
