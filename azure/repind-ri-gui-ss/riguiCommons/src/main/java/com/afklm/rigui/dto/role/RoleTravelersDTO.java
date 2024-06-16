package com.afklm.rigui.dto.role;

/*PROTECTED REGION ID(_i7fq0PceEd-Kx8TJdz7fGwTra i) ENABLED START*/

// add not generated imports here
import java.io.Serializable;
import java.util.Date;

/*PROTECTED REGION END*/

/**
 * <p>Title : RoleTravelersDTO</p>
 * 
 * <p>Copyright : Copyright (c) 2016</p>
 * <p>Company : AIRFRANCE</p>
 */
public class RoleTravelersDTO  implements Serializable {
        
    /**
	 * 
	 */
	private static final long serialVersionUID = -2438969600503589215L;


        
    /**
     * cleRole
     */
    private Integer cleRole;
        
        
    /**
     * gin
     */
    private String gin;
        

    /**
     * lastRecognitionDate
     */
    private Date lastRecognitionDate;
        
        
    /**
     * matchingRecognitionCode
     */
    private String matchingRecognitionCode;
        
        
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
        

    /*PROTECTED REGION ID(_4up_kEjdEeaaO77HTw9BUw u var) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/
    

    
    /** 
     * default constructor 
     */
    public RoleTravelersDTO() {
    
    }
    
    
    /** 
     * full constructor
     * @param pGin gin
     * @param pDateFinValidite dateFinValidite
     * @param pCleRole cleRole
     * @param pDateCreation dateCreation
     * @param pSignatureCreation signatureCreation
     * @param pDateModification dateModification
     * @param pSignatureModification signatureModification
     * @param pSiteCreation siteCreation
     * @param pSiteModification siteModification
     */
    public RoleTravelersDTO(String pGin, Date pLastRecognitionDate, String pMatchingrecognitionCode, Integer pCleRole, Date pDateCreation, String pSignatureCreation, Date pDateModification, String pSignatureModification, String pSiteCreation, String pSiteModification) {
        this.gin = pGin;
        this.lastRecognitionDate = pLastRecognitionDate;
        this.matchingRecognitionCode = pMatchingrecognitionCode;
        this.cleRole = pCleRole;
        this.dateCreation = pDateCreation;
        this.signatureCreation = pSignatureCreation;
        this.dateModification = pDateModification;
        this.signatureModification = pSignatureModification;
        this.siteCreation = pSiteCreation;
        this.siteModification = pSiteModification;
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
     * @return lastRecognitionDate
     */
    public Date getLastRecognitionDate() {
        return this.lastRecognitionDate;
    }

    /**
     *
     * @param pLastRecognitionDate lastRecognitionDate value
     */
    public void setLastRecognitionDate(Date pLastRecognitionDate) {
        this.lastRecognitionDate = pLastRecognitionDate;
    }

    /**
     *
     * @return matchingRecognitionCode
     */
    public String getMatchingRecognitionCode() {
        return this.matchingRecognitionCode;
    }

    /**
     *
     * @param pMatchingRecognitionCode matchingRecognitionCode value
     */
    public void setMatchingRecognitionCode(String pMatchingRecognitionCode) {
        this.matchingRecognitionCode = pMatchingRecognitionCode;
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
        /*PROTECTED REGION ID(toString_4up_kEjdEeaaO77HTw9BUw) ENABLED START*/
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
        buffer.append("gin=").append(getGin());
        buffer.append(",");
        buffer.append("lastRecognitionDate=").append(getLastRecognitionDate());
        buffer.append(",");
        buffer.append("matchingRecognitionCode=").append(getMatchingRecognitionCode());
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

    /*PROTECTED REGION ID(_4up_kEjdEeaaO77HTw9BUw u m) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/

}
