package com.afklm.rigui.dto.ws;

/*PROTECTED REGION ID(_voyuUNW_Eeef5oRB6XPNlw i) ENABLED START*/

// add not generated imports here

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/*PROTECTED REGION END*/

/**
 * <p>Title : MarketLanguageDTO</p>
 * 
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
	public  class MarketLanguageDTO implements Serializable {


        
    /**
     * market
     */
    private String market;
        
        
    /**
     * language
     */
    private String language;
        
        
    /**
     * optIn
     */
    private String optIn;
        
        
    /**
     * dateOfConsent
     */
    private Date dateOfConsent;
        
        
    /**
     * mediaDTO
     */
    private MediaDTO mediaDTO;



    /**
     * signatureDTO
     */
    private List<SignatureDTO> signatureDTOList;


    /*PROTECTED REGION ID(_voyuUNW_Eeef5oRB6XPNlw u var) ENABLED START*/
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
	    public MarketLanguageDTO() {
	    //empty constructor
	    }
	    
    /**
     *
     * @return dateOfConsent
     */
    public Date getDateOfConsent() {
        return this.dateOfConsent;
    }

    /**
     *
     * @param pDateOfConsent dateOfConsent value
     */
    public void setDateOfConsent(Date pDateOfConsent) {
        this.dateOfConsent = pDateOfConsent;
    }

    /**
     *
     * @return language
     */
    public String getLanguage() {
        return this.language;
    }

    /**
     *
     * @param pLanguage language value
     */
    public void setLanguage(String pLanguage) {
        this.language = pLanguage;
    }

    /**
     *
     * @return market
     */
    public String getMarket() {
        return this.market;
    }

    /**
     *
     * @param pMarket market value
     */
    public void setMarket(String pMarket) {
        this.market = pMarket;
    }

    /**
     *
     * @return mediaDTO
     */
    public MediaDTO getMediaDTO() {
        return this.mediaDTO;
    }

    /**
     *
     * @param pMediaDTO mediaDTO value
     */
    public void setMediaDTO(MediaDTO pMediaDTO) {
        this.mediaDTO = pMediaDTO;
    }

    /**
     *
     * @return optIn
     */
    public String getOptIn() {
        return this.optIn;
    }

    /**
     *
     * @param pOptIn optIn value
     */
    public void setOptIn(String pOptIn) {
        this.optIn = pOptIn;
    }

    public List<SignatureDTO> getSignatureDTOList() {
        return signatureDTOList;
    }

    public void setSignatureDTOList(List<SignatureDTO> signatureDTOList) {
        this.signatureDTOList = signatureDTOList;
    }

    /**
     *
     * @return object as string
     */
    public String toString() {
        String result = null;
        /*PROTECTED REGION ID(toString_voyuUNW_Eeef5oRB6XPNlw) ENABLED START*/
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
        buffer.append("market=").append(getMarket());
        buffer.append(",");
        buffer.append("language=").append(getLanguage());
        buffer.append(",");
        buffer.append("optIn=").append(getOptIn());
        buffer.append(",");
        buffer.append("dateOfConsent=").append(getDateOfConsent());
        buffer.append("]");
        return buffer.toString();
    }

    /*PROTECTED REGION ID(_voyuUNW_Eeef5oRB6XPNlw u m) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/

}
