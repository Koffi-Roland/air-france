package com.airfrance.repind.service.agency.searchagencyonmulticriteria.dto;

/**
 * DTO
 * Represents identification criteria  (GIN, SIRET, SIREN, ...)
 * Search input
 * @author t950700
 *
 */
public class IdentificationDTO {
	
	/*===============================================*/
	/*             INSTANCE VARIABLES                */
	/*===============================================*/
    protected String identificationType;
    protected String identificationValue;

    /*===============================================*/
	/*                  ACCESSORS                    */
	/*===============================================*/
    /**
     * Gets the value of the identificationType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdentificationType() {
        return identificationType;
    }

    /**
     * Sets the value of the identificationType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdentificationType(String value) {
        this.identificationType = value;
    }

    /**
     * Gets the value of the identificationValue property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdentificationValue() {
        return identificationValue;
    }

    /**
     * Sets the value of the identificationValue property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdentificationValue(String value) {
        this.identificationValue = value;
    }

}
