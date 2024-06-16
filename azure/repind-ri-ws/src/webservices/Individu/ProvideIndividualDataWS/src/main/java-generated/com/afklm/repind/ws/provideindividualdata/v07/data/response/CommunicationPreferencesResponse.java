
package com.afklm.repind.ws.provideindividualdata.v07.data.response;

import com.afklm.repind.ws.provideindividualdata.individutype.sicindividutype.CommunicationPreferences;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CommunicationPreferencesResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CommunicationPreferencesResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="communicationPreferences" type="{http://www.af-klm.com/services/passenger/SicIndividuType-v3/xsd}CommunicationPreferences" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CommunicationPreferencesResponse", propOrder = {
    "communicationPreferences"
})
public class CommunicationPreferencesResponse {

    protected CommunicationPreferences communicationPreferences;

    /**
     * Gets the value of the communicationPreferences property.
     * 
     * @return
     *     possible object is
     *     {@link CommunicationPreferences }
     *     
     */
    public CommunicationPreferences getCommunicationPreferences() {
        return communicationPreferences;
    }

    /**
     * Sets the value of the communicationPreferences property.
     * 
     * @param value
     *     allowed object is
     *     {@link CommunicationPreferences }
     *     
     */
    public void setCommunicationPreferences(CommunicationPreferences value) {
        this.communicationPreferences = value;
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
