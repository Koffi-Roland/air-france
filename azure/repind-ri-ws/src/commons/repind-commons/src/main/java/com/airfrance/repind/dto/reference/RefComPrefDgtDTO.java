package com.airfrance.repind.dto.reference;




import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;



public class RefComPrefDgtDTO implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	/**
     * refComPrefDgtId
     */
	private Integer refComPrefDgtId;
    
    
    /**
     * domain
     */
    private RefComPrefDomainDTO domain;
    
    
    /**
     * groupType
     */
    private RefComPrefGTypeDTO groupType;
    
    
    /**
     * type
     */
    private RefComPrefTypeDTO type; 
	
	
	/**
     * refComPrefMls
     */
    private List<RefComPrefMlDTO> refComPrefMls;
	
	
    /**
     * description
     */
    private String description;

        
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


	public RefComPrefDgtDTO() {
		super();
	}


	public RefComPrefDgtDTO(Integer refComPrefDgtId, RefComPrefDomainDTO domain, RefComPrefGTypeDTO groupType,
			RefComPrefTypeDTO type, List<RefComPrefMlDTO> refComPrefMls, String description, Date dateCreation,
			String siteCreation, String signatureCreation, Date dateModification, String siteModification,
			String signatureModification) {
		super();
		this.refComPrefDgtId = refComPrefDgtId;
		this.domain = domain;
		this.groupType = groupType;
		this.type = type;
		this.refComPrefMls = refComPrefMls;
		this.description = description;
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


	public RefComPrefDomainDTO getDomain() {
		return domain;
	}


	public void setDomain(RefComPrefDomainDTO domain) {
		this.domain = domain;
	}


	public RefComPrefGTypeDTO getGroupType() {
		return groupType;
	}


	public void setGroupType(RefComPrefGTypeDTO groupType) {
		this.groupType = groupType;
	}


	public RefComPrefTypeDTO getType() {
		return type;
	}


	public void setType(RefComPrefTypeDTO type) {
		this.type = type;
	}


	public List<RefComPrefMlDTO> getRefComPrefMls() {
		if (refComPrefMls == null) {
			return new ArrayList<RefComPrefMlDTO>();
		}
		return refComPrefMls;
	}


	public void setRefComPrefMls(List<RefComPrefMlDTO> refComPrefMls) {
		this.refComPrefMls = refComPrefMls;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
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
		return "RefComPrefDgtDTO [refComPrefDgtId=" + refComPrefDgtId + ", domain=" + domain + ", groupType="
				+ groupType + ", type=" + type + ", refComPrefMls=" + refComPrefMls + ", description=" + description
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
		RefComPrefDgtDTO other = (RefComPrefDgtDTO) obj;
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
