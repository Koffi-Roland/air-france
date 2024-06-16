package com.airfrance.repind.dto.telecom;

/*PROTECTED REGION ID(_zPkHsGYxEeOQF5R-qFxxUQ i) ENABLED START*/

// add not generated imports here
import java.io.Serializable;


/*PROTECTED REGION END*/

/**
 * <p>Title : NormalizePhoneNumberDTO</p>
 * 
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
public class NormalizePhoneNumberDTO  implements Serializable {
        
    /**
	 * 
	 */
	private static final long serialVersionUID = -3891211259486949208L;


	/**
     * normalizedInternationalCountryCode
     */
    private String normalizedInternationalCountryCode;
        
        
    /**
     * normalizedNationalPhoneNumber
     */
    private String normalizedNationalPhoneNumber;
        
        
    /**
     * normalizedNationalPhoneNumberClean
     */
    private String normalizedNationalPhoneNumberClean;
        
        
    /**
     * normalizedInternationalPhoneNumber
     */
    private String normalizedInternationalPhoneNumber;
        
        
    /**
     * normalizedTerminalType
     */
    private String normalizedTerminalType;
        
        
    /**
     * normalizedTerminalTypeDetail
     */
    private String normalizedTerminalTypeDetail;
        

    /*PROTECTED REGION ID(_zPkHsGYxEeOQF5R-qFxxUQ u var) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/

    
    
    /** 
     * default constructor 
     */
    public NormalizePhoneNumberDTO() {
    
    }
    
    
    /** 
     * full constructor
     * @param pNormalizedInternationalCountryCode normalizedInternationalCountryCode
     * @param pNormalizedNationalPhoneNumber normalizedNationalPhoneNumber
     * @param pNormalizedNationalPhoneNumberClean normalizedNationalPhoneNumberClean
     * @param pNormalizedInternationalPhoneNumber normalizedInternationalPhoneNumber
     * @param pNormalizedTerminalType normalizedTerminalType
     * @param pNormalizedTerminalTypeDetail normalizedTerminalTypeDetail
     */
    public NormalizePhoneNumberDTO(String pNormalizedInternationalCountryCode, String pNormalizedNationalPhoneNumber, String pNormalizedNationalPhoneNumberClean, String pNormalizedInternationalPhoneNumber, String pNormalizedTerminalType, String pNormalizedTerminalTypeDetail) {
        this.normalizedInternationalCountryCode = pNormalizedInternationalCountryCode;
        this.normalizedNationalPhoneNumber = pNormalizedNationalPhoneNumber;
        this.normalizedNationalPhoneNumberClean = pNormalizedNationalPhoneNumberClean;
        this.normalizedInternationalPhoneNumber = pNormalizedInternationalPhoneNumber;
        this.normalizedTerminalType = pNormalizedTerminalType;
        this.normalizedTerminalTypeDetail = pNormalizedTerminalTypeDetail;
    }

    /**
     *
     * @return normalizedInternationalCountryCode
     */
    public String getNormalizedInternationalCountryCode() {
        return this.normalizedInternationalCountryCode;
    }

    /**
     *
     * @param pNormalizedInternationalCountryCode normalizedInternationalCountryCode value
     */
    public void setNormalizedInternationalCountryCode(String pNormalizedInternationalCountryCode) {
        this.normalizedInternationalCountryCode = pNormalizedInternationalCountryCode;
    }

    /**
     *
     * @return normalizedInternationalPhoneNumber
     */
    public String getNormalizedInternationalPhoneNumber() {
        return this.normalizedInternationalPhoneNumber;
    }

    /**
     *
     * @param pNormalizedInternationalPhoneNumber normalizedInternationalPhoneNumber value
     */
    public void setNormalizedInternationalPhoneNumber(String pNormalizedInternationalPhoneNumber) {
        this.normalizedInternationalPhoneNumber = pNormalizedInternationalPhoneNumber;
    }

    /**
     *
     * @return normalizedNationalPhoneNumber
     */
    public String getNormalizedNationalPhoneNumber() {
        return this.normalizedNationalPhoneNumber;
    }

    /**
     *
     * @param pNormalizedNationalPhoneNumber normalizedNationalPhoneNumber value
     */
    public void setNormalizedNationalPhoneNumber(String pNormalizedNationalPhoneNumber) {
        this.normalizedNationalPhoneNumber = pNormalizedNationalPhoneNumber;
    }

    /**
     *
     * @return normalizedNationalPhoneNumberClean
     */
    public String getNormalizedNationalPhoneNumberClean() {
        return this.normalizedNationalPhoneNumberClean;
    }

    /**
     *
     * @param pNormalizedNationalPhoneNumberClean normalizedNationalPhoneNumberClean value
     */
    public void setNormalizedNationalPhoneNumberClean(String pNormalizedNationalPhoneNumberClean) {
        this.normalizedNationalPhoneNumberClean = pNormalizedNationalPhoneNumberClean;
    }

    /**
     *
     * @return normalizedTerminalType
     */
    public String getNormalizedTerminalType() {
        return this.normalizedTerminalType;
    }

    /**
     *
     * @param pNormalizedTerminalType normalizedTerminalType value
     */
    public void setNormalizedTerminalType(String pNormalizedTerminalType) {
        this.normalizedTerminalType = pNormalizedTerminalType;
    }

    /**
     *
     * @return normalizedTerminalTypeDetail
     */
    public String getNormalizedTerminalTypeDetail() {
        return this.normalizedTerminalTypeDetail;
    }

    /**
     *
     * @param pNormalizedTerminalTypeDetail normalizedTerminalTypeDetail value
     */
    public void setNormalizedTerminalTypeDetail(String pNormalizedTerminalTypeDetail) {
        this.normalizedTerminalTypeDetail = pNormalizedTerminalTypeDetail;
    }

    /**
     *
     * @return object as string
     */
    public String toString() {
        String result = null;
        /*PROTECTED REGION ID(toString_zPkHsGYxEeOQF5R-qFxxUQ) ENABLED START*/
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
        buffer.append("normalizedInternationalCountryCode=").append(getNormalizedInternationalCountryCode());
        buffer.append(",");
        buffer.append("normalizedNationalPhoneNumber=").append(getNormalizedNationalPhoneNumber());
        buffer.append(",");
        buffer.append("normalizedNationalPhoneNumberClean=").append(getNormalizedNationalPhoneNumberClean());
        buffer.append(",");
        buffer.append("normalizedInternationalPhoneNumber=").append(getNormalizedInternationalPhoneNumber());
        buffer.append(",");
        buffer.append("normalizedTerminalType=").append(getNormalizedTerminalType());
        buffer.append(",");
        buffer.append("normalizedTerminalTypeDetail=").append(getNormalizedTerminalTypeDetail());
        buffer.append("]");
        return buffer.toString();
    }

    /*PROTECTED REGION ID(_zPkHsGYxEeOQF5R-qFxxUQ u m) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/

}
