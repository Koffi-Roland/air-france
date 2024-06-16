package com.airfrance.repind.transversal.identifycustomercrossreferential.dto;

/**
 * IndetifyCustomerCrossreferential response corporateInformations
 * @author t950700
 *
 */
public class CorporateInformationsDTO {

	/*==========================================*/
	/*                                          */
	/*           INSTANCE VARIABLES             */
	/*                                          */
	/*==========================================*/
	private String corporateKey;
	private String siretNumber;
	private String legalName;
	private String usualName;
	private String status;
	private String type;
	
	/*==========================================*/
	/*                                          */
	/*              CONSTRUCTORS                */
	/*                                          */
	/*==========================================*/
	
	public CorporateInformationsDTO() {
		super();
	}
	public CorporateInformationsDTO(String corporateKey, String siretNumber,
			String legalName, String usualName, String status, String type) {
		super();
		this.corporateKey = corporateKey;
		this.siretNumber = siretNumber;
		this.legalName = legalName;
		this.usualName = usualName;
		this.setStatus(status);
		this.setType(type);
	}
	
	/*==========================================*/
	/*                                          */
	/*                ACCESSORS                 */
	/*                                          */
	/*==========================================*/
	
	public String getCorporateKey() {
		return corporateKey;
	}
	public void setCorporateKey(String corporateKey) {
		this.corporateKey = corporateKey;
	}
	public String getSiretNumber() {
		return siretNumber;
	}
	public void setSiretNumber(String siretNumber) {
		this.siretNumber = siretNumber;
	}
	public String getLegalName() {
		return legalName;
	}
	public void setLegalName(String legalName) {
		this.legalName = legalName;
	}
	public String getUsualName() {
		return usualName;
	}
	public void setUsualName(String usualName) {
		this.usualName = usualName;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
}
