package com.afklm.rigui.dto.individu;

/*PROTECTED REGION ID(_VDY-cPcREd-Kx8TJdz7fGw i) ENABLED START*/

// add not generated imports here

import com.afklm.rigui.dto.adresse.EmailDTO;
import com.afklm.rigui.dto.adresse.PostalAddressDTO;
import com.afklm.rigui.dto.adresse.TelecomsDTO;
import com.afklm.rigui.dto.delegation.DelegationDataDTO;
import com.afklm.rigui.dto.external.ExternalIdentifierDTO;
import com.afklm.rigui.dto.identifier.InternalIdentifierDTO;
import com.afklm.rigui.dto.preference.PreferenceDTO;
import com.afklm.rigui.dto.profil.Profil_mereDTO;
import com.afklm.rigui.dto.profil.ProfilsDTO;
import com.afklm.rigui.dto.role.BusinessRoleDTO;
import com.afklm.rigui.dto.role.RoleContratsDTO;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

/*PROTECTED REGION END*/

/**
 * <p>Title : IndividuDTO</p>
 * 
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
public class IndividuDTO  implements Serializable {
        
    /**
	 * 
	 */
	private static final long serialVersionUID = -5716848148473541880L;


	/**
     * sgin
     */
    private String sgin;
        
        
	/**
     * type
     */
    private String type;

        
    /**
     * version
     */
    private Integer version;
        

    /**
     * civilite
     */
    private String civilite;
        
        
    /**
     * motDePasse
     */
    private String motDePasse;
        
        
    /**
     * nom
     */
    private String nom;
        
        
    /**
     * nomSC
     */
    private String nomSC;
        
        
    /**
     * alias
     */
    private String alias;
        
        
    /**
     * prenom
     */
    private String prenom;
        
        
    /**
     * prenomSC
     */
    private String prenomSC;
        
        
    /**
     * secondPrenom
     */
    private String secondPrenom;
        
        
    /**
     * aliasPrenom
     */
    private String aliasPrenom;
        
        
    /**
     * sexe
     */
    private String sexe;
        
        
    /**
     * identifiantPersonnel
     */
    private String identifiantPersonnel;
        
        
    /**
     * dateNaissance
     */
    private Date dateNaissance;
        
        
    /**
     * statutIndividu
     */
    private String statutIndividu;
        
        
    /**
     * codeTitre
     */
    private String codeTitre;
        
        
    /**
     * nationalite
     */
    private String nationalite;
        
        
    /**
     * autreNationalite
     */
    private String autreNationalite;
        
        
    /**
     * nonFusionnable
     */
    private String nonFusionnable;
        
        
    /**
     * siteCreation
     */
    private String siteCreation;
        
        
    /**
     * signatureCreation
     */
    private String signatureCreation;
        
        
    /**
     * dateCreation
     */
    private Date dateCreation;
        
        
    /**
     * siteModification
     */
    private String siteModification;
        
        
    /**
     * signatureModification
     */
    private String signatureModification;
        
        
    /**
     * dateModification
     */
    private Date dateModification;
        
        
    /**
     * siteFraudeur
     */
    private String siteFraudeur;
        
        
    /**
     * signatureFraudeur
     */
    private String signatureFraudeur;
        
        
    /**
     * dateModifFraudeur
     */
    private Date dateModifFraudeur;
        
        
    /**
     * siteMotDePasse
     */
    private String siteMotDePasse;
        
        
    /**
     * signatureMotDePasse
     */
    private String signatureMotDePasse;
        
        
    /**
     * dateModifMotDePasse
     */
    private Date dateModifMotDePasse;
        
        
    /**
     * fraudeurCarteBancaire
     */
    private String fraudeurCarteBancaire;
        
        
    /**
     * tierUtiliseCommePiege
     */
    private String tierUtiliseCommePiege;
        
        
    /**
     * aliasNom1
     */
    private String aliasNom1;
        
        
    /**
     * aliasNom2
     */
    private String aliasNom2;
        
        
    /**
     * aliasPrenom1
     */
    private String aliasPrenom1;
        
        
    /**
     * aliasPrenom2
     */
    private String aliasPrenom2;
        
        
    /**
     * aliasCivilite1
     */
    private String aliasCivilite1;
        
        
    /**
     * aliasCivilite2
     */
    private String aliasCivilite2;
        
        
    /**
     * indicNomPrenom
     */
    private String indicNomPrenom;
        
        
    /**
     * indicNom
     */
    private String indicNom;
        
        
    /**
     * indcons
     */
    private String indcons;
        
        
    /**
     * ginFusion
     */
    private String ginFusion;
        
        
    /**
     * dateFusion
     */
    private Date dateFusion;
        
        
    /**
     * provAmex
     */
    private String provAmex;
        
        
    /**
     * cieGest
     */
    private String cieGest;
        
        
    /**
     * codeLangue
     */
    private String codeLangue;
        
        
    /**
     * accountdatadto
     */
    private AccountDataDTO accountdatadto;
        
        
    /**
     * alertDTO
     */
    private Set<AlertDTO> alertDTO;
        
        
    /**
     * communicationpreferencesdto
     */
    private List<CommunicationPreferencesDTO> communicationpreferencesdto;
        
        
    /**
     * delegateListDTO
     */
    private List<DelegationDataDTO> delegateListDTO;
        
        
    /**
     * delegatorListDTO
     */
    private List<DelegationDataDTO> delegatorListDTO;
        
        
    /**
     * emaildto
     */
    private Set<EmailDTO> emaildto;
        
        
    /**
     * externalIdentifierList
     */
    private List<ExternalIdentifierDTO> externalIdentifierList;
        
    
    /**
     * identifierDTO
     */
    private Set<InternalIdentifierDTO> identifierDTO;
       
        
    /**
     * postaladdressdto
     */
    private List<PostalAddressDTO> postaladdressdto;
    
    
    /**
     * preferenceDTO
     */
    private List<PreferenceDTO> preferenceDTO;
        
        
    /**
     * prefilledNumbers
     */
    private List<PrefilledNumbersDTO> prefilledNumbers;
        
        
    /**
     * profil_meredto
     */
    private Set<Profil_mereDTO> profil_meredto;
        
        
    /**
     * profilsdto
     */
    private ProfilsDTO profilsdto;
        
        
    /**
     * rolecontratsdto
     */
    private Set<RoleContratsDTO> rolecontratsdto;
        
    
    /**
     * businessRolesDTO
     */
    private Set<BusinessRoleDTO> businessRolesDTO;
    
    
    /**
     * telecoms
     */
    private Set<TelecomsDTO> telecoms;
        
        
    /**
     * usageclientsdto
     */
    private Set<UsageClientsDTO> usageclientsdto;
        

    /*PROTECTED REGION ID(_VDY-cPcREd-Kx8TJdz7fGw u var) ENABLED START*/
    // add your custom variables here if necessary
    
    private Set<WarningDTO> warnings;
    /*PROTECTED REGION END*/

    
    
    /** 
     * default constructor 
     */
    public IndividuDTO() {
    //empty constructor
    }
    
    
    /** 
     * full constructor
     * @param pSgin sgin
     * @param pVersion version
     * @param pCivilite civilite
     * @param pMotDePasse motDePasse
     * @param pNom nom
     * @param pNomSC nomSC
     * @param pAlias alias
     * @param pPrenom prenom
     * @param pPrenomSC prenomSC
     * @param pSecondPrenom secondPrenom
     * @param pAliasPrenom aliasPrenom
     * @param pSexe sexe
     * @param pIdentifiantPersonnel identifiantPersonnel
     * @param pDateNaissance dateNaissance
     * @param pStatutIndividu statutIndividu
     * @param pCodeTitre codeTitre
     * @param pNationalite nationalite
     * @param pAutreNationalite autreNationalite
     * @param pNonFusionnable nonFusionnable
     * @param pSiteCreation siteCreation
     * @param pSignatureCreation signatureCreation
     * @param pDateCreation dateCreation
     * @param pSiteModification siteModification
     * @param pSignatureModification signatureModification
     * @param pDateModification dateModification
     * @param pSiteFraudeur siteFraudeur
     * @param pSignatureFraudeur signatureFraudeur
     * @param pDateModifFraudeur dateModifFraudeur
     * @param pSiteMotDePasse siteMotDePasse
     * @param pSignatureMotDePasse signatureMotDePasse
     * @param pDateModifMotDePasse dateModifMotDePasse
     * @param pFraudeurCarteBancaire fraudeurCarteBancaire
     * @param pTierUtiliseCommePiege tierUtiliseCommePiege
     * @param pAliasNom1 aliasNom1
     * @param pAliasNom2 aliasNom2
     * @param pAliasPrenom1 aliasPrenom1
     * @param pAliasPrenom2 aliasPrenom2
     * @param pAliasCivilite1 aliasCivilite1
     * @param pAliasCivilite2 aliasCivilite2
     * @param pIndicNomPrenom indicNomPrenom
     * @param pIndicNom indicNom
     * @param pIndcons indcons
     * @param pGinFusion ginFusion
     * @param pDateFusion dateFusion
     * @param pProvAmex provAmex
     * @param pCieGest cieGest
     * @param pCodeLangue codeLangue
     */
    public IndividuDTO(String pSgin, Integer pVersion, String pCivilite, String pMotDePasse, String pNom, String pNomSC, String pAlias, String pPrenom, String pPrenomSC, String pSecondPrenom, String pAliasPrenom, String pSexe, String pIdentifiantPersonnel, Date pDateNaissance, String pStatutIndividu, String pCodeTitre, String pNationalite, String pAutreNationalite, String pNonFusionnable, String pSiteCreation, String pSignatureCreation, Date pDateCreation, String pSiteModification, String pSignatureModification, Date pDateModification, String pSiteFraudeur, String pSignatureFraudeur, Date pDateModifFraudeur, String pSiteMotDePasse, String pSignatureMotDePasse, Date pDateModifMotDePasse, String pFraudeurCarteBancaire, String pTierUtiliseCommePiege, String pAliasNom1, String pAliasNom2, String pAliasPrenom1, String pAliasPrenom2, String pAliasCivilite1, String pAliasCivilite2, String pIndicNomPrenom, String pIndicNom, String pIndcons, String pGinFusion, Date pDateFusion, String pProvAmex, String pCieGest, String pCodeLangue) {
        this.sgin = pSgin;
        this.version = pVersion;
        this.civilite = pCivilite;
        this.motDePasse = pMotDePasse;
        this.nom = pNom;
        this.nomSC = pNomSC;
        this.alias = pAlias;
        this.prenom = pPrenom;
        this.prenomSC = pPrenomSC;
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
        this.codeLangue = pCodeLangue;
    }

    /** 
     *
     * @return accountdatadto
     */
    public AccountDataDTO getAccountdatadto() {
        return this.accountdatadto;
    }

    /**
     *
     * @param pAccountdatadto accountdatadto value
     */
    public void setAccountdatadto(AccountDataDTO pAccountdatadto) {
        this.accountdatadto = pAccountdatadto;
    }

    /**
     *
     * @return alertDTO
     */
    public Set<AlertDTO> getAlertDTO() {
        return this.alertDTO;
    }

    /**
     *
     * @param pAlertDTO alertDTO value
     */
    public void setAlertDTO(Set<AlertDTO> pAlertDTO) {
        this.alertDTO = pAlertDTO;
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
     * @param pAlias alias value
     */
    public void setAlias(String pAlias) {
        this.alias = pAlias;
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
     * @param pAliasCivilite1 aliasCivilite1 value
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
     * @param pAliasCivilite2 aliasCivilite2 value
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
     * @param pAliasNom1 aliasNom1 value
     */
    public void setAliasNom1(String pAliasNom1) {
        this.aliasNom1 = pAliasNom1;
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
     * @param pAliasNom2 aliasNom2 value
     */
    public void setAliasNom2(String pAliasNom2) {
        this.aliasNom2 = pAliasNom2;
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
     * @param pAliasPrenom aliasPrenom value
     */
    public void setAliasPrenom(String pAliasPrenom) {
        this.aliasPrenom = pAliasPrenom;
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
     * @param pAliasPrenom1 aliasPrenom1 value
     */
    public void setAliasPrenom1(String pAliasPrenom1) {
        this.aliasPrenom1 = pAliasPrenom1;
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
     * @param pAliasPrenom2 aliasPrenom2 value
     */
    public void setAliasPrenom2(String pAliasPrenom2) {
        this.aliasPrenom2 = pAliasPrenom2;
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
     * @param pAutreNationalite autreNationalite value
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
     * @param pCieGest cieGest value
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
     * @param pCivilite civilite value
     */
    public void setCivilite(String pCivilite) {
        this.civilite = pCivilite;
    }

    /**
     *
     * @return codeLangue
     */
    public String getCodeLangue() {
        return this.codeLangue;
    }

    /**
     *
     * @param pCodeLangue codeLangue value
     */
    public void setCodeLangue(String pCodeLangue) {
        this.codeLangue = pCodeLangue;
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
     * @param pCodeTitre codeTitre value
     */
    public void setCodeTitre(String pCodeTitre) {
        this.codeTitre = pCodeTitre;
    }

    /**
     *
     * @return communicationpreferencesdto
     */
    public List<CommunicationPreferencesDTO> getCommunicationpreferencesdto() {
        return this.communicationpreferencesdto;
    }

    /**
     *
     * @param pCommunicationpreferencesdto communicationpreferencesdto value
     */
    public void setCommunicationpreferencesdto(List<CommunicationPreferencesDTO> pCommunicationpreferencesdto) {
        this.communicationpreferencesdto = pCommunicationpreferencesdto;
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
     * @return dateFusion
     */
    public Date getDateFusion() {
        return this.dateFusion;
    }

    /**
     *
     * @param pDateFusion dateFusion value
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
     * @param pDateModifFraudeur dateModifFraudeur value
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
     * @param pDateModifMotDePasse dateModifMotDePasse value
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
     * @param pDateModification dateModification value
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
     * @param pDateNaissance dateNaissance value
     */
    public void setDateNaissance(Date pDateNaissance) {
        this.dateNaissance = pDateNaissance;
    }

    /**
     *
     * @return delegateListDTO
     */
    public List<DelegationDataDTO> getDelegateListDTO() {
        return this.delegateListDTO;
    }

    /**
     *
     * @param pDelegateListDTO delegateListDTO value
     */
    public void setDelegateListDTO(List<DelegationDataDTO> pDelegateListDTO) {
        this.delegateListDTO = pDelegateListDTO;
    }

    /**
     *
     * @return delegatorListDTO
     */
    public List<DelegationDataDTO> getDelegatorListDTO() {
        return this.delegatorListDTO;
    }

    /**
     *
     * @param pDelegatorListDTO delegatorListDTO value
     */
    public void setDelegatorListDTO(List<DelegationDataDTO> pDelegatorListDTO) {
        this.delegatorListDTO = pDelegatorListDTO;
    }

    /**
     *
     * @return emaildto
     */
    public Set<EmailDTO> getEmaildto() {
        return this.emaildto;
    }

    /**
     *
     * @param pEmaildto emaildto value
     */
    public void setEmaildto(Set<EmailDTO> pEmaildto) {
        this.emaildto = pEmaildto;
    }

    /**
     *
     * @return externalIdentifierList
     */
    public List<ExternalIdentifierDTO> getExternalIdentifierList() {
        return this.externalIdentifierList;
    }

    /**
     *
     * @param pExternalIdentifierList externalIdentifierList value
     */
    public void setExternalIdentifierList(List<ExternalIdentifierDTO> pExternalIdentifierList) {
        this.externalIdentifierList = pExternalIdentifierList;
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
     * @param pFraudeurCarteBancaire fraudeurCarteBancaire value
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
     * @param pGinFusion ginFusion value
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
     * @param pIdentifiantPersonnel identifiantPersonnel value
     */
    public void setIdentifiantPersonnel(String pIdentifiantPersonnel) {
        this.identifiantPersonnel = pIdentifiantPersonnel;
    }
    
    /**
    *
    * @return identifierDTO
    */
    public Set<InternalIdentifierDTO> getIdentifierDTO() {
    	return this.identifierDTO;
    }

    /**
     *
     * @param pIdentifierDTO identifierDTO value
     */
    public void setIdentifierDTO(Set<InternalIdentifierDTO> pIdentifierDTO) {
       this.identifierDTO = pIdentifierDTO;
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
     * @param pIndcons indcons value
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
     * @param pIndicNom indicNom value
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
     * @param pIndicNomPrenom indicNomPrenom value
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
     * @param pMotDePasse motDePasse value
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
     * @param pNationalite nationalite value
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
     * @param pNom nom value
     */
    public void setNom(String pNom) {
        this.nom = pNom;
    }

    /**
     *
     * @return nomSC
     */
    public String getNomSC() {
        return this.nomSC;
    }

    /**
     *
     * @param pNomSC nomSC value
     */
    public void setNomSC(String pNomSC) {
        this.nomSC = pNomSC;
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
     * @param pNonFusionnable nonFusionnable value
     */
    public void setNonFusionnable(String pNonFusionnable) {
        this.nonFusionnable = pNonFusionnable;
    }

    /**
     *
     * @return postaladdressdto
     */
    public List<PostalAddressDTO> getPostaladdressdto() {
        return this.postaladdressdto;
    }

    /**
     *
     * @param pPostaladdressdto postaladdressdto value
     */
    public void setPostaladdressdto(List<PostalAddressDTO> pPostaladdressdto) {
        this.postaladdressdto = pPostaladdressdto;
    }
    
    /**
    *
    * @return preferenceDTO
    */
	public List<PreferenceDTO> getPreferenceDTO() {
	    return this.preferenceDTO;
	}
	
	/**
	*
	* @param pPreferenceDTO preferenceDTO value
	*/
    public void setPreferenceDTO(List<PreferenceDTO> pPreferenceDTO) {
        this.preferenceDTO = pPreferenceDTO;
    }

    /**
     *
     * @return prefilledNumbers
     */
    public List<PrefilledNumbersDTO> getPrefilledNumbers() {
        return this.prefilledNumbers;
    }

    /**
     *
     * @param pPrefilledNumbers prefilledNumbers value
     */
    public void setPrefilledNumbers(List<PrefilledNumbersDTO> pPrefilledNumbers) {
        this.prefilledNumbers = pPrefilledNumbers;
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
     * @param pPrenom prenom value
     */
    public void setPrenom(String pPrenom) {
        this.prenom = pPrenom;
    }

    /**
     *
     * @return prenomSC
     */
    public String getPrenomSC() {
        return this.prenomSC;
    }

    /**
     *
     * @param pPrenomSC prenomSC value
     */
    public void setPrenomSC(String pPrenomSC) {
        this.prenomSC = pPrenomSC;
    }

    /**
     *
     * @return profil_meredto
     */
    public Set<Profil_mereDTO> getProfil_meredto() {
        return this.profil_meredto;
    }

    /**
     *
     * @param pProfil_meredto profil_meredto value
     */
    public void setProfil_meredto(Set<Profil_mereDTO> pProfil_meredto) {
        this.profil_meredto = pProfil_meredto;
    }

    /**
     *
     * @return profilsdto
     */
    public ProfilsDTO getProfilsdto() {
        return this.profilsdto;
    }

    /**
     *
     * @param pProfilsdto profilsdto value
     */
    public void setProfilsdto(ProfilsDTO pProfilsdto) {
        this.profilsdto = pProfilsdto;
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
     * @param pProvAmex provAmex value
     */
    public void setProvAmex(String pProvAmex) {
        this.provAmex = pProvAmex;
    }

    /**
     *
     * @return rolecontratsdto
     */
    public Set<RoleContratsDTO> getRolecontratsdto() {
        return this.rolecontratsdto;
    }

    /**
     *
     * @param pRolecontratsdto rolecontratsdto value
     */
    public void setRolecontratsdto(Set<RoleContratsDTO> pRolecontratsdto) {
        this.rolecontratsdto = pRolecontratsdto;
    }
    
    /**
    *
    * @return businessRolesDTO
    */
   public Set<BusinessRoleDTO> getBusinessRoleDTO() {
       return this.businessRolesDTO;
   }

   /**
    *
    * @param pRolecontratsdto rolecontratsdto value
    */
   public void setBusinessRolesDTO(Set<BusinessRoleDTO> pBusinessRolesDTO) {
       this.businessRolesDTO = pBusinessRolesDTO;
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
     * @param pSecondPrenom secondPrenom value
     */
    public void setSecondPrenom(String pSecondPrenom) {
        this.secondPrenom = pSecondPrenom;
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
     * @param pSexe sexe value
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
     * @param pSgin sgin value
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
     * @param pSignatureCreation signatureCreation value
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
     * @param pSignatureFraudeur signatureFraudeur value
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
     * @param pSignatureModification signatureModification value
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
     * @param pSignatureMotDePasse signatureMotDePasse value
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
     * @param pSiteCreation siteCreation value
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
     * @param pSiteFraudeur siteFraudeur value
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
     * @param pSiteModification siteModification value
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
     * @param pSiteMotDePasse siteMotDePasse value
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
     * @param pStatutIndividu statutIndividu value
     */
    public void setStatutIndividu(String pStatutIndividu) {
        this.statutIndividu = pStatutIndividu;
    }

    /**
     *
     * @return telecoms
     */
    public Set<TelecomsDTO> getTelecoms() {
        return this.telecoms;
    }

    /**
     *
     * @param pTelecoms telecoms value
     */
    public void setTelecoms(Set<TelecomsDTO> pTelecoms) {
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
     * @param pTierUtiliseCommePiege tierUtiliseCommePiege value
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
     * @param pType type value
     */
    public void setType(String pType) {
        this.type = pType;
    }

    /**
    *
     * @return usageclientsdto
    */
    public Set<UsageClientsDTO> getUsageclientsdto() {
        return this.usageclientsdto;
   }

   /**
    *
     * @param pUsageclientsdto usageclientsdto value
    */
    public void setUsageclientsdto(Set<UsageClientsDTO> pUsageclientsdto) {
        this.usageclientsdto = pUsageclientsdto;
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
     * @param pVersion version value
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
        /*PROTECTED REGION ID(toString_VDY-cPcREd-Kx8TJdz7fGw) ENABLED START*/
        result = toStringImpl();
        /*PROTECTED REGION END*/
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
        buffer.append("nomSC=").append(getNomSC());
        buffer.append(",");
        buffer.append("alias=").append(getAlias());
        buffer.append(",");
        buffer.append("prenom=").append(getPrenom());
        buffer.append(",");
        buffer.append("prenomSC=").append(getPrenomSC());
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
        buffer.append("codeLangue=").append(getCodeLangue());
        buffer.append("]");
        return buffer.toString();
    }

    /*PROTECTED REGION ID(_VDY-cPcREd-Kx8TJdz7fGw u m) ENABLED START*/
    public PostalAddressDTO getAdrPostPriviligie() {
    	PostalAddressDTO result = null;
    	if (getPostaladdressdto()!=null) {
	    	for (PostalAddressDTO adrPost : getPostaladdressdto()) {
				if ("V".equalsIgnoreCase(adrPost.getSstatut_medium()) || "T".equalsIgnoreCase(adrPost.getSstatut_medium())) {
					if (result == null) {
						result = adrPost;
					} else {
						int compareCodeMedium = adrPost.getScode_medium().compareTo(result.getScode_medium());
						if (compareCodeMedium<0) {
							result = adrPost;
						} else if (compareCodeMedium==0) {
							Date resultDate = result.getDdate_creation();
							if (result.getDdate_modification()!=null && result.getDdate_modification().after(resultDate)) {
								resultDate = result.getDdate_modification();
								
							}
							Date adrPostDate = adrPost.getDdate_creation();
							if (adrPost.getDdate_modification()!=null && adrPost.getDdate_modification().after(resultDate)) {
								adrPostDate = adrPost.getDdate_modification();
								
							}
							
							if (adrPostDate.after(resultDate)) {
								result = adrPost;
							}

						}						
					}
				}
			}
    	}
    	return result;
    }


    public void setWarnings(Set<WarningDTO> warnings) {
        this.warnings = warnings;
    }


    public Set<WarningDTO> getWarnings() {
        return warnings;
    }

    /*PROTECTED REGION END*/

}
