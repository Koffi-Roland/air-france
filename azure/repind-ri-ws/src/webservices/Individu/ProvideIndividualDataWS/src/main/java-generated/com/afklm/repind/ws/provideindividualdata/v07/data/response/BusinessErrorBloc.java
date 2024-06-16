
package com.afklm.repind.ws.provideindividualdata.v07.data.response;

import com.afklm.repind.ws.provideindividualdata.siccommontype.siccommontype.InternalBusinessError;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for BusinessErrorBloc complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="BusinessErrorBloc">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="businessError" type="{http://www.af-klm.com/services/passenger/response-v7/xsd}BusinessError"/>
 *         &lt;element name="internalBusinessError" type="{http://www.af-klm.com/services/passenger/SicCommonType-v1/xsd}InternalBusinessError" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BusinessErrorBloc", propOrder = {
    "businessError",
    "internalBusinessError"
})
public class BusinessErrorBloc {

    @XmlElement(required = true)
    protected BusinessError businessError;
    protected InternalBusinessError internalBusinessError;

    /**
     * Gets the value of the businessError property.
     * 
     * @return
     *     possible object is
     *     {@link BusinessError }
     *     
     */
    public BusinessError getBusinessError() {
        return businessError;
    }

    /**
     * Sets the value of the businessError property.
     * 
     * @param value
     *     allowed object is
     *     {@link BusinessError }
     *     
     */
    public void setBusinessError(BusinessError value) {
        this.businessError = value;
    }

    /**
     * Gets the value of the internalBusinessError property.
     * 
     * @return
     *     possible object is
     *     {@link InternalBusinessError }
     *     
     */
    public InternalBusinessError getInternalBusinessError() {
        return internalBusinessError;
    }

    /**
     * Sets the value of the internalBusinessError property.
     * 
     * @param value
     *     allowed object is
     *     {@link InternalBusinessError }
     *     
     */
    public void setInternalBusinessError(InternalBusinessError value) {
        this.internalBusinessError = value;
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
