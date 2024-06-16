
package com.afklm.bird.ws.w000311.data.schema5299;

import com.afklm.bird.ws.w000311.data.schema5297.CommonsBIRDEntityCriteriaWSDTO;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CityCriteriaWSDTO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CityCriteriaWSDTO">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.af-klm.com/services/passenger/commonsBusinessData-v1/xsd}CommonsBIRDEntityCriteriaWSDTO">
 *       &lt;sequence>
 *         &lt;element name="iataCityCode" type="{http://www.af-klm.com/services/passenger/geographyBusinessData-v1/xsd}DT_IATACityCode" minOccurs="0"/>
 *         &lt;element name="iataCityCodeOperator" type="{http://www.af-klm.com/services/passenger/commonsBusinessData-v1/xsd}CDT_StringOperator" minOccurs="0"/>
 *         &lt;element name="iataCityCodeIndicator" type="{http://www.af-klm.com/services/passenger/commonsBusinessData-v1/xsd}CDT_Indicator" minOccurs="0"/>
 *         &lt;element name="icaoCityCode" type="{http://www.af-klm.com/services/passenger/geographyBusinessData-v1/xsd}DT_ICAOCityCode" minOccurs="0"/>
 *         &lt;element name="icaoCityCodeOperator" type="{http://www.af-klm.com/services/passenger/commonsBusinessData-v1/xsd}CDT_StringOperator" minOccurs="0"/>
 *         &lt;element name="icaoCityCodeIndicator" type="{http://www.af-klm.com/services/passenger/commonsBusinessData-v1/xsd}CDT_Indicator" minOccurs="0"/>
 *         &lt;element name="cityName" type="{http://www.af-klm.com/services/passenger/commonsBusinessData-v1/xsd}CDT_Name" minOccurs="0"/>
 *         &lt;element name="cityNameOperator" type="{http://www.af-klm.com/services/passenger/commonsBusinessData-v1/xsd}CDT_WordingOperator" minOccurs="0"/>
 *         &lt;element name="iataCityNumber" type="{http://www.af-klm.com/services/passenger/geographyBusinessData-v1/xsd}DT_IATACityNumber" minOccurs="0"/>
 *         &lt;element name="iataCityNumberOperator" type="{http://www.af-klm.com/services/passenger/commonsBusinessData-v1/xsd}CDT_StringOperator" minOccurs="0"/>
 *         &lt;element name="iataCityNumberIndicator" type="{http://www.af-klm.com/services/passenger/commonsBusinessData-v1/xsd}CDT_Indicator" minOccurs="0"/>
 *         &lt;element name="euIndicator" type="{http://www.af-klm.com/services/passenger/commonsBusinessData-v1/xsd}CDT_Indicator" minOccurs="0"/>
 *         &lt;element name="cityCategory" type="{http://www.af-klm.com/services/passenger/geographyBusinessData-v1/xsd}DT_CityCategory" minOccurs="0"/>
 *         &lt;element name="cityCategoryOperator" type="{http://www.af-klm.com/services/passenger/commonsBusinessData-v1/xsd}CDT_BasicOperator" minOccurs="0"/>
 *         &lt;element name="stateProvinceCode" type="{http://www.af-klm.com/services/passenger/geographyBusinessData-v1/xsd}DT_StateProvinceCode" minOccurs="0"/>
 *         &lt;element name="stateProvinceCodeOperator" type="{http://www.af-klm.com/services/passenger/commonsBusinessData-v1/xsd}CDT_StringOperator" minOccurs="0"/>
 *         &lt;element name="isoCountryCode" type="{http://www.af-klm.com/services/passenger/geographyBusinessData-v1/xsd}DT_ISOCountryCode" minOccurs="0"/>
 *         &lt;element name="isoCountryCodeOperator" type="{http://www.af-klm.com/services/passenger/commonsBusinessData-v1/xsd}CDT_StringOperator" minOccurs="0"/>
 *         &lt;element name="timeZoneCode" type="{http://www.af-klm.com/services/passenger/geographyBusinessData-v1/xsd}DT_TimeZoneCode" minOccurs="0"/>
 *         &lt;element name="timeZoneCodeOperator" type="{http://www.af-klm.com/services/passenger/commonsBusinessData-v1/xsd}CDT_StringOperator" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CityCriteriaWSDTO", propOrder = {
    "iataCityCode",
    "iataCityCodeOperator",
    "iataCityCodeIndicator",
    "icaoCityCode",
    "icaoCityCodeOperator",
    "icaoCityCodeIndicator",
    "cityName",
    "cityNameOperator",
    "iataCityNumber",
    "iataCityNumberOperator",
    "iataCityNumberIndicator",
    "euIndicator",
    "cityCategory",
    "cityCategoryOperator",
    "stateProvinceCode",
    "stateProvinceCodeOperator",
    "isoCountryCode",
    "isoCountryCodeOperator",
    "timeZoneCode",
    "timeZoneCodeOperator"
})
@XmlSeeAlso({
    CityDetailedCriteriaWSDTO.class
})
public class CityCriteriaWSDTO
    extends CommonsBIRDEntityCriteriaWSDTO
{

    protected String iataCityCode;
    protected String iataCityCodeOperator;
    protected String iataCityCodeIndicator;
    protected String icaoCityCode;
    protected String icaoCityCodeOperator;
    protected String icaoCityCodeIndicator;
    protected String cityName;
    protected String cityNameOperator;
    protected Integer iataCityNumber;
    protected String iataCityNumberOperator;
    protected String iataCityNumberIndicator;
    protected String euIndicator;
    protected String cityCategory;
    protected String cityCategoryOperator;
    protected String stateProvinceCode;
    protected String stateProvinceCodeOperator;
    protected String isoCountryCode;
    protected String isoCountryCodeOperator;
    protected String timeZoneCode;
    protected String timeZoneCodeOperator;

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
     * Gets the value of the icaoCityCodeOperator property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIcaoCityCodeOperator() {
        return icaoCityCodeOperator;
    }

    /**
     * Sets the value of the icaoCityCodeOperator property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIcaoCityCodeOperator(String value) {
        this.icaoCityCodeOperator = value;
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
     * Gets the value of the cityNameOperator property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCityNameOperator() {
        return cityNameOperator;
    }

    /**
     * Sets the value of the cityNameOperator property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCityNameOperator(String value) {
        this.cityNameOperator = value;
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
     * Gets the value of the iataCityNumberOperator property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIataCityNumberOperator() {
        return iataCityNumberOperator;
    }

    /**
     * Sets the value of the iataCityNumberOperator property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIataCityNumberOperator(String value) {
        this.iataCityNumberOperator = value;
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
     * Gets the value of the cityCategoryOperator property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCityCategoryOperator() {
        return cityCategoryOperator;
    }

    /**
     * Sets the value of the cityCategoryOperator property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCityCategoryOperator(String value) {
        this.cityCategoryOperator = value;
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
     * Gets the value of the stateProvinceCodeOperator property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStateProvinceCodeOperator() {
        return stateProvinceCodeOperator;
    }

    /**
     * Sets the value of the stateProvinceCodeOperator property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStateProvinceCodeOperator(String value) {
        this.stateProvinceCodeOperator = value;
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
     * Gets the value of the isoCountryCodeOperator property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsoCountryCodeOperator() {
        return isoCountryCodeOperator;
    }

    /**
     * Sets the value of the isoCountryCodeOperator property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsoCountryCodeOperator(String value) {
        this.isoCountryCodeOperator = value;
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
     * Gets the value of the timeZoneCodeOperator property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTimeZoneCodeOperator() {
        return timeZoneCodeOperator;
    }

    /**
     * Sets the value of the timeZoneCodeOperator property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTimeZoneCodeOperator(String value) {
        this.timeZoneCodeOperator = value;
    }

}
