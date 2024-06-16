package com.airfrance.repind.dto.firme;

/*PROTECTED REGION ID(_08z54GkzEeGhB9497mGnHw i) ENABLED START*/

// add not generated imports here
import java.io.Serializable;
import java.util.Date;

/*PROTECTED REGION END*/

/**
 * <p>Title : GestionPMDTO</p>
 * 
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
public class GestionPMDTO  implements Serializable {
        
    /**
	 * 
	 */
	private static final long serialVersionUID = -5111514639267170516L;


	/**
     * cle
     */
    private Integer cle;
        
        
    /**
     * version
     */
    private Integer version;
        
        
    /**
     * dateDebLien
     */
    private Date dateDebLien;
        
        
    /**
     * dateFinLien
     */
    private Date dateFinLien;
        
        
    /**
     * dateMaj
     */
    private Date dateMaj;
        
        
    /**
     * lienZCFirme
     */
    private String lienZCFirme;
        
        
    /**
     * privilegie
     */
    private String privilegie;
        
        
    /**
     * signatureMaj
     */
    private String signatureMaj;
        
        
    /**
     * siteMaj
     */
    private String siteMaj;
        
        
    /**
     * typeLien
     */
    private String typeLien;
        
        
    /**
     * personneMoraleGerante
     */
    private PersonneMoraleDTO personneMoraleGerante;
        
        
    /**
     * personneMoraleGeree
     */
    private PersonneMoraleDTO personneMoraleGeree;
        

    /*PROTECTED REGION ID(_08z54GkzEeGhB9497mGnHw u var) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/

    
    
    /** 
     * default constructor 
     */
    public GestionPMDTO() {
    
    }
    
    
    /** 
     * full constructor
     * @param pCle cle
     * @param pVersion version
     * @param pDateDebLien dateDebLien
     * @param pDateFinLien dateFinLien
     * @param pDateMaj dateMaj
     * @param pLienZCFirme lienZCFirme
     * @param pPrivilegie privilegie
     * @param pSignatureMaj signatureMaj
     * @param pSiteMaj siteMaj
     * @param pTypeLien typeLien
     */
    public GestionPMDTO(Integer pCle, Integer pVersion, Date pDateDebLien, Date pDateFinLien, Date pDateMaj, String pLienZCFirme, String pPrivilegie, String pSignatureMaj, String pSiteMaj, String pTypeLien) {
        this.cle = pCle;
        this.version = pVersion;
        this.dateDebLien = pDateDebLien;
        this.dateFinLien = pDateFinLien;
        this.dateMaj = pDateMaj;
        this.lienZCFirme = pLienZCFirme;
        this.privilegie = pPrivilegie;
        this.signatureMaj = pSignatureMaj;
        this.siteMaj = pSiteMaj;
        this.typeLien = pTypeLien;
    }

    /**
     *
     * @return cle
     */
    public Integer getCle() {
        return this.cle;
    }

    /**
     *
     * @param pCle cle value
     */
    public void setCle(Integer pCle) {
        this.cle = pCle;
    }

    /**
     *
     * @return dateDebLien
     */
    public Date getDateDebLien() {
        return this.dateDebLien;
    }

    /**
     *
     * @param pDateDebLien dateDebLien value
     */
    public void setDateDebLien(Date pDateDebLien) {
        this.dateDebLien = pDateDebLien;
    }

    /**
     *
     * @return dateFinLien
     */
    public Date getDateFinLien() {
        return this.dateFinLien;
    }

    /**
     *
     * @param pDateFinLien dateFinLien value
     */
    public void setDateFinLien(Date pDateFinLien) {
        this.dateFinLien = pDateFinLien;
    }

    /**
     *
     * @return dateMaj
     */
    public Date getDateMaj() {
        return this.dateMaj;
    }

    /**
     *
     * @param pDateMaj dateMaj value
     */
    public void setDateMaj(Date pDateMaj) {
        this.dateMaj = pDateMaj;
    }

    /**
     *
     * @return lienZCFirme
     */
    public String getLienZCFirme() {
        return this.lienZCFirme;
    }

    /**
     *
     * @param pLienZCFirme lienZCFirme value
     */
    public void setLienZCFirme(String pLienZCFirme) {
        this.lienZCFirme = pLienZCFirme;
    }

    /**
     *
     * @return personneMoraleGerante
     */
    public PersonneMoraleDTO getPersonneMoraleGerante() {
        return this.personneMoraleGerante;
    }

    /**
     *
     * @param pPersonneMoraleGerante personneMoraleGerante value
     */
    public void setPersonneMoraleGerante(PersonneMoraleDTO pPersonneMoraleGerante) {
        this.personneMoraleGerante = pPersonneMoraleGerante;
    }

    /**
     *
     * @return personneMoraleGeree
     */
    public PersonneMoraleDTO getPersonneMoraleGeree() {
        return this.personneMoraleGeree;
    }

    /**
     *
     * @param pPersonneMoraleGeree personneMoraleGeree value
     */
    public void setPersonneMoraleGeree(PersonneMoraleDTO pPersonneMoraleGeree) {
        this.personneMoraleGeree = pPersonneMoraleGeree;
    }

    /**
     *
     * @return privilegie
     */
    public String getPrivilegie() {
        return this.privilegie;
    }

    /**
     *
     * @param pPrivilegie privilegie value
     */
    public void setPrivilegie(String pPrivilegie) {
        this.privilegie = pPrivilegie;
    }

    /**
     *
     * @return signatureMaj
     */
    public String getSignatureMaj() {
        return this.signatureMaj;
    }

    /**
     *
     * @param pSignatureMaj signatureMaj value
     */
    public void setSignatureMaj(String pSignatureMaj) {
        this.signatureMaj = pSignatureMaj;
    }

    /**
     *
     * @return siteMaj
     */
    public String getSiteMaj() {
        return this.siteMaj;
    }

    /**
     *
     * @param pSiteMaj siteMaj value
     */
    public void setSiteMaj(String pSiteMaj) {
        this.siteMaj = pSiteMaj;
    }

    /**
     *
     * @return typeLien
     */
    public String getTypeLien() {
        return this.typeLien;
    }

    /**
     *
     * @param pTypeLien typeLien value
     */
    public void setTypeLien(String pTypeLien) {
        this.typeLien = pTypeLien;
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
     * @return object as string
     */
    public String toString() {
        String result = null;
        /*PROTECTED REGION ID(toString_08z54GkzEeGhB9497mGnHw) ENABLED START*/
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
        buffer.append("cle=").append(getCle());
        buffer.append(",");
        buffer.append("version=").append(getVersion());
        buffer.append(",");
        buffer.append("dateDebLien=").append(getDateDebLien());
        buffer.append(",");
        buffer.append("dateFinLien=").append(getDateFinLien());
        buffer.append(",");
        buffer.append("dateMaj=").append(getDateMaj());
        buffer.append(",");
        buffer.append("lienZCFirme=").append(getLienZCFirme());
        buffer.append(",");
        buffer.append("privilegie=").append(getPrivilegie());
        buffer.append(",");
        buffer.append("signatureMaj=").append(getSignatureMaj());
        buffer.append(",");
        buffer.append("siteMaj=").append(getSiteMaj());
        buffer.append(",");
        buffer.append("typeLien=").append(getTypeLien());
        buffer.append("]");
        return buffer.toString();
    }

    /*PROTECTED REGION ID(_08z54GkzEeGhB9497mGnHw u m) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/

}
