package com.afklm.rigui.dto.preference;

/*PROTECTED REGION ID(_A1c1sHZ_EeauaOCU3jazIg i) ENABLED START*/

// add not generated imports here

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/*PROTECTED REGION END*/

/**
 * <p>Title : PreferenceDTO</p>
 * 
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
	public  class PreferenceDTO implements Serializable {


        
    /**
     * preferenceId
     */
    private Long preferenceId;
        
        
    /**
     * gin
     */
    private String gin;
        
        
    /**
     * type
     */
    private String type;
        
        
    /**
     * link
     */
    private Integer link;
        
        
    /**
     * dateCreation
     */
    private Date dateCreation;
        
        
    /**
     * signatureCreation
     */
    private String signatureCreation;
        
        
    /**
     * siteCreation
     */
    private String siteCreation;
        
        
    /**
     * dateModification
     */
    private Date dateModification;
        
        
    /**
     * signatureModification
     */
    private String signatureModification;
        
        
    /**
     * siteModification
     */
    private String siteModification;
        
        
    /**
     * preferenceDataDTO
     */
    private Set<PreferenceDataDTO> preferenceDataDTO;
        

    /*PROTECTED REGION ID(_A1c1sHZ_EeauaOCU3jazIg u var) ENABLED START*/
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
	    public PreferenceDTO() {
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
     * @return link
     */
    public Integer getLink() {
        return this.link;
    }

    /**
     *
     * @param pLink link value
     */
    public void setLink(Integer pLink) {
        this.link = pLink;
    }

    /**
     *
     * @return preferenceDataDTO
     */
    public Set<PreferenceDataDTO> getPreferenceDataDTO() {
    	if (this.preferenceDataDTO == null) {
    		return new HashSet<PreferenceDataDTO>();
    	}
        return this.preferenceDataDTO;
    }

    /**
     *
     * @param pPreferenceDataDTO preferenceDataDTO value
     */
    public void setPreferenceDataDTO(Set<PreferenceDataDTO> pPreferenceDataDTO) {
        this.preferenceDataDTO = pPreferenceDataDTO;
    }

    /**
     *
     * @return preferenceId
     */
    public Long getPreferenceId() {
        return this.preferenceId;
    }

    /**
     *
     * @param pPreferenceId preferenceId value
     */
    public void setPreferenceId(Long pPreferenceId) {
        this.preferenceId = pPreferenceId;
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
     * @return object as string
     */
    public String toString() {
        String result = null;
        /*PROTECTED REGION ID(toString_A1c1sHZ_EeauaOCU3jazIg) ENABLED START*/
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
        buffer.append("preferenceId=").append(getPreferenceId());
        buffer.append(",");
        buffer.append("gin=").append(getGin());
        buffer.append(",");
        buffer.append("type=").append(getType());
        buffer.append(",");
        buffer.append("link=").append(getLink());
        buffer.append(",");
        buffer.append("dateCreation=").append(getDateCreation());
        buffer.append(",");
        buffer.append("signatureCreation=").append(getSignatureCreation());
        buffer.append(",");
        buffer.append("siteCreation=").append(getSiteCreation());
        buffer.append(",");
        buffer.append("dateModification=").append(getDateModification());
        buffer.append(",");
        buffer.append("signatureModification=").append(getSignatureModification());
        buffer.append(",");
        buffer.append("siteModification=").append(getSiteModification());
        buffer.append("]");
        return buffer.toString();
    }

    /*PROTECTED REGION ID(_A1c1sHZ_EeauaOCU3jazIg u m) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/

}
