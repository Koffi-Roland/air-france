package com.airfrance.repind.dto.tracking;

import com.airfrance.repind.dto.individu.IndividuDTO;
import com.airfrance.repind.dto.reference.RefPermissionsQuestionDTO;

import java.io.Serializable;
import java.util.Date;


public class TrackingPermissionsDTO implements Serializable {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	/**
     * id
     */
    private Integer id;
        
            
    /**
     * sgin
     */
    private IndividuDTO sgin;
        
            
    /**
     * permissionQuestionId
     */
    private RefPermissionsQuestionDTO permissionQuestionId;
    
    
    /**
     * consent
     */
    private String consent;

    
    /**
     * dateConsent
     */
    private Date dateConsent;
    
    
    /**
     * dateCreation
     */
    private Date dateCreation;
    
    
    /**
     * siteCreation
     */
    private String siteCreation;
    
    
    /**
     * signatureCreation
     */
    private String signatureCreation;
    
    
    /**
     * dateModification
     */
    private Date dateModification;
    
    
    /**
     * siteModification
     */
    private String siteModification;
    
    
    /**
     * signatureModification
     */
    private String signatureModification;


	public TrackingPermissionsDTO() {
		super();
	}


	public TrackingPermissionsDTO(Integer id, IndividuDTO sgin, RefPermissionsQuestionDTO permissionQuestionId, String consent,
			Date dateConsent, Date dateCreation, String siteCreation, String signatureCreation, Date dateModification,
			String siteModification, String signatureModification) {
		super();
		this.id = id;
		this.sgin = sgin;
		this.permissionQuestionId = permissionQuestionId;
		this.consent = consent;
		this.dateConsent = dateConsent;
		this.dateCreation = dateCreation;
		this.siteCreation = siteCreation;
		this.signatureCreation = signatureCreation;
		this.dateModification = dateModification;
		this.siteModification = siteModification;
		this.signatureModification = signatureModification;
	}


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public IndividuDTO getSgin() {
		return sgin;
	}


	public void setSgin(IndividuDTO sgin) {
		this.sgin = sgin;
	}


	public RefPermissionsQuestionDTO getPermissionQuestionId() {
		return permissionQuestionId;
	}


	public void setPermissionQuestionId(RefPermissionsQuestionDTO permissionQuestionId) {
		this.permissionQuestionId = permissionQuestionId;
	}


	public String getConsent() {
		return consent;
	}


	public void setConsent(String consent) {
		this.consent = consent;
	}


	public Date getDateConsent() {
		return dateConsent;
	}


	public void setDateConsent(Date dateConsent) {
		this.dateConsent = dateConsent;
	}


	public Date getDateCreation() {
		return dateCreation;
	}


	public void setDateCreation(Date dateCreation) {
		this.dateCreation = dateCreation;
	}


	public String getSiteCreation() {
		return siteCreation;
	}


	public void setSiteCreation(String siteCreation) {
		this.siteCreation = siteCreation;
	}


	public String getSignatureCreation() {
		return signatureCreation;
	}


	public void setSignatureCreation(String signatureCreation) {
		this.signatureCreation = signatureCreation;
	}


	public Date getDateModification() {
		return dateModification;
	}


	public void setDateModification(Date dateModification) {
		this.dateModification = dateModification;
	}


	public String getSiteModification() {
		return siteModification;
	}


	public void setSiteModification(String siteModification) {
		this.siteModification = siteModification;
	}


	public String getSignatureModification() {
		return signatureModification;
	}


	public void setSignatureModification(String signatureModification) {
		this.signatureModification = signatureModification;
	}


	@Override
	public String toString() {
		return "TrackingPermissionsDTO [id=" + id + ", sgin=" + sgin + ", permissionQuestionId=" + permissionQuestionId + ", consent=" + consent
				+ ", dateConsent=" + dateConsent + ", dateCreation=" + dateCreation + ", siteCreation=" + siteCreation
				+ ", signatureCreation=" + signatureCreation + ", dateModification=" + dateModification
				+ ", siteModification=" + siteModification + ", signatureModification=" + signatureModification + "]";
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((permissionQuestionId == null) ? 0 : permissionQuestionId.hashCode());
		result = prime * result + ((consent == null) ? 0 : consent.hashCode());
		result = prime * result + ((dateConsent == null) ? 0 : dateConsent.hashCode());
		result = prime * result + ((dateCreation == null) ? 0 : dateCreation.hashCode());
		result = prime * result + ((dateModification == null) ? 0 : dateModification.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((sgin == null) ? 0 : sgin.hashCode());
		result = prime * result + ((signatureCreation == null) ? 0 : signatureCreation.hashCode());
		result = prime * result + ((signatureModification == null) ? 0 : signatureModification.hashCode());
		result = prime * result + ((siteCreation == null) ? 0 : siteCreation.hashCode());
		result = prime * result + ((siteModification == null) ? 0 : siteModification.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TrackingPermissionsDTO other = (TrackingPermissionsDTO) obj;
		if (permissionQuestionId == null) {
			if (other.permissionQuestionId != null)
				return false;
		} else if (!permissionQuestionId.equals(other.permissionQuestionId))
			return false;
		if (consent == null) {
			if (other.consent != null)
				return false;
		} else if (!consent.equals(other.consent))
			return false;
		if (dateConsent == null) {
			if (other.dateConsent != null)
				return false;
		} else if (!dateConsent.equals(other.dateConsent))
			return false;
		if (dateCreation == null) {
			if (other.dateCreation != null)
				return false;
		} else if (!dateCreation.equals(other.dateCreation))
			return false;
		if (dateModification == null) {
			if (other.dateModification != null)
				return false;
		} else if (!dateModification.equals(other.dateModification))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (sgin == null) {
			if (other.sgin != null)
				return false;
		} else if (!sgin.equals(other.sgin))
			return false;
		if (signatureCreation == null) {
			if (other.signatureCreation != null)
				return false;
		} else if (!signatureCreation.equals(other.signatureCreation))
			return false;
		if (signatureModification == null) {
			if (other.signatureModification != null)
				return false;
		} else if (!signatureModification.equals(other.signatureModification))
			return false;
		if (siteCreation == null) {
			if (other.siteCreation != null)
				return false;
		} else if (!siteCreation.equals(other.siteCreation))
			return false;
		if (siteModification == null) {
			if (other.siteModification != null)
				return false;
		} else if (!siteModification.equals(other.siteModification))
			return false;
		return true;
	}
}
