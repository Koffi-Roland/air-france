package com.airfrance.repind.dto.profil;

/*PROTECTED REGION ID(_6dhK8LdeEeCrCZp8iGNNVw i) ENABLED START*/

// add not generated imports here

import java.io.Serializable;

/*PROTECTED REGION END*/

/**
 * <p>Title : ProfilFirmeDTO</p>
 * 
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
public class ProfilFirmeDTO implements Serializable {
        
    /**
	 * 
	 */
	private static final long serialVersionUID = 8242651039490533951L;


	/**
     * gin
     */
    private String gin;
        
        
    /**
     * exporting
     */
    private String exporting;
        
        
    /**
     * mailing
     */
    private String mailing;
        
        
    /**
     * codeInseeEmp
     */
    private String codeInseeEmp;
        
        
    /**
     * defautPaiement
     */
    private String defautPaiement;
        
        
    /**
     * interdictionVente
     */
    private String interdictionVente;
        
        
    /**
     * langueEcrite
     */
    private String langueEcrite;
        
        
    /**
     * nationalite
     */
    private String nationalite;
        
        
    /**
     * nombreEmploye
     */
    private Integer nombreEmploye;
        
        
    /**
     * importing
     */
    private String importing;
        
        
    /**
     * langueParlee
     */
    private String langueParlee;
        
        
    /**
     * niveauSegmentation
     */
    private String niveauSegmentation;
        
        
    /**
     * typeClient
     */
    private String typeClient;
        
        
    /**
     * typeSegmentation
     */
    private String typeSegmentation;
        
        
    /**
     * codGrpTie
     */
    private String codGrpTie;
        
        
    /**
     * codTypTie
     */
    private String codTypTie;
        
        
    /**
     * personneMorale

    private PersonneMoraleDTO personneMorale;
     */

    /*PROTECTED REGION ID(_6dhK8LdeEeCrCZp8iGNNVw u var) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/

    
    
    /** 
     * default constructor 
     */
    public ProfilFirmeDTO() {
    
    }
    
    
    /** 
     * full constructor
     * @param pGin gin
     * @param pExporting exporting
     * @param pMailing mailing
     * @param pCodeInseeEmp codeInseeEmp
     * @param pDefautPaiement defautPaiement
     * @param pInterdictionVente interdictionVente
     * @param pLangueEcrite langueEcrite
     * @param pNationalite nationalite
     * @param pNombreEmploye nombreEmploye
     * @param pImporting importing
     * @param pLangueParlee langueParlee
     * @param pNiveauSegmentation niveauSegmentation
     * @param pTypeClient typeClient
     * @param pTypeSegmentation typeSegmentation
     * @param pCodGrpTie codGrpTie
     * @param pCodTypTie codTypTie
     */
    public ProfilFirmeDTO(String pGin, String pExporting, String pMailing, String pCodeInseeEmp, String pDefautPaiement, String pInterdictionVente, String pLangueEcrite, String pNationalite, Integer pNombreEmploye, String pImporting, String pLangueParlee, String pNiveauSegmentation, String pTypeClient, String pTypeSegmentation, String pCodGrpTie, String pCodTypTie) {
        this.gin = pGin;
        this.exporting = pExporting;
        this.mailing = pMailing;
        this.codeInseeEmp = pCodeInseeEmp;
        this.defautPaiement = pDefautPaiement;
        this.interdictionVente = pInterdictionVente;
        this.langueEcrite = pLangueEcrite;
        this.nationalite = pNationalite;
        this.nombreEmploye = pNombreEmploye;
        this.importing = pImporting;
        this.langueParlee = pLangueParlee;
        this.niveauSegmentation = pNiveauSegmentation;
        this.typeClient = pTypeClient;
        this.typeSegmentation = pTypeSegmentation;
        this.codGrpTie = pCodGrpTie;
        this.codTypTie = pCodTypTie;
    }

    /**
     *
     * @return codGrpTie
     */
    public String getCodGrpTie() {
        return this.codGrpTie;
    }

    /**
     *
     * @param pCodGrpTie codGrpTie value
     */
    public void setCodGrpTie(String pCodGrpTie) {
        this.codGrpTie = pCodGrpTie;
    }

    /**
     *
     * @return codTypTie
     */
    public String getCodTypTie() {
        return this.codTypTie;
    }

    /**
     *
     * @param pCodTypTie codTypTie value
     */
    public void setCodTypTie(String pCodTypTie) {
        this.codTypTie = pCodTypTie;
    }

    /**
     *
     * @return codeInseeEmp
     */
    public String getCodeInseeEmp() {
        return this.codeInseeEmp;
    }

    /**
     *
     * @param pCodeInseeEmp codeInseeEmp value
     */
    public void setCodeInseeEmp(String pCodeInseeEmp) {
        this.codeInseeEmp = pCodeInseeEmp;
    }

    /**
     *
     * @return defautPaiement
     */
    public String getDefautPaiement() {
        return this.defautPaiement;
    }

    /**
     *
     * @param pDefautPaiement defautPaiement value
     */
    public void setDefautPaiement(String pDefautPaiement) {
        this.defautPaiement = pDefautPaiement;
    }

    /**
     *
     * @return exporting
     */
    public String getExporting() {
        return this.exporting;
    }

    /**
     *
     * @param pExporting exporting value
     */
    public void setExporting(String pExporting) {
        this.exporting = pExporting;
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
     * @return importing
     */
    public String getImporting() {
        return this.importing;
    }

    /**
     *
     * @param pImporting importing value
     */
    public void setImporting(String pImporting) {
        this.importing = pImporting;
    }

    /**
     *
     * @return interdictionVente
     */
    public String getInterdictionVente() {
        return this.interdictionVente;
    }

    /**
     *
     * @param pInterdictionVente interdictionVente value
     */
    public void setInterdictionVente(String pInterdictionVente) {
        this.interdictionVente = pInterdictionVente;
    }

    /**
     *
     * @return langueEcrite
     */
    public String getLangueEcrite() {
        return this.langueEcrite;
    }

    /**
     *
     * @param pLangueEcrite langueEcrite value
     */
    public void setLangueEcrite(String pLangueEcrite) {
        this.langueEcrite = pLangueEcrite;
    }

    /**
     *
     * @return langueParlee
     */
    public String getLangueParlee() {
        return this.langueParlee;
    }

    /**
     *
     * @param pLangueParlee langueParlee value
     */
    public void setLangueParlee(String pLangueParlee) {
        this.langueParlee = pLangueParlee;
    }

    /**
     *
     * @return mailing
     */
    public String getMailing() {
        return this.mailing;
    }

    /**
     *
     * @param pMailing mailing value
     */
    public void setMailing(String pMailing) {
        this.mailing = pMailing;
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
     * @return niveauSegmentation
     */
    public String getNiveauSegmentation() {
        return this.niveauSegmentation;
    }

    /**
     *
     * @param pNiveauSegmentation niveauSegmentation value
     */
    public void setNiveauSegmentation(String pNiveauSegmentation) {
        this.niveauSegmentation = pNiveauSegmentation;
    }

    /**
     *
     * @return nombreEmploye
     */
    public Integer getNombreEmploye() {
        return this.nombreEmploye;
    }

    /**
     *
     * @param pNombreEmploye nombreEmploye value
     */
    public void setNombreEmploye(Integer pNombreEmploye) {
        this.nombreEmploye = pNombreEmploye;
    }

    /**
     *
     * @return personneMorale

    public PersonneMoraleDTO getPersonneMorale() {
        return this.personneMorale;
    }

    /**
     *
     * @param pPersonneMorale personneMorale value

    public void setPersonneMorale(PersonneMoraleDTO pPersonneMorale) {
        this.personneMorale = pPersonneMorale;
    }
     */

    /**
     *
     * @return typeClient
     */
    public String getTypeClient() {
        return this.typeClient;
    }

    /**
     *
     * @param pTypeClient typeClient value
     */
    public void setTypeClient(String pTypeClient) {
        this.typeClient = pTypeClient;
    }

    /**
     *
     * @return typeSegmentation
     */
    public String getTypeSegmentation() {
        return this.typeSegmentation;
    }

    /**
     *
     * @param pTypeSegmentation typeSegmentation value
     */
    public void setTypeSegmentation(String pTypeSegmentation) {
        this.typeSegmentation = pTypeSegmentation;
    }

    /**
     *
     * @return object as string
     */
    public String toString() {
        String result = null;
        /*PROTECTED REGION ID(toString_6dhK8LdeEeCrCZp8iGNNVw) ENABLED START*/
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
        buffer.append("exporting=").append(getExporting());
        buffer.append(",");
        buffer.append("mailing=").append(getMailing());
        buffer.append(",");
        buffer.append("codeInseeEmp=").append(getCodeInseeEmp());
        buffer.append(",");
        buffer.append("defautPaiement=").append(getDefautPaiement());
        buffer.append(",");
        buffer.append("interdictionVente=").append(getInterdictionVente());
        buffer.append(",");
        buffer.append("langueEcrite=").append(getLangueEcrite());
        buffer.append(",");
        buffer.append("nationalite=").append(getNationalite());
        buffer.append(",");
        buffer.append("nombreEmploye=").append(getNombreEmploye());
        buffer.append(",");
        buffer.append("importing=").append(getImporting());
        buffer.append(",");
        buffer.append("langueParlee=").append(getLangueParlee());
        buffer.append(",");
        buffer.append("niveauSegmentation=").append(getNiveauSegmentation());
        buffer.append(",");
        buffer.append("typeClient=").append(getTypeClient());
        buffer.append(",");
        buffer.append("typeSegmentation=").append(getTypeSegmentation());
        buffer.append(",");
        buffer.append("codGrpTie=").append(getCodGrpTie());
        buffer.append(",");
        buffer.append("codTypTie=").append(getCodTypTie());
        buffer.append("]");
        return buffer.toString();
    }

    /*PROTECTED REGION ID(_6dhK8LdeEeCrCZp8iGNNVw u m) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/

}
