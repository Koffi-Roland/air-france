
package com.afklm.repind.ws.w000842.data.schema572954;

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
 *         &lt;element name="adhesionError" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="marketingError" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="returnCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
    "adhesionError",
    "marketingError",
    "returnCode"
})
public class BusinessError {

    protected String adhesionError;
    protected String marketingError;
    @XmlElement(required = true)
    protected String returnCode;

    /**
     * Gets the value of the adhesionError property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAdhesionError() {
        return adhesionError;
    }

    /**
     * Sets the value of the adhesionError property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAdhesionError(String value) {
        this.adhesionError = value;
    }

    /**
     * Gets the value of the marketingError property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMarketingError() {
        return marketingError;
    }

    /**
     * Sets the value of the marketingError property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMarketingError(String value) {
        this.marketingError = value;
    }

    /**
     * Gets the value of the returnCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReturnCode() {
        return returnCode;
    }

    /**
     * Sets the value of the returnCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReturnCode(String value) {
        this.returnCode = value;
    }

}
