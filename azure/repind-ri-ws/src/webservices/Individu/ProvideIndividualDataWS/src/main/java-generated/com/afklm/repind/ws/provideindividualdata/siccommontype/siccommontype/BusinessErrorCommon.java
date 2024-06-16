
package com.afklm.repind.ws.provideindividualdata.siccommontype.siccommontype;

import com.afklm.repind.ws.provideindividualdata.v07.data.response.BusinessError;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for BusinessErrorCommon complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="BusinessErrorCommon">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="errorLabel" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypeErrorLabel" minOccurs="0"/>
 *         &lt;element name="errorDetail" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypeErrorDetail" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BusinessErrorCommon", propOrder = {
    "errorLabel",
    "errorDetail"
})
@XmlSeeAlso({
    BusinessError.class,
    InternalBusinessError.class
})
public class BusinessErrorCommon {

    protected String errorLabel;
    protected String errorDetail;

    /**
     * Gets the value of the errorLabel property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getErrorLabel() {
        return errorLabel;
    }

    /**
     * Sets the value of the errorLabel property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setErrorLabel(String value) {
        this.errorLabel = value;
    }

    /**
     * Gets the value of the errorDetail property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getErrorDetail() {
        return errorDetail;
    }

    /**
     * Sets the value of the errorDetail property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setErrorDetail(String value) {
        this.errorDetail = value;
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
