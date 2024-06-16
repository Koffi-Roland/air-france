
package com.afklm.repind.ws.createorupdateindividualdata.sicindividutype;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Corporate complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Corporate">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="corporateEnvironmentID" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypeCorporateEnvironmentID" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Corporate", propOrder = {
    "corporateEnvironmentID"
})
public class Corporate {

    protected String corporateEnvironmentID;

    /**
     * Gets the value of the corporateEnvironmentID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCorporateEnvironmentID() {
        return corporateEnvironmentID;
    }

    /**
     * Sets the value of the corporateEnvironmentID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCorporateEnvironmentID(String value) {
        this.corporateEnvironmentID = value;
    }

}
