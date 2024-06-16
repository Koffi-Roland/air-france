
package com.afklm.repind.ws.provideindividualdata.individutype.sicindividutype;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Warning complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Warning">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="warningCode" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypeWarningCode" minOccurs="0"/>
 *         &lt;element name="warningDetails" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypeWarningDetails" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Warning", propOrder = {
    "warningCode",
    "warningDetails"
})
public class Warning {

    protected String warningCode;
    protected String warningDetails;

    /**
     * Gets the value of the warningCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWarningCode() {
        return warningCode;
    }

    /**
     * Sets the value of the warningCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWarningCode(String value) {
        this.warningCode = value;
    }

    /**
     * Gets the value of the warningDetails property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWarningDetails() {
        return warningDetails;
    }

    /**
     * Sets the value of the warningDetails property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWarningDetails(String value) {
        this.warningDetails = value;
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
