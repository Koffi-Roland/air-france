package com.airfrance.repind.transversal.identifycustomercrossreferential.dto;

/**
 * IdentifyCustomerCrossReferential - AddressRoleIndividualDTO
 * @author t950700
 *
 */
public class AddressRoleIndividualDTO {

	/*==========================================*/
	/*                                          */
	/*           INSTANCE VARIABLES             */
	/*                                          */
	/*==========================================*/
	private String role;
	private String applicationCode;
	private String usageNumber;
	
	/*==========================================*/
	/*                                          */
	/*              CONSTRUCTORS                */
	/*                                          */
	/*==========================================*/
	
	public AddressRoleIndividualDTO() {
		super();
	}

	public AddressRoleIndividualDTO(String role) {
		super();
		this.role = role;
	}

	public AddressRoleIndividualDTO(String role, String applicationCode, String usageNumber) {
		super();
		this.role = role;
		this.applicationCode = applicationCode;
		this.usageNumber = usageNumber;
	}

	
	/*==========================================*/
	/*                                          */
	/*                ACCESSORS                 */
	/*                                          */
	/*==========================================*/
	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getApplicationCode() {
		return applicationCode;
	}

	public void setApplicationCode(String applicationCode) {
		this.applicationCode = applicationCode;
	}

	public String getUsageNumber() {
		return usageNumber;
	}

	public void setUsageNumber(String usageNumber) {
		this.usageNumber = usageNumber;
	}
	
	
}
