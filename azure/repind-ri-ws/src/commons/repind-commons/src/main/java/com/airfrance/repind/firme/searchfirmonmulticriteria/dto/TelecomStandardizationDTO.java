package com.airfrance.repind.firme.searchfirmonmulticriteria.dto;

/**
 * DTO
 * Contains a standardized phone number
 * @author t950700
 *
 */
public class TelecomStandardizationDTO {
	
	/*==========================================*/
	/*           INSTANCE VARIABLES             */
	/*==========================================*/
	protected String normPhoneNumber;
    protected String normCountryCode;

    /*==========================================*/
	/*                 ACCESSORS                */
	/*==========================================*/
    /**
     * Gets the value of the normPhoneNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNormPhoneNumber() {
        return normPhoneNumber;
    }

    /**
     * Sets the value of the normPhoneNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNormPhoneNumber(String value) {
        this.normPhoneNumber = value;
    }

    /**
     * Gets the value of the normCountryCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNormCountryCode() {
        return normCountryCode;
    }

    /**
     * Sets the value of the normCountryCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNormCountryCode(String value) {
        this.normCountryCode = value;
    }

}
