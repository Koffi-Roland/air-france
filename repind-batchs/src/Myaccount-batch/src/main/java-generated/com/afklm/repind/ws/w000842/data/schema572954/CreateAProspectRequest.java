
package com.afklm.repind.ws.w000842.data.schema572954;

import com.afklm.repind.ws.w000842.data.schema571954.*;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CreateAProspectRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CreateAProspectRequest">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="contextData" type="{http://www.af-klm.com/services/passenger/ProspectType-v1/xsd}ContextData" minOccurs="0"/>
 *         &lt;element name="telecoms" type="{http://www.af-klm.com/services/passenger/ProspectType-v1/xsd}Telecoms" minOccurs="0"/>
 *         &lt;element name="identificationData" type="{http://www.af-klm.com/services/passenger/ProspectType-v1/xsd}IdentificationData"/>
 *         &lt;element name="communicationPreferences" type="{http://www.af-klm.com/services/passenger/ProspectType-v1/xsd}CommunicationPreferences" minOccurs="0"/>
 *         &lt;element name="profileInformation" type="{http://www.af-klm.com/services/passenger/ProspectType-v1/xsd}ProfileInformation" minOccurs="0"/>
 *         &lt;element name="localizationData" type="{http://www.af-klm.com/services/passenger/ProspectType-v1/xsd}LocalizationData" minOccurs="0"/>
 *         &lt;element name="signature" type="{http://www.af-klm.com/services/passenger/ProspectType-v1/xsd}Signature"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CreateAProspectRequest", propOrder = {
    "contextData",
    "telecoms",
    "identificationData",
    "communicationPreferences",
    "profileInformation",
    "localizationData",
    "signature"
})
public class CreateAProspectRequest {

    protected ContextData contextData;
    protected Telecoms telecoms;
    @XmlElement(required = true)
    protected IdentificationData identificationData;
    protected CommunicationPreferences communicationPreferences;
    protected ProfileInformation profileInformation;
    protected LocalizationData localizationData;
    @XmlElement(required = true)
    protected Signature signature;

    /**
     * Gets the value of the contextData property.
     * 
     * @return
     *     possible object is
     *     {@link ContextData }
     *     
     */
    public ContextData getContextData() {
        return contextData;
    }

    /**
     * Sets the value of the contextData property.
     * 
     * @param value
     *     allowed object is
     *     {@link ContextData }
     *     
     */
    public void setContextData(ContextData value) {
        this.contextData = value;
    }

    /**
     * Gets the value of the telecoms property.
     * 
     * @return
     *     possible object is
     *     {@link Telecoms }
     *     
     */
    public Telecoms getTelecoms() {
        return telecoms;
    }

    /**
     * Sets the value of the telecoms property.
     * 
     * @param value
     *     allowed object is
     *     {@link Telecoms }
     *     
     */
    public void setTelecoms(Telecoms value) {
        this.telecoms = value;
    }

    /**
     * Gets the value of the identificationData property.
     * 
     * @return
     *     possible object is
     *     {@link IdentificationData }
     *     
     */
    public IdentificationData getIdentificationData() {
        return identificationData;
    }

    /**
     * Sets the value of the identificationData property.
     * 
     * @param value
     *     allowed object is
     *     {@link IdentificationData }
     *     
     */
    public void setIdentificationData(IdentificationData value) {
        this.identificationData = value;
    }

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

    /**
     * Gets the value of the profileInformation property.
     * 
     * @return
     *     possible object is
     *     {@link ProfileInformation }
     *     
     */
    public ProfileInformation getProfileInformation() {
        return profileInformation;
    }

    /**
     * Sets the value of the profileInformation property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProfileInformation }
     *     
     */
    public void setProfileInformation(ProfileInformation value) {
        this.profileInformation = value;
    }

    /**
     * Gets the value of the localizationData property.
     * 
     * @return
     *     possible object is
     *     {@link LocalizationData }
     *     
     */
    public LocalizationData getLocalizationData() {
        return localizationData;
    }

    /**
     * Sets the value of the localizationData property.
     * 
     * @param value
     *     allowed object is
     *     {@link LocalizationData }
     *     
     */
    public void setLocalizationData(LocalizationData value) {
        this.localizationData = value;
    }

    /**
     * Gets the value of the signature property.
     * 
     * @return
     *     possible object is
     *     {@link Signature }
     *     
     */
    public Signature getSignature() {
        return signature;
    }

    /**
     * Sets the value of the signature property.
     * 
     * @param value
     *     allowed object is
     *     {@link Signature }
     *     
     */
    public void setSignature(Signature value) {
        this.signature = value;
    }

}
