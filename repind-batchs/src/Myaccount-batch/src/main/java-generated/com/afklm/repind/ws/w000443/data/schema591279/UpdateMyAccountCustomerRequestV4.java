
package com.afklm.repind.ws.w000443.data.schema591279;

import com.afklm.repind.ws.w000443.data.schema587190.SignatureLight;
import com.afklm.repind.ws.w000443.data.schema588217.AccountStatusEnum;
import com.afklm.repind.ws.w000443.data.schema588217.NewletterMediaSendingEnum;
import com.afklm.repind.ws.w000443.data.schema591280.MarketingInformationV2;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>Java class for UpdateMyAccountCustomerRequestV4 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="UpdateMyAccountCustomerRequestV4">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="postaladdress" type="{http://www.af-klm.com/services/passenger/UpdateMyAccountCustomerType-v1/xsd}PostalAddress" maxOccurs="2" minOccurs="0"/>
 *         &lt;element name="telecom" type="{http://www.af-klm.com/services/passenger/UpdateMyAccountCustomerType-v1/xsd}Telecom" maxOccurs="4" minOccurs="0"/>
 *         &lt;element name="email" type="{http://www.af-klm.com/services/passenger/UpdateMyAccountCustomerType-v1/xsd}Email" maxOccurs="2" minOccurs="0"/>
 *         &lt;element name="individual" type="{http://www.af-klm.com/services/passenger/UpdateMyAccountCustomerType-v1/xsd}IndividualInformations" minOccurs="0"/>
 *         &lt;element name="signature" type="{http://www.af-klm.com/services/passenger/SicIndividuType-v1/xsd}SignatureLight"/>
 *         &lt;element name="clientNumber" type="{http://www.af-klm.com/services/passenger/SicDataType-v1/xsd}DTypeNumeroClient"/>
 *         &lt;element name="newsletterMediaSending" type="{http://www.af-klm.com/services/passenger/SicDataType-v1/xsd}NewletterMediaSendingEnum" minOccurs="0"/>
 *         &lt;element name="status" type="{http://www.af-klm.com/services/passenger/SicDataType-v1/xsd}AccountStatusEnum" minOccurs="0"/>
 *         &lt;element name="updateCommunicationPrefMode" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypeOption" minOccurs="0"/>
 *         &lt;element name="communicationPrefV3" type="{http://www.af-klm.com/services/passenger/UpdateMyAccountCustomerType-v1/xsd}CommunicationPrefV3" maxOccurs="100" minOccurs="0"/>
 *         &lt;element name="marketingInformationV2" type="{http://www.af-klm.com/services/passenger/MarketingData-v1/xsd}MarketingInformationV2" minOccurs="0"/>
 *         &lt;element name="prefilledNumbersList" type="{http://www.af-klm.com/services/passenger/UpdateMyAccountCustomerType-v1/xsd}PrefilledNumbers" maxOccurs="10" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "UpdateMyAccountCustomerRequestV4", propOrder = {
    "postaladdress",
    "telecom",
    "email",
    "individual",
    "signature",
    "clientNumber",
    "newsletterMediaSending",
    "status",
    "updateCommunicationPrefMode",
    "communicationPrefV3",
    "marketingInformationV2",
    "prefilledNumbersList"
})
public class UpdateMyAccountCustomerRequestV4 {

    protected List<PostalAddress> postaladdress;
    protected List<Telecom> telecom;
    protected List<Email> email;
    protected IndividualInformations individual;
    @XmlElement(required = true)
    protected SignatureLight signature;
    @XmlElement(required = true)
    protected String clientNumber;
    protected NewletterMediaSendingEnum newsletterMediaSending;
    protected AccountStatusEnum status;
    protected String updateCommunicationPrefMode;
    protected List<CommunicationPrefV3> communicationPrefV3;
    protected MarketingInformationV2 marketingInformationV2;
    protected List<PrefilledNumbers> prefilledNumbersList;

    /**
     * Gets the value of the postaladdress property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the postaladdress property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPostaladdress().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PostalAddress }
     * 
     * 
     */
    public List<PostalAddress> getPostaladdress() {
        if (postaladdress == null) {
            postaladdress = new ArrayList<PostalAddress>();
        }
        return this.postaladdress;
    }

    /**
     * Gets the value of the telecom property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the telecom property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTelecom().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Telecom }
     * 
     * 
     */
    public List<Telecom> getTelecom() {
        if (telecom == null) {
            telecom = new ArrayList<Telecom>();
        }
        return this.telecom;
    }

    /**
     * Gets the value of the email property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the email property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getEmail().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Email }
     * 
     * 
     */
    public List<Email> getEmail() {
        if (email == null) {
            email = new ArrayList<Email>();
        }
        return this.email;
    }

    /**
     * Gets the value of the individual property.
     * 
     * @return
     *     possible object is
     *     {@link IndividualInformations }
     *     
     */
    public IndividualInformations getIndividual() {
        return individual;
    }

    /**
     * Sets the value of the individual property.
     * 
     * @param value
     *     allowed object is
     *     {@link IndividualInformations }
     *     
     */
    public void setIndividual(IndividualInformations value) {
        this.individual = value;
    }

    /**
     * Gets the value of the signature property.
     * 
     * @return
     *     possible object is
     *     {@link SignatureLight }
     *     
     */
    public SignatureLight getSignature() {
        return signature;
    }

    /**
     * Sets the value of the signature property.
     * 
     * @param value
     *     allowed object is
     *     {@link SignatureLight }
     *     
     */
    public void setSignature(SignatureLight value) {
        this.signature = value;
    }

    /**
     * Gets the value of the clientNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getClientNumber() {
        return clientNumber;
    }

    /**
     * Sets the value of the clientNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setClientNumber(String value) {
        this.clientNumber = value;
    }

    /**
     * Gets the value of the newsletterMediaSending property.
     * 
     * @return
     *     possible object is
     *     {@link NewletterMediaSendingEnum }
     *     
     */
    public NewletterMediaSendingEnum getNewsletterMediaSending() {
        return newsletterMediaSending;
    }

    /**
     * Sets the value of the newsletterMediaSending property.
     * 
     * @param value
     *     allowed object is
     *     {@link NewletterMediaSendingEnum }
     *     
     */
    public void setNewsletterMediaSending(NewletterMediaSendingEnum value) {
        this.newsletterMediaSending = value;
    }

    /**
     * Gets the value of the status property.
     * 
     * @return
     *     possible object is
     *     {@link AccountStatusEnum }
     *     
     */
    public AccountStatusEnum getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     * 
     * @param value
     *     allowed object is
     *     {@link AccountStatusEnum }
     *     
     */
    public void setStatus(AccountStatusEnum value) {
        this.status = value;
    }

    /**
     * Gets the value of the updateCommunicationPrefMode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUpdateCommunicationPrefMode() {
        return updateCommunicationPrefMode;
    }

    /**
     * Sets the value of the updateCommunicationPrefMode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUpdateCommunicationPrefMode(String value) {
        this.updateCommunicationPrefMode = value;
    }

    /**
     * Gets the value of the communicationPrefV3 property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the communicationPrefV3 property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCommunicationPrefV3().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CommunicationPrefV3 }
     * 
     * 
     */
    public List<CommunicationPrefV3> getCommunicationPrefV3() {
        if (communicationPrefV3 == null) {
            communicationPrefV3 = new ArrayList<CommunicationPrefV3>();
        }
        return this.communicationPrefV3;
    }

    /**
     * Gets the value of the marketingInformationV2 property.
     * 
     * @return
     *     possible object is
     *     {@link MarketingInformationV2 }
     *     
     */
    public MarketingInformationV2 getMarketingInformationV2() {
        return marketingInformationV2;
    }

    /**
     * Sets the value of the marketingInformationV2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link MarketingInformationV2 }
     *     
     */
    public void setMarketingInformationV2(MarketingInformationV2 value) {
        this.marketingInformationV2 = value;
    }

    /**
     * Gets the value of the prefilledNumbersList property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the prefilledNumbersList property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPrefilledNumbersList().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PrefilledNumbers }
     * 
     * 
     */
    public List<PrefilledNumbers> getPrefilledNumbersList() {
        if (prefilledNumbersList == null) {
            prefilledNumbersList = new ArrayList<PrefilledNumbers>();
        }
        return this.prefilledNumbersList;
    }

}