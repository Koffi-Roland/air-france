package com.airfrance.repind.transversal.identifycustomercrossreferential.dto;

import java.util.Date;

/**
 * IndetifyCustomerCrossReferential - ContractIndividualDTO
 * @author t950700
 *
 */
public class ContractIndividualDTO {

	/*==========================================*/
	/*                                          */
	/*           INSTANCE VARIABLES             */
	/*                                          */
	/*==========================================*/
	private String contractNumber;
	private String contractFamily;
	private String productType;
	private String productSubType;
	private String contractStatus;
	private Date validityStartDate;
	private Date validityEndDate;
	private String tierLevel;
	private String memberType;
	
	/*==========================================*/
	/*                                          */
	/*              CONSTRUCTORS                */
	/*                                          */
	/*==========================================*/
	
	public ContractIndividualDTO() {
		super();
	}
	
	
	public ContractIndividualDTO(String contractNumber, String contractFamily,
			String productType, String productSubType, String contractStatus,
			Date validityStartDate, Date validityEndDate, String tierLevel,
			String memberType) {
		super();
		this.contractNumber = contractNumber;
		this.contractFamily = contractFamily;
		this.productType = productType;
		this.productSubType = productSubType;
		this.contractStatus = contractStatus;
		this.validityStartDate = validityStartDate;
		this.validityEndDate = validityEndDate;
		this.tierLevel = tierLevel;
		this.memberType = memberType;
	}
	
	/*==========================================*/
	/*                                          */
	/*                ACCESSORS                 */
	/*                                          */
	/*==========================================*/
	public String getContractNumber() {
		return contractNumber;
	}
	public void setContractNumber(String contractNumber) {
		this.contractNumber = contractNumber;
	}
	public String getContractFamily() {
		return contractFamily;
	}
	public void setContractFamily(String contractFamily) {
		this.contractFamily = contractFamily;
	}
	public String getProductType() {
		return productType;
	}
	public void setProductType(String productType) {
		this.productType = productType;
	}
	public String getProductSubType() {
		return productSubType;
	}
	public void setProductSubType(String productSubType) {
		this.productSubType = productSubType;
	}
	public String getContractStatus() {
		return contractStatus;
	}
	public void setContractStatus(String contractStatus) {
		this.contractStatus = contractStatus;
	}
	public Date getValidityStartDate() {
		return validityStartDate;
	}
	public void setValidityStartDate(Date validityStartDate) {
		this.validityStartDate = validityStartDate;
	}
	public Date getValidityEndDate() {
		return validityEndDate;
	}
	public void setValidityEndDate(Date validityEndDate) {
		this.validityEndDate = validityEndDate;
	}
	public String getTierLevel() {
		return tierLevel;
	}
	public void setTierLevel(String tierLevel) {
		this.tierLevel = tierLevel;
	}
	public String getMemberType() {
		return memberType;
	}
	public void setMemberType(String memberType) {
		this.memberType = memberType;
	}
	
	
}
