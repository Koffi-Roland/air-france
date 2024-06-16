package com.airfrance.repind.dto.ws;

/*PROTECTED REGION ID(_Dvo7ENWwEeef5oRB6XPNlw i) ENABLED START*/

// add not generated imports here
import java.io.Serializable;


/*PROTECTED REGION END*/

/**
 * <p>Title : UsageAddressDTO</p>
 * 
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
	public  class UsageAddressDTO implements Serializable {


        
    /**
     * applicationCode
     */
    private String applicationCode;
        
        
    /**
     * usageNumber
     */
    private String usageNumber;
        
        
    /**
     * addressRoleCode
     */
    private String addressRoleCode;
        

    /*PROTECTED REGION ID(_Dvo7ENWwEeef5oRB6XPNlw u var) ENABLED START*/
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
	    public UsageAddressDTO() {
	    //empty constructor
	    }
	    
    /**
     *
     * @return addressRoleCode
     */
    public String getAddressRoleCode() {
        return this.addressRoleCode;
    }

    /**
     *
     * @param pAddressRoleCode addressRoleCode value
     */
    public void setAddressRoleCode(String pAddressRoleCode) {
        this.addressRoleCode = pAddressRoleCode;
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
     * @return usageNumber
     */
    public String getUsageNumber() {
        return this.usageNumber;
    }

    /**
     *
     * @param pUsageNumber usageNumber value
     */
    public void setUsageNumber(String pUsageNumber) {
        this.usageNumber = pUsageNumber;
    }

    /**
     *
     * @return object as string
     */
    public String toString() {
        String result = null;
        /*PROTECTED REGION ID(toString_Dvo7ENWwEeef5oRB6XPNlw) ENABLED START*/
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
        buffer.append("applicationCode=").append(getApplicationCode());
        buffer.append(",");
        buffer.append("usageNumber=").append(getUsageNumber());
        buffer.append(",");
        buffer.append("addressRoleCode=").append(getAddressRoleCode());
        buffer.append("]");
        return buffer.toString();
    }

    /*PROTECTED REGION ID(_Dvo7ENWwEeef5oRB6XPNlw u m) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/

}
