package com.airfrance.repind.firme.searchfirmonmulticriteria.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * DTO
 * Telecom bloc
 * @author t950700
 *
 */
public class TelecomBlocDTO {
	
	/*==========================================*/
	/*           INSTANCE VARIABLES             */
	/*==========================================*/
	protected TelecomDTO telecom;
    protected List<SignatureDTO> signature;
    protected TelecomStandardizationDTO telecomStandardization;

    /*==========================================*/
	/*                 ACCESSORS                */
	/*==========================================*/
    
    /**
     * Gets the value of the telecom property.
     * 
     * @return
     *     possible object is
     *     {@link Telecom }
     *     
     */
    public TelecomDTO getTelecom() {
        return telecom;
    }

    /**
     * Sets the value of the telecom property.
     * 
     * @param value
     *     allowed object is
     *     {@link Telecom }
     *     
     */
    public void setTelecom(TelecomDTO value) {
        this.telecom = value;
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

    /**
     * Gets the value of the telecomStandardization property.
     * 
     * @return
     *     possible object is
     *     {@link TelecomStandardization }
     *     
     */
    public TelecomStandardizationDTO getTelecomStandardization() {
        return telecomStandardization;
    }

    /**
     * Sets the value of the telecomStandardization property.
     * 
     * @param value
     *     allowed object is
     *     {@link TelecomStandardization }
     *     
     */
    public void setTelecomStandardization(TelecomStandardizationDTO value) {
        this.telecomStandardization = value;
    }

}
