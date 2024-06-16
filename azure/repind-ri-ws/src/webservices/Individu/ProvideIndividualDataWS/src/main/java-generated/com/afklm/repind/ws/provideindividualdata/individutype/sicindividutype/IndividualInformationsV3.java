
package com.afklm.repind.ws.provideindividualdata.individutype.sicindividutype;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import java.util.Date;


/**
 * <p>Java class for IndividualInformationsV3 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="IndividualInformationsV3">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="identifier" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypeCustomerNumber" minOccurs="0"/>
 *         &lt;element name="version" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypeVersion" minOccurs="0"/>
 *         &lt;element name="gender" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypeGender" minOccurs="0"/>
 *         &lt;element name="personalIdentifier" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypePersonalIdent" minOccurs="0"/>
 *         &lt;element name="birthDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="nationality" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypeNationality" minOccurs="0"/>
 *         &lt;element name="secondNationality" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypeNationality" minOccurs="0"/>
 *         &lt;element name="secondFirstName" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypeFirstName" minOccurs="0"/>
 *         &lt;element name="flagNoFusion" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="status" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypeStatus" minOccurs="0"/>
 *         &lt;element name="flagThirdTrap" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="civility" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypeCivility" minOccurs="0"/>
 *         &lt;element name="lastNamePseudonym" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypeSurnameAlias" minOccurs="0"/>
 *         &lt;element name="firstNamePseudonym" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypeFirstNameAlias" minOccurs="0"/>
 *         &lt;element name="lastNameSC" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypeSurname" minOccurs="0"/>
 *         &lt;element name="firstNameSC" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypeFirstName" minOccurs="0"/>
 *         &lt;element name="middleNameSC" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypeFirstName" minOccurs="0"/>
 *         &lt;element name="languageCode" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypeLanguageCode" minOccurs="0"/>
 *         &lt;element name="populationType" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypePopulationTargeted" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "IndividualInformationsV3", propOrder = {
    "identifier",
    "version",
    "gender",
    "personalIdentifier",
    "birthDate",
    "nationality",
    "secondNationality",
    "secondFirstName",
    "flagNoFusion",
    "status",
    "flagThirdTrap",
    "civility",
    "lastNamePseudonym",
    "firstNamePseudonym",
    "lastNameSC",
    "firstNameSC",
    "middleNameSC",
    "languageCode",
    "populationType"
})
public class IndividualInformationsV3 {

    protected String identifier;
    protected String version;
    protected String gender;
    protected String personalIdentifier;
    @XmlSchemaType(name = "dateTime")
    protected Date birthDate;
    protected String nationality;
    protected String secondNationality;
    protected String secondFirstName;
    protected Boolean flagNoFusion;
    protected String status;
    protected Boolean flagThirdTrap;
    protected String civility;
    protected String lastNamePseudonym;
    protected String firstNamePseudonym;
    protected String lastNameSC;
    protected String firstNameSC;
    protected String middleNameSC;
    protected String languageCode;
    protected String populationType;

    /**
     * Gets the value of the identifier property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdentifier() {
        return identifier;
    }

    /**
     * Sets the value of the identifier property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdentifier(String value) {
        this.identifier = value;
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
     * Gets the value of the personalIdentifier property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPersonalIdentifier() {
        return personalIdentifier;
    }

    /**
     * Sets the value of the personalIdentifier property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPersonalIdentifier(String value) {
        this.personalIdentifier = value;
    }

    /**
     * Gets the value of the birthDate property.
     * 
     * @return
     *     possible object is
     *     {@link Date }
     *     
     */
    public Date getBirthDate() {
        return birthDate;
    }

    /**
     * Sets the value of the birthDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link Date }
     *     
     */
    public void setBirthDate(Date value) {
        this.birthDate = value;
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
     * Gets the value of the secondNationality property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSecondNationality() {
        return secondNationality;
    }

    /**
     * Sets the value of the secondNationality property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSecondNationality(String value) {
        this.secondNationality = value;
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
     * Gets the value of the languageCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLanguageCode() {
        return languageCode;
    }

    /**
     * Sets the value of the languageCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLanguageCode(String value) {
        this.languageCode = value;
    }

    /**
     * Gets the value of the populationType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPopulationType() {
        return populationType;
    }

    /**
     * Sets the value of the populationType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPopulationType(String value) {
        this.populationType = value;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

    @Override
    public boolean equals(Object that) {
        return EqualsBuilder.reflectionEquals(this, that);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

}
