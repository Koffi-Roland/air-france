
package com.afklm.bird.ws.w000309.data.schema5299;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CityDetailedWSDTO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CityDetailedWSDTO">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.af-klm.com/services/passenger/geographyBusinessData-v1/xsd}CityWSDTO">
 *       &lt;sequence>
 *         &lt;element name="stateProvinceName" type="{http://www.af-klm.com/services/passenger/commonsBusinessData-v1/xsd}CDT_Name" minOccurs="0"/>
 *         &lt;element name="countryName" type="{http://www.af-klm.com/services/passenger/commonsBusinessData-v1/xsd}CDT_Name" minOccurs="0"/>
 *         &lt;element name="iataCountryNumber" type="{http://www.af-klm.com/services/passenger/geographyBusinessData-v1/xsd}DT_IATACountryNumber"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CityDetailedWSDTO", propOrder = {
    "stateProvinceName",
    "countryName",
    "iataCountryNumber"
})
public class CityDetailedWSDTO
    extends CityWSDTO
{

    protected String stateProvinceName;
    protected String countryName;
    protected int iataCountryNumber;

    /**
     * Gets the value of the stateProvinceName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStateProvinceName() {
        return stateProvinceName;
    }

    /**
     * Sets the value of the stateProvinceName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStateProvinceName(String value) {
        this.stateProvinceName = value;
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
     */
    public int getIataCountryNumber() {
        return iataCountryNumber;
    }

    /**
     * Sets the value of the iataCountryNumber property.
     * 
     */
    public void setIataCountryNumber(int value) {
        this.iataCountryNumber = value;
    }

}
