
package com.afklm.repind.ws.createorupdateindividualdata.siccommontype.siccommontype;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import java.util.Date;


/**
 * <p>Java class for Signature complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Signature">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="signatureType" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypeSignatureType" minOccurs="0"/>
 *         &lt;element name="signatureSite" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypeSignatureSite" minOccurs="0"/>
 *         &lt;element name="signature" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypeSignature" minOccurs="0"/>
 *         &lt;element name="date" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Signature", propOrder = {
    "signatureType",
    "signatureSite",
    "signature",
    "date"
})
public class Signature {

    protected String signatureType;
    protected String signatureSite;
    protected String signature;
    @XmlSchemaType(name = "dateTime")
    protected Date date;

    /**
     * Gets the value of the signatureType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSignatureType() {
        return signatureType;
    }

    /**
     * Sets the value of the signatureType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSignatureType(String value) {
        this.signatureType = value;
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
     * Gets the value of the date property.
     * 
     * @return
     *     possible object is
     *     {@link Date }
     *     
     */
    public Date getDate() {
        return date;
    }

    /**
     * Sets the value of the date property.
     * 
     * @param value
     *     allowed object is
     *     {@link Date }
     *     
     */
    public void setDate(Date value) {
        this.date = value;
    }

}
