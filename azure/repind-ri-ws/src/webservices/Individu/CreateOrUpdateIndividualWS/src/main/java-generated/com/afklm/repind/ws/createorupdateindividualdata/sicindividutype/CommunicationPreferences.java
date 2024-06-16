
package com.afklm.repind.ws.createorupdateindividualdata.sicindividutype;

import com.afklm.repind.ws.createorupdateindividualdata.siccommontype.siccommontype.Signature;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


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
 *         &lt;element name="domain" type="{http://www.af-klm.com/services/passenger/ProspectDataType-v1/xsd}DTypeDomain" minOccurs="0"/>
 *         &lt;element name="communicationGroupeType" type="{http://www.af-klm.com/services/passenger/ProspectDataType-v1/xsd}DTypeCommunicationType" minOccurs="0"/>
 *         &lt;element name="communicationType" type="{http://www.af-klm.com/services/passenger/ProspectDataType-v1/xsd}DTypeCommunicationType" minOccurs="0"/>
 *         &lt;element name="optIn" type="{http://www.af-klm.com/services/passenger/ProspectDataType-v1/xsd}DTypeOptIn" minOccurs="0"/>
 *         &lt;element name="dateOfConsent" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="subscriptionChannel" type="{http://www.af-klm.com/services/passenger/ProspectDataType-v1/xsd}DTypeSubscriptionChannel" minOccurs="0"/>
 *         &lt;element name="optInPartner" type="{http://www.af-klm.com/services/passenger/ProspectDataType-v1/xsd}DTypeOptIn" minOccurs="0"/>
 *         &lt;element name="dateOfConsentPartner" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="dateOfEntry" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="media" type="{http://www.af-klm.com/services/passenger/SicIndividuType-v3/xsd}Media" minOccurs="0"/>
 *         &lt;element name="marketLanguage" type="{http://www.af-klm.com/services/passenger/SicIndividuType-v3/xsd}MarketLanguage" maxOccurs="10" minOccurs="0"/>
 *         &lt;element name="signature" type="{http://www.af-klm.com/services/passenger/SicCommonType-v1/xsd}Signature" maxOccurs="2" minOccurs="0"/>
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
    "marketLanguage",
    "signature"
})
public class CommunicationPreferences {

    protected String domain;
    protected String communicationGroupeType;
    protected String communicationType;
    protected String optIn;
    @XmlSchemaType(name = "dateTime")
    protected Date dateOfConsent;
    protected String subscriptionChannel;
    protected String optInPartner;
    @XmlSchemaType(name = "dateTime")
    protected Date dateOfConsentPartner;
    @XmlSchemaType(name = "dateTime")
    protected Date dateOfEntry;
    protected Media media;
    protected List<MarketLanguage> marketLanguage;
    protected List<Signature> signature;

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
     *     {@link Date }
     *     
     */
    public Date getDateOfConsent() {
        return dateOfConsent;
    }

    /**
     * Sets the value of the dateOfConsent property.
     * 
     * @param value
     *     allowed object is
     *     {@link Date }
     *     
     */
    public void setDateOfConsent(Date value) {
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
     *     {@link Date }
     *     
     */
    public Date getDateOfConsentPartner() {
        return dateOfConsentPartner;
    }

    /**
     * Sets the value of the dateOfConsentPartner property.
     * 
     * @param value
     *     allowed object is
     *     {@link Date }
     *     
     */
    public void setDateOfConsentPartner(Date value) {
        this.dateOfConsentPartner = value;
    }

    /**
     * Gets the value of the dateOfEntry property.
     * 
     * @return
     *     possible object is
     *     {@link Date }
     *     
     */
    public Date getDateOfEntry() {
        return dateOfEntry;
    }

    /**
     * Sets the value of the dateOfEntry property.
     * 
     * @param value
     *     allowed object is
     *     {@link Date }
     *     
     */
    public void setDateOfEntry(Date value) {
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
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the marketLanguage property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMarketLanguage().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link MarketLanguage }
     * 
     * 
     */
    public List<MarketLanguage> getMarketLanguage() {
        if (marketLanguage == null) {
            marketLanguage = new ArrayList<MarketLanguage>();
        }
        return this.marketLanguage;
    }

    /**
     * Gets the value of the signature property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the signature property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSignature().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Signature }
     * 
     * 
     */
    public List<Signature> getSignature() {
        if (signature == null) {
            signature = new ArrayList<Signature>();
        }
        return this.signature;
    }

}
