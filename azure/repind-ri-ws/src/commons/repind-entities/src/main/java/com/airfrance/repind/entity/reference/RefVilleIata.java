package com.airfrance.repind.entity.reference;

/*PROTECTED REGION ID(_mzJEEBeIEeKJFbgRY_ODIg i) ENABLED START*/

// add not generated imports here

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.ForeignKey;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/*PROTECTED REGION END*/


/**
 * <p>Title : RefVilleIata.java</p>
 * BO RefVilleIata
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
@Entity


@Table(name="REF_VILLE_IATA")
public class RefVilleIata implements Serializable {

/*PROTECTED REGION ID(serialUID _mzJEEBeIEeKJFbgRY_ODIg) ENABLED START*/
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
     * scodeVille
     */
    @Id
    @Column(name="SCODE_VILLE_AER")
    private String scodeVille;
        
            
    /**
     * longitude
     */
    @Column(name="LONGITUDE")
    private Integer longitude;
        
            
    /**
     * escContinent
     */
    @Column(name="ESC_CONTINENT")
    private String escContinent;
        
            
    /**
     * territoire
     */
    @Column(name="TERRITOIRE")
    private String territoire;
        
            
    /**
     * spntVente
     */
    @Column(name="SPNT_VENTE")
    private String spntVente;
        
            
    /**
     * libelleEn
     */
    @Column(name="SLIBELLE_EN")
    private String libelleEn;
        
            
    /**
     * libelle
     */
    @Column(name="SLIBELLE")
    private String libelle;
        
            
    /**
     * codeVilleDeprecated
     */
    @Column(name="SCODE_VILLE")
    private String codeVilleDeprecated;
        
            
    /**
     * codeProvEtat
     */
    @Column(name="SCODE_PROV_ETAT")
    private String codeProvEtat;
        
            
    /**
     * dateModif
     */
    @Column(name="DDATE_MAJ")
    private Date dateModif;
        
            
    /**
     * dateFin
     */
    @Column(name="DDATE_FIN")
    private Date dateFin;
        
            
    /**
     * dateDebut
     */
    @Column(name="DDATE_DEB")
    private Date dateDebut;
        
            
    /**
     * latitude
     */
    @Column(name="LATITUDE")
    private Integer latitude;
        
            
    /**
     * pays
     */
    // * -> 1
    @ManyToOne()
    @JoinColumn(name="SCODE_PAYS", nullable=false)
    @ForeignKey(name = "FK_REF_VILLE_IATA_SCODE_PAYS")
    private Pays pays;
        
    /*PROTECTED REGION ID(_mzJEEBeIEeKJFbgRY_ODIg u var) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/
    
    
    /** 
     * default constructor 
     */
    public RefVilleIata() {
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
     * @param pDateModif dateModif
     * @param pDateFin dateFin
     * @param pDateDebut dateDebut
     * @param pLatitude latitude
     */
    public RefVilleIata(String pScodeVille, Integer pLongitude, String pEscContinent, String pTerritoire, String pSpntVente, String pLibelleEn, String pLibelle, String pCodeVilleDeprecated, String pCodeProvEtat, Date pDateModif, Date pDateFin, Date pDateDebut, Integer pLatitude) {
        this.scodeVille = pScodeVille;
        this.longitude = pLongitude;
        this.escContinent = pEscContinent;
        this.territoire = pTerritoire;
        this.spntVente = pSpntVente;
        this.libelleEn = pLibelleEn;
        this.libelle = pLibelle;
        this.codeVilleDeprecated = pCodeVilleDeprecated;
        this.codeProvEtat = pCodeProvEtat;
        this.dateModif = pDateModif;
        this.dateFin = pDateFin;
        this.dateDebut = pDateDebut;
        this.latitude = pLatitude;
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
     * @return pays
     */
    public Pays getPays() {
        return this.pays;
    }

    /**
     *
     * @param pPays pays value
     */
    public void setPays(Pays pPays) {
        this.pays = pPays;
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
        /*PROTECTED REGION ID(toString_mzJEEBeIEeKJFbgRY_ODIg) ENABLED START*/
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
            .append("dateModif", getDateModif())
            .append("dateFin", getDateFin())
            .append("dateDebut", getDateDebut())
            .append("latitude", getLatitude())
            .toString();
    }

     
    
    /*PROTECTED REGION ID(equals hash _mzJEEBeIEeKJFbgRY_ODIg) ENABLED START*/

    /**
     * {@inheritDoc}
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        
        if(obj == null){
        	return false;
        }
       
        if (getClass() != obj.getClass()) {
            return false;
        }
        final RefVilleIata other = (RefVilleIata) obj;

        // if pks are both set, compare
        if (scodeVille != null) {
          if (other.scodeVille != null) {
            return new EqualsBuilder().append(scodeVille, other.scodeVille).isEquals();
          }
        }

        return new EqualsBuilder().
        append(pays, other.pays).
        append(libelle, other.libelle).
        append(dateDebut, other.dateDebut).
        append(dateFin, other.dateFin).
        isEquals();
    }

    /**
     * {@inheritDoc}
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        int result = new HashCodeBuilder(17, 31).
        append(pays).
        append(libelle).
        append(dateDebut).
        append(dateFin).
        toHashCode();

        return result;
    }
    
    /*PROTECTED REGION END*/

    /*PROTECTED REGION ID(_mzJEEBeIEeKJFbgRY_ODIg u m) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/
    
}
