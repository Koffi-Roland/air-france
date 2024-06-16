package com.airfrance.repind.entity.firme;

import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.repind.entity.adresse.Email;
import com.airfrance.repind.entity.adresse.PostalAddress;
import com.airfrance.repind.entity.adresse.Telecoms;
import com.airfrance.repind.entity.adresse.enums.MediumCodeEnum;
import com.airfrance.repind.entity.adresse.enums.MediumStatusEnum;
import com.airfrance.repind.entity.profil.ProfilFirme;
import com.airfrance.repind.entity.profil.Profil_mere;
import com.airfrance.repind.entity.role.BusinessRole;
import com.airfrance.repind.entity.zone.PmZone;
import com.airfrance.repind.entity.zone.ZoneComm;
import com.airfrance.repind.entity.zone.ZoneVente;
import com.airfrance.repind.entity.zone.enums.PrivilegedLinkEnum;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.util.Assert;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

/*PROTECTED REGION END*/


/**
 * <p>Title : PersonneMorale.java</p>
 * BO PersonneMorale
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
@Entity

@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(
    name="STYPE",
    discriminatorType=DiscriminatorType.STRING)
@DiscriminatorValue("")

@Table(name="PERS_MORALE")
@GenericGenerator(name="seq_id", strategy="com.airfrance.repind.entity.PersonneMoraleHibernateSequence")
public abstract class PersonneMorale implements Serializable {

/*PROTECTED REGION ID(serialUID _04rowODbEd-TxbwIaEembQ) ENABLED START*/
   /**
   * Determines if a de-serialized file is compatible with this class.
   *
   * Maintainers must change this value if and only if the new version
   * of this class is not compatible with old versions. See Sun docs
   * for <a href=http://java.sun.com/j2se/1.5.0/docs/guide/serialization
   * /serialization/spec/class.html#4100> details. </a>
   *
   * Not necessary to include in first version of the class, but
   * included here as a reminder of its importance.
   */
    private static final long serialVersionUID = 1L;
    
    /** logger */
    private static final Log log = LogFactory.getLog(PersonneMorale.class);
/*PROTECTED REGION END*/

            
    /**
     * gin
     */
    @Id
    @Column(name="SGIN", length=12, nullable=false, unique=true)
    @GeneratedValue(generator="seq_id")
    private String gin;
        
            
    /**
     * version
     */
    @Version
    @Column(name="IVERSION", nullable=false)
    private Integer version;
        
            
    /**
     * nom
     */
    @Column(name="SNOM", length=45, nullable=false)
    private String nom;
        
            
    /**
     * statut
     */
    @Column(name="SSTATUT", length=2, nullable=false)
    private String statut;
        
            
    /**
     * dateModificationStatut
     */
    @Column(name="DDATE_MODIFICATION_STATUT")
    private Date dateModificationStatut;
        

	/**
     * activiteLocal
     */
    @Column(name="SACTIVITE_LOCAL", length=4)
    private String activiteLocal;
        
            
    /**
     * ginFusion
     */
    @Column(name="SGIN_FUSION", length=12)
    private String ginFusion;
        
            
    /**
     * dateFusion
     */
    @Column(name="DDATE_FUSION")
    private Date dateFusion;
        
            
    /**
     * signatureFusion
     */
    @Column(name="SSIGNATURE_FUSION", length=16)
    private String signatureFusion;
        
            
    /**
     * dateCreation
     */
    @Column(name="DDATE_CREATION", nullable=false)
    private Date dateCreation;
        
            
    /**
     * signatureCreation
     */
    @Column(name="SSIGNATURE_CREATION", length=16, nullable=false)
    private String signatureCreation;
        
            
    /**
     * dateModification
     */
    @Column(name="DDATE_MODIFICATION", nullable=false)
    private Date dateModification;
        
            
    /**
     * signatureModification
     */
    @Column(name="SSIGNATURE_MODIFICATION", length=16, nullable=false)
    private String signatureModification;
        
            
    /**
     * codeIndustrie
     */
    @Column(name="SCODE_INDUSTRIE", length=2)
    private String codeIndustrie;
        
            
    /**
     * typeDemarchage
     */
    @Column(name="STYPE_DEMARCHAGE", length=1)
    private String typeDemarchage;
        
            
    /**
     * siteInternet
     */
    @Column(name="SSITE_INTERNET", length=60)
    private String siteInternet;
        
            
    /**
     * indictNom
     */
    @Column(name="SINDICT_NOM", length=45)
    private String indictNom;
        
            
    /**
     * cieGest
     */
    @Column(name="SCIE_GEST", length=3)
    private String cieGest;
        
            
    /**
     * codeSource
     */
    @Column(name="SCODE_SOURCE", length=2)
    private String codeSource;
        
            
    /**
     * codeSupport
     */
    @Column(name="SCODE_SUPPORT", length=2)
    private String codeSupport;
        
            
    /**
     * statutJuridique
     */
    @Column(name="SSTATUT_JURIDIQUE", length=4)
    private String statutJuridique;
        
            
    /**
     * dateModificationNom
     */
    @Column(name="DDATE_MODIFICATION_SNOM")
    private Date dateModificationNom;
        
            
    /**
     * siteCreation
     */
    @Column(name="SSITE_CREATION", length=10, nullable=false)
    private String siteCreation;
        
            
    /**
     * siteModification
     */
    @Column(name="SSITE_MODIFICATION", length=10, nullable=false)
    private String siteModification;
        
            
    /**
     * businessRoles
     */
    // 1 -> *
    @OneToMany()
    @JoinColumn(name="SGIN_PM", nullable=true, foreignKey = @ForeignKey(name = "FK_BUSINESS_PM"))
    private Set<BusinessRole> businessRoles;
        
            
    /**
     * chiffres
     */
    // 1 <-> * 
    @OneToMany(mappedBy="personneMorale")
    private Set<Chiffre> chiffres;
        
            
    /**
     * compagniesAlliees
     */
    // 1 <-> * 
    @OneToMany(mappedBy="personneMorale")
    private Set<CompagnieAlliee> compagniesAlliees;
        
            
    /**
     * emails
     */
    // 1 <-> * 
    @OneToMany(mappedBy="personneMorale")
    private Set<Email> emails;
        
            
    /**
     * enfants
     */
    // 1 <-> * 
    @OneToMany(mappedBy="parent")
    private Set<PersonneMorale> enfants;
        
            
    /**
     * membres
     */
    // 1 <-> * 
    @OneToMany(mappedBy="personneMorale")
    private Set<Membre> membres;
        
            
    /**
     * numerosIdent
     */
    // 1 <-> * 
    @OneToMany(mappedBy="personneMorale")
    private Set<NumeroIdent> numerosIdent;
        
            
    /**
     * parent
     */
    // * <-> 1
    @ManyToOne()
    @JoinColumn(name="SGIN_PERE", nullable=true, foreignKey = @ForeignKey(name = "FK_PERS_MORALE_SGIN_PERE"))
    private PersonneMorale parent;
        
            
    /**
     * personnesMoralesGerantes
     */
    // 1 <-> * 
    @OneToMany(mappedBy="personneMoraleGeree")
    private Set<GestionPM> personnesMoralesGerantes;
        
            
    /**
     * personnesMoralesGerees
     */
    // 1 <-> * 
    @OneToMany(mappedBy="personneMoraleGerante")
    private Set<GestionPM> personnesMoralesGerees;
        
            
    /**
     * pmZones
     */
    // 1 <-> * 
    @OneToMany(mappedBy="personneMorale")
    private Set<PmZone> pmZones;
        
            
    /**
     * postalAddresses
     */
    // 1 <-> * 
    @OneToMany(mappedBy="personneMorale")
    private Set<PostalAddress> postalAddresses;
        
            
    /**
     * profilFirme
     */
    // 1 <-> 1
    @OneToOne()
    @JoinColumn(name="SGIN", foreignKey = @ForeignKey(name = "FK_PM_PRO"))
    private ProfilFirme profilFirme;
        
            
    /**
     * profils
     */
    // 1 -> *
    @OneToMany()
    @JoinColumn(name="SGIN_PM", nullable=true, foreignKey = @ForeignKey(name = "FK_PROFIL_MERE_PERS_MORALE"))
    private Set<Profil_mere> profils;
        
            
    /**
     * segmentations
     */
    // 1 <-> * 
    @OneToMany(mappedBy="personneMorale")
    private Set<Segmentation> segmentations;
        
            
    /**
     * selfBookingTool
     */
    // 1 <-> 1
    @OneToOne(cascade=CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private SelfBookingTool selfBookingTool;
        
            
    /**
     * synonymes
     */
    // 1 <-> * 
    @OneToMany(mappedBy="personneMorale")
    private Set<Synonyme> synonymes;
            
    /**
     * telecoms
     */
    // 1 <-> * 
    @OneToMany(mappedBy="personneMorale")
    private Set<Telecoms> telecoms;
        
    /*PROTECTED REGION ID(_04rowODbEd-TxbwIaEembQ u var) ENABLED START*/
    // add your custom variables here if necessary
    
    /*PROTECTED REGION END*/
    
    /** 
     * default constructor 
     */
    public PersonneMorale() {
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
    public PersonneMorale(String pGin, Integer pVersion, String pNom, String pStatut, Date pDateModificationStatut, String pActiviteLocal, String pGinFusion, Date pDateFusion, String pSignatureFusion, Date pDateCreation, String pSignatureCreation, Date pDateModification, String pSignatureModification, String pCodeIndustrie, String pTypeDemarchage, String pSiteInternet, String pIndictNom, String pCieGest, String pCodeSource, String pCodeSupport, String pStatutJuridique, Date pDateModificationNom, String pSiteCreation, String pSiteModification) {
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
    public Set<BusinessRole> getBusinessRoles() {
        return this.businessRoles;
    }

    /**
     *
     * @param pBusinessRoles businessRoles value
     */
    public void setBusinessRoles(Set<BusinessRole> pBusinessRoles) {
        this.businessRoles = pBusinessRoles;
    }

    /**
     *
     * @return chiffres
     */
    public Set<Chiffre> getChiffres() {
        return this.chiffres;
    }

    /**
     *
     * @param pChiffres chiffres value
     */
    public void setChiffres(Set<Chiffre> pChiffres) {
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
    public Set<CompagnieAlliee> getCompagniesAlliees() {
        return this.compagniesAlliees;
    }

    /**
     *
     * @param pCompagniesAlliees compagniesAlliees value
     */
    public void setCompagniesAlliees(Set<CompagnieAlliee> pCompagniesAlliees) {
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
    public Set<Email> getEmails() {
        return this.emails;
    }

    /**
     *
     * @param pEmails emails value
     */
    public void setEmails(Set<Email> pEmails) {
        this.emails = pEmails;
    }

    /**
     *
     * @return enfants
     */
    public Set<PersonneMorale> getEnfants() {
        return this.enfants;
    }

    /**
     *
     * @param pEnfants enfants value
     */
    public void setEnfants(Set<PersonneMorale> pEnfants) {
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
    public Set<NumeroIdent> getNumerosIdent() {
        return this.numerosIdent;
    }

    /**
     *
     * @param pNumerosIdent numerosIdent value
     */
    public void setNumerosIdent(Set<NumeroIdent> pNumerosIdent) {
        this.numerosIdent = pNumerosIdent;
    }

    /**
     *
     * @return parent
     */
    public PersonneMorale getParent() {
        return this.parent;
    }

    /**
     *
     * @param pParent parent value
     */
    public void setParent(PersonneMorale pParent) {
        this.parent = pParent;
    }

    /**
     *
     * @return personnesMoralesGerantes
     */
    public Set<GestionPM> getPersonnesMoralesGerantes() {
        return this.personnesMoralesGerantes;
    }

    /**
     *
     * @param pPersonnesMoralesGerantes personnesMoralesGerantes value
     */
    public void setPersonnesMoralesGerantes(Set<GestionPM> pPersonnesMoralesGerantes) {
        this.personnesMoralesGerantes = pPersonnesMoralesGerantes;
    }

    /**
     *
     * @return personnesMoralesGerees
     */
    public Set<GestionPM> getPersonnesMoralesGerees() {
        return this.personnesMoralesGerees;
    }

    /**
     *
     * @param pPersonnesMoralesGerees personnesMoralesGerees value
     */
    public void setPersonnesMoralesGerees(Set<GestionPM> pPersonnesMoralesGerees) {
        this.personnesMoralesGerees = pPersonnesMoralesGerees;
    }

    /**
     *
     * @return pmZones
     */
    public Set<PmZone> getPmZones() {
        return this.pmZones;
    }

    /**
     *
     * @param pPmZones pmZones value
     */
    public void setPmZones(Set<PmZone> pPmZones) {
        this.pmZones = pPmZones;
    }

    /**
     *
     * @return postalAddresses
     */
    public Set<PostalAddress> getPostalAddresses() {
        return this.postalAddresses;
    }

    /**
     *
     * @param pPostalAddresses postalAddresses value
     */
    public void setPostalAddresses(Set<PostalAddress> pPostalAddresses) {
        this.postalAddresses = pPostalAddresses;
    }

    /**
     *
     * @return profilFirme
     */
    public ProfilFirme getProfilFirme() {
        return this.profilFirme;
    }

    /**
     *
     * @param pProfilFirme profilFirme value
     */
    public void setProfilFirme(ProfilFirme pProfilFirme) {
        this.profilFirme = pProfilFirme;
    }

    /**
     *
     * @return profils
     */
    public Set<Profil_mere> getProfils() {
        return this.profils;
    }

    /**
     *
     * @param pProfils profils value
     */
    public void setProfils(Set<Profil_mere> pProfils) {
        this.profils = pProfils;
    }

    /**
     *
     * @return segmentations
     */
    public Set<Segmentation> getSegmentations() {
        return this.segmentations;
    }

    /**
     *
     * @param pSegmentations segmentations value
     */
    public void setSegmentations(Set<Segmentation> pSegmentations) {
        this.segmentations = pSegmentations;
    }

    /**
     *
     * @return selfBookingTool
     */
    public SelfBookingTool getSelfBookingTool() {
        return this.selfBookingTool;
    }

    /**
     *
     * @param pSelfBookingTool selfBookingTool value
     */
    public void setSelfBookingTool(SelfBookingTool pSelfBookingTool) {
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
    public Set<Synonyme> getSynonymes() {
        return this.synonymes;
    }

    /**
     *
     * @param pSynonymes synonymes value
     */
    public void setSynonymes(Set<Synonyme> pSynonymes) {
        this.synonymes = pSynonymes;
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
     * @param pTelecoms telecoms value
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
        /*PROTECTED REGION ID(toString_04rowODbEd-TxbwIaEembQ) ENABLED START*/
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

     
    
    /*PROTECTED REGION ID(equals hash _04rowODbEd-TxbwIaEembQ) ENABLED START*/

    /**
     * {@inheritDoc}
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
        final PersonneMorale other = (PersonneMorale) obj;

        // TODO: writes or generates equals method

        return super.equals(other);
    }

    /**
     * {@inheritDoc}
     * @see Object#hashCode()
     */
    @Override
    public int hashCode() {
        int result = super.hashCode();

        // TODO: writes or generates hashcode method

        return result;
    }
    
    /*PROTECTED REGION END*/
    
    /**
     * Generated implementation method for hashCode
     * You can stop calling it in the hashCode() generated in protected region if necessary
     * @return hashcode
     */
    private int hashCodeImpl() {
        return super.hashCode();
    }

    /**
     * Generated implementation method for equals
     * You can stop calling it in the equals() generated in protected region if necessary
     * @return if param equals the current object
     * @param obj Object to compare with current
     */
    private boolean equalsImpl(Object obj) {
        return super.equals(obj);
    }

    /*PROTECTED REGION ID(_04rowODbEd-TxbwIaEembQ u m) ENABLED START*/
    // add your custom methods here if necessary
    
    /**
     * @param pId zone link id
     * @return zone link found, null instead
     */
    public PmZone fetchZoneLinkById(Long pId) throws JrafDomainException {
        
        Assert.notNull(pId);       
        
        PmZone result = null;
        
        if (this.getPmZones() != null) {
            
            for (PmZone link : this.getPmZones()) {
                
                if (pId.equals(link.getCle())) {                    
                    
                    result = link;
                    break;
                }
            }
        }
        
        return result;
    }
    
    /**
     * @return liens zc
     */
    public List<PmZone> fetchCommercialZoneLinks() {
        
        List<PmZone> commercialZoneLinks = new ArrayList<PmZone>();
        
        if (this.getPmZones() != null) {

            for(PmZone zoneLink : this.getPmZones()) {
                
                if (zoneLink.getZoneDecoup() != null 
                	&& ZoneComm.class.equals(zoneLink.getZoneDecoup().getClass())) {
                    
                    commercialZoneLinks.add(zoneLink);
                }
            }
        }
        
        return commercialZoneLinks;
    }
    
    /**
     * @return liens zc privilégiés actifs (dans un monde parfait, un et un seul lien devrait remonter)
     */
    public List<PmZone> fetchActivePrivilegedCommercialZoneLinks() {
        
        List<PmZone> results = new ArrayList<PmZone>();
        
        Date now = new Date();
        
        if (this.getPmZones() != null) {
            
            for (PmZone link : this.getPmZones()) {
                
                if (ZoneComm.class.equals(link.getZoneDecoup().getClass())
                        && !now.before(link.getDateOuverture())
                        && PrivilegedLinkEnum.YES.equals(PrivilegedLinkEnum.fromLiteral(link.getLienPrivilegie()))
                        && (link.getDateFermeture() == null || !link.getDateFermeture().before(now))) {                    
                    
                    results.add(link);
                }
            }
        }
        
        if (results.size() > 1) {
            
            log.warn("LEGAL PERSON " + this.getGin() + " HAS " + results.size() + " SEVERAL ACTIVE PRIVILEGED ZC LINKS");
        }
        
        return results;
    }
    
    /**
     * @return liens zc privilégiés actifs (dans un monde parfait, un et un seul lien devrait remonter)
     */
    public List<PmZone> fetchPrivilegedCommercialZoneLinks() {
        
        List<PmZone> results = new ArrayList<PmZone>();
        
        if (this.getPmZones() != null) {
            
            for (PmZone link : this.getPmZones()) {
                
                if (ZoneComm.class.equals(link.getZoneDecoup().getClass())
                        && PrivilegedLinkEnum.YES.equals(PrivilegedLinkEnum.fromLiteral(link.getLienPrivilegie()))) {                    
                    
                    results.add(link);
                }
            }
        }
        
        return results;
    }
    
    /**
     * @return liens zc privilégiés historisés i.e datefin <> null et datefin < now
     */
    public List<PmZone> fetchPastPrivilegedCommercialZoneLinks() {
        
        List<PmZone> results = new ArrayList<PmZone>();
        
        Date now = new Date();
        
        if (this.getPmZones() != null) {
            
            for (PmZone link : this.getPmZones()) {
                
                if (ZoneComm.class.equals(link.getZoneDecoup().getClass())
                        && PrivilegedLinkEnum.YES.equals(PrivilegedLinkEnum.fromLiteral(link.getLienPrivilegie()))
                        && link.getDateFermeture() != null
                        && link.getDateFermeture().before(now)) {                    
                    
                    results.add(link);
                }
            }
        }
        
        return results;
    }
    
    /**
     * @return liens zv
     */
    public List<PmZone> fetchSalesZoneLinks() {
        
        List<PmZone> salesZoneLinks = new ArrayList<PmZone>();
        
        if (this.getPmZones() != null) {
            
            for (PmZone zoneLink : this.getPmZones()) {

                if (zoneLink.getZoneDecoup() != null
                        && ZoneVente.class.equals(zoneLink.getZoneDecoup().getClass())) {

                    salesZoneLinks.add(zoneLink);
                }
            }
        }
        
        return salesZoneLinks;
    }
    
    
    
    /**
     * @return liens zv privilégiés historisés i.e datefin <> null et datefin < now
     */
    public List<PmZone> fetchPastPrivilegedSalesZoneLinks() throws JrafDomainException {
        
        List<PmZone> results = new ArrayList<PmZone>();
        
        Date now = new Date();
        
        if (this.getPmZones() != null) {
            
            for (PmZone link : this.getPmZones()) {
                
                if (ZoneVente.class.equals(link.getZoneDecoup().getClass())
                        && PrivilegedLinkEnum.YES.equals(PrivilegedLinkEnum.fromLiteral(link.getLienPrivilegie()))
                        && link.getDateFermeture() != null
                        && link.getDateFermeture().before(now)) {                    
                    
                    results.add(link);
                }
            }
        }
        
        return results;
    }
    
	/**
	 * Retrieves the non-privileged commercial zone linked to the personne morale
	 * 
	 * @return the non-privileged commercial zone links
	 */
	public List<PmZone> fetchNonPrivilegedCommercialZoneLinks() {
		List<PmZone> results = new ArrayList<>();
		if (this.getPmZones() != null) {
			for (PmZone link : this.getPmZones()) {
				if (ZoneComm.class.equals(link.getZoneDecoup().getClass())
						&& PrivilegedLinkEnum.NO.equals(PrivilegedLinkEnum.fromLiteral(link.getLienPrivilegie()))) {
					results.add(link);
				}
			}
		}
		return results;
	}
    
    /**
     * @return valid localisation postal address
     */
    public PostalAddress fetchValidLocalisationPostalAddress() {
        
        PostalAddress validLocalisationPostalAddress = null;
        
        for (PostalAddress postalAddress : this.getPostalAddresses()) {
            
            if (MediumCodeEnum.LOCALISATION.equals(MediumCodeEnum.fromLiteral(postalAddress.getScode_medium()))
                    && MediumStatusEnum.VALID.equals(MediumStatusEnum.fromLiteral(postalAddress.getSstatut_medium()))) {
                
                validLocalisationPostalAddress = postalAddress;
                break;
            }
        }
        
        return validLocalisationPostalAddress;
    }
    
    /*PROTECTED REGION END*/

    @PreUpdate
    private void onPreUpdate() {
        this.dateModification = java.sql.Timestamp.valueOf(LocalDateTime.now(ZoneOffset.UTC));
    }
}
