package com.airfrance.repind.entity.tracking;

/*PROTECTED REGION ID(_UzbFMPI-EeSRYp0GhZuE2g i) ENABLED START*/

// add not generated imports here

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/*PROTECTED REGION END*/


/**
 * <p>Title : TriggerChange.java</p>
 * BO TriggerChange
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
@Entity


@Table(name="TRIGGER_CHANGE")
public class TriggerChange implements Serializable {

/*PROTECTED REGION ID(serialUID _UzbFMPI-EeSRYp0GhZuE2g) ENABLED START*/
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
    @Column(name="ID", length=12)
    private Long id;
        
            
    /**
     * gin
     */
    @Column(name="SGIN", length=12)
    private String gin;
        
            
    /**
     * changeTable
     */
    @Column(name="CHANGE_TABLE", length=32)
    private String changeTable;
        
            
    /**
     * changeTableId
     */
    @Column(name="CHANGE_TABLE_ID", length=16)
    private String changeTableId;
        
            
    /**
     * changeType
     */
    @Column(name="CHANGE_TYPE", length=1)
    private String changeType;
        
            
    /**
     * changeTime
     */
    @Column(name="CHANGE_TIME", length=2)
    private String changeTime;
        
            
    /**
     * changeBefore
     */
    @Column(name="CHANGE_BEFORE", length=4000)
    private String changeBefore;
        
            
    /**
     * changeAfter
     */
    @Column(name="CHANGE_AFTER", length=4000)
    private String changeAfter;
        
            
    /**
     * changeError
     */
    @Column(name="CHANGE_ERROR", length=4000)
    private String changeError;
        
            
    /**
     * signatureCreation
     */
    @Column(name="SSIGNATURE_MODIFICATION", length=16)
    private String signatureCreation;
        
            
    /**
     * siteCreation
     */
    @Column(name="SSITE_MODIFICATION", length=10)
    private String siteCreation;
        
            
    /**
     * dateCreation
     */
    @Column(name="DDATE_CREATION")
    private Date dateCreation;
        
    /*PROTECTED REGION ID(_UzbFMPI-EeSRYp0GhZuE2g u var) ENABLED START*/
    /**
     * dateCreation
     */
    @Column(name="CHANGE_STATUS")
    private String changeStatus;
    /*PROTECTED REGION END*/
    
    /** 
     * default constructor 
     */
    public TriggerChange() {
    }
        
    /** 
     * full constructor
     * @param pId id
     * @param pGin gin
     * @param pChangeTable changeTable
     * @param pChangeTableId changeTableId
     * @param pChangeType changeType
     * @param pChangeTime changeTime
     * @param pChangeBefore changeBefore
     * @param pChangeAfter changeAfter
     * @param pChangeError changeError
     * @param pSignatureCreation signatureCreation
     * @param pSiteCreation siteCreation
     * @param pDateCreation dateCreation
     */
    public TriggerChange(Long pId, String pGin, String pChangeTable, String pChangeTableId, String pChangeType, String pChangeTime, String pChangeBefore, String pChangeAfter, String pChangeError, String pSignatureCreation, String pSiteCreation, Date pDateCreation, String pChangeStatus) {
        this.id = pId;
        this.gin = pGin;
        this.changeTable = pChangeTable;
        this.changeTableId = pChangeTableId;
        this.changeType = pChangeType;
        this.changeTime = pChangeTime;
        this.changeBefore = pChangeBefore;
        this.changeAfter = pChangeAfter;
        this.changeError = pChangeError;
        this.signatureCreation = pSignatureCreation;
        this.siteCreation = pSiteCreation;
        this.dateCreation = pDateCreation;
        this.changeStatus = pChangeStatus;
    }

    /**
     *
     * @return changeAfter
     */
    public String getChangeAfter() {
        return this.changeAfter;
    }

    /**
     *
     * @param pChangeAfter changeAfter value
     */
    public void setChangeAfter(String pChangeAfter) {
        this.changeAfter = pChangeAfter;
    }

    /**
     *
     * @return changeBefore
     */
    public String getChangeBefore() {
        return this.changeBefore;
    }

    /**
     *
     * @param pChangeBefore changeBefore value
     */
    public void setChangeBefore(String pChangeBefore) {
        this.changeBefore = pChangeBefore;
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
     * @return changeTable
     */
    public String getChangeTable() {
        return this.changeTable;
    }

    /**
     *
     * @param pChangeTable changeTable value
     */
    public void setChangeTable(String pChangeTable) {
        this.changeTable = pChangeTable;
    }

    /**
     *
     * @return changeTableId
     */
    public String getChangeTableId() {
        return this.changeTableId;
    }

    /**
     *
     * @param pChangeTableId changeTableId value
     */
    public void setChangeTableId(String pChangeTableId) {
        this.changeTableId = pChangeTableId;
    }

    /**
     *
     * @return changeTime
     */
    public String getChangeTime() {
        return this.changeTime;
    }

    /**
     *
     * @param pChangeTime changeTime value
     */
    public void setChangeTime(String pChangeTime) {
        this.changeTime = pChangeTime;
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

    public String getChangeStatus() {
		return changeStatus;
	}

	public void setChangeStatus(String changeStatus) {
		this.changeStatus = changeStatus;
	}

	/**
     *
     * @return object as string
     */
    public String toString() {
        String result = null;
        /*PROTECTED REGION ID(toString_UzbFMPI-EeSRYp0GhZuE2g) ENABLED START*/
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
        buffer.append("changeTable=").append(getChangeTable());
        buffer.append(",");
        buffer.append("changeTableId=").append(getChangeTableId());
        buffer.append(",");
        buffer.append("changeType=").append(getChangeType());
        buffer.append(",");
        buffer.append("changeTime=").append(getChangeTime());
        buffer.append(",");
        buffer.append("changeBefore=").append(getChangeBefore());
        buffer.append(",");
        buffer.append("changeAfter=").append(getChangeAfter());
        buffer.append(",");
        buffer.append("changeError=").append(getChangeError());
        buffer.append(",");
        buffer.append("signatureCreation=").append(getSignatureCreation());
        buffer.append(",");
        buffer.append("siteCreation=").append(getSiteCreation());
        buffer.append(",");
        buffer.append("dateCreation=").append(getDateCreation());
        buffer.append(",");
        buffer.append("changeStatus=").append(getChangeStatus());
        buffer.append("]");
        return buffer.toString();
    }

     
    
    /*PROTECTED REGION ID(equals hash _UzbFMPI-EeSRYp0GhZuE2g) ENABLED START*/
    
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

    /*PROTECTED REGION ID(_UzbFMPI-EeSRYp0GhZuE2g u m) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/
    
}
