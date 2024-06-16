package com.airfrance.repind.service.agency.searchagencyonmulticriteria.dto;

/**
 * DTO
 * Email criteria
 * Search input
 * @author t950700
 *
 */
public class EmailBlocDTO {

	/*===============================================*/
	/*             INSTANCE VARIABLES                */
	/*===============================================*/
    protected String email;

    /*===============================================*/
	/*                  ACCESSORS                    */
	/*===============================================*/
    /**
     * Gets the value of the email property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the value of the email property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEmail(String value) {
        this.email = value;
    }
}
