
package com.afklm.repind.ws.provideindividualdata.individutype.sicindividutype;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DelegationActionData complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DelegationActionData">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="gin" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypeGin" minOccurs="0"/>
 *         &lt;element name="delegationAction" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypeDelegationAction" minOccurs="0"/>
 *         &lt;element name="delegationType" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypeDelegationType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DelegationActionData", propOrder = {
    "gin",
    "delegationAction",
    "delegationType"
})
public class DelegationActionData {

    protected String gin;
    protected String delegationAction;
    protected String delegationType;

    /**
     * Gets the value of the gin property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGin() {
        return gin;
    }

    /**
     * Sets the value of the gin property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGin(String value) {
        this.gin = value;
    }

    /**
     * Gets the value of the delegationAction property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDelegationAction() {
        return delegationAction;
    }

    /**
     * Sets the value of the delegationAction property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDelegationAction(String value) {
        this.delegationAction = value;
    }

    /**
     * Gets the value of the delegationType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDelegationType() {
        return delegationType;
    }

    /**
     * Sets the value of the delegationType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDelegationType(String value) {
        this.delegationType = value;
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
