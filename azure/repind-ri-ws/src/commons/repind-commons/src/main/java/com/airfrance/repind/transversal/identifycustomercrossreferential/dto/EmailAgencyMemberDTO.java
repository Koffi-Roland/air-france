package com.airfrance.repind.transversal.identifycustomercrossreferential.dto;

/**
 * IdentifyCustomerCrossReferential - EmailAgencyMemberDTO
 * @author t950700
 *
 */
public class EmailAgencyMemberDTO {

	/*==========================================*/
	/*                                          */
	/*           INSTANCE VARIABLES             */
	/*                                          */
	/*==========================================*/
	
	private String mediumCode;
	private String email;
	
	/*==========================================*/
	/*                                          */
	/*              CONSTRUCTORS                */
	/*                                          */
	/*==========================================*/
	public EmailAgencyMemberDTO() {
		super();
	}
	
	
	public EmailAgencyMemberDTO(String mediumCode, String email) {
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
	
	
	
	
}
