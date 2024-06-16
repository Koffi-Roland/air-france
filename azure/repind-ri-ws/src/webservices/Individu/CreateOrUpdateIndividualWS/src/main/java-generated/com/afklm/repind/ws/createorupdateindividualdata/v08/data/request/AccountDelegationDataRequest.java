
package com.afklm.repind.ws.createorupdateindividualdata.v08.data.request;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>Java class for AccountDelegationDataRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AccountDelegationDataRequest">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="delegate" type="{http://www.af-klm.com/services/passenger/request-v8/xsd}Delegate" maxOccurs="100" minOccurs="0"/>
 *         &lt;element name="delegator" type="{http://www.af-klm.com/services/passenger/request-v8/xsd}Delegator" maxOccurs="100" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AccountDelegationDataRequest", propOrder = {
    "delegate",
    "delegator"
})
public class AccountDelegationDataRequest {

    protected List<Delegate> delegate;
    protected List<Delegator> delegator;

    /**
     * Gets the value of the delegate property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the delegate property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDelegate().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Delegate }
     * 
     * 
     */
    public List<Delegate> getDelegate() {
        if (delegate == null) {
            delegate = new ArrayList<Delegate>();
        }
        return this.delegate;
    }

    /**
     * Gets the value of the delegator property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the delegator property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDelegator().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Delegator }
     * 
     * 
     */
    public List<Delegator> getDelegator() {
        if (delegator == null) {
            delegator = new ArrayList<Delegator>();
        }
        return this.delegator;
    }

}
