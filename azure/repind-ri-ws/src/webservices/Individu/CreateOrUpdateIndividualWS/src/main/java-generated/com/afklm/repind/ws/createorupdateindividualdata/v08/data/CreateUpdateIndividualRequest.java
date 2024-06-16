
package com.afklm.repind.ws.createorupdateindividualdata.v08.data;

import com.afklm.repind.ws.createorupdateindividualdata.siccommontype.siccommontype.RequestorV2;
import com.afklm.repind.ws.createorupdateindividualdata.v08.data.request.*;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>Java class for CreateUpdateIndividualRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CreateUpdateIndividualRequest">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="newsletterMediaSending" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypeNewsletterMediaSending" minOccurs="0"/>
 *         &lt;element name="status" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypeStatus" minOccurs="0"/>
 *         &lt;element name="updateCommunicationPrefMode" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypeUpdateMode" minOccurs="0"/>
 *         &lt;element name="updatePrefilledNumbersMode" type="{http://www.af-klm.com/services/passenger/SicCommonDataType-v1/xsd}DTypeUpdateMode" minOccurs="0"/>
 *         &lt;element name="process" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="requestor" type="{http://www.af-klm.com/services/passenger/SicCommonType-v1/xsd}RequestorV2"/>
 *         &lt;element name="emailRequest" type="{http://www.af-klm.com/services/passenger/request-v8/xsd}EmailRequest" maxOccurs="2" minOccurs="0"/>
 *         &lt;element name="telecomRequest" type="{http://www.af-klm.com/services/passenger/request-v8/xsd}TelecomRequest" maxOccurs="6" minOccurs="0"/>
 *         &lt;element name="postalAddressRequest" type="{http://www.af-klm.com/services/passenger/request-v8/xsd}PostalAddressRequest" maxOccurs="2" minOccurs="0"/>
 *         &lt;element name="individualRequest" type="{http://www.af-klm.com/services/passenger/request-v8/xsd}IndividualRequest" minOccurs="0"/>
 *         &lt;element name="externalIdentifierRequest" type="{http://www.af-klm.com/services/passenger/request-v8/xsd}ExternalIdentifierRequest" maxOccurs="100" minOccurs="0"/>
 *         &lt;element name="comunicationPreferencesRequest" type="{http://www.af-klm.com/services/passenger/request-v8/xsd}ComunicationPreferencesRequest" maxOccurs="100" minOccurs="0"/>
 *         &lt;element name="prefilledNumbersRequest" type="{http://www.af-klm.com/services/passenger/request-v8/xsd}PrefilledNumbersRequest" maxOccurs="100" minOccurs="0"/>
 *         &lt;element name="accountDelegationDataRequest" type="{http://www.af-klm.com/services/passenger/request-v8/xsd}AccountDelegationDataRequest" minOccurs="0"/>
 *         &lt;element name="alertRequest" type="{http://www.af-klm.com/services/passenger/request-v8/xsd}AlertRequest" minOccurs="0"/>
 *         &lt;element name="preferenceRequest" type="{http://www.af-klm.com/services/passenger/request-v8/xsd}PreferenceRequest" minOccurs="0"/>
 *         &lt;element name="utfRequest" type="{http://www.af-klm.com/services/passenger/request-v8/xsd}UtfRequest" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CreateUpdateIndividualRequest", propOrder = {
    "newsletterMediaSending",
    "status",
    "updateCommunicationPrefMode",
    "updatePrefilledNumbersMode",
    "process",
    "requestor",
    "emailRequest",
    "telecomRequest",
    "postalAddressRequest",
    "individualRequest",
    "externalIdentifierRequest",
    "comunicationPreferencesRequest",
    "prefilledNumbersRequest",
    "accountDelegationDataRequest",
    "alertRequest",
    "preferenceRequest",
    "utfRequest"
})
public class CreateUpdateIndividualRequest {

    protected String newsletterMediaSending;
    protected String status;
    protected String updateCommunicationPrefMode;
    protected String updatePrefilledNumbersMode;
    protected String process;
    @XmlElement(required = true)
    protected RequestorV2 requestor;
    protected List<EmailRequest> emailRequest;
    protected List<TelecomRequest> telecomRequest;
    protected List<PostalAddressRequest> postalAddressRequest;
    protected IndividualRequest individualRequest;
    protected List<ExternalIdentifierRequest> externalIdentifierRequest;
    protected List<ComunicationPreferencesRequest> comunicationPreferencesRequest;
    protected List<PrefilledNumbersRequest> prefilledNumbersRequest;
    protected AccountDelegationDataRequest accountDelegationDataRequest;
    protected AlertRequest alertRequest;
    protected PreferenceRequest preferenceRequest;
    protected UtfRequest utfRequest;

    /**
     * Gets the value of the newsletterMediaSending property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNewsletterMediaSending() {
        return newsletterMediaSending;
    }

    /**
     * Sets the value of the newsletterMediaSending property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNewsletterMediaSending(String value) {
        this.newsletterMediaSending = value;
    }

    /**
     * Gets the value of the status property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatus(String value) {
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
     * Gets the value of the updatePrefilledNumbersMode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUpdatePrefilledNumbersMode() {
        return updatePrefilledNumbersMode;
    }

    /**
     * Sets the value of the updatePrefilledNumbersMode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUpdatePrefilledNumbersMode(String value) {
        this.updatePrefilledNumbersMode = value;
    }

    /**
     * Gets the value of the process property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProcess() {
        return process;
    }

    /**
     * Sets the value of the process property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProcess(String value) {
        this.process = value;
    }

    /**
     * Gets the value of the requestor property.
     * 
     * @return
     *     possible object is
     *     {@link RequestorV2 }
     *     
     */
    public RequestorV2 getRequestor() {
        return requestor;
    }

    /**
     * Sets the value of the requestor property.
     * 
     * @param value
     *     allowed object is
     *     {@link RequestorV2 }
     *     
     */
    public void setRequestor(RequestorV2 value) {
        this.requestor = value;
    }

    /**
     * Gets the value of the emailRequest property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the emailRequest property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getEmailRequest().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link EmailRequest }
     * 
     * 
     */
    public List<EmailRequest> getEmailRequest() {
        if (emailRequest == null) {
            emailRequest = new ArrayList<EmailRequest>();
        }
        return this.emailRequest;
    }

    /**
     * Gets the value of the telecomRequest property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the telecomRequest property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTelecomRequest().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TelecomRequest }
     * 
     * 
     */
    public List<TelecomRequest> getTelecomRequest() {
        if (telecomRequest == null) {
            telecomRequest = new ArrayList<TelecomRequest>();
        }
        return this.telecomRequest;
    }

    /**
     * Gets the value of the postalAddressRequest property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the postalAddressRequest property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPostalAddressRequest().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PostalAddressRequest }
     * 
     * 
     */
    public List<PostalAddressRequest> getPostalAddressRequest() {
        if (postalAddressRequest == null) {
            postalAddressRequest = new ArrayList<PostalAddressRequest>();
        }
        return this.postalAddressRequest;
    }

    /**
     * Gets the value of the individualRequest property.
     * 
     * @return
     *     possible object is
     *     {@link IndividualRequest }
     *     
     */
    public IndividualRequest getIndividualRequest() {
        return individualRequest;
    }

    /**
     * Sets the value of the individualRequest property.
     * 
     * @param value
     *     allowed object is
     *     {@link IndividualRequest }
     *     
     */
    public void setIndividualRequest(IndividualRequest value) {
        this.individualRequest = value;
    }

    /**
     * Gets the value of the externalIdentifierRequest property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the externalIdentifierRequest property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getExternalIdentifierRequest().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ExternalIdentifierRequest }
     * 
     * 
     */
    public List<ExternalIdentifierRequest> getExternalIdentifierRequest() {
        if (externalIdentifierRequest == null) {
            externalIdentifierRequest = new ArrayList<ExternalIdentifierRequest>();
        }
        return this.externalIdentifierRequest;
    }

    /**
     * Gets the value of the comunicationPreferencesRequest property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the comunicationPreferencesRequest property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getComunicationPreferencesRequest().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ComunicationPreferencesRequest }
     * 
     * 
     */
    public List<ComunicationPreferencesRequest> getComunicationPreferencesRequest() {
        if (comunicationPreferencesRequest == null) {
            comunicationPreferencesRequest = new ArrayList<ComunicationPreferencesRequest>();
        }
        return this.comunicationPreferencesRequest;
    }

    /**
     * Gets the value of the prefilledNumbersRequest property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the prefilledNumbersRequest property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPrefilledNumbersRequest().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PrefilledNumbersRequest }
     * 
     * 
     */
    public List<PrefilledNumbersRequest> getPrefilledNumbersRequest() {
        if (prefilledNumbersRequest == null) {
            prefilledNumbersRequest = new ArrayList<PrefilledNumbersRequest>();
        }
        return this.prefilledNumbersRequest;
    }

    /**
     * Gets the value of the accountDelegationDataRequest property.
     * 
     * @return
     *     possible object is
     *     {@link AccountDelegationDataRequest }
     *     
     */
    public AccountDelegationDataRequest getAccountDelegationDataRequest() {
        return accountDelegationDataRequest;
    }

    /**
     * Sets the value of the accountDelegationDataRequest property.
     * 
     * @param value
     *     allowed object is
     *     {@link AccountDelegationDataRequest }
     *     
     */
    public void setAccountDelegationDataRequest(AccountDelegationDataRequest value) {
        this.accountDelegationDataRequest = value;
    }

    /**
     * Gets the value of the alertRequest property.
     * 
     * @return
     *     possible object is
     *     {@link AlertRequest }
     *     
     */
    public AlertRequest getAlertRequest() {
        return alertRequest;
    }

    /**
     * Sets the value of the alertRequest property.
     * 
     * @param value
     *     allowed object is
     *     {@link AlertRequest }
     *     
     */
    public void setAlertRequest(AlertRequest value) {
        this.alertRequest = value;
    }

    /**
     * Gets the value of the preferenceRequest property.
     * 
     * @return
     *     possible object is
     *     {@link PreferenceRequest }
     *     
     */
    public PreferenceRequest getPreferenceRequest() {
        return preferenceRequest;
    }

    /**
     * Sets the value of the preferenceRequest property.
     * 
     * @param value
     *     allowed object is
     *     {@link PreferenceRequest }
     *     
     */
    public void setPreferenceRequest(PreferenceRequest value) {
        this.preferenceRequest = value;
    }

    /**
     * Gets the value of the utfRequest property.
     * 
     * @return
     *     possible object is
     *     {@link UtfRequest }
     *     
     */
    public UtfRequest getUtfRequest() {
        return utfRequest;
    }

    /**
     * Sets the value of the utfRequest property.
     * 
     * @param value
     *     allowed object is
     *     {@link UtfRequest }
     *     
     */
    public void setUtfRequest(UtfRequest value) {
        this.utfRequest = value;
    }

}
