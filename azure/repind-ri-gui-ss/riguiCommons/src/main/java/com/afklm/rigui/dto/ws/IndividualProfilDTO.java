package com.afklm.rigui.dto.ws;

/*PROTECTED REGION ID(_Hp4kgNUrEeef5oRB6XPNlw i) ENABLED START*/

// add not generated imports here
import java.io.Serializable;


/*PROTECTED REGION END*/

/**
 * <p>Title : IndividualProfilDTO</p>
 * 
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
	public  class IndividualProfilDTO implements Serializable {


        
    /**
     * emailOptin
     */
    private String emailOptin;
        
        
    /**
     * proAreaCode
     */
    private String proAreaCode;
        
        
    /**
     * proAreaWording
     */
    private String proAreaWording;
        
        
    /**
     * civilianCode
     */
    private String civilianCode;
        
        
    /**
     * languageCode
     */
    private String languageCode;
        
        
    /**
     * carrierCode
     */
    private String carrierCode;
        
        
    /**
     * proFunctionCode
     */
    private String proFunctionCode;
        
        
    /**
     * proFunctionWording
     */
    private String proFunctionWording;
        
        
    /**
     * childrenNumber
     */
    private String childrenNumber;
        
        
    /**
     * customerSegment
     */
    private String customerSegment;
        
        
    /**
     * studentCode
     */
    private String studentCode;
        

    /*PROTECTED REGION ID(_Hp4kgNUrEeef5oRB6XPNlw u var) ENABLED START*/
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
	    public IndividualProfilDTO() {
	    //empty constructor
	    }
	    
    /**
     *
     * @return carrierCode
     */
    public String getCarrierCode() {
        return this.carrierCode;
    }

    /**
     *
     * @param pCarrierCode carrierCode value
     */
    public void setCarrierCode(String pCarrierCode) {
        this.carrierCode = pCarrierCode;
    }

    /**
     *
     * @return childrenNumber
     */
    public String getChildrenNumber() {
        return this.childrenNumber;
    }

    /**
     *
     * @param pChildrenNumber childrenNumber value
     */
    public void setChildrenNumber(String pChildrenNumber) {
        this.childrenNumber = pChildrenNumber;
    }

    /**
     *
     * @return civilianCode
     */
    public String getCivilianCode() {
        return this.civilianCode;
    }

    /**
     *
     * @param pCivilianCode civilianCode value
     */
    public void setCivilianCode(String pCivilianCode) {
        this.civilianCode = pCivilianCode;
    }

    /**
     *
     * @return customerSegment
     */
    public String getCustomerSegment() {
        return this.customerSegment;
    }

    /**
     *
     * @param pCustomerSegment customerSegment value
     */
    public void setCustomerSegment(String pCustomerSegment) {
        this.customerSegment = pCustomerSegment;
    }

    /**
     *
     * @return emailOptin
     */
    public String getEmailOptin() {
        return this.emailOptin;
    }

    /**
     *
     * @param pEmailOptin emailOptin value
     */
    public void setEmailOptin(String pEmailOptin) {
        this.emailOptin = pEmailOptin;
    }

    /**
     *
     * @return languageCode
     */
    public String getLanguageCode() {
        return this.languageCode;
    }

    /**
     *
     * @param pLanguageCode languageCode value
     */
    public void setLanguageCode(String pLanguageCode) {
        this.languageCode = pLanguageCode;
    }

    /**
     *
     * @return proAreaCode
     */
    public String getProAreaCode() {
        return this.proAreaCode;
    }

    /**
     *
     * @param pProAreaCode proAreaCode value
     */
    public void setProAreaCode(String pProAreaCode) {
        this.proAreaCode = pProAreaCode;
    }

    /**
     *
     * @return proAreaWording
     */
    public String getProAreaWording() {
        return this.proAreaWording;
    }

    /**
     *
     * @param pProAreaWording proAreaWording value
     */
    public void setProAreaWording(String pProAreaWording) {
        this.proAreaWording = pProAreaWording;
    }

    /**
     *
     * @return proFunctionCode
     */
    public String getProFunctionCode() {
        return this.proFunctionCode;
    }

    /**
     *
     * @param pProFunctionCode proFunctionCode value
     */
    public void setProFunctionCode(String pProFunctionCode) {
        this.proFunctionCode = pProFunctionCode;
    }

    /**
     *
     * @return proFunctionWording
     */
    public String getProFunctionWording() {
        return this.proFunctionWording;
    }

    /**
     *
     * @param pProFunctionWording proFunctionWording value
     */
    public void setProFunctionWording(String pProFunctionWording) {
        this.proFunctionWording = pProFunctionWording;
    }

    /**
     *
     * @return studentCode
     */
    public String getStudentCode() {
        return this.studentCode;
    }

    /**
     *
     * @param pStudentCode studentCode value
     */
    public void setStudentCode(String pStudentCode) {
        this.studentCode = pStudentCode;
    }

    /**
     *
     * @return object as string
     */
    public String toString() {
        String result = null;
        /*PROTECTED REGION ID(toString_Hp4kgNUrEeef5oRB6XPNlw) ENABLED START*/
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
        buffer.append("emailOptin=").append(getEmailOptin());
        buffer.append(",");
        buffer.append("proAreaCode=").append(getProAreaCode());
        buffer.append(",");
        buffer.append("proAreaWording=").append(getProAreaWording());
        buffer.append(",");
        buffer.append("civilianCode=").append(getCivilianCode());
        buffer.append(",");
        buffer.append("languageCode=").append(getLanguageCode());
        buffer.append(",");
        buffer.append("carrierCode=").append(getCarrierCode());
        buffer.append(",");
        buffer.append("proFunctionCode=").append(getProFunctionCode());
        buffer.append(",");
        buffer.append("proFunctionWording=").append(getProFunctionWording());
        buffer.append(",");
        buffer.append("childrenNumber=").append(getChildrenNumber());
        buffer.append(",");
        buffer.append("customerSegment=").append(getCustomerSegment());
        buffer.append(",");
        buffer.append("studentCode=").append(getStudentCode());
        buffer.append("]");
        return buffer.toString();
    }

    /*PROTECTED REGION ID(_Hp4kgNUrEeef5oRB6XPNlw u m) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/

}
