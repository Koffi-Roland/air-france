package com.airfrance.repind.transversal.identifycustomercrossreferential.dto;

/**
 * IdentifyCustomerCrossReferential - AgencyInformationsDTO
 * @author t950700
 *
 */
public class AgencyInformationsDTO {

	/*==========================================*/
	/*                                          */
	/*           INSTANCE VARIABLES             */
	/*                                          */
	/*==========================================*/
	private String agencyKey;
	private String typeAgreementNumber;
	private String agreementNumber;
	private String legalName;
	private String usualName;
	private String status;
	
	/*==========================================*/
	/*                                          */
	/*              CONSTRUCTORS                */
	/*                                          */
	/*==========================================*/
	
	public AgencyInformationsDTO() {
		super();
	}
	
	
	public AgencyInformationsDTO(String agencyKey, String typeAgreementNumber,
			String agreementNumber, String legalName, String usualName, String status) {
		super();
		this.agencyKey = agencyKey;
		this.typeAgreementNumber = typeAgreementNumber;
		this.agreementNumber = agreementNumber;
		this.legalName = legalName;
		this.usualName = usualName;
		this.status = status;
	}
	
	/*==========================================*/
	/*                                          */
	/*                ACCESSORS                 */
	/*                                          */
	/*==========================================*/
	
	public String getAgencyKey() {
		return agencyKey;
	}
	public void setAgencyKey(String agencyKey) {
		this.agencyKey = agencyKey;
	}
	public String getTypeAgreementNumber() {
		return typeAgreementNumber;
	}
	public void setTypeAgreementNumber(String typeAgreementNumber) {
		this.typeAgreementNumber = typeAgreementNumber;
	}
	public String getAgreementNumber() {
		return agreementNumber;
	}
	public void setAgreementNumber(String agreementNumber) {
		this.agreementNumber = agreementNumber;
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
}
