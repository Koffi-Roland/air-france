
package com.afklm.repind.ws.createorupdateindividualdata.sicindividutype;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SocialNetworkIdentifier complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SocialNetworkIdentifier">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="socialNetworkId" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypeSocialNetworkId"/>
 *         &lt;element name="socialNetworkUsed" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypeLastSocialNetworkUsed"/>
 *         &lt;element name="socialNetworkIdUsed" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypeLastSocialNetworkId"/>
 *         &lt;element name="usedBy" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypeUsedBy" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SocialNetworkIdentifier", propOrder = {
    "socialNetworkId",
    "socialNetworkUsed",
    "socialNetworkIdUsed",
    "usedBy"
})
public class SocialNetworkIdentifier {

    @XmlElement(required = true)
    protected String socialNetworkId;
    @XmlElement(required = true)
    protected String socialNetworkUsed;
    @XmlElement(required = true)
    protected String socialNetworkIdUsed;
    protected String usedBy;

    /**
     * Gets the value of the socialNetworkId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSocialNetworkId() {
        return socialNetworkId;
    }

    /**
     * Sets the value of the socialNetworkId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSocialNetworkId(String value) {
        this.socialNetworkId = value;
    }

    /**
     * Gets the value of the socialNetworkUsed property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSocialNetworkUsed() {
        return socialNetworkUsed;
    }

    /**
     * Sets the value of the socialNetworkUsed property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSocialNetworkUsed(String value) {
        this.socialNetworkUsed = value;
    }

    /**
     * Gets the value of the socialNetworkIdUsed property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSocialNetworkIdUsed() {
        return socialNetworkIdUsed;
    }

    /**
     * Sets the value of the socialNetworkIdUsed property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSocialNetworkIdUsed(String value) {
        this.socialNetworkIdUsed = value;
    }

    /**
     * Gets the value of the usedBy property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUsedBy() {
        return usedBy;
    }

    /**
     * Sets the value of the usedBy property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUsedBy(String value) {
        this.usedBy = value;
    }

}
