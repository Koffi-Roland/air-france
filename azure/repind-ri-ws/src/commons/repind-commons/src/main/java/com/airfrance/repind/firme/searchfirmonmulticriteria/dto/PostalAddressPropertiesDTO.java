package com.airfrance.repind.firme.searchfirmonmulticriteria.dto;

/**
 * DTO
 * Contains postal address properties
 * @author t950700
 *
 */
public class PostalAddressPropertiesDTO {
	
	/*===============================================*/
	/*             INSTANCE VARIABLES                */
	/*===============================================*/
	protected String ain;
    protected Boolean bypassNormAddress;
    protected String version;
    protected String mediumCode;
    protected String mediumStatus;

    /*===============================================*/
	/*                  ACCESSORS                    */
	/*===============================================*/
    /**
     * Gets the value of the ain property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAin() {
        return ain;
    }

    /**
     * Sets the value of the ain property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAin(String value) {
        this.ain = value;
    }

    /**
     * Gets the value of the bypassNormAddress property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isBypassNormAddress() {
        return bypassNormAddress;
    }

    /**
     * Sets the value of the bypassNormAddress property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setBypassNormAddress(Boolean value) {
        this.bypassNormAddress = value;
    }

    /**
     * Gets the value of the version property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVersion() {
        return version;
    }

    /**
     * Sets the value of the version property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVersion(String value) {
        this.version = value;
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

    /**
     * Gets the value of the mediumStatus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMediumStatus() {
        return mediumStatus;
    }

    /**
     * Sets the value of the mediumStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMediumStatus(String value) {
        this.mediumStatus = value;
    }

}
