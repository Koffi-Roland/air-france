
package com.afklm.repind.ws.createorupdateindividualdata.v08.data.request;

import com.afklm.repind.ws.createorupdateindividualdata.sicindividutype.PrefilledNumbers;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PrefilledNumbersRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PrefilledNumbersRequest">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="prefilledNumbers" type="{http://www.af-klm.com/services/passenger/SicIndividuType-v3/xsd}PrefilledNumbers" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PrefilledNumbersRequest", propOrder = {
    "prefilledNumbers"
})
public class PrefilledNumbersRequest {

    protected PrefilledNumbers prefilledNumbers;

    /**
     * Gets the value of the prefilledNumbers property.
     * 
     * @return
     *     possible object is
     *     {@link PrefilledNumbers }
     *     
     */
    public PrefilledNumbers getPrefilledNumbers() {
        return prefilledNumbers;
    }

    /**
     * Sets the value of the prefilledNumbers property.
     * 
     * @param value
     *     allowed object is
     *     {@link PrefilledNumbers }
     *     
     */
    public void setPrefilledNumbers(PrefilledNumbers value) {
        this.prefilledNumbers = value;
    }

}
