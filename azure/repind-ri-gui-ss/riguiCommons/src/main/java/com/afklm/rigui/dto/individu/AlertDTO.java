package com.afklm.rigui.dto.individu;

/*PROTECTED REGION ID(__G-z4FiREea7Yu0D-4113Q i) ENABLED START*/

// add not generated imports here

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/*PROTECTED REGION END*/

/**
 * <p>Title : AlertDTO</p>
 * 
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
	public  class AlertDTO implements Serializable {


        
    /**
     * alertId
     */
    private Integer alertId;
        
        
    /**
     * sgin
     */
    private String sgin;
        
        
    /**
     * creationDate
     */
    private Date creationDate;
        
        
    /**
     * creationSignature
     */
    private String creationSignature;
        
        
    /**
     * creationSite
     */
    private String creationSite;
        
        
    /**
     * modificationDate
     */
    private Date modificationDate;
        
        
    /**
     * modificationSignature
     */
    private String modificationSignature;
        
        
    /**
     * modificationSite
     */
    private String modificationSite;
        
        
    /**
     * type
     */
    private String type;
        
        
    /**
     * optIn
     */
    private String optIn;
        
        
    /**
     * alertDataDTO
     */
    private Set<AlertDataDTO> alertDataDTO;
        

    /*PROTECTED REGION ID(__G-z4FiREea7Yu0D-4113Q u var) ENABLED START*/
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
	    public AlertDTO() {
	    //empty constructor
	    }
	    
    /**
     *
     * @return alertDataDTO
     */
    public Set<AlertDataDTO> getAlertDataDTO() {
        return this.alertDataDTO;
    }

    /**
     *
     * @param pAlertDataDTO alertDataDTO value
     */
    public void setAlertDataDTO(Set<AlertDataDTO> pAlertDataDTO) {
        this.alertDataDTO = pAlertDataDTO;
    }

    /**
     *
     * @return alertId
     */
    public Integer getAlertId() {
        return this.alertId;
    }

    /**
     *
     * @param pAlertId alertId value
     */
    public void setAlertId(Integer pAlertId) {
        this.alertId = pAlertId;
    }

    /**
     *
     * @return creationDate
     */
    public Date getCreationDate() {
        return this.creationDate;
    }

    /**
     *
     * @param pCreationDate creationDate value
     */
    public void setCreationDate(Date pCreationDate) {
        this.creationDate = pCreationDate;
    }

    /**
     *
     * @return creationSignature
     */
    public String getCreationSignature() {
        return this.creationSignature;
    }

    /**
     *
     * @param pCreationSignature creationSignature value
     */
    public void setCreationSignature(String pCreationSignature) {
        this.creationSignature = pCreationSignature;
    }

    /**
     *
     * @return creationSite
     */
    public String getCreationSite() {
        return this.creationSite;
    }

    /**
     *
     * @param pCreationSite creationSite value
     */
    public void setCreationSite(String pCreationSite) {
        this.creationSite = pCreationSite;
    }

    /**
     *
     * @return modificationDate
     */
    public Date getModificationDate() {
        return this.modificationDate;
    }

    /**
     *
     * @param pModificationDate modificationDate value
     */
    public void setModificationDate(Date pModificationDate) {
        this.modificationDate = pModificationDate;
    }

    /**
     *
     * @return modificationSignature
     */
    public String getModificationSignature() {
        return this.modificationSignature;
    }

    /**
     *
     * @param pModificationSignature modificationSignature value
     */
    public void setModificationSignature(String pModificationSignature) {
        this.modificationSignature = pModificationSignature;
    }

    /**
     *
     * @return modificationSite
     */
    public String getModificationSite() {
        return this.modificationSite;
    }

    /**
     *
     * @param pModificationSite modificationSite value
     */
    public void setModificationSite(String pModificationSite) {
        this.modificationSite = pModificationSite;
    }

    /**
     *
     * @return optIn
     */
    public String getOptIn() {
        return this.optIn;
    }

    /**
     *
     * @param pOptIn optIn value
     */
    public void setOptIn(String pOptIn) {
        this.optIn = pOptIn;
    }

    /**
     *
     * @return sgin
     */
    public String getSgin() {
        return this.sgin;
    }

    /**
     *
     * @param pSgin sgin value
     */
    public void setSgin(String pSgin) {
        this.sgin = pSgin;
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
        /*PROTECTED REGION ID(toString__G-z4FiREea7Yu0D-4113Q) ENABLED START*/
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
        buffer.append("alertId=").append(getAlertId());
        buffer.append(",");
        buffer.append("sgin=").append(getSgin());
        buffer.append(",");
        buffer.append("creationDate=").append(getCreationDate());
        buffer.append(",");
        buffer.append("creationSignature=").append(getCreationSignature());
        buffer.append(",");
        buffer.append("creationSite=").append(getCreationSite());
        buffer.append(",");
        buffer.append("modificationDate=").append(getModificationDate());
        buffer.append(",");
        buffer.append("modificationSignature=").append(getModificationSignature());
        buffer.append(",");
        buffer.append("modificationSite=").append(getModificationSite());
        buffer.append(",");
        buffer.append("type=").append(getType());
        buffer.append(",");
        buffer.append("optIn=").append(getOptIn());
        buffer.append("]");
        return buffer.toString();
    }

    /*PROTECTED REGION ID(__G-z4FiREea7Yu0D-4113Q u m) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/

}
