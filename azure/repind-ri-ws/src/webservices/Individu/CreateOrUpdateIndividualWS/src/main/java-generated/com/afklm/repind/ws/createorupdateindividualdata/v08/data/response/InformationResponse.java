
package com.afklm.repind.ws.createorupdateindividualdata.v08.data.response;

import com.afklm.repind.ws.createorupdateindividualdata.siccommontype.siccommontype.Information;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for InformationResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="InformationResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="information" type="{http://www.af-klm.com/services/passenger/SicCommonType-v1/xsd}Information" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "InformationResponse", propOrder = {
    "information"
})
public class InformationResponse {

    protected Information information;

    /**
     * Gets the value of the information property.
     * 
     * @return
     *     possible object is
     *     {@link Information }
     *     
     */
    public Information getInformation() {
        return information;
    }

    /**
     * Sets the value of the information property.
     * 
     * @param value
     *     allowed object is
     *     {@link Information }
     *     
     */
    public void setInformation(Information value) {
        this.information = value;
    }

}
