package com.afklm.rigui.dto.role;

/*PROTECTED REGION ID(_6Uuz0FMVEeat9YXqXExedw i) ENABLED START*/

// add not generated imports here

import java.io.Serializable;
import java.util.Date;

/*PROTECTED REGION END*/

/**
 * <p>Title : RoleUCCRDTO</p>
 * 
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
	public  class RoleUCCRDTO implements Serializable {


        
    /**
     * cleRole
     */
    private Integer cleRole;
        
        
    /**
     * uccrID
     */
    private String uccrID;
        
        
    /**
     * ceID
     */
    private String ceID;
        
        
    /**
     * gin
     */
    private String gin;
        
        
    /**
     * type
     */
    private String type;
        
        
    /**
     * etat
     */
    private String etat;
        
        
    /**
     * debutValidite
     */
    private Date debutValidite;
        
        
    /**
     * finValidite
     */
    private Date finValidite;
        
        
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
     * businessRole
     */
    private BusinessRoleDTO businessRole;
        

    /*PROTECTED REGION ID(_6Uuz0FMVEeat9YXqXExedw u var) ENABLED START*/
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
	    public RoleUCCRDTO() {
	    //empty constructor
	    }
	    
    /**
     *
     * @return businessRole
     */
    public BusinessRoleDTO getBusinessRole() {
        return this.businessRole;
    }

    /**
     *
     * @param pBusinessRole businessRole value
     */
    public void setBusinessRole(BusinessRoleDTO pBusinessRole) {
        this.businessRole = pBusinessRole;
    }

    /**
     *
     * @return ceID
     */
    public String getCeID() {
        return this.ceID;
    }

    /**
     *
     * @param pCeID ceID value
     */
    public void setCeID(String pCeID) {
        this.ceID = pCeID;
    }

    /**
     *
     * @return cleRole
     */
    public Integer getCleRole() {
        return this.cleRole;
    }

    /**
     *
     * @param pCleRole cleRole value
     */
    public void setCleRole(Integer pCleRole) {
        this.cleRole = pCleRole;
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
     * @return debutValidite
     */
    public Date getDebutValidite() {
        return this.debutValidite;
    }

    /**
     *
     * @param pDebutValidite debutValidite value
     */
    public void setDebutValidite(Date pDebutValidite) {
        this.debutValidite = pDebutValidite;
    }

    /**
     *
     * @return etat
     */
    public String getEtat() {
        return this.etat;
    }

    /**
     *
     * @param pEtat etat value
     */
    public void setEtat(String pEtat) {
        this.etat = pEtat;
    }

    /**
     *
     * @return finValidite
     */
    public Date getFinValidite() {
        return this.finValidite;
    }

    /**
     *
     * @param pFinValidite finValidite value
     */
    public void setFinValidite(Date pFinValidite) {
        this.finValidite = pFinValidite;
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
     * @return uccrID
     */
    public String getUccrID() {
        return this.uccrID;
    }

    /**
     *
     * @param pUccrID uccrID value
     */
    public void setUccrID(String pUccrID) {
        this.uccrID = pUccrID;
    }

    /**
     *
     * @return object as string
     */
    public String toString() {
        String result = null;
        /*PROTECTED REGION ID(toString_6Uuz0FMVEeat9YXqXExedw) ENABLED START*/
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
        buffer.append("cleRole=").append(getCleRole());
        buffer.append(",");
        buffer.append("uccrID=").append(getUccrID());
        buffer.append(",");
        buffer.append("ceID=").append(getCeID());
        buffer.append(",");
        buffer.append("gin=").append(getGin());
        buffer.append(",");
        buffer.append("type=").append(getType());
        buffer.append(",");
        buffer.append("etat=").append(getEtat());
        buffer.append(",");
        buffer.append("debutValidite=").append(getDebutValidite());
        buffer.append(",");
        buffer.append("finValidite=").append(getFinValidite());
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

    /*PROTECTED REGION ID(_6Uuz0FMVEeat9YXqXExedw u m) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/

}
