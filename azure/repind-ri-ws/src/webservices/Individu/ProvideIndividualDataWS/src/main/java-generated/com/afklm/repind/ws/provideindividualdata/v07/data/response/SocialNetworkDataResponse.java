
package com.afklm.repind.ws.provideindividualdata.v07.data.response;

import com.afklm.repind.ws.provideindividualdata.individutype.sicindividutype.SocialNetworkTypeLight;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SocialNetworkDataResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SocialNetworkDataResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="socialNetworkTypeLight" type="{http://www.af-klm.com/services/passenger/SicIndividuType-v3/xsd}SocialNetworkTypeLight" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SocialNetworkDataResponse", propOrder = {
    "socialNetworkTypeLight"
})
public class SocialNetworkDataResponse {

    protected SocialNetworkTypeLight socialNetworkTypeLight;

    /**
     * Gets the value of the socialNetworkTypeLight property.
     * 
     * @return
     *     possible object is
     *     {@link SocialNetworkTypeLight }
     *     
     */
    public SocialNetworkTypeLight getSocialNetworkTypeLight() {
        return socialNetworkTypeLight;
    }

    /**
     * Sets the value of the socialNetworkTypeLight property.
     * 
     * @param value
     *     allowed object is
     *     {@link SocialNetworkTypeLight }
     *     
     */
    public void setSocialNetworkTypeLight(SocialNetworkTypeLight value) {
        this.socialNetworkTypeLight = value;
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