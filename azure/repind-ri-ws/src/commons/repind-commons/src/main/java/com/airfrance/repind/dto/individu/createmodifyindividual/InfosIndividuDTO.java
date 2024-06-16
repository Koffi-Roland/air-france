package com.airfrance.repind.dto.individu.createmodifyindividual;

/*PROTECTED REGION ID(_Z2LRADRnEeCc7ZsKsK1lbQ i) ENABLED START*/

// add not generated imports here

import com.airfrance.repind.util.NormalizedStringUtils;
import org.apache.commons.lang.StringUtils;

import java.io.Serializable;

/*PROTECTED REGION END*/

/**
 * <p>Title : InfosIndividuDTO</p>
 * 
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
public class InfosIndividuDTO  implements Serializable {
        
    /**
	 * 
	 */
	private static final long serialVersionUID = 8257836312158509664L;


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
    private String dateNaissance;
        
        
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
    private String indicNonFusion;
        
        
    /**
     * statut
     */
    private String statut;
        
        
    /**
     * indicFraudBanq
     */
    private String indicFraudBanq;
        
        
    /**
     * indicTiersPiege
     */
    private String indicTiersPiege;
        
        
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
    private String dateFusion;
        
        
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
        

    /*PROTECTED REGION ID(_Z2LRADRnEeCc7ZsKsK1lbQ u var) ENABLED START*/
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
     * @param pStatut statut
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
    public InfosIndividuDTO(String pNumeroClient, String pVersion, String pNom, String pSexe, String pIdentPerso, String pDateNaissance, String pNationalite, String pAutreNationalite, String pSecondPrenom, String pPrenom, String pIndicNonFusion, String pStatut, String pIndicFraudBanq, String pIndicTiersPiege, String pCivilite, String pAliasNom, String pAliasPrenom, String pGinFusion, String pDateFusion, String pMotDePasse, String pCieGestionnaire, String pNomSC, String pPrenomSC) {
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
        this.statut = pStatut;
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
     * @return aliasPrenom
     */
    public String getAliasPrenom() {
        return this.aliasPrenom;
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
    public String getDateFusion() {
        return this.dateFusion;
    }

    /**
     *
     * @param pDateFusion dateFusion value
     */
    public void setDateFusion(String pDateFusion) {
        this.dateFusion = pDateFusion;
    }

    /**
     *
     * @return dateNaissance
     */
    public String getDateNaissance() {
        return this.dateNaissance;
    }

    /**
     *
     * @param pDateNaissance dateNaissance value
     */
    public void setDateNaissance(String pDateNaissance) {
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
    public String getIndicFraudBanq() {
        return this.indicFraudBanq;
    }

    /**
     *
     * @param pIndicFraudBanq indicFraudBanq value
     */
    public void setIndicFraudBanq(String pIndicFraudBanq) {
        this.indicFraudBanq = pIndicFraudBanq;
    }

    /**
     *
     * @return indicNonFusion
     */
    public String getIndicNonFusion() {
        return this.indicNonFusion;
    }

    /**
     *
     * @param pIndicNonFusion indicNonFusion value
     */
    public void setIndicNonFusion(String pIndicNonFusion) {
        this.indicNonFusion = pIndicNonFusion;
    }

    /**
     *
     * @return indicTiersPiege
     */
    public String getIndicTiersPiege() {
        return this.indicTiersPiege;
    }

    /**
     *
     * @param pIndicTiersPiege indicTiersPiege value
     */
    public void setIndicTiersPiege(String pIndicTiersPiege) {
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
        /*PROTECTED REGION ID(toString_Z2LRADRnEeCc7ZsKsK1lbQ) ENABLED START*/
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
        buffer.append("numeroClient=").append(getNumeroClient());
        buffer.append(",");
        buffer.append("version=").append(getVersion());
        buffer.append(",");
        buffer.append("nom=").append(getNom());
        buffer.append(",");
        buffer.append("sexe=").append(getSexe());
        buffer.append(",");
        buffer.append("identPerso=").append(getIdentPerso());
        buffer.append(",");
        buffer.append("dateNaissance=").append(getDateNaissance());
        buffer.append(",");
        buffer.append("nationalite=").append(getNationalite());
        buffer.append(",");
        buffer.append("autreNationalite=").append(getAutreNationalite());
        buffer.append(",");
        buffer.append("secondPrenom=").append(getSecondPrenom());
        buffer.append(",");
        buffer.append("prenom=").append(getPrenom());
        buffer.append(",");
        buffer.append("indicNonFusion=").append(getIndicNonFusion());
        buffer.append(",");
        buffer.append("statut=").append(getStatut());
        buffer.append(",");
        buffer.append("indicFraudBanq=").append(getIndicFraudBanq());
        buffer.append(",");
        buffer.append("indicTiersPiege=").append(getIndicTiersPiege());
        buffer.append(",");
        buffer.append("civilite=").append(getCivilite());
        buffer.append(",");
        buffer.append("aliasNom=").append(getAliasNom());
        buffer.append(",");
        buffer.append("aliasPrenom=").append(getAliasPrenom());
        buffer.append(",");
        buffer.append("ginFusion=").append(getGinFusion());
        buffer.append(",");
        buffer.append("dateFusion=").append(getDateFusion());
        buffer.append(",");
        buffer.append("motDePasse=").append(getMotDePasse());
        buffer.append(",");
        buffer.append("cieGestionnaire=").append(getCieGestionnaire());
        buffer.append(",");
        buffer.append("nomSC=").append(getNomSC());
        buffer.append(",");
        buffer.append("prenomSC=").append(getPrenomSC());
        buffer.append("]");
        return buffer.toString();
    }

    /** 
     * setAliasNom
     * @param pAliasNom in String
     */
    public void setAliasNom(String pAliasNom) {
        /*PROTECTED REGION ID(_rzjpUI_XEeODX9H0_DQa9A) ENABLED START*/
    	aliasNom = normalize(pAliasNom);
        /*PROTECTED REGION END*/
    }

    /** 
     * setAliasPrenom
     * @param pAliasPrenom in String
     */
    public void setAliasPrenom(String pAliasPrenom) {
        /*PROTECTED REGION ID(_thZEII_XEeODX9H0_DQa9A) ENABLED START*/
    	aliasPrenom = normalize(pAliasPrenom);
        /*PROTECTED REGION END*/
    }

    /** 
     * setNom
     * @param pNom in String
     */
    public void setNom(String pNom) {
        /*PROTECTED REGION ID(_u2YhII_XEeODX9H0_DQa9A) ENABLED START*/
    	nom = normalize(pNom);
        /*PROTECTED REGION END*/
    }

    /** 
     * setPrenom
     * @param pPrenom in String
     */
    public void setPrenom(String pPrenom) {
        /*PROTECTED REGION ID(_xkZmkI_XEeODX9H0_DQa9A) ENABLED START*/
    	prenom = normalize(pPrenom);
        /*PROTECTED REGION END*/
    }

    /** 
     * setSecondPrenom
     * @param pSecondPrenom in String
     */
    public void setSecondPrenom(String pSecondPrenom) {
        /*PROTECTED REGION ID(_zNcoAI_XEeODX9H0_DQa9A) ENABLED START*/
    	secondPrenom = normalize(pSecondPrenom);
        /*PROTECTED REGION END*/
    }

    /*PROTECTED REGION ID(_Z2LRADRnEeCc7ZsKsK1lbQ u m) ENABLED START*/

    /**
     * This method is aimed to normalize a given string by:
     * <ul>
     * 	<li>replacing special characters</li>
     * 	<li>transforming to upper case</li>
     * </ul>
     */
    protected String normalize(String pStr) {
    	
    	// If string is empty normalization is useless
    	if(StringUtils.isEmpty(pStr)) {
    		return pStr;
    	}
    	
    	// Replace special characters
    	String cleanedStr = NormalizedStringUtils.normalizeString(pStr);
    	// Transform to upper case
    	cleanedStr = cleanedStr.toUpperCase();
    	
    	return cleanedStr;
    }
    
    /*PROTECTED REGION END*/

}
