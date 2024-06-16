
package com.afklm.repind.ws.createorupdateindividualdata.sicindividutype;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for InternalIdentifier complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="InternalIdentifier">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="identifierKey" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypeKeyIdentifier" minOccurs="0"/>
 *         &lt;element name="identifierValue" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypeValueIdentifier" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "InternalIdentifier", propOrder = {
    "identifierKey",
    "identifierValue"
})
public class InternalIdentifier {

    protected String identifierKey;
    protected String identifierValue;

    /**
     * Gets the value of the identifierKey property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdentifierKey() {
        return identifierKey;
    }

    /**
     * Sets the value of the identifierKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdentifierKey(String value) {
        this.identifierKey = value;
    }

    /**
     * Gets the value of the identifierValue property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdentifierValue() {
        return identifierValue;
    }

    /**
     * Sets the value of the identifierValue property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdentifierValue(String value) {
        this.identifierValue = value;
    }

}
