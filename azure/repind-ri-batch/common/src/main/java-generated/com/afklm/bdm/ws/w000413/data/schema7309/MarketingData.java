
package com.afklm.bdm.ws.w000413.data.schema7309;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for MarketingData complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="MarketingData">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="myAccountId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="GIN" type="{http://www.af-klm.com/services/bdm/types-v1/xsd}GinType"/>
 *         &lt;element name="personalInformation" type="{http://www.af-klm.com/services/marketingdata/common-v1/xsd}PersonalInformation" minOccurs="0"/>
 *         &lt;element name="travelPreferences" type="{http://www.af-klm.com/services/marketingdata/common-v1/xsd}TravelPreferences" minOccurs="0"/>
 *         &lt;element name="apisData" type="{http://www.af-klm.com/services/marketingdata/common-v1/xsd}ApisData" minOccurs="0"/>
 *         &lt;element name="travelCompanions" type="{http://www.af-klm.com/services/marketingdata/common-v1/xsd}TravelCompanions" minOccurs="0"/>
 *         &lt;element name="travelDocuments" type="{http://www.af-klm.com/services/marketingdata/common-v1/xsd}TravelDocuments" minOccurs="0"/>
 *         &lt;element name="emergencyContacts" type="{http://www.af-klm.com/services/marketingdata/common-v1/xsd}EmergencyContacts" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MarketingData", propOrder = {
    "myAccountId",
    "gin",
    "personalInformation",
    "travelPreferences",
    "apisData",
    "travelCompanions",
    "travelDocuments",
    "emergencyContacts"
})
public class MarketingData {

    protected String myAccountId;
    @XmlElement(name = "GIN", required = true)
    protected String gin;
    protected PersonalInformation personalInformation;
    protected TravelPreferences travelPreferences;
    protected ApisData apisData;
    protected TravelCompanions travelCompanions;
    protected TravelDocuments travelDocuments;
    protected EmergencyContacts emergencyContacts;

    /**
     * Gets the value of the myAccountId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMyAccountId() {
        return myAccountId;
    }

    /**
     * Sets the value of the myAccountId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMyAccountId(String value) {
        this.myAccountId = value;
    }

    /**
     * Gets the value of the gin property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGIN() {
        return gin;
    }

    /**
     * Sets the value of the gin property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGIN(String value) {
        this.gin = value;
    }

    /**
     * Gets the value of the personalInformation property.
     * 
     * @return
     *     possible object is
     *     {@link PersonalInformation }
     *     
     */
    public PersonalInformation getPersonalInformation() {
        return personalInformation;
    }

    /**
     * Sets the value of the personalInformation property.
     * 
     * @param value
     *     allowed object is
     *     {@link PersonalInformation }
     *     
     */
    public void setPersonalInformation(PersonalInformation value) {
        this.personalInformation = value;
    }

    /**
     * Gets the value of the travelPreferences property.
     * 
     * @return
     *     possible object is
     *     {@link TravelPreferences }
     *     
     */
    public TravelPreferences getTravelPreferences() {
        return travelPreferences;
    }

    /**
     * Sets the value of the travelPreferences property.
     * 
     * @param value
     *     allowed object is
     *     {@link TravelPreferences }
     *     
     */
    public void setTravelPreferences(TravelPreferences value) {
        this.travelPreferences = value;
    }

    /**
     * Gets the value of the apisData property.
     * 
     * @return
     *     possible object is
     *     {@link ApisData }
     *     
     */
    public ApisData getApisData() {
        return apisData;
    }

    /**
     * Sets the value of the apisData property.
     * 
     * @param value
     *     allowed object is
     *     {@link ApisData }
     *     
     */
    public void setApisData(ApisData value) {
        this.apisData = value;
    }

    /**
     * Gets the value of the travelCompanions property.
     * 
     * @return
     *     possible object is
     *     {@link TravelCompanions }
     *     
     */
    public TravelCompanions getTravelCompanions() {
        return travelCompanions;
    }

    /**
     * Sets the value of the travelCompanions property.
     * 
     * @param value
     *     allowed object is
     *     {@link TravelCompanions }
     *     
     */
    public void setTravelCompanions(TravelCompanions value) {
        this.travelCompanions = value;
    }

    /**
     * Gets the value of the travelDocuments property.
     * 
     * @return
     *     possible object is
     *     {@link TravelDocuments }
     *     
     */
    public TravelDocuments getTravelDocuments() {
        return travelDocuments;
    }

    /**
     * Sets the value of the travelDocuments property.
     * 
     * @param value
     *     allowed object is
     *     {@link TravelDocuments }
     *     
     */
    public void setTravelDocuments(TravelDocuments value) {
        this.travelDocuments = value;
    }

    /**
     * Gets the value of the emergencyContacts property.
     * 
     * @return
     *     possible object is
     *     {@link EmergencyContacts }
     *     
     */
    public EmergencyContacts getEmergencyContacts() {
        return emergencyContacts;
    }

    /**
     * Sets the value of the emergencyContacts property.
     * 
     * @param value
     *     allowed object is
     *     {@link EmergencyContacts }
     *     
     */
    public void setEmergencyContacts(EmergencyContacts value) {
        this.emergencyContacts = value;
    }

}
