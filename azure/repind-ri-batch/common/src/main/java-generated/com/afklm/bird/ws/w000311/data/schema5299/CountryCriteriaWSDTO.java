
package com.afklm.bird.ws.w000311.data.schema5299;

import com.afklm.bird.ws.w000311.data.schema5297.CommonsBIRDEntityCriteriaWSDTO;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CountryCriteriaWSDTO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CountryCriteriaWSDTO">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.af-klm.com/services/passenger/commonsBusinessData-v1/xsd}CommonsBIRDEntityCriteriaWSDTO">
 *       &lt;sequence>
 *         &lt;element name="isoCountryCode" type="{http://www.af-klm.com/services/passenger/geographyBusinessData-v1/xsd}DT_ISOCountryCode" minOccurs="0"/>
 *         &lt;element name="isoCountryCodeOperator" type="{http://www.af-klm.com/services/passenger/commonsBusinessData-v1/xsd}CDT_StringOperator" minOccurs="0"/>
 *         &lt;element name="iataIndicator" type="{http://www.af-klm.com/services/passenger/commonsBusinessData-v1/xsd}CDT_Indicator" minOccurs="0"/>
 *         &lt;element name="countryName" type="{http://www.af-klm.com/services/passenger/commonsBusinessData-v1/xsd}CDT_Name" minOccurs="0"/>
 *         &lt;element name="countryNameOperator" type="{http://www.af-klm.com/services/passenger/commonsBusinessData-v1/xsd}CDT_WordingOperator" minOccurs="0"/>
 *         &lt;element name="iataCountryNumber" type="{http://www.af-klm.com/services/passenger/geographyBusinessData-v1/xsd}DT_IATACountryNumber" minOccurs="0"/>
 *         &lt;element name="iataCountryNumberOperator" type="{http://www.af-klm.com/services/passenger/commonsBusinessData-v1/xsd}CDT_StringOperator" minOccurs="0"/>
 *         &lt;element name="iataCountryNumberIndicator" type="{http://www.af-klm.com/services/passenger/commonsBusinessData-v1/xsd}CDT_Indicator" minOccurs="0"/>
 *         &lt;element name="nationality" type="{http://www.af-klm.com/services/passenger/commonsBusinessData-v1/xsd}CDT_Name" minOccurs="0"/>
 *         &lt;element name="nationalityOperator" type="{http://www.af-klm.com/services/passenger/commonsBusinessData-v1/xsd}CDT_WordingOperator" minOccurs="0"/>
 *         &lt;element name="currency" type="{http://www.af-klm.com/services/passenger/geographyBusinessData-v1/xsd}DT_Currency" minOccurs="0"/>
 *         &lt;element name="currencyOperator" type="{http://www.af-klm.com/services/passenger/commonsBusinessData-v1/xsd}CDT_StringOperator" minOccurs="0"/>
 *         &lt;element name="iataArea" type="{http://www.af-klm.com/services/passenger/geographyBusinessData-v1/xsd}DT_IATAAreaCode" minOccurs="0"/>
 *         &lt;element name="iataAreaOperator" type="{http://www.af-klm.com/services/passenger/commonsBusinessData-v1/xsd}CDT_BasicOperator" minOccurs="0"/>
 *         &lt;element name="afArea" type="{http://www.af-klm.com/services/passenger/geographyBusinessData-v1/xsd}DT_AFArea" minOccurs="0"/>
 *         &lt;element name="afAreaOperator" type="{http://www.af-klm.com/services/passenger/commonsBusinessData-v1/xsd}CDT_BasicOperator" minOccurs="0"/>
 *         &lt;element name="ueIndicator" type="{http://www.af-klm.com/services/passenger/commonsBusinessData-v1/xsd}CDT_Indicator" minOccurs="0"/>
 *         &lt;element name="schengenIndicator" type="{http://www.af-klm.com/services/passenger/commonsBusinessData-v1/xsd}CDT_Indicator" minOccurs="0"/>
 *         &lt;element name="euroIndicator" type="{http://www.af-klm.com/services/passenger/commonsBusinessData-v1/xsd}CDT_Indicator" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CountryCriteriaWSDTO", propOrder = {
    "isoCountryCode",
    "isoCountryCodeOperator",
    "iataIndicator",
    "countryName",
    "countryNameOperator",
    "iataCountryNumber",
    "iataCountryNumberOperator",
    "iataCountryNumberIndicator",
    "nationality",
    "nationalityOperator",
    "currency",
    "currencyOperator",
    "iataArea",
    "iataAreaOperator",
    "afArea",
    "afAreaOperator",
    "ueIndicator",
    "schengenIndicator",
    "euroIndicator"
})
public class CountryCriteriaWSDTO
    extends CommonsBIRDEntityCriteriaWSDTO
{

    protected String isoCountryCode;
    protected String isoCountryCodeOperator;
    protected String iataIndicator;
    protected String countryName;
    protected String countryNameOperator;
    protected Integer iataCountryNumber;
    protected String iataCountryNumberOperator;
    protected String iataCountryNumberIndicator;
    protected String nationality;
    protected String nationalityOperator;
    protected String currency;
    protected String currencyOperator;
    protected String iataArea;
    protected String iataAreaOperator;
    protected String afArea;
    protected String afAreaOperator;
    protected String ueIndicator;
    protected String schengenIndicator;
    protected String euroIndicator;

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
     * Gets the value of the countryNameOperator property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCountryNameOperator() {
        return countryNameOperator;
    }

    /**
     * Sets the value of the countryNameOperator property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCountryNameOperator(String value) {
        this.countryNameOperator = value;
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
     * Gets the value of the iataCountryNumberOperator property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIataCountryNumberOperator() {
        return iataCountryNumberOperator;
    }

    /**
     * Sets the value of the iataCountryNumberOperator property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIataCountryNumberOperator(String value) {
        this.iataCountryNumberOperator = value;
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
     * Gets the value of the nationalityOperator property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNationalityOperator() {
        return nationalityOperator;
    }

    /**
     * Sets the value of the nationalityOperator property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNationalityOperator(String value) {
        this.nationalityOperator = value;
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
     * Gets the value of the currencyOperator property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCurrencyOperator() {
        return currencyOperator;
    }

    /**
     * Sets the value of the currencyOperator property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCurrencyOperator(String value) {
        this.currencyOperator = value;
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
     * Gets the value of the iataAreaOperator property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIataAreaOperator() {
        return iataAreaOperator;
    }

    /**
     * Sets the value of the iataAreaOperator property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIataAreaOperator(String value) {
        this.iataAreaOperator = value;
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

    /**
     * Gets the value of the afAreaOperator property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAfAreaOperator() {
        return afAreaOperator;
    }

    /**
     * Sets the value of the afAreaOperator property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAfAreaOperator(String value) {
        this.afAreaOperator = value;
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

}
