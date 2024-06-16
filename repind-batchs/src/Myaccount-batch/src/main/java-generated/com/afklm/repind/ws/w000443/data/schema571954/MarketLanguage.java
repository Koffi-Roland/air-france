
package com.afklm.repind.ws.w000443.data.schema571954;

import com.afklm.repind.ws.w000443.data.schema576961.LanguageCodesEnum;

import javax.xml.bind.annotation.*;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for MarketLanguage complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="MarketLanguage">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="market" type="{http://www.af-klm.com/services/passenger/ProspectDataType-v1/xsd}DTypeMarket"/>
 *         &lt;element name="language" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}LanguageCodesEnum"/>
 *         &lt;element name="optIn" type="{http://www.af-klm.com/services/passenger/ProspectDataType-v1/xsd}DTypeOptIn"/>
 *         &lt;element name="dateOfConsent" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="media" type="{http://www.af-klm.com/services/passenger/ProspectType-v1/xsd}Media" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MarketLanguage", propOrder = {
    "market",
    "language",
    "optIn",
    "dateOfConsent",
    "media"
})
public class MarketLanguage {

    @XmlElement(required = true)
    protected String market;
    @XmlElement(required = true)
    protected LanguageCodesEnum language;
    @XmlElement(required = true)
    protected String optIn;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dateOfConsent;
    protected Media media;

    /**
     * Gets the value of the market property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMarket() {
        return market;
    }

    /**
     * Sets the value of the market property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMarket(String value) {
        this.market = value;
    }

    /**
     * Gets the value of the language property.
     * 
     * @return
     *     possible object is
     *     {@link LanguageCodesEnum }
     *     
     */
    public LanguageCodesEnum getLanguage() {
        return language;
    }

    /**
     * Sets the value of the language property.
     * 
     * @param value
     *     allowed object is
     *     {@link LanguageCodesEnum }
     *     
     */
    public void setLanguage(LanguageCodesEnum value) {
        this.language = value;
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

}
