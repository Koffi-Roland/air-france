package com.afklm.rigui.dto.reference;




import java.io.Serializable;
import java.util.Date;



public class RefComPrefGroupInfoDTO implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	/**
     * id
     */
    private Integer id;
	
    
    /**
     * code
     */
    private String code;
        
    
    /**
     * libelleFR
     */
    private String libelleFR;
    
    
    /**
     * libelleEN
     */
    private String libelleEN;
    
    
    /**
     * mandatoryOption
     */
    private String mandatoryOption;
    
    
    /**
     * defaultOption
     */
    private String defaultOption;
    
    
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


	public RefComPrefGroupInfoDTO() {
		super();
	}


	public RefComPrefGroupInfoDTO(Integer id, String code, String libelleFR, String libelleEN, String mandatoryOption,
			String defaultOption, Date dateCreation, String siteCreation, String signatureCreation,
			Date dateModification, String siteModification, String signatureModification) {
		super();
		this.id = id;
		this.code = code;
		this.libelleFR = libelleFR;
		this.libelleEN = libelleEN;
		this.mandatoryOption = mandatoryOption;
		this.defaultOption = defaultOption;
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


	public String getCode() {
		return code;
	}


	public void setCode(String code) {
		this.code = code;
	}


	public String getLibelleFR() {
		return libelleFR;
	}


	public void setLibelleFR(String libelleFR) {
		this.libelleFR = libelleFR;
	}


	public String getLibelleEN() {
		return libelleEN;
	}


	public void setLibelleEN(String libelleEN) {
		this.libelleEN = libelleEN;
	}


	public String getMandatoryOption() {
		return mandatoryOption;
	}


	public void setMandatoryOption(String mandatoryOption) {
		this.mandatoryOption = mandatoryOption;
	}


	public String getDefaultOption() {
		return defaultOption;
	}


	public void setDefaultOption(String defaultOption) {
		this.defaultOption = defaultOption;
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
		return "RefComPrefGroupInfoDTO [id=" + id + ", code=" + code + ", libelleFR=" + libelleFR + ", libelleEN="
				+ libelleEN + ", mandatoryOption=" + mandatoryOption + ", defaultOption=" + defaultOption
				+ ", dateCreation=" + dateCreation + ", siteCreation=" + siteCreation + ", signatureCreation="
				+ signatureCreation + ", dateModification=" + dateModification + ", siteModification="
				+ siteModification + ", signatureModification=" + signatureModification + "]";
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result + ((dateCreation == null) ? 0 : dateCreation.hashCode());
		result = prime * result + ((dateModification == null) ? 0 : dateModification.hashCode());
		result = prime * result + ((defaultOption == null) ? 0 : defaultOption.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((libelleEN == null) ? 0 : libelleEN.hashCode());
		result = prime * result + ((libelleFR == null) ? 0 : libelleFR.hashCode());
		result = prime * result + ((mandatoryOption == null) ? 0 : mandatoryOption.hashCode());
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
		RefComPrefGroupInfoDTO other = (RefComPrefGroupInfoDTO) obj;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
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
		if (defaultOption == null) {
			if (other.defaultOption != null)
				return false;
		} else if (!defaultOption.equals(other.defaultOption))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (libelleEN == null) {
			if (other.libelleEN != null)
				return false;
		} else if (!libelleEN.equals(other.libelleEN))
			return false;
		if (libelleFR == null) {
			if (other.libelleFR != null)
				return false;
		} else if (!libelleFR.equals(other.libelleFR))
			return false;
		if (mandatoryOption == null) {
			if (other.mandatoryOption != null)
				return false;
		} else if (!mandatoryOption.equals(other.mandatoryOption))
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
