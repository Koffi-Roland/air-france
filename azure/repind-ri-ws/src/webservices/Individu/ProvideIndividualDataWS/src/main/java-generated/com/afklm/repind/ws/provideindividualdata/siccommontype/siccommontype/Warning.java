
package com.afklm.repind.ws.provideindividualdata.siccommontype.siccommontype;

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
 *         &lt;element name="warningLabel" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypeWarningCode" minOccurs="0"/>
 *         &lt;element name="warningDetail" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypeWarningDetails" minOccurs="0"/>
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
    "warningLabel",
    "warningDetail"
})
public class Warning {

    protected String warningLabel;
    protected String warningDetail;

    /**
     * Gets the value of the warningLabel property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWarningLabel() {
        return warningLabel;
    }

    /**
     * Sets the value of the warningLabel property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWarningLabel(String value) {
        this.warningLabel = value;
    }

    /**
     * Gets the value of the warningDetail property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWarningDetail() {
        return warningDetail;
    }

    /**
     * Sets the value of the warningDetail property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWarningDetail(String value) {
        this.warningDetail = value;
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
