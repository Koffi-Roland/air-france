
package com.afklm.repind.ws.w000443.data.schema591280;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * Required information that must be provided when travelling to certain countries as a security measure. 
 * 
 * <p>Java class for MaccApisData complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="MaccApisData">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="lastName" type="{http://www.af-klm.com/services/passenger/MarketingDataTypes-v1/xsd}NameType" minOccurs="0"/>
 *         &lt;element name="firstName" type="{http://www.af-klm.com/services/passenger/MarketingDataTypes-v1/xsd}NameType" minOccurs="0"/>
 *         &lt;element name="dateOfBirth" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="gender" type="{http://www.af-klm.com/services/passenger/MarketingDataTypes-v1/xsd}GenderType" minOccurs="0"/>
 *         &lt;element name="greenCardNumber" type="{http://www.af-klm.com/services/passenger/MarketingDataTypes-v1/xsd}GreenCardNumberType" minOccurs="0"/>
 *         &lt;element name="greenCardExpiryDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="countryOfResidence" type="{http://www.af-klm.com/services/passenger/MarketingDataTypes-v1/xsd}CountryCodeType" minOccurs="0"/>
 *         &lt;element name="adressUS" type="{http://www.af-klm.com/services/passenger/MarketingDataTypes-v1/xsd}AdressType" minOccurs="0"/>
 *         &lt;element name="postCodeUS" type="{http://www.af-klm.com/services/passenger/MarketingDataTypes-v1/xsd}PostCodeType" minOccurs="0"/>
 *         &lt;element name="cityUS" type="{http://www.af-klm.com/services/passenger/MarketingDataTypes-v1/xsd}CityType" minOccurs="0"/>
 *         &lt;element name="stateUS" type="{http://www.af-klm.com/services/passenger/MarketingDataTypes-v1/xsd}StateType" minOccurs="0"/>
 *         &lt;element name="redressControlNumber" type="{http://www.af-klm.com/services/passenger/MarketingDataTypes-v1/xsd}RedressControlType" minOccurs="0"/>
 *         &lt;element name="apisTravelDocument" type="{http://www.af-klm.com/services/passenger/MarketingData-v1/xsd}MaccTravelDocument" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MaccApisData", propOrder = {
    "lastName",
    "firstName",
    "dateOfBirth",
    "gender",
    "greenCardNumber",
    "greenCardExpiryDate",
    "countryOfResidence",
    "adressUS",
    "postCodeUS",
    "cityUS",
    "stateUS",
    "redressControlNumber",
    "apisTravelDocument"
})
public class MaccApisData {

    protected String lastName;
    protected String firstName;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dateOfBirth;
    protected String gender;
    protected String greenCardNumber;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar greenCardExpiryDate;
    protected String countryOfResidence;
    protected String adressUS;
    protected String postCodeUS;
    protected String cityUS;
    protected String stateUS;
    protected String redressControlNumber;
    protected MaccTravelDocument apisTravelDocument;

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
     * Gets the value of the adressUS property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAdressUS() {
        return adressUS;
    }

    /**
     * Sets the value of the adressUS property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAdressUS(String value) {
        this.adressUS = value;
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

    /**
     * Gets the value of the apisTravelDocument property.
     * 
     * @return
     *     possible object is
     *     {@link MaccTravelDocument }
     *     
     */
    public MaccTravelDocument getApisTravelDocument() {
        return apisTravelDocument;
    }

    /**
     * Sets the value of the apisTravelDocument property.
     * 
     * @param value
     *     allowed object is
     *     {@link MaccTravelDocument }
     *     
     */
    public void setApisTravelDocument(MaccTravelDocument value) {
        this.apisTravelDocument = value;
    }

}
