package com.afklm.rigui.dto.individu.adh.searchindividualbymulticriteria;

/*PROTECTED REGION ID(_lGVxkAWdEeegsNhfbw3UgQ i) ENABLED START*/

// add not generated imports here

import org.apache.logging.log4j.util.Strings;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.util.Date;
import java.util.Optional;

/*PROTECTED REGION END*/

/**
 * <p>Title : IdentityDTO</p>
 * 
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
	public  class IdentityDTO implements Serializable {


        
    /**
     * civility
     */
    private String civility;
        
        
    /**
     * lastName
     */
    private String lastName;
        
        
    /**
     * lastNameSearchType
     */
    private String lastNameSearchType;
        
        
    /**
     * firstName
     */
    private String firstName;
        
        
    /**
     * firstNameSearchType
     */
    private String firstNameSearchType;
        
        
    /**
     * birthday
     */
    private Date birthday;
        

    /*PROTECTED REGION ID(_lGVxkAWdEeegsNhfbw3UgQ u var) ENABLED START*/
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
	    public IdentityDTO() {
	    //empty constructor
	    }
	    
    /**
     *
     * @return birthday
     */
    public Date getBirthday() {
        return this.birthday;
    }

    /**
     *
     * @param pBirthday birthday value
     */
    public void setBirthday(Date pBirthday) {
        this.birthday = pBirthday;
    }

    /**
     *
     * @return civility
     */
    public String getCivility() {
        return this.civility;
    }

    /**
     *
     * @param pCivility civility value
     */
    public void setCivility(String pCivility) {
        this.civility = pCivility;
    }

    /**
     *
     * @return firstName
     */
    public String getFirstName() {
        return this.firstName;
    }

    /**
     *
     * @param pFirstName firstName value
     */
    public void setFirstName(String pFirstName) {
        this.firstName = pFirstName;
    }

    /**
     *
     * @return firstNameSearchType
     */
    public String getFirstNameSearchType() {
        return this.firstNameSearchType;
    }

    /**
     *
     * @param pFirstNameSearchType firstNameSearchType value
     */
    public void setFirstNameSearchType(String pFirstNameSearchType) {
        this.firstNameSearchType = pFirstNameSearchType;
    }

    /**
     *
     * @return lastName
     */
    public String getLastName() {
        return this.lastName;
    }

    /**
     *
     * @param pLastName lastName value
     */
    public void setLastName(String pLastName) {
        this.lastName = pLastName;
    }

    /**
     *
     * @return lastNameSearchType
     */
    public String getLastNameSearchType() {
        return this.lastNameSearchType;
    }

    /**
     *
     * @param pLastNameSearchType lastNameSearchType value
     */
    public void setLastNameSearchType(String pLastNameSearchType) {
        this.lastNameSearchType = pLastNameSearchType;
    }

    /**
     *
     * @return object as string
     */
    public String toString() {
        String result = null;
        /*PROTECTED REGION ID(toString_lGVxkAWdEeegsNhfbw3UgQ) ENABLED START*/
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
        buffer.append("civility=").append(getCivility());
        buffer.append(",");
        buffer.append("lastName=").append(getLastName());
        buffer.append(",");
        buffer.append("lastNameSearchType=").append(getLastNameSearchType());
        buffer.append(",");
        buffer.append("firstName=").append(getFirstName());
        buffer.append(",");
        buffer.append("firstNameSearchType=").append(getFirstNameSearchType());
        buffer.append(",");
        buffer.append("birthday=").append(getBirthday());
        buffer.append("]");
        return buffer.toString();
    }

    /*PROTECTED REGION ID(_lGVxkAWdEeegsNhfbw3UgQ u m) ENABLED START*/
    // add your custom methods here if necessary
    public boolean isEmpty() {
    	return Strings.isEmpty(this.civility) && Strings.isEmpty(this.lastName) && Strings.isEmpty(this.firstName) &&
    			this.birthday == null;
    }
    /*PROTECTED REGION END*/

}
