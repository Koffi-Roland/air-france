
package com.afklm.repind.ws.w000443.data.schema591280;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for MaccTravelPreferences complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="MaccTravelPreferences">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="departureAirport" type="{http://www.af-klm.com/services/passenger/MarketingDataTypes-v1/xsd}AirportCodeType" minOccurs="0"/>
 *         &lt;element name="arrivalAirport" type="{http://www.af-klm.com/services/passenger/MarketingDataTypes-v1/xsd}AirportCodeType" minOccurs="0"/>
 *         &lt;element name="meal" type="{http://www.af-klm.com/services/passenger/MarketingDataTypes-v1/xsd}MealCodeType" minOccurs="0"/>
 *         &lt;element name="seat" type="{http://www.af-klm.com/services/passenger/MarketingDataTypes-v1/xsd}SeatType" minOccurs="0"/>
 *         &lt;element name="travelClass" type="{http://www.af-klm.com/services/passenger/MarketingDataTypes-v1/xsd}TravelClassType" minOccurs="0"/>
 *         &lt;element name="boardingPass" type="{http://www.af-klm.com/services/passenger/MarketingDataTypes-v1/xsd}BoardingPassType" minOccurs="0"/>
 *         &lt;element name="customerLeisure" type="{http://www.af-klm.com/services/passenger/MarketingDataTypes-v1/xsd}LeisureCodeType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MaccTravelPreferences", propOrder = {
    "departureAirport",
    "arrivalAirport",
    "meal",
    "seat",
    "travelClass",
    "boardingPass",
    "customerLeisure"
})
public class MaccTravelPreferences {

    protected String departureAirport;
    protected String arrivalAirport;
    protected String meal;
    protected String seat;
    protected String travelClass;
    protected String boardingPass;
    protected String customerLeisure;

    /**
     * Gets the value of the departureAirport property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDepartureAirport() {
        return departureAirport;
    }

    /**
     * Sets the value of the departureAirport property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDepartureAirport(String value) {
        this.departureAirport = value;
    }

    /**
     * Gets the value of the arrivalAirport property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getArrivalAirport() {
        return arrivalAirport;
    }

    /**
     * Sets the value of the arrivalAirport property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setArrivalAirport(String value) {
        this.arrivalAirport = value;
    }

    /**
     * Gets the value of the meal property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMeal() {
        return meal;
    }

    /**
     * Sets the value of the meal property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMeal(String value) {
        this.meal = value;
    }

    /**
     * Gets the value of the seat property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSeat() {
        return seat;
    }

    /**
     * Sets the value of the seat property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSeat(String value) {
        this.seat = value;
    }

    /**
     * Gets the value of the travelClass property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTravelClass() {
        return travelClass;
    }

    /**
     * Sets the value of the travelClass property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTravelClass(String value) {
        this.travelClass = value;
    }

    /**
     * Gets the value of the boardingPass property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBoardingPass() {
        return boardingPass;
    }

    /**
     * Sets the value of the boardingPass property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBoardingPass(String value) {
        this.boardingPass = value;
    }

    /**
     * Gets the value of the customerLeisure property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCustomerLeisure() {
        return customerLeisure;
    }

    /**
     * Sets the value of the customerLeisure property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCustomerLeisure(String value) {
        this.customerLeisure = value;
    }

}
