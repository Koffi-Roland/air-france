
package com.afklm.repind.ws.w000443.data.schema587190;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SocialNetworkData complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SocialNetworkData">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="socialNetworkDataKey" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypeSocialNetworkKey"/>
 *         &lt;element name="socialNetworkDataValue" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypeSocialNetworkValue"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SocialNetworkData", propOrder = {
    "socialNetworkDataKey",
    "socialNetworkDataValue"
})
public class SocialNetworkData {

    @XmlElement(required = true)
    protected String socialNetworkDataKey;
    @XmlElement(required = true)
    protected String socialNetworkDataValue;

    /**
     * Gets the value of the socialNetworkDataKey property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSocialNetworkDataKey() {
        return socialNetworkDataKey;
    }

    /**
     * Sets the value of the socialNetworkDataKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSocialNetworkDataKey(String value) {
        this.socialNetworkDataKey = value;
    }

    /**
     * Gets the value of the socialNetworkDataValue property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSocialNetworkDataValue() {
        return socialNetworkDataValue;
    }

    /**
     * Sets the value of the socialNetworkDataValue property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSocialNetworkDataValue(String value) {
        this.socialNetworkDataValue = value;
    }

}
