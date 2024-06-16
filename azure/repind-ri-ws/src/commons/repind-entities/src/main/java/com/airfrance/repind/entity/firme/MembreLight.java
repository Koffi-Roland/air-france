package com.airfrance.repind.entity.firme;

import com.airfrance.repind.entity.individu.IndividuLight;
import org.hibernate.annotations.ForeignKey;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 * <p>
 * Title : Membre.java
 * </p>
 * BO Membre
 * <p>
 * Copyright : Copyright (c) 2009
 * </p>
 * <p>
 * Company : AIRFRANCE
 * </p>
 */
@Entity

@Table(name = "MEMBRE")
public class MembreLight implements Serializable {

	/**
	 * Determines if a de-serialized file is compatible with this class.
	 *
	 * Maintainers must change this value if and only if the new version of this
	 * class is not compatible with old versions. See Sun docs for <a
	 * href=http://java.sun.com/j2se/1.5.0/docs/guide/serialization
	 * /serialization/spec/class.html#4100> details. </a>
	 *
	 * Not necessary to include in first version of the class, but included here
	 * as a reminder of its importance.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * key
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ISEQ_MEMBRE")
    @SequenceGenerator(name="ISEQ_MEMBRE", sequenceName = "ISEQ_MEMBRE", allocationSize = 1)
	@Column(name = "IKEY")
	private Integer key;

	/**
	 * version
	 */
	@Version
	@Column(name = "IVERSION", length = 38)
	private Integer version;

	/**
	 * fonction
	 */
	@Column(name = "SFONCTION", length = 3)
	private String fonction;

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
	 * dateDebutValidite
	 */
	@Column(name = "DDATE_DEBUT_VALIDITE")
	private Date dateDebutValidite;

	/**
	 * dateFinValidite
	 */
	@Column(name = "DDATE_FIN_VALIDITE")
	private Date dateFinValidite;

	/**
	 * client
	 */
	@Column(name = "SCLIENT", length = 1)
	private String client;

	/**
	 * contact
	 */
	@Column(name = "SCONTACT", length = 1)
	private String contact;

	/**
	 * contactAf
	 */
	@Column(name = "SCONTACT_AF", length = 1)
	private String contactAf;

	/**
	 * emissionHs
	 */
	@Column(name = "SEMISSION_HS", length = 1)
	private String emissionHs;

	/**
	 * serviceAf
	 */
	@Column(name = "SERVICE_AF", length = 6)
	private String serviceAf;

	/**
	 * fonctions
	 */
	// 1 <-> *
	@OneToMany(mappedBy = "membre")
	private Set<Fonction> fonctions;

	/**
	 * individu
	 */
	// * -> 1
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SGIN_INDIVIDUS", nullable = false)
	@ForeignKey(name = "MEM_IND_FK")
	private IndividuLight individuLight;

	/**
	 * personneMorale
	 */
	// * <-> 1
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SGIN_PM", nullable = false)
	@ForeignKey(name = "MEM_PM_FK")
	private PersonneMoraleLight personneMoraleLight;

	/**
	 * default constructor
	 */
	public MembreLight() {
	}

	/**
	 * full constructor
	 * 
	 * @param pKey
	 *            key
	 * @param pVersion
	 *            version
	 * @param pFonction
	 *            fonction
	 * @param pDateCreation
	 *            dateCreation
	 * @param pSignatureCreation
	 *            signatureCreation
	 * @param pDateModification
	 *            dateModification
	 * @param pSignatureModification
	 *            signatureModification
	 * @param pDateDebutValidite
	 *            dateDebutValidite
	 * @param pDateFinValidite
	 *            dateFinValidite
	 * @param pClient
	 *            client
	 * @param pContact
	 *            contact
	 * @param pContactAf
	 *            contactAf
	 * @param pEmissionHs
	 *            emissionHs
	 * @param pServiceAf
	 *            serviceAf
	 */
	public MembreLight(Integer pKey, Integer pVersion, String pFonction, Date pDateCreation, String pSignatureCreation,
                       Date pDateModification, String pSignatureModification, Date pDateDebutValidite, Date pDateFinValidite,
                       String pClient, String pContact, String pContactAf, String pEmissionHs, String pServiceAf) {
		this.key = pKey;
		this.version = pVersion;
		this.fonction = pFonction;
		this.dateCreation = pDateCreation;
		this.signatureCreation = pSignatureCreation;
		this.dateModification = pDateModification;
		this.signatureModification = pSignatureModification;
		this.dateDebutValidite = pDateDebutValidite;
		this.dateFinValidite = pDateFinValidite;
		this.client = pClient;
		this.contact = pContact;
		this.contactAf = pContactAf;
		this.emissionHs = pEmissionHs;
		this.serviceAf = pServiceAf;
	}

	/**
	 *
	 * @return client
	 */
	public String getClient() {
		return this.client;
	}

	/**
	 *
	 * @param pClient
	 *            client value
	 */
	public void setClient(String pClient) {
		this.client = pClient;
	}

	/**
	 *
	 * @return contact
	 */
	public String getContact() {
		return this.contact;
	}

	/**
	 *
	 * @param pContact
	 *            contact value
	 */
	public void setContact(String pContact) {
		this.contact = pContact;
	}

	/**
	 *
	 * @return contactAf
	 */
	public String getContactAf() {
		return this.contactAf;
	}

	/**
	 *
	 * @param pContactAf
	 *            contactAf value
	 */
	public void setContactAf(String pContactAf) {
		this.contactAf = pContactAf;
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
	 * @param pDateCreation
	 *            dateCreation value
	 */
	public void setDateCreation(Date pDateCreation) {
		this.dateCreation = pDateCreation;
	}

	/**
	 *
	 * @return dateDebutValidite
	 */
	public Date getDateDebutValidite() {
		return this.dateDebutValidite;
	}

	/**
	 *
	 * @param pDateDebutValidite
	 *            dateDebutValidite value
	 */
	public void setDateDebutValidite(Date pDateDebutValidite) {
		this.dateDebutValidite = pDateDebutValidite;
	}

	/**
	 *
	 * @return dateFinValidite
	 */
	public Date getDateFinValidite() {
		return this.dateFinValidite;
	}

	/**
	 *
	 * @param pDateFinValidite
	 *            dateFinValidite value
	 */
	public void setDateFinValidite(Date pDateFinValidite) {
		this.dateFinValidite = pDateFinValidite;
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
	 * @param pDateModification
	 *            dateModification value
	 */
	public void setDateModification(Date pDateModification) {
		this.dateModification = pDateModification;
	}

	/**
	 *
	 * @return emissionHs
	 */
	public String getEmissionHs() {
		return this.emissionHs;
	}

	/**
	 *
	 * @param pEmissionHs
	 *            emissionHs value
	 */
	public void setEmissionHs(String pEmissionHs) {
		this.emissionHs = pEmissionHs;
	}

	/**
	 *
	 * @return fonction
	 */
	public String getFonction() {
		return this.fonction;
	}

	/**
	 *
	 * @param pFonction
	 *            fonction value
	 */
	public void setFonction(String pFonction) {
		this.fonction = pFonction;
	}

	/**
	 *
	 * @return fonctions
	 */
	public Set<Fonction> getFonctions() {
		return this.fonctions;
	}

	/**
	 *
	 * @param pFonctions
	 *            fonctions value
	 */
	public void setFonctions(Set<Fonction> pFonctions) {
		this.fonctions = pFonctions;
	}

	/**
	 *
	 * @return individu
	 */
	public IndividuLight getIndividuLight() {
		return this.individuLight;
	}

	/**
	 *
	 * @param pIndividu
	 *            individu value
	 */
	public void setIndividuLight(IndividuLight pIndividuLight) {
		this.individuLight = pIndividuLight;
	}

	/**
	 *
	 * @return key
	 */
	public Integer getKey() {
		return this.key;
	}

	/**
	 *
	 * @param pKey
	 *            key value
	 */
	public void setKey(Integer pKey) {
		this.key = pKey;
	}

	/**
	 *
	 * @return personneMorale
	 */
	public PersonneMoraleLight getPersonneMoraleLight() {
		return this.personneMoraleLight;
	}

	/**
	 *
	 * @param pPersonneMorale
	 *            personneMorale value
	 */
	public void setPersonneMoraleLight(PersonneMoraleLight pPersonneMoraleLight) {
		this.personneMoraleLight = pPersonneMoraleLight;
	}

	/**
	 *
	 * @return serviceAf
	 */
	public String getServiceAf() {
		return this.serviceAf;
	}

	/**
	 *
	 * @param pServiceAf
	 *            serviceAf value
	 */
	public void setServiceAf(String pServiceAf) {
		this.serviceAf = pServiceAf;
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
	 * @param pSignatureCreation
	 *            signatureCreation value
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
	 * @param pSignatureModification
	 *            signatureModification value
	 */
	public void setSignatureModification(String pSignatureModification) {
		this.signatureModification = pSignatureModification;
	}

	/**
	 *
	 * @return version
	 */
	public Integer getVersion() {
		return this.version;
	}

	/**
	 *
	 * @param pVersion
	 *            version value
	 */
	public void setVersion(Integer pVersion) {
		this.version = pVersion;
	}

	/**
	 *
	 * @return object as string
	 */
	public String toString() {
		String result = null;
		result = toStringImpl();
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
		buffer.append("key=").append(getKey());
		buffer.append(",");
		buffer.append("version=").append(getVersion());
		buffer.append(",");
		buffer.append("fonction=").append(getFonction());
		buffer.append(",");
		buffer.append("dateCreation=").append(getDateCreation());
		buffer.append(",");
		buffer.append("signatureCreation=").append(getSignatureCreation());
		buffer.append(",");
		buffer.append("dateModification=").append(getDateModification());
		buffer.append(",");
		buffer.append("signatureModification=").append(getSignatureModification());
		buffer.append(",");
		buffer.append("dateDebutValidite=").append(getDateDebutValidite());
		buffer.append(",");
		buffer.append("dateFinValidite=").append(getDateFinValidite());
		buffer.append(",");
		buffer.append("client=").append(getClient());
		buffer.append(",");
		buffer.append("contact=").append(getContact());
		buffer.append(",");
		buffer.append("contactAf=").append(getContactAf());
		buffer.append(",");
		buffer.append("emissionHs=").append(getEmissionHs());
		buffer.append(",");
		buffer.append("serviceAf=").append(getServiceAf());
		buffer.append("]");
		return buffer.toString();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see Object#equals(Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj)) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final MembreLight other = (MembreLight) obj;

		return super.equals(other);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see Object#hashCode()
	 */
	@Override
	public int hashCode() {
		int result = super.hashCode();

		return result;
	}

}
