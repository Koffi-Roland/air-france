
package com.afklm.repind.ws.createorupdateindividualdata.sicindividutype;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>Java class for SocialNetworkTypeLight complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SocialNetworkTypeLight">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="socialNetworkId" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypeSocialNetworkId" minOccurs="0"/>
 *         &lt;element name="socialNetworkData" type="{http://www.af-klm.com/services/passenger/SicIndividuType-v3/xsd}SocialNetworkData" maxOccurs="255" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SocialNetworkTypeLight", propOrder = {
    "socialNetworkId",
    "socialNetworkData"
})
public class SocialNetworkTypeLight {

    protected String socialNetworkId;
    protected List<SocialNetworkData> socialNetworkData;

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
     * Gets the value of the socialNetworkData property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the socialNetworkData property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSocialNetworkData().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SocialNetworkData }
     * 
     * 
     */
    public List<SocialNetworkData> getSocialNetworkData() {
        if (socialNetworkData == null) {
            socialNetworkData = new ArrayList<SocialNetworkData>();
        }
        return this.socialNetworkData;
    }

}
