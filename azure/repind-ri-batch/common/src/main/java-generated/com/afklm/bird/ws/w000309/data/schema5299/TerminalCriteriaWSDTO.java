
package com.afklm.bird.ws.w000309.data.schema5299;

import com.afklm.bird.ws.w000309.data.schema5297.CommonsBIRDEntityCriteriaWSDTO;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TerminalCriteriaWSDTO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TerminalCriteriaWSDTO">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.af-klm.com/services/passenger/commonsBusinessData-v1/xsd}CommonsBIRDEntityCriteriaWSDTO">
 *       &lt;sequence>
 *         &lt;element name="codeOperator" type="{http://www.af-klm.com/services/passenger/commonsBusinessData-v1/xsd}CDT_StringOperator" minOccurs="0"/>
 *         &lt;element name="nameOperator" type="{http://www.af-klm.com/services/passenger/commonsBusinessData-v1/xsd}CDT_WordingOperator" minOccurs="0"/>
 *         &lt;element name="iataStationCodeOperator" type="{http://www.af-klm.com/services/passenger/commonsBusinessData-v1/xsd}CDT_StringOperator" minOccurs="0"/>
 *         &lt;element name="code" type="{http://www.af-klm.com/services/passenger/geographyBusinessData-v1/xsd}DT_TimeZoneCode" minOccurs="0"/>
 *         &lt;element name="name" type="{http://www.af-klm.com/services/passenger/commonsBusinessData-v1/xsd}CDT_Name" minOccurs="0"/>
 *         &lt;element name="iataStationCode" type="{http://www.af-klm.com/services/passenger/geographyBusinessData-v1/xsd}DT_IATAStationCode" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TerminalCriteriaWSDTO", propOrder = {
    "codeOperator",
    "nameOperator",
    "iataStationCodeOperator",
    "code",
    "name",
    "iataStationCode"
})
public class TerminalCriteriaWSDTO
    extends CommonsBIRDEntityCriteriaWSDTO
{

    protected String codeOperator;
    protected String nameOperator;
    protected String iataStationCodeOperator;
    protected String code;
    protected String name;
    protected String iataStationCode;

    /**
     * Gets the value of the codeOperator property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodeOperator() {
        return codeOperator;
    }

    /**
     * Sets the value of the codeOperator property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodeOperator(String value) {
        this.codeOperator = value;
    }

    /**
     * Gets the value of the nameOperator property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNameOperator() {
        return nameOperator;
    }

    /**
     * Sets the value of the nameOperator property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNameOperator(String value) {
        this.nameOperator = value;
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
     * Gets the value of the code property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCode() {
        return code;
    }

    /**
     * Sets the value of the code property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCode(String value) {
        this.code = value;
    }

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

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

}
