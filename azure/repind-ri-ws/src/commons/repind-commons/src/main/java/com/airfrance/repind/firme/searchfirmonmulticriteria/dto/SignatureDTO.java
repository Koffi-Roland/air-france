package com.airfrance.repind.firme.searchfirmonmulticriteria.dto;

import javax.xml.datatype.XMLGregorianCalendar;

public class SignatureDTO {
	
	/*==========================================*/
	/*           INSTANCE VARIABLES             */
	/*==========================================*/
	protected String signatureType;
    protected String signatureSite;
    protected String signature;
    protected XMLGregorianCalendar date;

    /*==========================================*/
	/*                 ACCESSORS                */
	/*==========================================*/
    
    
    /**
     * Gets the value of the signatureType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSignatureType() {
        return signatureType;
    }

    /**
     * Sets the value of the signatureType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSignatureType(String value) {
        this.signatureType = value;
    }

    /**
     * Gets the value of the signatureSite property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSignatureSite() {
        return signatureSite;
    }

    /**
     * Sets the value of the signatureSite property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSignatureSite(String value) {
        this.signatureSite = value;
    }

    /**
     * Gets the value of the signature property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSignature() {
        return signature;
    }

    /**
     * Sets the value of the signature property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSignature(String value) {
        this.signature = value;
    }

    /**
     * Gets the value of the date property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDate() {
        return date;
    }

    /**
     * Sets the value of the date property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDate(XMLGregorianCalendar value) {
        this.date = value;
    }

}
