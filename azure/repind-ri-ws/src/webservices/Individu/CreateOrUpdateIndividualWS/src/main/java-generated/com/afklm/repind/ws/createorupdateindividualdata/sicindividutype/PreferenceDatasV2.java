
package com.afklm.repind.ws.createorupdateindividualdata.sicindividutype;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>Java class for PreferenceDatasV2 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PreferenceDatasV2">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="preferenceData" type="{http://www.af-klm.com/services/passenger/SicIndividuType-v3/xsd}PreferenceDataV2" maxOccurs="50" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PreferenceDatasV2", propOrder = {
    "preferenceData"
})
public class PreferenceDatasV2 {

    protected List<PreferenceDataV2> preferenceData;

    /**
     * Gets the value of the preferenceData property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the preferenceData property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPreferenceData().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PreferenceDataV2 }
     * 
     * 
     */
    public List<PreferenceDataV2> getPreferenceData() {
        if (preferenceData == null) {
            preferenceData = new ArrayList<PreferenceDataV2>();
        }
        return this.preferenceData;
    }

}
