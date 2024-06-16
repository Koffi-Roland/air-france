
package com.afklm.bird.ws.w000311.data.schema5299;

import com.afklm.bird.ws.w000311.data.schema5297.CommonsBIRDEntityCriteriaWSDTO;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for StationCriteriaWSDTO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="StationCriteriaWSDTO">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.af-klm.com/services/passenger/commonsBusinessData-v1/xsd}CommonsBIRDEntityCriteriaWSDTO">
 *       &lt;sequence>
 *         &lt;element name="iataStationCode" type="{http://www.af-klm.com/services/passenger/geographyBusinessData-v1/xsd}DT_IATAStationCode" minOccurs="0"/>
 *         &lt;element name="iataStationCodeOperator" type="{http://www.af-klm.com/services/passenger/commonsBusinessData-v1/xsd}CDT_StringOperator" minOccurs="0"/>
 *         &lt;element name="iataStationCodeIndicator" type="{http://www.af-klm.com/services/passenger/commonsBusinessData-v1/xsd}CDT_Indicator" minOccurs="0"/>
 *         &lt;element name="icaoStationCode" type="{http://www.af-klm.com/services/passenger/geographyBusinessData-v1/xsd}DT_ICAOStationCode" minOccurs="0"/>
 *         &lt;element name="icaoCodeOperator" type="{http://www.af-klm.com/services/passenger/commonsBusinessData-v1/xsd}CDT_StringOperator" minOccurs="0"/>
 *         &lt;element name="icaoStationCodeIndicator" type="{http://www.af-klm.com/services/passenger/commonsBusinessData-v1/xsd}CDT_Indicator" minOccurs="0"/>
 *         &lt;element name="stationName" type="{http://www.af-klm.com/services/passenger/commonsBusinessData-v1/xsd}CDT_Name" minOccurs="0"/>
 *         &lt;element name="stationNameOperator" type="{http://www.af-klm.com/services/passenger/commonsBusinessData-v1/xsd}CDT_WordingOperator" minOccurs="0"/>
 *         &lt;element name="iataCityCode" type="{http://www.af-klm.com/services/passenger/geographyBusinessData-v1/xsd}DT_IATACityCode" minOccurs="0"/>
 *         &lt;element name="iataCityCodeOperator" type="{http://www.af-klm.com/services/passenger/commonsBusinessData-v1/xsd}CDT_StringOperator" minOccurs="0"/>
 *         &lt;element name="locationType" type="{http://www.af-klm.com/services/passenger/geographyBusinessData-v1/xsd}DT_LocationType" minOccurs="0"/>
 *         &lt;element name="locationTypeOperator" type="{http://www.af-klm.com/services/passenger/commonsBusinessData-v1/xsd}CDT_BasicOperator" minOccurs="0"/>
 *         &lt;element name="airportArea" type="{http://www.af-klm.com/services/passenger/geographyBusinessData-v1/xsd}DT_AirportArea" minOccurs="0"/>
 *         &lt;element name="airportAreaOperator" type="{http://www.af-klm.com/services/passenger/commonsBusinessData-v1/xsd}CDT_BasicOperator" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "StationCriteriaWSDTO", propOrder = {
    "iataStationCode",
    "iataStationCodeOperator",
    "iataStationCodeIndicator",
    "icaoStationCode",
    "icaoCodeOperator",
    "icaoStationCodeIndicator",
    "stationName",
    "stationNameOperator",
    "iataCityCode",
    "iataCityCodeOperator",
    "locationType",
    "locationTypeOperator",
    "airportArea",
    "airportAreaOperator"
})
public class StationCriteriaWSDTO
    extends CommonsBIRDEntityCriteriaWSDTO
{

    protected String iataStationCode;
    protected String iataStationCodeOperator;
    protected String iataStationCodeIndicator;
    protected String icaoStationCode;
    protected String icaoCodeOperator;
    protected String icaoStationCodeIndicator;
    protected String stationName;
    protected String stationNameOperator;
    protected String iataCityCode;
    protected String iataCityCodeOperator;
    protected String locationType;
    protected String locationTypeOperator;
    protected String airportArea;
    protected String airportAreaOperator;

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
     * Gets the value of the iataStationCodeOperator property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIataStationCodeOperator() {
        return iataStationCodeOperator;
    }

    /**
     * Sets the value of the iataStationCodeOperator property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIataStationCodeOperator(String value) {
        this.iataStationCodeOperator = value;
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
     * Gets the value of the icaoCodeOperator property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIcaoCodeOperator() {
        return icaoCodeOperator;
    }

    /**
     * Sets the value of the icaoCodeOperator property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIcaoCodeOperator(String value) {
        this.icaoCodeOperator = value;
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
     * Gets the value of the stationNameOperator property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStationNameOperator() {
        return stationNameOperator;
    }

    /**
     * Sets the value of the stationNameOperator property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStationNameOperator(String value) {
        this.stationNameOperator = value;
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
     * Gets the value of the iataCityCodeOperator property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIataCityCodeOperator() {
        return iataCityCodeOperator;
    }

    /**
     * Sets the value of the iataCityCodeOperator property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIataCityCodeOperator(String value) {
        this.iataCityCodeOperator = value;
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
     * Gets the value of the locationTypeOperator property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLocationTypeOperator() {
        return locationTypeOperator;
    }

    /**
     * Sets the value of the locationTypeOperator property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLocationTypeOperator(String value) {
        this.locationTypeOperator = value;
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

    /**
     * Gets the value of the airportAreaOperator property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAirportAreaOperator() {
        return airportAreaOperator;
    }

    /**
     * Sets the value of the airportAreaOperator property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAirportAreaOperator(String value) {
        this.airportAreaOperator = value;
    }

}
