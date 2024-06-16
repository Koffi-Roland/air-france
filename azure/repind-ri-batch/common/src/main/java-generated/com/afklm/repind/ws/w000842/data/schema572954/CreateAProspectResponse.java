
package com.afklm.repind.ws.w000842.data.schema572954;

import com.afklm.repind.ws.w000842.data.schema571954.ReturnDetails;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CreateAProspectResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CreateAProspectResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="gin" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypeCustomerNumber"/>
 *         &lt;element name="returnDetails" type="{http://www.af-klm.com/services/passenger/ProspectType-v1/xsd}ReturnDetails" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CreateAProspectResponse", propOrder = {
    "gin",
    "returnDetails"
})
public class CreateAProspectResponse {

    @XmlElement(required = true)
    protected String gin;
    protected ReturnDetails returnDetails;

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
     * Gets the value of the returnDetails property.
     * 
     * @return
     *     possible object is
     *     {@link ReturnDetails }
     *     
     */
    public ReturnDetails getReturnDetails() {
        return returnDetails;
    }

    /**
     * Sets the value of the returnDetails property.
     * 
     * @param value
     *     allowed object is
     *     {@link ReturnDetails }
     *     
     */
    public void setReturnDetails(ReturnDetails value) {
        this.returnDetails = value;
    }

}
