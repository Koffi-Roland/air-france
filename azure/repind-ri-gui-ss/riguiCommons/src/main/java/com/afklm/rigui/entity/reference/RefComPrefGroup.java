package com.afklm.rigui.entity.reference;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;


@Entity
@Table(name="REF_COMPREF_GROUP")
public class RefComPrefGroup implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;


	/**
	 * refComPrefGroupId
	 */
	@EmbeddedId
	private RefComPrefGroupId refComPrefGroupId;


	/**
	 * dateCreation
	 */
	@Column(name="DDATE_CREATION", nullable=false)
	private Date dateCreation;


	/**
	 * siteCreation
	 */
	@Column(name="SSITE_CREATION", nullable=false)
	private String siteCreation;


	/**
	 * signatureCreation
	 */
	@Column(name="SSIGNATURE_CREATION", nullable=false)
	private String signatureCreation;


	/**
	 * dateModification
	 */
	@Column(name="DDATE_MODIFICATION")
	private Date dateModification;


	/**
	 * siteModification
	 */
	@Column(name="SSITE_MODIFICATION")
	private String siteModification;


	/**
	 * signatureModification
	 */
	@Column(name="SSIGNATURE_MODIFICATION")
	private String signatureModification;


	public RefComPrefGroup() {
		super();
	}


	public RefComPrefGroup(RefComPrefGroupId refComPrefGroupId, Date dateCreation, String siteCreation,
						   String signatureCreation, Date dateModification, String siteModification, String signatureModification) {
		super();
		this.refComPrefGroupId = refComPrefGroupId;
		this.dateCreation = dateCreation;
		this.siteCreation = siteCreation;
		this.signatureCreation = signatureCreation;
		this.dateModification = dateModification;
		this.siteModification = siteModification;
		this.signatureModification = signatureModification;
	}


	public RefComPrefGroupId getRefComPrefGroupId() {
		return refComPrefGroupId;
	}


	public void setRefComPrefGroupId(RefComPrefGroupId refComPrefGroupId) {
		this.refComPrefGroupId = refComPrefGroupId;
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
		return "RefComPrefGroup [refComPrefGroupId=" + refComPrefGroupId + ", dateCreation=" + dateCreation
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
		result = prime * result + ((refComPrefGroupId == null) ? 0 : refComPrefGroupId.hashCode());
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
		RefComPrefGroup other = (RefComPrefGroup) obj;
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
		if (refComPrefGroupId == null) {
			if (other.refComPrefGroupId != null)
				return false;
		} else if (!refComPrefGroupId.equals(other.refComPrefGroupId))
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
