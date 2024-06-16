
package com.afklm.repind.ws.provideindividualdata.individutype.sicindividutype;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
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
 *         &lt;element name="socialNetworkDataKey" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypeSocialNetworkKey" minOccurs="0"/>
 *         &lt;element name="socialNetworkDataValue" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypeSocialNetworkValue" minOccurs="0"/>
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

    protected String socialNetworkDataKey;
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
