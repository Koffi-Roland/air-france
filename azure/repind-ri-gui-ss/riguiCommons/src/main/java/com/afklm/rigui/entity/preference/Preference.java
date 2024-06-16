package com.afklm.rigui.entity.preference;

/*PROTECTED REGION ID(_UAaPcE2SEeaViNKBargFUw i) ENABLED START*/

// add not generated imports here

import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/*PROTECTED REGION END*/

/**
 * <p>
 * Title : Preference.java
 * </p>
 * BO Preference
 * <p>
 * Copyright : Copyright (c) 2009
 * </p>
 * <p>
 * Company : AIRFRANCE
 * </p>
 */
@Entity

@Table(name = "PREFERENCE")
public class Preference implements Serializable {

	/* PROTECTED REGION ID(serialUID _UAaPcE2SEeaViNKBargFUw) ENABLED START */
	/**
	 * Determines if a de-serialized file is compatible with this class.
	 *
	 * Maintainers must change this value if and only if the new version of this
	 * class is not compatible with old versions. See Sun docs for <a
	 * href=http://java.sun.com/j2se/1.5.0/docs/guide/serialization
	 * /serialization/spec/class.html#4100> details. </a>
	 *
	 * Not necessary to include in first version of the class, but included here as
	 * a reminder of its importance.
	 */
	private static final long serialVersionUID = 1L;
	/* PROTECTED REGION END */

	/**
	 * preferenceId
	 *
	 */
	@Id
	@Column(name = "PREFERENCE_ID")
	@SequenceGenerator(name = "SEQ_PREFERENCE", sequenceName = "SEQ_PREFERENCE",
			allocationSize = 1)
	@GeneratedValue(generator = "SEQ_PREFERENCE")
	private Long preferenceId;

	/**
	 * gin
	 */
	@Column(name = "SGIN", length = 12, nullable = false)
	private String gin;

	/**
	 * type
	 */
	@Column(name = "STYPE", length = 4, nullable = false)
	private String type;

	/**
	 * link
	 *
	 */
	@Column(name = "ILINK", length = 12)
	private Integer link;

	/**
	 * dateCreation
	 */
	@Column(name = "DDATE_CREATION")
	private Date dateCreation;

	/**
	 * signatureCreation
	 */
	@Column(name = "SSIGNATURE_CREATION", length = 16)
	private String signatureCreation;

	/**
	 * siteCreation
	 */
	@Column(name = "SSITE_CREATION", length = 10)
	private String siteCreation;

	/**
	 * dateModification
	 */
	@Column(name = "DDATE_MODIFICATION")
	private Date dateModification;

	/**
	 * signatureModification
	 */
	@Column(name = "SSIGNATURE_MODIFICATION", length = 16)
	private String signatureModification;

	/**
	 * siteModification
	 */
	@Column(name = "SSITE_MODIFICATION", length = 10)
	private String siteModification;

	/**
	 * preferenceData
	 */
	// 1 <-> *
	@OneToMany(mappedBy = "preference", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval=true)
	private Set<PreferenceData> preferenceData;

	/* PROTECTED REGION ID(_UAaPcE2SEeaViNKBargFUw u var) ENABLED START */
	// add your custom variables here if necessary
	/* PROTECTED REGION END */

	/**
	 * default constructor
	 */
	public Preference() {
		// empty constructor
	}

	/**
	 * full constructor
	 *
	 * @param pPreferenceId          preferenceId
	 * @param pGin                   gin
	 * @param pType                  type
	 * @param pLink                  link
	 * @param pDateCreation          dateCreation
	 * @param pSignatureCreation     signatureCreation
	 * @param pSiteCreation          siteCreation
	 * @param pDateModification      dateModification
	 * @param pSignatureModification signatureModification
	 * @param pSiteModification      siteModification
	 */
	public Preference(Long pPreferenceId, String pGin, String pType, Integer pLink, Date pDateCreation,
					  String pSignatureCreation, String pSiteCreation, Date pDateModification, String pSignatureModification,
					  String pSiteModification) {
		this.preferenceId = pPreferenceId;
		this.gin = pGin;
		this.type = pType;
		this.link = pLink;
		this.dateCreation = pDateCreation;
		this.signatureCreation = pSignatureCreation;
		this.siteCreation = pSiteCreation;
		this.dateModification = pDateModification;
		this.signatureModification = pSignatureModification;
		this.siteModification = pSiteModification;
	}

	/**
	 *
	 * @return dateCreation
	 */
	public Date getDateCreation() {
		return this.dateCreation;
	}

	/**
	 *
	 * @param pDateCreation dateCreation value
	 */
	public void setDateCreation(Date pDateCreation) {
		this.dateCreation = pDateCreation;
	}

	/**
	 *
	 * @return dateModification
	 */
	public Date getDateModification() {
		return this.dateModification;
	}

	/**
	 *
	 * @param pDateModification dateModification value
	 */
	public void setDateModification(Date pDateModification) {
		this.dateModification = pDateModification;
	}

	/**
	 *
	 * @return gin
	 */
	public String getGin() {
		return this.gin;
	}

	/**
	 *
	 * @param pGin gin value
	 */
	public void setGin(String pGin) {
		this.gin = pGin;
	}

	/**
	 *
	 * @return link
	 */
	public Integer getLink() {
		return this.link;
	}

	/**
	 *
	 * @param pLink link value
	 */
	public void setLink(Integer pLink) {
		this.link = pLink;
	}

	/**
	 *
	 * @return preferenceData
	 */
	public Set<PreferenceData> getPreferenceData() {
		return this.preferenceData;
	}

	/**
	 *
	 * @param pPreferenceData preferenceData value
	 */
	public void setPreferenceData(Set<PreferenceData> pPreferenceData) {
		this.preferenceData = pPreferenceData;
	}

	/**
	 *
	 * @return preferenceId
	 */
	public Long getPreferenceId() {
		return this.preferenceId;
	}

	/**
	 *
	 * @param pPreferenceId preferenceId value
	 */
	public void setPreferenceId(Long pPreferenceId) {
		this.preferenceId = pPreferenceId;
	}

	/**
	 *
	 * @return signatureCreation
	 */
	public String getSignatureCreation() {
		return this.signatureCreation;
	}

	/**
	 *
	 * @param pSignatureCreation signatureCreation value
	 */
	public void setSignatureCreation(String pSignatureCreation) {
		this.signatureCreation = pSignatureCreation;
	}

	/**
	 *
	 * @return signatureModification
	 */
	public String getSignatureModification() {
		return this.signatureModification;
	}

	/**
	 *
	 * @param pSignatureModification signatureModification value
	 */
	public void setSignatureModification(String pSignatureModification) {
		this.signatureModification = pSignatureModification;
	}

	/**
	 *
	 * @return siteCreation
	 */
	public String getSiteCreation() {
		return this.siteCreation;
	}

	/**
	 *
	 * @param pSiteCreation siteCreation value
	 */
	public void setSiteCreation(String pSiteCreation) {
		this.siteCreation = pSiteCreation;
	}

	/**
	 *
	 * @return siteModification
	 */
	public String getSiteModification() {
		return this.siteModification;
	}

	/**
	 *
	 * @param pSiteModification siteModification value
	 */
	public void setSiteModification(String pSiteModification) {
		this.siteModification = pSiteModification;
	}

	/**
	 *
	 * @return type
	 */
	public String getType() {
		return this.type;
	}

	/**
	 *
	 * @param pType type value
	 */
	public void setType(String pType) {
		this.type = pType;
	}

	/**
	 *
	 * @return object as string
	 */
	@Override
	public String toString() {
		/* PROTECTED REGION ID(toString_UAaPcE2SEeaViNKBargFUw) ENABLED START */
		String result = toStringImpl();
		/* PROTECTED REGION END */
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
		buffer.append("preferenceId=").append(getPreferenceId());
		buffer.append(",");
		buffer.append("gin=").append(getGin());
		buffer.append(",");
		buffer.append("type=").append(getType());
		buffer.append(",");
		buffer.append("link=").append(getLink());
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

	/* PROTECTED REGION ID(equals hash _UAaPcE2SEeaViNKBargFUw) ENABLED START */

	/**
	 * {@inheritDoc}
	 *
	 * @see Object#hashCode()
	 */
	@Override
	public int hashCode() {
		// TODO Customize here if necessary
		return hashCodeImpl();
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see Object#equals(Object)
	 */
	@Override
	public boolean equals(Object obj) {
		// TODO Customize here if necessary
		return equalsImpl(obj);
	}

	/* PROTECTED REGION END */

	/**
	 * Generated implementation method for hashCode You can stop calling it in the
	 * hashCode() generated in protected region if necessary
	 *
	 * @return hashcode
	 */
	private int hashCodeImpl() {
		return super.hashCode();
	}

	/**
	 * Generated implementation method for equals You can stop calling it in the
	 * equals() generated in protected region if necessary
	 *
	 * @return if param equals the current object
	 * @param obj Object to compare with current
	 */
	private boolean equalsImpl(Object obj) {
		return super.equals(obj);
	}

	/* PROTECTED REGION ID(_UAaPcE2SEeaViNKBargFUw u m) ENABLED START */
	// add your custom methods here if necessary
	/* PROTECTED REGION END */
}
