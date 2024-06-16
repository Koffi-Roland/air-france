
package com.afklm.repind.ws.w000443.data.schema587190;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for IndividualInformationException complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="IndividualInformationException">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="noErr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="libErr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "IndividualInformationException", propOrder = {
    "noErr",
    "libErr"
})
public class IndividualInformationException {

    protected String noErr;
    protected String libErr;

    /**
     * Gets the value of the noErr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNoErr() {
        return noErr;
    }

    /**
     * Sets the value of the noErr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNoErr(String value) {
        this.noErr = value;
    }

    /**
     * Gets the value of the libErr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLibErr() {
        return libErr;
    }

    /**
     * Sets the value of the libErr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLibErr(String value) {
        this.libErr = value;
    }

}
