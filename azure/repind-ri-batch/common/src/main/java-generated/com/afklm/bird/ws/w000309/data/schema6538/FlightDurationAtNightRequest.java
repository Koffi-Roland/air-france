
package com.afklm.bird.ws.w000309.data.schema6538;

import javax.xml.bind.annotation.*;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for FlightDurationAtNightRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="FlightDurationAtNightRequest">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="dateCriteria" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="departureTime" type="{http://www.af-klm.com/services/passenger/commonsBusinessData-v1/xsd}CDT_Time" minOccurs="0"/>
 *         &lt;element name="arrivalTime" type="{http://www.af-klm.com/services/passenger/commonsBusinessData-v1/xsd}CDT_Time" minOccurs="0"/>
 *         &lt;element name="departure_point" type="{http://www.af-klm.com/services/passenger/geographyOperationData-v1/xsd}GeographicPoint" minOccurs="0"/>
 *         &lt;element name="arrivalpoint" type="{http://www.af-klm.com/services/passenger/geographyOperationData-v1/xsd}GeographicPoint" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FlightDurationAtNightRequest", propOrder = {
    "dateCriteria",
    "departureTime",
    "arrivalTime",
    "departurePoint",
    "arrivalpoint"
})
public class FlightDurationAtNightRequest {

    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dateCriteria;
    protected String departureTime;
    protected String arrivalTime;
    @XmlElement(name = "departure_point")
    protected GeographicPoint departurePoint;
    protected GeographicPoint arrivalpoint;

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
     * Gets the value of the departureTime property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDepartureTime() {
        return departureTime;
    }

    /**
     * Sets the value of the departureTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDepartureTime(String value) {
        this.departureTime = value;
    }

    /**
     * Gets the value of the arrivalTime property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getArrivalTime() {
        return arrivalTime;
    }

    /**
     * Sets the value of the arrivalTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setArrivalTime(String value) {
        this.arrivalTime = value;
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
     * Gets the value of the arrivalpoint property.
     * 
     * @return
     *     possible object is
     *     {@link GeographicPoint }
     *     
     */
    public GeographicPoint getArrivalpoint() {
        return arrivalpoint;
    }

    /**
     * Sets the value of the arrivalpoint property.
     * 
     * @param value
     *     allowed object is
     *     {@link GeographicPoint }
     *     
     */
    public void setArrivalpoint(GeographicPoint value) {
        this.arrivalpoint = value;
    }

}
