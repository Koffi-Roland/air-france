package com.airfrance.repind.entity.firme;

import com.airfrance.repind.entity.adresse.Email;
import com.airfrance.repind.entity.adresse.Telecoms;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 * <p>
 * Title : PersonneMorale.java
 * </p>
 * BO PersonneMorale
 * <p>
 * Copyright : Copyright (c) 2009
 * </p>
 * <p>
 * Company : AIRFRANCE
 * </p>
 */
@Entity

@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "STYPE", discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue("")

@Table(name = "PERS_MORALE")
@GenericGenerator(name = "seq_id", strategy = "com.airfrance.repind.entity.PersonneMoraleHibernateSequence")
public abstract class PersonneMoraleLight implements Serializable {

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
	 * gin
	 */
	@Id
	@Column(name = "SGIN", length = 12, nullable = false, unique = true)
	@GeneratedValue(generator = "seq_id")
	private String gin;

	/**
	 * version
	 */
	@Version
	@Column(name = "IVERSION", nullable = false)
	private Integer version;

	/**
	 * nom
	 */
	@Column(name = "SNOM", length = 45, nullable = false)
	private String nom;

	/**
	 * statut
	 */
	@Column(name = "SSTATUT", length = 2, nullable = false)
	private String statut;

	/**
	 * dateModificationStatut
	 */
	@Column(name = "DDATE_MODIFICATION_STATUT")
	private Date dateModificationStatut;

	/**
	 * activiteLocal
	 */
	@Column(name = "SACTIVITE_LOCAL", length = 4)
	private String activiteLocal;

	/**
	 * ginFusion
	 */
	@Column(name = "SGIN_FUSION", length = 12)
	private String ginFusion;

	/**
	 * dateFusion
	 */
	@Column(name = "DDATE_FUSION")
	private Date dateFusion;

	/**
	 * signatureFusion
	 */
	@Column(name = "SSIGNATURE_FUSION", length = 16)
	private String signatureFusion;

	/**
	 * dateCreation
	 */
	@Column(name = "DDATE_CREATION", nullable = false)
	private Date dateCreation;

	/**
	 * signatureCreation
	 */
	@Column(name = "SSIGNATURE_CREATION", length = 16, nullable = false)
	private String signatureCreation;

	/**
	 * dateModification
	 */
	@Column(name = "DDATE_MODIFICATION", nullable = false)
	private Date dateModification;

	/**
	 * signatureModification
	 */
	@Column(name = "SSIGNATURE_MODIFICATION", length = 16, nullable = false)
	private String signatureModification;

	/**
	 * codeIndustrie
	 */
	@Column(name = "SCODE_INDUSTRIE", length = 2)
	private String codeIndustrie;

	/**
	 * typeDemarchage
	 */
	@Column(name = "STYPE_DEMARCHAGE", length = 1)
	private String typeDemarchage;

	/**
	 * siteInternet
	 */
	@Column(name = "SSITE_INTERNET", length = 60)
	private String siteInternet;

	/**
	 * indictNom
	 */
	@Column(name = "SINDICT_NOM", length = 45)
	private String indictNom;

	/**
	 * cieGest
	 */
	@Column(name = "SCIE_GEST", length = 3)
	private String cieGest;

	/**
	 * codeSource
	 */
	@Column(name = "SCODE_SOURCE", length = 2)
	private String codeSource;

	/**
	 * codeSupport
	 */
	@Column(name = "SCODE_SUPPORT", length = 2)
	private String codeSupport;

	/**
	 * statutJuridique
	 */
	@Column(name = "SSTATUT_JURIDIQUE", length = 4)
	private String statutJuridique;

	/**
	 * dateModificationNom
	 */
	@Column(name = "DDATE_MODIFICATION_SNOM")
	private Date dateModificationNom;

	/**
	 * siteCreation
	 */
	@Column(name = "SSITE_CREATION", length = 10, nullable = false)
	private String siteCreation;

	/**
	 * siteModification
	 */
	@Column(name = "SSITE_MODIFICATION", length = 10, nullable = false)
	private String siteModification;

	/**
	 * emails
	 */
	// 1 <-> *
	@OneToMany(mappedBy = "personneMorale")
	private Set<Email> emails;

	/**
	 * telecoms
	 */
	// 1 <-> *
	@OneToMany(mappedBy = "personneMorale")
	private Set<Telecoms> telecoms;

	/**
	 * membres
	 */
	// 1 <-> *
	@OneToMany(mappedBy = "personneMorale")
	private Set<Membre> membres;

	/**
	 * default constructor
	 */
	public PersonneMoraleLight() {
	}

	/**
	 * full constructor
	 * 
	 * @param pGin
	 *            gin
	 * @param pVersion
	 *            version
	 * @param pNom
	 *            nom
	 * @param pStatut
	 *            statut
	 * @param pDateModificationStatut
	 *            dateModificationStatut
	 * @param pActiviteLocal
	 *            activiteLocal
	 * @param pGinFusion
	 *            ginFusion
	 * @param pDateFusion
	 *            dateFusion
	 * @param pSignatureFusion
	 *            signatureFusion
	 * @param pDateCreation
	 *            dateCreation
	 * @param pSignatureCreation
	 *            signatureCreation
	 * @param pDateModification
	 *            dateModification
	 * @param pSignatureModification
	 *            signatureModification
	 * @param pCodeIndustrie
	 *            codeIndustrie
	 * @param pTypeDemarchage
	 *            typeDemarchage
	 * @param pSiteInternet
	 *            siteInternet
	 * @param pIndictNom
	 *            indictNom
	 * @param pCieGest
	 *            cieGest
	 * @param pCodeSource
	 *            codeSource
	 * @param pCodeSupport
	 *            codeSupport
	 * @param pStatutJuridique
	 *            statutJuridique
	 * @param pDateModificationNom
	 *            dateModificationNom
	 * @param pSiteCreation
	 *            siteCreation
	 * @param pSiteModification
	 *            siteModification
	 */
	public PersonneMoraleLight(String pGin, Integer pVersion, String pNom, String pStatut, Date pDateModificationStatut,
                               String pActiviteLocal, String pGinFusion, Date pDateFusion, String pSignatureFusion, Date pDateCreation,
                               String pSignatureCreation, Date pDateModification, String pSignatureModification, String pCodeIndustrie,
                               String pTypeDemarchage, String pSiteInternet, String pIndictNom, String pCieGest, String pCodeSource,
                               String pCodeSupport, String pStatutJuridique, Date pDateModificationNom, String pSiteCreation,
                               String pSiteModification) {
		this.gin = pGin;
		this.version = pVersion;
		this.nom = pNom;
		this.statut = pStatut;
		this.dateModificationStatut = pDateModificationStatut;
		this.activiteLocal = pActiviteLocal;
		this.ginFusion = pGinFusion;
		this.dateFusion = pDateFusion;
		this.signatureFusion = pSignatureFusion;
		this.dateCreation = pDateCreation;
		this.signatureCreation = pSignatureCreation;
		this.dateModification = pDateModification;
		this.signatureModification = pSignatureModification;
		this.codeIndustrie = pCodeIndustrie;
		this.typeDemarchage = pTypeDemarchage;
		this.siteInternet = pSiteInternet;
		this.indictNom = pIndictNom;
		this.cieGest = pCieGest;
		this.codeSource = pCodeSource;
		this.codeSupport = pCodeSupport;
		this.statutJuridique = pStatutJuridique;
		this.dateModificationNom = pDateModificationNom;
		this.siteCreation = pSiteCreation;
		this.siteModification = pSiteModification;
	}

	/**
	 *
	 * @return activiteLocal
	 */
	public String getActiviteLocal() {
		return this.activiteLocal;
	}

	/**
	 *
	 * @param pActiviteLocal
	 *            activiteLocal value
	 */
	public void setActiviteLocal(String pActiviteLocal) {
		this.activiteLocal = pActiviteLocal;
	}

	/**
	 *
	 * @return cieGest
	 */
	public String getCieGest() {
		return this.cieGest;
	}

	/**
	 *
	 * @param pCieGest
	 *            cieGest value
	 */
	public void setCieGest(String pCieGest) {
		this.cieGest = pCieGest;
	}

	/**
	 *
	 * @return codeIndustrie
	 */
	public String getCodeIndustrie() {
		return this.codeIndustrie;
	}

	/**
	 *
	 * @param pCodeIndustrie
	 *            codeIndustrie value
	 */
	public void setCodeIndustrie(String pCodeIndustrie) {
		this.codeIndustrie = pCodeIndustrie;
	}

	/**
	 *
	 * @return codeSource
	 */
	public String getCodeSource() {
		return this.codeSource;
	}

	/**
	 *
	 * @param pCodeSource
	 *            codeSource value
	 */
	public void setCodeSource(String pCodeSource) {
		this.codeSource = pCodeSource;
	}

	/**
	 *
	 * @return codeSupport
	 */
	public String getCodeSupport() {
		return this.codeSupport;
	}

	/**
	 *
	 * @param pCodeSupport
	 *            codeSupport value
	 */
	public void setCodeSupport(String pCodeSupport) {
		this.codeSupport = pCodeSupport;
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
	 * @return dateFusion
	 */
	public Date getDateFusion() {
		return this.dateFusion;
	}

	/**
	 *
	 * @param pDateFusion
	 *            dateFusion value
	 */
	public void setDateFusion(Date pDateFusion) {
		this.dateFusion = pDateFusion;
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
	 * @return dateModificationNom
	 */
	public Date getDateModificationNom() {
		return this.dateModificationNom;
	}

	/**
	 *
	 * @param pDateModificationNom
	 *            dateModificationNom value
	 */
	public void setDateModificationNom(Date pDateModificationNom) {
		this.dateModificationNom = pDateModificationNom;
	}

	/**
	 *
	 * @return dateModificationStatut
	 */
	public Date getDateModificationStatut() {
		return this.dateModificationStatut;
	}

	/**
	 *
	 * @param pDateModificationStatut
	 *            dateModificationStatut value
	 */
	public void setDateModificationStatut(Date pDateModificationStatut) {
		this.dateModificationStatut = pDateModificationStatut;
	}

	/**
	 *
	 * @return emails
	 */
	public Set<Email> getEmails() {
		return this.emails;
	}

	/**
	 *
	 * @param pEmails
	 *            emails value
	 */
	public void setEmails(Set<Email> pEmails) {
		this.emails = pEmails;
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
	 * @param pGin
	 *            gin value
	 */
	public void setGin(String pGin) {
		this.gin = pGin;
	}

	/**
	 *
	 * @return ginFusion
	 */
	public String getGinFusion() {
		return this.ginFusion;
	}

	/**
	 *
	 * @param pGinFusion
	 *            ginFusion value
	 */
	public void setGinFusion(String pGinFusion) {
		this.ginFusion = pGinFusion;
	}

	/**
	 *
	 * @return indictNom
	 */
	public String getIndictNom() {
		return this.indictNom;
	}

	/**
	 *
	 * @param pIndictNom
	 *            indictNom value
	 */
	public void setIndictNom(String pIndictNom) {
		this.indictNom = pIndictNom;
	}

	/**
	 *
	 * @return nom
	 */
	public String getNom() {
		return this.nom;
	}

	/**
	 *
	 * @param pNom
	 *            nom value
	 */
	public void setNom(String pNom) {
		this.nom = pNom;
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
	 * @return signatureFusion
	 */
	public String getSignatureFusion() {
		return this.signatureFusion;
	}

	/**
	 *
	 * @param pSignatureFusion
	 *            signatureFusion value
	 */
	public void setSignatureFusion(String pSignatureFusion) {
		this.signatureFusion = pSignatureFusion;
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
	 * @return siteCreation
	 */
	public String getSiteCreation() {
		return this.siteCreation;
	}

	/**
	 *
	 * @param pSiteCreation
	 *            siteCreation value
	 */
	public void setSiteCreation(String pSiteCreation) {
		this.siteCreation = pSiteCreation;
	}

	/**
	 *
	 * @return siteInternet
	 */
	public String getSiteInternet() {
		return this.siteInternet;
	}

	/**
	 *
	 * @param pSiteInternet
	 *            siteInternet value
	 */
	public void setSiteInternet(String pSiteInternet) {
		this.siteInternet = pSiteInternet;
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
	 * @param pSiteModification
	 *            siteModification value
	 */
	public void setSiteModification(String pSiteModification) {
		this.siteModification = pSiteModification;
	}

	/**
	 *
	 * @return statut
	 */
	public String getStatut() {
		return this.statut;
	}

	/**
	 *
	 * @param pStatut
	 *            statut value
	 */
	public void setStatut(String pStatut) {
		this.statut = pStatut;
	}

	/**
	 *
	 * @return statutJuridique
	 */
	public String getStatutJuridique() {
		return this.statutJuridique;
	}

	/**
	 *
	 * @param pStatutJuridique
	 *            statutJuridique value
	 */
	public void setStatutJuridique(String pStatutJuridique) {
		this.statutJuridique = pStatutJuridique;
	}

	/**
	 *
	 * @return telecoms
	 */
	public Set<Telecoms> getTelecoms() {
		return this.telecoms;
	}

	/**
	 *
	 * @param pTelecoms
	 *            telecoms value
	 */
	public void setTelecoms(Set<Telecoms> pTelecoms) {
		this.telecoms = pTelecoms;
	}

	/**
	 *
	 * @return typeDemarchage
	 */
	public String getTypeDemarchage() {
		return this.typeDemarchage;
	}

	/**
	 *
	 * @param pTypeDemarchage
	 *            typeDemarchage value
	 */
	public void setTypeDemarchage(String pTypeDemarchage) {
		this.typeDemarchage = pTypeDemarchage;
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
     * @return membres
     */
    public Set<Membre> getMembres() {
        return this.membres;
    }

    /**
     *
     * @param pMembres membres value
     */
    public void setMembres(Set<Membre> pMembres) {
        this.membres = pMembres;
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
		buffer.append("gin=").append(getGin());
		buffer.append(",");
		buffer.append("version=").append(getVersion());
		buffer.append(",");
		buffer.append("nom=").append(getNom());
		buffer.append(",");
		buffer.append("statut=").append(getStatut());
		buffer.append(",");
		buffer.append("dateModificationStatut=").append(getDateModificationStatut());
		buffer.append(",");
		buffer.append("activiteLocal=").append(getActiviteLocal());
		buffer.append(",");
		buffer.append("ginFusion=").append(getGinFusion());
		buffer.append(",");
		buffer.append("dateFusion=").append(getDateFusion());
		buffer.append(",");
		buffer.append("signatureFusion=").append(getSignatureFusion());
		buffer.append(",");
		buffer.append("dateCreation=").append(getDateCreation());
		buffer.append(",");
		buffer.append("signatureCreation=").append(getSignatureCreation());
		buffer.append(",");
		buffer.append("dateModification=").append(getDateModification());
		buffer.append(",");
		buffer.append("signatureModification=").append(getSignatureModification());
		buffer.append(",");
		buffer.append("codeIndustrie=").append(getCodeIndustrie());
		buffer.append(",");
		buffer.append("typeDemarchage=").append(getTypeDemarchage());
		buffer.append(",");
		buffer.append("siteInternet=").append(getSiteInternet());
		buffer.append(",");
		buffer.append("indictNom=").append(getIndictNom());
		buffer.append(",");
		buffer.append("cieGest=").append(getCieGest());
		buffer.append(",");
		buffer.append("codeSource=").append(getCodeSource());
		buffer.append(",");
		buffer.append("codeSupport=").append(getCodeSupport());
		buffer.append(",");
		buffer.append("statutJuridique=").append(getStatutJuridique());
		buffer.append(",");
		buffer.append("dateModificationNom=").append(getDateModificationNom());
		buffer.append(",");
		buffer.append("siteCreation=").append(getSiteCreation());
		buffer.append(",");
		buffer.append("siteModification=").append(getSiteModification());
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
		final PersonneMoraleLight other = (PersonneMoraleLight) obj;

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
