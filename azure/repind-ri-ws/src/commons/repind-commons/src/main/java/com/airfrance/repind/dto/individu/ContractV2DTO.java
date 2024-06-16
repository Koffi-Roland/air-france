package com.airfrance.repind.dto.individu;


import org.apache.commons.lang.builder.ToStringBuilder;

import java.util.Date;

/**
 * <p>Title : ContractV2DTO</p>
 * 
 * <p>Copyright : Copyright (c) 2018</p>
 * <p>Company : AIRFRANCE</p>
 */
public class ContractV2DTO  {
        
    /**
     * contractNumber
     */
    private String contractNumber;
        
        
    /**
     * contractType
     */
    private String contractType;
    
    /**
     * productType
     */
    private String productType;
    
    /**
     * productSubType
     */
    private String productSubType;
    
    /**
     * companyCode
     */
    private String companyCode;
    
    /**
     * contractStatus
     */
    private String contractStatus;
    
    /**
     * validityStartDate
     */
    private Date validityStartDate;
    
    /**
     * validityEndDate
     */
    private Date validityEndDate;
    
    /**
     * iataCode
     */
    private String iataCode;
    
    /** 
     * default constructor 
     */
    public ContractV2DTO() {
    
    }
    
    
    /** 
     * full constructor
     * @param pContractNumber contractNumber value
     * @param pContractType contractType value
     * @param pProductType productType value
     * @param pProductSubType productSubType value
     * @param pCompanyCode companyCode value
     * @param pContractStatus contractStatus value
     * @param pValidityStartDate validityStartDate value
     * @param pValidityEndDate validityEndDate value
     * @param pIataCode iataCode value
     */
    public ContractV2DTO(String pContractNumber, String pContractType, String pProductType, String pProductSubType, String pCompanyCode, String pContractStatus, Date pValidityStartDate, Date pValidityEndDate, String pIataCode) {
    	this.contractNumber = pContractNumber;
    	this.contractType = pContractType;
    	this.productType = pProductType;
    	this.productSubType = pProductSubType;
    	this.companyCode = pCompanyCode;
    	this.contractStatus = pContractStatus;
    	this.validityStartDate = pValidityStartDate;
    	this.validityEndDate = pValidityEndDate;
    	this.iataCode = pIataCode;
    }

    

    /**
	 * @return the contractNumber
	 */
	public String getContractNumber() {
		return contractNumber;
	}


	/**
	 * @param contractNumber the contractNumber to set
	 */
	public void setContractNumber(String contractNumber) {
		this.contractNumber = contractNumber;
	}


	/**
	 * @return the contractType
	 */
	public String getContractType() {
		return contractType;
	}


	/**
	 * @param contractType the contractType to set
	 */
	public void setContractType(String contractType) {
		this.contractType = contractType;
	}


	/**
	 * @return the productType
	 */
	public String getProductType() {
		return productType;
	}


	/**
	 * @param productType the productType to set
	 */
	public void setProductType(String productType) {
		this.productType = productType;
	}


	/**
	 * @return the productSubType
	 */
	public String getProductSubType() {
		return productSubType;
	}


	/**
	 * @param productSubType the productSubType to set
	 */
	public void setProductSubType(String productSubType) {
		this.productSubType = productSubType;
	}


	/**
	 * @return the companyCode
	 */
	public String getCompanyCode() {
		return companyCode;
	}


	/**
	 * @param companyCode the companyCode to set
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}


	/**
	 * @return the contractStatus
	 */
	public String getContractStatus() {
		return contractStatus;
	}


	/**
	 * @param contractStatus the contractStatus to set
	 */
	public void setContractStatus(String contractStatus) {
		this.contractStatus = contractStatus;
	}


	/**
	 * @return the validityStartDate
	 */
	public Date getValidityStartDate() {
		return validityStartDate;
	}


	/**
	 * @param validityStartDate the validityStartDate to set
	 */
	public void setValidityStartDate(Date validityStartDate) {
		this.validityStartDate = validityStartDate;
	}


	/**
	 * @return the validityEndDate
	 */
	public Date getValidityEndDate() {
		return validityEndDate;
	}


	/**
	 * @param validityEndDate the validityEndDate to set
	 */
	public void setValidityEndDate(Date validityEndDate) {
		this.validityEndDate = validityEndDate;
	}


	/**
	 * @return the iataCode
	 */
	public String getIataCode() {
		return iataCode;
	}


	/**
	 * @param iataCode the iataCode to set
	 */
	public void setIataCode(String iataCode) {
		this.iataCode = iataCode;
	}


	/**
     *
     * @return object as string
     */
    public String toString() {
        String result = null;
        result = toStringImpl();
        return result;
    }

    /**
     *
     * @return object as string
     */
    public String toStringImpl() {
        return new ToStringBuilder(this)
        		.append("contractNumber", getContractNumber())
        		.append("contractType", getContractType())
        		.append("productType", getProductType())
        		.append("productSubType", getProductSubType())
        		.append("companyCode", getCompanyCode())
        		.append("contractStatus", getContractStatus())
        		.append("validityStartDate", getValidityStartDate())
        		.append("validityEndDate", getValidityEndDate())
        		.append("iataCode", getIataCode())
            .toString();
    }

}
