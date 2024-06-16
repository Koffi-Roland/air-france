package com.airfrance.repind.entity.role;

/*PROTECTED REGION ID(_sydB8FMSEeat9YXqXExedw i) ENABLED START*/

// add not generated imports here

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/*PROTECTED REGION END*/


/**
 * <p>Title : RoleUCCR.java</p>
 * BO RoleUCCR
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
@Entity


@Table(name="ROLE_UCCR")
public class RoleUCCR implements Serializable {

/*PROTECTED REGION ID(serialUID _sydB8FMSEeat9YXqXExedw) ENABLED START*/
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
     * cleRole
     */
    @Id
    @Column(name="ICLE_ROLE", length=10, nullable=false, unique=true)
    @GeneratedValue(generator = "foreignGeneratorUccr")
    @GenericGenerator(name = "foreignGeneratorUccr", strategy = "foreign", parameters = { @Parameter(name = "property", value = "businessRole") })
    private Integer cleRole;
        
            
    /**
     * uccrID
     */
    @Column(name="UCCR_ID", length=12, nullable=false)
    private String uccrID;
        
            
    /**
     * gin
     */
    @Column(name="SGIN", length=12, nullable=false)
    private String gin;
        
            
    /**
     * ceID
     */
    @Column(name="CE_ID", length=200, nullable=false)
    private String ceID;
        
            
    /**
     * type
     */
    @Column(name="STYPE", length=2, nullable=false)
    private String type;
        
            
    /**
     * etat
     */
    @Column(name="SETAT", length=1, nullable=false)
    private String etat;
        
            
    /**
     * debutValidite
     */
    @Column(name="DDEBUT_VALIDITE")
    private Date debutValidite;
        
            
    /**
     * finValidite
     */
    @Column(name="DFIN_VALIDITE")
    private Date finValidite;
        
            
    /**
     * dateCreation
     */
    @Column(name="DDATE_CREATION")
    private Date dateCreation;
        
            
    /**
     * signatureCreation
     */
    @Column(name="SSIGNATURE_CREATION", length=16)
    private String signatureCreation;
        
            
    /**
     * siteCreation
     */
    @Column(name="SSITE_CREATION", length=10)
    private String siteCreation;
        
            
    /**
     * dateModification
     */
    @Column(name="DDATE_MODIFICATION")
    private Date dateModification;
        
            
    /**
     * signatureModification
     */
    @Column(name="SSIGNATURE_MODIFICATION", length=16)
    private String signatureModification;
        
            
    /**
     * siteModification
     */
    @Column(name="SSITE_MODIFICATION", length=10)
    private String siteModification;
        
            
    /**
     * businessRole
     */
    // 1 <-> 1
    @OneToOne(mappedBy="roleUCCR", fetch=FetchType.LAZY)
    private BusinessRole businessRole;
        
    /*PROTECTED REGION ID(_sydB8FMSEeat9YXqXExedw u var) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/
    
    /** 
     * default constructor 
     */
    public RoleUCCR() {
    	//empty constructor
    }
        
    /** 
     * full constructor
     * @param pCleRole cleRole
     * @param pUccrID uccrID
     * @param pGin gin
     * @param pCeID ceID
     * @param pType type
     * @param pEtat etat
     * @param pDebutValidite debutValidite
     * @param pFinValidite finValidite
     * @param pDateCreation dateCreation
     * @param pSignatureCreation signatureCreation
     * @param pSiteCreation siteCreation
     * @param pDateModification dateModification
     * @param pSignatureModification signatureModification
     * @param pSiteModification siteModification
     */
    public RoleUCCR(Integer pCleRole, String pUccrID, String pGin, String pCeID, String pType, String pEtat, Date pDebutValidite, Date pFinValidite, Date pDateCreation, String pSignatureCreation, String pSiteCreation, Date pDateModification, String pSignatureModification, String pSiteModification) {
        this.cleRole = pCleRole;
        this.uccrID = pUccrID;
        this.gin = pGin;
        this.ceID = pCeID;
        this.type = pType;
        this.etat = pEtat;
        this.debutValidite = pDebutValidite;
        this.finValidite = pFinValidite;
        this.dateCreation = pDateCreation;
        this.signatureCreation = pSignatureCreation;
        this.siteCreation = pSiteCreation;
        this.dateModification = pDateModification;
        this.signatureModification = pSignatureModification;
        this.siteModification = pSiteModification;
    }

    /**
     *
     * @return businessRole
     */
    public BusinessRole getBusinessRole() {
        return this.businessRole;
    }

    /**
     *
     * @param pBusinessRole businessRole value
     */
    public void setBusinessRole(BusinessRole pBusinessRole) {
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
        /*PROTECTED REGION ID(toString_sydB8FMSEeat9YXqXExedw) ENABLED START*/
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
        buffer.append("gin=").append(getGin());
        buffer.append(",");
        buffer.append("ceID=").append(getCeID());
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

     
    
    /*PROTECTED REGION ID(equals hash _sydB8FMSEeat9YXqXExedw) ENABLED START*/
    
    /**
     * {@inheritDoc}
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        //TODO Customize here if necessary
        return hashCodeImpl();
    }

    /**
     * {@inheritDoc}
     * @see java.lang.Object#equals(java.lang.Object)
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

    /*PROTECTED REGION ID(_sydB8FMSEeat9YXqXExedw u m) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/
    
}
