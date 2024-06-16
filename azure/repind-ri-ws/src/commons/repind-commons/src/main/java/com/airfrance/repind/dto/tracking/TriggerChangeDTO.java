package com.airfrance.repind.dto.tracking;

/*PROTECTED REGION ID(_NKV-gPI_EeSRYp0GhZuE2g i) ENABLED START*/

// add not generated imports here

import java.io.Serializable;
import java.util.Date;

/*PROTECTED REGION END*/

/**
 * <p>Title : TriggerChangeDTO</p>
 * 
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
public class TriggerChangeDTO  implements Serializable {
        
    /**
	 * 
	 */
	private static final long serialVersionUID = 1526218655544327632L;


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
     * changeBefore
     */
    private String changeBefore;
        
        
    /**
     * changeAfter
     */
    private String changeAfter;
        
        
    /**
     * changeError
     */
    private String changeError;
        
        
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
        

    /*PROTECTED REGION ID(_NKV-gPI_EeSRYp0GhZuE2g u var) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/

    
    
    /** 
     * default constructor 
     */
    public TriggerChangeDTO() {
    
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
    public TriggerChangeDTO(Long pId, String pGin, String pChangeTable, String pChangeTableId, String pChangeType, String pChangeTime, String pChangeBefore, String pChangeAfter, String pChangeError, String pSignatureCreation, String pSiteCreation, Date pDateCreation) {
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

    /**
     *
     * @return object as string
     */
    public String toString() {
        String result = null;
        /*PROTECTED REGION ID(toString_NKV-gPI_EeSRYp0GhZuE2g) ENABLED START*/
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
        buffer.append("]");
        return buffer.toString();
    }

    /*PROTECTED REGION ID(_NKV-gPI_EeSRYp0GhZuE2g u m) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/

}
