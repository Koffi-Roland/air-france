package com.airfrance.repind.dto.firme;

/*PROTECTED REGION ID(_QjebMODkEd-TxbwIaEembQ i) ENABLED START*/

// add not generated imports here

import com.airfrance.repind.dto.adresse.EmailDTO;
import com.airfrance.repind.dto.adresse.PostalAddressDTO;
import com.airfrance.repind.dto.adresse.TelecomsDTO;
import com.airfrance.repind.dto.agence.AgenceDTO;
import com.airfrance.repind.dto.profil.ProfilFirmeDTO;
import com.airfrance.repind.dto.profil.Profil_mereDTO;
import com.airfrance.repind.dto.role.BusinessRoleDTO;
import com.airfrance.repind.dto.zone.PmZoneDTO;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

/*PROTECTED REGION END*/

/**
 * <p>Title : PersonneMoraleDTO</p>
 * 
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
@JsonTypeInfo(use = Id.NAME, include = As.PROPERTY, property = "pmtype")
@JsonSubTypes({ 
	  @Type(value = AgenceDTO.class, name = "A"), 
	  @Type(value = EtablissementDTO.class, name = "T") 
	})
public abstract class PersonneMoraleDTO implements Serializable {
        
    /**
	 * 
	 */
	private static final long serialVersionUID = -870179102073891562L;


	/**
     * gin
     */
    private String gin;
        
        
    /**
     * version
     */
    private Integer version;
        
        
    /**
     * nom
     */
    private String nom;
        
        
    /**
     * statut
     */
    private String statut;
        
        
    /**
     * dateModificationStatut
     */
    private Date dateModificationStatut;
        
        
    /**
     * activiteLocal
     */
    private String activiteLocal;
        
        
    /**
     * ginFusion
     */
    private String ginFusion;
        
        
    /**
     * dateFusion
     */
    private Date dateFusion;
        
        
    /**
     * signatureFusion
     */
    private String signatureFusion;
        
        
    /**
     * dateCreation
     */
    private Date dateCreation;
        
        
    /**
     * signatureCreation
     */
    private String signatureCreation;
        
        
    /**
     * dateModification
     */
    private Date dateModification;
        
        
    /**
     * signatureModification
     */
    private String signatureModification;
        
        
    /**
     * codeIndustrie
     */
    private String codeIndustrie;
        
        
    /**
     * typeDemarchage
     */
    private String typeDemarchage;
        
        
    /**
     * siteInternet
     */
    private String siteInternet;
        
        
    /**
     * indictNom
     */
    private String indictNom;
        
        
    /**
     * cieGest
     */
    private String cieGest;
        
        
    /**
     * codeSource
     */
    private String codeSource;
        
        
    /**
     * codeSupport
     */
    private String codeSupport;
        
        
    /**
     * statutJuridique
     */
    private String statutJuridique;
        
        
    /**
     * dateModificationNom
     */
    private Date dateModificationNom;
        
        
    /**
     * siteCreation
     */
    private String siteCreation;
        
        
    /**
     * siteModification
     */
    private String siteModification;
        
        
    /**
     * businessRoles
     */
    private Set<BusinessRoleDTO> businessRoles;
        
        
    /**
     * chiffres
     */
    private Set<ChiffreDTO> chiffres;
        
        
    /**
     * compagniesAlliees
     */
    private List<CompagnieAllieeDTO> compagniesAlliees;
        
        
    /**
     * emails
     */
    private Set<EmailDTO> emails;
        
        
    /**
     * enfants
     */
    private Set<PersonneMoraleDTO> enfants;
        
        
    /**
     * membres
     */
    private List<MembreDTO> membres;
        
        
    /**
     * numerosIdent
     */
    private Set<NumeroIdentDTO> numerosIdent;
        
        
    /**
     * parent
     */
    private PersonneMoraleDTO parent;
        
        
    /**
     * personnesMoralesGerantes
     */
    private Set<GestionPMDTO> personnesMoralesGerantes;
        
        
    /**
     * personnesMoralesGerees
     */
    private Set<GestionPMDTO> personnesMoralesGerees;
        
        
    /**
     * pmZones
     */
    private Set<PmZoneDTO> pmZones;
        
        
    /**
     * postalAddresses
     */
    private List<PostalAddressDTO> postalAddresses;
        
        
    /**
     * profilFirme
     */
    private ProfilFirmeDTO profilFirme;
        
        
    /**
     * profils
     */
    private Set<Profil_mereDTO> profils;
        
        
    /**
     * segmentations
     */
    private Set<SegmentationDTO> segmentations;
        
        
    /**
     * selfBookingTool
     */
    private SelfBookingToolDTO selfBookingTool;
        
        
    /**
     * synonymes
     */
    private Set<SynonymeDTO> synonymes;
        
        
    /**
     * telecoms
     */
    private Set<TelecomsDTO> telecoms;
        

    /*PROTECTED REGION ID(_QjebMODkEd-TxbwIaEembQ u var) ENABLED START*/
    // add your custom variables here if necessary
    
    /*PROTECTED REGION END*/

    
    
    /** 
     * default constructor 
     */
    public PersonneMoraleDTO() {
    
    }
    
    
    /** 
     * full constructor
     * @param pGin gin
     * @param pVersion version
     * @param pNom nom
     * @param pStatut statut
     * @param pDateModificationStatut dateModificationStatut
     * @param pActiviteLocal activiteLocal
     * @param pGinFusion ginFusion
     * @param pDateFusion dateFusion
     * @param pSignatureFusion signatureFusion
     * @param pDateCreation dateCreation
     * @param pSignatureCreation signatureCreation
     * @param pDateModification dateModification
     * @param pSignatureModification signatureModification
     * @param pCodeIndustrie codeIndustrie
     * @param pTypeDemarchage typeDemarchage
     * @param pSiteInternet siteInternet
     * @param pIndictNom indictNom
     * @param pCieGest cieGest
     * @param pCodeSource codeSource
     * @param pCodeSupport codeSupport
     * @param pStatutJuridique statutJuridique
     * @param pDateModificationNom dateModificationNom
     * @param pSiteCreation siteCreation
     * @param pSiteModification siteModification
     */
    public PersonneMoraleDTO(String pGin, Integer pVersion, String pNom, String pStatut, Date pDateModificationStatut, String pActiviteLocal, String pGinFusion, Date pDateFusion, String pSignatureFusion, Date pDateCreation, String pSignatureCreation, Date pDateModification, String pSignatureModification, String pCodeIndustrie, String pTypeDemarchage, String pSiteInternet, String pIndictNom, String pCieGest, String pCodeSource, String pCodeSupport, String pStatutJuridique, Date pDateModificationNom, String pSiteCreation, String pSiteModification) {
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
     * @param pActiviteLocal activiteLocal value
     */
    public void setActiviteLocal(String pActiviteLocal) {
        this.activiteLocal = pActiviteLocal;
    }

    /**
     *
     * @return businessRoles
     */
    public Set<BusinessRoleDTO> getBusinessRoles() {
        return this.businessRoles;
    }

    /**
     *
     * @param pBusinessRoles businessRoles value
     */
    public void setBusinessRoles(Set<BusinessRoleDTO> pBusinessRoles) {
        this.businessRoles = pBusinessRoles;
    }

    /**
     *
     * @return chiffres
     */
    public Set<ChiffreDTO> getChiffres() {
        return this.chiffres;
    }

    /**
     *
     * @param pChiffres chiffres value
     */
    public void setChiffres(Set<ChiffreDTO> pChiffres) {
        this.chiffres = pChiffres;
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
     * @return codeIndustrie
     */
    public String getCodeIndustrie() {
        return this.codeIndustrie;
    }

    /**
     *
     * @param pCodeIndustrie codeIndustrie value
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
     * @param pCodeSource codeSource value
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
     * @param pCodeSupport codeSupport value
     */
    public void setCodeSupport(String pCodeSupport) {
        this.codeSupport = pCodeSupport;
    }

    /**
     *
     * @return compagniesAlliees
     */
    public List<CompagnieAllieeDTO> getCompagniesAlliees() {
        return this.compagniesAlliees;
    }

    /**
     *
     * @param pCompagniesAlliees compagniesAlliees value
     */
    public void setCompagniesAlliees(List<CompagnieAllieeDTO> pCompagniesAlliees) {
        this.compagniesAlliees = pCompagniesAlliees;
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
     * @return dateModificationNom
     */
    public Date getDateModificationNom() {
        return this.dateModificationNom;
    }

    /**
     *
     * @param pDateModificationNom dateModificationNom value
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
     * @param pDateModificationStatut dateModificationStatut value
     */
    public void setDateModificationStatut(Date pDateModificationStatut) {
        this.dateModificationStatut = pDateModificationStatut;
    }

    /**
     *
     * @return emails
     */
    public Set<EmailDTO> getEmails() {
        return this.emails;
    }

    /**
     *
     * @param pEmails emails value
     */
    public void setEmails(Set<EmailDTO> pEmails) {
        this.emails = pEmails;
    }

    /**
     *
     * @return enfants
     */
    public Set<PersonneMoraleDTO> getEnfants() {
        return this.enfants;
    }

    /**
     *
     * @param pEnfants enfants value
     */
    public void setEnfants(Set<PersonneMoraleDTO> pEnfants) {
        this.enfants = pEnfants;
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
     * @return indictNom
     */
    public String getIndictNom() {
        return this.indictNom;
    }

    /**
     *
     * @param pIndictNom indictNom value
     */
    public void setIndictNom(String pIndictNom) {
        this.indictNom = pIndictNom;
    }

    /**
     *
     * @return membres
     */
    public List<MembreDTO> getMembres() {
        return this.membres;
    }

    /**
     *
     * @param pMembres membres value
     */
    public void setMembres(List<MembreDTO> pMembres) {
        this.membres = pMembres;
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
     * @return numerosIdent
     */
    public Set<NumeroIdentDTO> getNumerosIdent() {
        return this.numerosIdent;
    }

    /**
     *
     * @param pNumerosIdent numerosIdent value
     */
    public void setNumerosIdent(Set<NumeroIdentDTO> pNumerosIdent) {
        this.numerosIdent = pNumerosIdent;
    }

    /**
     *
     * @return parent
     */
    public PersonneMoraleDTO getParent() {
        return this.parent;
    }

    /**
     *
     * @param pParent parent value
     */
    public void setParent(PersonneMoraleDTO pParent) {
        this.parent = pParent;
    }

    /**
     *
     * @return personnesMoralesGerantes
     */
    public Set<GestionPMDTO> getPersonnesMoralesGerantes() {
        return this.personnesMoralesGerantes;
    }

    /**
     *
     * @param pPersonnesMoralesGerantes personnesMoralesGerantes value
     */
    public void setPersonnesMoralesGerantes(Set<GestionPMDTO> pPersonnesMoralesGerantes) {
        this.personnesMoralesGerantes = pPersonnesMoralesGerantes;
    }

    /**
     *
     * @return personnesMoralesGerees
     */
    public Set<GestionPMDTO> getPersonnesMoralesGerees() {
        return this.personnesMoralesGerees;
    }

    /**
     *
     * @param pPersonnesMoralesGerees personnesMoralesGerees value
     */
    public void setPersonnesMoralesGerees(Set<GestionPMDTO> pPersonnesMoralesGerees) {
        this.personnesMoralesGerees = pPersonnesMoralesGerees;
    }

    /**
     *
     * @return pmZones
     */
    public Set<PmZoneDTO> getPmZones() {
        return this.pmZones;
    }

    /**
     *
     * @param pPmZones pmZones value
     */
    public void setPmZones(Set<PmZoneDTO> pPmZones) {
        this.pmZones = pPmZones;
    }

    /**
     *
     * @return postalAddresses
     */
    public List<PostalAddressDTO> getPostalAddresses() {
        return this.postalAddresses;
    }

    /**
     *
     * @param pPostalAddresses postalAddresses value
     */
    public void setPostalAddresses(List<PostalAddressDTO> pPostalAddresses) {
        this.postalAddresses = pPostalAddresses;
    }

    /**
     *
     * @return profilFirme
     */
    public ProfilFirmeDTO getProfilFirme() {
        return this.profilFirme;
    }

    /**
     *
     * @param pProfilFirme profilFirme value
     */
    public void setProfilFirme(ProfilFirmeDTO pProfilFirme) {
        this.profilFirme = pProfilFirme;
    }

    /**
     *
     * @return profils
     */
    public Set<Profil_mereDTO> getProfils() {
        return this.profils;
    }

    /**
     *
     * @param pProfils profils value
     */
    public void setProfils(Set<Profil_mereDTO> pProfils) {
        this.profils = pProfils;
    }

    /**
     *
     * @return segmentations
     */
    public Set<SegmentationDTO> getSegmentations() {
        return this.segmentations;
    }

    /**
     *
     * @param pSegmentations segmentations value
     */
    public void setSegmentations(Set<SegmentationDTO> pSegmentations) {
        this.segmentations = pSegmentations;
    }

    /**
     *
     * @return selfBookingTool
     */
    public SelfBookingToolDTO getSelfBookingTool() {
        return this.selfBookingTool;
    }

    /**
     *
     * @param pSelfBookingTool selfBookingTool value
     */
    public void setSelfBookingTool(SelfBookingToolDTO pSelfBookingTool) {
        this.selfBookingTool = pSelfBookingTool;
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
     * @return signatureFusion
     */
    public String getSignatureFusion() {
        return this.signatureFusion;
    }

    /**
     *
     * @param pSignatureFusion signatureFusion value
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
     * @return siteInternet
     */
    public String getSiteInternet() {
        return this.siteInternet;
    }

    /**
     *
     * @param pSiteInternet siteInternet value
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
     * @param pSiteModification siteModification value
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
     * @param pStatut statut value
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
     * @param pStatutJuridique statutJuridique value
     */
    public void setStatutJuridique(String pStatutJuridique) {
        this.statutJuridique = pStatutJuridique;
    }

    /**
     *
     * @return synonymes
     */
    public Set<SynonymeDTO> getSynonymes() {
        return this.synonymes;
    }

    /**
     *
     * @param pSynonymes synonymes value
     */
    public void setSynonymes(Set<SynonymeDTO> pSynonymes) {
        this.synonymes = pSynonymes;
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
     * @return typeDemarchage
     */
    public String getTypeDemarchage() {
        return this.typeDemarchage;
    }

    /**
     *
     * @param pTypeDemarchage typeDemarchage value
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
        /*PROTECTED REGION ID(toString_QjebMODkEd-TxbwIaEembQ) ENABLED START*/
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

    /*PROTECTED REGION ID(_QjebMODkEd-TxbwIaEembQ u m) ENABLED START*/
    // add your custom methods here if necessary
   
    /*PROTECTED REGION END*/

}
