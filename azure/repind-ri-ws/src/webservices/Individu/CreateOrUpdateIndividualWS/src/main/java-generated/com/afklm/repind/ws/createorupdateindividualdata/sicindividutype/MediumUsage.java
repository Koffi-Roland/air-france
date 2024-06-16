
package com.afklm.repind.ws.createorupdateindividualdata.sicindividutype;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>Java class for MediumUsage complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="MediumUsage">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="workCode" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypeCodeAppliMetier" minOccurs="0"/>
 *         &lt;element name="usageNumber" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypeNumeroUsage" minOccurs="0"/>
 *         &lt;element name="addressRole" type="{http://www.af-klm.com/services/passenger/SicIndividuType-v3/xsd}AddressRole" maxOccurs="5" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MediumUsage", propOrder = {
    "workCode",
    "usageNumber",
    "addressRole"
})
public class MediumUsage {

    protected String workCode;
    protected String usageNumber;
    protected List<AddressRole> addressRole;

    /**
     * Gets the value of the workCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWorkCode() {
        return workCode;
    }

    /**
     * Sets the value of the workCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWorkCode(String value) {
        this.workCode = value;
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
     * Gets the value of the addressRole property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the addressRole property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAddressRole().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link AddressRole }
     * 
     * 
     */
    public List<AddressRole> getAddressRole() {
        if (addressRole == null) {
            addressRole = new ArrayList<AddressRole>();
        }
        return this.addressRole;
    }

}
