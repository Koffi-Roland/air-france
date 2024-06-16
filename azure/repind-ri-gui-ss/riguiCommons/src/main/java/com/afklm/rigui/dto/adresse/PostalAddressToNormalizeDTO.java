package com.afklm.rigui.dto.adresse;

/*PROTECTED REGION ID(_I3zkILgcEeSmGvH7BtiQjw i) ENABLED START*/

// add not generated imports here

import java.io.Serializable;
import java.util.Date;

/*PROTECTED REGION END*/

/**
 * <p>Title : PostalAddressToNormalizeDTO</p>
 * 
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
public class PostalAddressToNormalizeDTO  implements Serializable {
        
    /**
	 * 
	 */
	private static final long serialVersionUID = -7183388931597413825L;


	/**
     * sain
     */
    private String sain;
        
        
    /**
     * site
     */
    private String site;
        
        
    /**
     * appliErrorCode
     */
    private String appliErrorCode;
        
        
    /**
     * dateLog
     */
    private Date dateLog;
        
        
    /**
     * dateCreation
     */
    private Date dateCreation;
        
        
    /**
     * dateSend
     */
    private Date dateSend;
        
        
    /**
     * dateRecep
     */
    private Date dateRecep;
        
        
    /**
     * status
     */
    private String status;
        
        
    /**
     * postalAddress
     */
    private PostalAddressDTO postalAddress;
        

    /*PROTECTED REGION ID(_I3zkILgcEeSmGvH7BtiQjw u var) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/

    
    
    /** 
     * default constructor 
     */
    public PostalAddressToNormalizeDTO() {
    
    }
    
    
    /** 
     * full constructor
     * @param pSain sain
     * @param pSite site
     * @param pAppliErrorCode appliErrorCode
     * @param pDateLog dateLog
     * @param pDateCreation dateCreation
     * @param pDateSend dateSend
     * @param pDateRecep dateRecep
     * @param pStatus status
     */
    public PostalAddressToNormalizeDTO(String pSain, String pSite, String pAppliErrorCode, Date pDateLog, Date pDateCreation, Date pDateSend, Date pDateRecep, String pStatus) {
        this.sain = pSain;
        this.site = pSite;
        this.appliErrorCode = pAppliErrorCode;
        this.dateLog = pDateLog;
        this.dateCreation = pDateCreation;
        this.dateSend = pDateSend;
        this.dateRecep = pDateRecep;
        this.status = pStatus;
    }

    /**
     *
     * @return appliErrorCode
     */
    public String getAppliErrorCode() {
        return this.appliErrorCode;
    }

    /**
     *
     * @param pAppliErrorCode appliErrorCode value
     */
    public void setAppliErrorCode(String pAppliErrorCode) {
        this.appliErrorCode = pAppliErrorCode;
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
     * @return dateLog
     */
    public Date getDateLog() {
        return this.dateLog;
    }

    /**
     *
     * @param pDateLog dateLog value
     */
    public void setDateLog(Date pDateLog) {
        this.dateLog = pDateLog;
    }

    /**
     *
     * @return dateRecep
     */
    public Date getDateRecep() {
        return this.dateRecep;
    }

    /**
     *
     * @param pDateRecep dateRecep value
     */
    public void setDateRecep(Date pDateRecep) {
        this.dateRecep = pDateRecep;
    }

    /**
     *
     * @return dateSend
     */
    public Date getDateSend() {
        return this.dateSend;
    }

    /**
     *
     * @param pDateSend dateSend value
     */
    public void setDateSend(Date pDateSend) {
        this.dateSend = pDateSend;
    }

    /**
     *
     * @return postalAddress
     */
    public PostalAddressDTO getPostalAddress() {
        return this.postalAddress;
    }

    /**
     *
     * @param pPostalAddress postalAddress value
     */
    public void setPostalAddress(PostalAddressDTO pPostalAddress) {
        this.postalAddress = pPostalAddress;
    }

    /**
     *
     * @return sain
     */
    public String getSain() {
        return this.sain;
    }

    /**
     *
     * @param pSain sain value
     */
    public void setSain(String pSain) {
        this.sain = pSain;
    }

    /**
     *
     * @return site
     */
    public String getSite() {
        return this.site;
    }

    /**
     *
     * @param pSite site value
     */
    public void setSite(String pSite) {
        this.site = pSite;
    }

    /**
     *
     * @return status
     */
    public String getStatus() {
        return this.status;
    }

    /**
     *
     * @param pStatus status value
     */
    public void setStatus(String pStatus) {
        this.status = pStatus;
    }

    /**
     *
     * @return object as string
     */
    public String toString() {
        String result = null;
        /*PROTECTED REGION ID(toString_I3zkILgcEeSmGvH7BtiQjw) ENABLED START*/
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
        buffer.append("sain=").append(getSain());
        buffer.append(",");
        buffer.append("site=").append(getSite());
        buffer.append(",");
        buffer.append("appliErrorCode=").append(getAppliErrorCode());
        buffer.append(",");
        buffer.append("dateLog=").append(getDateLog());
        buffer.append(",");
        buffer.append("dateCreation=").append(getDateCreation());
        buffer.append(",");
        buffer.append("dateSend=").append(getDateSend());
        buffer.append(",");
        buffer.append("dateRecep=").append(getDateRecep());
        buffer.append(",");
        buffer.append("status=").append(getStatus());
        buffer.append("]");
        return buffer.toString();
    }

    /*PROTECTED REGION ID(_I3zkILgcEeSmGvH7BtiQjw u m) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/

}
