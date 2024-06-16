
package com.afklm.repind.ws.createorupdateindividualdata.v08.data.request;

import com.afklm.repind.ws.createorupdateindividualdata.sicindividutype.ExternalIdentifier;
import com.afklm.repind.ws.createorupdateindividualdata.sicindividutype.ExternalIdentifierData;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>Java class for ExternalIdentifierRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ExternalIdentifierRequest">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="externalIdentifier" type="{http://www.af-klm.com/services/passenger/SicIndividuType-v3/xsd}ExternalIdentifier" minOccurs="0"/>
 *         &lt;element name="externalIdentifierData" type="{http://www.af-klm.com/services/passenger/SicIndividuType-v3/xsd}ExternalIdentifierData" maxOccurs="100" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ExternalIdentifierRequest", propOrder = {
    "externalIdentifier",
    "externalIdentifierData"
})
public class ExternalIdentifierRequest {

    protected ExternalIdentifier externalIdentifier;
    protected List<ExternalIdentifierData> externalIdentifierData;

    /**
     * Gets the value of the externalIdentifier property.
     * 
     * @return
     *     possible object is
     *     {@link ExternalIdentifier }
     *     
     */
    public ExternalIdentifier getExternalIdentifier() {
        return externalIdentifier;
    }

    /**
     * Sets the value of the externalIdentifier property.
     * 
     * @param value
     *     allowed object is
     *     {@link ExternalIdentifier }
     *     
     */
    public void setExternalIdentifier(ExternalIdentifier value) {
        this.externalIdentifier = value;
    }

    /**
     * Gets the value of the externalIdentifierData property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the externalIdentifierData property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getExternalIdentifierData().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ExternalIdentifierData }
     * 
     * 
     */
    public List<ExternalIdentifierData> getExternalIdentifierData() {
        if (externalIdentifierData == null) {
            externalIdentifierData = new ArrayList<ExternalIdentifierData>();
        }
        return this.externalIdentifierData;
    }

}
