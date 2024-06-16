
package com.afklm.repindpp.paymentpreference.delete.deletepaymentpreferencesschema;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DeletePaymentPreferencesResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DeletePaymentPreferencesResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="deletionStatus" type="{http://www.af-klm.com/services/passenger/RepindPPDataType-v1/xsd}DTypeDeletionStatus"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DeletePaymentPreferencesResponse", propOrder = {
    "deletionStatus"
})
public class DeletePaymentPreferencesResponse {

    @XmlElement(required = true)
    protected String deletionStatus;

    /**
     * Gets the value of the deletionStatus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDeletionStatus() {
        return deletionStatus;
    }

    /**
     * Sets the value of the deletionStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDeletionStatus(String value) {
        this.deletionStatus = value;
    }

}
