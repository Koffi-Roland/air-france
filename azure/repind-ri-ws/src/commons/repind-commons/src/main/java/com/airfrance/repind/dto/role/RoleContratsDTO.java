package com.airfrance.repind.dto.role;

/*PROTECTED REGION ID(_i7fq0PceEd-Kx8TJdz7fGw i) ENABLED START*/

// add not generated imports here

import com.airfrance.repind.dto.individu.IndividuDTO;

import java.io.Serializable;
import java.util.Date;

/*PROTECTED REGION END*/

/**
 * <p>Title : RoleContratsDTO</p>
 * 
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
public class RoleContratsDTO  implements Serializable {
        
    /**
	 * 
	 */
	private static final long serialVersionUID = -2438969600503589215L;


	/**
     * srin
     */
    private String srin;
        
        
    /**
     * version
     */
    private Integer version;
        
        
    /**
     * numeroContrat
     */
    private String numeroContrat;
        
        
    /**
     * gin
     */
    private String gin;
        
        
    /**
     * etat
     */
    private String etat;
        
        
    /**
     * typeContrat
     */
    private String typeContrat;
        
        
    /**
     * sousType
     */
    private String sousType;
        
        
    /**
     * tier
     */
    private String tier;
        
        
    /**
     * codeCompagnie
     */
    private String codeCompagnie;
        
        
    /**
     * versionProduit
     */
    private Integer versionProduit;
        
        
    /**
     * dateFinValidite
     */
    private Date dateFinValidite;
        
        
    /**
     * dateDebutValidite
     */
    private Date dateDebutValidite;
        
        
    /**
     * familleTraitement
     */
    private String familleTraitement;
        
        
    /**
     * familleProduit
     */
    private String familleProduit;
        
        
    /**
     * cleRole
     */
    private Integer cleRole;
        
        
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
     * siteCreation
     */
    private String siteCreation;
        
        
    /**
     * siteModification
     */
    private String siteModification;
        
        
    /**
     * agenceIATA
     */
    private String agenceIATA;
        
        
    /**
     * iata
     */
    private String iata;
        
        
    /**
     * sourceAdhesion
     */
    private String sourceAdhesion;
        
        
    /**
     * permissionPrime
     */
    private String permissionPrime;
        
        
    /**
     * soldeMiles
     */
    private Integer soldeMiles;
        
        
    /**
     * milesQualif
     */
    private Integer milesQualif;
        
        
    /**
     * milesQualifPrec
     */
    private Integer milesQualifPrec;
        
        
    /**
     * segmentsQualif
     */
    private Integer segmentsQualif;
        
        
    /**
     * segmentsQualifPrec
     */
    private Integer segmentsQualifPrec;
        
        
    /**
     * cuscoCreated
     */
    private String cuscoCreated;
        
        
    /**
     * businessroledto
     */
    private BusinessRoleDTO businessroledto;
        
        
    /**
     * individudto
     */
    private IndividuDTO individudto;
    
    /**
     * memberType
     */
    private String memberType;

    /*PROTECTED REGION ID(_i7fq0PceEd-Kx8TJdz7fGw u var) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/

    
    
    /** 
     * default constructor 
     */
    public RoleContratsDTO() {
    
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
    public RoleContratsDTO(String pSrin, Integer pVersion, String pNumeroContrat, String pGin, String pEtat, String pTypeContrat, String pSousType, String pTier, String pCodeCompagnie, Integer pVersionProduit, Date pDateFinValidite, Date pDateDebutValidite, String pFamilleTraitement, String pFamilleProduit, Integer pCleRole, Date pDateCreation, String pSignatureCreation, Date pDateModification, String pSignatureModification, String pSiteCreation, String pSiteModification, String pAgenceIATA, String pIata, String pSourceAdhesion, String pPermissionPrime, Integer pSoldeMiles, Integer pMilesQualif, Integer pMilesQualifPrec, Integer pSegmentsQualif, Integer pSegmentsQualifPrec, String pCuscoCreated) {
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

    /**
     *
     * @return agenceIATA
     */
    public String getAgenceIATA() {
        return this.agenceIATA;
    }

    /**
     *
     * @param pAgenceIATA agenceIATA value
     */
    public void setAgenceIATA(String pAgenceIATA) {
        this.agenceIATA = pAgenceIATA;
    }

    /**
     *
     * @return businessroledto
     */
    public BusinessRoleDTO getBusinessroledto() {
        return this.businessroledto;
    }

    /**
     *
     * @param pBusinessroledto businessroledto value
     */
    public void setBusinessroledto(BusinessRoleDTO pBusinessroledto) {
        this.businessroledto = pBusinessroledto;
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
     * @return codeCompagnie
     */
    public String getCodeCompagnie() {
        return this.codeCompagnie;
    }

    /**
     *
     * @param pCodeCompagnie codeCompagnie value
     */
    public void setCodeCompagnie(String pCodeCompagnie) {
        this.codeCompagnie = pCodeCompagnie;
    }

    /**
     *
     * @return cuscoCreated
     */
    public String getCuscoCreated() {
        return this.cuscoCreated;
    }

    /**
     *
     * @param pCuscoCreated cuscoCreated value
     */
    public void setCuscoCreated(String pCuscoCreated) {
        this.cuscoCreated = pCuscoCreated;
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
     * @return dateDebutValidite
     */
    public Date getDateDebutValidite() {
        return this.dateDebutValidite;
    }

    /**
     *
     * @param pDateDebutValidite dateDebutValidite value
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
     * @param pDateFinValidite dateFinValidite value
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
     * @param pDateModification dateModification value
     */
    public void setDateModification(Date pDateModification) {
        this.dateModification = pDateModification;
    }

    /**
     *
     * @return etat
     */
    public String getEtat() {
        return this.etat;
    }

    /**
     *
     * @param pEtat etat value
     */
    public void setEtat(String pEtat) {
        this.etat = pEtat;
    }

    /**
     *
     * @return familleProduit
     */
    public String getFamilleProduit() {
        return this.familleProduit;
    }

    /**
     *
     * @param pFamilleProduit familleProduit value
     */
    public void setFamilleProduit(String pFamilleProduit) {
        this.familleProduit = pFamilleProduit;
    }

    /**
     *
     * @return familleTraitement
     */
    public String getFamilleTraitement() {
        return this.familleTraitement;
    }

    /**
     *
     * @param pFamilleTraitement familleTraitement value
     */
    public void setFamilleTraitement(String pFamilleTraitement) {
        this.familleTraitement = pFamilleTraitement;
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
     * @return iata
     */
    public String getIata() {
        return this.iata;
    }

    /**
     *
     * @param pIata iata value
     */
    public void setIata(String pIata) {
        this.iata = pIata;
    }

    /**
     *
     * @return individudto
     */
    public IndividuDTO getIndividudto() {
        return this.individudto;
    }

    /**
     *
     * @param pIndividudto individudto value
     */
    public void setIndividudto(IndividuDTO pIndividudto) {
        this.individudto = pIndividudto;
    }

    /**
     *
     * @return milesQualif
     */
    public Integer getMilesQualif() {
        return this.milesQualif;
    }

    /**
     *
     * @param pMilesQualif milesQualif value
     */
    public void setMilesQualif(Integer pMilesQualif) {
        this.milesQualif = pMilesQualif;
    }

    /**
     *
     * @return milesQualifPrec
     */
    public Integer getMilesQualifPrec() {
        return this.milesQualifPrec;
    }

    /**
     *
     * @param pMilesQualifPrec milesQualifPrec value
     */
    public void setMilesQualifPrec(Integer pMilesQualifPrec) {
        this.milesQualifPrec = pMilesQualifPrec;
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
     * @return permissionPrime
     */
    public String getPermissionPrime() {
        return this.permissionPrime;
    }

    /**
     *
     * @param pPermissionPrime permissionPrime value
     */
    public void setPermissionPrime(String pPermissionPrime) {
        this.permissionPrime = pPermissionPrime;
    }

    /**
     *
     * @return segmentsQualif
     */
    public Integer getSegmentsQualif() {
        return this.segmentsQualif;
    }

    /**
     *
     * @param pSegmentsQualif segmentsQualif value
     */
    public void setSegmentsQualif(Integer pSegmentsQualif) {
        this.segmentsQualif = pSegmentsQualif;
    }

    /**
     *
     * @return segmentsQualifPrec
     */
    public Integer getSegmentsQualifPrec() {
        return this.segmentsQualifPrec;
    }

    /**
     *
     * @param pSegmentsQualifPrec segmentsQualifPrec value
     */
    public void setSegmentsQualifPrec(Integer pSegmentsQualifPrec) {
        this.segmentsQualifPrec = pSegmentsQualifPrec;
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
     * @return soldeMiles
     */
    public Integer getSoldeMiles() {
        return this.soldeMiles;
    }

    /**
     *
     * @param pSoldeMiles soldeMiles value
     */
    public void setSoldeMiles(Integer pSoldeMiles) {
        this.soldeMiles = pSoldeMiles;
    }

    /**
     *
     * @return sourceAdhesion
     */
    public String getSourceAdhesion() {
        return this.sourceAdhesion;
    }

    /**
     *
     * @param pSourceAdhesion sourceAdhesion value
     */
    public void setSourceAdhesion(String pSourceAdhesion) {
        this.sourceAdhesion = pSourceAdhesion;
    }

    /**
     *
     * @return sousType
     */
    public String getSousType() {
        return this.sousType;
    }

    /**
     *
     * @param pSousType sousType value
     */
    public void setSousType(String pSousType) {
        this.sousType = pSousType;
    }

    /**
     *
     * @return srin
     */
    public String getSrin() {
        return this.srin;
    }

    /**
     *
     * @param pSrin srin value
     */
    public void setSrin(String pSrin) {
        this.srin = pSrin;
    }

    /**
     *
     * @return tier
     */
    public String getTier() {
        return this.tier;
    }

    /**
     *
     * @param pTier tier value
     */
    public void setTier(String pTier) {
        this.tier = pTier;
    }

    /**
     *
     * @return typeContrat
     */
    public String getTypeContrat() {
        return this.typeContrat;
    }

    /**
     *
     * @param pTypeContrat typeContrat value
     */
    public void setTypeContrat(String pTypeContrat) {
        this.typeContrat = pTypeContrat;
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
     * @return versionProduit
     */
    public Integer getVersionProduit() {
        return this.versionProduit;
    }

    /**
     *
     * @param pVersionProduit versionProduit value
     */
    public void setVersionProduit(Integer pVersionProduit) {
        this.versionProduit = pVersionProduit;
    }

    /**
     *
     * @return object as string
     */
    public String toString() {
        String result = null;
        /*PROTECTED REGION ID(toString_i7fq0PceEd-Kx8TJdz7fGw) ENABLED START*/
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
        buffer.append("srin=").append(getSrin());
        buffer.append(",");
        buffer.append("version=").append(getVersion());
        buffer.append(",");
        buffer.append("numeroContrat=").append(getNumeroContrat());
        buffer.append(",");
        buffer.append("gin=").append(getGin());
        buffer.append(",");
        buffer.append("etat=").append(getEtat());
        buffer.append(",");
        buffer.append("typeContrat=").append(getTypeContrat());
        buffer.append(",");
        buffer.append("sousType=").append(getSousType());
        buffer.append(",");
        buffer.append("tier=").append(getTier());
        buffer.append(",");
        buffer.append("codeCompagnie=").append(getCodeCompagnie());
        buffer.append(",");
        buffer.append("versionProduit=").append(getVersionProduit());
        buffer.append(",");
        buffer.append("dateFinValidite=").append(getDateFinValidite());
        buffer.append(",");
        buffer.append("dateDebutValidite=").append(getDateDebutValidite());
        buffer.append(",");
        buffer.append("familleTraitement=").append(getFamilleTraitement());
        buffer.append(",");
        buffer.append("familleProduit=").append(getFamilleProduit());
        buffer.append(",");
        buffer.append("cleRole=").append(getCleRole());
        buffer.append(",");
        buffer.append("dateCreation=").append(getDateCreation());
        buffer.append(",");
        buffer.append("signatureCreation=").append(getSignatureCreation());
        buffer.append(",");
        buffer.append("dateModification=").append(getDateModification());
        buffer.append(",");
        buffer.append("signatureModification=").append(getSignatureModification());
        buffer.append(",");
        buffer.append("siteCreation=").append(getSiteCreation());
        buffer.append(",");
        buffer.append("siteModification=").append(getSiteModification());
        buffer.append(",");
        buffer.append("agenceIATA=").append(getAgenceIATA());
        buffer.append(",");
        buffer.append("iata=").append(getIata());
        buffer.append(",");
        buffer.append("sourceAdhesion=").append(getSourceAdhesion());
        buffer.append(",");
        buffer.append("permissionPrime=").append(getPermissionPrime());
        buffer.append(",");
        buffer.append("soldeMiles=").append(getSoldeMiles());
        buffer.append(",");
        buffer.append("milesQualif=").append(getMilesQualif());
        buffer.append(",");
        buffer.append("milesQualifPrec=").append(getMilesQualifPrec());
        buffer.append(",");
        buffer.append("segmentsQualif=").append(getSegmentsQualif());
        buffer.append(",");
        buffer.append("segmentsQualifPrec=").append(getSegmentsQualifPrec());
        buffer.append(",");
        buffer.append("cuscoCreated=").append(getCuscoCreated());
        buffer.append("]");
        return buffer.toString();
    }

	public String getMemberType() {
		return memberType;
	}

	public void setMemberType(String memberType) {
		this.memberType = memberType;
	}

    /*PROTECTED REGION ID(_i7fq0PceEd-Kx8TJdz7fGw u m) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/

}
