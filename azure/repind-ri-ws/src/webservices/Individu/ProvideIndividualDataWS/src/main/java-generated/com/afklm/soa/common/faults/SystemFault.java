
package com.afklm.soa.common.faults;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ErrorCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="OriginatingError" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ErrorTrace" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="FaultDescription" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "errorCode",
    "originatingError",
    "errorTrace",
    "faultDescription"
})
@XmlRootElement(name = "SystemFault")
public class SystemFault {

    @XmlElement(name = "ErrorCode", required = true)
    protected String errorCode;
    @XmlElement(name = "OriginatingError")
    protected String originatingError;
    @XmlElement(name = "ErrorTrace")
    protected List<String> errorTrace;
    @XmlElement(name = "FaultDescription", required = true)
    protected String faultDescription;

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

    /**
     * Gets the value of the originatingError property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOriginatingError() {
        return originatingError;
    }

    /**
     * Sets the value of the originatingError property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOriginatingError(String value) {
        this.originatingError = value;
    }

    /**
     * Gets the value of the errorTrace property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the errorTrace property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getErrorTrace().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getErrorTrace() {
        if (errorTrace == null) {
            errorTrace = new ArrayList<String>();
        }
        return this.errorTrace;
    }

    /**
     * Gets the value of the faultDescription property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFaultDescription() {
        return faultDescription;
    }

    /**
     * Sets the value of the faultDescription property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFaultDescription(String value) {
        this.faultDescription = value;
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
