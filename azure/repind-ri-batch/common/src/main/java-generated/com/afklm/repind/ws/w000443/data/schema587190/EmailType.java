
package com.afklm.repind.ws.w000443.data.schema587190;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for EmailType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="EmailType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="emailKey" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypeEmailKey" minOccurs="0"/>
 *         &lt;element name="version" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypeVersion" minOccurs="0"/>
 *         &lt;element name="mediumStatus" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypeMediaStatus" minOccurs="0"/>
 *         &lt;element name="mediumCode" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypeMediumCode" minOccurs="0"/>
 *         &lt;element name="email" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypeEmail" minOccurs="0"/>
 *         &lt;element name="fbOptin" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypeMailingAuthorisation" minOccurs="0"/>
 *         &lt;element name="additionalInformations" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypeInfosCompl" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EmailType", propOrder = {
    "emailKey",
    "version",
    "mediumStatus",
    "mediumCode",
    "email",
    "fbOptin",
    "additionalInformations"
})
public class EmailType {

    protected String emailKey;
    protected String version;
    protected String mediumStatus;
    protected String mediumCode;
    protected String email;
    protected String fbOptin;
    protected String additionalInformations;

    /**
     * Gets the value of the emailKey property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEmailKey() {
        return emailKey;
    }

    /**
     * Sets the value of the emailKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEmailKey(String value) {
        this.emailKey = value;
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

    /**
     * Gets the value of the additionalInformations property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAdditionalInformations() {
        return additionalInformations;
    }

    /**
     * Sets the value of the additionalInformations property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAdditionalInformations(String value) {
        this.additionalInformations = value;
    }

}
