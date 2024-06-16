package com.airfrance.repind.dto.ws;

/*PROTECTED REGION ID(_a-uwgNWzEeef5oRB6XPNlw i) ENABLED START*/

// add not generated imports here
import java.io.Serializable;


/*PROTECTED REGION END*/

/**
 * <p>Title : EmailDTO</p>
 * 
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
	public  class EmailDTO implements Serializable {


        
    /**
     * version
     */
    private String version;
        
        
    /**
     * mediumStatus
     */
    private String mediumStatus;
        
        
    /**
     * mediumCode
     */
    private String mediumCode;
        
        
    /**
     * email
     */
    private String email;
        
        
    /**
     * emailOptin
     */
    private String emailOptin;
        

    /*PROTECTED REGION ID(_a-uwgNWzEeef5oRB6XPNlw u var) ENABLED START*/
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
	    public EmailDTO() {
	    //empty constructor
	    }
	    
    /**
     *
     * @return email
     */
    public String getEmail() {
        return this.email;
    }

    /**
     *
     * @param pEmail email value
     */
    public void setEmail(String pEmail) {
        this.email = pEmail;
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
        /*PROTECTED REGION ID(toString_a-uwgNWzEeef5oRB6XPNlw) ENABLED START*/
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
        buffer.append("mediumStatus=").append(getMediumStatus());
        buffer.append(",");
        buffer.append("mediumCode=").append(getMediumCode());
        buffer.append(",");
        buffer.append("email=").append(getEmail());
        buffer.append(",");
        buffer.append("emailOptin=").append(getEmailOptin());
        buffer.append("]");
        return buffer.toString();
    }

    /*PROTECTED REGION ID(_a-uwgNWzEeef5oRB6XPNlw u m) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/

}