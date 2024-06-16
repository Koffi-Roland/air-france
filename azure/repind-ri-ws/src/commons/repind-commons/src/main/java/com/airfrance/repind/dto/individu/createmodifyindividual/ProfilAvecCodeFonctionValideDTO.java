package com.airfrance.repind.dto.individu.createmodifyindividual;

/*PROTECTED REGION ID(_nV_MejRkEeCc7ZsKsK1lbQ i) ENABLED START*/

// add not generated imports here
import org.apache.commons.lang.builder.ToStringBuilder;


/*PROTECTED REGION END*/

/**
 * <p>Title : ProfilAvecCodeFonctionValideDTO</p>
 * 
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
public class ProfilAvecCodeFonctionValideDTO  {
        
    /**
     * cleProfil
     */
    private Integer cleProfil;
        
        
    /**
     * indicMailing
     */
    private String indicMailing;
        
        
    /**
     * indicSolvabilite
     */
    private String indicSolvabilite;
        
        
    /**
     * codeDomainePro
     */
    private String codeDomainePro;
        
        
    /**
     * libelDomainePro
     */
    private String libelDomainePro;
        
        
    /**
     * codeMarital
     */
    private String codeMarital;
        
        
    /**
     * codeLangue
     */
    private String codeLangue;
        
        
    /**
     * codeFonctionPro
     */
    private String codeFonctionPro;
        
        
    /**
     * libelFonctionPro
     */
    private String libelFonctionPro;
        
        
    /**
     * nbEnfants
     */
    private Integer nbEnfants;
        
        
    /**
     * segmentClient
     */
    private String segmentClient;
        
        
    /**
     * codeEtudiant
     */
    private String codeEtudiant;
        

    /*PROTECTED REGION ID(_nV_MejRkEeCc7ZsKsK1lbQ u var) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/

    
    
    /** 
     * default constructor 
     */
    public ProfilAvecCodeFonctionValideDTO() {
    
    }
    
    
    /** 
     * full constructor
     * @param pCleProfil cleProfil
     * @param pIndicMailing indicMailing
     * @param pIndicSolvabilite indicSolvabilite
     * @param pCodeDomainePro codeDomainePro
     * @param pLibelDomainePro libelDomainePro
     * @param pCodeMarital codeMarital
     * @param pCodeLangue codeLangue
     * @param pCodeFonctionPro codeFonctionPro
     * @param pLibelFonctionPro libelFonctionPro
     * @param pNbEnfants nbEnfants
     * @param pSegmentClient segmentClient
     * @param pCodeEtudiant codeEtudiant
     */
    public ProfilAvecCodeFonctionValideDTO(Integer pCleProfil, String pIndicMailing, String pIndicSolvabilite, String pCodeDomainePro, String pLibelDomainePro, String pCodeMarital, String pCodeLangue, String pCodeFonctionPro, String pLibelFonctionPro, Integer pNbEnfants, String pSegmentClient, String pCodeEtudiant) {
        this.cleProfil = pCleProfil;
        this.indicMailing = pIndicMailing;
        this.indicSolvabilite = pIndicSolvabilite;
        this.codeDomainePro = pCodeDomainePro;
        this.libelDomainePro = pLibelDomainePro;
        this.codeMarital = pCodeMarital;
        this.codeLangue = pCodeLangue;
        this.codeFonctionPro = pCodeFonctionPro;
        this.libelFonctionPro = pLibelFonctionPro;
        this.nbEnfants = pNbEnfants;
        this.segmentClient = pSegmentClient;
        this.codeEtudiant = pCodeEtudiant;
    }

    /**
     *
     * @return cleProfil
     */
    public Integer getCleProfil() {
        return this.cleProfil;
    }

    /**
     *
     * @param pCleProfil cleProfil value
     */
    public void setCleProfil(Integer pCleProfil) {
        this.cleProfil = pCleProfil;
    }

    /**
     *
     * @return codeDomainePro
     */
    public String getCodeDomainePro() {
        return this.codeDomainePro;
    }

    /**
     *
     * @param pCodeDomainePro codeDomainePro value
     */
    public void setCodeDomainePro(String pCodeDomainePro) {
        this.codeDomainePro = pCodeDomainePro;
    }

    /**
     *
     * @return codeEtudiant
     */
    public String getCodeEtudiant() {
        return this.codeEtudiant;
    }

    /**
     *
     * @param pCodeEtudiant codeEtudiant value
     */
    public void setCodeEtudiant(String pCodeEtudiant) {
        this.codeEtudiant = pCodeEtudiant;
    }

    /**
     *
     * @return codeFonctionPro
     */
    public String getCodeFonctionPro() {
        return this.codeFonctionPro;
    }

    /**
     *
     * @param pCodeFonctionPro codeFonctionPro value
     */
    public void setCodeFonctionPro(String pCodeFonctionPro) {
        this.codeFonctionPro = pCodeFonctionPro;
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
     * @return codeMarital
     */
    public String getCodeMarital() {
        return this.codeMarital;
    }

    /**
     *
     * @param pCodeMarital codeMarital value
     */
    public void setCodeMarital(String pCodeMarital) {
        this.codeMarital = pCodeMarital;
    }

    /**
     *
     * @return indicMailing
     */
    public String getIndicMailing() {
        return this.indicMailing;
    }

    /**
     *
     * @param pIndicMailing indicMailing value
     */
    public void setIndicMailing(String pIndicMailing) {
        this.indicMailing = pIndicMailing;
    }

    /**
     *
     * @return indicSolvabilite
     */
    public String getIndicSolvabilite() {
        return this.indicSolvabilite;
    }

    /**
     *
     * @param pIndicSolvabilite indicSolvabilite value
     */
    public void setIndicSolvabilite(String pIndicSolvabilite) {
        this.indicSolvabilite = pIndicSolvabilite;
    }

    /**
     *
     * @return libelDomainePro
     */
    public String getLibelDomainePro() {
        return this.libelDomainePro;
    }

    /**
     *
     * @param pLibelDomainePro libelDomainePro value
     */
    public void setLibelDomainePro(String pLibelDomainePro) {
        this.libelDomainePro = pLibelDomainePro;
    }

    /**
     *
     * @return libelFonctionPro
     */
    public String getLibelFonctionPro() {
        return this.libelFonctionPro;
    }

    /**
     *
     * @param pLibelFonctionPro libelFonctionPro value
     */
    public void setLibelFonctionPro(String pLibelFonctionPro) {
        this.libelFonctionPro = pLibelFonctionPro;
    }

    /**
     *
     * @return nbEnfants
     */
    public Integer getNbEnfants() {
        return this.nbEnfants;
    }

    /**
     *
     * @param pNbEnfants nbEnfants value
     */
    public void setNbEnfants(Integer pNbEnfants) {
        this.nbEnfants = pNbEnfants;
    }

    /**
     *
     * @return segmentClient
     */
    public String getSegmentClient() {
        return this.segmentClient;
    }

    /**
     *
     * @param pSegmentClient segmentClient value
     */
    public void setSegmentClient(String pSegmentClient) {
        this.segmentClient = pSegmentClient;
    }

    /**
     *
     * @return object as string
     */
    public String toString() {
        String result = null;
        /*PROTECTED REGION ID(toString_nV_MejRkEeCc7ZsKsK1lbQ) ENABLED START*/
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
            .append("cleProfil", getCleProfil())
            .append("indicMailing", getIndicMailing())
            .append("indicSolvabilite", getIndicSolvabilite())
            .append("codeDomainePro", getCodeDomainePro())
            .append("libelDomainePro", getLibelDomainePro())
            .append("codeMarital", getCodeMarital())
            .append("codeLangue", getCodeLangue())
            .append("codeFonctionPro", getCodeFonctionPro())
            .append("libelFonctionPro", getLibelFonctionPro())
            .append("nbEnfants", getNbEnfants())
            .append("segmentClient", getSegmentClient())
            .append("codeEtudiant", getCodeEtudiant())
            .toString();
    }

    /*PROTECTED REGION ID(_nV_MejRkEeCc7ZsKsK1lbQ u m) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/

}
