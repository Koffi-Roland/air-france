
package com.afklm.repind.ws.createorupdateindividualdata.sicindividutype;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PostalAddressProperties complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PostalAddressProperties">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="indicAdrNorm" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="version" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypeVersion" minOccurs="0"/>
 *         &lt;element name="mediumCode" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypeMediumCode" minOccurs="0"/>
 *         &lt;element name="mediumStatus" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypeMediumStatus" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PostalAddressProperties", propOrder = {
    "indicAdrNorm",
    "version",
    "mediumCode",
    "mediumStatus"
})
public class PostalAddressProperties {

    protected Boolean indicAdrNorm;
    protected String version;
    protected String mediumCode;
    protected String mediumStatus;

    /**
     * Gets the value of the indicAdrNorm property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIndicAdrNorm() {
        return indicAdrNorm;
    }

    /**
     * Sets the value of the indicAdrNorm property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIndicAdrNorm(Boolean value) {
        this.indicAdrNorm = value;
    }

    /**
     * Gets the value of the version property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVersion() {
        return version;
    }

    /**
     * Sets the value of the version property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVersion(String value) {
        this.version = value;
    }

    /**
     * Gets the value of the mediumCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMediumCode() {
        return mediumCode;
    }

    /**
     * Sets the value of the mediumCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMediumCode(String value) {
        this.mediumCode = value;
    }

    /**
     * Gets the value of the mediumStatus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMediumStatus() {
        return mediumStatus;
    }

    /**
     * Sets the value of the mediumStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMediumStatus(String value) {
        this.mediumStatus = value;
    }

}
