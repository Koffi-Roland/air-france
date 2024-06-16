
package com.afklm.repind.ws.w000443.data.schema591280;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>Java class for MarketingInformationV2 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="MarketingInformationV2">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="errorCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="apisDataV2" type="{http://www.af-klm.com/services/passenger/MarketingData-v1/xsd}MaccApisDataV2" minOccurs="0"/>
 *         &lt;element name="personalInformationV2" type="{http://www.af-klm.com/services/passenger/MarketingData-v1/xsd}MaccPersonalInformationV2" minOccurs="0"/>
 *         &lt;element name="travelCompanionV2List" type="{http://www.af-klm.com/services/passenger/MarketingData-v1/xsd}MaccTravelCompanionV2" maxOccurs="8" minOccurs="0"/>
 *         &lt;element name="travelPreferencesV2" type="{http://www.af-klm.com/services/passenger/MarketingData-v1/xsd}MaccTravelPreferencesV2" minOccurs="0"/>
 *         &lt;element name="handicap" type="{http://www.af-klm.com/services/passenger/MarketingData-v1/xsd}MaccHandicap" minOccurs="0"/>
 *         &lt;element name="emergencyContactList" type="{http://www.af-klm.com/services/passenger/MarketingData-v1/xsd}MaccEmergencyContact" maxOccurs="5" minOccurs="0"/>
 *         &lt;element name="travelDocumentList" type="{http://www.af-klm.com/services/passenger/MarketingData-v1/xsd}MaccTravelDocument" maxOccurs="5" minOccurs="0"/>
 *         &lt;element name="tutorUMList" type="{http://www.af-klm.com/services/passenger/MarketingData-v1/xsd}MaccTutorUM" maxOccurs="2" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MarketingInformationV2", propOrder = {
    "errorCode",
    "apisDataV2",
    "personalInformationV2",
    "travelCompanionV2List",
    "travelPreferencesV2",
    "handicap",
    "emergencyContactList",
    "travelDocumentList",
    "tutorUMList"
})
public class MarketingInformationV2 {

    protected String errorCode;
    protected MaccApisDataV2 apisDataV2;
    protected MaccPersonalInformationV2 personalInformationV2;
    protected List<MaccTravelCompanionV2> travelCompanionV2List;
    protected MaccTravelPreferencesV2 travelPreferencesV2;
    protected MaccHandicap handicap;
    protected List<MaccEmergencyContact> emergencyContactList;
    protected List<MaccTravelDocument> travelDocumentList;
    protected List<MaccTutorUM> tutorUMList;

    /**
     * Gets the value of the errorCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getErrorCode() {
        return errorCode;
    }

    /**
     * Sets the value of the errorCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setErrorCode(String value) {
        this.errorCode = value;
    }

    /**
     * Gets the value of the apisDataV2 property.
     * 
     * @return
     *     possible object is
     *     {@link MaccApisDataV2 }
     *     
     */
    public MaccApisDataV2 getApisDataV2() {
        return apisDataV2;
    }

    /**
     * Sets the value of the apisDataV2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link MaccApisDataV2 }
     *     
     */
    public void setApisDataV2(MaccApisDataV2 value) {
        this.apisDataV2 = value;
    }

    /**
     * Gets the value of the personalInformationV2 property.
     * 
     * @return
     *     possible object is
     *     {@link MaccPersonalInformationV2 }
     *     
     */
    public MaccPersonalInformationV2 getPersonalInformationV2() {
        return personalInformationV2;
    }

    /**
     * Sets the value of the personalInformationV2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link MaccPersonalInformationV2 }
     *     
     */
    public void setPersonalInformationV2(MaccPersonalInformationV2 value) {
        this.personalInformationV2 = value;
    }

    /**
     * Gets the value of the travelCompanionV2List property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the travelCompanionV2List property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTravelCompanionV2List().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link MaccTravelCompanionV2 }
     * 
     * 
     */
    public List<MaccTravelCompanionV2> getTravelCompanionV2List() {
        if (travelCompanionV2List == null) {
            travelCompanionV2List = new ArrayList<MaccTravelCompanionV2>();
        }
        return this.travelCompanionV2List;
    }

    /**
     * Gets the value of the travelPreferencesV2 property.
     * 
     * @return
     *     possible object is
     *     {@link MaccTravelPreferencesV2 }
     *     
     */
    public MaccTravelPreferencesV2 getTravelPreferencesV2() {
        return travelPreferencesV2;
    }

    /**
     * Sets the value of the travelPreferencesV2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link MaccTravelPreferencesV2 }
     *     
     */
    public void setTravelPreferencesV2(MaccTravelPreferencesV2 value) {
        this.travelPreferencesV2 = value;
    }

    /**
     * Gets the value of the handicap property.
     * 
     * @return
     *     possible object is
     *     {@link MaccHandicap }
     *     
     */
    public MaccHandicap getHandicap() {
        return handicap;
    }

    /**
     * Sets the value of the handicap property.
     * 
     * @param value
     *     allowed object is
     *     {@link MaccHandicap }
     *     
     */
    public void setHandicap(MaccHandicap value) {
        this.handicap = value;
    }

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

    /**
     * Gets the value of the tutorUMList property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the tutorUMList property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTutorUMList().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link MaccTutorUM }
     * 
     * 
     */
    public List<MaccTutorUM> getTutorUMList() {
        if (tutorUMList == null) {
            tutorUMList = new ArrayList<MaccTutorUM>();
        }
        return this.tutorUMList;
    }

}
