
package com.afklm.bird.ws.w000311.data.schema6538;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for FlightDurationAtNightResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="FlightDurationAtNightResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="flightDurationAtNight" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FlightDurationAtNightResponse", propOrder = {
    "flightDurationAtNight"
})
public class FlightDurationAtNightResponse {

    protected int flightDurationAtNight;

    /**
     * Gets the value of the flightDurationAtNight property.
     * 
     */
    public int getFlightDurationAtNight() {
        return flightDurationAtNight;
    }

    /**
     * Sets the value of the flightDurationAtNight property.
     * 
     */
    public void setFlightDurationAtNight(int value) {
        this.flightDurationAtNight = value;
    }

}
