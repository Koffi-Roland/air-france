package com.airfrance.repind.entity.individu;

/*PROTECTED REGION ID(_bouG4NN9Eee-fsR_-mfQcQ i) ENABLED START*/

// add not generated imports here

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/*PROTECTED REGION END*/

/**
 * <p>
 * Title : ForgottenIndividual.java
 * </p>
 * BO ForgottenIndividual
 * <p>
 * Copyright : Copyright (c) 2009
 * </p>
 * <p>
 * Company : AIRFRANCE
 * </p>
 */
@Entity

@Table(name = "FORGOTTEN_INDIVIDUAL")
public class ForgottenIndividual implements Serializable {

	/* PROTECTED REGION ID(serialUID _bouG4NN9Eee-fsR_-mfQcQ) ENABLED START */
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
	 * forgottenIndividualId
	 */
	@Id
	@Column(name = "FORGOTTEN_INDIVIDUAL_ID")
	@SequenceGenerator(name = "SEQ_FORGOTTEN_INDIVIDUAL",
		sequenceName = "SEQ_FORGOTTEN_INDIVIDUAL", allocationSize = 1)
	@GeneratedValue(generator = "SEQ_FORGOTTEN_INDIVIDUAL")
	private Long forgottenIndividualId;

	/**
	 * identifier
	 */
	@Column(name = "IDENTIFIER")
	private String identifier;

	/**
	 * identifierType
	 */
	@Column(name = "IDENTIFIER_TYPE")
	private String identifierType;

	/**
	 * context
	 */
	@Column(name = "CONTEXT")
	private String context;

	/**
	 * deletionDate
	 */
	@Column(name = "DELETION_DATE")
	private Date deletionDate;

	/**
	 * signature
	 */
	@Column(name = "SIGNATURE")
	private String signature;

	/**
	 * applicationCode
	 */
	@Column(name = "APPLI")
	private String applicationCode;

	/**
	 * site
	 */
	@Column(name = "SITE")
	private String site;

	/**
	 * modificationDate
	 */
	@Column(name = "MODIFICATION_DATE")
	private Date modificationDate;

	/* PROTECTED REGION ID(_bouG4NN9Eee-fsR_-mfQcQ u var) ENABLED START */
	// add your custom variables here if necessary
	/* PROTECTED REGION END */

	/**
	 * default constructor
	 */
	public ForgottenIndividual() {
		// empty constructor
	}

	/**
	 * full constructor
	 * 
	 * @param pForgottenIndividualId forgottenIndividualId
	 * @param pIdentifier            identifier
	 * @param pIdentifierType        identifierType
	 * @param pContext               context
	 * @param pDeletionDate          deletionDate
	 * @param pSignature             signature
	 * @param pApplicationCode       applicationCode
	 * @param pSite                  site
	 * @param pModificationDate      modificationDate
	 */
	public ForgottenIndividual(Long pForgottenIndividualId, String pIdentifier, String pIdentifierType, String pContext,
			Date pDeletionDate, String pSignature, String pApplicationCode, String pSite, Date pModificationDate) {
		this.forgottenIndividualId = pForgottenIndividualId;
		this.identifier = pIdentifier;
		this.identifierType = pIdentifierType;
		this.context = pContext;
		this.deletionDate = pDeletionDate;
		this.signature = pSignature;
		this.applicationCode = pApplicationCode;
		this.site = pSite;
		this.modificationDate = pModificationDate;
	}

	/**
	 *
	 * @return applicationCode
	 */
	public String getApplicationCode() {
		return this.applicationCode;
	}

	/**
	 *
	 * @param pApplicationCode applicationCode value
	 */
	public void setApplicationCode(String pApplicationCode) {
		this.applicationCode = pApplicationCode;
	}

	/**
	 *
	 * @return context
	 */
	public String getContext() {
		return this.context;
	}

	/**
	 *
	 * @param pContext context value
	 */
	public void setContext(String pContext) {
		this.context = pContext;
	}

	/**
	 *
	 * @return deletionDate
	 */
	public Date getDeletionDate() {
		return this.deletionDate;
	}

	/**
	 *
	 * @param pDeletionDate deletionDate value
	 */
	public void setDeletionDate(Date pDeletionDate) {
		this.deletionDate = pDeletionDate;
	}

	/**
	 *
	 * @return forgottenIndividualId
	 */
	public Long getForgottenIndividualId() {
		return this.forgottenIndividualId;
	}

	/**
	 *
	 * @param pForgottenIndividualId forgottenIndividualId value
	 */
	public void setForgottenIndividualId(Long pForgottenIndividualId) {
		this.forgottenIndividualId = pForgottenIndividualId;
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
	 * @return identifierType
	 */
	public String getIdentifierType() {
		return this.identifierType;
	}

	/**
	 *
	 * @param pIdentifierType identifierType value
	 */
	public void setIdentifierType(String pIdentifierType) {
		this.identifierType = pIdentifierType;
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
	 * @return signature
	 */
	public String getSignature() {
		return this.signature;
	}

	/**
	 *
	 * @param pSignature signature value
	 */
	public void setSignature(String pSignature) {
		this.signature = pSignature;
	}

	/**
	 *
	 * @return site
	 */
	public String getSite() {
		return this.site;
	}

	/**
	 *
	 * @param pSite site value
	 */
	public void setSite(String pSite) {
		this.site = pSite;
	}

	/**
	 *
	 * @return object as string
	 */
	public String toString() {
		String result = null;
		/* PROTECTED REGION ID(toString_bouG4NN9Eee-fsR_-mfQcQ) ENABLED START */
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
		buffer.append("forgottenIndividualId=").append(getForgottenIndividualId());
		buffer.append(",");
		buffer.append("identifier=").append(getIdentifier());
		buffer.append(",");
		buffer.append("identifierType=").append(getIdentifierType());
		buffer.append(",");
		buffer.append("context=").append(getContext());
		buffer.append(",");
		buffer.append("deletionDate=").append(getDeletionDate());
		buffer.append(",");
		buffer.append("signature=").append(getSignature());
		buffer.append(",");
		buffer.append("applicationCode=").append(getApplicationCode());
		buffer.append(",");
		buffer.append("site=").append(getSite());
		buffer.append(",");
		buffer.append("modificationDate=").append(getModificationDate());
		buffer.append("]");
		return buffer.toString();
	}

	/* PROTECTED REGION ID(equals hash _bouG4NN9Eee-fsR_-mfQcQ) ENABLED START */

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

	/* PROTECTED REGION ID(_bouG4NN9Eee-fsR_-mfQcQ u m) ENABLED START */
	// add your custom methods here if necessary
	/* PROTECTED REGION END */

}
