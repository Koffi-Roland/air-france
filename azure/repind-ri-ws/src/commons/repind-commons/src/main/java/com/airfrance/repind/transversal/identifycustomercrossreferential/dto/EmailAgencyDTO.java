package com.airfrance.repind.transversal.identifycustomercrossreferential.dto;

/**
 * IdentifyCustomerCrossReferential - EmailAgencyDTO
 * @author t950700
 *
 */
public class EmailAgencyDTO {

	/*==========================================*/
	/*                                          */
	/*           INSTANCE VARIABLES             */
	/*                                          */
	/*==========================================*/
	private String mediumCode;
	private String email;
	private String optin;
	
	/*==========================================*/
	/*                                          */
	/*              CONSTRUCTORS                */
	/*                                          */
	/*==========================================*/
	
	public EmailAgencyDTO() {
		super();
	}
	
	
	public EmailAgencyDTO(String mediumCode, String email) {
		super();
		this.mediumCode = mediumCode;
		this.email = email;
	}
	
	/*==========================================*/
	/*                                          */
	/*                ACCESSORS                 */
	/*                                          */
	/*==========================================*/
	
	public String getMediumCode() {
		return mediumCode;
	}
	public void setMediumCode(String mediumCode) {
		this.mediumCode = mediumCode;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getOptin() {
		return optin;
	}
	public void setOptin(String optin) {
		this.optin = optin;
	}
	
	
}
