package com.airfrance.repindutf8.dto.utf;

/*PROTECTED REGION ID(_oWasAEQ3EeeGNOeccGyf5g i) ENABLED START*/

// add not generated imports here
import java.io.Serializable;
import java.util.Date;

/*PROTECTED REGION END*/

/**
 * <p>
 * Title : UtfDataDTO
 * </p>
 *
 * <p>
 * Copyright : Copyright (c) 2009
 * </p>
 * <p>
 * Company : AIRFRANCE
 * </p>
 */
public class UtfDataDTO implements Serializable {

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

	/**
	 * utfDataId
	 */
	private Long utfDataId;

	/**
	 * utfId
	 */
	private Long utfId;

	/**
	 * key
	 */
	private String key;

	/**
	 * value
	 */
	private String value;

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

	/* PROTECTED REGION ID(_oWasAEQ3EeeGNOeccGyf5g u var) ENABLED START */
	// add your custom variables here if necessary

	/**
	 * signatureModification
	 */
	private String signatureModification;

	/* PROTECTED REGION END */

	/**
	 * default constructor
	 */
	public UtfDataDTO() {
		// empty constructor
	}

	/**
	 *
	 * @return dateCreation
	 */
	public Date getDateCreation() {
		return dateCreation;
	}

	/**
	 *
	 * @return dateModification
	 */
	public Date getDateModification() {
		return dateModification;
	}

	/**
	 *
	 * @return key
	 */
	public String getKey() {
		return key;
	}

	/**
	 *
	 * @return signatureCreation
	 */
	public String getSignatureCreation() {
		return signatureCreation;
	}

	/**
	 *
	 * @return signatureModification
	 */
	public String getSignatureModification() {
		return signatureModification;
	}

	/**
	 *
	 * @return siteCreation
	 */
	public String getSiteCreation() {
		return siteCreation;
	}

	/**
	 *
	 * @return siteModification
	 */
	public String getSiteModification() {
		return siteModification;
	}

	/**
	 *
	 * @return utfDataId
	 */
	public Long getUtfDataId() {
		return utfDataId;
	}

	/**
	 *
	 * @return utfId
	 */
	public Long getUtfId() {
		return utfId;
	}

	/**
	 *
	 * @return value
	 */
	public String getValue() {
		return value;
	}

	/**
	 *
	 * @param pDateCreation
	 *            dateCreation value
	 */
	public void setDateCreation(final Date pDateCreation) {
		dateCreation = pDateCreation;
	}

	/**
	 *
	 * @param pDateModification
	 *            dateModification value
	 */
	public void setDateModification(final Date pDateModification) {
		dateModification = pDateModification;
	}

	/**
	 *
	 * @param pKey
	 *            key value
	 */
	public void setKey(final String pKey) {
		key = pKey;
	}

	/**
	 *
	 * @param pSignatureCreation
	 *            signatureCreation value
	 */
	public void setSignatureCreation(final String pSignatureCreation) {
		signatureCreation = pSignatureCreation;
	}

	/**
	 *
	 * @param pSignatureModification
	 *            signatureModification value
	 */
	public void setSignatureModification(final String pSignatureModification) {
		signatureModification = pSignatureModification;
	}

	/**
	 *
	 * @param pSiteCreation
	 *            siteCreation value
	 */
	public void setSiteCreation(final String pSiteCreation) {
		siteCreation = pSiteCreation;
	}

	/**
	 *
	 * @param pSiteModification
	 *            siteModification value
	 */
	public void setSiteModification(final String pSiteModification) {
		siteModification = pSiteModification;
	}

	/**
	 *
	 * @param l
	 *            utfDataId value
	 */
	public void setUtfDataId(final Long l) {
		utfDataId = l;
	}

	/**
	 *
	 * @param l
	 *            utfId value
	 */
	public void setUtfId(final Long l) {
		utfId = l;
	}

	/**
	 *
	 * @param pValue
	 *            value value
	 */
	public void setValue(final String pValue) {
		value = pValue;
	}

	/**
	 *
	 * @return object as string
	 */
	@Override
	public String toString() {
		String result = null;
		/* PROTECTED REGION ID(toString_oWasAEQ3EeeGNOeccGyf5g) ENABLED START */
		result = toStringImpl();
		/* PROTECTED REGION END */
		return result;
	}

	/**
	 *
	 * @return object as string
	 */
	public String toStringImpl() {
		final StringBuffer buffer = new StringBuffer();
		buffer.append(getClass().getName());
		buffer.append("@").append(Integer.toHexString(System.identityHashCode(this)));
		buffer.append("[");
		buffer.append("utfDataId=").append(getUtfDataId());
		buffer.append(",");
		buffer.append("utfId=").append(getUtfId());
		buffer.append(",");
		buffer.append("key=").append(getKey());
		buffer.append(",");
		buffer.append("value=").append(getValue());
		buffer.append(",");
		buffer.append("dateCreation=").append(getDateCreation());
		buffer.append(",");
		buffer.append("siteCreation=").append(getSiteCreation());
		buffer.append(",");
		buffer.append("signatureCreation=").append(getSignatureCreation());
		buffer.append(",");
		buffer.append("dateModification=").append(getDateModification());
		buffer.append(",");
		buffer.append("siteModification=").append(getSiteModification());
		buffer.append(",");
		buffer.append("signatureModification=").append(getSignatureModification());
		buffer.append("]");
		return buffer.toString();
	}

	/* PROTECTED REGION ID(_oWasAEQ3EeeGNOeccGyf5g u m) ENABLED START */
	// add your custom methods here if necessary
	/* PROTECTED REGION END */

}
