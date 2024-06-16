package com.airfrance.repind.dto.reference;

/*PROTECTED REGION ID(_1QsU4DSaEeaR_YJoHRGtPg i) ENABLED START*/

// add not generated imports here

import java.io.Serializable;
import java.util.Date;

/*PROTECTED REGION END*/

/**
 * <p>Title : RefComPrefMediaDTO</p>
 * 
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
	public  class RefComPrefMediaDTO implements Serializable {


        
    /**
     * codeMedia
     */
    private String codeMedia;
        
        
    /**
     * libelleMedia
     */
    private String libelleMedia;
        
        
    /**
     * libelleMediaEN
     */
    private String libelleMediaEN;
        
        
    /**
     * signatureModification
     */
    private String signatureModification;
        
        
    /**
     * siteModification
     */
    private String siteModification;
        
        
    /**
     * dateModification
     */
    private Date dateModification;
        
        
    /**
     * signatureCreation
     */
    private String signatureCreation;
        
        
    /**
     * siteCreation
     */
    private String siteCreation;
        
        
    /**
     * dateCreation
     */
    private Date dateCreation;
        

    /*PROTECTED REGION ID(_1QsU4DSaEeaR_YJoHRGtPg u var) ENABLED START*/
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
	    public RefComPrefMediaDTO() {
	    //empty constructor
	    }
	    
    /**
     *
     * @return codeMedia
     */
    public String getCodeMedia() {
        return this.codeMedia;
    }

    /**
     *
     * @param pCodeMedia codeMedia value
     */
    public void setCodeMedia(String pCodeMedia) {
        this.codeMedia = pCodeMedia;
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
     * @return libelleMedia
     */
    public String getLibelleMedia() {
        return this.libelleMedia;
    }

    /**
     *
     * @param pLibelleMedia libelleMedia value
     */
    public void setLibelleMedia(String pLibelleMedia) {
        this.libelleMedia = pLibelleMedia;
    }

    /**
     *
     * @return libelleMediaEN
     */
    public String getLibelleMediaEN() {
        return this.libelleMediaEN;
    }

    /**
     *
     * @param pLibelleMediaEN libelleMediaEN value
     */
    public void setLibelleMediaEN(String pLibelleMediaEN) {
        this.libelleMediaEN = pLibelleMediaEN;
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
        /*PROTECTED REGION ID(toString_1QsU4DSaEeaR_YJoHRGtPg) ENABLED START*/
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
        buffer.append("codeMedia=").append(getCodeMedia());
        buffer.append(",");
        buffer.append("libelleMedia=").append(getLibelleMedia());
        buffer.append(",");
        buffer.append("libelleMediaEN=").append(getLibelleMediaEN());
        buffer.append(",");
        buffer.append("signatureModification=").append(getSignatureModification());
        buffer.append(",");
        buffer.append("siteModification=").append(getSiteModification());
        buffer.append(",");
        buffer.append("dateModification=").append(getDateModification());
        buffer.append(",");
        buffer.append("signatureCreation=").append(getSignatureCreation());
        buffer.append(",");
        buffer.append("siteCreation=").append(getSiteCreation());
        buffer.append(",");
        buffer.append("dateCreation=").append(getDateCreation());
        buffer.append("]");
        return buffer.toString();
    }

    /*PROTECTED REGION ID(_1QsU4DSaEeaR_YJoHRGtPg u m) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/

}
