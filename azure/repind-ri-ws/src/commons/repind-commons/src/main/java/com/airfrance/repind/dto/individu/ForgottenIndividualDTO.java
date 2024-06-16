package com.airfrance.repind.dto.individu;

/*PROTECTED REGION ID(_zw_nENOAEee-fsR_-mfQcQ i) ENABLED START*/

// add not generated imports here
import java.io.Serializable;
import java.util.Date;


/*PROTECTED REGION END*/

/**
 * <p>Title : ForgottenIndividualDTO</p>
 * 
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
	public  class ForgottenIndividualDTO implements Serializable {


        
    /**
     * forgottenIndividualId
     */
    private Long forgottenIndividualId;
        
        
    /**
     * identifier
     */
    private String identifier;
        
        
    /**
     * identifierType
     */
    private String identifierType;
        
        
    /**
     * context
     */
    private String context;
        
        
    /**
     * deletionDate
     */
    private Date deletionDate;
        
        
    /**
     * signature
     */
    private String signature;
        
        
    /**
     * applicationCode
     */
    private String applicationCode;
        
        
    /**
     * site
     */
    private String site;
        
        
    /**
     * modificationDate
     */
    private Date modificationDate;
        

    /*PROTECTED REGION ID(_zw_nENOAEee-fsR_-mfQcQ u var) ENABLED START*/
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
	    public ForgottenIndividualDTO() {
	    //empty constructor
	    }
	    
    /**
     *
     * @return applicationCode
     */
    public String getApplicationCode() {
        return this.applicationCode;
    }

    /**
     *
     * @param pApplicationCode applicationCode value
     */
    public void setApplicationCode(String pApplicationCode) {
        this.applicationCode = pApplicationCode;
    }

    /**
     *
     * @return context
     */
    public String getContext() {
        return this.context;
    }

    /**
     *
     * @param pContext context value
     */
    public void setContext(String pContext) {
        this.context = pContext;
    }

    /**
     *
     * @return deletionDate
     */
    public Date getDeletionDate() {
        return this.deletionDate;
    }

    /**
     *
     * @param pDeletionDate deletionDate value
     */
    public void setDeletionDate(Date pDeletionDate) {
        this.deletionDate = pDeletionDate;
    }

    /**
     *
     * @return forgottenIndividualId
     */
    public Long getForgottenIndividualId() {
        return this.forgottenIndividualId;
    }

    /**
     *
     * @param pForgottenIndividualId forgottenIndividualId value
     */
    public void setForgottenIndividualId(Long pForgottenIndividualId) {
        this.forgottenIndividualId = pForgottenIndividualId;
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
     * @return identifierType
     */
    public String getIdentifierType() {
        return this.identifierType;
    }

    /**
     *
     * @param pIdentifierType identifierType value
     */
    public void setIdentifierType(String pIdentifierType) {
        this.identifierType = pIdentifierType;
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
     * @return signature
     */
    public String getSignature() {
        return this.signature;
    }

    /**
     *
     * @param pSignature signature value
     */
    public void setSignature(String pSignature) {
        this.signature = pSignature;
    }

    /**
     *
     * @return site
     */
    public String getSite() {
        return this.site;
    }

    /**
     *
     * @param pSite site value
     */
    public void setSite(String pSite) {
        this.site = pSite;
    }

    /**
     *
     * @return object as string
     */
    public String toString() {
        String result = null;
        /*PROTECTED REGION ID(toString_zw_nENOAEee-fsR_-mfQcQ) ENABLED START*/
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
        buffer.append("forgottenIndividualId=").append(getForgottenIndividualId());
        buffer.append(",");
        buffer.append("identifier=").append(getIdentifier());
        buffer.append(",");
        buffer.append("identifierType=").append(getIdentifierType());
        buffer.append(",");
        buffer.append("context=").append(getContext());
        buffer.append(",");
        buffer.append("deletionDate=").append(getDeletionDate());
        buffer.append(",");
        buffer.append("signature=").append(getSignature());
        buffer.append(",");
        buffer.append("applicationCode=").append(getApplicationCode());
        buffer.append(",");
        buffer.append("site=").append(getSite());
        buffer.append(",");
        buffer.append("modificationDate=").append(getModificationDate());
        buffer.append("]");
        return buffer.toString();
    }

    /*PROTECTED REGION ID(_zw_nENOAEee-fsR_-mfQcQ u m) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/

}
