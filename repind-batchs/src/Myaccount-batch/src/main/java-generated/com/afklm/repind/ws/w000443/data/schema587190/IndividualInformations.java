
package com.afklm.repind.ws.w000443.data.schema587190;

import com.afklm.repind.ws.w000443.data.schema576961.CivilityEnum;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for IndividualInformations complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="IndividualInformations">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="clientNumber" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypeCustomerNumber" minOccurs="0"/>
 *         &lt;element name="version" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypeVersion" minOccurs="0"/>
 *         &lt;element name="lastName" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypeSurname" minOccurs="0"/>
 *         &lt;element name="gender" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypeGender" minOccurs="0"/>
 *         &lt;element name="personnalIdentity" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypePersonalIdent" minOccurs="0"/>
 *         &lt;element name="dateOfBirth" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="nationality" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypeNationality" minOccurs="0"/>
 *         &lt;element name="otherNationality" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypeNationality" minOccurs="0"/>
 *         &lt;element name="secondFirstName" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypeFirstName" minOccurs="0"/>
 *         &lt;element name="firstName" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypeFirstName" minOccurs="0"/>
 *         &lt;element name="flagNoFusion" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="status" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypeStatus" minOccurs="0"/>
 *         &lt;element name="flagBankFraud" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="flagThirdTrap" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="civility" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}CivilityEnum" minOccurs="0"/>
 *         &lt;element name="lastNamePseudonym" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypeSurnameAlias" minOccurs="0"/>
 *         &lt;element name="firstNamePseudonym" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypeFirstNameAlias" minOccurs="0"/>
 *         &lt;element name="ginFusion" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypeGinMerged" minOccurs="0"/>
 *         &lt;element name="dateFusion" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="password" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypePassword" minOccurs="0"/>
 *         &lt;element name="managingCompany" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypeCompanyManager" minOccurs="0"/>
 *         &lt;element name="lastNameSC" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypeSurname" minOccurs="0"/>
 *         &lt;element name="firstNameSC" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypeFirstName" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "IndividualInformations", propOrder = {
    "clientNumber",
    "version",
    "lastName",
    "gender",
    "personnalIdentity",
    "dateOfBirth",
    "nationality",
    "otherNationality",
    "secondFirstName",
    "firstName",
    "flagNoFusion",
    "status",
    "flagBankFraud",
    "flagThirdTrap",
    "civility",
    "lastNamePseudonym",
    "firstNamePseudonym",
    "ginFusion",
    "dateFusion",
    "password",
    "managingCompany",
    "lastNameSC",
    "firstNameSC"
})
public class IndividualInformations {

    protected String clientNumber;
    protected String version;
    protected String lastName;
    protected String gender;
    protected String personnalIdentity;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dateOfBirth;
    protected String nationality;
    protected String otherNationality;
    protected String secondFirstName;
    protected String firstName;
    protected Boolean flagNoFusion;
    protected String status;
    protected Boolean flagBankFraud;
    protected Boolean flagThirdTrap;
    protected CivilityEnum civility;
    protected String lastNamePseudonym;
    protected String firstNamePseudonym;
    protected String ginFusion;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dateFusion;
    protected String password;
    protected String managingCompany;
    protected String lastNameSC;
    protected String firstNameSC;

    /**
     * Gets the value of the clientNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getClientNumber() {
        return clientNumber;
    }

    /**
     * Sets the value of the clientNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setClientNumber(String value) {
        this.clientNumber = value;
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
     * Gets the value of the personnalIdentity property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPersonnalIdentity() {
        return personnalIdentity;
    }

    /**
     * Sets the value of the personnalIdentity property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPersonnalIdentity(String value) {
        this.personnalIdentity = value;
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
     * Gets the value of the secondFirstName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSecondFirstName() {
        return secondFirstName;
    }

    /**
     * Sets the value of the secondFirstName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSecondFirstName(String value) {
        this.secondFirstName = value;
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
     * Gets the value of the flagNoFusion property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isFlagNoFusion() {
        return flagNoFusion;
    }

    /**
     * Sets the value of the flagNoFusion property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setFlagNoFusion(Boolean value) {
        this.flagNoFusion = value;
    }

    /**
     * Gets the value of the status property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatus(String value) {
        this.status = value;
    }

    /**
     * Gets the value of the flagBankFraud property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isFlagBankFraud() {
        return flagBankFraud;
    }

    /**
     * Sets the value of the flagBankFraud property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setFlagBankFraud(Boolean value) {
        this.flagBankFraud = value;
    }

    /**
     * Gets the value of the flagThirdTrap property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isFlagThirdTrap() {
        return flagThirdTrap;
    }

    /**
     * Sets the value of the flagThirdTrap property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setFlagThirdTrap(Boolean value) {
        this.flagThirdTrap = value;
    }

    /**
     * Gets the value of the civility property.
     * 
     * @return
     *     possible object is
     *     {@link CivilityEnum }
     *     
     */
    public CivilityEnum getCivility() {
        return civility;
    }

    /**
     * Sets the value of the civility property.
     * 
     * @param value
     *     allowed object is
     *     {@link CivilityEnum }
     *     
     */
    public void setCivility(CivilityEnum value) {
        this.civility = value;
    }

    /**
     * Gets the value of the lastNamePseudonym property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLastNamePseudonym() {
        return lastNamePseudonym;
    }

    /**
     * Sets the value of the lastNamePseudonym property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLastNamePseudonym(String value) {
        this.lastNamePseudonym = value;
    }

    /**
     * Gets the value of the firstNamePseudonym property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFirstNamePseudonym() {
        return firstNamePseudonym;
    }

    /**
     * Sets the value of the firstNamePseudonym property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFirstNamePseudonym(String value) {
        this.firstNamePseudonym = value;
    }

    /**
     * Gets the value of the ginFusion property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGinFusion() {
        return ginFusion;
    }

    /**
     * Sets the value of the ginFusion property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGinFusion(String value) {
        this.ginFusion = value;
    }

    /**
     * Gets the value of the dateFusion property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDateFusion() {
        return dateFusion;
    }

    /**
     * Sets the value of the dateFusion property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDateFusion(XMLGregorianCalendar value) {
        this.dateFusion = value;
    }

    /**
     * Gets the value of the password property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the value of the password property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPassword(String value) {
        this.password = value;
    }

    /**
     * Gets the value of the managingCompany property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getManagingCompany() {
        return managingCompany;
    }

    /**
     * Sets the value of the managingCompany property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setManagingCompany(String value) {
        this.managingCompany = value;
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

}
