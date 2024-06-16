package com.airfrance.repind.entity.tracking;

/*PROTECTED REGION ID(_h6iegJYAEea6T-Kx4YCVeA i) ENABLED START*/

// add not generated imports here

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/*PROTECTED REGION END*/


/**
 * <p>Title : TriggerChangeAutMailing.java</p>
 * BO TriggerChangeAutMailing
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
@Entity


@Table(name="TRIGGER_CHANGE_AUT_MAILING")
public class TriggerChangeAutMailing implements Serializable {

/*PROTECTED REGION ID(serialUID _h6iegJYAEea6T-Kx4YCVeA) ENABLED START*/
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
     * id
     */
    @Id
    @Column(name="ID", length=12, nullable=false)
    private Long id;
        
            
    /**
     * gin
     */
    @Column(name="SGIN", length=12)
    private String gin;
        
            
    /**
     * ginPM
     */
    @Column(name="SGIN_PM", length=12)
    private String ginPM;
        
            
    /**
     * emailId
     */
    @Column(name="EMAIL_ID", length=16)
    private String emailId;
        
            
    /**
     * changeType
     */
    @Column(name="CHANGE_TYPE", length=1)
    private String changeType;
        
            
    /**
     * changeError
     */
    @Column(name="CHANGE_ERROR", length=4000)
    private String changeError;
        
            
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
     * dateChange
     */
    @Column(name="DDATE_CHANGE")
    private Date dateChange;
        
            
    /**
     * changeStatus
     */
    @Column(name="CHANGE_STATUS", length=1)
    private String changeStatus;
        
            
    /**
     * oldValue
     */
    @Column(name="OLD_VALUE", length=1)
    private String oldValue;
        
            
    /**
     * newValue
     */
    @Column(name="NEW_VALUE", length=1)
    private String newValue;
        
    /*PROTECTED REGION ID(_h6iegJYAEea6T-Kx4YCVeA u var) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/
    
    /** 
     * default constructor 
     */
    public TriggerChangeAutMailing() {
    	//empty constructor
    }
        
    /** 
     * full constructor
     * @param pId id
     * @param pGin gin
     * @param pGinPM ginPM
     * @param pEmailId emailId
     * @param pChangeType changeType
     * @param pChangeError changeError
     * @param pSignatureModification signatureModification
     * @param pSiteModification siteModification
     * @param pDateChange dateChange
     * @param pChangeStatus changeStatus
     * @param pOldValue oldValue
     * @param pNewValue newValue
     */
    public TriggerChangeAutMailing(Long pId, String pGin, String pGinPM, String pEmailId, String pChangeType, String pChangeError, String pSignatureModification, String pSiteModification, Date pDateChange, String pChangeStatus, String pOldValue, String pNewValue) {
        this.id = pId;
        this.gin = pGin;
        this.ginPM = pGinPM;
        this.emailId = pEmailId;
        this.changeType = pChangeType;
        this.changeError = pChangeError;
        this.signatureModification = pSignatureModification;
        this.siteModification = pSiteModification;
        this.dateChange = pDateChange;
        this.changeStatus = pChangeStatus;
        this.oldValue = pOldValue;
        this.newValue = pNewValue;
    }

    /**
     *
     * @return changeError
     */
    public String getChangeError() {
        return this.changeError;
    }

    /**
     *
     * @param pChangeError changeError value
     */
    public void setChangeError(String pChangeError) {
        this.changeError = pChangeError;
    }

    /**
     *
     * @return changeStatus
     */
    public String getChangeStatus() {
        return this.changeStatus;
    }

    /**
     *
     * @param pChangeStatus changeStatus value
     */
    public void setChangeStatus(String pChangeStatus) {
        this.changeStatus = pChangeStatus;
    }

    /**
     *
     * @return changeType
     */
    public String getChangeType() {
        return this.changeType;
    }

    /**
     *
     * @param pChangeType changeType value
     */
    public void setChangeType(String pChangeType) {
        this.changeType = pChangeType;
    }

    /**
     *
     * @return dateChange
     */
    public Date getDateChange() {
        return this.dateChange;
    }

    /**
     *
     * @param pDateChange dateChange value
     */
    public void setDateChange(Date pDateChange) {
        this.dateChange = pDateChange;
    }

    /**
     *
     * @return emailId
     */
    public String getEmailId() {
        return this.emailId;
    }

    /**
     *
     * @param pEmailId emailId value
     */
    public void setEmailId(String pEmailId) {
        this.emailId = pEmailId;
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
     * @return ginPM
     */
    public String getGinPM() {
        return this.ginPM;
    }

    /**
     *
     * @param pGinPM ginPM value
     */
    public void setGinPM(String pGinPM) {
        this.ginPM = pGinPM;
    }

    /**
     *
     * @return id
     */
    public Long getId() {
        return this.id;
    }

    /**
     *
     * @param pId id value
     */
    public void setId(Long pId) {
        this.id = pId;
    }

    /**
     *
     * @return newValue
     */
    public String getNewValue() {
        return this.newValue;
    }

    /**
     *
     * @param pNewValue newValue value
     */
    public void setNewValue(String pNewValue) {
        this.newValue = pNewValue;
    }

    /**
     *
     * @return oldValue
     */
    public String getOldValue() {
        return this.oldValue;
    }

    /**
     *
     * @param pOldValue oldValue value
     */
    public void setOldValue(String pOldValue) {
        this.oldValue = pOldValue;
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
        /*PROTECTED REGION ID(toString_h6iegJYAEea6T-Kx4YCVeA) ENABLED START*/
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
        buffer.append("gin=").append(getGin());
        buffer.append(",");
        buffer.append("ginPM=").append(getGinPM());
        buffer.append(",");
        buffer.append("emailId=").append(getEmailId());
        buffer.append(",");
        buffer.append("changeType=").append(getChangeType());
        buffer.append(",");
        buffer.append("changeError=").append(getChangeError());
        buffer.append(",");
        buffer.append("signatureModification=").append(getSignatureModification());
        buffer.append(",");
        buffer.append("siteModification=").append(getSiteModification());
        buffer.append(",");
        buffer.append("dateChange=").append(getDateChange());
        buffer.append(",");
        buffer.append("changeStatus=").append(getChangeStatus());
        buffer.append(",");
        buffer.append("oldValue=").append(getOldValue());
        buffer.append(",");
        buffer.append("newValue=").append(getNewValue());
        buffer.append("]");
        return buffer.toString();
    }

     
    
    /*PROTECTED REGION ID(equals hash _h6iegJYAEea6T-Kx4YCVeA) ENABLED START*/
    
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

    /*PROTECTED REGION ID(_h6iegJYAEea6T-Kx4YCVeA u m) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/
    
}
