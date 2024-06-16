
package com.afklm.repind.ws.w000443.data.schema571954;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ContextData complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ContextData">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="loggedGin" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypeCustomerNumber" minOccurs="0"/>
 *         &lt;element name="reconciliationCin" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypeCustomerNumber" minOccurs="0"/>
 *         &lt;element name="context" type="{http://www.af-klm.com/services/passenger/ProspectDataType-v1/xsd}DTypeContext"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ContextData", propOrder = {
    "loggedGin",
    "reconciliationCin",
    "context"
})
public class ContextData {

    protected String loggedGin;
    protected String reconciliationCin;
    @XmlElement(required = true)
    protected String context;

    /**
     * Gets the value of the loggedGin property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLoggedGin() {
        return loggedGin;
    }

    /**
     * Sets the value of the loggedGin property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLoggedGin(String value) {
        this.loggedGin = value;
    }

    /**
     * Gets the value of the reconciliationCin property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReconciliationCin() {
        return reconciliationCin;
    }

    /**
     * Sets the value of the reconciliationCin property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReconciliationCin(String value) {
        this.reconciliationCin = value;
    }

    /**
     * Gets the value of the context property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContext() {
        return context;
    }

    /**
     * Sets the value of the context property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContext(String value) {
        this.context = value;
    }

}
