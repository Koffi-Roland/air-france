
package com.afklm.repind.ws.w000443.data.schema591280;

import javax.xml.bind.annotation.*;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for MaccTravelCompanion complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="MaccTravelCompanion">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="civility" type="{http://www.af-klm.com/services/passenger/MarketingDataTypes-v1/xsd}CivilityType" minOccurs="0"/>
 *         &lt;element name="firstName" type="{http://www.af-klm.com/services/passenger/MarketingDataTypes-v1/xsd}NameType" minOccurs="0"/>
 *         &lt;element name="lastName" type="{http://www.af-klm.com/services/passenger/MarketingDataTypes-v1/xsd}NameType" minOccurs="0"/>
 *         &lt;element name="dateOfBirth" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="email" type="{http://www.af-klm.com/services/passenger/MarketingDataTypes-v1/xsd}EmailType" minOccurs="0"/>
 *         &lt;element name="tCPersonalInformation" type="{http://www.af-klm.com/services/passenger/MarketingData-v1/xsd}MaccPersonalInformation" minOccurs="0"/>
 *         &lt;element name="tCApisData" type="{http://www.af-klm.com/services/passenger/MarketingData-v1/xsd}MaccApisData" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MaccTravelCompanion", propOrder = {
    "civility",
    "firstName",
    "lastName",
    "dateOfBirth",
    "email",
    "tcPersonalInformation",
    "tcApisData"
})
public class MaccTravelCompanion {

    protected String civility;
    protected String firstName;
    protected String lastName;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dateOfBirth;
    protected String email;
    @XmlElement(name = "tCPersonalInformation")
    protected MaccPersonalInformation tcPersonalInformation;
    @XmlElement(name = "tCApisData")
    protected MaccApisData tcApisData;

    /**
     * Gets the value of the civility property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCivility() {
        return civility;
    }

    /**
     * Sets the value of the civility property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCivility(String value) {
        this.civility = value;
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
     * Gets the value of the tcPersonalInformation property.
     * 
     * @return
     *     possible object is
     *     {@link MaccPersonalInformation }
     *     
     */
    public MaccPersonalInformation getTCPersonalInformation() {
        return tcPersonalInformation;
    }

    /**
     * Sets the value of the tcPersonalInformation property.
     * 
     * @param value
     *     allowed object is
     *     {@link MaccPersonalInformation }
     *     
     */
    public void setTCPersonalInformation(MaccPersonalInformation value) {
        this.tcPersonalInformation = value;
    }

    /**
     * Gets the value of the tcApisData property.
     * 
     * @return
     *     possible object is
     *     {@link MaccApisData }
     *     
     */
    public MaccApisData getTCApisData() {
        return tcApisData;
    }

    /**
     * Sets the value of the tcApisData property.
     * 
     * @param value
     *     allowed object is
     *     {@link MaccApisData }
     *     
     */
    public void setTCApisData(MaccApisData value) {
        this.tcApisData = value;
    }

}
