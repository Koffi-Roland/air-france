package com.airfrance.repind.entity.reference;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/*PROTECTED REGION END*/


/**
 * <p>Title : RefFctProOwner.java</p>
 * BO RefFctProOwner
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
@Entity


@Table(name="REF_FCTPRO_OWNER")
public class RefFctProOwner implements java.io.Serializable {

/*PROTECTED REGION ID(serialUID _puOBgDyZEeeO_ZXPvPFEyw) ENABLED START*/
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

    /** the class id*/
    @EmbeddedId
    private com.airfrance.repind.entity.reference.RefFctProOwnerId id;
            
    /**
     * dateCreation
     */
    @Column(name="DDATE_CREATION")
    private java.util.Date dateCreation;
        
            
    /**
     * siteCreation
     */
    @Column(name="SSITE_CREATION")
    private String siteCreation;
        
            
    /**
     * signatureCreation
     */
    @Column(name="SSIGNATURE_CREATION")
    private String signatureCreation;
        
            
    /**
     * dateModification
     */
    @Column(name="DDATE_MODIFICATION")
    private java.util.Date dateModification;
        
            
    /**
     * siteModification
     */
    @Column(name="SSITE_MODIFICATION")
    private String siteModification;
        
            
    /**
     * signatureModification
     */
    @Column(name="SSIGNATURE_MODIFICATION")
    private String signatureModification;
        
    /*PROTECTED REGION ID(_puOBgDyZEeeO_ZXPvPFEyw u var) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/
    
    /** 
     * default constructor 
     */
    public RefFctProOwner() {
    	//empty constructor
    }
        
    /** 
     * full constructor
     * @param refFctProOwnerId primary key
     * @param pDateCreation dateCreation
     * @param pSiteCreation siteCreation
     * @param pSignatureCreation signatureCreation
     * @param pDateModification dateModification
     * @param pSiteModification siteModification
     * @param pSignatureModification signatureModification
     */
    public RefFctProOwner(com.airfrance.repind.entity.reference.RefFctProOwnerId refFctProOwnerId, java.util.Date pDateCreation, String pSiteCreation, String pSignatureCreation, java.util.Date pDateModification, String pSiteModification, String pSignatureModification) {
        this.id = refFctProOwnerId;
        this.dateCreation = pDateCreation;
        this.siteCreation = pSiteCreation;
        this.signatureCreation = pSignatureCreation;
        this.dateModification = pDateModification;
        this.siteModification = pSiteModification;
        this.signatureModification = pSignatureModification;
    }

    public com.airfrance.repind.entity.reference.RefFctProOwnerId getId() {
        return id;
    }

    public void setId(com.airfrance.repind.entity.reference.RefFctProOwnerId id) {
        this.id = id;
    }

    /**
     *
     * @return dateCreation
     */
    public java.util.Date getDateCreation() {
        return this.dateCreation;
    }

    /**
     *
     * @param pDateCreation dateCreation value
     */
    public void setDateCreation(java.util.Date pDateCreation) {
        this.dateCreation = pDateCreation;
    }

    /**
     *
     * @return dateModification
     */
    public java.util.Date getDateModification() {
        return this.dateModification;
    }

    /**
     *
     * @param pDateModification dateModification value
     */
    public void setDateModification(java.util.Date pDateModification) {
        this.dateModification = pDateModification;
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
        /*PROTECTED REGION ID(toString_puOBgDyZEeeO_ZXPvPFEyw) ENABLED START*/
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
        buffer.append("id=").append(getId());
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

     
    
    /*PROTECTED REGION ID(equals hash _puOBgDyZEeeO_ZXPvPFEyw) ENABLED START*/
    
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

    /*PROTECTED REGION ID(_puOBgDyZEeeO_ZXPvPFEyw u m) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/
    
}
