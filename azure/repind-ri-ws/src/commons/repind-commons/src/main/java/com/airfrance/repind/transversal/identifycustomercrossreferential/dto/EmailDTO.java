package com.airfrance.repind.transversal.identifycustomercrossreferential.dto;

public class EmailDTO {
	
	/*==========================================*/
	/*                                          */
	/*           INSTANCE VARIABLES             */
	/*                                          */
	/*==========================================*/
	private String email;

	/*==========================================*/
	/*                                          */
	/*              CONSTRUCTORS                */
	/*                                          */
	/*==========================================*/
	public EmailDTO() {
		super();
	}

	
	public EmailDTO(String email) {
		super();
		this.email = email;
	}

	/*==========================================*/
	/*                                          */
	/*                ACCESSORS                 */
	/*                                          */
	/*==========================================*/
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	
	
	
}
