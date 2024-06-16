
package com.afklm.repind.ws.createorupdateindividualdata.v08.data.request;

import com.afklm.repind.ws.createorupdateindividualdata.sicindividutype.PostalAddressContent;
import com.afklm.repind.ws.createorupdateindividualdata.sicindividutype.PostalAddressProperties;
import com.afklm.repind.ws.createorupdateindividualdata.sicindividutype.UsageAddress;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PostalAddressRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PostalAddressRequest">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="postalAddressProperties" type="{http://www.af-klm.com/services/passenger/SicIndividuType-v3/xsd}PostalAddressProperties" minOccurs="0"/>
 *         &lt;element name="postalAddressContent" type="{http://www.af-klm.com/services/passenger/SicIndividuType-v3/xsd}PostalAddressContent" minOccurs="0"/>
 *         &lt;element name="usageAddress" type="{http://www.af-klm.com/services/passenger/SicIndividuType-v3/xsd}UsageAddress" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PostalAddressRequest", propOrder = {
    "postalAddressProperties",
    "postalAddressContent",
    "usageAddress"
})
public class PostalAddressRequest {

    protected PostalAddressProperties postalAddressProperties;
    protected PostalAddressContent postalAddressContent;
    protected UsageAddress usageAddress;

    /**
     * Gets the value of the postalAddressProperties property.
     * 
     * @return
     *     possible object is
     *     {@link PostalAddressProperties }
     *     
     */
    public PostalAddressProperties getPostalAddressProperties() {
        return postalAddressProperties;
    }

    /**
     * Sets the value of the postalAddressProperties property.
     * 
     * @param value
     *     allowed object is
     *     {@link PostalAddressProperties }
     *     
     */
    public void setPostalAddressProperties(PostalAddressProperties value) {
        this.postalAddressProperties = value;
    }

    /**
     * Gets the value of the postalAddressContent property.
     * 
     * @return
     *     possible object is
     *     {@link PostalAddressContent }
     *     
     */
    public PostalAddressContent getPostalAddressContent() {
        return postalAddressContent;
    }

    /**
     * Sets the value of the postalAddressContent property.
     * 
     * @param value
     *     allowed object is
     *     {@link PostalAddressContent }
     *     
     */
    public void setPostalAddressContent(PostalAddressContent value) {
        this.postalAddressContent = value;
    }

    /**
     * Gets the value of the usageAddress property.
     * 
     * @return
     *     possible object is
     *     {@link UsageAddress }
     *     
     */
    public UsageAddress getUsageAddress() {
        return usageAddress;
    }

    /**
     * Sets the value of the usageAddress property.
     * 
     * @param value
     *     allowed object is
     *     {@link UsageAddress }
     *     
     */
    public void setUsageAddress(UsageAddress value) {
        this.usageAddress = value;
    }

}
