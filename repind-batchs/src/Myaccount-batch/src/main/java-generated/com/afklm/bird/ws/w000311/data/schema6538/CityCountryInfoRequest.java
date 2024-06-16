
package com.afklm.bird.ws.w000311.data.schema6538;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for CityCountryInfoRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CityCountryInfoRequest">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="dateCriteria" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="iataCode" type="{http://www.af-klm.com/services/passenger/geographyBusinessData-v1/xsd}DT_IATAStationCode" minOccurs="0"/>
 *         &lt;element name="icaoCode" type="{http://www.af-klm.com/services/passenger/geographyBusinessData-v1/xsd}DT_ICAOStationCode" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CityCountryInfoRequest", propOrder = {
    "dateCriteria",
    "iataCode",
    "icaoCode"
})
public class CityCountryInfoRequest {

    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dateCriteria;
    protected String iataCode;
    protected String icaoCode;

    /**
     * Gets the value of the dateCriteria property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDateCriteria() {
        return dateCriteria;
    }

    /**
     * Sets the value of the dateCriteria property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDateCriteria(XMLGregorianCalendar value) {
        this.dateCriteria = value;
    }

    /**
     * Gets the value of the iataCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIataCode() {
        return iataCode;
    }

    /**
     * Sets the value of the iataCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIataCode(String value) {
        this.iataCode = value;
    }

    /**
     * Gets the value of the icaoCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIcaoCode() {
        return icaoCode;
    }

    /**
     * Sets the value of the icaoCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIcaoCode(String value) {
        this.icaoCode = value;
    }

}
