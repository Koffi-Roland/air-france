
package com.afklm.repind.ws.w000443.data.schema591280;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for MaccPersonalInformationV2 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="MaccPersonalInformationV2">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="blueBizNumber" type="{http://www.af-klm.com/services/passenger/SicMarketingType-v1/xsd}BlueBizNumber" minOccurs="0"/>
 *         &lt;element name="fFPNumber" type="{http://www.af-klm.com/services/passenger/SicMarketingType-v1/xsd}FFPnumberType" minOccurs="0"/>
 *         &lt;element name="fFPProgram" type="{http://www.af-klm.com/services/passenger/SicMarketingType-v1/xsd}FFProgramType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MaccPersonalInformationV2", propOrder = {
    "blueBizNumber",
    "ffpNumber",
    "ffpProgram"
})
public class MaccPersonalInformationV2 {

    protected String blueBizNumber;
    @XmlElement(name = "fFPNumber")
    protected String ffpNumber;
    @XmlElement(name = "fFPProgram")
    protected String ffpProgram;

    /**
     * Gets the value of the blueBizNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBlueBizNumber() {
        return blueBizNumber;
    }

    /**
     * Sets the value of the blueBizNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBlueBizNumber(String value) {
        this.blueBizNumber = value;
    }

    /**
     * Gets the value of the ffpNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFFPNumber() {
        return ffpNumber;
    }

    /**
     * Sets the value of the ffpNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFFPNumber(String value) {
        this.ffpNumber = value;
    }

    /**
     * Gets the value of the ffpProgram property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFFPProgram() {
        return ffpProgram;
    }

    /**
     * Sets the value of the ffpProgram property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFFPProgram(String value) {
        this.ffpProgram = value;
    }

}
