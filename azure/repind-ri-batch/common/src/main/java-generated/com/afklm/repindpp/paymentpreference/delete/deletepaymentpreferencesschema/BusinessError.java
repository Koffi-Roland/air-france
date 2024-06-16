
package com.afklm.repindpp.paymentpreference.delete.deletepaymentpreferencesschema;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for BusinessError complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="BusinessError">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="errorCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="missingParameter" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="faultDescription" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BusinessError", propOrder = {
    "errorCode",
    "missingParameter",
    "faultDescription"
})
public class BusinessError {

    @XmlElement(required = true)
    protected String errorCode;
    protected String missingParameter;
    @XmlElement(required = true)
    protected String faultDescription;

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

}
