
package com.afklm.repind.ws.provideindividualdata.individutype.sicindividutype;

import com.afklm.repind.ws.provideindividualdata.siccommontype.siccommonenum.LanguageCodesEnum;
import com.afklm.repind.ws.provideindividualdata.siccommontype.siccommontype.Signature;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


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
 *         &lt;element name="market" type="{http://www.af-klm.com/services/passenger/ProspectDataType-v1/xsd}DTypeMarket" minOccurs="0"/>
 *         &lt;element name="language" type="{http://www.af-klm.com/services/passenger/SicCommonEnum-v1/xsd}LanguageCodesEnum" minOccurs="0"/>
 *         &lt;element name="optIn" type="{http://www.af-klm.com/services/passenger/ProspectDataType-v1/xsd}DTypeOptIn" minOccurs="0"/>
 *         &lt;element name="dateOfConsent" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="media" type="{http://www.af-klm.com/services/passenger/SicIndividuType-v3/xsd}Media" minOccurs="0"/>
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
@XmlType(name = "MarketLanguage", propOrder = {
    "market",
    "language",
    "optIn",
    "dateOfConsent",
    "media",
    "signature"
})
public class MarketLanguage {

    protected String market;
    protected LanguageCodesEnum language;
    protected String optIn;
    @XmlSchemaType(name = "dateTime")
    protected Date dateOfConsent;
    protected Media media;
    protected List<Signature> signature;

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

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

    @Override
    public boolean equals(Object that) {
        return EqualsBuilder.reflectionEquals(this, that);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

}
