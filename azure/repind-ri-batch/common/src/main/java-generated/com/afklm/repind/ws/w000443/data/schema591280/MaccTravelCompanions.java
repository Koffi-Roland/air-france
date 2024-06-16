
package com.afklm.repind.ws.w000443.data.schema591280;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>Java class for MaccTravelCompanions complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="MaccTravelCompanions">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="travelCompanionList" type="{http://www.af-klm.com/services/passenger/MarketingData-v1/xsd}MaccTravelCompanion" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MaccTravelCompanions", propOrder = {
    "travelCompanionList"
})
public class MaccTravelCompanions {

    protected List<MaccTravelCompanion> travelCompanionList;

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
     * {@link MaccTravelCompanion }
     * 
     * 
     */
    public List<MaccTravelCompanion> getTravelCompanionList() {
        if (travelCompanionList == null) {
            travelCompanionList = new ArrayList<MaccTravelCompanion>();
        }
        return this.travelCompanionList;
    }

}
