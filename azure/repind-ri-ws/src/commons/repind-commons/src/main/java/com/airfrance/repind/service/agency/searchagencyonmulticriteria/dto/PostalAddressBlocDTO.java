package com.airfrance.repind.service.agency.searchagencyonmulticriteria.dto;

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
    PostalAddressPropertiesDTO postalAddressPropertiesDTO;
    PostalAddressContentDTO postalAddressContentDTO;

    /*===============================================*/
	/*                  CONSTRUCTORS                 */
	/*===============================================*/
    
    public PostalAddressBlocDTO() {
		super();
	}
    
    public PostalAddressBlocDTO(String numberAndStreet, String city,
			String citySearchType, String zipCode, String zipSearchType,
			String stateCode, String countryCode,
			PostalAddressPropertiesDTO postalAddressPropertiesDTO,
			PostalAddressContentDTO postalAddressContentDTO) {
		super();
		this.numberAndStreet = numberAndStreet;
		this.city = city;
		this.citySearchType = citySearchType;
		this.zipCode = zipCode;
		this.zipSearchType = zipSearchType;
		this.stateCode = stateCode;
		this.countryCode = countryCode;
		this.postalAddressContentDTO = postalAddressContentDTO;
		this.postalAddressPropertiesDTO = postalAddressPropertiesDTO;
	}

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

	public PostalAddressPropertiesDTO getPostalAddressPropertiesDTO() {
		return postalAddressPropertiesDTO;
	}

	public void setPostalAddressPropertiesDTO(
			PostalAddressPropertiesDTO postalAddressPropertiesDTO) {
		this.postalAddressPropertiesDTO = postalAddressPropertiesDTO;
	}

	public PostalAddressContentDTO getPostalAddressContentDTO() {
		return postalAddressContentDTO;
	}

	public void setPostalAddressContentDTO(
			PostalAddressContentDTO postalAddressContentDTO) {
		this.postalAddressContentDTO = postalAddressContentDTO;
	}


}