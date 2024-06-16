package com.afklm.rigui.dto.profil;

/*PROTECTED REGION ID(_7iwvMEr6EeSzt4lojjJV5Q i) ENABLED START*/

// add not generated imports here

import java.io.Serializable;
import java.util.Date;

/*PROTECTED REGION END*/

/**
 * <p>Title : ProfilQualitatifDTO</p>
 * 
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
public class ProfilQualitatifDTO  implements Serializable {
        
    /**
	 * 
	 */
	private static final long serialVersionUID = -6391733920797000759L;


	/**
     * cle
     */
    private Integer cle;
        
        
    /**
     * debutPeriodeResultat
     */
    private Date debutPeriodeResultat;
        
        
    /**
     * finPeriodeResultat
     */
    private Date finPeriodeResultat;
        
        
    /**
     * inbBilManuscrits
     */
    private Integer inbBilManuscrits;
        
        
    /**
     * inbGoShowNavette
     */
    private Integer inbGoShowNavette;
        
        
    /**
     * inbGoShowNonNav
     */
    private Integer inbGoShowNonNav;
        
        
    /**
     * inbNoShowNavette
     */
    private Integer inbNoShowNavette;
        
        
    /**
     * inbNoShowNonNav
     */
    private Integer inbNoShowNonNav;
        
        
    /**
     * inbReserveNavette
     */
    private Integer inbReserveNavette;
        
        
    /**
     * inbReserveNonNav
     */
    private Integer inbReserveNonNav;
        
        
    /**
     * inbTotBilEmis
     */
    private Integer inbTotBilEmis;
        
        
    /**
     * profil
     */
    private Profil_mereDTO profil;
        

    /*PROTECTED REGION ID(_7iwvMEr6EeSzt4lojjJV5Q u var) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/

    
    
    /** 
     * default constructor 
     */
    public ProfilQualitatifDTO() {
    
    }
    
    
    /** 
     * full constructor
     * @param pCle cle
     * @param pDebutPeriodeResultat debutPeriodeResultat
     * @param pFinPeriodeResultat finPeriodeResultat
     * @param pInbBilManuscrits inbBilManuscrits
     * @param pInbGoShowNavette inbGoShowNavette
     * @param pInbGoShowNonNav inbGoShowNonNav
     * @param pInbNoShowNavette inbNoShowNavette
     * @param pInbNoShowNonNav inbNoShowNonNav
     * @param pInbReserveNavette inbReserveNavette
     * @param pInbReserveNonNav inbReserveNonNav
     * @param pInbTotBilEmis inbTotBilEmis
     */
    public ProfilQualitatifDTO(Integer pCle, Date pDebutPeriodeResultat, Date pFinPeriodeResultat, Integer pInbBilManuscrits, Integer pInbGoShowNavette, Integer pInbGoShowNonNav, Integer pInbNoShowNavette, Integer pInbNoShowNonNav, Integer pInbReserveNavette, Integer pInbReserveNonNav, Integer pInbTotBilEmis) {
        this.cle = pCle;
        this.debutPeriodeResultat = pDebutPeriodeResultat;
        this.finPeriodeResultat = pFinPeriodeResultat;
        this.inbBilManuscrits = pInbBilManuscrits;
        this.inbGoShowNavette = pInbGoShowNavette;
        this.inbGoShowNonNav = pInbGoShowNonNav;
        this.inbNoShowNavette = pInbNoShowNavette;
        this.inbNoShowNonNav = pInbNoShowNonNav;
        this.inbReserveNavette = pInbReserveNavette;
        this.inbReserveNonNav = pInbReserveNonNav;
        this.inbTotBilEmis = pInbTotBilEmis;
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
     * @return debutPeriodeResultat
     */
    public Date getDebutPeriodeResultat() {
        return this.debutPeriodeResultat;
    }

    /**
     *
     * @param pDebutPeriodeResultat debutPeriodeResultat value
     */
    public void setDebutPeriodeResultat(Date pDebutPeriodeResultat) {
        this.debutPeriodeResultat = pDebutPeriodeResultat;
    }

    /**
     *
     * @return finPeriodeResultat
     */
    public Date getFinPeriodeResultat() {
        return this.finPeriodeResultat;
    }

    /**
     *
     * @param pFinPeriodeResultat finPeriodeResultat value
     */
    public void setFinPeriodeResultat(Date pFinPeriodeResultat) {
        this.finPeriodeResultat = pFinPeriodeResultat;
    }

    /**
     *
     * @return inbBilManuscrits
     */
    public Integer getInbBilManuscrits() {
        return this.inbBilManuscrits;
    }

    /**
     *
     * @param pInbBilManuscrits inbBilManuscrits value
     */
    public void setInbBilManuscrits(Integer pInbBilManuscrits) {
        this.inbBilManuscrits = pInbBilManuscrits;
    }

    /**
     *
     * @return inbGoShowNavette
     */
    public Integer getInbGoShowNavette() {
        return this.inbGoShowNavette;
    }

    /**
     *
     * @param pInbGoShowNavette inbGoShowNavette value
     */
    public void setInbGoShowNavette(Integer pInbGoShowNavette) {
        this.inbGoShowNavette = pInbGoShowNavette;
    }

    /**
     *
     * @return inbGoShowNonNav
     */
    public Integer getInbGoShowNonNav() {
        return this.inbGoShowNonNav;
    }

    /**
     *
     * @param pInbGoShowNonNav inbGoShowNonNav value
     */
    public void setInbGoShowNonNav(Integer pInbGoShowNonNav) {
        this.inbGoShowNonNav = pInbGoShowNonNav;
    }

    /**
     *
     * @return inbNoShowNavette
     */
    public Integer getInbNoShowNavette() {
        return this.inbNoShowNavette;
    }

    /**
     *
     * @param pInbNoShowNavette inbNoShowNavette value
     */
    public void setInbNoShowNavette(Integer pInbNoShowNavette) {
        this.inbNoShowNavette = pInbNoShowNavette;
    }

    /**
     *
     * @return inbNoShowNonNav
     */
    public Integer getInbNoShowNonNav() {
        return this.inbNoShowNonNav;
    }

    /**
     *
     * @param pInbNoShowNonNav inbNoShowNonNav value
     */
    public void setInbNoShowNonNav(Integer pInbNoShowNonNav) {
        this.inbNoShowNonNav = pInbNoShowNonNav;
    }

    /**
     *
     * @return inbReserveNavette
     */
    public Integer getInbReserveNavette() {
        return this.inbReserveNavette;
    }

    /**
     *
     * @param pInbReserveNavette inbReserveNavette value
     */
    public void setInbReserveNavette(Integer pInbReserveNavette) {
        this.inbReserveNavette = pInbReserveNavette;
    }

    /**
     *
     * @return inbReserveNonNav
     */
    public Integer getInbReserveNonNav() {
        return this.inbReserveNonNav;
    }

    /**
     *
     * @param pInbReserveNonNav inbReserveNonNav value
     */
    public void setInbReserveNonNav(Integer pInbReserveNonNav) {
        this.inbReserveNonNav = pInbReserveNonNav;
    }

    /**
     *
     * @return inbTotBilEmis
     */
    public Integer getInbTotBilEmis() {
        return this.inbTotBilEmis;
    }

    /**
     *
     * @param pInbTotBilEmis inbTotBilEmis value
     */
    public void setInbTotBilEmis(Integer pInbTotBilEmis) {
        this.inbTotBilEmis = pInbTotBilEmis;
    }

    /**
     *
     * @return profil
     */
    public Profil_mereDTO getProfil() {
        return this.profil;
    }

    /**
     *
     * @param pProfil profil value
     */
    public void setProfil(Profil_mereDTO pProfil) {
        this.profil = pProfil;
    }

    /**
     *
     * @return object as string
     */
    public String toString() {
        String result = null;
        /*PROTECTED REGION ID(toString_7iwvMEr6EeSzt4lojjJV5Q) ENABLED START*/
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
        buffer.append("debutPeriodeResultat=").append(getDebutPeriodeResultat());
        buffer.append(",");
        buffer.append("finPeriodeResultat=").append(getFinPeriodeResultat());
        buffer.append(",");
        buffer.append("inbBilManuscrits=").append(getInbBilManuscrits());
        buffer.append(",");
        buffer.append("inbGoShowNavette=").append(getInbGoShowNavette());
        buffer.append(",");
        buffer.append("inbGoShowNonNav=").append(getInbGoShowNonNav());
        buffer.append(",");
        buffer.append("inbNoShowNavette=").append(getInbNoShowNavette());
        buffer.append(",");
        buffer.append("inbNoShowNonNav=").append(getInbNoShowNonNav());
        buffer.append(",");
        buffer.append("inbReserveNavette=").append(getInbReserveNavette());
        buffer.append(",");
        buffer.append("inbReserveNonNav=").append(getInbReserveNonNav());
        buffer.append(",");
        buffer.append("inbTotBilEmis=").append(getInbTotBilEmis());
        buffer.append("]");
        return buffer.toString();
    }

    /*PROTECTED REGION ID(_7iwvMEr6EeSzt4lojjJV5Q u m) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/

}
