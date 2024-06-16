
package com.afklm.repind.ws.w000443.data.schema587190;

import com.afklm.repind.ws.w000443.data.schema576961.TitleCodeEnum;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Civilian complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Civilian">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="titleCode" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}TitleCodeEnum" minOccurs="0"/>
 *         &lt;element name="frenchWording" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypeLabelFR" minOccurs="0"/>
 *         &lt;element name="englishWording" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypeLabelGB" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Civilian", propOrder = {
    "titleCode",
    "frenchWording",
    "englishWording"
})
public class Civilian {

    protected TitleCodeEnum titleCode;
    protected String frenchWording;
    protected String englishWording;

    /**
     * Gets the value of the titleCode property.
     * 
     * @return
     *     possible object is
     *     {@link TitleCodeEnum }
     *     
     */
    public TitleCodeEnum getTitleCode() {
        return titleCode;
    }

    /**
     * Sets the value of the titleCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link TitleCodeEnum }
     *     
     */
    public void setTitleCode(TitleCodeEnum value) {
        this.titleCode = value;
    }

    /**
     * Gets the value of the frenchWording property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFrenchWording() {
        return frenchWording;
    }

    /**
     * Sets the value of the frenchWording property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFrenchWording(String value) {
        this.frenchWording = value;
    }

    /**
     * Gets the value of the englishWording property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEnglishWording() {
        return englishWording;
    }

    /**
     * Sets the value of the englishWording property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEnglishWording(String value) {
        this.englishWording = value;
    }

}
