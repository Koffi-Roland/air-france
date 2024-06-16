package com.airfrance.repind.dto.individu;

/*PROTECTED REGION ID(_RZiF4FxpEeC2fu5mfEzrgA i) ENABLED START*/

// add not generated imports here

import com.airfrance.repind.dto.individu.createmodifyindividual.RoleAdresseDTO;

import java.io.Serializable;
import java.util.Set;

/*PROTECTED REGION END*/

/**
 * <p>Title : AdressePostaleDTO</p>
 * 
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
public class AdressePostaleDTO  implements Serializable {
        
    /**
	 * 
	 */
	private static final long serialVersionUID = -3812372556899580863L;


	/**
     * cleAdresse
     */
    private String cleAdresse;
        
        
    /**
     * version
     */
    private Integer version;
        
        
    /**
     * numeroUsage
     */
    private String numeroUsage;
        
        
    /**
     * codeMedium
     */
    private String codeMedium;
        
        
    /**
     * indicAdrNorm
     */
    private String indicAdrNorm;
        
        
    /**
     * statutMedium
     */
    private String statutMedium;
        
        
    /**
     * raisonSociale
     */
    private String raisonSociale;
        
        
    /**
     * ville
     */
    private String ville;
        
        
    /**
     * complAdr
     */
    private String complAdr;
        
        
    /**
     * numeroRue
     */
    private String numeroRue;
        
        
    /**
     * lieuDit
     */
    private String lieuDit;
        
        
    /**
     * codePostal
     */
    private String codePostal;
        
        
    /**
     * codePays
     */
    private String codePays;
        
        
    /**
     * codeProvince
     */
    private String codeProvince;
        
        
    /**
     * roleadresse
     */
    private Set<RoleAdresseDTO> roleadresse;
        

    /*PROTECTED REGION ID(_RZiF4FxpEeC2fu5mfEzrgA u var) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/

    
    
    /** 
     * default constructor 
     */
    public AdressePostaleDTO() {
    
    }
    
    
    /** 
     * full constructor
     * @param pCleAdresse cleAdresse
     * @param pVersion version
     * @param pNumeroUsage numeroUsage
     * @param pCodeMedium codeMedium
     * @param pIndicAdrNorm indicAdrNorm
     * @param pStatutMedium statutMedium
     * @param pRaisonSociale raisonSociale
     * @param pVille ville
     * @param pComplAdr complAdr
     * @param pNumeroRue numeroRue
     * @param pLieuDit lieuDit
     * @param pCodePostal codePostal
     * @param pCodePays codePays
     * @param pCodeProvince codeProvince
     */
    public AdressePostaleDTO(String pCleAdresse, Integer pVersion, String pNumeroUsage, String pCodeMedium, String pIndicAdrNorm, String pStatutMedium, String pRaisonSociale, String pVille, String pComplAdr, String pNumeroRue, String pLieuDit, String pCodePostal, String pCodePays, String pCodeProvince) {
        this.cleAdresse = pCleAdresse;
        this.version = pVersion;
        this.numeroUsage = pNumeroUsage;
        this.codeMedium = pCodeMedium;
        this.indicAdrNorm = pIndicAdrNorm;
        this.statutMedium = pStatutMedium;
        this.raisonSociale = pRaisonSociale;
        this.ville = pVille;
        this.complAdr = pComplAdr;
        this.numeroRue = pNumeroRue;
        this.lieuDit = pLieuDit;
        this.codePostal = pCodePostal;
        this.codePays = pCodePays;
        this.codeProvince = pCodeProvince;
    }

    /**
     *
     * @return cleAdresse
     */
    public String getCleAdresse() {
        return this.cleAdresse;
    }

    /**
     *
     * @param pCleAdresse cleAdresse value
     */
    public void setCleAdresse(String pCleAdresse) {
        this.cleAdresse = pCleAdresse;
    }

    /**
     *
     * @return codeMedium
     */
    public String getCodeMedium() {
        return this.codeMedium;
    }

    /**
     *
     * @param pCodeMedium codeMedium value
     */
    public void setCodeMedium(String pCodeMedium) {
        this.codeMedium = pCodeMedium;
    }

    /**
     *
     * @return codePays
     */
    public String getCodePays() {
        return this.codePays;
    }

    /**
     *
     * @param pCodePays codePays value
     */
    public void setCodePays(String pCodePays) {
        this.codePays = pCodePays;
    }

    /**
     *
     * @return codePostal
     */
    public String getCodePostal() {
        return this.codePostal;
    }

    /**
     *
     * @param pCodePostal codePostal value
     */
    public void setCodePostal(String pCodePostal) {
        this.codePostal = pCodePostal;
    }

    /**
     *
     * @return codeProvince
     */
    public String getCodeProvince() {
        return this.codeProvince;
    }

    /**
     *
     * @param pCodeProvince codeProvince value
     */
    public void setCodeProvince(String pCodeProvince) {
        this.codeProvince = pCodeProvince;
    }

    /**
     *
     * @return complAdr
     */
    public String getComplAdr() {
        return this.complAdr;
    }

    /**
     *
     * @param pComplAdr complAdr value
     */
    public void setComplAdr(String pComplAdr) {
        this.complAdr = pComplAdr;
    }

    /**
     *
     * @return indicAdrNorm
     */
    public String getIndicAdrNorm() {
        return this.indicAdrNorm;
    }

    /**
     *
     * @param pIndicAdrNorm indicAdrNorm value
     */
    public void setIndicAdrNorm(String pIndicAdrNorm) {
        this.indicAdrNorm = pIndicAdrNorm;
    }

    /**
     *
     * @return lieuDit
     */
    public String getLieuDit() {
        return this.lieuDit;
    }

    /**
     *
     * @param pLieuDit lieuDit value
     */
    public void setLieuDit(String pLieuDit) {
        this.lieuDit = pLieuDit;
    }

    /**
     *
     * @return numeroRue
     */
    public String getNumeroRue() {
        return this.numeroRue;
    }

    /**
     *
     * @param pNumeroRue numeroRue value
     */
    public void setNumeroRue(String pNumeroRue) {
        this.numeroRue = pNumeroRue;
    }

    /**
     *
     * @return numeroUsage
     */
    public String getNumeroUsage() {
        return this.numeroUsage;
    }

    /**
     *
     * @param pNumeroUsage numeroUsage value
     */
    public void setNumeroUsage(String pNumeroUsage) {
        this.numeroUsage = pNumeroUsage;
    }

    /**
     *
     * @return raisonSociale
     */
    public String getRaisonSociale() {
        return this.raisonSociale;
    }

    /**
     *
     * @param pRaisonSociale raisonSociale value
     */
    public void setRaisonSociale(String pRaisonSociale) {
        this.raisonSociale = pRaisonSociale;
    }

    /**
     *
     * @return roleadresse
     */
    public Set<RoleAdresseDTO> getRoleadresse() {
        return this.roleadresse;
    }

    /**
     *
     * @param pRoleadresse roleadresse value
     */
    public void setRoleadresse(Set<RoleAdresseDTO> pRoleadresse) {
        this.roleadresse = pRoleadresse;
    }

    /**
     *
     * @return statutMedium
     */
    public String getStatutMedium() {
        return this.statutMedium;
    }

    /**
     *
     * @param pStatutMedium statutMedium value
     */
    public void setStatutMedium(String pStatutMedium) {
        this.statutMedium = pStatutMedium;
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
     * @return ville
     */
    public String getVille() {
        return this.ville;
    }

    /**
     *
     * @param pVille ville value
     */
    public void setVille(String pVille) {
        this.ville = pVille;
    }

    /**
     *
     * @return object as string
     */
    public String toString() {
        String result = null;
        /*PROTECTED REGION ID(toString_RZiF4FxpEeC2fu5mfEzrgA) ENABLED START*/
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
        buffer.append("cleAdresse=").append(getCleAdresse());
        buffer.append(",");
        buffer.append("version=").append(getVersion());
        buffer.append(",");
        buffer.append("numeroUsage=").append(getNumeroUsage());
        buffer.append(",");
        buffer.append("codeMedium=").append(getCodeMedium());
        buffer.append(",");
        buffer.append("indicAdrNorm=").append(getIndicAdrNorm());
        buffer.append(",");
        buffer.append("statutMedium=").append(getStatutMedium());
        buffer.append(",");
        buffer.append("raisonSociale=").append(getRaisonSociale());
        buffer.append(",");
        buffer.append("ville=").append(getVille());
        buffer.append(",");
        buffer.append("complAdr=").append(getComplAdr());
        buffer.append(",");
        buffer.append("numeroRue=").append(getNumeroRue());
        buffer.append(",");
        buffer.append("lieuDit=").append(getLieuDit());
        buffer.append(",");
        buffer.append("codePostal=").append(getCodePostal());
        buffer.append(",");
        buffer.append("codePays=").append(getCodePays());
        buffer.append(",");
        buffer.append("codeProvince=").append(getCodeProvince());
        buffer.append("]");
        return buffer.toString();
    }

    /*PROTECTED REGION ID(_RZiF4FxpEeC2fu5mfEzrgA u m) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/

}
