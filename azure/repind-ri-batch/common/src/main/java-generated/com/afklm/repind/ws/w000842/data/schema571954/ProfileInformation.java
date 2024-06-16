
package com.afklm.repind.ws.w000842.data.schema571954;

import com.afklm.repind.ws.w000842.data.schema569983.LanguageCodesEnum;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ProfileInformation complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ProfileInformation">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="languageCode" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}LanguageCodesEnum" minOccurs="0"/>
 *         &lt;element name="carrierCode" type="{http://www.af-klm.com/services/passenger/ProspectDataType-v1/xsd}DTypeCarrier" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ProfileInformation", propOrder = {
    "languageCode",
    "carrierCode"
})
public class ProfileInformation {

    protected LanguageCodesEnum languageCode;
    protected String carrierCode;

    /**
     * Gets the value of the languageCode property.
     * 
     * @return
     *     possible object is
     *     {@link LanguageCodesEnum }
     *     
     */
    public LanguageCodesEnum getLanguageCode() {
        return languageCode;
    }

    /**
     * Sets the value of the languageCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link LanguageCodesEnum }
     *     
     */
    public void setLanguageCode(LanguageCodesEnum value) {
        this.languageCode = value;
    }

    /**
     * Gets the value of the carrierCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCarrierCode() {
        return carrierCode;
    }

    /**
     * Sets the value of the carrierCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCarrierCode(String value) {
        this.carrierCode = value;
    }

}
