package com.afklm.rigui.dto.ws;

/*PROTECTED REGION ID(_kBtWcNW_Eeef5oRB6XPNlw i) ENABLED START*/

// add not generated imports here

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/*PROTECTED REGION END*/

/**
 * <p>Title : CommunicationPreferencesDTO</p>
 * 
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
	public  class CommunicationPreferencesDTO implements Serializable {


        
    /**
     * domain
     */
    private String domain;
        
        
    /**
     * communicationGroupeType
     */
    private String communicationGroupeType;
        
        
    /**
     * communicationType
     */
    private String communicationType;
        
        
    /**
     * optIn
     */
    private String optIn;
        
        
    /**
     * dateOfConsent
     */
    private Date dateOfConsent;
        
        
    /**
     * subscriptionChannel
     */
    private String subscriptionChannel;
        
        
    /**
     * optInPartner
     */
    private String optInPartner;
        
        
    /**
     * dateOfConsentPartner
     */
    private Date dateOfConsentPartner;
        
        
    /**
     * dateOfEntry
     */
    private Date dateOfEntry;
        
        
    /**
     * marketLanguageDTO
     */
    private List<MarketLanguageDTO> marketLanguageDTO;
        
        
    /**
     * mediaDTO
     */
    private MediaDTO mediaDTO;
        

    /*PROTECTED REGION ID(_kBtWcNW_Eeef5oRB6XPNlw u var) ENABLED START*/
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
	    public CommunicationPreferencesDTO() {
	    //empty constructor
	    }
	    
    /**
     *
     * @return communicationGroupeType
     */
    public String getCommunicationGroupeType() {
        return this.communicationGroupeType;
    }

    /**
     *
     * @param pCommunicationGroupeType communicationGroupeType value
     */
    public void setCommunicationGroupeType(String pCommunicationGroupeType) {
        this.communicationGroupeType = pCommunicationGroupeType;
    }

    /**
     *
     * @return communicationType
     */
    public String getCommunicationType() {
        return this.communicationType;
    }

    /**
     *
     * @param pCommunicationType communicationType value
     */
    public void setCommunicationType(String pCommunicationType) {
        this.communicationType = pCommunicationType;
    }

    /**
     *
     * @return dateOfConsent
     */
    public Date getDateOfConsent() {
        return this.dateOfConsent;
    }

    /**
     *
     * @param pDateOfConsent dateOfConsent value
     */
    public void setDateOfConsent(Date pDateOfConsent) {
        this.dateOfConsent = pDateOfConsent;
    }

    /**
     *
     * @return dateOfConsentPartner
     */
    public Date getDateOfConsentPartner() {
        return this.dateOfConsentPartner;
    }

    /**
     *
     * @param pDateOfConsentPartner dateOfConsentPartner value
     */
    public void setDateOfConsentPartner(Date pDateOfConsentPartner) {
        this.dateOfConsentPartner = pDateOfConsentPartner;
    }

    /**
     *
     * @return dateOfEntry
     */
    public Date getDateOfEntry() {
        return this.dateOfEntry;
    }

    /**
     *
     * @param pDateOfEntry dateOfEntry value
     */
    public void setDateOfEntry(Date pDateOfEntry) {
        this.dateOfEntry = pDateOfEntry;
    }

    /**
     *
     * @return domain
     */
    public String getDomain() {
        return this.domain;
    }

    /**
     *
     * @param pDomain domain value
     */
    public void setDomain(String pDomain) {
        this.domain = pDomain;
    }

    /**
     *
     * @return marketLanguageDTO
     */
    public List<MarketLanguageDTO> getMarketLanguageDTO() {
        return this.marketLanguageDTO;
    }

    /**
     *
     * @param pMarketLanguageDTO marketLanguageDTO value
     */
    public void setMarketLanguageDTO(List<MarketLanguageDTO> pMarketLanguageDTO) {
        this.marketLanguageDTO = pMarketLanguageDTO;
    }

    /**
     *
     * @return mediaDTO
     */
    public MediaDTO getMediaDTO() {
        return this.mediaDTO;
    }

    /**
     *
     * @param pMediaDTO mediaDTO value
     */
    public void setMediaDTO(MediaDTO pMediaDTO) {
        this.mediaDTO = pMediaDTO;
    }

    /**
     *
     * @return optIn
     */
    public String getOptIn() {
        return this.optIn;
    }

    /**
     *
     * @param pOptIn optIn value
     */
    public void setOptIn(String pOptIn) {
        this.optIn = pOptIn;
    }

    /**
     *
     * @return optInPartner
     */
    public String getOptInPartner() {
        return this.optInPartner;
    }

    /**
     *
     * @param pOptInPartner optInPartner value
     */
    public void setOptInPartner(String pOptInPartner) {
        this.optInPartner = pOptInPartner;
    }

    /**
     *
     * @return subscriptionChannel
     */
    public String getSubscriptionChannel() {
        return this.subscriptionChannel;
    }

    /**
     *
     * @param pSubscriptionChannel subscriptionChannel value
     */
    public void setSubscriptionChannel(String pSubscriptionChannel) {
        this.subscriptionChannel = pSubscriptionChannel;
    }

    /**
     *
     * @return object as string
     */
    public String toString() {
        String result = null;
        /*PROTECTED REGION ID(toString_kBtWcNW_Eeef5oRB6XPNlw) ENABLED START*/
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
        buffer.append("domain=").append(getDomain());
        buffer.append(",");
        buffer.append("communicationGroupeType=").append(getCommunicationGroupeType());
        buffer.append(",");
        buffer.append("communicationType=").append(getCommunicationType());
        buffer.append(",");
        buffer.append("optIn=").append(getOptIn());
        buffer.append(",");
        buffer.append("dateOfConsent=").append(getDateOfConsent());
        buffer.append(",");
        buffer.append("subscriptionChannel=").append(getSubscriptionChannel());
        buffer.append(",");
        buffer.append("optInPartner=").append(getOptInPartner());
        buffer.append(",");
        buffer.append("dateOfConsentPartner=").append(getDateOfConsentPartner());
        buffer.append(",");
        buffer.append("dateOfEntry=").append(getDateOfEntry());
        buffer.append("]");
        return buffer.toString();
    }

    /*PROTECTED REGION ID(_kBtWcNW_Eeef5oRB6XPNlw u m) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/

}
