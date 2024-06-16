package com.afklm.rigui.dto.reference;




import java.io.Serializable;
import java.util.Date;



public class RefPermissionsDTO implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	/**
     * refPermissionsId
     */
	private RefPermissionsIdDTO refPermissionsId;
	
	
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


	public RefPermissionsDTO() {
		super();
	}


	public RefPermissionsDTO(RefPermissionsIdDTO refPermissionsId, Date dateCreation, String siteCreation,
			String signatureCreation, Date dateModification, String siteModification, String signatureModification) {
		super();
		this.refPermissionsId = refPermissionsId;
		this.dateCreation = dateCreation;
		this.siteCreation = siteCreation;
		this.signatureCreation = signatureCreation;
		this.dateModification = dateModification;
		this.siteModification = siteModification;
		this.signatureModification = signatureModification;
	}


	public RefPermissionsIdDTO getRefPermissionsId() {
		return refPermissionsId;
	}


	public void setRefPermissionsId(RefPermissionsIdDTO refPermissionsId) {
		this.refPermissionsId = refPermissionsId;
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
		return "RefPermissionsDTO [refPermissionsId=" + refPermissionsId + ", dateCreation=" + dateCreation
				+ ", siteCreation=" + siteCreation + ", signatureCreation=" + signatureCreation + ", dateModification="
				+ dateModification + ", siteModification=" + siteModification + ", signatureModification="
				+ signatureModification + "]";
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dateCreation == null) ? 0 : dateCreation.hashCode());
		result = prime * result + ((dateModification == null) ? 0 : dateModification.hashCode());
		result = prime * result + ((refPermissionsId == null) ? 0 : refPermissionsId.hashCode());
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
		RefPermissionsDTO other = (RefPermissionsDTO) obj;
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
		if (refPermissionsId == null) {
			if (other.refPermissionsId != null)
				return false;
		} else if (!refPermissionsId.equals(other.refPermissionsId))
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
