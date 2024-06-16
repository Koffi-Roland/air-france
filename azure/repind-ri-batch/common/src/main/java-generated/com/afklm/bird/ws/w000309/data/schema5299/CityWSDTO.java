
package com.afklm.bird.ws.w000309.data.schema5299;

import com.afklm.bird.ws.w000309.data.schema5297.CommonsBIRDEntityWSDTO;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CityWSDTO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CityWSDTO">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.af-klm.com/services/passenger/commonsBusinessData-v1/xsd}CommonsBIRDEntityWSDTO">
 *       &lt;sequence>
 *         &lt;element name="iataCityCode" type="{http://www.af-klm.com/services/passenger/geographyBusinessData-v1/xsd}DT_IATACityCode" minOccurs="0"/>
 *         &lt;element name="iataCityCodeIndicator" type="{http://www.af-klm.com/services/passenger/commonsBusinessData-v1/xsd}CDT_Indicator" minOccurs="0"/>
 *         &lt;element name="cityName" type="{http://www.af-klm.com/services/passenger/commonsBusinessData-v1/xsd}CDT_Name" minOccurs="0"/>
 *         &lt;element name="stateProvinceCode" type="{http://www.af-klm.com/services/passenger/geographyBusinessData-v1/xsd}DT_StateProvinceCode" minOccurs="0"/>
 *         &lt;element name="isoCountryCode" type="{http://www.af-klm.com/services/passenger/geographyBusinessData-v1/xsd}DT_ISOCountryCode" minOccurs="0"/>
 *         &lt;element name="iataCityNumber" type="{http://www.af-klm.com/services/passenger/geographyBusinessData-v1/xsd}DT_IATACityNumber" minOccurs="0"/>
 *         &lt;element name="iataCityNumberIndicator" type="{http://www.af-klm.com/services/passenger/commonsBusinessData-v1/xsd}CDT_Indicator" minOccurs="0"/>
 *         &lt;element name="icaoCityCode" type="{http://www.af-klm.com/services/passenger/geographyBusinessData-v1/xsd}DT_ICAOCityCode" minOccurs="0"/>
 *         &lt;element name="icaoCityCodeIndicator" type="{http://www.af-klm.com/services/passenger/commonsBusinessData-v1/xsd}CDT_Indicator" minOccurs="0"/>
 *         &lt;element name="timeZoneCode" type="{http://www.af-klm.com/services/passenger/geographyBusinessData-v1/xsd}DT_TimeZoneCode" minOccurs="0"/>
 *         &lt;element name="latitude" type="{http://www.af-klm.com/services/passenger/geographyBusinessData-v1/xsd}CDT_latitude" minOccurs="0"/>
 *         &lt;element name="longitude" type="{http://www.af-klm.com/services/passenger/geographyBusinessData-v1/xsd}CDT_longitude" minOccurs="0"/>
 *         &lt;element name="euIndicator" type="{http://www.af-klm.com/services/passenger/commonsBusinessData-v1/xsd}CDT_Indicator" minOccurs="0"/>
 *         &lt;element name="cityCategory" type="{http://www.af-klm.com/services/passenger/geographyBusinessData-v1/xsd}DT_CityCategory" minOccurs="0"/>
 *         &lt;element name="crewAreaTravelCode" type="{http://www.af-klm.com/services/passenger/geographyBusinessData-v1/xsd}DT_CrewAreaTravelCode" minOccurs="0"/>
 *         &lt;element name="dgpgCode" type="{http://www.af-klm.com/services/passenger/geographyBusinessData-v1/xsd}DT_DGPGCode" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CityWSDTO", propOrder = {
    "iataCityCode",
    "iataCityCodeIndicator",
    "cityName",
    "stateProvinceCode",
    "isoCountryCode",
    "iataCityNumber",
    "iataCityNumberIndicator",
    "icaoCityCode",
    "icaoCityCodeIndicator",
    "timeZoneCode",
    "latitude",
    "longitude",
    "euIndicator",
    "cityCategory",
    "crewAreaTravelCode",
    "dgpgCode"
})
@XmlSeeAlso({
    CityDetailedWSDTO.class
})
public class CityWSDTO
    extends CommonsBIRDEntityWSDTO
{

    protected String iataCityCode;
    protected String iataCityCodeIndicator;
    protected String cityName;
    protected String stateProvinceCode;
    protected String isoCountryCode;
    protected Integer iataCityNumber;
    protected String iataCityNumberIndicator;
    protected String icaoCityCode;
    protected String icaoCityCodeIndicator;
    protected String timeZoneCode;
    protected String latitude;
    protected String longitude;
    protected String euIndicator;
    protected String cityCategory;
    protected String crewAreaTravelCode;
    protected String dgpgCode;

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
     * Gets the value of the iataCityCodeIndicator property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIataCityCodeIndicator() {
        return iataCityCodeIndicator;
    }

    /**
     * Sets the value of the iataCityCodeIndicator property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIataCityCodeIndicator(String value) {
        this.iataCityCodeIndicator = value;
    }

    /**
     * Gets the value of the cityName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCityName() {
        return cityName;
    }

    /**
     * Sets the value of the cityName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCityName(String value) {
        this.cityName = value;
    }

    /**
     * Gets the value of the stateProvinceCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStateProvinceCode() {
        return stateProvinceCode;
    }

    /**
     * Sets the value of the stateProvinceCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStateProvinceCode(String value) {
        this.stateProvinceCode = value;
    }

    /**
     * Gets the value of the isoCountryCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsoCountryCode() {
        return isoCountryCode;
    }

    /**
     * Sets the value of the isoCountryCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsoCountryCode(String value) {
        this.isoCountryCode = value;
    }

    /**
     * Gets the value of the iataCityNumber property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getIataCityNumber() {
        return iataCityNumber;
    }

    /**
     * Sets the value of the iataCityNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setIataCityNumber(Integer value) {
        this.iataCityNumber = value;
    }

    /**
     * Gets the value of the iataCityNumberIndicator property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIataCityNumberIndicator() {
        return iataCityNumberIndicator;
    }

    /**
     * Sets the value of the iataCityNumberIndicator property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIataCityNumberIndicator(String value) {
        this.iataCityNumberIndicator = value;
    }

    /**
     * Gets the value of the icaoCityCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIcaoCityCode() {
        return icaoCityCode;
    }

    /**
     * Sets the value of the icaoCityCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIcaoCityCode(String value) {
        this.icaoCityCode = value;
    }

    /**
     * Gets the value of the icaoCityCodeIndicator property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIcaoCityCodeIndicator() {
        return icaoCityCodeIndicator;
    }

    /**
     * Sets the value of the icaoCityCodeIndicator property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIcaoCityCodeIndicator(String value) {
        this.icaoCityCodeIndicator = value;
    }

    /**
     * Gets the value of the timeZoneCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTimeZoneCode() {
        return timeZoneCode;
    }

    /**
     * Sets the value of the timeZoneCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTimeZoneCode(String value) {
        this.timeZoneCode = value;
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
     * Gets the value of the euIndicator property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEuIndicator() {
        return euIndicator;
    }

    /**
     * Sets the value of the euIndicator property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEuIndicator(String value) {
        this.euIndicator = value;
    }

    /**
     * Gets the value of the cityCategory property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCityCategory() {
        return cityCategory;
    }

    /**
     * Sets the value of the cityCategory property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCityCategory(String value) {
        this.cityCategory = value;
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
     * Gets the value of the dgpgCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDgpgCode() {
        return dgpgCode;
    }

    /**
     * Sets the value of the dgpgCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDgpgCode(String value) {
        this.dgpgCode = value;
    }

}
