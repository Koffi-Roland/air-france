package com.airfrance.repind.firme.searchfirmonmulticriteria.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * DTO
 * Contains email data returned as part of
 * the search result
 * @author t950700
 *
 */
public class EmailDTO {
	
	/*===============================================*/
	/*             INSTANCE VARIABLES                */
	/*===============================================*/
	protected String ain;
    protected String version;
    protected String mediumStatus;
    protected String mediumCode;
    protected String email;
    protected String mailingAuthorized;
    protected String complementaryInformation;
    protected List<SignatureDTO> signature;

    
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
     * Gets the value of the mailingAuthorized property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMailingAuthorized() {
        return mailingAuthorized;
    }

    /**
     * Sets the value of the mailingAuthorized property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMailingAuthorized(String value) {
        this.mailingAuthorized = value;
    }

    /**
     * Gets the value of the complementaryInformation property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getComplementaryInformation() {
        return complementaryInformation;
    }

    /**
     * Sets the value of the complementaryInformation property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setComplementaryInformation(String value) {
        this.complementaryInformation = value;
    }

    /**
     * Gets the value of the signature property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the signature property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSignature().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Signature }
     * 
     * 
     */
    public List<SignatureDTO> getSignature() {
        if (signature == null) {
            signature = new ArrayList<SignatureDTO>();
        }
        return this.signature;
    }

}
