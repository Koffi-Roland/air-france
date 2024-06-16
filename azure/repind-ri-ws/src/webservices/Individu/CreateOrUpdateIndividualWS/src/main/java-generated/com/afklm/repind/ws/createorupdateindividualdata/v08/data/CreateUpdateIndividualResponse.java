
package com.afklm.repind.ws.createorupdateindividualdata.v08.data;

import com.afklm.repind.ws.createorupdateindividualdata.v08.data.response.InformationResponse;
import com.afklm.repind.ws.createorupdateindividualdata.v08.data.response.PostalAddressResponse;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>Java class for CreateUpdateIndividualResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CreateUpdateIndividualResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="gin" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypeCustomerNumber" minOccurs="0"/>
 *         &lt;element name="success" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="postalAddressResponse" type="{http://www.af-klm.com/services/passenger/response-v8/xsd}PostalAddressResponse" maxOccurs="2" minOccurs="0"/>
 *         &lt;element name="informationResponse" type="{http://www.af-klm.com/services/passenger/response-v8/xsd}InformationResponse" maxOccurs="100" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CreateUpdateIndividualResponse", propOrder = {
    "gin",
    "success",
    "postalAddressResponse",
    "informationResponse"
})
public class CreateUpdateIndividualResponse {

    protected String gin;
    protected Boolean success;
    protected List<PostalAddressResponse> postalAddressResponse;
    protected List<InformationResponse> informationResponse;

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
     * Gets the value of the success property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isSuccess() {
        return success;
    }

    /**
     * Sets the value of the success property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setSuccess(Boolean value) {
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

    /**
     * Gets the value of the informationResponse property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the informationResponse property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getInformationResponse().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link InformationResponse }
     * 
     * 
     */
    public List<InformationResponse> getInformationResponse() {
        if (informationResponse == null) {
            informationResponse = new ArrayList<InformationResponse>();
        }
        return this.informationResponse;
    }

}
