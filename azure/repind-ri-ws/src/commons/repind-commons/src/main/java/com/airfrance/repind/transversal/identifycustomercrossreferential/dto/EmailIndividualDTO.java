package com.airfrance.repind.transversal.identifycustomercrossreferential.dto;

/**
 * IdentifyCustomerCrossReferential - EmailIndividualDTO
 * @author t950700
 *
 */
public class EmailIndividualDTO {

	/*==========================================*/
	/*                                          */
	/*           INSTANCE VARIABLES             */
	/*                                          */
	/*==========================================*/
	private String mediumCode;
	private String email;
	private String emailOptin;
	
	/*==========================================*/
	/*                                          */
	/*              CONSTRUCTORS                */
	/*                                          */
	/*==========================================*/
	
	public EmailIndividualDTO() {
		super();
		// TODO Module de remplacement de constructeur auto-généré
	}
	
	
	public EmailIndividualDTO(String mediumCode, String email, String emailOptin) {
		super();
		this.mediumCode = mediumCode;
		this.email = email;
		this.emailOptin = emailOptin;
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
	public String getEmailOptin() {
		return emailOptin;
	}
	public void setEmailOptin(String emailOptin) {
		this.emailOptin = emailOptin;
	}
	  
	
}
