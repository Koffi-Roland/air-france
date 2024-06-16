
package com.afklm.repind.ws.provideindividualdata.siccommontype.siccommontype;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Information complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Information">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="informationCode" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypeWarningCode" minOccurs="0"/>
 *         &lt;element name="informationDetails" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypeWarningDetails" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Information", propOrder = {
    "informationCode",
    "informationDetails"
})
public class Information {

    protected String informationCode;
    protected String informationDetails;

    /**
     * Gets the value of the informationCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInformationCode() {
        return informationCode;
    }

    /**
     * Sets the value of the informationCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInformationCode(String value) {
        this.informationCode = value;
    }

    /**
     * Gets the value of the informationDetails property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInformationDetails() {
        return informationDetails;
    }

    /**
     * Sets the value of the informationDetails property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInformationDetails(String value) {
        this.informationDetails = value;
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
