package com.airfrance.repind.firme.searchfirmonmulticriteria.dto;

/**
 * DTO
 * Contains firm name and commercial zones
 * Search output
 * @author t950700
 *
 */
public class FirmNameDTO {

	/*===============================================*/
	/*             INSTANCE VARIABLES                */
	/*===============================================*/
    protected String name;
    protected String location;
    protected String commercialZone;
    protected String nameType;

    /*===============================================*/
	/*                  ACCESSORS                    */
	/*===============================================*/
    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the location property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLocation() {
        return location;
    }

    /**
     * Sets the value of the location property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLocation(String value) {
        this.location = value;
    }

    /**
     * Gets the value of the commercialZone property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCommercialZone() {
        return commercialZone;
    }

    /**
     * Sets the value of the commercialZone property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCommercialZone(String value) {
        this.commercialZone = value;
    }

    /**
     * Gets the value of the nameType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNameType() {
        return nameType;
    }

    /**
     * Sets the value of the nameType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNameType(String value) {
        this.nameType = value;
    }

}
