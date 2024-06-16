package com.airfrance.repind.dto.ws.createupdateindividual;

/*PROTECTED REGION ID(_we0eDtUoEeef5oRB6XPNlw i) ENABLED START*/

// add not generated imports here

import com.airfrance.repind.dto.ws.*;

import java.io.Serializable;
import java.util.List;

/*PROTECTED REGION END*/

/**
 * <p>Title : CreateUpdateIndividualRequestDTO</p>
 * 
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
	public  class CreateUpdateIndividualRequestDTO implements Serializable {


        
    /**
     * newsletterMediaSending
     */
    private String newsletterMediaSending;
        
        
    /**
     * status
     */
    private String status;
        
        
    /**
     * updateCommunicationPrefMode
     */
    private String updateCommunicationPrefMode;
        
        
    /**
     * updatePrefilledNumbersMode
     */
    private String updatePrefilledNumbersMode;
        
        
    /**
     * process
     */
    private String process;
        
        
    /**
     * accountDelegationDataRequestDTO
     */
    private AccountDelegationDataRequestDTO accountDelegationDataRequestDTO;
        
        
    /**
     * alertRequestDTO
     */
    private AlertRequestDTO alertRequestDTO;
        
        
    /**
     * communicationPreferencesRequestDTO
     */
    private List<CommunicationPreferencesRequestDTO> communicationPreferencesRequestDTO;
        
        
    /**
     * emailRequestDTO
     */
    private List<EmailRequestDTO> emailRequestDTO;
        
        
    /**
     * externalIdentifierRequestDTO
     */
    private List<ExternalIdentifierRequestDTO> externalIdentifierRequestDTO;
        
        
    /**
     * individualRequestDTO
     */
    private IndividualRequestDTO individualRequestDTO;
        
        
    /**
     * postalAddressRequestDTO
     */
    private List<PostalAddressRequestDTO> postalAddressRequestDTO;
        
        
    /**
     * preferenceRequestDTO
     */
    private PreferenceRequestDTO preferenceRequestDTO;
        
        
    /**
     * prefilledNumbersRequestDTO
     */
    private List<PrefilledNumbersRequestDTO> prefilledNumbersRequestDTO;
        
        
    /**
     * requestorDTO
     */
    private RequestorDTO requestorDTO;
        
        
    /**
     * telecomRequestDTO
     */
    private List<TelecomRequestDTO> telecomRequestDTO;
        
        
    /**
     * utfRequestDTO
     */
    private UtfRequestDTO utfRequestDTO;
    
    /**
     * consumerId
     */
    private String consumerId;

    /*PROTECTED REGION ID(_we0eDtUoEeef5oRB6XPNlw u var) ENABLED START*/
    // add your custom variables here if necessary
    

	/**
   * Determines if a de-serialized file is compatible with this class.
   *
   * Maintainers must change this value if and only if the new version
   * of this class is not compatible with old versions. See Sun docs
   * for <a href=http://java.sun.com/j2se/1.5.0/docs/guide/serialization
   * /serialization/spec/class.html#4100> details. </a>
   *
   * Not necessary to include in first version of the class, but
   * included here as a reminder of its importance.
   */
    private static final long serialVersionUID = 1L;
    
    /*PROTECTED REGION END*/

    
	    
	    /** 
	     * default constructor 
	     */
	    public CreateUpdateIndividualRequestDTO() {
	    //empty constructor
	    }
	    
    /**
     *
     * @return accountDelegationDataRequestDTO
     */
    public AccountDelegationDataRequestDTO getAccountDelegationDataRequestDTO() {
        return this.accountDelegationDataRequestDTO;
    }

    /**
     *
     * @param pAccountDelegationDataRequestDTO accountDelegationDataRequestDTO value
     */
    public void setAccountDelegationDataRequestDTO(AccountDelegationDataRequestDTO pAccountDelegationDataRequestDTO) {
        this.accountDelegationDataRequestDTO = pAccountDelegationDataRequestDTO;
    }

    /**
     *
     * @return alertRequestDTO
     */
    public AlertRequestDTO getAlertRequestDTO() {
        return this.alertRequestDTO;
    }

    /**
     *
     * @param pAlertRequestDTO alertRequestDTO value
     */
    public void setAlertRequestDTO(AlertRequestDTO pAlertRequestDTO) {
        this.alertRequestDTO = pAlertRequestDTO;
    }

    /**
     *
     * @return communicationPreferencesRequestDTO
     */
    public List<CommunicationPreferencesRequestDTO> getCommunicationPreferencesRequestDTO() {
        return this.communicationPreferencesRequestDTO;
    }

    /**
     *
     * @param pCommunicationPreferencesRequestDTO communicationPreferencesRequestDTO value
     */
    public void setCommunicationPreferencesRequestDTO(List<CommunicationPreferencesRequestDTO> pCommunicationPreferencesRequestDTO) {
        this.communicationPreferencesRequestDTO = pCommunicationPreferencesRequestDTO;
    }

    /**
     *
     * @return emailRequestDTO
     */
    public List<EmailRequestDTO> getEmailRequestDTO() {
        return this.emailRequestDTO;
    }

    /**
     *
     * @param pEmailRequestDTO emailRequestDTO value
     */
    public void setEmailRequestDTO(List<EmailRequestDTO> pEmailRequestDTO) {
        this.emailRequestDTO = pEmailRequestDTO;
    }

    /**
     *
     * @return externalIdentifierRequestDTO
     */
    public List<ExternalIdentifierRequestDTO> getExternalIdentifierRequestDTO() {
        return this.externalIdentifierRequestDTO;
    }

    /**
     *
     * @param pExternalIdentifierRequestDTO externalIdentifierRequestDTO value
     */
    public void setExternalIdentifierRequestDTO(List<ExternalIdentifierRequestDTO> pExternalIdentifierRequestDTO) {
        this.externalIdentifierRequestDTO = pExternalIdentifierRequestDTO;
    }

    /**
     *
     * @return individualRequestDTO
     */
    public IndividualRequestDTO getIndividualRequestDTO() {
        return this.individualRequestDTO;
    }

    /**
     *
     * @param pIndividualRequestDTO individualRequestDTO value
     */
    public void setIndividualRequestDTO(IndividualRequestDTO pIndividualRequestDTO) {
        this.individualRequestDTO = pIndividualRequestDTO;
    }

    /**
     *
     * @return newsletterMediaSending
     */
    public String getNewsletterMediaSending() {
        return this.newsletterMediaSending;
    }

    /**
     *
     * @param pNewsletterMediaSending newsletterMediaSending value
     */
    public void setNewsletterMediaSending(String pNewsletterMediaSending) {
        this.newsletterMediaSending = pNewsletterMediaSending;
    }

    /**
     *
     * @return postalAddressRequestDTO
     */
    public List<PostalAddressRequestDTO> getPostalAddressRequestDTO() {
        return this.postalAddressRequestDTO;
    }

    /**
     *
     * @param pPostalAddressRequestDTO postalAddressRequestDTO value
     */
    public void setPostalAddressRequestDTO(List<PostalAddressRequestDTO> pPostalAddressRequestDTO) {
        this.postalAddressRequestDTO = pPostalAddressRequestDTO;
    }

    /**
     *
     * @return preferenceRequestDTO
     */
    public PreferenceRequestDTO getPreferenceRequestDTO() {
        return this.preferenceRequestDTO;
    }

    /**
     *
     * @param pPreferenceRequestDTO preferenceRequestDTO value
     */
    public void setPreferenceRequestDTO(PreferenceRequestDTO pPreferenceRequestDTO) {
        this.preferenceRequestDTO = pPreferenceRequestDTO;
    }

    /**
     *
     * @return prefilledNumbersRequestDTO
     */
    public List<PrefilledNumbersRequestDTO> getPrefilledNumbersRequestDTO() {
        return this.prefilledNumbersRequestDTO;
    }

    /**
     *
     * @param pPrefilledNumbersRequestDTO prefilledNumbersRequestDTO value
     */
    public void setPrefilledNumbersRequestDTO(List<PrefilledNumbersRequestDTO> pPrefilledNumbersRequestDTO) {
        this.prefilledNumbersRequestDTO = pPrefilledNumbersRequestDTO;
    }

    /**
     *
     * @return process
     */
    public String getProcess() {
        return this.process;
    }

    /**
     *
     * @param pProcess process value
     */
    public void setProcess(String pProcess) {
        this.process = pProcess;
    }

    /**
     *
     * @return requestorDTO
     */
    public RequestorDTO getRequestorDTO() {
        return this.requestorDTO;
    }

    /**
     *
     * @param pRequestorDTO requestorDTO value
     */
    public void setRequestorDTO(RequestorDTO pRequestorDTO) {
        this.requestorDTO = pRequestorDTO;
    }

    /**
     *
     * @return status
     */
    public String getStatus() {
        return this.status;
    }

    /**
     *
     * @param pStatus status value
     */
    public void setStatus(String pStatus) {
        this.status = pStatus;
    }

    /**
     *
     * @return telecomRequestDTO
     */
    public List<TelecomRequestDTO> getTelecomRequestDTO() {
        return this.telecomRequestDTO;
    }

    /**
     *
     * @param pTelecomRequestDTO telecomRequestDTO value
     */
    public void setTelecomRequestDTO(List<TelecomRequestDTO> pTelecomRequestDTO) {
        this.telecomRequestDTO = pTelecomRequestDTO;
    }

    /**
     *
     * @return updateCommunicationPrefMode
     */
    public String getUpdateCommunicationPrefMode() {
        return this.updateCommunicationPrefMode;
    }

    /**
     *
     * @param pUpdateCommunicationPrefMode updateCommunicationPrefMode value
     */
    public void setUpdateCommunicationPrefMode(String pUpdateCommunicationPrefMode) {
        this.updateCommunicationPrefMode = pUpdateCommunicationPrefMode;
    }

    /**
     *
     * @return updatePrefilledNumbersMode
     */
    public String getUpdatePrefilledNumbersMode() {
        return this.updatePrefilledNumbersMode;
    }

    /**
     *
     * @param pUpdatePrefilledNumbersMode updatePrefilledNumbersMode value
     */
    public void setUpdatePrefilledNumbersMode(String pUpdatePrefilledNumbersMode) {
        this.updatePrefilledNumbersMode = pUpdatePrefilledNumbersMode;
    }

    /**
     *
     * @return utfRequestDTO
     */
    public UtfRequestDTO getUtfRequestDTO() {
        return this.utfRequestDTO;
    }

    /**
     *
     * @param pUtfRequestDTO utfRequestDTO value
     */
    public void setUtfRequestDTO(UtfRequestDTO pUtfRequestDTO) {
        this.utfRequestDTO = pUtfRequestDTO;
    }
    
    /**
     * 
     * @return consumerId
     */
    public String getConsumerId() {
		return consumerId;
	}

    /**
     * 
     * @param consumerId
     */
	public void setConsumerId(String consumerId) {
		this.consumerId = consumerId;
	}

    /**
     *
     * @return object as string
     */
    public String toString() {
        String result = null;
        /*PROTECTED REGION ID(toString_we0eDtUoEeef5oRB6XPNlw) ENABLED START*/
        result = toStringImpl();
        /*PROTECTED REGION END*/
        return result;
    }

    /**
     *
     * @return object as string
     */
    public String toStringImpl() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(getClass().getName());
        buffer.append("@").append(Integer.toHexString(System.identityHashCode(this)));
        buffer.append("[");
        buffer.append("newsletterMediaSending=").append(getNewsletterMediaSending());
        buffer.append(",");
        buffer.append("status=").append(getStatus());
        buffer.append(",");
        buffer.append("updateCommunicationPrefMode=").append(getUpdateCommunicationPrefMode());
        buffer.append(",");
        buffer.append("updatePrefilledNumbersMode=").append(getUpdatePrefilledNumbersMode());
        buffer.append(",");
        buffer.append("process=").append(getProcess());
        buffer.append("]");
        return buffer.toString();
    }

    /*PROTECTED REGION ID(_we0eDtUoEeef5oRB6XPNlw u m) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/

}
