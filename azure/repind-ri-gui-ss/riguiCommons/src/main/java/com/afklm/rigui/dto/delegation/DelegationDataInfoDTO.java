package com.afklm.rigui.dto.delegation;

/*PROTECTED REGION ID(_HIwDAOZmEee2NuY-gHh1Ow i) ENABLED START*/

// add not generated imports here

import java.io.Serializable;
import java.util.Date;

/*PROTECTED REGION END*/

/**
 * <p>Title : DelegationDataInfoDTO</p>
 * 
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
	public  class DelegationDataInfoDTO implements Serializable {


        
    /**
     * delegationDataInfoId
     */
    private Integer delegationDataInfoId;
               
        
    /**
     * type
     */
    private String type;
    
    
    /**
     * typeGroupId
     */
    private Integer typeGroupId;
        
        
    /**
     * key
     */
    private String key;
        
        
    /**
     * value
     */
    private String value;
        
        
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
        
        
    /**
     * delegationDataDto
     */
    private DelegationDataDTO delegationDataDto;
        

    /*PROTECTED REGION ID(_HIwDAOZmEee2NuY-gHh1Ow u var) ENABLED START*/
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
	    public DelegationDataInfoDTO() {
	    //empty constructor
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
     * @return delegationDataDto
     */
    public DelegationDataDTO getDelegationDataDto() {
        return this.delegationDataDto;
    }

    /**
     *
     * @param pDelegationDataDto delegationDataDto value
     */
    public void setDelegationDataDto(DelegationDataDTO pDelegationDataDto) {
        this.delegationDataDto = pDelegationDataDto;
    }

    /**
     *
     * @return delegationDataInfoId
     */
    public Integer getDelegationDataInfoId() {
        return this.delegationDataInfoId;
    }

    /**
     *
     * @param pDelegationDataInfoId delegationDataInfoId value
     */
    public void setDelegationDataInfoId(Integer pDelegationDataInfoId) {
        this.delegationDataInfoId = pDelegationDataInfoId;
    }

    /**
     *
     * @return key
     */
    public String getKey() {
        return this.key;
    }

    /**
     *
     * @param pKey key value
     */
    public void setKey(String pKey) {
        this.key = pKey;
    }
    
    /**
    *
    * @return typeGroupId
    */
   public Integer getTypeGroupId() {
       return this.typeGroupId;
   }

   /**
    *
    * @param pTypeGroupId typeGroupId value
    */
   public void setTypeGroupId(Integer pTypeGroupId) {
       this.typeGroupId = pTypeGroupId;
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
     * @return type
     */
    public String getType() {
        return this.type;
    }

    /**
     *
     * @param pType type value
     */
    public void setType(String pType) {
        this.type = pType;
    }

    /**
     *
     * @return value
     */
    public String getValue() {
        return this.value;
    }

    /**
     *
     * @param pValue value value
     */
    public void setValue(String pValue) {
        this.value = pValue;
    }

    /**
     *
     * @return object as string
     */
    public String toString() {
        String result = null;
        /*PROTECTED REGION ID(toString_HIwDAOZmEee2NuY-gHh1Ow) ENABLED START*/
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
       buffer.append("delegationDataInfoId=").append(getDelegationDataInfoId());
       buffer.append(",");
       buffer.append("type=").append(getType());
       buffer.append(",");
       buffer.append("typeGroupId=").append(getTypeGroupId());
       buffer.append(",");
       buffer.append("key=").append(getKey());
       buffer.append(",");
       buffer.append("value=").append(getValue());
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

    /*PROTECTED REGION ID(_HIwDAOZmEee2NuY-gHh1Ow u m) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/

}
