package com.airfrance.ref.dto.common;

/*PROTECTED REGION ID(_aLKiYNHaEeOFGI_wKxJAmQ i) ENABLED START*/

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
	private static final long serialVersionUID = 3795330072228535187L;


	/**
     * date
     */
    private Date date;
        
        
    /**
     * signature
     */
    private String signature;
        
        
    /**
     * site
     */
    private String site;
        
        
    /**
     * type
     */
    private String type;
        

    /*PROTECTED REGION ID(_aLKiYNHaEeOFGI_wKxJAmQ u var) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/

    
    
    /** 
     * default constructor 
     */
    public SignatureDTO() {
    
    }
    
    
    /** 
     * full constructor
     * @param pDate date
     * @param pSignature signature
     * @param pSite site
     * @param pType type
     */
    public SignatureDTO(Date pDate, String pSignature, String pSite, String pType) {
        this.date = pDate;
        this.signature = pSignature;
        this.site = pSite;
        this.type = pType;
    }

    /**
     * Instantiates a new Signature dto.
     *
     * @param signature the signature
     * @param site      the site
     */
    public SignatureDTO(String signature, String site) {
        this.signature = signature;
        this.site = site;
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
     * @return type
     */
    public String getType() {
        return this.type;
    }

    /**
     *
     * @param pType type value
     */
    public void setType(String pType) {
        this.type = pType;
    }

    /**
     *
     * @return object as string
     */
    public String toString() {
        String result = null;
        /*PROTECTED REGION ID(toString_aLKiYNHaEeOFGI_wKxJAmQ) ENABLED START*/
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
        buffer.append("date=").append(getDate());
        buffer.append(",");
        buffer.append("signature=").append(getSignature());
        buffer.append(",");
        buffer.append("site=").append(getSite());
        buffer.append(",");
        buffer.append("type=").append(getType());
        buffer.append("]");
        return buffer.toString();
    }

    /*PROTECTED REGION ID(_aLKiYNHaEeOFGI_wKxJAmQ u m) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/

}
