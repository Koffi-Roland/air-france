
package com.afklm.repind.ws.createorupdateindividualdata.siccommontype.siccommontype;

import com.afklm.repind.ws.createorupdateindividualdata.siccommontype.siccommonenum.BusinessErrorTypeEnum;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for InternalBusinessError complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="InternalBusinessError">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.af-klm.com/services/passenger/SicCommonType-v1/xsd}BusinessErrorCommon">
 *       &lt;sequence>
 *         &lt;element name="errorType" type="{http://www.af-klm.com/services/passenger/SicCommonEnum-v1/xsd}BusinessErrorTypeEnum" minOccurs="0"/>
 *         &lt;element name="errorCode" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypeErrorCode" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "InternalBusinessError", propOrder = {
    "errorType",
    "errorCode"
})
public class InternalBusinessError
    extends BusinessErrorCommon
{

    protected BusinessErrorTypeEnum errorType;
    protected String errorCode;

    /**
     * Gets the value of the errorType property.
     * 
     * @return
     *     possible object is
     *     {@link BusinessErrorTypeEnum }
     *     
     */
    public BusinessErrorTypeEnum getErrorType() {
        return errorType;
    }

    /**
     * Sets the value of the errorType property.
     * 
     * @param value
     *     allowed object is
     *     {@link BusinessErrorTypeEnum }
     *     
     */
    public void setErrorType(BusinessErrorTypeEnum value) {
        this.errorType = value;
    }

    /**
     * Gets the value of the errorCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getErrorCode() {
        return errorCode;
    }

    /**
     * Sets the value of the errorCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setErrorCode(String value) {
        this.errorCode = value;
    }

}
