package com.airfrance.repind.dto.tracking;

/*PROTECTED REGION ID(_h5O18DlFEeaXzKXbfHYeHw i) ENABLED START*/

// add not generated imports here

import java.io.Serializable;
import java.util.Date;

/*PROTECTED REGION END*/

/**
 * <p>Title : TriggerChangeIndividusDTO</p>
 * 
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
	public  class TriggerChangeIndividusDTO implements Serializable {


        
    /**
     * id
     */
    private Long id;
        
        
    /**
     * gin
     */
    private String gin;
        
        
    /**
     * changeTable
     */
    private String changeTable;
        
        
    /**
     * changeTableId
     */
    private String changeTableId;
        
        
    /**
     * changeType
     */
    private String changeType;
        
        
    /**
     * changeTime
     */
    private String changeTime;
        
        
    /**
     * changeError
     */
    private String changeError;
        
        
    /**
     * signatureModification
     */
    private String signatureModification;
        
        
    /**
     * siteModification
     */
    private String siteModification;
        
        
    /**
     * dateChange
     */
    private Date dateChange;
        
        
    /**
     * changeStatus
     */
    private String changeStatus;
        
        
    /**
     * changeBefore
     */
    private String changeBefore;
        
        
    /**
     * changeAfter
     */
    private String changeAfter;
        

    /*PROTECTED REGION ID(_h5O18DlFEeaXzKXbfHYeHw u var) ENABLED START*/
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
	    public TriggerChangeIndividusDTO() {
	    //empty constructor
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
        /*PROTECTED REGION ID(toString_h5O18DlFEeaXzKXbfHYeHw) ENABLED START*/
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
        buffer.append("changeBefore=").append(getChangeBefore());
        buffer.append(",");
        buffer.append("changeAfter=").append(getChangeAfter());
        buffer.append("]");
        return buffer.toString();
    }

    /*PROTECTED REGION ID(_h5O18DlFEeaXzKXbfHYeHw u m) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/

}
