
package com.afklm.bird.ws.w000311.data.schema5299;

import com.afklm.bird.ws.w000311.data.schema5297.CommonsBIRDEntityWSDTO;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for StationWSDTO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="StationWSDTO">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.af-klm.com/services/passenger/commonsBusinessData-v1/xsd}CommonsBIRDEntityWSDTO">
 *       &lt;sequence>
 *         &lt;element name="iataStationCode" type="{http://www.af-klm.com/services/passenger/geographyBusinessData-v1/xsd}DT_IATAStationCode" minOccurs="0"/>
 *         &lt;element name="iataStationCodeIndicator" type="{http://www.af-klm.com/services/passenger/commonsBusinessData-v1/xsd}CDT_Indicator" minOccurs="0"/>
 *         &lt;element name="stationName" type="{http://www.af-klm.com/services/passenger/commonsBusinessData-v1/xsd}CDT_Name" minOccurs="0"/>
 *         &lt;element name="icaoStationCode" type="{http://www.af-klm.com/services/passenger/geographyBusinessData-v1/xsd}DT_ICAOStationCode" minOccurs="0"/>
 *         &lt;element name="icaoStationCodeIndicator" type="{http://www.af-klm.com/services/passenger/commonsBusinessData-v1/xsd}CDT_Indicator" minOccurs="0"/>
 *         &lt;element name="iataCityCode" type="{http://www.af-klm.com/services/passenger/geographyBusinessData-v1/xsd}DT_IATACityCode" minOccurs="0"/>
 *         &lt;element name="locationType" type="{http://www.af-klm.com/services/passenger/geographyBusinessData-v1/xsd}DT_LocationType" minOccurs="0"/>
 *         &lt;element name="latitude" type="{http://www.af-klm.com/services/passenger/geographyBusinessData-v1/xsd}CDT_latitude" minOccurs="0"/>
 *         &lt;element name="longitude" type="{http://www.af-klm.com/services/passenger/geographyBusinessData-v1/xsd}CDT_longitude" minOccurs="0"/>
 *         &lt;element name="crewAreaTravelCode" type="{http://www.af-klm.com/services/passenger/geographyBusinessData-v1/xsd}DT_CrewAreaTravelCode" minOccurs="0"/>
 *         &lt;element name="airportArea" type="{http://www.af-klm.com/services/passenger/geographyBusinessData-v1/xsd}DT_AirportArea" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "StationWSDTO", propOrder = {
    "iataStationCode",
    "iataStationCodeIndicator",
    "stationName",
    "icaoStationCode",
    "icaoStationCodeIndicator",
    "iataCityCode",
    "locationType",
    "latitude",
    "longitude",
    "crewAreaTravelCode",
    "airportArea"
})
public class StationWSDTO
    extends CommonsBIRDEntityWSDTO
{

    protected String iataStationCode;
    protected String iataStationCodeIndicator;
    protected String stationName;
    protected String icaoStationCode;
    protected String icaoStationCodeIndicator;
    protected String iataCityCode;
    protected String locationType;
    protected String latitude;
    protected String longitude;
    protected String crewAreaTravelCode;
    protected String airportArea;

    /**
     * Gets the value of the iataStationCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIataStationCode() {
        return iataStationCode;
    }

    /**
     * Sets the value of the iataStationCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIataStationCode(String value) {
        this.iataStationCode = value;
    }

    /**
     * Gets the value of the iataStationCodeIndicator property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIataStationCodeIndicator() {
        return iataStationCodeIndicator;
    }

    /**
     * Sets the value of the iataStationCodeIndicator property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIataStationCodeIndicator(String value) {
        this.iataStationCodeIndicator = value;
    }

    /**
     * Gets the value of the stationName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStationName() {
        return stationName;
    }

    /**
     * Sets the value of the stationName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStationName(String value) {
        this.stationName = value;
    }

    /**
     * Gets the value of the icaoStationCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIcaoStationCode() {
        return icaoStationCode;
    }

    /**
     * Sets the value of the icaoStationCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIcaoStationCode(String value) {
        this.icaoStationCode = value;
    }

    /**
     * Gets the value of the icaoStationCodeIndicator property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIcaoStationCodeIndicator() {
        return icaoStationCodeIndicator;
    }

    /**
     * Sets the value of the icaoStationCodeIndicator property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIcaoStationCodeIndicator(String value) {
        this.icaoStationCodeIndicator = value;
    }

    /**
     * Gets the value of the iataCityCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIataCityCode() {
        return iataCityCode;
    }

    /**
     * Sets the value of the iataCityCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIataCityCode(String value) {
        this.iataCityCode = value;
    }

    /**
     * Gets the value of the locationType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLocationType() {
        return locationType;
    }

    /**
     * Sets the value of the locationType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLocationType(String value) {
        this.locationType = value;
    }

    /**
     * Gets the value of the latitude property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLatitude() {
        return latitude;
    }

    /**
     * Sets the value of the latitude property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLatitude(String value) {
        this.latitude = value;
    }

    /**
     * Gets the value of the longitude property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLongitude() {
        return longitude;
    }

    /**
     * Sets the value of the longitude property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLongitude(String value) {
        this.longitude = value;
    }

    /**
     * Gets the value of the crewAreaTravelCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCrewAreaTravelCode() {
        return crewAreaTravelCode;
    }

    /**
     * Sets the value of the crewAreaTravelCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCrewAreaTravelCode(String value) {
        this.crewAreaTravelCode = value;
    }

    /**
     * Gets the value of the airportArea property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAirportArea() {
        return airportArea;
    }

    /**
     * Sets the value of the airportArea property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAirportArea(String value) {
        this.airportArea = value;
    }

}
