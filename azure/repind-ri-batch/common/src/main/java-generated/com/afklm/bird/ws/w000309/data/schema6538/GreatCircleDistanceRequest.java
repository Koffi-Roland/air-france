
package com.afklm.bird.ws.w000309.data.schema6538;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for GreatCircleDistanceRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="GreatCircleDistanceRequest">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="dateCriteria" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="departurePoint" type="{http://www.af-klm.com/services/passenger/geographyOperationData-v1/xsd}GeographicPoint" minOccurs="0"/>
 *         &lt;element name="arrivalPoint" type="{http://www.af-klm.com/services/passenger/geographyOperationData-v1/xsd}GeographicPoint" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GreatCircleDistanceRequest", propOrder = {
    "dateCriteria",
    "departurePoint",
    "arrivalPoint"
})
public class GreatCircleDistanceRequest {

    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dateCriteria;
    protected GeographicPoint departurePoint;
    protected GeographicPoint arrivalPoint;

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
     * Gets the value of the departurePoint property.
     * 
     * @return
     *     possible object is
     *     {@link GeographicPoint }
     *     
     */
    public GeographicPoint getDeparturePoint() {
        return departurePoint;
    }

    /**
     * Sets the value of the departurePoint property.
     * 
     * @param value
     *     allowed object is
     *     {@link GeographicPoint }
     *     
     */
    public void setDeparturePoint(GeographicPoint value) {
        this.departurePoint = value;
    }

    /**
     * Gets the value of the arrivalPoint property.
     * 
     * @return
     *     possible object is
     *     {@link GeographicPoint }
     *     
     */
    public GeographicPoint getArrivalPoint() {
        return arrivalPoint;
    }

    /**
     * Sets the value of the arrivalPoint property.
     * 
     * @param value
     *     allowed object is
     *     {@link GeographicPoint }
     *     
     */
    public void setArrivalPoint(GeographicPoint value) {
        this.arrivalPoint = value;
    }

}
