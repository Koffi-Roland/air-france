package com.airfrance.repindutf8.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Utf generated by hbm2java
 */
@Entity
@Table(name = "UTF", schema = "SIC_UTF8")
public class Utf implements java.io.Serializable {

	private Long utfId;
	private RefUtfType refUtfType;
	private String sgin;
	private Date ddateCreation;
	private String ssiteCreation;
	private String ssignatureCreation;
	private Date ddateModification;
	private String ssiteModification;
	private String ssignatureModification;
	private Set<UtfData> utfDatas = new HashSet<UtfData>(0);

	public Utf() {
	}

	public Utf(Long utfId, RefUtfType refUtfType, String sgin) {
		this.utfId = utfId;
		this.refUtfType = refUtfType;
		this.sgin = sgin;
	}

	public Utf(long utfId, RefUtfType refUtfType, String sgin, Date ddateCreation, String ssiteCreation,
			String ssignatureCreation, Date ddateModification, String ssiteModification, String ssignatureModification,
			Set<UtfData> utfDatas) {
		this.utfId = utfId;
		this.refUtfType = refUtfType;
		this.sgin = sgin;
		this.ddateCreation = ddateCreation;
		this.ssiteCreation = ssiteCreation;
		this.ssignatureCreation = ssignatureCreation;
		this.ddateModification = ddateModification;
		this.ssiteModification = ssiteModification;
		this.ssignatureModification = ssignatureModification;
		this.utfDatas = utfDatas;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_UTF")
    @SequenceGenerator(name="SEQ_UTF", sequenceName = "SEQ_UTF",  allocationSize = 0)
	@Column(name = "UTF_ID", unique = true, nullable = false, precision = 12, scale = 0)
	public Long getUtfId() {
		return this.utfId;
	}

	public void setUtfId(Long utfId) {
		this.utfId = utfId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "STYPE", nullable = false)
	public RefUtfType getRefUtfType() {
		return this.refUtfType;
	}

	public void setRefUtfType(RefUtfType refUtfType) {
		this.refUtfType = refUtfType;
	}

	@Column(name = "SGIN", nullable = false, length = 12)
	public String getSgin() {
		return this.sgin;
	}

	public void setSgin(String sgin) {
		this.sgin = sgin;
	}

//	@Temporal(TemporalType.DATE)
//	@Column(name = "DDATE_CREATION", length = 7)
	@Column(name = "DDATE_CREATION")
	public Date getDdateCreation() {
		return this.ddateCreation;
	}

	public void setDdateCreation(Date ddateCreation) {
		this.ddateCreation = ddateCreation;
	}

	@Column(name = "SSITE_CREATION", length = 10)
	public String getSsiteCreation() {
		return this.ssiteCreation;
	}

	public void setSsiteCreation(String ssiteCreation) {
		this.ssiteCreation = ssiteCreation;
	}

	@Column(name = "SSIGNATURE_CREATION", length = 16)
	public String getSsignatureCreation() {
		return this.ssignatureCreation;
	}

	public void setSsignatureCreation(String ssignatureCreation) {
		this.ssignatureCreation = ssignatureCreation;
	}

//	@Temporal(TemporalType.DATE)
//	@Column(name = "DDATE_MODIFICATION", length = 7)
	@Column(name = "DDATE_MODIFICATION")
	public Date getDdateModification() {
		return this.ddateModification;
	}

	public void setDdateModification(Date ddateModification) {
		this.ddateModification = ddateModification;
	}

	@Column(name = "SSITE_MODIFICATION", length = 10)
	public String getSsiteModification() {
		return this.ssiteModification;
	}

	public void setSsiteModification(String ssiteModification) {
		this.ssiteModification = ssiteModification;
	}

	@Column(name = "SSIGNATURE_MODIFICATION", length = 16)
	public String getSsignatureModification() {
		return this.ssignatureModification;
	}

	public void setSsignatureModification(String ssignatureModification) {
		this.ssignatureModification = ssignatureModification;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "utf")
	public Set<UtfData> getUtfDatas() {
		return this.utfDatas;
	}

	public void setUtfDatas(Set<UtfData> utfDatas) {
		this.utfDatas = utfDatas;
	}

}
