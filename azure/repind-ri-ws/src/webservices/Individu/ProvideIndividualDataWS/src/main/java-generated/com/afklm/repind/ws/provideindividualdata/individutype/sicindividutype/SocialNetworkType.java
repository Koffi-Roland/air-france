
package com.afklm.repind.ws.provideindividualdata.individutype.sicindividutype;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>Java class for SocialNetworkType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SocialNetworkType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="socialNetworkId" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypeSocialNetworkId"/>
 *         &lt;element name="socialNetworkUsed" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypeLastSocialNetworkUsed" minOccurs="0"/>
 *         &lt;element name="socialNetworkIdUsed" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypeLastSocialNetworkId" minOccurs="0"/>
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
@XmlType(name = "SocialNetworkType", propOrder = {
    "socialNetworkId",
    "socialNetworkUsed",
    "socialNetworkIdUsed",
    "socialNetworkData"
})
public class SocialNetworkType {

    @XmlElement(required = true)
    protected String socialNetworkId;
    protected String socialNetworkUsed;
    protected String socialNetworkIdUsed;
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
