
package com.afklm.repind.ws.w000443.data.schema587190;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for ClientUses complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ClientUses">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="srin" type="{http://www.af-klm.com/services/passenger/SicDataType-v1/xsd}DTypeSrin" minOccurs="0"/>
 *         &lt;element name="work" type="{http://www.af-klm.com/services/passenger/SicDataType-v1/xsd}DTypeMetier" minOccurs="0"/>
 *         &lt;element name="authorizedModification" type="{http://www.af-klm.com/services/passenger/SicDataType-v1/xsd}DTypeModifAutorisee" minOccurs="0"/>
 *         &lt;element name="dateOfModification" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ClientUses", propOrder = {
    "srin",
    "work",
    "authorizedModification",
    "dateOfModification"
})
public class ClientUses {

    protected String srin;
    protected String work;
    protected String authorizedModification;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dateOfModification;

    /**
     * Gets the value of the srin property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSrin() {
        return srin;
    }

    /**
     * Sets the value of the srin property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSrin(String value) {
        this.srin = value;
    }

    /**
     * Gets the value of the work property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWork() {
        return work;
    }

    /**
     * Sets the value of the work property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWork(String value) {
        this.work = value;
    }

    /**
     * Gets the value of the authorizedModification property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAuthorizedModification() {
        return authorizedModification;
    }

    /**
     * Sets the value of the authorizedModification property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAuthorizedModification(String value) {
        this.authorizedModification = value;
    }

    /**
     * Gets the value of the dateOfModification property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDateOfModification() {
        return dateOfModification;
    }

    /**
     * Sets the value of the dateOfModification property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDateOfModification(XMLGregorianCalendar value) {
        this.dateOfModification = value;
    }

}
