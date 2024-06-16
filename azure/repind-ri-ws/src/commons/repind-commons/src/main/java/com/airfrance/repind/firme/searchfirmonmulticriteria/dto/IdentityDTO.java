package com.airfrance.repind.firme.searchfirmonmulticriteria.dto;

/**
 * DTO
 * Represents name and firm type criteria
 * Search input
 * @author t950700
 *
 */
public class IdentityDTO {
	
	/*===============================================*/
	/*             INSTANCE VARIABLES                */
	/*===============================================*/
    protected String name;
    protected String nameType;
    protected String nameSearchType;
    protected String firmType;

    
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

    /**
     * Gets the value of the nameSearchType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNameSearchType() {
        return nameSearchType;
    }

    /**
     * Sets the value of the nameSearchType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNameSearchType(String value) {
        this.nameSearchType = value;
    }

    /**
     * Gets the value of the firmType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFirmType() {
        return firmType;
    }

    /**
     * Sets the value of the firmType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFirmType(String value) {
        this.firmType = value;
    }

}
