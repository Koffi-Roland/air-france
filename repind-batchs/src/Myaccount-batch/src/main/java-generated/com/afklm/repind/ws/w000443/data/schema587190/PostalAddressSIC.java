
package com.afklm.repind.ws.w000443.data.schema587190;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>Java class for PostalAddressSIC complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PostalAddressSIC">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="indicAdrNorm" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="adresseKey" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypeAddressKey" minOccurs="0"/>
 *         &lt;element name="version" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypeVersion" minOccurs="0"/>
 *         &lt;element name="usageNumber" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypeUsageNumber" minOccurs="0"/>
 *         &lt;element name="mediumCode" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypeMediumCode" minOccurs="0"/>
 *         &lt;element name="mediumStatus" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypeMediaStatus" minOccurs="0"/>
 *         &lt;element name="corporateName" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypeBusinessName" minOccurs="0"/>
 *         &lt;element name="city" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypeCity" minOccurs="0"/>
 *         &lt;element name="additionalInformation" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypeComplAdr" minOccurs="0"/>
 *         &lt;element name="numberAndStreet" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypeStreetNumber" minOccurs="0"/>
 *         &lt;element name="district" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypeLocality" minOccurs="0"/>
 *         &lt;element name="zipCode" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypeZipCode" minOccurs="0"/>
 *         &lt;element name="countryCode" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypeCountryCode" minOccurs="0"/>
 *         &lt;element name="stateCode" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypeProvinceCode" minOccurs="0"/>
 *         &lt;element name="addressRole" type="{http://www.af-klm.com/services/passenger/SicIndividuType-v1/xsd}AddressRole" maxOccurs="5" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PostalAddressSIC", propOrder = {
    "indicAdrNorm",
    "adresseKey",
    "version",
    "usageNumber",
    "mediumCode",
    "mediumStatus",
    "corporateName",
    "city",
    "additionalInformation",
    "numberAndStreet",
    "district",
    "zipCode",
    "countryCode",
    "stateCode",
    "addressRole"
})
public class PostalAddressSIC {

    protected Boolean indicAdrNorm;
    protected String adresseKey;
    protected String version;
    protected String usageNumber;
    protected String mediumCode;
    protected String mediumStatus;
    protected String corporateName;
    protected String city;
    protected String additionalInformation;
    protected String numberAndStreet;
    protected String district;
    protected String zipCode;
    protected String countryCode;
    protected String stateCode;
    protected List<AddressRole> addressRole;

    /**
     * Gets the value of the indicAdrNorm property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIndicAdrNorm() {
        return indicAdrNorm;
    }

    /**
     * Sets the value of the indicAdrNorm property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIndicAdrNorm(Boolean value) {
        this.indicAdrNorm = value;
    }

    /**
     * Gets the value of the adresseKey property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAdresseKey() {
        return adresseKey;
    }

    /**
     * Sets the value of the adresseKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAdresseKey(String value) {
        this.adresseKey = value;
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
     * Gets the value of the addressRole property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the addressRole property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAddressRole().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link AddressRole }
     * 
     * 
     */
    public List<AddressRole> getAddressRole() {
        if (addressRole == null) {
            addressRole = new ArrayList<AddressRole>();
        }
        return this.addressRole;
    }

}
