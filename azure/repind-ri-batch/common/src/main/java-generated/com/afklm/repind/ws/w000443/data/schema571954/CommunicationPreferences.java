
package com.afklm.repind.ws.w000443.data.schema571954;

import javax.xml.bind.annotation.*;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for CommunicationPreferences complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CommunicationPreferences">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="domain" type="{http://www.af-klm.com/services/passenger/ProspectDataType-v1/xsd}DTypeDomain"/>
 *         &lt;element name="communicationGroupeType" type="{http://www.af-klm.com/services/passenger/ProspectDataType-v1/xsd}DTypeCommunicationType"/>
 *         &lt;element name="communicationType" type="{http://www.af-klm.com/services/passenger/ProspectDataType-v1/xsd}DTypeCommunicationType"/>
 *         &lt;element name="optIn" type="{http://www.af-klm.com/services/passenger/ProspectDataType-v1/xsd}DTypeOptIn"/>
 *         &lt;element name="dateOfConsent" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="subscriptionChannel" type="{http://www.af-klm.com/services/passenger/ProspectDataType-v1/xsd}DTypeSubscriptionChannel"/>
 *         &lt;element name="optInPartner" type="{http://www.af-klm.com/services/passenger/ProspectDataType-v1/xsd}DTypeOptIn" minOccurs="0"/>
 *         &lt;element name="dateOfConsentPartner" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="dateOfEntry" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="media" type="{http://www.af-klm.com/services/passenger/ProspectType-v1/xsd}Media" minOccurs="0"/>
 *         &lt;element name="marketLanguage" type="{http://www.af-klm.com/services/passenger/ProspectType-v1/xsd}MarketLanguage" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CommunicationPreferences", propOrder = {
    "domain",
    "communicationGroupeType",
    "communicationType",
    "optIn",
    "dateOfConsent",
    "subscriptionChannel",
    "optInPartner",
    "dateOfConsentPartner",
    "dateOfEntry",
    "media",
    "marketLanguage"
})
public class CommunicationPreferences {

    @XmlElement(required = true)
    protected String domain;
    @XmlElement(required = true)
    protected String communicationGroupeType;
    @XmlElement(required = true)
    protected String communicationType;
    @XmlElement(required = true)
    protected String optIn;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dateOfConsent;
    @XmlElement(required = true)
    protected String subscriptionChannel;
    protected String optInPartner;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dateOfConsentPartner;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dateOfEntry;
    protected Media media;
    protected MarketLanguage marketLanguage;

    /**
     * Gets the value of the domain property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDomain() {
        return domain;
    }

    /**
     * Sets the value of the domain property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDomain(String value) {
        this.domain = value;
    }

    /**
     * Gets the value of the communicationGroupeType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCommunicationGroupeType() {
        return communicationGroupeType;
    }

    /**
     * Sets the value of the communicationGroupeType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCommunicationGroupeType(String value) {
        this.communicationGroupeType = value;
    }

    /**
     * Gets the value of the communicationType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCommunicationType() {
        return communicationType;
    }

    /**
     * Sets the value of the communicationType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCommunicationType(String value) {
        this.communicationType = value;
    }

    /**
     * Gets the value of the optIn property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOptIn() {
        return optIn;
    }

    /**
     * Sets the value of the optIn property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOptIn(String value) {
        this.optIn = value;
    }

    /**
     * Gets the value of the dateOfConsent property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDateOfConsent() {
        return dateOfConsent;
    }

    /**
     * Sets the value of the dateOfConsent property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDateOfConsent(XMLGregorianCalendar value) {
        this.dateOfConsent = value;
    }

    /**
     * Gets the value of the subscriptionChannel property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSubscriptionChannel() {
        return subscriptionChannel;
    }

    /**
     * Sets the value of the subscriptionChannel property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSubscriptionChannel(String value) {
        this.subscriptionChannel = value;
    }

    /**
     * Gets the value of the optInPartner property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOptInPartner() {
        return optInPartner;
    }

    /**
     * Sets the value of the optInPartner property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOptInPartner(String value) {
        this.optInPartner = value;
    }

    /**
     * Gets the value of the dateOfConsentPartner property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDateOfConsentPartner() {
        return dateOfConsentPartner;
    }

    /**
     * Sets the value of the dateOfConsentPartner property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDateOfConsentPartner(XMLGregorianCalendar value) {
        this.dateOfConsentPartner = value;
    }

    /**
     * Gets the value of the dateOfEntry property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDateOfEntry() {
        return dateOfEntry;
    }

    /**
     * Sets the value of the dateOfEntry property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDateOfEntry(XMLGregorianCalendar value) {
        this.dateOfEntry = value;
    }

    /**
     * Gets the value of the media property.
     * 
     * @return
     *     possible object is
     *     {@link Media }
     *     
     */
    public Media getMedia() {
        return media;
    }

    /**
     * Sets the value of the media property.
     * 
     * @param value
     *     allowed object is
     *     {@link Media }
     *     
     */
    public void setMedia(Media value) {
        this.media = value;
    }

    /**
     * Gets the value of the marketLanguage property.
     * 
     * @return
     *     possible object is
     *     {@link MarketLanguage }
     *     
     */
    public MarketLanguage getMarketLanguage() {
        return marketLanguage;
    }

    /**
     * Sets the value of the marketLanguage property.
     * 
     * @param value
     *     allowed object is
     *     {@link MarketLanguage }
     *     
     */
    public void setMarketLanguage(MarketLanguage value) {
        this.marketLanguage = value;
    }

}
