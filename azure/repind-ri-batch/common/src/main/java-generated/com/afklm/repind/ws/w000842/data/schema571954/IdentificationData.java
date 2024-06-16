
package com.afklm.repind.ws.w000842.data.schema571954;

import com.afklm.repind.ws.w000842.data.schema569983.CivilityEnum;

import javax.xml.bind.annotation.*;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for IdentificationData complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="IdentificationData">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="civilityCode" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}CivilityEnum" minOccurs="0"/>
 *         &lt;element name="lastNameSC" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypeSurname" minOccurs="0"/>
 *         &lt;element name="firstNameSC" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypeFirstName" minOccurs="0"/>
 *         &lt;element name="middleNameSC" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypeFirstName" minOccurs="0"/>
 *         &lt;element name="dateOfBirth" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="nationality" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypeNationality" minOccurs="0"/>
 *         &lt;element name="otherNationality" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypeNationality" minOccurs="0"/>
 *         &lt;element name="email" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypeEmail"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "IdentificationData", propOrder = {
    "civilityCode",
    "lastNameSC",
    "firstNameSC",
    "middleNameSC",
    "dateOfBirth",
    "nationality",
    "otherNationality",
    "email"
})
public class IdentificationData {

    protected CivilityEnum civilityCode;
    protected String lastNameSC;
    protected String firstNameSC;
    protected String middleNameSC;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dateOfBirth;
    protected String nationality;
    protected String otherNationality;
    @XmlElement(required = true)
    protected String email;

    /**
     * Gets the value of the civilityCode property.
     * 
     * @return
     *     possible object is
     *     {@link CivilityEnum }
     *     
     */
    public CivilityEnum getCivilityCode() {
        return civilityCode;
    }

    /**
     * Sets the value of the civilityCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link CivilityEnum }
     *     
     */
    public void setCivilityCode(CivilityEnum value) {
        this.civilityCode = value;
    }

    /**
     * Gets the value of the lastNameSC property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLastNameSC() {
        return lastNameSC;
    }

    /**
     * Sets the value of the lastNameSC property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLastNameSC(String value) {
        this.lastNameSC = value;
    }

    /**
     * Gets the value of the firstNameSC property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFirstNameSC() {
        return firstNameSC;
    }

    /**
     * Sets the value of the firstNameSC property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFirstNameSC(String value) {
        this.firstNameSC = value;
    }

    /**
     * Gets the value of the middleNameSC property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMiddleNameSC() {
        return middleNameSC;
    }

    /**
     * Sets the value of the middleNameSC property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMiddleNameSC(String value) {
        this.middleNameSC = value;
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
     * Gets the value of the nationality property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNationality() {
        return nationality;
    }

    /**
     * Sets the value of the nationality property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNationality(String value) {
        this.nationality = value;
    }

    /**
     * Gets the value of the otherNationality property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOtherNationality() {
        return otherNationality;
    }

    /**
     * Sets the value of the otherNationality property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOtherNationality(String value) {
        this.otherNationality = value;
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

}
