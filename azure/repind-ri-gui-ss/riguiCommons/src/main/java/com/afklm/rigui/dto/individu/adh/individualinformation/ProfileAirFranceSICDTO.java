package com.afklm.rigui.dto.individu.adh.individualinformation;

/*PROTECTED REGION ID(_b1qT0yMUEeCWJOBY8f-ONQ i) ENABLED START*/

// add not generated imports here
import org.apache.commons.lang.builder.ToStringBuilder;


/*PROTECTED REGION END*/

/**
 * <p>Title : ProfileAirFranceSICDTO</p>
 * 
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
public class ProfileAirFranceSICDTO  {
        
    /**
     * matricule
     */
    private String matricule;
        
        
    /**
     * rang
     */
    private String rang;
        
        
    /**
     * adresseNotes
     */
    private String adresseNotes;
        
        
    /**
     * motDePasse
     */
    private String motDePasse;
        
        
    /**
     * fonction
     */
    private String fonction;
        
        
    /**
     * referenceR
     */
    private String referenceR;
        
        
    /**
     * typologie
     */
    private String typologie;
        
        
    /**
     * codeOrigine
     */
    private String codeOrigine;
        
        
    /**
     * codeCompagnie
     */
    private String codeCompagnie;
        
        
    /**
     * codeStatut
     */
    private String codeStatut;
        

    /*PROTECTED REGION ID(_b1qT0yMUEeCWJOBY8f-ONQ u var) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/

    
    
    /** 
     * default constructor 
     */
    public ProfileAirFranceSICDTO() {
    
    }
    
    
    /** 
     * full constructor
     * @param pMatricule matricule
     * @param pRang rang
     * @param pAdresseNotes adresseNotes
     * @param pMotDePasse motDePasse
     * @param pFonction fonction
     * @param pReferenceR referenceR
     * @param pTypologie typologie
     * @param pCodeOrigine codeOrigine
     * @param pCodeCompagnie codeCompagnie
     * @param pCodeStatut codeStatut
     */
    public ProfileAirFranceSICDTO(String pMatricule, String pRang, String pAdresseNotes, String pMotDePasse, String pFonction, String pReferenceR, String pTypologie, String pCodeOrigine, String pCodeCompagnie, String pCodeStatut) {
        this.matricule = pMatricule;
        this.rang = pRang;
        this.adresseNotes = pAdresseNotes;
        this.motDePasse = pMotDePasse;
        this.fonction = pFonction;
        this.referenceR = pReferenceR;
        this.typologie = pTypologie;
        this.codeOrigine = pCodeOrigine;
        this.codeCompagnie = pCodeCompagnie;
        this.codeStatut = pCodeStatut;
    }

    /**
     *
     * @return adresseNotes
     */
    public String getAdresseNotes() {
        return this.adresseNotes;
    }

    /**
     *
     * @param pAdresseNotes adresseNotes value
     */
    public void setAdresseNotes(String pAdresseNotes) {
        this.adresseNotes = pAdresseNotes;
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
     * @return codeOrigine
     */
    public String getCodeOrigine() {
        return this.codeOrigine;
    }

    /**
     *
     * @param pCodeOrigine codeOrigine value
     */
    public void setCodeOrigine(String pCodeOrigine) {
        this.codeOrigine = pCodeOrigine;
    }

    /**
     *
     * @return codeStatut
     */
    public String getCodeStatut() {
        return this.codeStatut;
    }

    /**
     *
     * @param pCodeStatut codeStatut value
     */
    public void setCodeStatut(String pCodeStatut) {
        this.codeStatut = pCodeStatut;
    }

    /**
     *
     * @return fonction
     */
    public String getFonction() {
        return this.fonction;
    }

    /**
     *
     * @param pFonction fonction value
     */
    public void setFonction(String pFonction) {
        this.fonction = pFonction;
    }

    /**
     *
     * @return matricule
     */
    public String getMatricule() {
        return this.matricule;
    }

    /**
     *
     * @param pMatricule matricule value
     */
    public void setMatricule(String pMatricule) {
        this.matricule = pMatricule;
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
     * @return rang
     */
    public String getRang() {
        return this.rang;
    }

    /**
     *
     * @param pRang rang value
     */
    public void setRang(String pRang) {
        this.rang = pRang;
    }

    /**
     *
     * @return referenceR
     */
    public String getReferenceR() {
        return this.referenceR;
    }

    /**
     *
     * @param pReferenceR referenceR value
     */
    public void setReferenceR(String pReferenceR) {
        this.referenceR = pReferenceR;
    }

    /**
     *
     * @return typologie
     */
    public String getTypologie() {
        return this.typologie;
    }

    /**
     *
     * @param pTypologie typologie value
     */
    public void setTypologie(String pTypologie) {
        this.typologie = pTypologie;
    }

    /**
     *
     * @return object as string
     */
    public String toString() {
        String result = null;
        /*PROTECTED REGION ID(toString_b1qT0yMUEeCWJOBY8f-ONQ) ENABLED START*/
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
            .append("matricule", getMatricule())
            .append("rang", getRang())
            .append("adresseNotes", getAdresseNotes())
            .append("motDePasse", getMotDePasse())
            .append("fonction", getFonction())
            .append("referenceR", getReferenceR())
            .append("typologie", getTypologie())
            .append("codeOrigine", getCodeOrigine())
            .append("codeCompagnie", getCodeCompagnie())
            .append("codeStatut", getCodeStatut())
            .toString();
    }

    /*PROTECTED REGION ID(_b1qT0yMUEeCWJOBY8f-ONQ u m) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/

}
