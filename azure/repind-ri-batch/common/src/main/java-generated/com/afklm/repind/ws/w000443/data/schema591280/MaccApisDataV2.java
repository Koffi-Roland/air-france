
package com.afklm.repind.ws.w000443.data.schema591280;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for MaccApisDataV2 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="MaccApisDataV2">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="lastName" type="{http://www.af-klm.com/services/passenger/SicMarketingType-v1/xsd}NameType" minOccurs="0"/>
 *         &lt;element name="firstName" type="{http://www.af-klm.com/services/passenger/SicMarketingType-v1/xsd}NameType" minOccurs="0"/>
 *         &lt;element name="dateOfBirth" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="gender" type="{http://www.af-klm.com/services/passenger/SicMarketingType-v1/xsd}GenderType" minOccurs="0"/>
 *         &lt;element name="greenCardNumber" type="{http://www.af-klm.com/services/passenger/SicMarketingType-v1/xsd}GreenCardNumberType" minOccurs="0"/>
 *         &lt;element name="greenCardExpiryDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="countryOfResidence" type="{http://www.af-klm.com/services/passenger/SicMarketingType-v1/xsd}CountryCodeType" minOccurs="0"/>
 *         &lt;element name="addressUS" type="{http://www.af-klm.com/services/passenger/SicMarketingType-v1/xsd}AdressType" minOccurs="0"/>
 *         &lt;element name="postCodeUS" type="{http://www.af-klm.com/services/passenger/SicMarketingType-v1/xsd}PostCodeType" minOccurs="0"/>
 *         &lt;element name="cityUS" type="{http://www.af-klm.com/services/passenger/SicMarketingType-v1/xsd}CityType" minOccurs="0"/>
 *         &lt;element name="stateUS" type="{http://www.af-klm.com/services/passenger/SicMarketingType-v1/xsd}StateType" minOccurs="0"/>
 *         &lt;element name="redressControlNumber" type="{http://www.af-klm.com/services/passenger/SicMarketingType-v1/xsd}RedressControlType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MaccApisDataV2", propOrder = {
    "lastName",
    "firstName",
    "dateOfBirth",
    "gender",
    "greenCardNumber",
    "greenCardExpiryDate",
    "countryOfResidence",
    "addressUS",
    "postCodeUS",
    "cityUS",
    "stateUS",
    "redressControlNumber"
})
public class MaccApisDataV2 {

    protected String lastName;
    protected String firstName;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dateOfBirth;
    protected String gender;
    protected String greenCardNumber;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar greenCardExpiryDate;
    protected String countryOfResidence;
    protected String addressUS;
    protected String postCodeUS;
    protected String cityUS;
    protected String stateUS;
    protected String redressControlNumber;

    /**
     * Gets the value of the lastName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the value of the lastName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLastName(String value) {
        this.lastName = value;
    }

    /**
     * Gets the value of the firstName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the value of the firstName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFirstName(String value) {
        this.firstName = value;
    }

    /**
     * Gets the value of the dateOfBirth property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDateOfBirth() {
        return dateOfBirth;
    }

    /**
     * Sets the value of the dateOfBirth property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDateOfBirth(XMLGregorianCalendar value) {
        this.dateOfBirth = value;
    }

    /**
     * Gets the value of the gender property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGender() {
        return gender;
    }

    /**
     * Sets the value of the gender property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGender(String value) {
        this.gender = value;
    }

    /**
     * Gets the value of the greenCardNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGreenCardNumber() {
        return greenCardNumber;
    }

    /**
     * Sets the value of the greenCardNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGreenCardNumber(String value) {
        this.greenCardNumber = value;
    }

    /**
     * Gets the value of the greenCardExpiryDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getGreenCardExpiryDate() {
        return greenCardExpiryDate;
    }

    /**
     * Sets the value of the greenCardExpiryDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setGreenCardExpiryDate(XMLGregorianCalendar value) {
        this.greenCardExpiryDate = value;
    }

    /**
     * Gets the value of the countryOfResidence property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCountryOfResidence() {
        return countryOfResidence;
    }

    /**
     * Sets the value of the countryOfResidence property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCountryOfResidence(String value) {
        this.countryOfResidence = value;
    }

    /**
     * Gets the value of the addressUS property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAddressUS() {
        return addressUS;
    }

    /**
     * Sets the value of the addressUS property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAddressUS(String value) {
        this.addressUS = value;
    }

    /**
     * Gets the value of the postCodeUS property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPostCodeUS() {
        return postCodeUS;
    }

    /**
     * Sets the value of the postCodeUS property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPostCodeUS(String value) {
        this.postCodeUS = value;
    }

    /**
     * Gets the value of the cityUS property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCityUS() {
        return cityUS;
    }

    /**
     * Sets the value of the cityUS property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCityUS(String value) {
        this.cityUS = value;
    }

    /**
     * Gets the value of the stateUS property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStateUS() {
        return stateUS;
    }

    /**
     * Sets the value of the stateUS property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStateUS(String value) {
        this.stateUS = value;
    }

    /**
     * Gets the value of the redressControlNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRedressControlNumber() {
        return redressControlNumber;
    }

    /**
     * Sets the value of the redressControlNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRedressControlNumber(String value) {
        this.redressControlNumber = value;
    }

}
