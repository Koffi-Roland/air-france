
package com.afklm.repindpp.paymentpreference.delete.deletepaymentpreferencesschema;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DeletePaymentPreferencesRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DeletePaymentPreferencesRequest">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="gin" type="{http://www.af-klm.com/services/passenger/RepindPPDataType-v1/xsd}DTypeGin"/>
 *         &lt;element name="signature" type="{http://www.af-klm.com/services/passenger/RepindPPDataType-v1/xsd}DTypeSignature"/>
 *         &lt;element name="signatureSite" type="{http://www.af-klm.com/services/passenger/RepindPPDataType-v1/xsd}DTypeSignatureSite"/>
 *         &lt;element name="ipAdress" type="{http://www.af-klm.com/services/passenger/RepindPPDataType-v1/xsd}DTypeIpAdresse"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DeletePaymentPreferencesRequest", propOrder = {
    "gin",
    "signature",
    "signatureSite",
    "ipAdress"
})
public class DeletePaymentPreferencesRequest {

    @XmlElement(required = true)
    protected String gin;
    @XmlElement(required = true)
    protected String signature;
    @XmlElement(required = true)
    protected String signatureSite;
    @XmlElement(required = true)
    protected String ipAdress;

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
     * Gets the value of the signature property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSignature() {
        return signature;
    }

    /**
     * Sets the value of the signature property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSignature(String value) {
        this.signature = value;
    }

    /**
     * Gets the value of the signatureSite property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSignatureSite() {
        return signatureSite;
    }

    /**
     * Sets the value of the signatureSite property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSignatureSite(String value) {
        this.signatureSite = value;
    }

    /**
     * Gets the value of the ipAdress property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIpAdress() {
        return ipAdress;
    }

    /**
     * Sets the value of the ipAdress property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIpAdress(String value) {
        this.ipAdress = value;
    }

}
