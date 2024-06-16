package com.afklm.rigui.entity.external;

/*PROTECTED REGION ID(_bII4Ak4kEeS-eLH--0fARw i) ENABLED START*/

// add not generated imports here

import com.afklm.rigui.entity.individu.Individu;
import org.hibernate.annotations.ForeignKey;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/*PROTECTED REGION END*/

/**
 * <p>
 * Title : ExternalIdentifier.java
 * </p>
 * BO ExternalIdentifier
 * <p>
 * Copyright : Copyright (c) 2009
 * </p>
 * <p>
 * Company : AIRFRANCE
 * </p>
 */

@Entity
@Table(name = "EXTERNAL_IDENTIFIER")
public class ExternalIdentifier implements Serializable {

	/* PROTECTED REGION ID(serialUID _bII4Ak4kEeS-eLH--0fARw) ENABLED START */
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
	 * identifierId
	 */
	@Id
	@Column(name = "IDENTIFIER_ID")
	@SequenceGenerator(name = "SEQ_EXTERNAL_IDENTIFIER", sequenceName = "SEQ_EXTERNAL_IDENTIFIER",
			allocationSize = 1)
	@GeneratedValue(generator = "SEQ_EXTERNAL_IDENTIFIER")
	private Long identifierId;

	/**
	 * gin
	 */
	@Column(name = "SGIN", insertable = false, updatable = false)
	private String gin;

	/**
	 * identifier
	 */
	@Column(name = "IDENTIFIER")
	private String identifier;

	/**
	 * type
	 */
	@Column(name = "TYPE")
	private String type;

	/**
	 * lastSeenDate
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "LAST_SEEN_DATE")
	private Date lastSeenDate;

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
	 * externalIdentifierDataList
	 */
	// 1 <-> *
	@OneToMany(mappedBy = "externalIdentifier", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<ExternalIdentifierData> externalIdentifierDataList;

	/**
	 * individu
	 */
	// * <-> 1
	@ManyToOne()
	@JoinColumn(name="SGIN", nullable=false, foreignKey = @javax.persistence.ForeignKey(name = "FK_EXTERNAL_IDENTIFIER_SGIN_SGIN"))
	private Individu individu;

	/* PROTECTED REGION ID(_bII4Ak4kEeS-eLH--0fARw u var) ENABLED START */
	// add your custom variables here if necessary
	/* PROTECTED REGION END */

	/**
	 * default constructor
	 */
	public ExternalIdentifier() {
	}

	/**
	 * full constructor
	 *
	 * @param pIdentifierId          identifierId
	 * @param pGin                   gin
	 * @param pIdentifier            identifier
	 * @param pType                  type
	 * @param pLastSeenDate          lastSeenDate
	 * @param pCreationDate          creationDate
	 * @param pCreationSignature     creationSignature
	 * @param pCreationSite          creationSite
	 * @param pModificationDate      modificationDate
	 * @param pModificationSignature modificationSignature
	 * @param pModificationSite      modificationSite
	 */
	public ExternalIdentifier(Long pIdentifierId, String pGin, String pIdentifier, String pType, Date pLastSeenDate,
							  Date pCreationDate, String pCreationSignature, String pCreationSite, Date pModificationDate,
							  String pModificationSignature, String pModificationSite) {
		this.identifierId = pIdentifierId;
		this.gin = pGin;
		this.identifier = pIdentifier;
		this.type = pType;
		this.lastSeenDate = pLastSeenDate;
		this.creationDate = pCreationDate;
		this.creationSignature = pCreationSignature;
		this.creationSite = pCreationSite;
		this.modificationDate = pModificationDate;
		this.modificationSignature = pModificationSignature;
		this.modificationSite = pModificationSite;
	}

	/**
	 * full constructor
	 *
	 * @param pIdentifierId          identifierId
	 * @param pIdentifier            identifier
	 * @param pType                  type
	 * @param pLastSeenDate          lastSeenDate
	 * @param pCreationDate          creationDate
	 * @param pCreationSignature     creationSignature
	 * @param pCreationSite          creationSite
	 * @param pModificationDate      modificationDate
	 * @param pModificationSignature modificationSignature
	 * @param pModificationSite      modificationSite
	 */
	public ExternalIdentifier(Long pIdentifierId, String pIdentifier, String pType, Date pLastSeenDate,
							  Date pCreationDate, String pCreationSignature, String pCreationSite, Date pModificationDate,
							  String pModificationSignature, String pModificationSite) {
		this.identifierId = pIdentifierId;
		this.identifier = pIdentifier;
		this.type = pType;
		this.lastSeenDate = pLastSeenDate;
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
	 * @return externalIdentifierDataList
	 */
	public Set<ExternalIdentifierData> getExternalIdentifierDataList() {
		return this.externalIdentifierDataList;
	}

	/**
	 *
	 * @param pExternalIdentifierDataList externalIdentifierDataList value
	 */
	public void setExternalIdentifierDataList(Set<ExternalIdentifierData> pExternalIdentifierDataList) {
		this.externalIdentifierDataList = pExternalIdentifierDataList;
	}

	/**
	 *
	 * @return identifier
	 */
	public String getIdentifier() {
		return this.identifier;
	}

	/**
	 *
	 * @param pIdentifier identifier value
	 */
	public void setIdentifier(String pIdentifier) {
		this.identifier = pIdentifier;
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
	 * @return identifierId
	 */
	public Long getIdentifierId() {
		return this.identifierId;
	}

	/**
	 *
	 * @param pIdentifierId identifierId value
	 */
	public void setIdentifierId(Long pIdentifierId) {
		this.identifierId = pIdentifierId;
	}

	/**
	 *
	 * @return individu
	 */
	public Individu getIndividu() {
		return this.individu;
	}

	/**
	 *
	 * @param pIndividu individu value
	 */
	public void setIndividu(Individu pIndividu) {
		this.individu = pIndividu;
	}

	/**
	 *
	 * @return lastSeenDate
	 */
	public Date getLastSeenDate() {
		return this.lastSeenDate;
	}

	/**
	 *
	 * @param pLastSeenDate lastSeenDate value
	 */
	public void setLastSeenDate(Date pLastSeenDate) {
		this.lastSeenDate = pLastSeenDate;
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
	public String toString() {
		String result = null;
		/* PROTECTED REGION ID(toString_bII4Ak4kEeS-eLH--0fARw) ENABLED START */
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
		buffer.append("identifierId=").append(getIdentifierId());
		buffer.append(",");
		buffer.append("gin=").append(getGin());
		buffer.append(",");
		buffer.append("identifier=").append(getIdentifier());
		buffer.append(",");
		buffer.append("type=").append(getType());
		buffer.append(",");
		buffer.append("lastSeenDate=").append(getLastSeenDate());
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

	/* PROTECTED REGION ID(equals hash _bII4Ak4kEeS-eLH--0fARw) ENABLED START */

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

	/* PROTECTED REGION ID(_bII4Ak4kEeS-eLH--0fARw u m) ENABLED START */
	// add your custom methods here if necessary
	/* PROTECTED REGION END */

}
