package com.afklm.rigui.dto.individu.adh.searchindividualbymulticriteria;

/*PROTECTED REGION ID(_ogCVcAgHEeegsNhfbw3UgQ i) ENABLED START*/

// add not generated imports here

import org.apache.commons.lang.StringUtils;

import java.io.Serializable;


/*PROTECTED REGION END*/

/**
 * <p>Title : ContactDTO</p>
 * 
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
	public  class ContactDTO implements Serializable {


        
    /**
     * email
     */
    private String email;
        
        
    /**
     * countryCode
     */
    private String countryCode;
        
        
    /**
     * phoneNumber
     */
    private String phoneNumber;
        
        
    /**
     * postalAddressBloc
     */
    private PostalAddressBlocDTO postalAddressBloc;
        

    /*PROTECTED REGION ID(_ogCVcAgHEeegsNhfbw3UgQ u var) ENABLED START*/
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
	    public ContactDTO() {
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
     * @return postaladdressbloc
     */
    public PostalAddressBlocDTO getPostalAddressBloc() {
        return this.postalAddressBloc;
    }

    /**
     *
     * @param pPostaladdressbloc postaladdressbloc value
     */
    public void setPostalAddressBloc(PostalAddressBlocDTO pPostaladdressbloc) {
        this.postalAddressBloc = pPostaladdressbloc;
    }

    /**
     *
     * @return object as string
     */
    public String toString() {
        String result = null;
        /*PROTECTED REGION ID(toString_ogCVcAgHEeegsNhfbw3UgQ) ENABLED START*/
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
        buffer.append("email=").append(getEmail());
        buffer.append(",");
        buffer.append("countryCode=").append(getCountryCode());
        buffer.append(",");
        buffer.append("phoneNumber=").append(getPhoneNumber());
        buffer.append("]");
        return buffer.toString();
    }

    /*PROTECTED REGION ID(_ogCVcAgHEeegsNhfbw3UgQ u m) ENABLED START*/
    // add your custom methods here if necessary
    public boolean isEmpty() {
    	return StringUtils.isEmpty(this.email) && StringUtils.isEmpty(this.countryCode) && StringUtils.isEmpty(this.phoneNumber) && 
    			(this.postalAddressBloc == null || this.postalAddressBloc.isEmpty());
    }
    
    /*PROTECTED REGION END*/

}
