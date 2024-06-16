
package com.afklm.repind.ws.createorupdateindividualdata.sicindividutype;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for UsageAddress complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="UsageAddress">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="applicationCode" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypeApplicationCode" minOccurs="0"/>
 *         &lt;element name="usageNumber" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypeUsageNumber" minOccurs="0"/>
 *         &lt;element name="addressRoleCode" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypeAddressRoleCode" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "UsageAddress", propOrder = {
    "applicationCode",
    "usageNumber",
    "addressRoleCode"
})
public class UsageAddress {

    protected String applicationCode;
    protected String usageNumber;
    protected String addressRoleCode;

    /**
     * Gets the value of the applicationCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getApplicationCode() {
        return applicationCode;
    }

    /**
     * Sets the value of the applicationCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setApplicationCode(String value) {
        this.applicationCode = value;
    }

    /**
     * Gets the value of the usageNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUsageNumber() {
        return usageNumber;
    }

    /**
     * Sets the value of the usageNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUsageNumber(String value) {
        this.usageNumber = value;
    }

    /**
     * Gets the value of the addressRoleCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAddressRoleCode() {
        return addressRoleCode;
    }

    /**
     * Sets the value of the addressRoleCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAddressRoleCode(String value) {
        this.addressRoleCode = value;
    }

}
