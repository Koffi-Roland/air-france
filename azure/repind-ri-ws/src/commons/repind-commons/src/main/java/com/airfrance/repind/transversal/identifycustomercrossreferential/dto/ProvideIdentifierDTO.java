package com.airfrance.repind.transversal.identifycustomercrossreferential.dto;

/**
 * IdentifyCustomerCrossReferential Request's provide identifier DTO
 * @author t950700
 *
 */
public class ProvideIdentifierDTO {

	/*==========================================*/
	/*                                          */
	/*           INSTANCE VARIABLES             */
	/*                                          */
	/*==========================================*/
	private String customerGin;  
	private String identifierType;  
	private String identifierValue;
	
	/*==========================================*/
	/*                                          */
	/*             CONSTRUCTORS                 */
	/*                                          */
	/*==========================================*/
	public ProvideIdentifierDTO() {
		super();
	}

	public ProvideIdentifierDTO(String customerGin, String identifierType,
			String identifierValue) {
		super();
		this.customerGin = customerGin;
		this.identifierType = identifierType;
		this.identifierValue = identifierValue;
	}

	/*==========================================*/
	/*                                          */
	/*               ACCESSORS                  */
	/*                                          */
	/*==========================================*/
	public String getCustomerGin() {
		return customerGin;
	}

	public void setCustomerGin(String customerGin) {
		this.customerGin = customerGin;
	}

	public String getIdentifierType() {
		return identifierType;
	}

	public void setIdentifierType(String identifierType) {
		this.identifierType = identifierType;
	}

	public String getIdentifierValue() {
		return identifierValue;
	}

	public void setIdentifierValue(String identifierValue) {
		this.identifierValue = identifierValue;
	}
	
	
}
