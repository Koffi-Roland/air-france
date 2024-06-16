package com.afklm.rigui.entity.reference;

/*PROTECTED REGION ID(_4vzKIPz6EeaexJbSRqCy0Q i) ENABLED START*/

// add not generated imports here
/*PROTECTED REGION END*/


/**
 * <p>Title : RefProduct.java</p>
 * BO RefProduct
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
@javax.persistence.Entity
@javax.persistence.Table(name="REF_PRODUCT")
public class RefProduct implements java.io.Serializable {

    /*PROTECTED REGION ID(serialUID _4vzKIPz6EeaexJbSRqCy0Q) ENABLED START*/
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
     * productId
     */
    @javax.persistence.Id
    @javax.persistence.Column(name="PRODUCTID", length=10, nullable=false)
    private Integer productId;


    /**
     * contractType
     */
    @javax.persistence.Column(name="SCONTRACTTYPE", length=1, nullable=false)
    private String contractType;


    /**
     * productType
     */
    @javax.persistence.Column(name="SPRODUCTTYPE", length=2, nullable=false)
    private String productType;


    /**
     * subProductType
     */
    @javax.persistence.Column(name="SSUBPRODUCTTYPE", length=10, nullable=true)
    private String subProductType;


    /**
     * productName
     */
    @javax.persistence.Column(name="SPRODUCTNAME", length=38, nullable=false)
    private String productName;


    /**
     * contractId
     */
    @javax.persistence.Column(name="SCONTRACTID", length=1, nullable=false)
    private String contractId;


    /**
     * generateComPref
     */
    @javax.persistence.Column(name="SGENERATECOMPREF", length=1, nullable=false)
    private String generateComPref = "N";


    /**
     * associatedComPref
     */
    @javax.persistence.Column(name="SASSOCIATEDCOMPREFDOMAIN", length=1, nullable=true)
    private String associatedComPref;


    /**
     * dateCreation
     */
    @javax.persistence.Column(name="DDATE_CREATION")
    private java.util.Date dateCreation;


    /**
     * siteCreation
     */
    @javax.persistence.Column(name="SSITE_CREATION", length=10)
    private String siteCreation;


    /**
     * signatureCreation
     */
    @javax.persistence.Column(name="SSIGNATURE_CREATION", length=16)
    private String signatureCreation;


    /**
     * dateModification
     */
    @javax.persistence.Column(name="DDATE_MODIFICATION")
    private java.util.Date dateModification;


    /**
     * siteModification
     */
    @javax.persistence.Column(name="SSITE_MODIFICATION", length=10)
    private String siteModification;


    /**
     * signatureModification
     */
    @javax.persistence.Column(name="SSIGNATURE_MODIFICATION", length=16)
    private String signatureModification;

    /*PROTECTED REGION ID(_4vzKIPz6EeaexJbSRqCy0Q u var) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/

    /**
     * default constructor 
     */
    public RefProduct() {
        //empty constructor
        super();
    }

    /**
     * full constructor
     * @param pProductId productId
     * @param pContractType contractType
     * @param pProductType productType
     * @param pSubProductType subProductType
     * @param pProductName productName
     * @param pContractId contractId
     * @param pGenerateComPref generateComPref
     * @param pAssociatedComPref associatedComPref
     * @param pDateCreation dateCreation
     * @param pSiteCreation siteCreation
     * @param pSignatureCreation signatureCreation
     * @param pDateModification dateModification
     * @param pSiteModification siteModification
     * @param pSignatureModification signatureModification
     */
    public RefProduct(Integer pProductId, String pContractType, String pProductType, String pSubProductType, String pProductName, String pContractId, String pGenerateComPref, String pAssociatedComPref, java.util.Date pDateCreation, String pSiteCreation, String pSignatureCreation, java.util.Date pDateModification, String pSiteModification, String pSignatureModification) {
        super();
        this.productId = pProductId;
        this.contractType = pContractType;
        this.productType = pProductType;
        this.subProductType = pSubProductType;
        this.productName = pProductName;
        this.contractId = pContractId;
        this.generateComPref = pGenerateComPref;
        this.associatedComPref = pAssociatedComPref;
        this.dateCreation = pDateCreation;
        this.siteCreation = pSiteCreation;
        this.signatureCreation = pSignatureCreation;
        this.dateModification = pDateModification;
        this.siteModification = pSiteModification;
        this.signatureModification = pSignatureModification;
    }

    /**
     *
     * @return associatedComPref
     */
    public String getAssociatedComPref() {
        return this.associatedComPref;
    }

    /**
     *
     * @param pAssociatedComPref associatedComPref value
     */
    public void setAssociatedComPref(String pAssociatedComPref) {
        this.associatedComPref = pAssociatedComPref;
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
     * @return productName
     */
    public String getProductName() {
        return this.productName;
    }

    /**
     *
     * @param pProductName productName value
     */
    public void setProductName(String pProductName) {
        this.productName = pProductName;
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
        /*PROTECTED REGION ID(toString_4vzKIPz6EeaexJbSRqCy0Q) ENABLED START*/
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
        buffer.append("productName=").append(getProductName());
        buffer.append(",");
        buffer.append("contractId=").append(getContractId());
        buffer.append(",");
        buffer.append("generateComPref=").append(getGenerateComPref());
        buffer.append(",");
        buffer.append("associatedComPref=").append(getAssociatedComPref());
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



    /*PROTECTED REGION ID(equals hash _4vzKIPz6EeaexJbSRqCy0Q) ENABLED START*/

    /**
     * {@inheritDoc}
     * @see Object#hashCode()
     */
    @Override
    public int hashCode() {
        //TODO Customize here if necessary
        return hashCodeImpl();
    }

    /**
     * {@inheritDoc}
     * @see Object#equals(Object)
     */
    @Override
    public boolean equals(Object obj) {
        //TODO Customize here if necessary
        return equalsImpl(obj);
    }

    /*PROTECTED REGION END*/

    /**
     * Generated implementation method for hashCode
     * You can stop calling it in the hashCode() generated in protected region if necessary
     * @return hashcode
     */
    private int hashCodeImpl() {
        return super.hashCode();
    }

    /**
     * Generated implementation method for equals
     * You can stop calling it in the equals() generated in protected region if necessary
     * @return if param equals the current object
     * @param obj Object to compare with current
     */
    private boolean equalsImpl(Object obj) {
        return super.equals(obj);
    }

    /*PROTECTED REGION ID(_4vzKIPz6EeaexJbSRqCy0Q u m) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/

}
