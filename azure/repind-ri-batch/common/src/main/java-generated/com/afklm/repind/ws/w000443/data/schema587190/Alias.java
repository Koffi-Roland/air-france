
package com.afklm.repind.ws.w000443.data.schema587190;

import com.afklm.repind.ws.w000443.data.schema588217.CiviliteEnum;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Alias complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Alias">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="lastName" type="{http://www.af-klm.com/services/passenger/SicDataType-v1/xsd}DTypeNom" minOccurs="0"/>
 *         &lt;element name="firstName" type="{http://www.af-klm.com/services/passenger/SicDataType-v1/xsd}DTypePrenom" minOccurs="0"/>
 *         &lt;element name="civility" type="{http://www.af-klm.com/services/passenger/SicDataType-v1/xsd}CiviliteEnum" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Alias", propOrder = {
    "lastName",
    "firstName",
    "civility"
})
public class Alias {

    protected String lastName;
    protected String firstName;
    protected CiviliteEnum civility;

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

}
