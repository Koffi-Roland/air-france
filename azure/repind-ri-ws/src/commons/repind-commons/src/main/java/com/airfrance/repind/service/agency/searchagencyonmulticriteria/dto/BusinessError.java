package com.airfrance.repind.service.agency.searchagencyonmulticriteria.dto;

/**
 * Business error for SearchAgencyOnMultiCriteria
 * @author t950700
 *
 */
public class BusinessError {

    protected String errorCode;
    protected String faultDescription;
    protected String missingParameter;

    /**
     * Gets the value of the errorCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getErrorCode() {
        return errorCode;
    }

    /**
     * Sets the value of the errorCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setErrorCode(String value) {
        this.errorCode = value;
    }

    /**
     * Gets the value of the faultDescription property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFaultDescription() {
        return faultDescription;
    }

    /**
     * Sets the value of the faultDescription property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFaultDescription(String value) {
        this.faultDescription = value;
    }

    /**
     * Gets the value of the missingParameter property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMissingParameter() {
        return missingParameter;
    }

    /**
     * Sets the value of the missingParameter property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMissingParameter(String value) {
        this.missingParameter = value;
    }

}
