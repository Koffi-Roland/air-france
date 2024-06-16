package com.airfrance.repind.entity.reference;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.ForeignKey;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;


@Entity
@Table(name="REF_COMPREF_DGT")
public class RefComPrefDgt implements Serializable {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
       

	/**
     * refComPrefDgtId
     */
    @Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_REF_COMPREF_DGT")
    @SequenceGenerator(name="SEQ_REF_COMPREF_DGT", sequenceName = "SEQ_REF_COMPREF_DGT",
			allocationSize = 1)
    @Column(name="REF_COMPREF_DGT_ID", length=12, nullable=false)
    private Integer refComPrefDgtId;
    
    
    /**
     * domain
     */
    @OneToOne()
    @JoinColumn(name="DOMAIN", nullable=false)
    @ForeignKey(name = "FK_REF_COMPREF_DOMAIN")
    private RefComPrefDomain domain;
    
    
    /**
     * groupType
     */
    @OneToOne()
    @JoinColumn(name="GROUP_TYPE", nullable=false)
    @ForeignKey(name = "FK_REF_COMPREF_GROUP_TYPE")
    private RefComPrefGType groupType;
    
    
    /**
     * type
     */
    @OneToOne()
    @JoinColumn(name="TYPE", nullable=false)
    @ForeignKey(name = "FK_REF_COMPREF_TYPE")
    private RefComPrefType type;    
    
    
    /**
     * description
     */
    @Column(name="DESCRIPTION", length=50, nullable=false)
    private String description;
    
    
    /**
     * refComPrefMls
     */
    @OneToMany(mappedBy="refComPrefDgt", cascade=CascadeType.ALL, fetch=FetchType.LAZY)
    @Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
    private List<RefComPrefMl> refComPrefMls;
    
    
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
    @Column(name="DDATE_MODIFICATION", nullable=false)
    private Date dateModification;
    
    
    /**
     * siteModification
     */
    @Column(name="SSITE_MODIFICATION", nullable=false)
    private String siteModification;
    
    
    /**
     * signatureModification
     */
    @Column(name="SSIGNATURE_MODIFICATION", nullable=false)
    private String signatureModification;


	public RefComPrefDgt() {
		super();
	}


	public RefComPrefDgt(Integer refComPrefDgtId, RefComPrefDomain domain, RefComPrefGType groupType,
			RefComPrefType type, String description, List<RefComPrefMl> refComPrefMls, Date dateCreation,
			String siteCreation, String signatureCreation, Date dateModification, String siteModification,
			String signatureModification) {
		super();
		this.refComPrefDgtId = refComPrefDgtId;
		this.domain = domain;
		this.groupType = groupType;
		this.type = type;
		this.description = description;
		this.refComPrefMls = refComPrefMls;
		this.dateCreation = dateCreation;
		this.siteCreation = siteCreation;
		this.signatureCreation = signatureCreation;
		this.dateModification = dateModification;
		this.siteModification = siteModification;
		this.signatureModification = signatureModification;
	}


	public Integer getRefComPrefDgtId() {
		return refComPrefDgtId;
	}


	public void setRefComPrefDgtId(Integer refComPrefDgtId) {
		this.refComPrefDgtId = refComPrefDgtId;
	}


	public RefComPrefDomain getDomain() {
		return domain;
	}


	public void setDomain(RefComPrefDomain domain) {
		this.domain = domain;
	}


	public RefComPrefGType getGroupType() {
		return groupType;
	}


	public void setGroupType(RefComPrefGType groupType) {
		this.groupType = groupType;
	}


	public RefComPrefType getType() {
		return type;
	}


	public void setType(RefComPrefType type) {
		this.type = type;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public List<RefComPrefMl> getRefComPrefMls() {
		return refComPrefMls;
	}


	public void setRefComPrefMls(List<RefComPrefMl> refComPrefMls) {
		this.refComPrefMls = refComPrefMls;
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
		return "RefComPrefDgt [refComPrefDgtId=" + refComPrefDgtId + ", domain=" + domain + ", groupType=" + groupType
				+ ", type=" + type + ", description=" + description + ", refComPrefMls=" + refComPrefMls
				+ ", dateCreation=" + dateCreation + ", siteCreation=" + siteCreation + ", signatureCreation="
				+ signatureCreation + ", dateModification=" + dateModification + ", siteModification="
				+ siteModification + ", signatureModification=" + signatureModification + "]";
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dateCreation == null) ? 0 : dateCreation.hashCode());
		result = prime * result + ((dateModification == null) ? 0 : dateModification.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((domain == null) ? 0 : domain.hashCode());
		result = prime * result + ((groupType == null) ? 0 : groupType.hashCode());
		result = prime * result + ((refComPrefDgtId == null) ? 0 : refComPrefDgtId.hashCode());
		result = prime * result + ((refComPrefMls == null) ? 0 : refComPrefMls.hashCode());
		result = prime * result + ((signatureCreation == null) ? 0 : signatureCreation.hashCode());
		result = prime * result + ((signatureModification == null) ? 0 : signatureModification.hashCode());
		result = prime * result + ((siteCreation == null) ? 0 : siteCreation.hashCode());
		result = prime * result + ((siteModification == null) ? 0 : siteModification.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		RefComPrefDgt other = (RefComPrefDgt) obj;
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
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (domain == null) {
			if (other.domain != null)
				return false;
		} else if (!domain.equals(other.domain))
			return false;
		if (groupType == null) {
			if (other.groupType != null)
				return false;
		} else if (!groupType.equals(other.groupType))
			return false;
		if (refComPrefDgtId == null) {
			if (other.refComPrefDgtId != null)
				return false;
		} else if (!refComPrefDgtId.equals(other.refComPrefDgtId))
			return false;
		if (refComPrefMls == null) {
			if (other.refComPrefMls != null)
				return false;
		} else if (!refComPrefMls.equals(other.refComPrefMls))
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
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}
}


