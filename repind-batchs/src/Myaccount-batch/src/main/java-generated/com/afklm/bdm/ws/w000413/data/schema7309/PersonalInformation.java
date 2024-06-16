
package com.afklm.bdm.ws.w000413.data.schema7309;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * information about airline partner Frequent Flyer programs.
 * 
 * <p>Java class for PersonalInformation complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PersonalInformation">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="blueBizNumber" type="{http://www.af-klm.com/services/bdm/types-v1/xsd}BlueBizNumber" minOccurs="0"/>
 *         &lt;element name="FFPNumber" type="{http://www.af-klm.com/services/bdm/types-v1/xsd}FFPnumberType" minOccurs="0"/>
 *         &lt;element name="FFPProgram" type="{http://www.af-klm.com/services/bdm/types-v1/xsd}FFProgramType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PersonalInformation", propOrder = {
    "blueBizNumber",
    "ffpNumber",
    "ffpProgram"
})
public class PersonalInformation {

    protected String blueBizNumber;
    @XmlElement(name = "FFPNumber")
    protected String ffpNumber;
    @XmlElement(name = "FFPProgram")
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
