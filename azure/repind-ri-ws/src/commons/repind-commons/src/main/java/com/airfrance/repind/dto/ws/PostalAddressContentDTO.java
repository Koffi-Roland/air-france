package com.airfrance.repind.dto.ws;

/*PROTECTED REGION ID(_Alt8wNWwEeef5oRB6XPNlw i) ENABLED START*/

// add not generated imports here
import java.io.Serializable;


/*PROTECTED REGION END*/

/**
 * <p>Title : PostalAddressContentDTO</p>
 * 
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
	public  class PostalAddressContentDTO implements Serializable {


        
    /**
     * corporateName
     */
    private String corporateName;
        
        
    /**
     * numberAndStreet
     */
    private String numberAndStreet;
        
        
    /**
     * additionalInformation
     */
    private String additionalInformation;
        
        
    /**
     * district
     */
    private String district;
        
        
    /**
     * city
     */
    private String city;
        
        
    /**
     * zipCode
     */
    private String zipCode;
        
        
    /**
     * stateCode
     */
    private String stateCode;
        
        
    /**
     * countryCode
     */
    private String countryCode;
        

    /*PROTECTED REGION ID(_Alt8wNWwEeef5oRB6XPNlw u var) ENABLED START*/
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
	    public PostalAddressContentDTO() {
	    //empty constructor
	    }
	    
    /**
     *
     * @return additionalInformation
     */
    public String getAdditionalInformation() {
        return this.additionalInformation;
    }

    /**
     *
     * @param pAdditionalInformation additionalInformation value
     */
    public void setAdditionalInformation(String pAdditionalInformation) {
        this.additionalInformation = pAdditionalInformation;
    }

    /**
     *
     * @return city
     */
    public String getCity() {
        return this.city;
    }

    /**
     *
     * @param pCity city value
     */
    public void setCity(String pCity) {
        this.city = pCity;
    }

    /**
     *
     * @return corporateName
     */
    public String getCorporateName() {
        return this.corporateName;
    }

    /**
     *
     * @param pCorporateName corporateName value
     */
    public void setCorporateName(String pCorporateName) {
        this.corporateName = pCorporateName;
    }

    /**
     *
     * @return countryCode
     */
    public String getCountryCode() {
        return this.countryCode;
    }

    /**
     *
     * @param pCountryCode countryCode value
     */
    public void setCountryCode(String pCountryCode) {
        this.countryCode = pCountryCode;
    }

    /**
     *
     * @return district
     */
    public String getDistrict() {
        return this.district;
    }

    /**
     *
     * @param pDistrict district value
     */
    public void setDistrict(String pDistrict) {
        this.district = pDistrict;
    }

    /**
     *
     * @return numberAndStreet
     */
    public String getNumberAndStreet() {
        return this.numberAndStreet;
    }

    /**
     *
     * @param pNumberAndStreet numberAndStreet value
     */
    public void setNumberAndStreet(String pNumberAndStreet) {
        this.numberAndStreet = pNumberAndStreet;
    }

    /**
     *
     * @return stateCode
     */
    public String getStateCode() {
        return this.stateCode;
    }

    /**
     *
     * @param pStateCode stateCode value
     */
    public void setStateCode(String pStateCode) {
        this.stateCode = pStateCode;
    }

    /**
     *
     * @return zipCode
     */
    public String getZipCode() {
        return this.zipCode;
    }

    /**
     *
     * @param pZipCode zipCode value
     */
    public void setZipCode(String pZipCode) {
        this.zipCode = pZipCode;
    }

    /**
     *
     * @return object as string
     */
    public String toString() {
        String result = null;
        /*PROTECTED REGION ID(toString_Alt8wNWwEeef5oRB6XPNlw) ENABLED START*/
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
        buffer.append("corporateName=").append(getCorporateName());
        buffer.append(",");
        buffer.append("numberAndStreet=").append(getNumberAndStreet());
        buffer.append(",");
        buffer.append("additionalInformation=").append(getAdditionalInformation());
        buffer.append(",");
        buffer.append("district=").append(getDistrict());
        buffer.append(",");
        buffer.append("city=").append(getCity());
        buffer.append(",");
        buffer.append("zipCode=").append(getZipCode());
        buffer.append(",");
        buffer.append("stateCode=").append(getStateCode());
        buffer.append(",");
        buffer.append("countryCode=").append(getCountryCode());
        buffer.append("]");
        return buffer.toString();
    }

    /*PROTECTED REGION ID(_Alt8wNWwEeef5oRB6XPNlw u m) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/

}
