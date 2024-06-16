package com.airfrance.repind.firme.searchfirmonmulticriteria.dto;





/**
 * DTO
 * Represents postal address criteria
 * Search input
 * @author t950700
 *
 */
public class PostalAddressBlocDTO {
	
	/*===============================================*/
	/*             INSTANCE VARIABLES                */
	/*===============================================*/
    protected String numberAndStreet;
    protected String city;
    protected String citySearchType;
    protected String zipCode;
    protected String zipSearchType;
    protected String stateCode;
    protected String countryCode;

    /*===============================================*/
	/*                  ACCESSORS                    */
	/*===============================================*/
    
    /**
     * Gets the value of the numberAndStreet property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumberAndStreet() {
        return numberAndStreet;
    }

    /**
     * Sets the value of the numberAndStreet property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumberAndStreet(String value) {
        this.numberAndStreet = value;
    }

    /**
     * Gets the value of the city property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCity() {
        return city;
    }

    /**
     * Sets the value of the city property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCity(String value) {
        this.city = value;
    }

    /**
     * Gets the value of the citySearchType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCitySearchType() {
        return citySearchType;
    }

    /**
     * Sets the value of the citySearchType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCitySearchType(String value) {
        this.citySearchType = value;
    }

    /**
     * Gets the value of the zipCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getZipCode() {
        return zipCode;
    }

    /**
     * Sets the value of the zipCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setZipCode(String value) {
        this.zipCode = value;
    }

    /**
     * Gets the value of the zipSearchType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getZipSearchType() {
        return zipSearchType;
    }

    /**
     * Sets the value of the zipSearchType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setZipSearchType(String value) {
        this.zipSearchType = value;
    }

    /**
     * Gets the value of the stateCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStateCode() {
        return stateCode;
    }

    /**
     * Sets the value of the stateCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStateCode(String value) {
        this.stateCode = value;
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
}
