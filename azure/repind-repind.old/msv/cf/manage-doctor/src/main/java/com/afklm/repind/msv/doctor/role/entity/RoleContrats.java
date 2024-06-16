package com.afklm.repind.msv.doctor.role.entity;

/*PROTECTED REGION ID(_3ifg0PcaEd-Kx8TJdz7fGw i) ENABLED START*/

// add not generated imports here

import lombok.Getter;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Date;

/*PROTECTED REGION END*/


/**
 * <p>Title : RoleContrats.java</p>
 * BO RoleContrats
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
/*PROTECTED REGION ID(@Entity _3ifg0PcaEd-Kx8TJdz7fGw) ENABLED START*/
@Entity


@Table(name="ROLE_CONTRATS")
public class RoleContrats implements Serializable {
/*PROTECTED REGION END*/

/*PROTECTED REGION ID(serialUID _3ifg0PcaEd-Kx8TJdz7fGw) ENABLED START*/
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
/*PROTECTED REGION END*/

            
    /**
     * srin
     */
    @Id
    @Column(name="SRIN", nullable=false, updatable=false)
    private String srin;
        
            
    /**
     * version
     */
    @Version
    @Column(name="IVERSION", nullable=false)
    @Getter private Integer version;
        
            
    /**
     * numeroContrat
     */
    @Column(name="SNUMERO_CONTRAT", length=20, nullable=false)
    @Getter private String numeroContrat;
        
            
    /**
     * gin
     */
    @Column(name="SGIN", length=12, nullable=false, updatable=false, insertable=false)
    @Getter private String gin;
        
            
    /**
     * etat
     */
    @Column(name="SETAT", length=1, nullable=false)
    @Getter private String etat;
        
            
    /**
     * typeContrat
     */
    @Column(name="STYPE_CONTRAT", length=10, nullable=false)
    @Getter private String typeContrat;
        
            
    /**
     * sousType
     */
    @Column(name="SSOUS_TYPE", length=10)
    @Getter private String sousType;
        
            
    /**
     * tier
     */
    @Column(name="STIER", length=1)
    @Getter private String tier;
        
            
    /**
     * codeCompagnie
     */
    @Column(name="SCODE_COMPAGNIE", length=3)
    @Getter private String codeCompagnie;
        
            
    /**
     * versionProduit
     */
    @Column(name="IVERSION_PRODUIT")
    @Getter private Integer versionProduit;
        
            
    /**
     * dateFinValidite
     */
    @Column(name="DFIN_VALIDITE")
    @Getter private Date dateFinValidite;
        
            
    /**
     * dateDebutValidite
     */
    @Column(name="DDEBUT_VALIDITE")
    @Getter private Date dateDebutValidite;
        
            
    /**
     * familleTraitement
     */
    @Column(name="SFAMILLE_TRAITEMENT", length=1)
    @Getter private String familleTraitement;
        
            
    /**
     * familleProduit
     */
    @Column(name="SFAMILLE_PRODUIT", length=3)
    @Getter private String familleProduit;
        
            
    /**
     * cleRole
     */
    @Column(name="ICLE_ROLE", updatable=false, insertable=false)
    @Getter private Integer cleRole;
        
            
    /**
     * dateCreation
     */
    @Column(name="DDATE_CREATION")
    @Getter private Date dateCreation;
        
            
    /**
     * signatureCreation
     */
    @Column(name="SSIGNATURE_CREATION")
    @Getter private String signatureCreation;
        
            
    /**
     * dateModification
     */
    @Column(name="DDATE_MODIFICATION")
    @Getter private Date dateModification;
        
            
    /**
     * signatureModification
     */
    @Column(name="SSIGNATURE_MODIFICATION", length=16)
    @Getter private String signatureModification;
        
            
    /**
     * siteCreation
     */
    @Column(name="SSITE_CREATION", length=10)
    @Getter private String siteCreation;
        
            
    /**
     * siteModification
     */
    @Column(name="SSITE_MODIFICATION", length=10)
    @Getter private String siteModification;
        
            
    /**
     * agenceIATA
     */
    @Column(name="SAGENCE_IATA", length=8)
    @Getter private String agenceIATA;
        
            
    /**
     * iata
     */
    @Column(name="IATA", length=8)
    @Getter private String iata;
        
            
    /**
     * sourceAdhesion
     */
    @Column(name="SSOURCE_ADHESION", length=4)
    @Getter private String sourceAdhesion;
        
            
    /**
     * permissionPrime
     */
    @Column(name="SPERMISSION_PRIME", length=1)
    @Getter private String permissionPrime;
        
            
    /**
     * soldeMiles
     */
    @Column(name="ISOLDE_MILES")
    @Getter private Integer soldeMiles;
        
            
    /**
     * milesQualif
     */
    @Column(name="IMILES_QUALIF")
    @Getter private Integer milesQualif;
        
            
    /**
     * milesQualifPrec
     */
    @Column(name="IMILES_QUALIF_PREC")
    @Getter private Integer milesQualifPrec;
        
            
    /**
     * segmentsQualif
     */
    @Column(name="ISEGMENTS_QUALIF")
    @Getter private Integer segmentsQualif;
        
            
    /**
     * segmentsQualifPrec
     */
    @Column(name="ISEGMENTS_QUALIF_PREC")
    @Getter private Integer segmentsQualifPrec;
        
            
    /**
     * cuscoCreated
     */
    @Column(name="CUSCO_CREATED")
    @Getter private String cuscoCreated;
    
    /**
     * memberType
     */
    @Column(name="SMEMBER_TYPE")
    @Getter private String memberType;

    /**
     * default constructor 
     */
    public RoleContrats() {
    }
        
    /** 
     * full constructor
     * @param pSrin srin
     * @param pVersion version
     * @param pNumeroContrat numeroContrat
     * @param pGin gin
     * @param pEtat etat
     * @param pTypeContrat typeContrat
     * @param pSousType sousType
     * @param pTier tier
     * @param pCodeCompagnie codeCompagnie
     * @param pVersionProduit versionProduit
     * @param pDateFinValidite dateFinValidite
     * @param pDateDebutValidite dateDebutValidite
     * @param pFamilleTraitement familleTraitement
     * @param pFamilleProduit familleProduit
     * @param pCleRole cleRole
     * @param pDateCreation dateCreation
     * @param pSignatureCreation signatureCreation
     * @param pDateModification dateModification
     * @param pSignatureModification signatureModification
     * @param pSiteCreation siteCreation
     * @param pSiteModification siteModification
     * @param pAgenceIATA agenceIATA
     * @param pIata iata
     * @param pSourceAdhesion sourceAdhesion
     * @param pPermissionPrime permissionPrime
     * @param pSoldeMiles soldeMiles
     * @param pMilesQualif milesQualif
     * @param pMilesQualifPrec milesQualifPrec
     * @param pSegmentsQualif segmentsQualif
     * @param pSegmentsQualifPrec segmentsQualifPrec
     * @param pCuscoCreated cuscoCreated
     */
    public RoleContrats(String pSrin, Integer pVersion, String pNumeroContrat, String pGin, String pEtat, String pTypeContrat, String pSousType, String pTier, String pCodeCompagnie, Integer pVersionProduit, Date pDateFinValidite, Date pDateDebutValidite, String pFamilleTraitement, String pFamilleProduit, Integer pCleRole, Date pDateCreation, String pSignatureCreation, Date pDateModification, String pSignatureModification, String pSiteCreation, String pSiteModification, String pAgenceIATA, String pIata, String pSourceAdhesion, String pPermissionPrime, Integer pSoldeMiles, Integer pMilesQualif, Integer pMilesQualifPrec, Integer pSegmentsQualif, Integer pSegmentsQualifPrec, String pCuscoCreated) {
        this.srin = pSrin;
        this.version = pVersion;
        this.numeroContrat = pNumeroContrat;
        this.gin = pGin;
        this.etat = pEtat;
        this.typeContrat = pTypeContrat;
        this.sousType = pSousType;
        this.tier = pTier;
        this.codeCompagnie = pCodeCompagnie;
        this.versionProduit = pVersionProduit;
        this.dateFinValidite = pDateFinValidite;
        this.dateDebutValidite = pDateDebutValidite;
        this.familleTraitement = pFamilleTraitement;
        this.familleProduit = pFamilleProduit;
        this.cleRole = pCleRole;
        this.dateCreation = pDateCreation;
        this.signatureCreation = pSignatureCreation;
        this.dateModification = pDateModification;
        this.signatureModification = pSignatureModification;
        this.siteCreation = pSiteCreation;
        this.siteModification = pSiteModification;
        this.agenceIATA = pAgenceIATA;
        this.iata = pIata;
        this.sourceAdhesion = pSourceAdhesion;
        this.permissionPrime = pPermissionPrime;
        this.soldeMiles = pSoldeMiles;
        this.milesQualif = pMilesQualif;
        this.milesQualifPrec = pMilesQualifPrec;
        this.segmentsQualif = pSegmentsQualif;
        this.segmentsQualifPrec = pSegmentsQualifPrec;
        this.cuscoCreated = pCuscoCreated;
    }
    
    /*PROTECTED REGION ID(equals hash _3ifg0PcaEd-Kx8TJdz7fGw) ENABLED START*/

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
        final RoleContrats other = (RoleContrats) obj;

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

    /*PROTECTED REGION ID(_3ifg0PcaEd-Kx8TJdz7fGw u m) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/
    
}
