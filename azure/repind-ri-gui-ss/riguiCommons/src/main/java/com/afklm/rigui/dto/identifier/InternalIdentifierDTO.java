package com.afklm.rigui.dto.identifier;

/*PROTECTED REGION ID(_4wcyIN2EEeav9oUtVVZbTQ i) ENABLED START*/

// add not generated imports here

import java.io.Serializable;
import java.util.Date;

/*PROTECTED REGION END*/

/**
 * <p>Title : InternalIdentifierDTO</p>
 * 
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
	public  class InternalIdentifierDTO implements Serializable {


        
    /**
     * identifierId
     */
    private long identifierId;
        
        
    /**
     * identifier
     */
    private String identifier;
        
        
    /**
     * type
     */
    private String type;
        
        
    /**
     * lastSeenDate
     */
    private Date lastSeenDate;
        
        
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
        

    /*PROTECTED REGION ID(_4wcyIN2EEeav9oUtVVZbTQ u var) ENABLED START*/
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
	    public InternalIdentifierDTO() {
	    //empty constructor
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
     * @return identifier
     */
    public String getIdentifier() {
        return this.identifier;
    }

    /**
     *
     * @param pIdentifier identifier value
     */
    public void setIdentifier(String pIdentifier) {
        this.identifier = pIdentifier;
    }

    /**
     *
     * @return identifierId
     */
    public long getIdentifierId() {
        return this.identifierId;
    }

    /**
     *
     * @param pIdentifierId identifierId value
     */
    public void setIdentifierId(long pIdentifierId) {
        this.identifierId = pIdentifierId;
    }

    /**
     *
     * @return lastSeenDate
     */
    public Date getLastSeenDate() {
        return this.lastSeenDate;
    }

    /**
     *
     * @param pLastSeenDate lastSeenDate value
     */
    public void setLastSeenDate(Date pLastSeenDate) {
        this.lastSeenDate = pLastSeenDate;
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
        /*PROTECTED REGION ID(toString_4wcyIN2EEeav9oUtVVZbTQ) ENABLED START*/
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
        buffer.append("identifierId=").append(getIdentifierId());
        buffer.append(",");
        buffer.append("identifier=").append(getIdentifier());
        buffer.append(",");
        buffer.append("type=").append(getType());
        buffer.append(",");
        buffer.append("lastSeenDate=").append(getLastSeenDate());
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
        buffer.append("]");
        return buffer.toString();
    }

    /*PROTECTED REGION ID(_4wcyIN2EEeav9oUtVVZbTQ u m) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/

}
