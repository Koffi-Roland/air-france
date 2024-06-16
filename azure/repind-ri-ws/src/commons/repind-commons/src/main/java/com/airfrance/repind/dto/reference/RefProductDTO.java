package com.airfrance.repind.dto.reference;

/*PROTECTED REGION ID(_san6wP2KEeaexJbSRqCy0Q i) ENABLED START*/

// add not generated imports here
import java.io.Serializable;


/*PROTECTED REGION END*/

/**
 * <p>Title : RefProductDTO</p>
 * 
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
	public  class RefProductDTO implements Serializable {


        
    /**
     * productId
     */
    private Integer productId;
        
        
    /**
     * contractType
     */
    private String contractType;
        
        
    /**
     * productType
     */
    private String productType;
        
        
    /**
     * subProductType
     */
    private String subProductType;
        
    /**
     * productName
     */
    private String productName;
    
    /**
     * contractId
     */
    private String contractId;
        
        
    /**
     * generateComPref
     */
    private String generateComPref;
        
        
    /**
     * associatedComPrefDomain
     */
    private String associatedComPrefDomain;
        
        
    /**
     * dateCreation
     */
    private java.util.Date dateCreation;
        
        
    /**
     * siteCreation
     */
    private String siteCreation;
        
        
    /**
     * signatureCreation
     */
    private String signatureCreation;
        
        
    /**
     * dateModification
     */
    private java.util.Date dateModification;
        
        
    /**
     * siteModification
     */
    private String siteModification;
        
        
    /**
     * signatureModification
     */
    private String signatureModification;
        

    /*PROTECTED REGION ID(_san6wP2KEeaexJbSRqCy0Q u var) ENABLED START*/
    // add your custom variables here if necessary
    
     /**
   * Determines if a de-serialized file is compatible with this class.
   *
   * Maintainers must change this value if and only if the new version
   * of this class is not compatible with old versions. See Sun docs
   * for <a href=http://java.sun.com/j2se/1.5.0/docs/guide/serialization
   * /serialization/spec/class.html#4100> details. </a>
   *
   * Not necessary to include in first version of the class, but
   * included here as a reminder of its importance.
   */
    private static final long serialVersionUID = 1L;
    
    /*PROTECTED REGION END*/

    
	    
	    /** 
	     * default constructor 
	     */
	    public RefProductDTO() {
	    //empty constructor
	    }
	    
    /**
     *
     * @return associatedComPrefDomain
     */
    public String getAssociatedComPrefDomain() {
        return this.associatedComPrefDomain;
    }

    /**
     *
     * @param pAssociatedComPrefDomain associatedComPrefDomain value
     */
    public void setAssociatedComPrefDomain(String pAssociatedComPrefDomain) {
        this.associatedComPrefDomain = pAssociatedComPrefDomain;
    }

    /**
     *
     * @return contractId
     */
    public String getContractId() {
        return this.contractId;
    }

    /**
     *
     * @param pContractId contractId value
     */
    public void setContractId(String pContractId) {
        this.contractId = pContractId;
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
     * @return dateCreation
     */
    public java.util.Date getDateCreation() {
        return this.dateCreation;
    }

    /**
     *
     * @param pDateCreation dateCreation value
     */
    public void setDateCreation(java.util.Date pDateCreation) {
        this.dateCreation = pDateCreation;
    }

    /**
     *
     * @return dateModification
     */
    public java.util.Date getDateModification() {
        return this.dateModification;
    }

    /**
     *
     * @param pDateModification dateModification value
     */
    public void setDateModification(java.util.Date pDateModification) {
        this.dateModification = pDateModification;
    }

    /**
     *
     * @return generateComPref
     */
    public String getGenerateComPref() {
        return this.generateComPref;
    }

    /**
     *
     * @param pGenerateComPref generateComPref value
     */
    public void setGenerateComPref(String pGenerateComPref) {
        this.generateComPref = pGenerateComPref;
    }

    /**
     *
     * @return productId
     */
    public Integer getProductId() {
        return this.productId;
    }

    /**
     *
     * @param pProductId productId value
     */
    public void setProductId(Integer pProductId) {
        this.productId = pProductId;
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

    public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	/**
     *
     * @return signatureCreation
     */
    public String getSignatureCreation() {
        return this.signatureCreation;
    }

    /**
     *
     * @param pSignatureCreation signatureCreation value
     */
    public void setSignatureCreation(String pSignatureCreation) {
        this.signatureCreation = pSignatureCreation;
    }

    /**
     *
     * @return signatureModification
     */
    public String getSignatureModification() {
        return this.signatureModification;
    }

    /**
     *
     * @param pSignatureModification signatureModification value
     */
    public void setSignatureModification(String pSignatureModification) {
        this.signatureModification = pSignatureModification;
    }

    /**
     *
     * @return siteCreation
     */
    public String getSiteCreation() {
        return this.siteCreation;
    }

    /**
     *
     * @param pSiteCreation siteCreation value
     */
    public void setSiteCreation(String pSiteCreation) {
        this.siteCreation = pSiteCreation;
    }

    /**
     *
     * @return siteModification
     */
    public String getSiteModification() {
        return this.siteModification;
    }

    /**
     *
     * @param pSiteModification siteModification value
     */
    public void setSiteModification(String pSiteModification) {
        this.siteModification = pSiteModification;
    }

    /**
     *
     * @return subProductType
     */
    public String getSubProductType() {
        return this.subProductType;
    }

    /**
     *
     * @param pSubProductType subProductType value
     */
    public void setSubProductType(String pSubProductType) {
        this.subProductType = pSubProductType;
    }

    /**
     *
     * @return object as string
     */
    public String toString() {
        String result = null;
        /*PROTECTED REGION ID(toString_san6wP2KEeaexJbSRqCy0Q) ENABLED START*/
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
        buffer.append("productId=").append(getProductId());
        buffer.append(",");
        buffer.append("contractType=").append(getContractType());
        buffer.append(",");
        buffer.append("productType=").append(getProductType());
        buffer.append(",");
        buffer.append("subProductType=").append(getSubProductType());
        buffer.append(",");
        buffer.append("contractId=").append(getContractId());
        buffer.append(",");
        buffer.append("generateComPref=").append(getGenerateComPref());
        buffer.append(",");
        buffer.append("associatedComPrefDomain=").append(getAssociatedComPrefDomain());
        buffer.append(",");
        buffer.append("dateCreation=").append(getDateCreation());
        buffer.append(",");
        buffer.append("siteCreation=").append(getSiteCreation());
        buffer.append(",");
        buffer.append("signatureCreation=").append(getSignatureCreation());
        buffer.append(",");
        buffer.append("dateModification=").append(getDateModification());
        buffer.append(",");
        buffer.append("siteModification=").append(getSiteModification());
        buffer.append(",");
        buffer.append("signatureModification=").append(getSignatureModification());
        buffer.append("]");
        return buffer.toString();
    }

    /*PROTECTED REGION ID(_san6wP2KEeaexJbSRqCy0Q u m) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/

}
