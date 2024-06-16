package com.afklm.rigui.entity.external;

/*PROTECTED REGION ID(_bII4E04kEeS-eLH--0fARw i) ENABLED START*/

// add not generated imports here

import org.hibernate.annotations.ForeignKey;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/*PROTECTED REGION END*/

/**
 * <p>
 * Title : ExternalIdentifierData.java
 * </p>
 * BO ExternalIdentifierData
 * <p>
 * Copyright : Copyright (c) 2009
 * </p>
 * <p>
 * Company : AIRFRANCE
 * </p>
 */

@Entity
@Table(name = "EXTERNAL_IDENTIFIER_DATA")
public class ExternalIdentifierData implements Serializable {

	/* PROTECTED REGION ID(serialUID _bII4E04kEeS-eLH--0fARw) ENABLED START */
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
	 * identifierDataId
	 */
	@Id
	@Column(name = "IDENTIFIER_DATA_ID")
	@SequenceGenerator(name = "SEQ_EXTERNAL_IDENTIFIER_DATA", sequenceName = "SEQ_EXTERNAL_IDENTIFIER_DATA",
			allocationSize = 1)
	@GeneratedValue(generator = "SEQ_EXTERNAL_IDENTIFIER_DATA")
	private Long identifierDataId;

	/**
	 * key
	 */
	@Column(name = "KEY")
	private String key;

	/**
	 * value
	 */
	@Column(name = "VALUE")
	private String value;

	/**
	 * creationDate
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATION_DATE")
	private Date creationDate;

	/**
	 * creationSignature
	 */
	@Column(name = "CREATION_SIGNATURE")
	private String creationSignature;

	/**
	 * creationSite
	 */
	@Column(name = "CREATION_SITE")
	private String creationSite;

	/**
	 * modificationDate
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "MODIFICATION_DATE")
	private Date modificationDate;

	/**
	 * modificationSignature
	 */
	@Column(name = "MODIFICATION_SIGNATURE")
	private String modificationSignature;

	/**
	 * modificationSite
	 */
	@Column(name = "MODIFICATION_SITE")
	private String modificationSite;

	/**
	 * externalIdentifier
	 */
	// * <-> 1
	@ManyToOne()
	@JoinColumn(name="IDENTIFIER_ID", nullable=false, foreignKey = @javax.persistence.ForeignKey(name = "FK_EXTERNAL_IDENTIFIER_DATA_IDENTIFIER_ID_IDENTIFIER_ID"))
	private ExternalIdentifier externalIdentifier;

	/* PROTECTED REGION ID(_bII4E04kEeS-eLH--0fARw u var) ENABLED START */
	// add your custom variables here if necessary
	/* PROTECTED REGION END */

	/**
	 * default constructor
	 */
	public ExternalIdentifierData() {
	}

	/**
	 * full constructor
	 *
	 * @param pIdentifierDataId      identifierDataId
	 * @param pKey                   key
	 * @param pValue                 value
	 * @param pCreationDate          creationDate
	 * @param pCreationSignature     creationSignature
	 * @param pCreationSite          creationSite
	 * @param pModificationDate      modificationDate
	 * @param pModificationSignature modificationSignature
	 * @param pModificationSite      modificationSite
	 */
	public ExternalIdentifierData(Long pIdentifierDataId, String pKey, String pValue, Date pCreationDate,
								  String pCreationSignature, String pCreationSite, Date pModificationDate, String pModificationSignature,
								  String pModificationSite) {
		this.identifierDataId = pIdentifierDataId;
		this.key = pKey;
		this.value = pValue;
		this.creationDate = pCreationDate;
		this.creationSignature = pCreationSignature;
		this.creationSite = pCreationSite;
		this.modificationDate = pModificationDate;
		this.modificationSignature = pModificationSignature;
		this.modificationSite = pModificationSite;
	}

	/**
	 *
	 * @return creationDate
	 */
	public Date getCreationDate() {
		return this.creationDate;
	}

	/**
	 *
	 * @param pCreationDate creationDate value
	 */
	public void setCreationDate(Date pCreationDate) {
		this.creationDate = pCreationDate;
	}

	/**
	 *
	 * @return creationSignature
	 */
	public String getCreationSignature() {
		return this.creationSignature;
	}

	/**
	 *
	 * @param pCreationSignature creationSignature value
	 */
	public void setCreationSignature(String pCreationSignature) {
		this.creationSignature = pCreationSignature;
	}

	/**
	 *
	 * @return creationSite
	 */
	public String getCreationSite() {
		return this.creationSite;
	}

	/**
	 *
	 * @param pCreationSite creationSite value
	 */
	public void setCreationSite(String pCreationSite) {
		this.creationSite = pCreationSite;
	}

	/**
	 *
	 * @return externalIdentifier
	 */
	public ExternalIdentifier getExternalIdentifier() {
		return this.externalIdentifier;
	}

	/**
	 *
	 * @param pExternalIdentifier externalIdentifier value
	 */
	public void setExternalIdentifier(ExternalIdentifier pExternalIdentifier) {
		this.externalIdentifier = pExternalIdentifier;
	}

	/**
	 *
	 * @return identifierDataId
	 */
	public Long getIdentifierDataId() {
		return this.identifierDataId;
	}

	/**
	 *
	 * @param pIdentifierDataId identifierDataId value
	 */
	public void setIdentifierDataId(Long pIdentifierDataId) {
		this.identifierDataId = pIdentifierDataId;
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
	 * @return modificationDate
	 */
	public Date getModificationDate() {
		return this.modificationDate;
	}

	/**
	 *
	 * @param pModificationDate modificationDate value
	 */
	public void setModificationDate(Date pModificationDate) {
		this.modificationDate = pModificationDate;
	}

	/**
	 *
	 * @return modificationSignature
	 */
	public String getModificationSignature() {
		return this.modificationSignature;
	}

	/**
	 *
	 * @param pModificationSignature modificationSignature value
	 */
	public void setModificationSignature(String pModificationSignature) {
		this.modificationSignature = pModificationSignature;
	}

	/**
	 *
	 * @return modificationSite
	 */
	public String getModificationSite() {
		return this.modificationSite;
	}

	/**
	 *
	 * @param pModificationSite modificationSite value
	 */
	public void setModificationSite(String pModificationSite) {
		this.modificationSite = pModificationSite;
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
	 * @return object as string
	 */
	public String toString() {
		String result = null;
		/* PROTECTED REGION ID(toString_bII4E04kEeS-eLH--0fARw) ENABLED START */
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
		buffer.append("identifierDataId=").append(getIdentifierDataId());
		buffer.append(",");
		buffer.append("key=").append(getKey());
		buffer.append(",");
		buffer.append("value=").append(getValue());
		buffer.append(",");
		buffer.append("creationDate=").append(getCreationDate());
		buffer.append(",");
		buffer.append("creationSignature=").append(getCreationSignature());
		buffer.append(",");
		buffer.append("creationSite=").append(getCreationSite());
		buffer.append(",");
		buffer.append("modificationDate=").append(getModificationDate());
		buffer.append(",");
		buffer.append("modificationSignature=").append(getModificationSignature());
		buffer.append(",");
		buffer.append("modificationSite=").append(getModificationSite());
		buffer.append("]");
		return buffer.toString();
	}

	/* PROTECTED REGION ID(equals hash _bII4E04kEeS-eLH--0fARw) ENABLED START */

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

	/* PROTECTED REGION ID(_bII4E04kEeS-eLH--0fARw u m) ENABLED START */
	// add your custom methods here if necessary
	/* PROTECTED REGION END */

}
