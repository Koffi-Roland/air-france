
package com.afklm.repind.ws.w000443.data.schema591280;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>Java class for MaccTravelDocuments complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="MaccTravelDocuments">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="travelDocumentList" type="{http://www.af-klm.com/services/passenger/MarketingData-v1/xsd}MaccTravelDocument" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MaccTravelDocuments", propOrder = {
    "travelDocumentList"
})
public class MaccTravelDocuments {

    protected List<MaccTravelDocument> travelDocumentList;

    /**
     * Gets the value of the travelDocumentList property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the travelDocumentList property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTravelDocumentList().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link MaccTravelDocument }
     * 
     * 
     */
    public List<MaccTravelDocument> getTravelDocumentList() {
        if (travelDocumentList == null) {
            travelDocumentList = new ArrayList<MaccTravelDocument>();
        }
        return this.travelDocumentList;
    }

}
