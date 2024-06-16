
package com.afklm.bird.ws.w000309.data.schema6538;

import com.afklm.bird.ws.w000309.data.schema5299.CountryCriteriaWSDTO;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for CountryRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CountryRequest">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="dateCriteria" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="countryCriteria" type="{http://www.af-klm.com/services/passenger/geographyBusinessData-v1/xsd}CountryCriteriaWSDTO" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CountryRequest", propOrder = {
    "dateCriteria",
    "countryCriteria"
})
public class CountryRequest {

    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dateCriteria;
    protected CountryCriteriaWSDTO countryCriteria;

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
     * Gets the value of the countryCriteria property.
     * 
     * @return
     *     possible object is
     *     {@link CountryCriteriaWSDTO }
     *     
     */
    public CountryCriteriaWSDTO getCountryCriteria() {
        return countryCriteria;
    }

    /**
     * Sets the value of the countryCriteria property.
     * 
     * @param value
     *     allowed object is
     *     {@link CountryCriteriaWSDTO }
     *     
     */
    public void setCountryCriteria(CountryCriteriaWSDTO value) {
        this.countryCriteria = value;
    }

}
