
package com.afklm.repind.ws.provideindividualdata.v07.data;

import com.afklm.repind.ws.provideindividualdata.v07.data.response.*;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>Java class for ProvideIndividualInformationResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ProvideIndividualInformationResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="telecomResponse" type="{http://www.af-klm.com/services/passenger/response-v7/xsd}TelecomResponse" maxOccurs="6" minOccurs="0"/>
 *         &lt;element name="emailResponse" type="{http://www.af-klm.com/services/passenger/response-v7/xsd}EmailResponse" maxOccurs="2" minOccurs="0"/>
 *         &lt;element name="postalAddressResponse" type="{http://www.af-klm.com/services/passenger/response-v7/xsd}PostalAddressResponse" maxOccurs="5" minOccurs="0"/>
 *         &lt;element name="individualResponse" type="{http://www.af-klm.com/services/passenger/response-v7/xsd}IndividualResponse" minOccurs="0"/>
 *         &lt;element name="contractResponse" type="{http://www.af-klm.com/services/passenger/response-v7/xsd}ContractResponse" maxOccurs="25" minOccurs="0"/>
 *         &lt;element name="externalIdentifierResponse" type="{http://www.af-klm.com/services/passenger/response-v7/xsd}ExternalIdentifierResponse" maxOccurs="100" minOccurs="0"/>
 *         &lt;element name="accountDataResponse" type="{http://www.af-klm.com/services/passenger/response-v7/xsd}AccountDataResponse" minOccurs="0"/>
 *         &lt;element name="prefilledNumbersResponse" type="{http://www.af-klm.com/services/passenger/response-v7/xsd}PrefilledNumbersResponse" maxOccurs="50" minOccurs="0"/>
 *         &lt;element name="socialNetworkDataResponse" type="{http://www.af-klm.com/services/passenger/response-v7/xsd}SocialNetworkDataResponse" minOccurs="0"/>
 *         &lt;element name="communicationPreferencesResponse" type="{http://www.af-klm.com/services/passenger/response-v7/xsd}CommunicationPreferencesResponse" maxOccurs="100" minOccurs="0"/>
 *         &lt;element name="delegationDataResponse" type="{http://www.af-klm.com/services/passenger/response-v7/xsd}DelegationDataResponse" minOccurs="0"/>
 *         &lt;element name="warningResponse" type="{http://www.af-klm.com/services/passenger/response-v7/xsd}WarningResponse" maxOccurs="100" minOccurs="0"/>
 *         &lt;element name="localizationResponse" type="{http://www.af-klm.com/services/passenger/response-v7/xsd}LocalizationResponse" maxOccurs="5" minOccurs="0"/>
 *         &lt;element name="alertResponse" type="{http://www.af-klm.com/services/passenger/response-v7/xsd}AlertResponse" minOccurs="0"/>
 *         &lt;element name="preferenceResponse" type="{http://www.af-klm.com/services/passenger/response-v7/xsd}PreferenceResponse" minOccurs="0"/>
 *         &lt;element name="utfResponse" type="{http://www.af-klm.com/services/passenger/response-v7/xsd}UtfResponse" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ProvideIndividualInformationResponse", propOrder = {
    "telecomResponse",
    "emailResponse",
    "postalAddressResponse",
    "individualResponse",
    "contractResponse",
    "externalIdentifierResponse",
    "accountDataResponse",
    "prefilledNumbersResponse",
    "socialNetworkDataResponse",
    "communicationPreferencesResponse",
    "delegationDataResponse",
    "warningResponse",
    "localizationResponse",
    "alertResponse",
    "preferenceResponse",
    "utfResponse"
})
public class ProvideIndividualInformationResponse {

    protected List<TelecomResponse> telecomResponse;
    protected List<EmailResponse> emailResponse;
    protected List<PostalAddressResponse> postalAddressResponse;
    protected IndividualResponse individualResponse;
    protected List<ContractResponse> contractResponse;
    protected List<ExternalIdentifierResponse> externalIdentifierResponse;
    protected AccountDataResponse accountDataResponse;
    protected List<PrefilledNumbersResponse> prefilledNumbersResponse;
    protected SocialNetworkDataResponse socialNetworkDataResponse;
    protected List<CommunicationPreferencesResponse> communicationPreferencesResponse;
    protected DelegationDataResponse delegationDataResponse;
    protected List<WarningResponse> warningResponse;
    protected List<LocalizationResponse> localizationResponse;
    protected AlertResponse alertResponse;
    protected PreferenceResponse preferenceResponse;
    protected UtfResponse utfResponse;

    /**
     * Gets the value of the telecomResponse property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the telecomResponse property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTelecomResponse().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TelecomResponse }
     * 
     * 
     */
    public List<TelecomResponse> getTelecomResponse() {
        if (telecomResponse == null) {
            telecomResponse = new ArrayList<TelecomResponse>();
        }
        return this.telecomResponse;
    }

    /**
     * Gets the value of the emailResponse property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the emailResponse property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getEmailResponse().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link EmailResponse }
     * 
     * 
     */
    public List<EmailResponse> getEmailResponse() {
        if (emailResponse == null) {
            emailResponse = new ArrayList<EmailResponse>();
        }
        return this.emailResponse;
    }

    /**
     * Gets the value of the postalAddressResponse property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the postalAddressResponse property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPostalAddressResponse().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PostalAddressResponse }
     * 
     * 
     */
    public List<PostalAddressResponse> getPostalAddressResponse() {
        if (postalAddressResponse == null) {
            postalAddressResponse = new ArrayList<PostalAddressResponse>();
        }
        return this.postalAddressResponse;
    }

    /**
     * Gets the value of the individualResponse property.
     * 
     * @return
     *     possible object is
     *     {@link IndividualResponse }
     *     
     */
    public IndividualResponse getIndividualResponse() {
        return individualResponse;
    }

    /**
     * Sets the value of the individualResponse property.
     * 
     * @param value
     *     allowed object is
     *     {@link IndividualResponse }
     *     
     */
    public void setIndividualResponse(IndividualResponse value) {
        this.individualResponse = value;
    }

    /**
     * Gets the value of the contractResponse property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the contractResponse property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getContractResponse().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ContractResponse }
     * 
     * 
     */
    public List<ContractResponse> getContractResponse() {
        if (contractResponse == null) {
            contractResponse = new ArrayList<ContractResponse>();
        }
        return this.contractResponse;
    }

    /**
     * Gets the value of the externalIdentifierResponse property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the externalIdentifierResponse property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getExternalIdentifierResponse().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ExternalIdentifierResponse }
     * 
     * 
     */
    public List<ExternalIdentifierResponse> getExternalIdentifierResponse() {
        if (externalIdentifierResponse == null) {
            externalIdentifierResponse = new ArrayList<ExternalIdentifierResponse>();
        }
        return this.externalIdentifierResponse;
    }

    /**
     * Gets the value of the accountDataResponse property.
     * 
     * @return
     *     possible object is
     *     {@link AccountDataResponse }
     *     
     */
    public AccountDataResponse getAccountDataResponse() {
        return accountDataResponse;
    }

    /**
     * Sets the value of the accountDataResponse property.
     * 
     * @param value
     *     allowed object is
     *     {@link AccountDataResponse }
     *     
     */
    public void setAccountDataResponse(AccountDataResponse value) {
        this.accountDataResponse = value;
    }

    /**
     * Gets the value of the prefilledNumbersResponse property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the prefilledNumbersResponse property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPrefilledNumbersResponse().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PrefilledNumbersResponse }
     * 
     * 
     */
    public List<PrefilledNumbersResponse> getPrefilledNumbersResponse() {
        if (prefilledNumbersResponse == null) {
            prefilledNumbersResponse = new ArrayList<PrefilledNumbersResponse>();
        }
        return this.prefilledNumbersResponse;
    }

    /**
     * Gets the value of the socialNetworkDataResponse property.
     * 
     * @return
     *     possible object is
     *     {@link SocialNetworkDataResponse }
     *     
     */
    public SocialNetworkDataResponse getSocialNetworkDataResponse() {
        return socialNetworkDataResponse;
    }

    /**
     * Sets the value of the socialNetworkDataResponse property.
     * 
     * @param value
     *     allowed object is
     *     {@link SocialNetworkDataResponse }
     *     
     */
    public void setSocialNetworkDataResponse(SocialNetworkDataResponse value) {
        this.socialNetworkDataResponse = value;
    }

    /**
     * Gets the value of the communicationPreferencesResponse property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the communicationPreferencesResponse property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCommunicationPreferencesResponse().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CommunicationPreferencesResponse }
     * 
     * 
     */
    public List<CommunicationPreferencesResponse> getCommunicationPreferencesResponse() {
        if (communicationPreferencesResponse == null) {
            communicationPreferencesResponse = new ArrayList<CommunicationPreferencesResponse>();
        }
        return this.communicationPreferencesResponse;
    }

    /**
     * Gets the value of the delegationDataResponse property.
     * 
     * @return
     *     possible object is
     *     {@link DelegationDataResponse }
     *     
     */
    public DelegationDataResponse getDelegationDataResponse() {
        return delegationDataResponse;
    }

    /**
     * Sets the value of the delegationDataResponse property.
     * 
     * @param value
     *     allowed object is
     *     {@link DelegationDataResponse }
     *     
     */
    public void setDelegationDataResponse(DelegationDataResponse value) {
        this.delegationDataResponse = value;
    }

    /**
     * Gets the value of the warningResponse property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the warningResponse property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getWarningResponse().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link WarningResponse }
     * 
     * 
     */
    public List<WarningResponse> getWarningResponse() {
        if (warningResponse == null) {
            warningResponse = new ArrayList<WarningResponse>();
        }
        return this.warningResponse;
    }

    /**
     * Gets the value of the localizationResponse property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the localizationResponse property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getLocalizationResponse().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link LocalizationResponse }
     * 
     * 
     */
    public List<LocalizationResponse> getLocalizationResponse() {
        if (localizationResponse == null) {
            localizationResponse = new ArrayList<LocalizationResponse>();
        }
        return this.localizationResponse;
    }

    /**
     * Gets the value of the alertResponse property.
     * 
     * @return
     *     possible object is
     *     {@link AlertResponse }
     *     
     */
    public AlertResponse getAlertResponse() {
        return alertResponse;
    }

    /**
     * Sets the value of the alertResponse property.
     * 
     * @param value
     *     allowed object is
     *     {@link AlertResponse }
     *     
     */
    public void setAlertResponse(AlertResponse value) {
        this.alertResponse = value;
    }

    /**
     * Gets the value of the preferenceResponse property.
     * 
     * @return
     *     possible object is
     *     {@link PreferenceResponse }
     *     
     */
    public PreferenceResponse getPreferenceResponse() {
        return preferenceResponse;
    }

    /**
     * Sets the value of the preferenceResponse property.
     * 
     * @param value
     *     allowed object is
     *     {@link PreferenceResponse }
     *     
     */
    public void setPreferenceResponse(PreferenceResponse value) {
        this.preferenceResponse = value;
    }

    /**
     * Gets the value of the utfResponse property.
     * 
     * @return
     *     possible object is
     *     {@link UtfResponse }
     *     
     */
    public UtfResponse getUtfResponse() {
        return utfResponse;
    }

    /**
     * Sets the value of the utfResponse property.
     * 
     * @param value
     *     allowed object is
     *     {@link UtfResponse }
     *     
     */
    public void setUtfResponse(UtfResponse value) {
        this.utfResponse = value;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

    @Override
    public boolean equals(Object that) {
        return EqualsBuilder.reflectionEquals(this, that);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

}
