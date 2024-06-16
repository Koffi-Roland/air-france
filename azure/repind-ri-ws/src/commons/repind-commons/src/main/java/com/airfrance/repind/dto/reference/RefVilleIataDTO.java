package com.airfrance.repind.dto.reference;

/*PROTECTED REGION ID(_XVH24BeIEeKJFbgRY_ODIg i) ENABLED START*/

// add not generated imports here
import org.apache.commons.lang.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.Date;

/*PROTECTED REGION END*/

/**
 * <p>Title : RefVilleIataDTO</p>
 * 
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
public class RefVilleIataDTO implements Serializable {
        
    /**
	 * Default serial version Id
	 */
	private static final long serialVersionUID = 1L;


	/**
     * scodeVille
     */
    private String scodeVille;
        
        
    /**
     * longitude
     */
    private Integer longitude;
        
        
    /**
     * escContinent
     */
    private String escContinent;
        
        
    /**
     * territoire
     */
    private String territoire;
        
        
    /**
     * spntVente
     */
    private String spntVente;
        
        
    /**
     * libelleEn
     */
    private String libelleEn;
        
        
    /**
     * libelle
     */
    private String libelle;
        
        
    /**
     * codeVilleDeprecated
     */
    private String codeVilleDeprecated;
        
        
    /**
     * codeProvEtat
     */
    private String codeProvEtat;
        
        
    /**
     * codePays
     */
    private String codePays;
        
        
    /**
     * dateModif
     */
    private Date dateModif;
        
        
    /**
     * dateFin
     */
    private Date dateFin;
        
        
    /**
     * dateDebut
     */
    private Date dateDebut;
        
        
    /**
     * latitude
     */
    private Integer latitude;
        

    /*PROTECTED REGION ID(_XVH24BeIEeKJFbgRY_ODIg u var) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/

    
    
    /** 
     * default constructor 
     */
    public RefVilleIataDTO() {
    
    }
    
    
    /** 
     * full constructor
     * @param pScodeVille scodeVille
     * @param pLongitude longitude
     * @param pEscContinent escContinent
     * @param pTerritoire territoire
     * @param pSpntVente spntVente
     * @param pLibelleEn libelleEn
     * @param pLibelle libelle
     * @param pCodeVilleDeprecated codeVilleDeprecated
     * @param pCodeProvEtat codeProvEtat
     * @param pCodePays codePays
     * @param pDateModif dateModif
     * @param pDateFin dateFin
     * @param pDateDebut dateDebut
     * @param pLatitude latitude
     */
    public RefVilleIataDTO(String pScodeVille, Integer pLongitude, String pEscContinent, String pTerritoire, String pSpntVente, String pLibelleEn, String pLibelle, String pCodeVilleDeprecated, String pCodeProvEtat, String pCodePays, Date pDateModif, Date pDateFin, Date pDateDebut, Integer pLatitude) {
        this.scodeVille = pScodeVille;
        this.longitude = pLongitude;
        this.escContinent = pEscContinent;
        this.territoire = pTerritoire;
        this.spntVente = pSpntVente;
        this.libelleEn = pLibelleEn;
        this.libelle = pLibelle;
        this.codeVilleDeprecated = pCodeVilleDeprecated;
        this.codeProvEtat = pCodeProvEtat;
        this.codePays = pCodePays;
        this.dateModif = pDateModif;
        this.dateFin = pDateFin;
        this.dateDebut = pDateDebut;
        this.latitude = pLatitude;
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
     * @return codeProvEtat
     */
    public String getCodeProvEtat() {
        return this.codeProvEtat;
    }

    /**
     *
     * @param pCodeProvEtat codeProvEtat value
     */
    public void setCodeProvEtat(String pCodeProvEtat) {
        this.codeProvEtat = pCodeProvEtat;
    }

    /**
     *
     * @return codeVilleDeprecated
     */
    public String getCodeVilleDeprecated() {
        return this.codeVilleDeprecated;
    }

    /**
     *
     * @param pCodeVilleDeprecated codeVilleDeprecated value
     */
    public void setCodeVilleDeprecated(String pCodeVilleDeprecated) {
        this.codeVilleDeprecated = pCodeVilleDeprecated;
    }

    /**
     *
     * @return dateDebut
     */
    public Date getDateDebut() {
        return this.dateDebut;
    }

    /**
     *
     * @param pDateDebut dateDebut value
     */
    public void setDateDebut(Date pDateDebut) {
        this.dateDebut = pDateDebut;
    }

    /**
     *
     * @return dateFin
     */
    public Date getDateFin() {
        return this.dateFin;
    }

    /**
     *
     * @param pDateFin dateFin value
     */
    public void setDateFin(Date pDateFin) {
        this.dateFin = pDateFin;
    }

    /**
     *
     * @return dateModif
     */
    public Date getDateModif() {
        return this.dateModif;
    }

    /**
     *
     * @param pDateModif dateModif value
     */
    public void setDateModif(Date pDateModif) {
        this.dateModif = pDateModif;
    }

    /**
     *
     * @return escContinent
     */
    public String getEscContinent() {
        return this.escContinent;
    }

    /**
     *
     * @param pEscContinent escContinent value
     */
    public void setEscContinent(String pEscContinent) {
        this.escContinent = pEscContinent;
    }

    /**
     *
     * @return latitude
     */
    public Integer getLatitude() {
        return this.latitude;
    }

    /**
     *
     * @param pLatitude latitude value
     */
    public void setLatitude(Integer pLatitude) {
        this.latitude = pLatitude;
    }

    /**
     *
     * @return libelle
     */
    public String getLibelle() {
        return this.libelle;
    }

    /**
     *
     * @param pLibelle libelle value
     */
    public void setLibelle(String pLibelle) {
        this.libelle = pLibelle;
    }

    /**
     *
     * @return libelleEn
     */
    public String getLibelleEn() {
        return this.libelleEn;
    }

    /**
     *
     * @param pLibelleEn libelleEn value
     */
    public void setLibelleEn(String pLibelleEn) {
        this.libelleEn = pLibelleEn;
    }

    /**
     *
     * @return longitude
     */
    public Integer getLongitude() {
        return this.longitude;
    }

    /**
     *
     * @param pLongitude longitude value
     */
    public void setLongitude(Integer pLongitude) {
        this.longitude = pLongitude;
    }

    /**
     *
     * @return scodeVille
     */
    public String getScodeVille() {
        return this.scodeVille;
    }

    /**
     *
     * @param pScodeVille scodeVille value
     */
    public void setScodeVille(String pScodeVille) {
        this.scodeVille = pScodeVille;
    }

    /**
     *
     * @return spntVente
     */
    public String getSpntVente() {
        return this.spntVente;
    }

    /**
     *
     * @param pSpntVente spntVente value
     */
    public void setSpntVente(String pSpntVente) {
        this.spntVente = pSpntVente;
    }

    /**
     *
     * @return territoire
     */
    public String getTerritoire() {
        return this.territoire;
    }

    /**
     *
     * @param pTerritoire territoire value
     */
    public void setTerritoire(String pTerritoire) {
        this.territoire = pTerritoire;
    }

    /**
     *
     * @return object as string
     */
    public String toString() {
        String result = null;
        /*PROTECTED REGION ID(toString_XVH24BeIEeKJFbgRY_ODIg) ENABLED START*/
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
            .append("scodeVille", getScodeVille())
            .append("longitude", getLongitude())
            .append("escContinent", getEscContinent())
            .append("territoire", getTerritoire())
            .append("spntVente", getSpntVente())
            .append("libelleEn", getLibelleEn())
            .append("libelle", getLibelle())
            .append("codeVilleDeprecated", getCodeVilleDeprecated())
            .append("codeProvEtat", getCodeProvEtat())
            .append("codePays", getCodePays())
            .append("dateModif", getDateModif())
            .append("dateFin", getDateFin())
            .append("dateDebut", getDateDebut())
            .append("latitude", getLatitude())
            .toString();
    }

    /*PROTECTED REGION ID(_XVH24BeIEeKJFbgRY_ODIg u m) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/

}
