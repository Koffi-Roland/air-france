package com.airfrance.repind.dto.consent;

import java.io.Serializable;
import java.util.Date;


public  class ConsentDataDTO implements Serializable {
	
    /**
     * consentDataId
     */
    private Long consentDataId;

        
    /**
     * type
     */
    private String type;
        
        
    /**
     * consent
     */
    private String isConsent;

    /**
     * dateConsent
     */
    private Date dateConsent;
    
    
	/**
	 * dateCreation
	 */
	private Date dateCreation;
	    
	    
	/**
	 * signatureCreation
	 */
	private String signatureCreation;
	    
	    
	/**
	 * siteCreation
	 */
	private String siteCreation;
	    
	    
	/**
	 * dateModification
	 */
	private Date dateModification;
	    
	    
	/**
	 * signatureModification
	 */
	private String signatureModification;
	    
	    
	/**
	 * siteModification
	 */
	private String siteModification;
        
        
    /**
     * consentDTO
     */
    private ConsentDTO consentDTO;
    
    
    private static final long serialVersionUID = 1L;
    
	    
    /** 
     * default constructor 
     */
    public ConsentDataDTO() {
    //empty constructor
    }
	    

    /**
     *
     * @return consentDTO
     */
    public ConsentDTO getConsentDTO() {
        return this.consentDTO;
    }

    /**
     *
     * @param pConsentDTO consentDTO value
     */
    public void setConsentDTO(ConsentDTO pConsentDTO) {
        this.consentDTO = pConsentDTO;
    }

    /**
     *
     * @return consentDataId
     */
    public Long getConsentDataId() {
        return this.consentDataId;
    }

    /**
     *
     * @param pConsentDataId consentDataId value
     */
    public void setConsentDataId(Long pConsentDataId) {
        this.consentDataId = pConsentDataId;
    }

    /**
	 * @return the type
	 */
	public String getType() {
		return type;
	}


	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}


	/**
	 * @return the isConsent
	 */
	public String getIsConsent() {
		return isConsent;
	}


	/**
	 * @param isConsent the isConsent to set
	 */
	public void setIsConsent(String isConsent) {
		this.isConsent = isConsent;
	}


	/**
	 * @return the dateConsent
	 */
	public Date getDateConsent() {
		return dateConsent;
	}


	/**
	 * @param dateConsent the dateConsent to set
	 */
	public void setDateConsent(Date dateConsent) {
		this.dateConsent = dateConsent;
	}


	/**
	 * @return the dateCreation
	 */
	public Date getDateCreation() {
		return dateCreation;
	}


	/**
	 * @param dateCreation the dateCreation to set
	 */
	public void setDateCreation(Date dateCreation) {
		this.dateCreation = dateCreation;
	}


	/**
	 * @return the signatureCreation
	 */
	public String getSignatureCreation() {
		return signatureCreation;
	}


	/**
	 * @param signatureCreation the signatureCreation to set
	 */
	public void setSignatureCreation(String signatureCreation) {
		this.signatureCreation = signatureCreation;
	}


	/**
	 * @return the siteCreation
	 */
	public String getSiteCreation() {
		return siteCreation;
	}


	/**
	 * @param siteCreation the siteCreation to set
	 */
	public void setSiteCreation(String siteCreation) {
		this.siteCreation = siteCreation;
	}


	/**
	 * @return the dateModification
	 */
	public Date getDateModification() {
		return dateModification;
	}


	/**
	 * @param dateModification the dateModification to set
	 */
	public void setDateModification(Date dateModification) {
		this.dateModification = dateModification;
	}


	/**
	 * @return the signatureModification
	 */
	public String getSignatureModification() {
		return signatureModification;
	}


	/**
	 * @param signatureModification the signatureModification to set
	 */
	public void setSignatureModification(String signatureModification) {
		this.signatureModification = signatureModification;
	}


	/**
	 * @return the siteModification
	 */
	public String getSiteModification() {
		return siteModification;
	}


	/**
	 * @param siteModification the siteModification to set
	 */
	public void setSiteModification(String siteModification) {
		this.siteModification = siteModification;
	}


	/**
     *
     * @return object as string
     */
    public String toString() {
        String result = null;
        result = toStringImpl();
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
        buffer.append("consentDataId=").append(getConsentDataId());
        buffer.append(",");
        buffer.append("type=").append(getType());
        buffer.append(",");
        buffer.append("consent=").append(getIsConsent());
        buffer.append(",");
        buffer.append("dateConsent=").append(getDateConsent());
        buffer.append(",");
        buffer.append("dateCreation=").append(getDateCreation());
        buffer.append(",");
        buffer.append("signatureCreation=").append(getSignatureCreation());
        buffer.append(",");
        buffer.append("siteCreation=").append(getSiteCreation());
        buffer.append(",");
        buffer.append("dateModification=").append(getDateModification());
        buffer.append(",");
        buffer.append("signatureModification=").append(getSignatureModification());
        buffer.append(",");
        buffer.append("siteModification=").append(getSiteModification());
        buffer.append("]");
        return buffer.toString();
    }

}
