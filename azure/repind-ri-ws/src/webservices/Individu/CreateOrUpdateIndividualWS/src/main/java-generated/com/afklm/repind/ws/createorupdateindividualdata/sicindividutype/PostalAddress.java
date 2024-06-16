
package com.afklm.repind.ws.createorupdateindividualdata.sicindividutype;

import com.afklm.repind.ws.createorupdateindividualdata.softcomputingtype.SoftComputingResponse;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>Java class for PostalAddress complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PostalAddress">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="postalAddressProperties" type="{http://www.af-klm.com/services/passenger/SicIndividuType-v3/xsd}PostalAddressProperties"/>
 *         &lt;element name="adrPostContent" type="{http://www.af-klm.com/services/passenger/SicIndividuType-v3/xsd}PostalAddressContent"/>
 *         &lt;element name="softComputingResponse" type="{http://www.af-klm.com/services/passenger/SoftComputingType-v1/xsd}SoftComputingResponse" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PostalAddress", propOrder = {
    "postalAddressProperties",
    "adrPostContent",
    "softComputingResponse"
})
public class PostalAddress {

    @XmlElement(required = true)
    protected PostalAddressProperties postalAddressProperties;
    @XmlElement(required = true)
    protected PostalAddressContent adrPostContent;
    protected List<SoftComputingResponse> softComputingResponse;

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
     * Gets the value of the adrPostContent property.
     * 
     * @return
     *     possible object is
     *     {@link PostalAddressContent }
     *     
     */
    public PostalAddressContent getAdrPostContent() {
        return adrPostContent;
    }

    /**
     * Sets the value of the adrPostContent property.
     * 
     * @param value
     *     allowed object is
     *     {@link PostalAddressContent }
     *     
     */
    public void setAdrPostContent(PostalAddressContent value) {
        this.adrPostContent = value;
    }

    /**
     * Gets the value of the softComputingResponse property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the softComputingResponse property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSoftComputingResponse().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SoftComputingResponse }
     * 
     * 
     */
    public List<SoftComputingResponse> getSoftComputingResponse() {
        if (softComputingResponse == null) {
            softComputingResponse = new ArrayList<SoftComputingResponse>();
        }
        return this.softComputingResponse;
    }

}
