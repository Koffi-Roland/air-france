
package com.afklm.repind.ws.w000443.data.schema591280;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>Java class for MaccEmergencyContacts complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="MaccEmergencyContacts">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="emergencyContactList" type="{http://www.af-klm.com/services/passenger/MarketingData-v1/xsd}MaccEmergencyContact" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MaccEmergencyContacts", propOrder = {
    "emergencyContactList"
})
public class MaccEmergencyContacts {

    protected List<MaccEmergencyContact> emergencyContactList;

    /**
     * Gets the value of the emergencyContactList property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the emergencyContactList property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getEmergencyContactList().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link MaccEmergencyContact }
     * 
     * 
     */
    public List<MaccEmergencyContact> getEmergencyContactList() {
        if (emergencyContactList == null) {
            emergencyContactList = new ArrayList<MaccEmergencyContact>();
        }
        return this.emergencyContactList;
    }

}
