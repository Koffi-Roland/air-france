
package com.afklm.repind.ws.createorupdateindividualdata.v08.data.request;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


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
 *         &lt;element name="delegationData" type="{http://www.af-klm.com/services/passenger/request-v8/xsd}DelegationData" minOccurs="0"/>
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
    "delegationData"
})
public class Delegator {

    protected DelegationData delegationData;

    /**
     * Gets the value of the delegationData property.
     * 
     * @return
     *     possible object is
     *     {@link DelegationData }
     *     
     */
    public DelegationData getDelegationData() {
        return delegationData;
    }

    /**
     * Sets the value of the delegationData property.
     * 
     * @param value
     *     allowed object is
     *     {@link DelegationData }
     *     
     */
    public void setDelegationData(DelegationData value) {
        this.delegationData = value;
    }

}
