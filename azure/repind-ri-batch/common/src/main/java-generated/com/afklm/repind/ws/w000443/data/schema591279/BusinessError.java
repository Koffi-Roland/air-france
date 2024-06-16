
package com.afklm.repind.ws.w000443.data.schema591279;

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
 *         &lt;element name="returnCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="marketingError" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="adhesionError" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    "returnCode",
    "marketingError",
    "adhesionError"
})
public class BusinessError {

    @XmlElement(required = true)
    protected String returnCode;
    protected String marketingError;
    protected String adhesionError;

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

}
