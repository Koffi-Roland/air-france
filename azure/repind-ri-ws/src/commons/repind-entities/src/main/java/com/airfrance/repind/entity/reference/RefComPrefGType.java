package com.airfrance.repind.entity.reference;

/*PROTECTED REGION ID(_qN0A0DSbEeaR_YJoHRGtPg i) ENABLED START*/

// add not generated imports here

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/*PROTECTED REGION END*/


/**
 * <p>Title : RefComPrefGType.java</p>
 * BO RefComPrefGType
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
@Entity


@Table(name="REF_COMPREF_GTYPE")
public class RefComPrefGType implements Serializable {

/*PROTECTED REGION ID(serialUID _qN0A0DSbEeaR_YJoHRGtPg) ENABLED START*/
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
     * codeGType
     */
    @Id
    @Column(name="SCODE_GTYPE", length=7)
    private String codeGType;
        
            
    /**
     * libelleGType
     */
    @Column(name="SLIBELLE_GTYPE", length=25)
    private String libelleGType;
        
            
    /**
     * libelleGTypeEN
     */
    @Column(name="SLIBELLE_GTYPE_EN", length=25)
    private String libelleGTypeEN;
        
            
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
     * dateModification
     */
    @Column(name="DDATE_MODIFICATION")
    private Date dateModification;
        
            
    /**
     * signatureCreation
     */
    @Column(name="SSIGNATURE_CREATION", length=16, nullable=false)
    private String signatureCreation;
        
            
    /**
     * siteCreation
     */
    @Column(name="SSITE_CREATION", length=10, nullable=false)
    private String siteCreation;
        
            
    /**
     * dateCreation
     */
    @Column(name="DDATE_CREATION")
    private Date dateCreation;
        
    /*PROTECTED REGION ID(_qN0A0DSbEeaR_YJoHRGtPg u var) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/
    
    /** 
     * default constructor 
     */
    public RefComPrefGType() {
    	//empty constructor
    }
        
    /** 
     * full constructor
     * @param pCodeGType codeGType
     * @param pLibelleGType libelleGType
     * @param pLibelleGTypeEN libelleGTypeEN
     * @param pSignatureModification signatureModification
     * @param pSiteModification siteModification
     * @param pDateModification dateModification
     * @param pSignatureCreation signatureCreation
     * @param pSiteCreation siteCreation
     * @param pDateCreation dateCreation
     */
    public RefComPrefGType(String pCodeGType, String pLibelleGType, String pLibelleGTypeEN, String pSignatureModification, String pSiteModification, Date pDateModification, String pSignatureCreation, String pSiteCreation, Date pDateCreation) {
        this.codeGType = pCodeGType;
        this.libelleGType = pLibelleGType;
        this.libelleGTypeEN = pLibelleGTypeEN;
        this.signatureModification = pSignatureModification;
        this.siteModification = pSiteModification;
        this.dateModification = pDateModification;
        this.signatureCreation = pSignatureCreation;
        this.siteCreation = pSiteCreation;
        this.dateCreation = pDateCreation;
    }

    /**
     *
     * @return codeGType
     */
    public String getCodeGType() {
        return this.codeGType;
    }

    /**
     *
     * @param pCodeGType codeGType value
     */
    public void setCodeGType(String pCodeGType) {
        this.codeGType = pCodeGType;
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
     * @return libelleGType
     */
    public String getLibelleGType() {
        return this.libelleGType;
    }

    /**
     *
     * @param pLibelleGType libelleGType value
     */
    public void setLibelleGType(String pLibelleGType) {
        this.libelleGType = pLibelleGType;
    }

    /**
     *
     * @return libelleGTypeEN
     */
    public String getLibelleGTypeEN() {
        return this.libelleGTypeEN;
    }

    /**
     *
     * @param pLibelleGTypeEN libelleGTypeEN value
     */
    public void setLibelleGTypeEN(String pLibelleGTypeEN) {
        this.libelleGTypeEN = pLibelleGTypeEN;
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
        /*PROTECTED REGION ID(toString_qN0A0DSbEeaR_YJoHRGtPg) ENABLED START*/
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
        buffer.append("codeGType=").append(getCodeGType());
        buffer.append(",");
        buffer.append("libelleGType=").append(getLibelleGType());
        buffer.append(",");
        buffer.append("libelleGTypeEN=").append(getLibelleGTypeEN());
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

     
    
    /*PROTECTED REGION ID(equals hash _qN0A0DSbEeaR_YJoHRGtPg) ENABLED START*/
    
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

    /*PROTECTED REGION ID(_qN0A0DSbEeaR_YJoHRGtPg u m) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/
    
}
