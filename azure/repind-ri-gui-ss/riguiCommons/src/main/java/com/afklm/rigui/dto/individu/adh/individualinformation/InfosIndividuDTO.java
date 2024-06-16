package com.afklm.rigui.dto.individu.adh.individualinformation;

/*PROTECTED REGION ID(_b1hJ4yMUEeCWJOBY8f-ONQ i) ENABLED START*/

// add not generated imports here

import org.apache.commons.lang.builder.ToStringBuilder;

import java.util.Date;

/*PROTECTED REGION END*/

/**
 * <p>Title : InfosIndividuDTO</p>
 * 
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
public class InfosIndividuDTO  {
        
    /**
     * numeroClient
     */
    private String numeroClient;
        
        
    /**
     * version
     */
    private String version;
        
        
    /**
     * nom
     */
    private String nom;
        
        
    /**
     * sexe
     */
    private String sexe;
        
        
    /**
     * identPerso
     */
    private String identPerso;
        
        
    /**
     * dateNaissance
     */
    private Date dateNaissance;
        
        
    /**
     * nationalite
     */
    private String nationalite;
        
        
    /**
     * autreNationalite
     */
    private String autreNationalite;
        
        
    /**
     * secondPrenom
     */
    private String secondPrenom;
        
        
    /**
     * prenom
     */
    private String prenom;
        
        
    /**
     * indicNonFusion
     */
    private Boolean indicNonFusion;
        
        
    /**
     * status
     */
    private String status;
        
        
    /**
     * indicFraudBanq
     */
    private Boolean indicFraudBanq;
        
        
    /**
     * indicTiersPiege
     */
    private Boolean indicTiersPiege;
        
        
    /**
     * civilite
     */
    private String civilite;
        
        
    /**
     * aliasNom
     */
    private String aliasNom;
        
        
    /**
     * aliasPrenom
     */
    private String aliasPrenom;
        
        
    /**
     * ginFusion
     */
    private String ginFusion;
        
        
    /**
     * dateFusion
     */
    private Date dateFusion;
        
        
    /**
     * motDePasse
     */
    private String motDePasse;
        
        
    /**
     * cieGestionnaire
     */
    private String cieGestionnaire;
        
        
    /**
     * nomSC
     */
    private String nomSC;
        
        
    /**
     * prenomSC
     */
    private String prenomSC;
        

    /*PROTECTED REGION ID(_b1hJ4yMUEeCWJOBY8f-ONQ u var) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/

    
    
    /** 
     * default constructor 
     */
    public InfosIndividuDTO() {
    
    }
    
    
    /** 
     * full constructor
     * @param pNumeroClient numeroClient
     * @param pVersion version
     * @param pNom nom
     * @param pSexe sexe
     * @param pIdentPerso identPerso
     * @param pDateNaissance dateNaissance
     * @param pNationalite nationalite
     * @param pAutreNationalite autreNationalite
     * @param pSecondPrenom secondPrenom
     * @param pPrenom prenom
     * @param pIndicNonFusion indicNonFusion
     * @param pStatus status
     * @param pIndicFraudBanq indicFraudBanq
     * @param pIndicTiersPiege indicTiersPiege
     * @param pCivilite civilite
     * @param pAliasNom aliasNom
     * @param pAliasPrenom aliasPrenom
     * @param pGinFusion ginFusion
     * @param pDateFusion dateFusion
     * @param pMotDePasse motDePasse
     * @param pCieGestionnaire cieGestionnaire
     * @param pNomSC nomSC
     * @param pPrenomSC prenomSC
     */
    public InfosIndividuDTO(String pNumeroClient, String pVersion, String pNom, String pSexe, String pIdentPerso, Date pDateNaissance, String pNationalite, String pAutreNationalite, String pSecondPrenom, String pPrenom, Boolean pIndicNonFusion, String pStatus, Boolean pIndicFraudBanq, Boolean pIndicTiersPiege, String pCivilite, String pAliasNom, String pAliasPrenom, String pGinFusion, Date pDateFusion, String pMotDePasse, String pCieGestionnaire, String pNomSC, String pPrenomSC) {
        this.numeroClient = pNumeroClient;
        this.version = pVersion;
        this.nom = pNom;
        this.sexe = pSexe;
        this.identPerso = pIdentPerso;
        this.dateNaissance = pDateNaissance;
        this.nationalite = pNationalite;
        this.autreNationalite = pAutreNationalite;
        this.secondPrenom = pSecondPrenom;
        this.prenom = pPrenom;
        this.indicNonFusion = pIndicNonFusion;
        this.status = pStatus;
        this.indicFraudBanq = pIndicFraudBanq;
        this.indicTiersPiege = pIndicTiersPiege;
        this.civilite = pCivilite;
        this.aliasNom = pAliasNom;
        this.aliasPrenom = pAliasPrenom;
        this.ginFusion = pGinFusion;
        this.dateFusion = pDateFusion;
        this.motDePasse = pMotDePasse;
        this.cieGestionnaire = pCieGestionnaire;
        this.nomSC = pNomSC;
        this.prenomSC = pPrenomSC;
    }

    /**
     *
     * @return aliasNom
     */
    public String getAliasNom() {
        return this.aliasNom;
    }

    /**
     *
     * @param pAliasNom aliasNom value
     */
    public void setAliasNom(String pAliasNom) {
        this.aliasNom = pAliasNom;
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
     * @return cieGestionnaire
     */
    public String getCieGestionnaire() {
        return this.cieGestionnaire;
    }

    /**
     *
     * @param pCieGestionnaire cieGestionnaire value
     */
    public void setCieGestionnaire(String pCieGestionnaire) {
        this.cieGestionnaire = pCieGestionnaire;
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
     * @return identPerso
     */
    public String getIdentPerso() {
        return this.identPerso;
    }

    /**
     *
     * @param pIdentPerso identPerso value
     */
    public void setIdentPerso(String pIdentPerso) {
        this.identPerso = pIdentPerso;
    }

    /**
     *
     * @return indicFraudBanq
     */
    public Boolean getIndicFraudBanq() {
        return this.indicFraudBanq;
    }

    /**
     *
     * @param pIndicFraudBanq indicFraudBanq value
     */
    public void setIndicFraudBanq(Boolean pIndicFraudBanq) {
        this.indicFraudBanq = pIndicFraudBanq;
    }

    /**
     *
     * @return indicNonFusion
     */
    public Boolean getIndicNonFusion() {
        return this.indicNonFusion;
    }

    /**
     *
     * @param pIndicNonFusion indicNonFusion value
     */
    public void setIndicNonFusion(Boolean pIndicNonFusion) {
        this.indicNonFusion = pIndicNonFusion;
    }

    /**
     *
     * @return indicTiersPiege
     */
    public Boolean getIndicTiersPiege() {
        return this.indicTiersPiege;
    }

    /**
     *
     * @param pIndicTiersPiege indicTiersPiege value
     */
    public void setIndicTiersPiege(Boolean pIndicTiersPiege) {
        this.indicTiersPiege = pIndicTiersPiege;
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
     * @return numeroClient
     */
    public String getNumeroClient() {
        return this.numeroClient;
    }

    /**
     *
     * @param pNumeroClient numeroClient value
     */
    public void setNumeroClient(String pNumeroClient) {
        this.numeroClient = pNumeroClient;
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
     * @return status
     */
    public String getStatus() {
        return this.status;
    }

    /**
     *
     * @param pStatus status value
     */
    public void setStatus(String pStatus) {
        this.status = pStatus;
    }

    /**
     *
     * @return version
     */
    public String getVersion() {
        return this.version;
    }

    /**
     *
     * @param pVersion version value
     */
    public void setVersion(String pVersion) {
        this.version = pVersion;
    }

    /**
     *
     * @return object as string
     */
    public String toString() {
        String result = null;
        /*PROTECTED REGION ID(toString_b1hJ4yMUEeCWJOBY8f-ONQ) ENABLED START*/
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
            .append("numeroClient", getNumeroClient())
            .append("version", getVersion())
            .append("nom", getNom())
            .append("sexe", getSexe())
            .append("identPerso", getIdentPerso())
            .append("dateNaissance", getDateNaissance())
            .append("nationalite", getNationalite())
            .append("autreNationalite", getAutreNationalite())
            .append("secondPrenom", getSecondPrenom())
            .append("prenom", getPrenom())
            .append("indicNonFusion", getIndicNonFusion())
            .append("status", getStatus())
            .append("indicFraudBanq", getIndicFraudBanq())
            .append("indicTiersPiege", getIndicTiersPiege())
            .append("civilite", getCivilite())
            .append("aliasNom", getAliasNom())
            .append("aliasPrenom", getAliasPrenom())
            .append("ginFusion", getGinFusion())
            .append("dateFusion", getDateFusion())
            .append("motDePasse", getMotDePasse())
            .append("cieGestionnaire", getCieGestionnaire())
            .append("nomSC", getNomSC())
            .append("prenomSC", getPrenomSC())
            .toString();
    }

    /*PROTECTED REGION ID(_b1hJ4yMUEeCWJOBY8f-ONQ u m) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/

}
