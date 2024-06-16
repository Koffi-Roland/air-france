
package com.afklm.repind.ws.w000443.data.schema571954;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ReturnDetails complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ReturnDetails">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="detailedCode" type="{http://www.af-klm.com/services/passenger/ProspectDataType-v1/xsd}DTypeDetailedCode"/>
 *         &lt;element name="labelCode" type="{http://www.af-klm.com/services/passenger/ProspectDataType-v1/xsd}DTypeLabelCode"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ReturnDetails", propOrder = {
    "detailedCode",
    "labelCode"
})
public class ReturnDetails {

    @XmlElement(required = true)
    protected String detailedCode;
    @XmlElement(required = true)
    protected String labelCode;

    /**
     * Gets the value of the detailedCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDetailedCode() {
        return detailedCode;
    }

    /**
     * Sets the value of the detailedCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDetailedCode(String value) {
        this.detailedCode = value;
    }

    /**
     * Gets the value of the labelCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLabelCode() {
        return labelCode;
    }

    /**
     * Sets the value of the labelCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLabelCode(String value) {
        this.labelCode = value;
    }

}
