
package com.afklm.repind.ws.createorupdateindividualdata.v08.data.request;

import com.afklm.repind.ws.createorupdateindividualdata.sicindividutype.CommunicationPreferences;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ComunicationPreferencesRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ComunicationPreferencesRequest">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="communicationPreferences" type="{http://www.af-klm.com/services/passenger/SicIndividuType-v3/xsd}CommunicationPreferences" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ComunicationPreferencesRequest", propOrder = {
    "communicationPreferences"
})
public class ComunicationPreferencesRequest {

    protected CommunicationPreferences communicationPreferences;

    /**
     * Gets the value of the communicationPreferences property.
     * 
     * @return
     *     possible object is
     *     {@link CommunicationPreferences }
     *     
     */
    public CommunicationPreferences getCommunicationPreferences() {
        return communicationPreferences;
    }

    /**
     * Sets the value of the communicationPreferences property.
     * 
     * @param value
     *     allowed object is
     *     {@link CommunicationPreferences }
     *     
     */
    public void setCommunicationPreferences(CommunicationPreferences value) {
        this.communicationPreferences = value;
    }

}
