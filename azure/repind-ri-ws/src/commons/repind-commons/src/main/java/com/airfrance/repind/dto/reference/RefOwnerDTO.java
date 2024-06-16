package com.airfrance.repind.dto.reference;

/*PROTECTED REGION ID(_rSe8EP2MEeaexJbSRqCy0Q i) ENABLED START*/

// add not generated imports here

import java.io.Serializable;
import java.util.Date;

/*PROTECTED REGION END*/

/**
 * <p>Title : RefOwnerDTO</p>
 * 
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
	public  class RefOwnerDTO implements Serializable {


        
    /**
     * ownerId
     */
    private Integer ownerId;
        
        
    /**
     * appCode
     */
    private String appCode;
        
        
    /**
     * consumerId
     */
    private String consumerId;
        
        
    /**
     * applicationName
     */
    private String applicationName;
        
        
    /**
     * dateCreation
     */
    private Date dateCreation;
        
        
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
    private Date dateModification;
        
        
    /**
     * siteModification
     */
    private String siteModification;
        
        
    /**
     * signatureModification
     */
    private String signatureModification;
        

    /*PROTECTED REGION ID(_rSe8EP2MEeaexJbSRqCy0Q u var) ENABLED START*/
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
	    public RefOwnerDTO() {
	    //empty constructor
	    }
	    
    /**
     *
     * @return appCode
     */
    public String getAppCode() {
        return this.appCode;
    }

    /**
     *
     * @param pAppCode appCode value
     */
    public void setAppCode(String pAppCode) {
        this.appCode = pAppCode;
    }

    /**
     *
     * @return applicationName
     */
    public String getApplicationName() {
        return this.applicationName;
    }

    /**
     *
     * @param pApplicationName applicationName value
     */
    public void setApplicationName(String pApplicationName) {
        this.applicationName = pApplicationName;
    }

    /**
     *
     * @return consumerId
     */
    public String getConsumerId() {
        return this.consumerId;
    }

    /**
     *
     * @param pConsumerId consumerId value
     */
    public void setConsumerId(String pConsumerId) {
        this.consumerId = pConsumerId;
    }

    /**
     *
     * @return dateCreation
     */
    public Date getDateCreation() {
        return this.dateCreation;
    }

    /**
     *
     * @param pDateCreation dateCreation value
     */
    public void setDateCreation(Date pDateCreation) {
        this.dateCreation = pDateCreation;
    }

    /**
     *
     * @return dateModification
     */
    public Date getDateModification() {
        return this.dateModification;
    }

    /**
     *
     * @param pDateModification dateModification value
     */
    public void setDateModification(Date pDateModification) {
        this.dateModification = pDateModification;
    }

    /**
     *
     * @return ownerId
     */
    public Integer getOwnerId() {
        return this.ownerId;
    }

    /**
     *
     * @param pOwnerId ownerId value
     */
    public void setOwnerId(Integer pOwnerId) {
        this.ownerId = pOwnerId;
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
     * @return object as string
     */
    public String toString() {
        String result = null;
        /*PROTECTED REGION ID(toString_rSe8EP2MEeaexJbSRqCy0Q) ENABLED START*/
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
        buffer.append("ownerId=").append(getOwnerId());
        buffer.append(",");
        buffer.append("appCode=").append(getAppCode());
        buffer.append(",");
        buffer.append("consumerId=").append(getConsumerId());
        buffer.append(",");
        buffer.append("applicationName=").append(getApplicationName());
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

    /*PROTECTED REGION ID(_rSe8EP2MEeaexJbSRqCy0Q u m) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/

}
