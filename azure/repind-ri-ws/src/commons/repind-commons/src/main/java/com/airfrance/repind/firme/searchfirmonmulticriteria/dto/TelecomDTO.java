package com.airfrance.repind.firme.searchfirmonmulticriteria.dto;

/**
 * DTO
 * Contains Phone number
 * @author t950700
 *
 */
public class TelecomDTO {
	
	/*==========================================*/
	/*           INSTANCE VARIABLES             */
	/*==========================================*/
    protected String terminalType;
    protected String countryCode;
    protected String phoneNumber;
    protected String interNormPhoneNumber;
    protected String unchangedPhoneNumber;
    protected String mediumCode;

    /*==========================================*/
	/*                 ACCESSORS                */
	/*==========================================*/
    /**
     * Gets the value of the terminalType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTerminalType() {
        return terminalType;
    }

    /**
     * Sets the value of the terminalType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTerminalType(String value) {
        this.terminalType = value;
    }

    /**
     * Gets the value of the countryCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCountryCode() {
        return countryCode;
    }

    /**
     * Sets the value of the countryCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCountryCode(String value) {
        this.countryCode = value;
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
     * Gets the value of the interNormPhoneNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInterNormPhoneNumber() {
        return interNormPhoneNumber;
    }

    /**
     * Sets the value of the interNormPhoneNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInterNormPhoneNumber(String value) {
        this.interNormPhoneNumber = value;
    }
    
    /**
     * Gets the value of the unchangedPhoneNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUnchangedPhoneNumber() {
        return unchangedPhoneNumber;
    }

    /**
     * Sets the value of the unchangedPhoneNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
	public void setUnchangedPhoneNumber(String value) {
		this.unchangedPhoneNumber = value;	
	}
	
	/**
     * Gets the value of the mediumCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMediumCode() {
        return mediumCode;
    }

    /**
     * Sets the value of the mediumCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
	public void setMediumCode(String value) {
		this.mediumCode = value;
	}
}
