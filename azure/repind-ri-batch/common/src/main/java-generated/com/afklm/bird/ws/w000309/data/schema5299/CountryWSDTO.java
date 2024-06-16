
package com.afklm.bird.ws.w000309.data.schema5299;

import com.afklm.bird.ws.w000309.data.schema5297.CommonsBIRDEntityWSDTO;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CountryWSDTO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CountryWSDTO">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.af-klm.com/services/passenger/commonsBusinessData-v1/xsd}CommonsBIRDEntityWSDTO">
 *       &lt;sequence>
 *         &lt;element name="isoCountryCode" type="{http://www.af-klm.com/services/passenger/geographyBusinessData-v1/xsd}DT_ISOCountryCode" minOccurs="0"/>
 *         &lt;element name="countryName" type="{http://www.af-klm.com/services/passenger/commonsBusinessData-v1/xsd}CDT_Name" minOccurs="0"/>
 *         &lt;element name="iataCountryNumber" type="{http://www.af-klm.com/services/passenger/geographyBusinessData-v1/xsd}DT_IATACountryNumber" minOccurs="0"/>
 *         &lt;element name="iataIndicator" type="{http://www.af-klm.com/services/passenger/commonsBusinessData-v1/xsd}CDT_Indicator" minOccurs="0"/>
 *         &lt;element name="iataCountryNumberIndicator" type="{http://www.af-klm.com/services/passenger/commonsBusinessData-v1/xsd}CDT_Indicator" minOccurs="0"/>
 *         &lt;element name="overflightRequestIndicator" type="{http://www.af-klm.com/services/passenger/commonsBusinessData-v1/xsd}CDT_Indicator" minOccurs="0"/>
 *         &lt;element name="ueIndicator" type="{http://www.af-klm.com/services/passenger/commonsBusinessData-v1/xsd}CDT_Indicator" minOccurs="0"/>
 *         &lt;element name="schengenIndicator" type="{http://www.af-klm.com/services/passenger/commonsBusinessData-v1/xsd}CDT_Indicator" minOccurs="0"/>
 *         &lt;element name="euroIndicator" type="{http://www.af-klm.com/services/passenger/commonsBusinessData-v1/xsd}CDT_Indicator" minOccurs="0"/>
 *         &lt;element name="currency" type="{http://www.af-klm.com/services/passenger/geographyBusinessData-v1/xsd}DT_Currency" minOccurs="0"/>
 *         &lt;element name="nationality" type="{http://www.af-klm.com/services/passenger/commonsBusinessData-v1/xsd}CDT_Name" minOccurs="0"/>
 *         &lt;element name="iataArea" type="{http://www.af-klm.com/services/passenger/geographyBusinessData-v1/xsd}DT_IATAAreaCode" minOccurs="0"/>
 *         &lt;element name="afArea" type="{http://www.af-klm.com/services/passenger/geographyBusinessData-v1/xsd}DT_AFArea" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CountryWSDTO", propOrder = {
    "isoCountryCode",
    "countryName",
    "iataCountryNumber",
    "iataIndicator",
    "iataCountryNumberIndicator",
    "overflightRequestIndicator",
    "ueIndicator",
    "schengenIndicator",
    "euroIndicator",
    "currency",
    "nationality",
    "iataArea",
    "afArea"
})
public class CountryWSDTO
    extends CommonsBIRDEntityWSDTO
{

    protected String isoCountryCode;
    protected String countryName;
    protected Integer iataCountryNumber;
    protected String iataIndicator;
    protected String iataCountryNumberIndicator;
    protected String overflightRequestIndicator;
    protected String ueIndicator;
    protected String schengenIndicator;
    protected String euroIndicator;
    protected String currency;
    protected String nationality;
    protected String iataArea;
    protected String afArea;

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
     * Gets the value of the countryName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCountryName() {
        return countryName;
    }

    /**
     * Sets the value of the countryName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCountryName(String value) {
        this.countryName = value;
    }

    /**
     * Gets the value of the iataCountryNumber property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getIataCountryNumber() {
        return iataCountryNumber;
    }

    /**
     * Sets the value of the iataCountryNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setIataCountryNumber(Integer value) {
        this.iataCountryNumber = value;
    }

    /**
     * Gets the value of the iataIndicator property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIataIndicator() {
        return iataIndicator;
    }

    /**
     * Sets the value of the iataIndicator property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIataIndicator(String value) {
        this.iataIndicator = value;
    }

    /**
     * Gets the value of the iataCountryNumberIndicator property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIataCountryNumberIndicator() {
        return iataCountryNumberIndicator;
    }

    /**
     * Sets the value of the iataCountryNumberIndicator property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIataCountryNumberIndicator(String value) {
        this.iataCountryNumberIndicator = value;
    }

    /**
     * Gets the value of the overflightRequestIndicator property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOverflightRequestIndicator() {
        return overflightRequestIndicator;
    }

    /**
     * Sets the value of the overflightRequestIndicator property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOverflightRequestIndicator(String value) {
        this.overflightRequestIndicator = value;
    }

    /**
     * Gets the value of the ueIndicator property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUeIndicator() {
        return ueIndicator;
    }

    /**
     * Sets the value of the ueIndicator property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUeIndicator(String value) {
        this.ueIndicator = value;
    }

    /**
     * Gets the value of the schengenIndicator property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSchengenIndicator() {
        return schengenIndicator;
    }

    /**
     * Sets the value of the schengenIndicator property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSchengenIndicator(String value) {
        this.schengenIndicator = value;
    }

    /**
     * Gets the value of the euroIndicator property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEuroIndicator() {
        return euroIndicator;
    }

    /**
     * Sets the value of the euroIndicator property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEuroIndicator(String value) {
        this.euroIndicator = value;
    }

    /**
     * Gets the value of the currency property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCurrency() {
        return currency;
    }

    /**
     * Sets the value of the currency property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCurrency(String value) {
        this.currency = value;
    }

    /**
     * Gets the value of the nationality property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNationality() {
        return nationality;
    }

    /**
     * Sets the value of the nationality property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNationality(String value) {
        this.nationality = value;
    }

    /**
     * Gets the value of the iataArea property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIataArea() {
        return iataArea;
    }

    /**
     * Sets the value of the iataArea property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIataArea(String value) {
        this.iataArea = value;
    }

    /**
     * Gets the value of the afArea property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAfArea() {
        return afArea;
    }

    /**
     * Sets the value of the afArea property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAfArea(String value) {
        this.afArea = value;
    }

}
