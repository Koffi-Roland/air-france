package com.airfrance.repind.dto.individu.adh.individualinformation;

/*PROTECTED REGION ID(_b10E1iMUEeCWJOBY8f-ONQ i) ENABLED START*/

// add not generated imports here

import org.apache.commons.lang.builder.ToStringBuilder;

import java.util.Date;

/*PROTECTED REGION END*/

/**
 * <p>Title : RoleContratV2SICDTO</p>
 * 
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
public class RoleContratV2SICDTO  {
        
    /**
     * numeroContrat
     */
    private String numeroContrat;
        
        
    /**
     * typeContrat
     */
    private String typeContrat;
        
        
    /**
     * typeProduit
     */
    private String typeProduit;
        
        
    /**
     * sousTypeProduit
     */
    private String sousTypeProduit;
        
        
    /**
     * codeCieContrat
     */
    private String codeCieContrat;
        
        
    /**
     * versionProduit
     */
    private String versionProduit;
        
        
    /**
     * etatContrat
     */
    private String etatContrat;
        
        
    /**
     * dateDebValid
     */
    private Date dateDebValid;
        
        
    /**
     * dateFinValid
     */
    private Date dateFinValid;
        
        
    /**
     * niveauTiers
     */
    private String niveauTiers;
        
        
    /**
     * familleProduit
     */
    private String familleProduit;
        
        
    /**
     * iata
     */
    private String iata;
        
        
    /**
     * organismeOrigine
     */
    private String organismeOrigine;
        
        
    /**
     * sourceAdhesion
     */
    private String sourceAdhesion;
        
        
    /**
     * permissionPriseDePrime
     */
    private String permissionPriseDePrime;
        
        
    /**
     * soldeMiles
     */
    private String soldeMiles;
        
        
    /**
     * milesQualifiant
     */
    private String milesQualifiant;
        
        
    /**
     * milesQualifiantHist
     */
    private String milesQualifiantHist;
        
        
    /**
     * segmentsQualifiant
     */
    private String segmentsQualifiant;
        
        
    /**
     * segmentQualifiantHist
     */
    private String segmentQualifiantHist;
        

    /*PROTECTED REGION ID(_b10E1iMUEeCWJOBY8f-ONQ u var) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/

    
    
    /** 
     * default constructor 
     */
    public RoleContratV2SICDTO() {
    
    }
    
    
    /** 
     * full constructor
     * @param pNumeroContrat numeroContrat
     * @param pTypeContrat typeContrat
     * @param pTypeProduit typeProduit
     * @param pSousTypeProduit sousTypeProduit
     * @param pCodeCieContrat codeCieContrat
     * @param pVersionProduit versionProduit
     * @param pEtatContrat etatContrat
     * @param pDateDebValid dateDebValid
     * @param pDateFinValid dateFinValid
     * @param pNiveauTiers niveauTiers
     * @param pFamilleProduit familleProduit
     * @param pIata iata
     * @param pOrganismeOrigine organismeOrigine
     * @param pSourceAdhesion sourceAdhesion
     * @param pPermissionPriseDePrime permissionPriseDePrime
     * @param pSoldeMiles soldeMiles
     * @param pMilesQualifiant milesQualifiant
     * @param pMilesQualifiantHist milesQualifiantHist
     * @param pSegmentsQualifiant segmentsQualifiant
     * @param pSegmentQualifiantHist segmentQualifiantHist
     */
    public RoleContratV2SICDTO(String pNumeroContrat, String pTypeContrat, String pTypeProduit, String pSousTypeProduit, String pCodeCieContrat, String pVersionProduit, String pEtatContrat, Date pDateDebValid, Date pDateFinValid, String pNiveauTiers, String pFamilleProduit, String pIata, String pOrganismeOrigine, String pSourceAdhesion, String pPermissionPriseDePrime, String pSoldeMiles, String pMilesQualifiant, String pMilesQualifiantHist, String pSegmentsQualifiant, String pSegmentQualifiantHist) {
        this.numeroContrat = pNumeroContrat;
        this.typeContrat = pTypeContrat;
        this.typeProduit = pTypeProduit;
        this.sousTypeProduit = pSousTypeProduit;
        this.codeCieContrat = pCodeCieContrat;
        this.versionProduit = pVersionProduit;
        this.etatContrat = pEtatContrat;
        this.dateDebValid = pDateDebValid;
        this.dateFinValid = pDateFinValid;
        this.niveauTiers = pNiveauTiers;
        this.familleProduit = pFamilleProduit;
        this.iata = pIata;
        this.organismeOrigine = pOrganismeOrigine;
        this.sourceAdhesion = pSourceAdhesion;
        this.permissionPriseDePrime = pPermissionPriseDePrime;
        this.soldeMiles = pSoldeMiles;
        this.milesQualifiant = pMilesQualifiant;
        this.milesQualifiantHist = pMilesQualifiantHist;
        this.segmentsQualifiant = pSegmentsQualifiant;
        this.segmentQualifiantHist = pSegmentQualifiantHist;
    }

    /**
     *
     * @return codeCieContrat
     */
    public String getCodeCieContrat() {
        return this.codeCieContrat;
    }

    /**
     *
     * @param pCodeCieContrat codeCieContrat value
     */
    public void setCodeCieContrat(String pCodeCieContrat) {
        this.codeCieContrat = pCodeCieContrat;
    }

    /**
     *
     * @return dateDebValid
     */
    public Date getDateDebValid() {
        return this.dateDebValid;
    }

    /**
     *
     * @param pDateDebValid dateDebValid value
     */
    public void setDateDebValid(Date pDateDebValid) {
        this.dateDebValid = pDateDebValid;
    }

    /**
     *
     * @return dateFinValid
     */
    public Date getDateFinValid() {
        return this.dateFinValid;
    }

    /**
     *
     * @param pDateFinValid dateFinValid value
     */
    public void setDateFinValid(Date pDateFinValid) {
        this.dateFinValid = pDateFinValid;
    }

    /**
     *
     * @return etatContrat
     */
    public String getEtatContrat() {
        return this.etatContrat;
    }

    /**
     *
     * @param pEtatContrat etatContrat value
     */
    public void setEtatContrat(String pEtatContrat) {
        this.etatContrat = pEtatContrat;
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
     * @return milesQualifiant
     */
    public String getMilesQualifiant() {
        return this.milesQualifiant;
    }

    /**
     *
     * @param pMilesQualifiant milesQualifiant value
     */
    public void setMilesQualifiant(String pMilesQualifiant) {
        this.milesQualifiant = pMilesQualifiant;
    }

    /**
     *
     * @return milesQualifiantHist
     */
    public String getMilesQualifiantHist() {
        return this.milesQualifiantHist;
    }

    /**
     *
     * @param pMilesQualifiantHist milesQualifiantHist value
     */
    public void setMilesQualifiantHist(String pMilesQualifiantHist) {
        this.milesQualifiantHist = pMilesQualifiantHist;
    }

    /**
     *
     * @return niveauTiers
     */
    public String getNiveauTiers() {
        return this.niveauTiers;
    }

    /**
     *
     * @param pNiveauTiers niveauTiers value
     */
    public void setNiveauTiers(String pNiveauTiers) {
        this.niveauTiers = pNiveauTiers;
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
     * @return organismeOrigine
     */
    public String getOrganismeOrigine() {
        return this.organismeOrigine;
    }

    /**
     *
     * @param pOrganismeOrigine organismeOrigine value
     */
    public void setOrganismeOrigine(String pOrganismeOrigine) {
        this.organismeOrigine = pOrganismeOrigine;
    }

    /**
     *
     * @return permissionPriseDePrime
     */
    public String getPermissionPriseDePrime() {
        return this.permissionPriseDePrime;
    }

    /**
     *
     * @param pPermissionPriseDePrime permissionPriseDePrime value
     */
    public void setPermissionPriseDePrime(String pPermissionPriseDePrime) {
        this.permissionPriseDePrime = pPermissionPriseDePrime;
    }

    /**
     *
     * @return segmentQualifiantHist
     */
    public String getSegmentQualifiantHist() {
        return this.segmentQualifiantHist;
    }

    /**
     *
     * @param pSegmentQualifiantHist segmentQualifiantHist value
     */
    public void setSegmentQualifiantHist(String pSegmentQualifiantHist) {
        this.segmentQualifiantHist = pSegmentQualifiantHist;
    }

    /**
     *
     * @return segmentsQualifiant
     */
    public String getSegmentsQualifiant() {
        return this.segmentsQualifiant;
    }

    /**
     *
     * @param pSegmentsQualifiant segmentsQualifiant value
     */
    public void setSegmentsQualifiant(String pSegmentsQualifiant) {
        this.segmentsQualifiant = pSegmentsQualifiant;
    }

    /**
     *
     * @return soldeMiles
     */
    public String getSoldeMiles() {
        return this.soldeMiles;
    }

    /**
     *
     * @param pSoldeMiles soldeMiles value
     */
    public void setSoldeMiles(String pSoldeMiles) {
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
     * @return sousTypeProduit
     */
    public String getSousTypeProduit() {
        return this.sousTypeProduit;
    }

    /**
     *
     * @param pSousTypeProduit sousTypeProduit value
     */
    public void setSousTypeProduit(String pSousTypeProduit) {
        this.sousTypeProduit = pSousTypeProduit;
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
     * @return typeProduit
     */
    public String getTypeProduit() {
        return this.typeProduit;
    }

    /**
     *
     * @param pTypeProduit typeProduit value
     */
    public void setTypeProduit(String pTypeProduit) {
        this.typeProduit = pTypeProduit;
    }

    /**
     *
     * @return versionProduit
     */
    public String getVersionProduit() {
        return this.versionProduit;
    }

    /**
     *
     * @param pVersionProduit versionProduit value
     */
    public void setVersionProduit(String pVersionProduit) {
        this.versionProduit = pVersionProduit;
    }

    /**
     *
     * @return object as string
     */
    public String toString() {
        String result = null;
        /*PROTECTED REGION ID(toString_b10E1iMUEeCWJOBY8f-ONQ) ENABLED START*/
        result = toStringImpl();
        /*PROTECTED REGION END*/
        return result;
    }

    /**
     *
     * @return object as string
     */
    public String toStringImpl() {
        return new ToStringBuilder(this)
            .append("numeroContrat", getNumeroContrat())
            .append("typeContrat", getTypeContrat())
            .append("typeProduit", getTypeProduit())
            .append("sousTypeProduit", getSousTypeProduit())
            .append("codeCieContrat", getCodeCieContrat())
            .append("versionProduit", getVersionProduit())
            .append("etatContrat", getEtatContrat())
            .append("dateDebValid", getDateDebValid())
            .append("dateFinValid", getDateFinValid())
            .append("niveauTiers", getNiveauTiers())
            .append("familleProduit", getFamilleProduit())
            .append("iata", getIata())
            .append("organismeOrigine", getOrganismeOrigine())
            .append("sourceAdhesion", getSourceAdhesion())
            .append("permissionPriseDePrime", getPermissionPriseDePrime())
            .append("soldeMiles", getSoldeMiles())
            .append("milesQualifiant", getMilesQualifiant())
            .append("milesQualifiantHist", getMilesQualifiantHist())
            .append("segmentsQualifiant", getSegmentsQualifiant())
            .append("segmentQualifiantHist", getSegmentQualifiantHist())
            .toString();
    }

    /*PROTECTED REGION ID(_b10E1iMUEeCWJOBY8f-ONQ u m) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/

}
