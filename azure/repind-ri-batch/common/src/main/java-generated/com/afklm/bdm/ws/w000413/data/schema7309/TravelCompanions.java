
package com.afklm.bdm.ws.w000413.data.schema7309;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>Java class for TravelCompanions complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TravelCompanions">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="travelCompanionList" type="{http://www.af-klm.com/services/marketingdata/common-v1/xsd}TravelCompanion" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TravelCompanions", propOrder = {
    "travelCompanionList"
})
public class TravelCompanions {

    protected List<TravelCompanion> travelCompanionList;

    /**
     * Gets the value of the travelCompanionList property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the travelCompanionList property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTravelCompanionList().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TravelCompanion }
     * 
     * 
     */
    public List<TravelCompanion> getTravelCompanionList() {
        if (travelCompanionList == null) {
            travelCompanionList = new ArrayList<TravelCompanion>();
        }
        return this.travelCompanionList;
    }

}
