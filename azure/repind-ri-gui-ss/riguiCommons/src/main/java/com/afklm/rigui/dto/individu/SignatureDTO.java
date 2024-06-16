package com.afklm.rigui.dto.individu;

/*PROTECTED REGION ID(_jOs7QEM8EeCk2djT-5OeOA i) ENABLED START*/

// add not generated imports here
import java.io.Serializable;
import java.util.Date;

/*PROTECTED REGION END*/

/**
 * <p>Title : SignatureDTO</p>
 * 
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
public class SignatureDTO  implements Serializable {
        
    /**
	 * 
	 */
	private static final long serialVersionUID = -2042420502796246820L;


	/**
     * typeSignature
     */
    private String typeSignature;
        
        
    /**
     * site
     */
    private String site;
        
        
    /**
     * signature
     */
    private String signature;
        
        
    /**
     * date
     */
    private Date date;
        
        
    /**
     * heure
     */
    private String heure;
        
        
    /**
     * ipAddress
     */
    private String ipAddress;
        
        
    /**
     * applicationCode
     */
    private String applicationCode;
        

    /*PROTECTED REGION ID(_jOs7QEM8EeCk2djT-5OeOA u var) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/

    
    
    /** 
     * default constructor 
     */
    public SignatureDTO() {
    
    }
    
    
    /** 
     * full constructor
     * @param pTypeSignature typeSignature
     * @param pSite site
     * @param pSignature signature
     * @param pDate date
     * @param pHeure heure
     * @param pIpAddress ipAddress
     * @param pApplicationCode applicationCode
     */
    public SignatureDTO(String pTypeSignature, String pSite, String pSignature, Date pDate, String pHeure, String pIpAddress, String pApplicationCode) {
        this.typeSignature = pTypeSignature;
        this.site = pSite;
        this.signature = pSignature;
        this.date = pDate;
        this.heure = pHeure;
        this.ipAddress = pIpAddress;
        this.applicationCode = pApplicationCode;
    }
    
    /** 
     * constructor
     * @param pTypeSignature typeSignature
     * @param pSite site
     * @param pSignature signature
     * @param pDate date
     * @param pHeure heure
     */
    public SignatureDTO(String pTypeSignature, String pSite, String pSignature, Date pDate, String pHeure) {
        this.typeSignature = pTypeSignature;
        this.site = pSite;
        this.signature = pSignature;
        this.date = pDate;
        this.heure = pHeure;
    }

    /**
     *
     * @return applicationCode
     */
    public String getApplicationCode() {
        return this.applicationCode;
    }

    /**
     *
     * @param pApplicationCode applicationCode value
     */
    public void setApplicationCode(String pApplicationCode) {
        this.applicationCode = pApplicationCode;
    }

    /**
     *
     * @return date
     */
    public Date getDate() {
        return this.date;
    }

    /**
     *
     * @param pDate date value
     */
    public void setDate(Date pDate) {
        this.date = pDate;
    }

    /**
     *
     * @return heure
     */
    public String getHeure() {
        return this.heure;
    }

    /**
     *
     * @param pHeure heure value
     */
    public void setHeure(String pHeure) {
        this.heure = pHeure;
    }

    /**
     *
     * @return ipAddress
     */
    public String getIpAddress() {
        return this.ipAddress;
    }

    /**
     *
     * @param pIpAddress ipAddress value
     */
    public void setIpAddress(String pIpAddress) {
        this.ipAddress = pIpAddress;
    }

    /**
     *
     * @return signature
     */
    public String getSignature() {
        return this.signature;
    }

    /**
     *
     * @param pSignature signature value
     */
    public void setSignature(String pSignature) {
        this.signature = pSignature;
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
     * @return typeSignature
     */
    public String getTypeSignature() {
        return this.typeSignature;
    }

    /**
     *
     * @param pTypeSignature typeSignature value
     */
    public void setTypeSignature(String pTypeSignature) {
        this.typeSignature = pTypeSignature;
    }

    /**
     *
     * @return object as string
     */
    public String toString() {
        String result = null;
        /*PROTECTED REGION ID(toString_jOs7QEM8EeCk2djT-5OeOA) ENABLED START*/
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
        buffer.append("typeSignature=").append(getTypeSignature());
        buffer.append(",");
        buffer.append("site=").append(getSite());
        buffer.append(",");
        buffer.append("signature=").append(getSignature());
        buffer.append(",");
        buffer.append("date=").append(getDate());
        buffer.append(",");
        buffer.append("heure=").append(getHeure());
        buffer.append(",");
        buffer.append("ipAddress=").append(getIpAddress());
        buffer.append(",");
        buffer.append("applicationCode=").append(getApplicationCode());
        buffer.append("]");
        return buffer.toString();
    }

    /*PROTECTED REGION ID(_jOs7QEM8EeCk2djT-5OeOA u m) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/

}
