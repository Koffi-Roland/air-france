
package com.afklm.repind.ws.provideindividualdata.individutype.sicindividutype;

import com.afklm.repind.ws.provideindividualdata.siccommontype.siccommontype.Signature;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>Java class for Delegator complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Delegator">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="signature" type="{http://www.af-klm.com/services/passenger/SicCommonType-v1/xsd}Signature" maxOccurs="2" minOccurs="0"/>
 *         &lt;element name="telecom" type="{http://www.af-klm.com/services/passenger/SicIndividuType-v3/xsd}Telecom" maxOccurs="2" minOccurs="0"/>
 *         &lt;element name="delegationIndividualData" type="{http://www.af-klm.com/services/passenger/SicIndividuType-v3/xsd}DelegationIndividualData" minOccurs="0"/>
 *         &lt;element name="delegationStatusData" type="{http://www.af-klm.com/services/passenger/SicIndividuType-v3/xsd}DelegationStatusData" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Delegator", propOrder = {
    "signature",
    "telecom",
    "delegationIndividualData",
    "delegationStatusData"
})
public class Delegator {

    protected List<Signature> signature;
    protected List<Telecom> telecom;
    protected DelegationIndividualData delegationIndividualData;
    protected DelegationStatusData delegationStatusData;

    /**
     * Gets the value of the signature property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the signature property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSignature().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Signature }
     * 
     * 
     */
    public List<Signature> getSignature() {
        if (signature == null) {
            signature = new ArrayList<Signature>();
        }
        return this.signature;
    }

    /**
     * Gets the value of the telecom property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the telecom property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTelecom().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Telecom }
     * 
     * 
     */
    public List<Telecom> getTelecom() {
        if (telecom == null) {
            telecom = new ArrayList<Telecom>();
        }
        return this.telecom;
    }

    /**
     * Gets the value of the delegationIndividualData property.
     * 
     * @return
     *     possible object is
     *     {@link DelegationIndividualData }
     *     
     */
    public DelegationIndividualData getDelegationIndividualData() {
        return delegationIndividualData;
    }

    /**
     * Sets the value of the delegationIndividualData property.
     * 
     * @param value
     *     allowed object is
     *     {@link DelegationIndividualData }
     *     
     */
    public void setDelegationIndividualData(DelegationIndividualData value) {
        this.delegationIndividualData = value;
    }

    /**
     * Gets the value of the delegationStatusData property.
     * 
     * @return
     *     possible object is
     *     {@link DelegationStatusData }
     *     
     */
    public DelegationStatusData getDelegationStatusData() {
        return delegationStatusData;
    }

    /**
     * Sets the value of the delegationStatusData property.
     * 
     * @param value
     *     allowed object is
     *     {@link DelegationStatusData }
     *     
     */
    public void setDelegationStatusData(DelegationStatusData value) {
        this.delegationStatusData = value;
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
