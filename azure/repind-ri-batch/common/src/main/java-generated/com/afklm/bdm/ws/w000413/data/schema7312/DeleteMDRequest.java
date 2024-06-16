
package com.afklm.bdm.ws.w000413.data.schema7312;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * delete all the marketing data of an individual in the BDM.
 * 
 * <p>Java class for DeleteMDRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DeleteMDRequest">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="GIN" type="{http://www.af-klm.com/services/bdm/types-v1/xsd}GinType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DeleteMDRequest", propOrder = {
    "gin"
})
public class DeleteMDRequest {

    @XmlElement(name = "GIN")
    protected String gin;

    /**
     * Gets the value of the gin property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGIN() {
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
    public void setGIN(String value) {
        this.gin = value;
    }

}
