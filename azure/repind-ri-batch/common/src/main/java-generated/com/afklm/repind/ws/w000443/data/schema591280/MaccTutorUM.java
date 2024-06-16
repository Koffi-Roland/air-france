
package com.afklm.repind.ws.w000443.data.schema591280;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for MaccTutorUM complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="MaccTutorUM">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ginMinor" type="{http://www.af-klm.com/services/passenger/SicMarketingType-v1/xsd}GinType" minOccurs="0"/>
 *         &lt;element name="civilityTutor" type="{http://www.af-klm.com/services/passenger/SicMarketingType-v1/xsd}GenderType" minOccurs="0"/>
 *         &lt;element name="lastnameTutor" type="{http://www.af-klm.com/services/passenger/SicMarketingType-v1/xsd}NameType" minOccurs="0"/>
 *         &lt;element name="firstnameTutor" type="{http://www.af-klm.com/services/passenger/SicMarketingType-v1/xsd}NameType" minOccurs="0"/>
 *         &lt;element name="addressTutor" type="{http://www.af-klm.com/services/passenger/SicMarketingType-v1/xsd}AddressUMType" minOccurs="0"/>
 *         &lt;element name="postCodeTutor" type="{http://www.af-klm.com/services/passenger/SicMarketingType-v1/xsd}PostCodeTutorType" minOccurs="0"/>
 *         &lt;element name="cityTutor" type="{http://www.af-klm.com/services/passenger/SicMarketingType-v1/xsd}CityTutorType" minOccurs="0"/>
 *         &lt;element name="codeProvinceTutor" type="{http://www.af-klm.com/services/passenger/SicMarketingType-v1/xsd}CodeProvinceTutorType" minOccurs="0"/>
 *         &lt;element name="countryCodeTutor" type="{http://www.af-klm.com/services/passenger/SicMarketingType-v1/xsd}CountryCodeTutorType" minOccurs="0"/>
 *         &lt;element name="phoneNumberTutor" type="{http://www.af-klm.com/services/passenger/SicMarketingType-v1/xsd}Phone2Type" minOccurs="0"/>
 *         &lt;element name="dialingCodePhoneTutor" type="{http://www.af-klm.com/services/passenger/SicMarketingType-v1/xsd}DialingCodeType" minOccurs="0"/>
 *         &lt;element name="mobilePhoneTutor" type="{http://www.af-klm.com/services/passenger/SicMarketingType-v1/xsd}Phone2Type" minOccurs="0"/>
 *         &lt;element name="dialingCodeMobileTutor" type="{http://www.af-klm.com/services/passenger/SicMarketingType-v1/xsd}DialingCodeType" minOccurs="0"/>
 *         &lt;element name="orderTutor" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MaccTutorUM", propOrder = {
    "ginMinor",
    "civilityTutor",
    "lastnameTutor",
    "firstnameTutor",
    "addressTutor",
    "postCodeTutor",
    "cityTutor",
    "codeProvinceTutor",
    "countryCodeTutor",
    "phoneNumberTutor",
    "dialingCodePhoneTutor",
    "mobilePhoneTutor",
    "dialingCodeMobileTutor",
    "orderTutor"
})
public class MaccTutorUM {

    protected String ginMinor;
    protected String civilityTutor;
    protected String lastnameTutor;
    protected String firstnameTutor;
    protected String addressTutor;
    protected String postCodeTutor;
    protected String cityTutor;
    protected String codeProvinceTutor;
    protected String countryCodeTutor;
    protected String phoneNumberTutor;
    protected String dialingCodePhoneTutor;
    protected String mobilePhoneTutor;
    protected String dialingCodeMobileTutor;
    protected Integer orderTutor;

    /**
     * Gets the value of the ginMinor property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGinMinor() {
        return ginMinor;
    }

    /**
     * Sets the value of the ginMinor property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGinMinor(String value) {
        this.ginMinor = value;
    }

    /**
     * Gets the value of the civilityTutor property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCivilityTutor() {
        return civilityTutor;
    }

    /**
     * Sets the value of the civilityTutor property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCivilityTutor(String value) {
        this.civilityTutor = value;
    }

    /**
     * Gets the value of the lastnameTutor property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLastnameTutor() {
        return lastnameTutor;
    }

    /**
     * Sets the value of the lastnameTutor property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLastnameTutor(String value) {
        this.lastnameTutor = value;
    }

    /**
     * Gets the value of the firstnameTutor property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFirstnameTutor() {
        return firstnameTutor;
    }

    /**
     * Sets the value of the firstnameTutor property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFirstnameTutor(String value) {
        this.firstnameTutor = value;
    }

    /**
     * Gets the value of the addressTutor property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAddressTutor() {
        return addressTutor;
    }

    /**
     * Sets the value of the addressTutor property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAddressTutor(String value) {
        this.addressTutor = value;
    }

    /**
     * Gets the value of the postCodeTutor property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPostCodeTutor() {
        return postCodeTutor;
    }

    /**
     * Sets the value of the postCodeTutor property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPostCodeTutor(String value) {
        this.postCodeTutor = value;
    }

    /**
     * Gets the value of the cityTutor property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCityTutor() {
        return cityTutor;
    }

    /**
     * Sets the value of the cityTutor property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCityTutor(String value) {
        this.cityTutor = value;
    }

    /**
     * Gets the value of the codeProvinceTutor property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodeProvinceTutor() {
        return codeProvinceTutor;
    }

    /**
     * Sets the value of the codeProvinceTutor property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodeProvinceTutor(String value) {
        this.codeProvinceTutor = value;
    }

    /**
     * Gets the value of the countryCodeTutor property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCountryCodeTutor() {
        return countryCodeTutor;
    }

    /**
     * Sets the value of the countryCodeTutor property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCountryCodeTutor(String value) {
        this.countryCodeTutor = value;
    }

    /**
     * Gets the value of the phoneNumberTutor property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPhoneNumberTutor() {
        return phoneNumberTutor;
    }

    /**
     * Sets the value of the phoneNumberTutor property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPhoneNumberTutor(String value) {
        this.phoneNumberTutor = value;
    }

    /**
     * Gets the value of the dialingCodePhoneTutor property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDialingCodePhoneTutor() {
        return dialingCodePhoneTutor;
    }

    /**
     * Sets the value of the dialingCodePhoneTutor property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDialingCodePhoneTutor(String value) {
        this.dialingCodePhoneTutor = value;
    }

    /**
     * Gets the value of the mobilePhoneTutor property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMobilePhoneTutor() {
        return mobilePhoneTutor;
    }

    /**
     * Sets the value of the mobilePhoneTutor property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMobilePhoneTutor(String value) {
        this.mobilePhoneTutor = value;
    }

    /**
     * Gets the value of the dialingCodeMobileTutor property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDialingCodeMobileTutor() {
        return dialingCodeMobileTutor;
    }

    /**
     * Sets the value of the dialingCodeMobileTutor property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDialingCodeMobileTutor(String value) {
        this.dialingCodeMobileTutor = value;
    }

    /**
     * Gets the value of the orderTutor property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getOrderTutor() {
        return orderTutor;
    }

    /**
     * Sets the value of the orderTutor property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setOrderTutor(Integer value) {
        this.orderTutor = value;
    }

}
