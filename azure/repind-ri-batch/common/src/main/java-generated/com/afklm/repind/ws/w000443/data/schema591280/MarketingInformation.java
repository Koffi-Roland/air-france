
package com.afklm.repind.ws.w000443.data.schema591280;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for MarketingInformation complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="MarketingInformation">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="personalInformation" type="{http://www.af-klm.com/services/passenger/MarketingData-v1/xsd}MaccPersonalInformation" minOccurs="0"/>
 *         &lt;element name="travelPreferences" type="{http://www.af-klm.com/services/passenger/MarketingData-v1/xsd}MaccTravelPreferences" minOccurs="0"/>
 *         &lt;element name="apisData" type="{http://www.af-klm.com/services/passenger/MarketingData-v1/xsd}MaccApisData" minOccurs="0"/>
 *         &lt;element name="travelCompanions" type="{http://www.af-klm.com/services/passenger/MarketingData-v1/xsd}MaccTravelCompanions" minOccurs="0"/>
 *         &lt;element name="travelDocuments" type="{http://www.af-klm.com/services/passenger/MarketingData-v1/xsd}MaccTravelDocuments" minOccurs="0"/>
 *         &lt;element name="emergencyContacts" type="{http://www.af-klm.com/services/passenger/MarketingData-v1/xsd}MaccEmergencyContacts" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MarketingInformation", propOrder = {
    "personalInformation",
    "travelPreferences",
    "apisData",
    "travelCompanions",
    "travelDocuments",
    "emergencyContacts"
})
public class MarketingInformation {

    protected MaccPersonalInformation personalInformation;
    protected MaccTravelPreferences travelPreferences;
    protected MaccApisData apisData;
    protected MaccTravelCompanions travelCompanions;
    protected MaccTravelDocuments travelDocuments;
    protected MaccEmergencyContacts emergencyContacts;

    /**
     * Gets the value of the personalInformation property.
     * 
     * @return
     *     possible object is
     *     {@link MaccPersonalInformation }
     *     
     */
    public MaccPersonalInformation getPersonalInformation() {
        return personalInformation;
    }

    /**
     * Sets the value of the personalInformation property.
     * 
     * @param value
     *     allowed object is
     *     {@link MaccPersonalInformation }
     *     
     */
    public void setPersonalInformation(MaccPersonalInformation value) {
        this.personalInformation = value;
    }

    /**
     * Gets the value of the travelPreferences property.
     * 
     * @return
     *     possible object is
     *     {@link MaccTravelPreferences }
     *     
     */
    public MaccTravelPreferences getTravelPreferences() {
        return travelPreferences;
    }

    /**
     * Sets the value of the travelPreferences property.
     * 
     * @param value
     *     allowed object is
     *     {@link MaccTravelPreferences }
     *     
     */
    public void setTravelPreferences(MaccTravelPreferences value) {
        this.travelPreferences = value;
    }

    /**
     * Gets the value of the apisData property.
     * 
     * @return
     *     possible object is
     *     {@link MaccApisData }
     *     
     */
    public MaccApisData getApisData() {
        return apisData;
    }

    /**
     * Sets the value of the apisData property.
     * 
     * @param value
     *     allowed object is
     *     {@link MaccApisData }
     *     
     */
    public void setApisData(MaccApisData value) {
        this.apisData = value;
    }

    /**
     * Gets the value of the travelCompanions property.
     * 
     * @return
     *     possible object is
     *     {@link MaccTravelCompanions }
     *     
     */
    public MaccTravelCompanions getTravelCompanions() {
        return travelCompanions;
    }

    /**
     * Sets the value of the travelCompanions property.
     * 
     * @param value
     *     allowed object is
     *     {@link MaccTravelCompanions }
     *     
     */
    public void setTravelCompanions(MaccTravelCompanions value) {
        this.travelCompanions = value;
    }

    /**
     * Gets the value of the travelDocuments property.
     * 
     * @return
     *     possible object is
     *     {@link MaccTravelDocuments }
     *     
     */
    public MaccTravelDocuments getTravelDocuments() {
        return travelDocuments;
    }

    /**
     * Sets the value of the travelDocuments property.
     * 
     * @param value
     *     allowed object is
     *     {@link MaccTravelDocuments }
     *     
     */
    public void setTravelDocuments(MaccTravelDocuments value) {
        this.travelDocuments = value;
    }

    /**
     * Gets the value of the emergencyContacts property.
     * 
     * @return
     *     possible object is
     *     {@link MaccEmergencyContacts }
     *     
     */
    public MaccEmergencyContacts getEmergencyContacts() {
        return emergencyContacts;
    }

    /**
     * Sets the value of the emergencyContacts property.
     * 
     * @param value
     *     allowed object is
     *     {@link MaccEmergencyContacts }
     *     
     */
    public void setEmergencyContacts(MaccEmergencyContacts value) {
        this.emergencyContacts = value;
    }

}
