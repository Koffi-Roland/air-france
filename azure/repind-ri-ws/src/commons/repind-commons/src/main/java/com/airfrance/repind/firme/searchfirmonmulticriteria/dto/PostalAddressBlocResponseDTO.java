package com.airfrance.repind.firme.searchfirmonmulticriteria.dto;

import java.util.ArrayList;
import java.util.List;


/**
 * DTO
 * Wrapper for all postal address data
 * returned in the search result
 * @author t950700
 *
 */
public class PostalAddressBlocResponseDTO {
	
	/*===============================================*/
	/*             INSTANCE VARIABLES                */
	/*===============================================*/
	protected PostalAddressPropertiesDTO postalAddressProperties;
    protected PostalAddressContentDTO postalAddressContent;
    protected List<SignatureDTO> signature;

    /*===============================================*/
	/*                  ACCESSORS                    */
	/*===============================================*/
    
    /**
     * Gets the value of the postalAddressProperties property.
     * 
     * @return
     *     possible object is
     *     {@link PostalAddressProperties }
     *     
     */
    public PostalAddressPropertiesDTO getPostalAddressProperties() {
        return postalAddressProperties;
    }

    /**
     * Sets the value of the postalAddressProperties property.
     * 
     * @param value
     *     allowed object is
     *     {@link PostalAddressProperties }
     *     
     */
    public void setPostalAddressProperties(PostalAddressPropertiesDTO value) {
        this.postalAddressProperties = value;
    }

    /**
     * Gets the value of the postalAddressContent property.
     * 
     * @return
     *     possible object is
     *     {@link PostalAddressContent }
     *     
     */
    public PostalAddressContentDTO getPostalAddressContent() {
        return postalAddressContent;
    }

    /**
     * Sets the value of the postalAddressContent property.
     * 
     * @param value
     *     allowed object is
     *     {@link PostalAddressContent }
     *     
     */
    public void setPostalAddressContent(PostalAddressContentDTO value) {
        this.postalAddressContent = value;
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
