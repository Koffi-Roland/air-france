package com.airfrance.repind.dto.profil;

/*PROTECTED REGION ID(_S9GW1jUfEeCq6pHdxM8RnQ i) ENABLED START*/

// add not generated imports here
import java.io.Serializable;

/*PROTECTED REGION END*/

/**
 * <p>Title : ProfilsDTO</p>
 * 
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
public class ProfilsDTO  implements Serializable {
        
    /**
	 * 
	 */
	private static final long serialVersionUID = 3661543950489886269L;


	/**
     * sgin
     */
    private String sgin;
        
        
    /**
     * iversion
     */
    private Integer iversion;
        
        
    /**
     * smailing_autorise
     */
    private String smailing_autorise;
        
        
    /**
     * srin
     */
    private String srin;
        
        
    /**
     * ssolvabilite
     */
    private String ssolvabilite;
        
        
    /**
     * scode_professionnel
     */
    private String scode_professionnel;
        
        
    /**
     * scode_maritale
     */
    private String scode_maritale;
        
        
    /**
     * scode_langue
     */
    private String scode_langue;
        
        
    /**
     * scode_fonction
     */
    private String scode_fonction;
        
        
    /**
     * inb_enfants
     */
    private Integer inb_enfants;
        
        
    /**
     * ssegment
     */
    private String ssegment;
        
        
    /**
     * setudiant
     */
    private String setudiant;
        

    /*PROTECTED REGION ID(_S9GW1jUfEeCq6pHdxM8RnQ u var) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/

    
    
    /** 
     * default constructor 
     */
    public ProfilsDTO() {
    
    }
    
    
    /** 
     * full constructor
     * @param pIversion iversion
     * @param pSmailing_autorise smailing_autorise
     * @param pSrin srin
     * @param pSsolvabilite ssolvabilite
     * @param pScode_professionnel scode_professionnel
     * @param pScode_maritale scode_maritale
     * @param pScode_langue scode_langue
     * @param pSgin sgin
     * @param pScode_fonction scode_fonction
     * @param pInb_enfants inb_enfants
     * @param pSsegment ssegment
     * @param pSetudiant setudiant
     */
    public ProfilsDTO(Integer pIversion, String pSmailing_autorise, String pSrin, String pSsolvabilite, String pScode_professionnel, String pScode_maritale, String pScode_langue, String pSgin, String pScode_fonction, Integer pInb_enfants, String pSsegment, String pSetudiant) {
        this.iversion = pIversion;
        this.smailing_autorise = pSmailing_autorise;
        this.srin = pSrin;
        this.ssolvabilite = pSsolvabilite;
        this.scode_professionnel = pScode_professionnel;
        this.scode_maritale = pScode_maritale;
        this.scode_langue = pScode_langue;
        this.sgin = pSgin;
        this.scode_fonction = pScode_fonction;
        this.inb_enfants = pInb_enfants;
        this.ssegment = pSsegment;
        this.setudiant = pSetudiant;
    }

    /**
     *
     * @return inb_enfants
     */
    public Integer getInb_enfants() {
        return this.inb_enfants;
    }

    /**
     *
     * @param pInb_enfants inb_enfants value
     */
    public void setInb_enfants(Integer pInb_enfants) {
        this.inb_enfants = pInb_enfants;
    }

    /**
     *
     * @return iversion
     */
    public Integer getIversion() {
        return this.iversion;
    }

    /**
     *
     * @param pIversion iversion value
     */
    public void setIversion(Integer pIversion) {
        this.iversion = pIversion;
    }

    /**
     *
     * @return scode_fonction
     */
    public String getScode_fonction() {
        return this.scode_fonction;
    }

    /**
     *
     * @param pScode_fonction scode_fonction value
     */
    public void setScode_fonction(String pScode_fonction) {
        this.scode_fonction = pScode_fonction;
    }

    /**
     *
     * @return scode_langue
     */
    public String getScode_langue() {
        return this.scode_langue;
    }

    /**
     *
     * @param pScode_langue scode_langue value
     */
    public void setScode_langue(String pScode_langue) {
        this.scode_langue = pScode_langue;
    }

    /**
     *
     * @return scode_maritale
     */
    public String getScode_maritale() {
        return this.scode_maritale;
    }

    /**
     *
     * @param pScode_maritale scode_maritale value
     */
    public void setScode_maritale(String pScode_maritale) {
        this.scode_maritale = pScode_maritale;
    }

    /**
     *
     * @return scode_professionnel
     */
    public String getScode_professionnel() {
        return this.scode_professionnel;
    }

    /**
     *
     * @param pScode_professionnel scode_professionnel value
     */
    public void setScode_professionnel(String pScode_professionnel) {
        this.scode_professionnel = pScode_professionnel;
    }

    /**
     *
     * @return setudiant
     */
    public String getSetudiant() {
        return this.setudiant;
    }

    /**
     *
     * @param pSetudiant setudiant value
     */
    public void setSetudiant(String pSetudiant) {
        this.setudiant = pSetudiant;
    }

    /**
     *
     * @return sgin
     */
    public String getSgin() {
        return this.sgin;
    }

    /**
     *
     * @param pSgin sgin value
     */
    public void setSgin(String pSgin) {
        this.sgin = pSgin;
    }

    /**
     *
     * @return smailing_autorise
     */
    public String getSmailing_autorise() {
        return this.smailing_autorise;
    }

    /**
     *
     * @param pSmailing_autorise smailing_autorise value
     */
    public void setSmailing_autorise(String pSmailing_autorise) {
        this.smailing_autorise = pSmailing_autorise;
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
     * @return ssegment
     */
    public String getSsegment() {
        return this.ssegment;
    }

    /**
     *
     * @param pSsegment ssegment value
     */
    public void setSsegment(String pSsegment) {
        this.ssegment = pSsegment;
    }

    /**
     *
     * @return ssolvabilite
     */
    public String getSsolvabilite() {
        return this.ssolvabilite;
    }

    /**
     *
     * @param pSsolvabilite ssolvabilite value
     */
    public void setSsolvabilite(String pSsolvabilite) {
        this.ssolvabilite = pSsolvabilite;
    }

    /**
     *
     * @return object as string
     */
    public String toString() {
        String result = null;
        /*PROTECTED REGION ID(toString_S9GW1jUfEeCq6pHdxM8RnQ) ENABLED START*/
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
        buffer.append("iversion=").append(getIversion());
        buffer.append(",");
        buffer.append("smailing_autorise=").append(getSmailing_autorise());
        buffer.append(",");
        buffer.append("srin=").append(getSrin());
        buffer.append(",");
        buffer.append("ssolvabilite=").append(getSsolvabilite());
        buffer.append(",");
        buffer.append("scode_professionnel=").append(getScode_professionnel());
        buffer.append(",");
        buffer.append("scode_maritale=").append(getScode_maritale());
        buffer.append(",");
        buffer.append("scode_langue=").append(getScode_langue());
        buffer.append(",");
        buffer.append("sgin=").append(getSgin());
        buffer.append(",");
        buffer.append("scode_fonction=").append(getScode_fonction());
        buffer.append(",");
        buffer.append("inb_enfants=").append(getInb_enfants());
        buffer.append(",");
        buffer.append("ssegment=").append(getSsegment());
        buffer.append(",");
        buffer.append("setudiant=").append(getSetudiant());
        buffer.append("]");
        return buffer.toString();
    }

    /*PROTECTED REGION ID(_S9GW1jUfEeCq6pHdxM8RnQ u m) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/

}
