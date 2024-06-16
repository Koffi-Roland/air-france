
package com.afklm.bird.ws.w000311.data.schema5299;

import com.afklm.bird.ws.w000311.data.schema5297.CommonsBIRDEntityWSDTO;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for StateProvinceWSDTO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="StateProvinceWSDTO">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.af-klm.com/services/passenger/commonsBusinessData-v1/xsd}CommonsBIRDEntityWSDTO">
 *       &lt;sequence>
 *         &lt;element name="isoCountryCode" type="{http://www.af-klm.com/services/passenger/geographyBusinessData-v1/xsd}DT_ISOCountryCode" minOccurs="0"/>
 *         &lt;element name="iataStateProvinceCode" type="{http://www.af-klm.com/services/passenger/geographyBusinessData-v1/xsd}DT_StateProvinceCode" minOccurs="0"/>
 *         &lt;element name="stateProvinceName" type="{http://www.af-klm.com/services/passenger/commonsBusinessData-v1/xsd}CDT_Name" minOccurs="0"/>
 *         &lt;element name="countryName" type="{http://www.af-klm.com/services/passenger/commonsBusinessData-v1/xsd}CDT_Name" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "StateProvinceWSDTO", propOrder = {
    "isoCountryCode",
    "iataStateProvinceCode",
    "stateProvinceName",
    "countryName"
})
public class StateProvinceWSDTO
    extends CommonsBIRDEntityWSDTO
{

    protected String isoCountryCode;
    protected String iataStateProvinceCode;
    protected String stateProvinceName;
    protected String countryName;

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
     * Gets the value of the iataStateProvinceCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIataStateProvinceCode() {
        return iataStateProvinceCode;
    }

    /**
     * Sets the value of the iataStateProvinceCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIataStateProvinceCode(String value) {
        this.iataStateProvinceCode = value;
    }

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

}
