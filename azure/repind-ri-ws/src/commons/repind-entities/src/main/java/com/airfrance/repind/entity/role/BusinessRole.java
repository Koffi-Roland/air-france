package com.airfrance.repind.entity.role;

/*PROTECTED REGION ID(_7y2qIPcXEd-Kx8TJdz7fGw i) ENABLED START*/

// add not generated imports here

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/*PROTECTED REGION END*/

/**
 * <p>
 * Title : BusinessRole.java
 * </p>
 * BO BusinessRole
 * <p>
 * Copyright : Copyright (c) 2009
 * </p>
 * <p>
 * Company : AIRFRANCE
 * </p>
 */
@Entity

@Table(name = "BUSINESS_ROLE")
public class BusinessRole implements Serializable {

	/* PROTECTED REGION ID(serialUID _7y2qIPcXEd-Kx8TJdz7fGw) ENABLED START */
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
	 * cleRole
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ISEQ_BUSINESS_ROLE")
	@SequenceGenerator(name = "ISEQ_BUSINESS_ROLE", sequenceName = "ISEQ_BUSINESS_ROLE", allocationSize = 1)
	@Column(name = "ICLE_ROLE", length = 10, nullable = false)
	private Integer cleRole;

	/**
	 * ginInd
	 */
	@Column(name = "SGIN_IND", length = 12)
	private String ginInd;

	/**
	 * ginPm
	 */
	@Column(name = "SGIN_PM", length = 12)
	private String ginPm;

	/**
	 * numeroContrat
	 */
	@Column(name = "SNUMERO_CONTRAT", length = 20)
	private String numeroContrat;

	/**
	 * type
	 */
	@Column(name = "STYPE", length = 1)
	private String type;

	/**
	 * dateCreation
	 */
	@Column(name = "DDATE_CREATION")
	private Date dateCreation;

	/**
	 * siteCreation
	 */
	@Column(name = "SSITE_CREATION", length = 12)
	private String siteCreation;

	/**
	 * signatureCreation
	 */
	@Column(name = "SSIGNATURE_CREATION", length = 16)
	private String signatureCreation;

	/**
	 * dateModification
	 */
	@Column(name = "DDATE_MODIFICATION")
	private Date dateModification;

	/**
	 * siteModification
	 */
	@Column(name = "SSITE_MODIFICATION", length = 10)
	private String siteModification;

	/**
	 * signatureModification
	 */
	@Column(name = "SSIGNATURE_MODIFICATION", length = 16)
	private String signatureModification;

	/**
	 * roleAgence
	 */
	// 1 <-> 1
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@PrimaryKeyJoinColumn
	private RoleAgence roleAgence;

	/**
	 * roleFirme
	 */
	// 1 <-> 1
	@OneToOne(mappedBy = "businessRole", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private RoleFirme roleFirme;

	/**
	 * roleRcs
	 */
	// 1 <-> 1
	@OneToOne(mappedBy = "businessRole", fetch = FetchType.LAZY)
	private RoleRcs roleRcs;

	/**
	 * roleTravelers
	 */
	// 1 <-> 1
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@PrimaryKeyJoinColumn
	private RoleTravelers roleTravelers;

	/**
	 * roleUCCR
	 */
	// 1 <-> 1
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@PrimaryKeyJoinColumn
	private RoleUCCR roleUCCR;

	/**
	 * roleGP
	 */
	// 1 <-> 1
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@PrimaryKeyJoinColumn
	private RoleGP roleGP;

	/* PROTECTED REGION ID(_7y2qIPcXEd-Kx8TJdz7fGw u var) ENABLED START */
	// add your custom variables here if necessary
	/* PROTECTED REGION END */

	/**
	 * default constructor
	 */
	public BusinessRole() {
		// empty constructor
	}

	/**
	 * full constructor
	 * 
	 * @param pCleRole               cleRole
	 * @param pGinInd                ginInd
	 * @param pGinPm                 ginPm
	 * @param pNumeroContrat         numeroContrat
	 * @param pType                  type
	 * @param pDateCreation          dateCreation
	 * @param pSiteCreation          siteCreation
	 * @param pSignatureCreation     signatureCreation
	 * @param pDateModification      dateModification
	 * @param pSiteModification      siteModification
	 * @param pSignatureModification signatureModification
	 */
	public BusinessRole(Integer pCleRole, String pGinInd, String pGinPm, String pNumeroContrat, String pType,
			Date pDateCreation, String pSiteCreation, String pSignatureCreation, Date pDateModification,
			String pSiteModification, String pSignatureModification) {
		this.cleRole = pCleRole;
		this.ginInd = pGinInd;
		this.ginPm = pGinPm;
		this.numeroContrat = pNumeroContrat;
		this.type = pType;
		this.dateCreation = pDateCreation;
		this.siteCreation = pSiteCreation;
		this.signatureCreation = pSignatureCreation;
		this.dateModification = pDateModification;
		this.siteModification = pSiteModification;
		this.signatureModification = pSignatureModification;
	}

	/**
	 *
	 * @return cleRole
	 */
	public Integer getCleRole() {
		return this.cleRole;
	}

	/**
	 *
	 * @param pCleRole cleRole value
	 */
	public void setCleRole(Integer pCleRole) {
		this.cleRole = pCleRole;
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
	 * @return ginInd
	 */
	public String getGinInd() {
		return this.ginInd;
	}

	/**
	 *
	 * @param pGinInd ginInd value
	 */
	public void setGinInd(String pGinInd) {
		this.ginInd = pGinInd;
	}

	/**
	 *
	 * @return ginPm
	 */
	public String getGinPm() {
		return this.ginPm;
	}

	/**
	 *
	 * @param pGinPm ginPm value
	 */
	public void setGinPm(String pGinPm) {
		this.ginPm = pGinPm;
	}

	/**
	 *
	 * @return numeroContrat
	 */
	public String getNumeroContrat() {
		return this.numeroContrat;
	}

	/**
	 *
	 * @param pNumeroContrat numeroContrat value
	 */
	public void setNumeroContrat(String pNumeroContrat) {
		this.numeroContrat = pNumeroContrat;
	}

	/**
	 *
	 * @return roleAgence
	 */
	public RoleAgence getRoleAgence() {
		return this.roleAgence;
	}

	/**
	 *
	 * @param pRoleAgence roleAgence value
	 */
	public void setRoleAgence(RoleAgence pRoleAgence) {
		this.roleAgence = pRoleAgence;
	}

	/**
	 *
	 * @return roleFirme
	 */
	public RoleFirme getRoleFirme() {
		return this.roleFirme;
	}

	/**
	 *
	 * @param pRoleFirme roleFirme value
	 */
	public void setRoleFirme(RoleFirme pRoleFirme) {
		this.roleFirme = pRoleFirme;
	}

	/**
	 *
	 * @return roleRcs
	 */
	public RoleRcs getRoleRcs() {
		return this.roleRcs;
	}

	/**
	 *
	 * @param pRoleRcs roleRcs value
	 */
	public void setRoleRcs(RoleRcs pRoleRcs) {
		this.roleRcs = pRoleRcs;
	}

	/**
	 *
	 * @return roleTravelers
	 */
	public RoleTravelers getRoleTravelers() {
		return this.roleTravelers;
	}

	/**
	 *
	 * @param pRoleTravelers roleTravelers value
	 */
	public void setRoleTravelers(RoleTravelers pRoleTravelers) {
		this.roleTravelers = pRoleTravelers;
	}

	/**
	 *
	 * @return roleUCCR
	 */
	public RoleUCCR getRoleUCCR() {
		return this.roleUCCR;
	}

	/**
	 * @return the roleGP
	 */
	public RoleGP getRoleGP() {
		return roleGP;
	}

	/**
	 * @param roleGP the roleGP to set
	 */
	public void setRoleGP(RoleGP roleGP) {
		this.roleGP = roleGP;
	}

	/**
	 *
	 * @param pRoleUCCR roleUCCR value
	 */
	public void setRoleUCCR(RoleUCCR pRoleUCCR) {
		this.roleUCCR = pRoleUCCR;
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
	public String toString() {
		String result = null;
		/* PROTECTED REGION ID(toString_7y2qIPcXEd-Kx8TJdz7fGw) ENABLED START */
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
		buffer.append("cleRole=").append(getCleRole());
		buffer.append(",");
		buffer.append("ginInd=").append(getGinInd());
		buffer.append(",");
		buffer.append("ginPm=").append(getGinPm());
		buffer.append(",");
		buffer.append("numeroContrat=").append(getNumeroContrat());
		buffer.append(",");
		buffer.append("type=").append(getType());
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

	/* PROTECTED REGION ID(equals hash _7y2qIPcXEd-Kx8TJdz7fGw) ENABLED START */

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

	/* PROTECTED REGION ID(_7y2qIPcXEd-Kx8TJdz7fGw u m) ENABLED START */
	// add your custom methods here if necessary
	/* PROTECTED REGION END */

}
