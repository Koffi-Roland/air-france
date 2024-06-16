
package com.afklm.repind.ws.w000443.data.schema587190;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PostalAddressType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PostalAddressType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="addressKey" type="{http://www.af-klm.com/services/passenger/SicDataType-v1/xsd}DTypeCleAdresse" minOccurs="0"/>
 *         &lt;element name="version" type="{http://www.af-klm.com/services/passenger/SicDataType-v1/xsd}DTypeVersion" minOccurs="0"/>
 *         &lt;element name="usageNumber" type="{http://www.af-klm.com/services/passenger/SicDataType-v1/xsd}DTypeNumeroUsage" minOccurs="0"/>
 *         &lt;element name="mediumCode" type="{http://www.af-klm.com/services/passenger/SicDataType-v1/xsd}DTypeCodeMedium" minOccurs="0"/>
 *         &lt;element name="flagNormAddress" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="mediumStatus" type="{http://www.af-klm.com/services/passenger/SicDataType-v1/xsd}DTypeStatutMedium" minOccurs="0"/>
 *         &lt;element name="corporateName" type="{http://www.af-klm.com/services/passenger/SicDataType-v1/xsd}DTypeRaisonSociale" minOccurs="0"/>
 *         &lt;element name="city" type="{http://www.af-klm.com/services/passenger/SicDataType-v1/xsd}DTypeVille" minOccurs="0"/>
 *         &lt;element name="additionalInformation" type="{http://www.af-klm.com/services/passenger/SicDataType-v1/xsd}DTypeComplAdr" minOccurs="0"/>
 *         &lt;element name="numberAndStreet" type="{http://www.af-klm.com/services/passenger/SicDataType-v1/xsd}DTypeNumeroRue" minOccurs="0"/>
 *         &lt;element name="district" type="{http://www.af-klm.com/services/passenger/SicDataType-v1/xsd}DTypeLieuDit" minOccurs="0"/>
 *         &lt;element name="zipCode" type="{http://www.af-klm.com/services/passenger/SicDataType-v1/xsd}DTypeCodePostal" minOccurs="0"/>
 *         &lt;element name="countryCode" type="{http://www.af-klm.com/services/passenger/SicDataType-v1/xsd}DTypeCodePays" minOccurs="0"/>
 *         &lt;element name="stateCode" type="{http://www.af-klm.com/services/passenger/SicDataType-v1/xsd}DTypeCodeProvince" minOccurs="0"/>
 *         &lt;element name="cityLineFormat" type="{http://www.af-klm.com/services/passenger/SicDataType-v1/xsd}DTypeCityLineFormat" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PostalAddressType", propOrder = {
    "addressKey",
    "version",
    "usageNumber",
    "mediumCode",
    "flagNormAddress",
    "mediumStatus",
    "corporateName",
    "city",
    "additionalInformation",
    "numberAndStreet",
    "district",
    "zipCode",
    "countryCode",
    "stateCode",
    "cityLineFormat"
})
public class PostalAddressType {

    protected String addressKey;
    protected String version;
    protected String usageNumber;
    protected String mediumCode;
    protected Boolean flagNormAddress;
    protected String mediumStatus;
    protected String corporateName;
    protected String city;
    protected String additionalInformation;
    protected String numberAndStreet;
    protected String district;
    protected String zipCode;
    protected String countryCode;
    protected String stateCode;
    protected String cityLineFormat;

    /**
     * Gets the value of the addressKey property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAddressKey() {
        return addressKey;
    }

    /**
     * Sets the value of the addressKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAddressKey(String value) {
        this.addressKey = value;
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
     * Gets the value of the usageNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUsageNumber() {
        return usageNumber;
    }

    /**
     * Sets the value of the usageNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUsageNumber(String value) {
        this.usageNumber = value;
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
     * Gets the value of the flagNormAddress property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isFlagNormAddress() {
        return flagNormAddress;
    }

    /**
     * Sets the value of the flagNormAddress property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setFlagNormAddress(Boolean value) {
        this.flagNormAddress = value;
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
     * Gets the value of the corporateName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCorporateName() {
        return corporateName;
    }

    /**
     * Sets the value of the corporateName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCorporateName(String value) {
        this.corporateName = value;
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
     * Gets the value of the additionalInformation property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAdditionalInformation() {
        return additionalInformation;
    }

    /**
     * Sets the value of the additionalInformation property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAdditionalInformation(String value) {
        this.additionalInformation = value;
    }

    /**
     * Gets the value of the numberAndStreet property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumberAndStreet() {
        return numberAndStreet;
    }

    /**
     * Sets the value of the numberAndStreet property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumberAndStreet(String value) {
        this.numberAndStreet = value;
    }

    /**
     * Gets the value of the district property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDistrict() {
        return district;
    }

    /**
     * Sets the value of the district property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDistrict(String value) {
        this.district = value;
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
     * Gets the value of the cityLineFormat property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCityLineFormat() {
        return cityLineFormat;
    }

    /**
     * Sets the value of the cityLineFormat property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCityLineFormat(String value) {
        this.cityLineFormat = value;
    }

}
