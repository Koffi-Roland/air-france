package com.afklm.rigui.dto.ws;

/*PROTECTED REGION ID(_mtKYcNWxEeef5oRB6XPNlw i) ENABLED START*/

// add not generated imports here
import java.io.Serializable;


/*PROTECTED REGION END*/

/**
 * <p>Title : TelecomDTO</p>
 * 
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
	public  class TelecomDTO implements Serializable {


        
    /**
     * version
     */
    private String version;
        
        
    /**
     * mediumCode
     */
    private String mediumCode;
        
        
    /**
     * mediumStatus
     */
    private String mediumStatus;
        
        
    /**
     * terminalType
     */
    private String terminalType;
        
        
    /**
     * countryCode
     */
    private String countryCode;
        
        
    /**
     * phoneNumber
     */
    private String phoneNumber;
        

    /*PROTECTED REGION ID(_mtKYcNWxEeef5oRB6XPNlw u var) ENABLED START*/
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
	    public TelecomDTO() {
	    //empty constructor
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
     * @return mediumCode
     */
    public String getMediumCode() {
        return this.mediumCode;
    }

    /**
     *
     * @param pMediumCode mediumCode value
     */
    public void setMediumCode(String pMediumCode) {
        this.mediumCode = pMediumCode;
    }

    /**
     *
     * @return mediumStatus
     */
    public String getMediumStatus() {
        return this.mediumStatus;
    }

    /**
     *
     * @param pMediumStatus mediumStatus value
     */
    public void setMediumStatus(String pMediumStatus) {
        this.mediumStatus = pMediumStatus;
    }

    /**
     *
     * @return phoneNumber
     */
    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    /**
     *
     * @param pPhoneNumber phoneNumber value
     */
    public void setPhoneNumber(String pPhoneNumber) {
        this.phoneNumber = pPhoneNumber;
    }

    /**
     *
     * @return terminalType
     */
    public String getTerminalType() {
        return this.terminalType;
    }

    /**
     *
     * @param pTerminalType terminalType value
     */
    public void setTerminalType(String pTerminalType) {
        this.terminalType = pTerminalType;
    }

    /**
     *
     * @return version
     */
    public String getVersion() {
        return this.version;
    }

    /**
     *
     * @param pVersion version value
     */
    public void setVersion(String pVersion) {
        this.version = pVersion;
    }

    /**
     *
     * @return object as string
     */
    public String toString() {
        String result = null;
        /*PROTECTED REGION ID(toString_mtKYcNWxEeef5oRB6XPNlw) ENABLED START*/
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
        buffer.append("version=").append(getVersion());
        buffer.append(",");
        buffer.append("mediumCode=").append(getMediumCode());
        buffer.append(",");
        buffer.append("mediumStatus=").append(getMediumStatus());
        buffer.append(",");
        buffer.append("terminalType=").append(getTerminalType());
        buffer.append(",");
        buffer.append("countryCode=").append(getCountryCode());
        buffer.append(",");
        buffer.append("phoneNumber=").append(getPhoneNumber());
        buffer.append("]");
        return buffer.toString();
    }

    /*PROTECTED REGION ID(_mtKYcNWxEeef5oRB6XPNlw u m) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/

}
