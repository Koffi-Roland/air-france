
package com.afklm.repind.ws.w000443.data.schema591279;

import com.afklm.repind.ws.w000443.data.schema588217.CiviliteEnum;
import com.afklm.repind.ws.w000443.data.schema588217.LanguageCodesEnum;

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
 *         &lt;element name="lastNameSC" type="{http://www.af-klm.com/services/passenger/SicDataType-v1/xsd}DTypeNom" minOccurs="0"/>
 *         &lt;element name="dateOfBirth" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="firstNameSC" type="{http://www.af-klm.com/services/passenger/SicDataType-v1/xsd}DTypePrenom" minOccurs="0"/>
 *         &lt;element name="civility" type="{http://www.af-klm.com/services/passenger/SicDataType-v1/xsd}CiviliteEnum" minOccurs="0"/>
 *         &lt;element name="language" type="{http://www.af-klm.com/services/passenger/SicDataType-v1/xsd}LanguageCodesEnum" minOccurs="0"/>
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
    "lastNameSC",
    "dateOfBirth",
    "firstNameSC",
    "civility",
    "language"
})
public class IndividualInformations {

    protected String lastNameSC;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dateOfBirth;
    protected String firstNameSC;
    protected CiviliteEnum civility;
    protected LanguageCodesEnum language;

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
     * Gets the value of the civility property.
     * 
     * @return
     *     possible object is
     *     {@link CiviliteEnum }
     *     
     */
    public CiviliteEnum getCivility() {
        return civility;
    }

    /**
     * Sets the value of the civility property.
     * 
     * @param value
     *     allowed object is
     *     {@link CiviliteEnum }
     *     
     */
    public void setCivility(CiviliteEnum value) {
        this.civility = value;
    }

    /**
     * Gets the value of the language property.
     * 
     * @return
     *     possible object is
     *     {@link LanguageCodesEnum }
     *     
     */
    public LanguageCodesEnum getLanguage() {
        return language;
    }

    /**
     * Sets the value of the language property.
     * 
     * @param value
     *     allowed object is
     *     {@link LanguageCodesEnum }
     *     
     */
    public void setLanguage(LanguageCodesEnum value) {
        this.language = value;
    }

}
