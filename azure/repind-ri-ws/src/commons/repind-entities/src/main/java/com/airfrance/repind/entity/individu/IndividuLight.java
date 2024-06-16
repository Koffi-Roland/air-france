package com.airfrance.repind.entity.individu;

import com.airfrance.ref.type.TerminalTypeEnum;
import com.airfrance.repind.entity.adresse.Email;
import com.airfrance.repind.entity.adresse.Telecoms;
import com.airfrance.repind.entity.adresse.enums.MediumStatusEnum;
import com.airfrance.repind.util.NormalizedStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.ForeignKey;

import javax.persistence.*;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * <p>
 * Title : Individu.java
 * </p>
 * BO Individu
 * <p>
 * Copyright : Copyright (c) 2009
 * </p>
 * <p>
 * Company : AIRFRANCE
 * </p>
 */
@Entity

@Table(name = "INDIVIDUS_ALL")
public class IndividuLight implements Serializable {

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
	private static Log log = LogFactory.getLog(IndividuLight.class);

	/**
	 * sgin
	 */
	@Id
	@Column(name = "SGIN", length = 12, nullable = false, unique = true, updatable = false)
	private String sgin;

	/**
	 * type
	 */
	@Column(name = "STYPE", length = 1, nullable = false)
	private String type;

	/**
	 * version
	 */
	@Version
	@Column(name = "IVERSION", nullable = false)
	private Integer version;

	/**
	 * civilite
	 */
	@Column(name = "SCIVILITE", length = 4, nullable = false)
	private String civilite;

	/**
	 * motDePasse
	 */
	@Column(name = "SMOT_DE_PASSE", length = 10)
	private String motDePasse;

	/**
	 * nom
	 */
	@Column(name = "SNOM", length = 35)
	private String nom;

	/**
	 * nomTypo
	 */
	@Column(name = "SNOM_TYPO2", length = 140)
	private byte[] nomTypo;

	/**
	 * alias
	 */
	@Column(name = "SALIAS", length = 35)
	private String alias;

	/**
	 * prenom
	 */
	@Column(name = "SPRENOM", length = 25)
	private String prenom;

	/**
	 * prenomTypo
	 */
	@Column(name = "SPRENOM_TYPO2", length = 100)
	private byte[] prenomTypo;

	/**
	 * secondPrenom
	 */
	@Column(name = "SSECOND_PRENOM", length = 25)
	private String secondPrenom;

	/**
	 * aliasPrenom
	 */
	@Column(name = "SALIAS_PRENOM", length = 25)
	private String aliasPrenom;

	/**
	 * sexe
	 */
	@Column(name = "SSEXE", length = 1, nullable = false)
	private String sexe;

	/**
	 * identifiantPersonnel
	 */
	@Column(name = "SIDENTIFIANT_PERSONNEL", length = 16)
	private String identifiantPersonnel;

	/**
	 * dateNaissance
	 */
	@Column(name = "DDATE_NAISSANCE")
	private Date dateNaissance;

	/**
	 * statutIndividu
	 */
	@Column(name = "SSTATUT_INDIVIDU", length = 1)
	private String statutIndividu;

	/**
	 * codeTitre
	 */
	@Column(name = "SCODE_TITRE", length = 3)
	private String codeTitre;

	/**
	 * nationalite
	 */
	@Column(name = "SNATIONALITE", length = 2)
	private String nationalite;

	/**
	 * autreNationalite
	 */
	@Column(name = "SAUTRE_NATIONALITE", length = 2)
	private String autreNationalite;

	/**
	 * nonFusionnable
	 */
	@Column(name = "SNON_FUSIONNABLE", length = 1, nullable = false)
	private String nonFusionnable;

	/**
	 * siteCreation
	 */
	@Column(name = "SSITE_CREATION", length = 10, nullable = false)
	private String siteCreation;

	/**
	 * signatureCreation
	 */
	@Column(name = "SSIGNATURE_CREATION", length = 16, nullable = false)
	private String signatureCreation;

	/**
	 * dateCreation
	 */
	@Column(name = "DDATE_CREATION", nullable = false)
	private Date dateCreation;

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
	 * dateModification
	 */
	@Column(name = "DDATE_MODIFICATION")
	private Date dateModification;

	/**
	 * siteFraudeur
	 */
	@Column(name = "SSITE_FRAUDEUR", length = 10)
	private String siteFraudeur;

	/**
	 * signatureFraudeur
	 */
	@Column(name = "SSIGNATURE_FRAUDEUR", length = 16)
	private String signatureFraudeur;

	/**
	 * dateModifFraudeur
	 */
	@Column(name = "DDATE_MOD_FRAUDEUR")
	private Date dateModifFraudeur;

	/**
	 * siteMotDePasse
	 */
	@Column(name = "SSITE_MOT_PASSE", length = 10)
	private String siteMotDePasse;

	/**
	 * signatureMotDePasse
	 */
	@Column(name = "SSIGNATURE_MOT_PASSE", length = 16)
	private String signatureMotDePasse;

	/**
	 * dateModifMotDePasse
	 */
	@Column(name = "DDATE_MOD_MOT_DE_PASSE")
	private Date dateModifMotDePasse;

	/**
	 * fraudeurCarteBancaire
	 */
	@Column(name = "SFRAUDEUR_CARTE_BANCAIRE", length = 1)
	private String fraudeurCarteBancaire;

	/**
	 * tierUtiliseCommePiege
	 */
	@Column(name = "STIER_UTILISE_COMME_PIEGE", length = 1)
	private String tierUtiliseCommePiege;

	/**
	 * aliasNom1
	 */
	@Column(name = "SALIAS_NOM1", length = 35)
	private String aliasNom1;

	/**
	 * aliasNom2
	 */
	@Column(name = "SALIAS_NOM2", length = 35)
	private String aliasNom2;

	/**
	 * aliasPrenom1
	 */
	@Column(name = "SALIAS_PRE1", length = 25)
	private String aliasPrenom1;

	/**
	 * aliasPrenom2
	 */
	@Column(name = "SALIAS_PRE2", length = 25)
	private String aliasPrenom2;

	/**
	 * aliasCivilite1
	 */
	@Column(name = "SALIAS_CIV1", length = 4)
	private String aliasCivilite1;

	/**
	 * aliasCivilite2
	 */
	@Column(name = "SALIAS_CIV2", length = 4)
	private String aliasCivilite2;

	/**
	 * indicNomPrenom
	 */
	@Column(name = "SINDICT_NP", length = 40)
	private String indicNomPrenom;

	/**
	 * indicNom
	 */
	@Column(name = "SINDICT_NOM", length = 35)
	private String indicNom;

	/**
	 * indcons
	 */
	@Column(name = "SINDCONS", length = 30)
	private String indcons;

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
	 * provAmex
	 */
	@Column(name = "SPROV_AMEX", length = 1)
	private String provAmex;

	/**
	 * cieGest
	 */
	@Column(name = "SCIE_GEST", length = 3)
	private String cieGest;

	/**
	 * nomTypo1
	 */
	@Column(name = "SNOM_TYPO", length = 140)
	private String nomTypo1;

	/**
	 * prenomTypo1
	 */
	@Column(name = "SPRENOM_TYPO", length = 140)
	private String prenomTypo1;

	/* PROTECTED REGION ID(_Lkp80jKeEeCdBYomX6pnCg p) ENABLED START */
	/**
	 * email
	 */
	// 1 -> *
	@OneToMany(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "SGIN", nullable = true, updatable = false)
	@ForeignKey(name = "FK_EMAILS_INDIVIDUS_ALL")
	@Filter(name = "emailStatus")
	private Set<Email> email;
	/* PROTECTED REGION END */

	/**
	 * telecoms
	 */
	// 1 <-> *
	@OneToMany(mappedBy = "individu")
	private Set<Telecoms> telecoms;

	/**
	 * default constructor
	 */
	public IndividuLight() {
		// empty constructor
	}

	/**
	 * full constructor
	 *
	 * @param pSgin
	 *            sgin
	 * @param pType
	 *            type
	 * @param pVersion
	 *            version
	 * @param pCivilite
	 *            civilite
	 * @param pMotDePasse
	 *            motDePasse
	 * @param pNom
	 *            nom
	 * @param pNomTypo
	 *            nomTypo
	 * @param pAlias
	 *            alias
	 * @param pPrenom
	 *            prenom
	 * @param pPrenomTypo
	 *            prenomTypo
	 * @param pSecondPrenom
	 *            secondPrenom
	 * @param pAliasPrenom
	 *            aliasPrenom
	 * @param pSexe
	 *            sexe
	 * @param pIdentifiantPersonnel
	 *            identifiantPersonnel
	 * @param pDateNaissance
	 *            dateNaissance
	 * @param pStatutIndividu
	 *            statutIndividu
	 * @param pCodeTitre
	 *            codeTitre
	 * @param pNationalite
	 *            nationalite
	 * @param pAutreNationalite
	 *            autreNationalite
	 * @param pNonFusionnable
	 *            nonFusionnable
	 * @param pSiteCreation
	 *            siteCreation
	 * @param pSignatureCreation
	 *            signatureCreation
	 * @param pDateCreation
	 *            dateCreation
	 * @param pSiteModification
	 *            siteModification
	 * @param pSignatureModification
	 *            signatureModification
	 * @param pDateModification
	 *            dateModification
	 * @param pSiteFraudeur
	 *            siteFraudeur
	 * @param pSignatureFraudeur
	 *            signatureFraudeur
	 * @param pDateModifFraudeur
	 *            dateModifFraudeur
	 * @param pSiteMotDePasse
	 *            siteMotDePasse
	 * @param pSignatureMotDePasse
	 *            signatureMotDePasse
	 * @param pDateModifMotDePasse
	 *            dateModifMotDePasse
	 * @param pFraudeurCarteBancaire
	 *            fraudeurCarteBancaire
	 * @param pTierUtiliseCommePiege
	 *            tierUtiliseCommePiege
	 * @param pAliasNom1
	 *            aliasNom1
	 * @param pAliasNom2
	 *            aliasNom2
	 * @param pAliasPrenom1
	 *            aliasPrenom1
	 * @param pAliasPrenom2
	 *            aliasPrenom2
	 * @param pAliasCivilite1
	 *            aliasCivilite1
	 * @param pAliasCivilite2
	 *            aliasCivilite2
	 * @param pIndicNomPrenom
	 *            indicNomPrenom
	 * @param pIndicNom
	 *            indicNom
	 * @param pIndcons
	 *            indcons
	 * @param pGinFusion
	 *            ginFusion
	 * @param pDateFusion
	 *            dateFusion
	 * @param pProvAmex
	 *            provAmex
	 * @param pCieGest
	 *            cieGest
	 * @param pNomTypo1
	 *            nomTypo1
	 * @param pPrenomTypo1
	 *            prenomTypo1
	 */
	public IndividuLight(String pSgin, String pType, Integer pVersion, String pCivilite, String pMotDePasse,
			String pNom, byte[] pNomTypo, String pAlias, String pPrenom, byte[] pPrenomTypo, String pSecondPrenom,
			String pAliasPrenom, String pSexe, String pIdentifiantPersonnel, Date pDateNaissance,
			String pStatutIndividu, String pCodeTitre, String pNationalite, String pAutreNationalite,
			String pNonFusionnable, String pSiteCreation, String pSignatureCreation, Date pDateCreation,
			String pSiteModification, String pSignatureModification, Date pDateModification, String pSiteFraudeur,
			String pSignatureFraudeur, Date pDateModifFraudeur, String pSiteMotDePasse, String pSignatureMotDePasse,
			Date pDateModifMotDePasse, String pFraudeurCarteBancaire, String pTierUtiliseCommePiege, String pAliasNom1,
			String pAliasNom2, String pAliasPrenom1, String pAliasPrenom2, String pAliasCivilite1,
			String pAliasCivilite2, String pIndicNomPrenom, String pIndicNom, String pIndcons, String pGinFusion,
			Date pDateFusion, String pProvAmex, String pCieGest, String pNomTypo1, String pPrenomTypo1) {
		this.sgin = pSgin;
		this.type = pType;
		this.version = pVersion;
		this.civilite = pCivilite;
		this.motDePasse = pMotDePasse;
		this.nom = pNom;
		this.nomTypo = pNomTypo;
		this.alias = pAlias;
		this.prenom = pPrenom;
		this.prenomTypo = pPrenomTypo;
		this.secondPrenom = pSecondPrenom;
		this.aliasPrenom = pAliasPrenom;
		this.sexe = pSexe;
		this.identifiantPersonnel = pIdentifiantPersonnel;
		this.dateNaissance = pDateNaissance;
		this.statutIndividu = pStatutIndividu;
		this.codeTitre = pCodeTitre;
		this.nationalite = pNationalite;
		this.autreNationalite = pAutreNationalite;
		this.nonFusionnable = pNonFusionnable;
		this.siteCreation = pSiteCreation;
		this.signatureCreation = pSignatureCreation;
		this.dateCreation = pDateCreation;
		this.siteModification = pSiteModification;
		this.signatureModification = pSignatureModification;
		this.dateModification = pDateModification;
		this.siteFraudeur = pSiteFraudeur;
		this.signatureFraudeur = pSignatureFraudeur;
		this.dateModifFraudeur = pDateModifFraudeur;
		this.siteMotDePasse = pSiteMotDePasse;
		this.signatureMotDePasse = pSignatureMotDePasse;
		this.dateModifMotDePasse = pDateModifMotDePasse;
		this.fraudeurCarteBancaire = pFraudeurCarteBancaire;
		this.tierUtiliseCommePiege = pTierUtiliseCommePiege;
		this.aliasNom1 = pAliasNom1;
		this.aliasNom2 = pAliasNom2;
		this.aliasPrenom1 = pAliasPrenom1;
		this.aliasPrenom2 = pAliasPrenom2;
		this.aliasCivilite1 = pAliasCivilite1;
		this.aliasCivilite2 = pAliasCivilite2;
		this.indicNomPrenom = pIndicNomPrenom;
		this.indicNom = pIndicNom;
		this.indcons = pIndcons;
		this.ginFusion = pGinFusion;
		this.dateFusion = pDateFusion;
		this.provAmex = pProvAmex;
		this.cieGest = pCieGest;
		this.nomTypo1 = pNomTypo1;
		this.prenomTypo1 = pPrenomTypo1;
	}

	/**
	 *
	 * @return alias
	 */
	public String getAlias() {
		return this.alias;
	}

	/**
	 *
	 * @return aliasCivilite1
	 */
	public String getAliasCivilite1() {
		return this.aliasCivilite1;
	}

	/**
	 *
	 * @param pAliasCivilite1
	 *            aliasCivilite1 value
	 */
	public void setAliasCivilite1(String pAliasCivilite1) {
		this.aliasCivilite1 = pAliasCivilite1;
	}

	/**
	 *
	 * @return aliasCivilite2
	 */
	public String getAliasCivilite2() {
		return this.aliasCivilite2;
	}

	/**
	 *
	 * @param pAliasCivilite2
	 *            aliasCivilite2 value
	 */
	public void setAliasCivilite2(String pAliasCivilite2) {
		this.aliasCivilite2 = pAliasCivilite2;
	}

	/**
	 *
	 * @return aliasNom1
	 */
	public String getAliasNom1() {
		return this.aliasNom1;
	}

	/**
	 *
	 * @return aliasNom2
	 */
	public String getAliasNom2() {
		return this.aliasNom2;
	}

	/**
	 *
	 * @return aliasPrenom
	 */
	public String getAliasPrenom() {
		return this.aliasPrenom;
	}

	/**
	 *
	 * @return aliasPrenom1
	 */
	public String getAliasPrenom1() {
		return this.aliasPrenom1;
	}

	/**
	 *
	 * @return aliasPrenom2
	 */
	public String getAliasPrenom2() {
		return this.aliasPrenom2;
	}

	/**
	 *
	 * @return autreNationalite
	 */
	public String getAutreNationalite() {
		return this.autreNationalite;
	}

	/**
	 *
	 * @param pAutreNationalite
	 *            autreNationalite value
	 */
	public void setAutreNationalite(String pAutreNationalite) {
		this.autreNationalite = pAutreNationalite;
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
	 * @return civilite
	 */
	public String getCivilite() {
		return this.civilite;
	}

	/**
	 *
	 * @param pCivilite
	 *            civilite value
	 */
	public void setCivilite(String pCivilite) {
		this.civilite = pCivilite;
	}

	/**
	 *
	 * @return codeTitre
	 */
	public String getCodeTitre() {
		return this.codeTitre;
	}

	/**
	 *
	 * @param pCodeTitre
	 *            codeTitre value
	 */
	public void setCodeTitre(String pCodeTitre) {
		this.codeTitre = pCodeTitre;
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
	 * @return dateModifFraudeur
	 */
	public Date getDateModifFraudeur() {
		return this.dateModifFraudeur;
	}

	/**
	 *
	 * @param pDateModifFraudeur
	 *            dateModifFraudeur value
	 */
	public void setDateModifFraudeur(Date pDateModifFraudeur) {
		this.dateModifFraudeur = pDateModifFraudeur;
	}

	/**
	 *
	 * @return dateModifMotDePasse
	 */
	public Date getDateModifMotDePasse() {
		return this.dateModifMotDePasse;
	}

	/**
	 *
	 * @param pDateModifMotDePasse
	 *            dateModifMotDePasse value
	 */
	public void setDateModifMotDePasse(Date pDateModifMotDePasse) {
		this.dateModifMotDePasse = pDateModifMotDePasse;
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
	 * @return dateNaissance
	 */
	public Date getDateNaissance() {
		return this.dateNaissance;
	}

	/**
	 *
	 * @param pDateNaissance
	 *            dateNaissance value
	 */
	public void setDateNaissance(Date pDateNaissance) {
		this.dateNaissance = pDateNaissance;
	}

	/**
	 *
	 * @return email
	 */
	public Set<Email> getEmail() {
		return this.email;
	}

	/**
	 *
	 * @param pEmail
	 *            email value
	 */
	public void setEmail(Set<Email> pEmail) {
		this.email = pEmail;
	}

	/**
	 *
	 * @return fraudeurCarteBancaire
	 */
	public String getFraudeurCarteBancaire() {
		return this.fraudeurCarteBancaire;
	}

	/**
	 *
	 * @param pFraudeurCarteBancaire
	 *            fraudeurCarteBancaire value
	 */
	public void setFraudeurCarteBancaire(String pFraudeurCarteBancaire) {
		this.fraudeurCarteBancaire = pFraudeurCarteBancaire;
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
	 * @return identifiantPersonnel
	 */
	public String getIdentifiantPersonnel() {
		return this.identifiantPersonnel;
	}

	/**
	 *
	 * @param pIdentifiantPersonnel
	 *            identifiantPersonnel value
	 */
	public void setIdentifiantPersonnel(String pIdentifiantPersonnel) {
		this.identifiantPersonnel = pIdentifiantPersonnel;
	}

	/**
	 *
	 * @return indcons
	 */
	public String getIndcons() {
		return this.indcons;
	}

	/**
	 *
	 * @param pIndcons
	 *            indcons value
	 */
	public void setIndcons(String pIndcons) {
		this.indcons = pIndcons;
	}

	/**
	 *
	 * @return indicNom
	 */
	public String getIndicNom() {
		return this.indicNom;
	}

	/**
	 *
	 * @param pIndicNom
	 *            indicNom value
	 */
	public void setIndicNom(String pIndicNom) {
		this.indicNom = pIndicNom;
	}

	/**
	 *
	 * @return indicNomPrenom
	 */
	public String getIndicNomPrenom() {
		return this.indicNomPrenom;
	}

	/**
	 *
	 * @param pIndicNomPrenom
	 *            indicNomPrenom value
	 */
	public void setIndicNomPrenom(String pIndicNomPrenom) {
		this.indicNomPrenom = pIndicNomPrenom;
	}

	/**
	 *
	 * @return motDePasse
	 */
	public String getMotDePasse() {
		return this.motDePasse;
	}

	/**
	 *
	 * @param pMotDePasse
	 *            motDePasse value
	 */
	public void setMotDePasse(String pMotDePasse) {
		this.motDePasse = pMotDePasse;
	}

	/**
	 *
	 * @return nationalite
	 */
	public String getNationalite() {
		return this.nationalite;
	}

	/**
	 *
	 * @param pNationalite
	 *            nationalite value
	 */
	public void setNationalite(String pNationalite) {
		this.nationalite = pNationalite;
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
	 * @return nomTypo
	 */
	public byte[] getNomTypo() {
		return this.nomTypo;
	}

	/**
	 *
	 * @param pNomTypo
	 *            nomTypo value
	 */
	public void setNomTypo(byte[] pNomTypo) {
		this.nomTypo = pNomTypo;
	}

	/**
	 *
	 * @return nomTypo1
	 */
	public String getNomTypo1() {
		return this.nomTypo1;
	}

	/**
	 *
	 * @param pNomTypo1
	 *            nomTypo1 value
	 */
	public void setNomTypo1(String pNomTypo1) {
		this.nomTypo1 = pNomTypo1;
	}

	/**
	 *
	 * @return nonFusionnable
	 */
	public String getNonFusionnable() {
		return this.nonFusionnable;
	}

	/**
	 *
	 * @param pNonFusionnable
	 *            nonFusionnable value
	 */
	public void setNonFusionnable(String pNonFusionnable) {
		this.nonFusionnable = pNonFusionnable;
	}

	/**
	 *
	 * @return prenom
	 */
	public String getPrenom() {
		return this.prenom;
	}

	/**
	 *
	 * @return prenomTypo
	 */
	public byte[] getPrenomTypo() {
		return this.prenomTypo;
	}

	/**
	 *
	 * @param pPrenomTypo
	 *            prenomTypo value
	 */
	public void setPrenomTypo(byte[] pPrenomTypo) {
		this.prenomTypo = pPrenomTypo;
	}

	/**
	 *
	 * @return prenomTypo1
	 */
	public String getPrenomTypo1() {
		return this.prenomTypo1;
	}

	/**
	 *
	 * @param pPrenomTypo1
	 *            prenomTypo1 value
	 */
	public void setPrenomTypo1(String pPrenomTypo1) {
		this.prenomTypo1 = pPrenomTypo1;
	}

	/**
	 *
	 * @return provAmex
	 */
	public String getProvAmex() {
		return this.provAmex;
	}

	/**
	 *
	 * @param pProvAmex
	 *            provAmex value
	 */
	public void setProvAmex(String pProvAmex) {
		this.provAmex = pProvAmex;
	}

	/**
	 *
	 * @return secondPrenom
	 */
	public String getSecondPrenom() {
		return this.secondPrenom;
	}

	/**
	 *
	 * @return sexe
	 */
	public String getSexe() {
		return this.sexe;
	}

	/**
	 *
	 * @param pSexe
	 *            sexe value
	 */
	public void setSexe(String pSexe) {
		this.sexe = pSexe;
	}

	/**
	 *
	 * @return sgin
	 */
	public String getSgin() {
		return this.sgin;
	}

	/**
	 *
	 * @param pSgin
	 *            sgin value
	 */
	public void setSgin(String pSgin) {
		this.sgin = pSgin;
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
	 * @return signatureFraudeur
	 */
	public String getSignatureFraudeur() {
		return this.signatureFraudeur;
	}

	/**
	 *
	 * @param pSignatureFraudeur
	 *            signatureFraudeur value
	 */
	public void setSignatureFraudeur(String pSignatureFraudeur) {
		this.signatureFraudeur = pSignatureFraudeur;
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
	 * @return signatureMotDePasse
	 */
	public String getSignatureMotDePasse() {
		return this.signatureMotDePasse;
	}

	/**
	 *
	 * @param pSignatureMotDePasse
	 *            signatureMotDePasse value
	 */
	public void setSignatureMotDePasse(String pSignatureMotDePasse) {
		this.signatureMotDePasse = pSignatureMotDePasse;
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
	 * @return siteFraudeur
	 */
	public String getSiteFraudeur() {
		return this.siteFraudeur;
	}

	/**
	 *
	 * @param pSiteFraudeur
	 *            siteFraudeur value
	 */
	public void setSiteFraudeur(String pSiteFraudeur) {
		this.siteFraudeur = pSiteFraudeur;
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
	 * @return siteMotDePasse
	 */
	public String getSiteMotDePasse() {
		return this.siteMotDePasse;
	}

	/**
	 *
	 * @param pSiteMotDePasse
	 *            siteMotDePasse value
	 */
	public void setSiteMotDePasse(String pSiteMotDePasse) {
		this.siteMotDePasse = pSiteMotDePasse;
	}

	/**
	 *
	 * @return statutIndividu
	 */
	public String getStatutIndividu() {
		return this.statutIndividu;
	}

	/**
	 *
	 * @param pStatutIndividu
	 *            statutIndividu value
	 */
	public void setStatutIndividu(String pStatutIndividu) {
		this.statutIndividu = pStatutIndividu;
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
	 * @return tierUtiliseCommePiege
	 */
	public String getTierUtiliseCommePiege() {
		return this.tierUtiliseCommePiege;
	}

	/**
	 *
	 * @param pTierUtiliseCommePiege
	 *            tierUtiliseCommePiege value
	 */
	public void setTierUtiliseCommePiege(String pTierUtiliseCommePiege) {
		this.tierUtiliseCommePiege = pTierUtiliseCommePiege;
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
	 * @param pType
	 *            type value
	 */
	public void setType(String pType) {
		/*
		 * PROTECTED REGION ID(toString_WGOOEPcJEd-Kx8TJdz7fGw_BUG_ReleaseV44)
		 * ENABLED START
		 */
		if (pType == null) {
			this.type = "I"; // BUG Release_V44 : Positionner une valeur par
			// defaut
		} else {
			this.type = pType;
		}
		/* PROTECTED REGION END */
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
	@Override
	public String toString() {
		String result = null;
		/* PROTECTED REGION ID(toString_WGOOEPcJEd-Kx8TJdz7fGw) ENABLED START */
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
		buffer.append("sgin=").append(getSgin());
		buffer.append(",");
		buffer.append("type=").append(getType());
		buffer.append(",");
		buffer.append("version=").append(getVersion());
		buffer.append(",");
		buffer.append("civilite=").append(getCivilite());
		buffer.append(",");
		buffer.append("motDePasse=").append(getMotDePasse());
		buffer.append(",");
		buffer.append("nom=").append(getNom());
		buffer.append(",");
		buffer.append("nomTypo=").append(getNomTypo());
		buffer.append(",");
		buffer.append("alias=").append(getAlias());
		buffer.append(",");
		buffer.append("prenom=").append(getPrenom());
		buffer.append(",");
		buffer.append("prenomTypo=").append(getPrenomTypo());
		buffer.append(",");
		buffer.append("secondPrenom=").append(getSecondPrenom());
		buffer.append(",");
		buffer.append("aliasPrenom=").append(getAliasPrenom());
		buffer.append(",");
		buffer.append("sexe=").append(getSexe());
		buffer.append(",");
		buffer.append("identifiantPersonnel=").append(getIdentifiantPersonnel());
		buffer.append(",");
		buffer.append("dateNaissance=").append(getDateNaissance());
		buffer.append(",");
		buffer.append("statutIndividu=").append(getStatutIndividu());
		buffer.append(",");
		buffer.append("codeTitre=").append(getCodeTitre());
		buffer.append(",");
		buffer.append("nationalite=").append(getNationalite());
		buffer.append(",");
		buffer.append("autreNationalite=").append(getAutreNationalite());
		buffer.append(",");
		buffer.append("nonFusionnable=").append(getNonFusionnable());
		buffer.append(",");
		buffer.append("siteCreation=").append(getSiteCreation());
		buffer.append(",");
		buffer.append("signatureCreation=").append(getSignatureCreation());
		buffer.append(",");
		buffer.append("dateCreation=").append(getDateCreation());
		buffer.append(",");
		buffer.append("siteModification=").append(getSiteModification());
		buffer.append(",");
		buffer.append("signatureModification=").append(getSignatureModification());
		buffer.append(",");
		buffer.append("dateModification=").append(getDateModification());
		buffer.append(",");
		buffer.append("siteFraudeur=").append(getSiteFraudeur());
		buffer.append(",");
		buffer.append("signatureFraudeur=").append(getSignatureFraudeur());
		buffer.append(",");
		buffer.append("dateModifFraudeur=").append(getDateModifFraudeur());
		buffer.append(",");
		buffer.append("siteMotDePasse=").append(getSiteMotDePasse());
		buffer.append(",");
		buffer.append("signatureMotDePasse=").append(getSignatureMotDePasse());
		buffer.append(",");
		buffer.append("dateModifMotDePasse=").append(getDateModifMotDePasse());
		buffer.append(",");
		buffer.append("fraudeurCarteBancaire=").append(getFraudeurCarteBancaire());
		buffer.append(",");
		buffer.append("tierUtiliseCommePiege=").append(getTierUtiliseCommePiege());
		buffer.append(",");
		buffer.append("aliasNom1=").append(getAliasNom1());
		buffer.append(",");
		buffer.append("aliasNom2=").append(getAliasNom2());
		buffer.append(",");
		buffer.append("aliasPrenom1=").append(getAliasPrenom1());
		buffer.append(",");
		buffer.append("aliasPrenom2=").append(getAliasPrenom2());
		buffer.append(",");
		buffer.append("aliasCivilite1=").append(getAliasCivilite1());
		buffer.append(",");
		buffer.append("aliasCivilite2=").append(getAliasCivilite2());
		buffer.append(",");
		buffer.append("indicNomPrenom=").append(getIndicNomPrenom());
		buffer.append(",");
		buffer.append("indicNom=").append(getIndicNom());
		buffer.append(",");
		buffer.append("indcons=").append(getIndcons());
		buffer.append(",");
		buffer.append("ginFusion=").append(getGinFusion());
		buffer.append(",");
		buffer.append("dateFusion=").append(getDateFusion());
		buffer.append(",");
		buffer.append("provAmex=").append(getProvAmex());
		buffer.append(",");
		buffer.append("cieGest=").append(getCieGest());
		buffer.append(",");
		buffer.append("nomTypo1=").append(getNomTypo1());
		buffer.append(",");
		buffer.append("prenomTypo1=").append(getPrenomTypo1());
		buffer.append("]");
		return buffer.toString();
	}

	/**
	 * getNomSC
	 *
	 * @return The getNomSC as <code>String</code>
	 */
	public String getNomSC() {
		String result = null;
		try {
			if (nomTypo != null && nomTypo.length > 0) {
				result = new String(nomTypo, "UTF-8");
			}
		} catch (UnsupportedEncodingException e) {
			IndividuLight.log.fatal(e);
			throw new PersistenceException(e);
		}
		return result;
	}

	/**
	 * getPrenomSC
	 *
	 * @return The getPrenomSC as <code>String</code>
	 */
	public String getPrenomSC() {
		String result = null;
		try {
			if (prenomTypo != null && prenomTypo.length > 0) {
				result = new String(prenomTypo, "UTF-8");
			}
		} catch (UnsupportedEncodingException e) {
			IndividuLight.log.fatal(e);
			throw new PersistenceException(e);
		}
		return result;
	}

	/**
	 * determineNewGin
	 *
	 * @return The determineNewGin as <code>String</code>
	 */
	public String determineNewGin() {
		// TODO method determineNewGin() to implement
		throw new UnsupportedOperationException();
	}

	/**
	 * setNom
	 *
	 * @param pNom
	 *            in String
	 */
	public void setNom(String pNom) {
		nom = normalize(pNom);
	}

	/**
	 * setAlias
	 *
	 * @param pAlias
	 *            in String
	 */
	public void setAlias(String pAlias) {
		alias = normalize(pAlias);
	}

	/**
	 * setPrenom
	 *
	 * @param pPrenom
	 *            in String
	 */
	public void setPrenom(String pPrenom) {
		prenom = normalize(pPrenom);
	}

	/**
	 * setSecondPrenom
	 *
	 * @param pSecondPrenom
	 *            in String
	 */
	public void setSecondPrenom(String pSecondPrenom) {
		secondPrenom = normalize(pSecondPrenom);
	}

	/**
	 * setAliasPrenom
	 *
	 * @param pAliasPrenom
	 *            in String
	 */
	public void setAliasPrenom(String pAliasPrenom) {
		aliasPrenom = normalize(pAliasPrenom);
	}

	/**
	 * setAliasNom1
	 *
	 * @param pAliasNom1
	 *            in String
	 */
	public void setAliasNom1(String pAliasNom1) {
		aliasNom1 = normalize(pAliasNom1);
	}

	/**
	 * setAliasNom2
	 *
	 * @param pAliasNom2
	 *            in String
	 */
	public void setAliasNom2(String pAliasNom2) {
		aliasNom2 = normalize(pAliasNom2);
	}

	/**
	 * setAliasPrenom1
	 *
	 * @param pAliasPrenom1
	 *            in String
	 */
	public void setAliasPrenom1(String pAliasPrenom1) {
		aliasPrenom1 = normalize(aliasPrenom1);
	}

	/**
	 * setAliasPrenom2
	 *
	 * @param pAliasPrenom2
	 *            in String
	 */
	public void setAliasPrenom2(String pAliasPrenom2) {
		aliasPrenom2 = normalize(aliasPrenom2);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		IndividuLight.log.error("this == obj : " + this == obj);
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj)) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final IndividuLight other = (IndividuLight) obj;

		IndividuLight.log.error("this.sgin != null && other.getSgin() != null : " + this.sgin != null && other.getSgin() != null);
		if (this.sgin != null && other.getSgin() != null) {
			return this.sgin.equals(other.getSgin());
		}

		return super.equals(other);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		int result = super.hashCode();

		return result;
	}

	/**
	 * This method is aimed to normalize a given string by:
	 * <ul>
	 * <li>replacing special characters</li>
	 * <li>transforming to upper case</li>
	 * </ul>
	 */
	protected String normalize(String pStr) {

		// If string is empty normalization is useless
		if (StringUtils.isEmpty(pStr)) {
			return pStr;
		}

		// Replace special characters
		String cleanedStr = NormalizedStringUtils.normalizeString(pStr);
		// Transform to upper case
		cleanedStr = cleanedStr.toUpperCase();

		return cleanedStr;
	}

	/**
	 *
	 * @return dernier tél fixe valide voire invalide dernièrement mis à jour
	 *         ainsi que le dernier tél mobile valide voire invalide
	 *         dernièrement mis à jour
	 */
	public Set<Telecoms> fetchLatestFixedAndMobilePhones() {

		Set<Telecoms> latestFixedAndMobilePhones = new HashSet<>();
		if (!this.getTelecoms().isEmpty()) {

			List<Telecoms> fixedPhones = new ArrayList<>();
			List<Telecoms> mobilePhones = new ArrayList<>();
			for (Telecoms tel : this.getTelecoms()) {

				if (MediumStatusEnum.VALID.toLiteral().equals(tel.getSstatut_medium())
						|| MediumStatusEnum.INVALID.toLiteral().equals(tel.getSstatut_medium())) {
					if (TerminalTypeEnum.FIX.toString().equals(tel.getSterminal())) {
						fixedPhones.add(tel);
					} else if (TerminalTypeEnum.MOBILE.toString().equals(tel.getSterminal())) {
						mobilePhones.add(tel);
					}
				}
			}

			Comparator<Telecoms> phoneComparator = new Comparator<Telecoms>() {

				@Override
				public int compare(Telecoms tel1, Telecoms tel2) {

					if (!tel1.getSstatut_medium().equals(tel2.getSstatut_medium())) {
						return tel1.getSstatut_medium().compareTo(tel2.getSstatut_medium());
					} else if (tel1.getDdate_modification() != null && tel2.getDdate_modification() != null
							&& !tel1.getDdate_modification().equals(tel2.getDdate_modification())) {
						return tel1.getDdate_modification().compareTo(tel2.getDdate_modification());
					} else if (tel1.getDdate_modification() == null && tel2.getDdate_modification() != null) {
						return 1;
					} else if (tel1.getDdate_modification() != null && tel2.getDdate_modification() == null) {
						return -1;
					} else {
						return tel1.getSain().compareTo(tel2.getSain());
					}
				}
			};

			if (!fixedPhones.isEmpty()) {

				Collections.sort(fixedPhones, phoneComparator);
				Collections.reverse(fixedPhones);
				latestFixedAndMobilePhones.add(fixedPhones.get(0));
			}

			if (!mobilePhones.isEmpty()) {

				Collections.sort(mobilePhones, phoneComparator);
				Collections.reverse(mobilePhones);
				latestFixedAndMobilePhones.add(mobilePhones.get(0));
			}
		}

		return latestFixedAndMobilePhones;
	}

}
