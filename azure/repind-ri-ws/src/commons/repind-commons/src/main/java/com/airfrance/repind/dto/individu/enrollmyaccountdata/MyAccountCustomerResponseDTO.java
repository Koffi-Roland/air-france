package com.airfrance.repind.dto.individu.enrollmyaccountdata;

/*PROTECTED REGION ID(_es-R8EM7EeCk2djT-5OeOA i) ENABLED START*/

// add not generated imports here
import java.io.Serializable;
import java.util.Date;

/*PROTECTED REGION END*/

/**
 * <p>Title : MyAccountCustomerResponseDTO</p>
 * 
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
public class MyAccountCustomerResponseDTO  implements Serializable {
        
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	/**
     * General Identification Number
     */
    private String gin;
        
        
    /**
     * Identification number
     */
    private String accountID;
        
        
    /**
     * email
     */
    private String email;
        
        
    /**
     * Contract number
     */
    private String contractRole;
        
        
    /**
     * contractType
     */
    private String contractType;
        
        
    /**
     * contractStatus
     */
    private String contractStatus;
        
        
    /**
     * productType
     */
    private String productType;
        
        
    /**
     * version
     */
    private String version;
        
        
    /**
     * validityStartDate
     */
    private Date validityStartDate;
        
        
    /**
     * validityEndDate
     */
    private Date validityEndDate;
        

    /*PROTECTED REGION ID(_es-R8EM7EeCk2djT-5OeOA u var) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/

    
    
    /** 
     * default constructor 
     */
    public MyAccountCustomerResponseDTO() {
    
    }
    
    
    /** 
     * full constructor
     * @param pGin gin
     * @param pAccountID accountID
     * @param pEmail email
     * @param pContractRole contractRole
     * @param pContractType contractType
     * @param pContractStatus contractStatus
     * @param pProductType productType
     * @param pVersion version
     * @param pValidityStartDate validityStartDate
     * @param pValidityEndDate validityEndDate
     */
    public MyAccountCustomerResponseDTO(String pGin, String pAccountID, String pEmail, String pContractRole, String pContractType, String pContractStatus, String pProductType, String pVersion, Date pValidityStartDate, Date pValidityEndDate) {
        this.gin = pGin;
        this.accountID = pAccountID;
        this.email = pEmail;
        this.contractRole = pContractRole;
        this.contractType = pContractType;
        this.contractStatus = pContractStatus;
        this.productType = pProductType;
        this.version = pVersion;
        this.validityStartDate = pValidityStartDate;
        this.validityEndDate = pValidityEndDate;
    }

    /**
     *
     * @return accountID
     */
    public String getAccountID() {
        return this.accountID;
    }

    /**
     *
     * @param pAccountID accountID value
     */
    public void setAccountID(String pAccountID) {
        this.accountID = pAccountID;
    }

    /**
     *
     * @return contractRole
     */
    public String getContractRole() {
        return this.contractRole;
    }

    /**
     *
     * @param pContractRole contractRole value
     */
    public void setContractRole(String pContractRole) {
        this.contractRole = pContractRole;
    }

    /**
     *
     * @return contractStatus
     */
    public String getContractStatus() {
        return this.contractStatus;
    }

    /**
     *
     * @param pContractStatus contractStatus value
     */
    public void setContractStatus(String pContractStatus) {
        this.contractStatus = pContractStatus;
    }

    /**
     *
     * @return contractType
     */
    public String getContractType() {
        return this.contractType;
    }

    /**
     *
     * @param pContractType contractType value
     */
    public void setContractType(String pContractType) {
        this.contractType = pContractType;
    }

    /**
     *
     * @return email
     */
    public String getEmail() {
        return this.email;
    }

    /**
     *
     * @param pEmail email value
     */
    public void setEmail(String pEmail) {
        this.email = pEmail;
    }

    /**
     *
     * @return gin
     */
    public String getGin() {
        return this.gin;
    }

    /**
     *
     * @param pGin gin value
     */
    public void setGin(String pGin) {
        this.gin = pGin;
    }

    /**
     *
     * @return productType
     */
    public String getProductType() {
        return this.productType;
    }

    /**
     *
     * @param pProductType productType value
     */
    public void setProductType(String pProductType) {
        this.productType = pProductType;
    }

    /**
     *
     * @return validityEndDate
     */
    public Date getValidityEndDate() {
        return this.validityEndDate;
    }

    /**
     *
     * @param pValidityEndDate validityEndDate value
     */
    public void setValidityEndDate(Date pValidityEndDate) {
        this.validityEndDate = pValidityEndDate;
    }

    /**
     *
     * @return validityStartDate
     */
    public Date getValidityStartDate() {
        return this.validityStartDate;
    }

    /**
     *
     * @param pValidityStartDate validityStartDate value
     */
    public void setValidityStartDate(Date pValidityStartDate) {
        this.validityStartDate = pValidityStartDate;
    }

    /**
     *
     * @return version
     */
    public String getVersion() {
        return this.version;
    }

    /**
     *
     * @param pVersion version value
     */
    public void setVersion(String pVersion) {
        this.version = pVersion;
    }

    /**
     *
     * @return object as string
     */
    public String toString() {
        String result = null;
        /*PROTECTED REGION ID(toString_es-R8EM7EeCk2djT-5OeOA) ENABLED START*/
        result = toStringImpl();
        /*PROTECTED REGION END*/
        return result;
    }

    /**
     *
     * @return object as string
     */
    public String toStringImpl() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(getClass().getName());
        buffer.append("@").append(Integer.toHexString(System.identityHashCode(this)));
        buffer.append("[");
        buffer.append("gin=").append(getGin());
        buffer.append(",");
        buffer.append("accountID=").append(getAccountID());
        buffer.append(",");
        buffer.append("email=").append(getEmail());
        buffer.append(",");
        buffer.append("contractRole=").append(getContractRole());
        buffer.append(",");
        buffer.append("contractType=").append(getContractType());
        buffer.append(",");
        buffer.append("contractStatus=").append(getContractStatus());
        buffer.append(",");
        buffer.append("productType=").append(getProductType());
        buffer.append(",");
        buffer.append("version=").append(getVersion());
        buffer.append(",");
        buffer.append("validityStartDate=").append(getValidityStartDate());
        buffer.append(",");
        buffer.append("validityEndDate=").append(getValidityEndDate());
        buffer.append("]");
        return buffer.toString();
    }

    /*PROTECTED REGION ID(_es-R8EM7EeCk2djT-5OeOA u m) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/

}
