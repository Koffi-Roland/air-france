
package com.afklm.repind.ws.provideindividualdata.v07.data.response;

import com.afklm.repind.ws.provideindividualdata.siccommontype.siccommontype.BusinessErrorCommon;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for BusinessError complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="BusinessError">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.af-klm.com/services/passenger/SicCommonType-v1/xsd}BusinessErrorCommon">
 *       &lt;sequence>
 *         &lt;element name="errorCode" type="{http://www.af-klm.com/services/passenger/response-v7/xsd}BusinessErrorCodeEnum" minOccurs="0"/>
 *         &lt;element name="otherErrorCode" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypeErrorCode" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BusinessError", propOrder = {
    "errorCode",
    "otherErrorCode"
})
public class BusinessError
    extends BusinessErrorCommon
{

    protected BusinessErrorCodeEnum errorCode;
    protected String otherErrorCode;

    /**
     * Gets the value of the errorCode property.
     * 
     * @return
     *     possible object is
     *     {@link BusinessErrorCodeEnum }
     *     
     */
    public BusinessErrorCodeEnum getErrorCode() {
        return errorCode;
    }

    /**
     * Sets the value of the errorCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link BusinessErrorCodeEnum }
     *     
     */
    public void setErrorCode(BusinessErrorCodeEnum value) {
        this.errorCode = value;
    }

    /**
     * Gets the value of the otherErrorCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOtherErrorCode() {
        return otherErrorCode;
    }

    /**
     * Sets the value of the otherErrorCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOtherErrorCode(String value) {
        this.otherErrorCode = value;
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
