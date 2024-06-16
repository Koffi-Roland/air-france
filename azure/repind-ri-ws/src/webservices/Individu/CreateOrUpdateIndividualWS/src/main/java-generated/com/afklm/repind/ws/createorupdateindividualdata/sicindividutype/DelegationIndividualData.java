
package com.afklm.repind.ws.createorupdateindividualdata.sicindividutype;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DelegationIndividualData complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DelegationIndividualData">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="fBIdentifier" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypeFbIdentifier12" minOccurs="0"/>
 *         &lt;element name="accountIdentifier" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypeAccountID" minOccurs="0"/>
 *         &lt;element name="emailIdentifier" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypeEmail" minOccurs="0"/>
 *         &lt;element name="civility" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypeCivility" minOccurs="0"/>
 *         &lt;element name="firstName" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypeFirstName" minOccurs="0"/>
 *         &lt;element name="firstNameSC" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypeFirstName" minOccurs="0"/>
 *         &lt;element name="lastName" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypeLastName" minOccurs="0"/>
 *         &lt;element name="lastNameSC" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypeLastName" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DelegationIndividualData", propOrder = {
    "fbIdentifier",
    "accountIdentifier",
    "emailIdentifier",
    "civility",
    "firstName",
    "firstNameSC",
    "lastName",
    "lastNameSC"
})
public class DelegationIndividualData {

    @XmlElement(name = "fBIdentifier")
    protected String fbIdentifier;
    protected String accountIdentifier;
    protected String emailIdentifier;
    protected String civility;
    protected String firstName;
    protected String firstNameSC;
    protected String lastName;
    protected String lastNameSC;

    /**
     * Gets the value of the fbIdentifier property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFBIdentifier() {
        return fbIdentifier;
    }

    /**
     * Sets the value of the fbIdentifier property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFBIdentifier(String value) {
        this.fbIdentifier = value;
    }

    /**
     * Gets the value of the accountIdentifier property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAccountIdentifier() {
        return accountIdentifier;
    }

    /**
     * Sets the value of the accountIdentifier property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAccountIdentifier(String value) {
        this.accountIdentifier = value;
    }

    /**
     * Gets the value of the emailIdentifier property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEmailIdentifier() {
        return emailIdentifier;
    }

    /**
     * Sets the value of the emailIdentifier property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEmailIdentifier(String value) {
        this.emailIdentifier = value;
    }

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

}
