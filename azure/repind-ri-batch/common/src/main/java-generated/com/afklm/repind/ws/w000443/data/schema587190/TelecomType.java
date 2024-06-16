
package com.afklm.repind.ws.w000443.data.schema587190;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TelecomType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TelecomType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="telecomKey" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypePhoneKey" minOccurs="0"/>
 *         &lt;element name="version" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypeVersion" minOccurs="0"/>
 *         &lt;element name="mediumStatus" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypeMediaStatus" minOccurs="0"/>
 *         &lt;element name="mediumCode" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypeMediumCode" minOccurs="0"/>
 *         &lt;element name="terminalType" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypeTerminal" minOccurs="0"/>
 *         &lt;element name="countryCode" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypeCountryId" minOccurs="0"/>
 *         &lt;element name="regionCode" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypeRegionId" minOccurs="0"/>
 *         &lt;element name="phoneNumber" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypePhoneNumber" minOccurs="0"/>
 *         &lt;element name="normalizedPhoneNumber" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypePhoneNumber" minOccurs="0"/>
 *         &lt;element name="normalizedCountryCode" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypeCountryId" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TelecomType", propOrder = {
    "telecomKey",
    "version",
    "mediumStatus",
    "mediumCode",
    "terminalType",
    "countryCode",
    "regionCode",
    "phoneNumber",
    "normalizedPhoneNumber",
    "normalizedCountryCode"
})
public class TelecomType {

    protected String telecomKey;
    protected String version;
    protected String mediumStatus;
    protected String mediumCode;
    protected String terminalType;
    protected String countryCode;
    protected String regionCode;
    protected String phoneNumber;
    protected String normalizedPhoneNumber;
    protected String normalizedCountryCode;

    /**
     * Gets the value of the telecomKey property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTelecomKey() {
        return telecomKey;
    }

    /**
     * Sets the value of the telecomKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTelecomKey(String value) {
        this.telecomKey = value;
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
     * Gets the value of the terminalType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTerminalType() {
        return terminalType;
    }

    /**
     * Sets the value of the terminalType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTerminalType(String value) {
        this.terminalType = value;
    }

    /**
     * Gets the value of the countryCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCountryCode() {
        return countryCode;
    }

    /**
     * Sets the value of the countryCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCountryCode(String value) {
        this.countryCode = value;
    }

    /**
     * Gets the value of the regionCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRegionCode() {
        return regionCode;
    }

    /**
     * Sets the value of the regionCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRegionCode(String value) {
        this.regionCode = value;
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
     * Gets the value of the normalizedPhoneNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNormalizedPhoneNumber() {
        return normalizedPhoneNumber;
    }

    /**
     * Sets the value of the normalizedPhoneNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNormalizedPhoneNumber(String value) {
        this.normalizedPhoneNumber = value;
    }

    /**
     * Gets the value of the normalizedCountryCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNormalizedCountryCode() {
        return normalizedCountryCode;
    }

    /**
     * Sets the value of the normalizedCountryCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNormalizedCountryCode(String value) {
        this.normalizedCountryCode = value;
    }

}
