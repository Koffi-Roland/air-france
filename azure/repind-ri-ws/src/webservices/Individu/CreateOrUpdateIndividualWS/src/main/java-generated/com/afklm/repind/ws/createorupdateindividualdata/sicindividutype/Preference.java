
package com.afklm.repind.ws.createorupdateindividualdata.sicindividutype;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>Java class for Preference complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Preference">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="typePreference" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypePreferenceType" minOccurs="0"/>
 *         &lt;element name="preferenceData" type="{http://www.af-klm.com/services/passenger/SicIndividuType-v3/xsd}PreferenceData" maxOccurs="100" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Preference", propOrder = {
    "typePreference",
    "preferenceData"
})
public class Preference {

    protected String typePreference;
    protected List<PreferenceData> preferenceData;

    /**
     * Gets the value of the typePreference property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTypePreference() {
        return typePreference;
    }

    /**
     * Sets the value of the typePreference property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTypePreference(String value) {
        this.typePreference = value;
    }

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
     * {@link PreferenceData }
     * 
     * 
     */
    public List<PreferenceData> getPreferenceData() {
        if (preferenceData == null) {
            preferenceData = new ArrayList<PreferenceData>();
        }
        return this.preferenceData;
    }

}
