
package com.afklm.bdm.ws.w000413.data.schema7309;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>Java class for EmergencyContacts complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="EmergencyContacts">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="emergencyContactList" type="{http://www.af-klm.com/services/marketingdata/common-v1/xsd}EmergencyContact" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EmergencyContacts", propOrder = {
    "emergencyContactList"
})
public class EmergencyContacts {

    protected List<EmergencyContact> emergencyContactList;

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
     * {@link EmergencyContact }
     * 
     * 
     */
    public List<EmergencyContact> getEmergencyContactList() {
        if (emergencyContactList == null) {
            emergencyContactList = new ArrayList<EmergencyContact>();
        }
        return this.emergencyContactList;
    }

}
