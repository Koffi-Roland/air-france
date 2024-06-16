
package com.afklm.repind.ws.w000443.data.schema591279;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>Java class for UpdateMyAccountCustomerResponseV2 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="UpdateMyAccountCustomerResponseV2">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="success" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="postalAddressResponse" type="{http://www.af-klm.com/services/passenger/UpdateMyAccountCustomerType-v1/xsd}PostalAddressResponse" maxOccurs="2" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "UpdateMyAccountCustomerResponseV2", propOrder = {
    "success",
    "postalAddressResponse"
})
public class UpdateMyAccountCustomerResponseV2 {

    protected boolean success;
    protected List<PostalAddressResponse> postalAddressResponse;

    /**
     * Gets the value of the success property.
     * 
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     * Sets the value of the success property.
     * 
     */
    public void setSuccess(boolean value) {
        this.success = value;
    }

    /**
     * Gets the value of the postalAddressResponse property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the postalAddressResponse property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPostalAddressResponse().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PostalAddressResponse }
     * 
     * 
     */
    public List<PostalAddressResponse> getPostalAddressResponse() {
        if (postalAddressResponse == null) {
            postalAddressResponse = new ArrayList<PostalAddressResponse>();
        }
        return this.postalAddressResponse;
    }

}
