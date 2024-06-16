
package com.afklm.repind.ws.w000443.data.schema591279;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Email complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Email">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="mediumCode" type="{http://www.af-klm.com/services/passenger/SicDataType-v1/xsd}DTypeCodeMedium"/>
 *         &lt;element name="email" type="{http://www.af-klm.com/services/passenger/SicDataType-v1/xsd}DTypeEmail"/>
 *         &lt;element name="fbOptin" type="{http://www.af-klm.com/services/passenger/SicDataType-v1/xsd}DTypeAutoriseMailing"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Email", propOrder = {
    "mediumCode",
    "email",
    "fbOptin"
})
public class Email {

    @XmlElement(required = true)
    protected String mediumCode;
    @XmlElement(required = true)
    protected String email;
    @XmlElement(required = true)
    protected String fbOptin;

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
     * Gets the value of the fbOptin property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFbOptin() {
        return fbOptin;
    }

    /**
     * Sets the value of the fbOptin property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFbOptin(String value) {
        this.fbOptin = value;
    }

}
