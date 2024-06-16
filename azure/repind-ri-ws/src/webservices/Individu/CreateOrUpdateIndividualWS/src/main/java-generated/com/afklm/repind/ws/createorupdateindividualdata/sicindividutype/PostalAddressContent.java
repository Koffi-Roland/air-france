
package com.afklm.repind.ws.createorupdateindividualdata.sicindividutype;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PostalAddressContent complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PostalAddressContent">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="corporateName" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypeBusinessName" minOccurs="0"/>
 *         &lt;element name="numberAndStreet" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypeStreetNumber" minOccurs="0"/>
 *         &lt;element name="additionalInformation" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypeComplAdr" minOccurs="0"/>
 *         &lt;element name="district" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypeLocality" minOccurs="0"/>
 *         &lt;element name="city" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypeCity" minOccurs="0"/>
 *         &lt;element name="zipCode" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypeZipCode" minOccurs="0"/>
 *         &lt;element name="stateCode" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypeProvinceCode" minOccurs="0"/>
 *         &lt;element name="countryCode" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypeCountryCode" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PostalAddressContent", propOrder = {
    "corporateName",
    "numberAndStreet",
    "additionalInformation",
    "district",
    "city",
    "zipCode",
    "stateCode",
    "countryCode"
})
public class PostalAddressContent {

    protected String corporateName;
    protected String numberAndStreet;
    protected String additionalInformation;
    protected String district;
    protected String city;
    protected String zipCode;
    protected String stateCode;
    protected String countryCode;

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

}
