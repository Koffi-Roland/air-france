
package com.afklm.repind.ws.createorupdateindividualdata.sicindividutype;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import java.util.Date;


/**
 * <p>Java class for SignatureV2 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SignatureV2">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="creationSignature" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypeSignature" minOccurs="0"/>
 *         &lt;element name="creationSite" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypeSignatureSite" minOccurs="0"/>
 *         &lt;element name="creationDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="modificationSignature" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypeSignature" minOccurs="0"/>
 *         &lt;element name="modificationSite" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypeSignatureSite" minOccurs="0"/>
 *         &lt;element name="modificationDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SignatureV2", propOrder = {
    "creationSignature",
    "creationSite",
    "creationDate",
    "modificationSignature",
    "modificationSite",
    "modificationDate"
})
public class SignatureV2 {

    protected String creationSignature;
    protected String creationSite;
    @XmlSchemaType(name = "dateTime")
    protected Date creationDate;
    protected String modificationSignature;
    protected String modificationSite;
    @XmlSchemaType(name = "dateTime")
    protected Date modificationDate;

    /**
     * Gets the value of the creationSignature property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCreationSignature() {
        return creationSignature;
    }

    /**
     * Sets the value of the creationSignature property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCreationSignature(String value) {
        this.creationSignature = value;
    }

    /**
     * Gets the value of the creationSite property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCreationSite() {
        return creationSite;
    }

    /**
     * Sets the value of the creationSite property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCreationSite(String value) {
        this.creationSite = value;
    }

    /**
     * Gets the value of the creationDate property.
     * 
     * @return
     *     possible object is
     *     {@link Date }
     *     
     */
    public Date getCreationDate() {
        return creationDate;
    }

    /**
     * Sets the value of the creationDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link Date }
     *     
     */
    public void setCreationDate(Date value) {
        this.creationDate = value;
    }

    /**
     * Gets the value of the modificationSignature property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getModificationSignature() {
        return modificationSignature;
    }

    /**
     * Sets the value of the modificationSignature property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setModificationSignature(String value) {
        this.modificationSignature = value;
    }

    /**
     * Gets the value of the modificationSite property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getModificationSite() {
        return modificationSite;
    }

    /**
     * Sets the value of the modificationSite property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setModificationSite(String value) {
        this.modificationSite = value;
    }

    /**
     * Gets the value of the modificationDate property.
     * 
     * @return
     *     possible object is
     *     {@link Date }
     *     
     */
    public Date getModificationDate() {
        return modificationDate;
    }

    /**
     * Sets the value of the modificationDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link Date }
     *     
     */
    public void setModificationDate(Date value) {
        this.modificationDate = value;
    }

}
