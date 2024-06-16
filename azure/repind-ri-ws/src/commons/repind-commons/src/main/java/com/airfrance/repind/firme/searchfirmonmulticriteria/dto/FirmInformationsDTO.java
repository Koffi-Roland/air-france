package com.airfrance.repind.firme.searchfirmonmulticriteria.dto;



/**
 * DTO
 * Contains Firm's informations
 * Search output
 * @author t950700
 *
 */
public class FirmInformationsDTO {

	/*===============================================*/
	/*             INSTANCE VARIABLES                */
	/*===============================================*/
    protected String firmKey;
    protected String firmStatus;
    protected FirmNameDTO firmName;
    protected String phoneNumber;
    protected String email;
    protected PostalAddressBlocResponseDTO postalAddressBloc;
    protected String firmType;
    protected String siretNumber;
    protected String usualName;

    /*===============================================*/
	/*                  ACCESSORS                    */
	/*===============================================*/
    
    /**
     * Gets the value of the firmKey property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFirmKey() {
        return firmKey;
    }

    /**
     * Sets the value of the firmKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFirmKey(String value) {
        this.firmKey = value;
    }

    /**
     * Gets the value of the firmStatus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFirmStatus() {
        return firmStatus;
    }

    /**
     * Sets the value of the firmStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFirmStatus(String value) {
        this.firmStatus = value;
    }

    /**
     * Gets the value of the firmName property.
     * 
     * @return
     *     possible object is
     *     {@link FirmName }
     *     
     */
    public FirmNameDTO getFirmName() {
        return firmName;
    }

    /**
     * Sets the value of the firmName property.
     * 
     * @param value
     *     allowed object is
     *     {@link FirmName }
     *     
     */
    public void setFirmName(FirmNameDTO value) {
        this.firmName = value;
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

    /**
     * Gets the value of the postalAddressBloc property.
     * 
     * @return
     *     possible object is
     *     {@link PostalAddressBloc }
     *     
     */
    public PostalAddressBlocResponseDTO getPostalAddressBloc() {
        return postalAddressBloc;
    }

    /**
     * Sets the value of the postalAddressBloc property.
     * 
     * @param value
     *     allowed object is
     *     {@link PostalAddressBloc }
     *     
     */
    public void setPostalAddressBloc(PostalAddressBlocResponseDTO value) {
        this.postalAddressBloc = value;
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
    /**
     * Gets the value of the siretNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSiretNumber() {
        return siretNumber;
    }

    /**
     * Sets the value of the siretNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSiretNumber(String value) {
        this.siretNumber = value;
    }
    /**
     * Gets the value of the usualName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUsualName() {
        return usualName;
    }

    /**
     * Sets the value of the usualName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUsualName(String value) {
        this.usualName = value;
    }
}
