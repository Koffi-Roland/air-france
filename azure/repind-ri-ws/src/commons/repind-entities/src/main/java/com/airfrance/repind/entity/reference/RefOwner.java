package com.airfrance.repind.entity.reference;

/*PROTECTED REGION ID(_VN2NMPz9EeaexJbSRqCy0Q i) ENABLED START*/

// add not generated imports here

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/*PROTECTED REGION END*/


/**
 * <p>Title : RefOwner.java</p>
 * BO RefOwner
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
@Entity


@Table(name="REF_OWNER")
public class RefOwner implements Serializable {

/*PROTECTED REGION ID(serialUID _VN2NMPz9EeaexJbSRqCy0Q) ENABLED START*/
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
     * ownerId
     */
    @Id
    @Column(name="OWNERID", length=10, nullable=false)
    private Integer ownerId;
        
            
    /**
     * appCode
     */
    @Column(name="SAPPCODE", length=10, nullable=false)
    private String appCode;
        
            
    /**
     * consumerId
     */
    @Column(name="SCONSUMERID", length=12, nullable=false)
    private String consumerId;
        
            
    /**
     * dateCreation
     */
    @Column(name="DDATE_CREATION")
    private Date dateCreation;
        
            
    /**
     * siteCreation
     */
    @Column(name="SSITE_CREATION", length=10)
    private String siteCreation;
        
            
    /**
     * signatureCreation
     */
    @Column(name="SSIGNATURE_CREATION", length=16)
    private String signatureCreation;
        
            
    /**
     * dateModification
     */
    @Column(name="DDATE_MODIFICATION")
    private Date dateModification;
        
            
    /**
     * siteModification
     */
    @Column(name="SSITE_MODIFICATION", length=10)
    private String siteModification;
        
            
    /**
     * signatureModification
     */
    @Column(name="SSIGNATURE_MODIFICATION", length=16)
    private String signatureModification;
        
    /*PROTECTED REGION ID(_VN2NMPz9EeaexJbSRqCy0Q u var) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/
    
    /** 
     * default constructor 
     */
    public RefOwner() {
    	//empty constructor
    }
        
    /** 
     * full constructor
     * @param pOwnerId ownerId
     * @param pAppCode appCode
     * @param pConsumerId consumerId
     * @param pDateCreation dateCreation
     * @param pSiteCreation siteCreation
     * @param pSignatureCreation signatureCreation
     * @param pDateModification dateModification
     * @param pSiteModification siteModification
     * @param pSignatureModification signatureModification
     */
    public RefOwner(Integer pOwnerId, String pAppCode, String pConsumerId, Date pDateCreation, String pSiteCreation, String pSignatureCreation, Date pDateModification, String pSiteModification, String pSignatureModification) {
        this.ownerId = pOwnerId;
        this.appCode = pAppCode;
        this.consumerId = pConsumerId;
        this.dateCreation = pDateCreation;
        this.siteCreation = pSiteCreation;
        this.signatureCreation = pSignatureCreation;
        this.dateModification = pDateModification;
        this.siteModification = pSiteModification;
        this.signatureModification = pSignatureModification;
    }

    /**
     *
     * @return appCode
     */
    public String getAppCode() {
        return this.appCode;
    }

    /**
     *
     * @param pAppCode appCode value
     */
    public void setAppCode(String pAppCode) {
        this.appCode = pAppCode;
    }

    /**
     *
     * @return consumerId
     */
    public String getConsumerId() {
        return this.consumerId;
    }

    /**
     *
     * @param pConsumerId consumerId value
     */
    public void setConsumerId(String pConsumerId) {
        this.consumerId = pConsumerId;
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
     * @return ownerId
     */
    public Integer getOwnerId() {
        return this.ownerId;
    }

    /**
     *
     * @param pOwnerId ownerId value
     */
    public void setOwnerId(Integer pOwnerId) {
        this.ownerId = pOwnerId;
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
        /*PROTECTED REGION ID(toString_VN2NMPz9EeaexJbSRqCy0Q) ENABLED START*/
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
        buffer.append("ownerId=").append(getOwnerId());
        buffer.append(",");
        buffer.append("appCode=").append(getAppCode());
        buffer.append(",");
        buffer.append("consumerId=").append(getConsumerId());
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

     
    
    /*PROTECTED REGION ID(equals hash _VN2NMPz9EeaexJbSRqCy0Q) ENABLED START*/
    
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

    /*PROTECTED REGION ID(_VN2NMPz9EeaexJbSRqCy0Q u m) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/
    
}
