
package com.afklm.repind.ws.w000443.data.schema571954;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for LocalizationData complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="LocalizationData">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="postalAddress" type="{http://www.af-klm.com/services/passenger/ProspectDataType-v1/xsd}DTypePostalAddress" minOccurs="0"/>
 *         &lt;element name="zipCode" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypeZipCode" minOccurs="0"/>
 *         &lt;element name="city" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypeCity" minOccurs="0"/>
 *         &lt;element name="stateCode" type="{http://www.af-klm.com/services/passenger/ProspectDataType-v1/xsd}DTypeStateCode" minOccurs="0"/>
 *         &lt;element name="countryCode" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypeCountryCode" minOccurs="0"/>
 *         &lt;element name="mediumStatus" type="{http://www.af-klm.com/services/passenger/ProspectDataType-v1/xsd}DTypeMediumStatus" minOccurs="0"/>
 *         &lt;element name="mediumCode" type="{http://www.af-klm.com/services/passenger/ProspectDataType-v1/xsd}DTypeMediumCode" minOccurs="0"/>
 *         &lt;element name="preferredAirport" type="{http://www.af-klm.com/services/passenger/ProspectDataType-v1/xsd}DTypePreferedAirport" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "LocalizationData", propOrder = {
    "postalAddress",
    "zipCode",
    "city",
    "stateCode",
    "countryCode",
    "mediumStatus",
    "mediumCode",
    "preferredAirport"
})
public class LocalizationData {

    protected String postalAddress;
    protected String zipCode;
    protected String city;
    protected String stateCode;
    protected String countryCode;
    protected String mediumStatus;
    protected String mediumCode;
    protected String preferredAirport;

    /**
     * Gets the value of the postalAddress property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPostalAddress() {
        return postalAddress;
    }

    /**
     * Sets the value of the postalAddress property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPostalAddress(String value) {
        this.postalAddress = value;
    }

    /**
     * Gets the value of the zipCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getZipCode() {
        return zipCode;
    }

    /**
     * Sets the value of the zipCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setZipCode(String value) {
        this.zipCode = value;
    }

    /**
     * Gets the value of the city property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCity() {
        return city;
    }

    /**
     * Sets the value of the city property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCity(String value) {
        this.city = value;
    }

    /**
     * Gets the value of the stateCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStateCode() {
        return stateCode;
    }

    /**
     * Sets the value of the stateCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStateCode(String value) {
        this.stateCode = value;
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
     * Gets the value of the preferredAirport property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPreferredAirport() {
        return preferredAirport;
    }

    /**
     * Sets the value of the preferredAirport property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPreferredAirport(String value) {
        this.preferredAirport = value;
    }

}
