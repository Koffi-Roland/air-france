package com.airfrance.repind.entity.profil;

/*PROTECTED REGION ID(_QSW-QLbFEeCrCZp8iGNNVw i) ENABLED START*/

// add not generated imports here

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/*PROTECTED REGION END*/


/**
 * <p>Title : ProfilFirme.java</p>
 * BO ProfilFirme
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
@Entity
@Table(name="PROFIL_FIRME")
public class ProfilFirme implements Serializable {

/*PROTECTED REGION ID(serialUID _QSW-QLbFEeCrCZp8iGNNVw) ENABLED START*/
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
     * gin
     */
    @Id
    @Column(name="SGIN", length=12, nullable=false)
    private String gin;
        
            
    /**
     * exporting
     */
    @Column(name="SEXPORT", length=1)
    private String exporting;
        
            
    /**
     * mailing
     */
    @Column(name="SMAILING", length=2)
    private String mailing;
        
            
    /**
     * codeInseeEmp
     */
    @Column(name="SCODE_INSEE_EMP", length=2)
    private String codeInseeEmp;
        
            
    /**
     * defautPaiement
     */
    @Column(name="SDEFAUT_PAIEMENT", length=1)
    private String defautPaiement;
        
            
    /**
     * interdictionVente
     */
    @Column(name="SINTERDICTION_VENTE", length=1)
    private String interdictionVente;
        
            
    /**
     * langueEcrite
     */
    @Column(name="SLANGUE_ECRITE", length=2)
    private String langueEcrite;
        
            
    /**
     * nationalite
     */
    @Column(name="SNATIONALITE", length=2)
    private String nationalite;
        
            
    /**
     * nombreEmploye
     */
    @Column(name="INOMBRE_EMPLOYE", length=10)
    private Integer nombreEmploye;
        
            
    /**
     * importing
     */
    @Column(name="SIMPORT", length=1)
    private String importing;
        
            
    /**
     * langueParlee
     */
    @Column(name="SLANGUE_PARLEE", length=2)
    private String langueParlee;
        
            
    /**
     * niveauSegmentation
     */
    @Column(name="SNIVEAU_SEGMENTATION", length=3)
    private String niveauSegmentation;
        
            
    /**
     * typeClient
     */
    @Column(name="STYPE_CLIENT", length=1)
    private String typeClient;
        
            
    /**
     * typeSegmentation
     */
    @Column(name="STYPE_SEGMENTATION", length=3)
    private String typeSegmentation;
        
            
    /**
     * codGrpTie
     */
    @Column(name="COD_GRP_TIE", length=1)
    private String codGrpTie;
        
            
    /**
     * codTypTie
     */
    @Column(name="COD_TYP_TIE", length=1)
    private String codTypTie;
        

    /*PROTECTED REGION ID(_QSW-QLbFEeCrCZp8iGNNVw u var) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/
    
    /** 
     * default constructor 
     */
    public ProfilFirme() {
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
    public ProfilFirme(String pGin, String pExporting, String pMailing, String pCodeInseeEmp, String pDefautPaiement, String pInterdictionVente, String pLangueEcrite, String pNationalite, Integer pNombreEmploye, String pImporting, String pLangueParlee, String pNiveauSegmentation, String pTypeClient, String pTypeSegmentation, String pCodGrpTie, String pCodTypTie) {
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
        /*PROTECTED REGION ID(toString_QSW-QLbFEeCrCZp8iGNNVw) ENABLED START*/
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

     
    
    /*PROTECTED REGION ID(equals hash _QSW-QLbFEeCrCZp8iGNNVw) ENABLED START*/

    /**
     * {@inheritDoc}
     * @see java.lang.Object#equals(java.lang.Object)
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
        final ProfilFirme other = (ProfilFirme) obj;

        // TODO: writes or generates equals method

        return super.equals(other);
    }

    /**
     * {@inheritDoc}
     * @see java.lang.Object#hashCode()
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

    /*PROTECTED REGION ID(_QSW-QLbFEeCrCZp8iGNNVw u m) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/
    
}
