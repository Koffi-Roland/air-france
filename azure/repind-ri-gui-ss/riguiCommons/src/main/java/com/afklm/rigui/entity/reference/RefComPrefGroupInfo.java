package com.afklm.rigui.entity.reference;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


@Entity
@Table(name="REF_COMPREF_GROUP_INFO")
public class RefComPrefGroupInfo implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;


	/**
	 * id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_REF_COMPREF_GROUP_INFO")
	@SequenceGenerator(name="SEQ_REF_COMPREF_GROUP_INFO", sequenceName = "SEQ_REF_COMPREF_GROUP_INFO", allocationSize = 1)
	@Column(name="REF_COMPREF_GROUP_INFO_ID", length=12, nullable=false)
	private Integer id;


	/**
	 * code
	 */
	@Column(name = "SCODE", length=30, nullable=false)
	private String code;


	/**
	 * libelleFR
	 */
	@Column(name="SLIBELLE_FR", length=100)
	private String libelleFR;


	/**
	 * libelleEN
	 */
	@Column(name="SLIBELLE_EN", length=100)
	private String libelleEN;


	/**
	 * mandatoryOption
	 */
	@Column(name="SMANDATORY_OPTIN", nullable=false)
	private String mandatoryOption;


	/**
	 * defaultOption
	 */
	@Column(name="SDEFAULT_OPTIN", nullable=false)
	private String defaultOption;


	/**
	 * dateCreation
	 */
	@Column(name="DDATE_CREATION", nullable=false)
	private Date dateCreation;


	/**
	 * siteCreation
	 */
	@Column(name="SSITE_CREATION", length=10, nullable=false)
	private String siteCreation;


	/**
	 * signatureCreation
	 */
	@Column(name="SSIGNATURE_CREATION", length=16, nullable=false)
	private String signatureCreation;


	/**
	 * dateModification
	 */
	@Column(name="DDATE_MODIFICATION")
	private Date dateModification;


	/**
	 * siteModification
	 */
	@Column(name="SSITE_MODIFICATION", length=10)
	private String siteModification;


	/**
	 * signatureModification
	 */
	@Column(name="SSIGNATURE_MODIFICATION", length=16)
	private String signatureModification;


	public RefComPrefGroupInfo() {
		super();
	}


	public RefComPrefGroupInfo(Integer id, String code, String libelleFR, String libelleEN, String mandatoryOption,
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
		return "RefComPrefGroupInfo [id=" + id + ", code=" + code + ", libelleFR=" + libelleFR + ", libelleEN="
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
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		RefComPrefGroupInfo other = (RefComPrefGroupInfo) obj;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}






}


