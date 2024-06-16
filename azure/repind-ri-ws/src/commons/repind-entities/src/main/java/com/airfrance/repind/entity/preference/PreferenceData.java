package com.airfrance.repind.entity.preference;

/*PROTECTED REGION ID(_2i-3QE0dEeesvPXCiPqpnw i) ENABLED START*/

// add not generated imports here

import org.hibernate.annotations.ForeignKey;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/*PROTECTED REGION END*/

/**
 * <p>
 * Title : PreferenceData.java
 * </p>
 * BO PreferenceData
 * <p>
 * Copyright : Copyright (c) 2009
 * </p>
 * <p>
 * Company : AIRFRANCE
 * </p>
 */
@Entity

@Table(name = "PREFERENCE_DATA")
public class PreferenceData implements Serializable {

	/* PROTECTED REGION ID(serialUID _2i-3QE0dEeesvPXCiPqpnw) ENABLED START */
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
	 * preferenceDataId
	 */
	@Id
	@Column(name = "PREFERENCE_DATA_ID")
	@SequenceGenerator(name = "SEQ_PREFERENCE_DATA", sequenceName = "SEQ_PREFERENCE_DATA",
			allocationSize = 1)
	@GeneratedValue(generator = "SEQ_PREFERENCE_DATA")
	private Long preferenceDataId;

	/**
	 * key
	 */
	@Column(name = "SKEY", length = 60, nullable = false)
	private String key;

	/**
	 * value
	 */
	@Column(name = "SVALUE", length = 256)
	private String value;

	
	
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
	 * preference
	 */
	// * <-> 1
	@ManyToOne()
	@JoinColumn(name = "PREFERENCE_ID", referencedColumnName = "PREFERENCE_ID", nullable = false)
	@ForeignKey(name = "FK_PREF_DATA_PREF_ID")
	private Preference preference;

	/* PROTECTED REGION ID(_2i-3QE0dEeesvPXCiPqpnw u var) ENABLED START */
	// add your custom variables here if necessary
	/* PROTECTED REGION END */

	/**
	 * default constructor
	 */
	public PreferenceData() {
		// empty constructor
	}

	/**
	 * full constructor
	 * 
	 * @param pPreferenceDataId preferenceDataId
	 * @param pKey              key
	 * @param pValue            value
	 */
	public PreferenceData(Long pPreferenceDataId, String pKey, String pValue) {
		this.preferenceDataId = pPreferenceDataId;
		this.key = pKey;
		this.value = pValue;
	}

	/**
	 *
	 * @return key
	 */
	public String getKey() {
		return this.key;
	}

	/**
	 *
	 * @param pKey key value
	 */
	public void setKey(String pKey) {
		this.key = pKey;
	}

	/**
	 *
	 * @return preference
	 */
	public Preference getPreference() {
		return this.preference;
	}

	/**
	 *
	 * @param pPreference preference value
	 */
	public void setPreference(Preference pPreference) {
		this.preference = pPreference;
	}

	/**
	 *
	 * @return preferenceDataId
	 */
	public Long getPreferenceDataId() {
		return this.preferenceDataId;
	}

	/**
	 *
	 * @param pPreferenceDataId preferenceDataId value
	 */
	public void setPreferenceDataId(Long pPreferenceDataId) {
		this.preferenceDataId = pPreferenceDataId;
	}

	/**
	 *
	 * @return value
	 */
	public String getValue() {
		return this.value;
	}

	/**
	 *
	 * @param pValue value value
	 */
	public void setValue(String pValue) {
		this.value = pValue;
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
	 * @return object as string
	 */
	public String toString() {
		String result = null;
		/* PROTECTED REGION ID(toString_2i-3QE0dEeesvPXCiPqpnw) ENABLED START */
		result = toStringImpl();
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
		buffer.append("preferenceDataId=").append(getPreferenceDataId());
		buffer.append(",");
		buffer.append("key=").append(getKey());
		buffer.append(",");
		buffer.append("value=").append(getValue());
		buffer.append("]");
		return buffer.toString();
	}

	/* PROTECTED REGION ID(equals hash _2i-3QE0dEeesvPXCiPqpnw) ENABLED START */

	/**
	 * {@inheritDoc}
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		// TODO Customize here if necessary
		return hashCodeImpl();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
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

	/* PROTECTED REGION ID(_2i-3QE0dEeesvPXCiPqpnw u m) ENABLED START */
	// add your custom methods here if necessary
	/* PROTECTED REGION END */
}
