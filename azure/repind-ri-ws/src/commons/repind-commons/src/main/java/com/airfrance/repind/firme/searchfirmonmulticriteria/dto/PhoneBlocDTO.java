package com.airfrance.repind.firme.searchfirmonmulticriteria.dto;

/**
 * DTO
 * Represents phone number criteria
 * Search input
 * @author t950700
 *
 */
public class PhoneBlocDTO {
	
	/*===============================================*/
	/*             INSTANCE VARIABLES                */
	/*===============================================*/
    protected String country;
    protected String phoneNumber;
    
    protected String normalizedCountry;
    protected String normalizedPhoneNumber;

    /*===============================================*/
	/*                  ACCESSORS                    */
	/*===============================================*/
    /**
     * Gets the value of the country property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCountry() {
        return country;
    }

    /**
     * Sets the value of the country property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCountry(String value) {
        this.country = value;
    }

    /**
     * Gets the value of the phoneNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Sets the value of the phoneNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPhoneNumber(String value) {
        this.phoneNumber = value;
    }

    /**
     * Normalized country
     * @return
     */
	public String getNormalizedCountry() {
		return normalizedCountry;
	}

	public void setNormalizedCountry(String normalizedCountry) {
		this.normalizedCountry = normalizedCountry;
	}
	
	/**
	 * Normalized phone number
	 * @return
	 */
	public String getNormalizedPhoneNumber() {
		return normalizedPhoneNumber;
	}

	public void setNormalizedPhoneNumber(String normalizedPhoneNumber) {
		this.normalizedPhoneNumber = normalizedPhoneNumber;
	}
}
