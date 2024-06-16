
package com.afklm.repind.ws.w000443.data.schema591279;

import com.afklm.repind.ws.w000443.data.schema571954.MarketLanguage;

import javax.xml.bind.annotation.*;
import javax.xml.datatype.XMLGregorianCalendar;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>Java class for CommunicationPrefV3 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CommunicationPrefV3">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="mode" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypeOption" minOccurs="0"/>
 *         &lt;element name="domain" type="{http://www.af-klm.com/services/passenger/ProspectDataType-v1/xsd}DTypeDomain"/>
 *         &lt;element name="comGroupType" type="{http://www.af-klm.com/services/passenger/SicDataType-v1/xsd}DTypeComGroupType"/>
 *         &lt;element name="comType" type="{http://www.af-klm.com/services/passenger/SicDataType-v1/xsd}DTypeComType"/>
 *         &lt;element name="optin" type="{http://www.af-klm.com/services/passenger/ProspectDataType-v1/xsd}DTypeOptIn"/>
 *         &lt;element name="dateOfConsent" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="subscriptionChannel" type="{http://www.af-klm.com/services/passenger/ProspectDataType-v1/xsd}DTypeSubscriptionChannel"/>
 *         &lt;element name="optinPartners" type="{http://www.af-klm.com/services/passenger/ProspectDataType-v1/xsd}DTypeOptIn" minOccurs="0"/>
 *         &lt;element name="dateOfConsentPartners" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="dateOfEntry" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="media1" type="{http://www.af-klm.com/services/passenger/SicDataType-v1/xsd}DTypeMedia" minOccurs="0"/>
 *         &lt;element name="media2" type="{http://www.af-klm.com/services/passenger/SicDataType-v1/xsd}DTypeMedia" minOccurs="0"/>
 *         &lt;element name="media3" type="{http://www.af-klm.com/services/passenger/SicDataType-v1/xsd}DTypeMedia" minOccurs="0"/>
 *         &lt;element name="media4" type="{http://www.af-klm.com/services/passenger/SicDataType-v1/xsd}DTypeMedia" minOccurs="0"/>
 *         &lt;element name="media5" type="{http://www.af-klm.com/services/passenger/SicDataType-v1/xsd}DTypeMedia" minOccurs="0"/>
 *         &lt;element name="marketLanguage" type="{http://www.af-klm.com/services/passenger/ProspectType-v1/xsd}MarketLanguage" maxOccurs="10" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CommunicationPrefV3", propOrder = {
    "mode",
    "domain",
    "comGroupType",
    "comType",
    "optin",
    "dateOfConsent",
    "subscriptionChannel",
    "optinPartners",
    "dateOfConsentPartners",
    "dateOfEntry",
    "media1",
    "media2",
    "media3",
    "media4",
    "media5",
    "marketLanguage"
})
public class CommunicationPrefV3 {

    protected String mode;
    @XmlElement(required = true)
    protected String domain;
    @XmlElement(required = true)
    protected String comGroupType;
    @XmlElement(required = true)
    protected String comType;
    @XmlElement(required = true)
    protected String optin;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dateOfConsent;
    @XmlElement(required = true)
    protected String subscriptionChannel;
    protected String optinPartners;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dateOfConsentPartners;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dateOfEntry;
    protected String media1;
    protected String media2;
    protected String media3;
    protected String media4;
    protected String media5;
    protected List<MarketLanguage> marketLanguage;

    /**
     * Gets the value of the mode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMode() {
        return mode;
    }

    /**
     * Sets the value of the mode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMode(String value) {
        this.mode = value;
    }

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
     * Gets the value of the comGroupType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getComGroupType() {
        return comGroupType;
    }

    /**
     * Sets the value of the comGroupType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setComGroupType(String value) {
        this.comGroupType = value;
    }

    /**
     * Gets the value of the comType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getComType() {
        return comType;
    }

    /**
     * Sets the value of the comType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setComType(String value) {
        this.comType = value;
    }

    /**
     * Gets the value of the optin property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOptin() {
        return optin;
    }

    /**
     * Sets the value of the optin property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOptin(String value) {
        this.optin = value;
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
     * Gets the value of the optinPartners property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOptinPartners() {
        return optinPartners;
    }

    /**
     * Sets the value of the optinPartners property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOptinPartners(String value) {
        this.optinPartners = value;
    }

    /**
     * Gets the value of the dateOfConsentPartners property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDateOfConsentPartners() {
        return dateOfConsentPartners;
    }

    /**
     * Sets the value of the dateOfConsentPartners property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDateOfConsentPartners(XMLGregorianCalendar value) {
        this.dateOfConsentPartners = value;
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
     * Gets the value of the media1 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMedia1() {
        return media1;
    }

    /**
     * Sets the value of the media1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMedia1(String value) {
        this.media1 = value;
    }

    /**
     * Gets the value of the media2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMedia2() {
        return media2;
    }

    /**
     * Sets the value of the media2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMedia2(String value) {
        this.media2 = value;
    }

    /**
     * Gets the value of the media3 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMedia3() {
        return media3;
    }

    /**
     * Sets the value of the media3 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMedia3(String value) {
        this.media3 = value;
    }

    /**
     * Gets the value of the media4 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMedia4() {
        return media4;
    }

    /**
     * Sets the value of the media4 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMedia4(String value) {
        this.media4 = value;
    }

    /**
     * Gets the value of the media5 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMedia5() {
        return media5;
    }

    /**
     * Sets the value of the media5 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMedia5(String value) {
        this.media5 = value;
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

}
