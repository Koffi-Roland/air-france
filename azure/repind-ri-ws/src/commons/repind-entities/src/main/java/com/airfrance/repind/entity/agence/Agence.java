package com.airfrance.repind.entity.agence;

/*PROTECTED REGION ID(_QepBwGk1EeGhB9497mGnHw i) ENABLED START*/

// add not generated imports here

import com.airfrance.repind.entity.firme.PersonneMorale;
import org.hibernate.annotations.ForeignKey;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

/*PROTECTED REGION END*/


/**
 * <p>Title : Agence.java</p>
 * BO Agence
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
@Entity


@DiscriminatorValue("A")
@SecondaryTable(name="AGENCE")
public class Agence extends PersonneMorale implements Serializable {

/*PROTECTED REGION ID(serialUID _QepBwGk1EeGhB9497mGnHw) ENABLED START*/
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

            
    /*PROTECTED REGION ID(_QepBwmk1EeGhB9497mGnHw p) ENABLED START*/
    /**
     * ddateModifiSTAIATA
     */
    @Column(table="AGENCE", name="DDAT_MODIF_STA_IATA")
    private Date dateModifiSTAIATA;
    /*PROTECTED REGION END*/
        
            
    /*PROTECTED REGION ID(_QepBw2k1EeGhB9497mGnHw p) ENABLED START*/
    /**
     * ddateAgrement
     */
    @Column(table="AGENCE", name="DDATE_AGREMENT")
    private Date dateAgrement;
    /*PROTECTED REGION END*/
        
            
    /*PROTECTED REGION ID(_QepBxGk1EeGhB9497mGnHw p) ENABLED START*/
    /**
     * ddateDebut
     */
    @Column(table="AGENCE", name="DDATE_DEB")
    private Date dateDebut;
    /*PROTECTED REGION END*/
        
            
    /*PROTECTED REGION ID(_QepBxWk1EeGhB9497mGnHw p) ENABLED START*/
    /**
     * ddateFin
     */
    @Column(table="AGENCE", name="DDATE_FIN")
    private Date dateFin;
    /*PROTECTED REGION END*/
        
            
    /*PROTECTED REGION ID(_QepBxmk1EeGhB9497mGnHw p) ENABLED START*/
    /**
     * ddateRadiation
     */
    @Column(table="AGENCE", name="DDATE_RADIATION")
    private Date dateRadiation;
    /*PROTECTED REGION END*/
        
            
    /*PROTECTED REGION ID(_QepByGk1EeGhB9497mGnHw p) ENABLED START*/
    /**
     * sagenceRA2
     */
    @Column(table="AGENCE", name="SAGENCE_RA2")
    private String agenceRA2;
    /*PROTECTED REGION END*/
        
            
    /*PROTECTED REGION ID(_QepByWk1EeGhB9497mGnHw p) ENABLED START*/
    /**
     * sbsp
     */
    @Column(table="AGENCE", name="SBSP")
    private String bsp;
    /*PROTECTED REGION END*/
        
            
    /*PROTECTED REGION ID(_QepBymk1EeGhB9497mGnHw p) ENABLED START*/
    /**
     * scible
     */
    @Column(table="AGENCE", name="SCIBLE")
    private String cible;
    /*PROTECTED REGION END*/
        
            
    /*PROTECTED REGION ID(_QepBy2k1EeGhB9497mGnHw p) ENABLED START*/
    /**
     * scodeVilleIso
     */
    @Column(table="AGENCE", name="SCOD_VIL_ISO")
    private String codeVilleIso;
    /*PROTECTED REGION END*/
        
            
    /*PROTECTED REGION ID(_QepBzGk1EeGhB9497mGnHw p) ENABLED START*/
    /**
     * scodeService
     */
    @Column(table="AGENCE", name="SCODE_SERVICE")
    private String codeService;
    /*PROTECTED REGION END*/
        
            
    /*PROTECTED REGION ID(_QepBzWk1EeGhB9497mGnHw p) ENABLED START*/
    /**
     * sdomaine
     */
    @Column(table="AGENCE", name="SDOMAINE")
    private String domaine;
    /*PROTECTED REGION END*/
        
            
    /*PROTECTED REGION ID(_QepBzmk1EeGhB9497mGnHw p) ENABLED START*/
    /**
     * senvoieSI
     */
    @Column(table="AGENCE", name="SENVOI_SI")
    private String envoieSI;
    /*PROTECTED REGION END*/
        
            
    /*PROTECTED REGION ID(_QepBz2k1EeGhB9497mGnHw p) ENABLED START*/
    /**
     * sexclusifGrdCpt
     */
    @Column(table="AGENCE", name="SEXCLUSIF_GRD_CPT")
    private String exclusifGrdCpt;
    /*PROTECTED REGION END*/
        
            
    /*PROTECTED REGION ID(_QepB0Wk1EeGhB9497mGnHw p) ENABLED START*/
    /**
     * sgsa
     */
    @Column(table="AGENCE", name="SGSA")
    private String gsa;
    /*PROTECTED REGION END*/
        
            
    /*PROTECTED REGION ID(_QepB0mk1EeGhB9497mGnHw p) ENABLED START*/
    /**
     * sinfra
     */
    @Column(table="AGENCE", name="SINFRA")
    private String infra;
    /*PROTECTED REGION END*/
        
            
    /*PROTECTED REGION ID(_QepB02k1EeGhB9497mGnHw p) ENABLED START*/
    /**
     * slocalisation
     */
    @Column(table="AGENCE", name="SLOCALISATION")
    private String localisation;
    /*PROTECTED REGION END*/
        
            
    /*PROTECTED REGION ID(_QepB1Gk1EeGhB9497mGnHw p) ENABLED START*/
    /**
     * snumeroIATAMere
     */
    @Column(table="AGENCE", name="SNUMERO_IATA_MERE")
    private String numeroIATAMere;
    /*PROTECTED REGION END*/
        
            
    /*PROTECTED REGION ID(_QepB1Wk1EeGhB9497mGnHw p) ENABLED START*/
    /**
     * sobservation
     */
    @Column(table="AGENCE", name="SOBSERVATION")
    private String observation;
    /*PROTECTED REGION END*/
        
            
    /*PROTECTED REGION ID(_QepB1mk1EeGhB9497mGnHw p) ENABLED START*/
    /**
     * ssousDomaine
     */
    @Column(table="AGENCE", name="SSOUS_DOMAINE")
    private String sousDomaine;
    /*PROTECTED REGION END*/
        
            
    /*PROTECTED REGION ID(_QepB12k1EeGhB9497mGnHw p) ENABLED START*/
    /**
     * sstatutIATA
     */
    @Column(table="AGENCE", name="SSTATUT_IATA")
    private String statutIATA;
    /*PROTECTED REGION END*/
        
            
    /*PROTECTED REGION ID(_QepB2Gk1EeGhB9497mGnHw p) ENABLED START*/
    /**
     * stype
     */
    @Column(table="AGENCE", name="STYPE")
    private String type;
    /*PROTECTED REGION END*/
        
            
    /*PROTECTED REGION ID(_QepB2Wk1EeGhB9497mGnHw p) ENABLED START*/
    /**
     * stypeAgrement
     */
    @Column(table="AGENCE", name="STYPE_AGREMENT")
    private String typeAgrement;
    /*PROTECTED REGION END*/
        
            
    /*PROTECTED REGION ID(_QepB2mk1EeGhB9497mGnHw p) ENABLED START*/
    /**
     * stypeVente
     */
    @Column(table="AGENCE", name="STYPE_VENTE")
    private String typeVente;
    /*PROTECTED REGION END*/
        
            
    /*PROTECTED REGION ID(_QepB22k1EeGhB9497mGnHw p) ENABLED START*/
    /**
     * szoneChalandise
     */
    @Column(table="AGENCE", name="SZONE_CHALANDISE")
    private String zoneChalandise;
    /*PROTECTED REGION END*/

    /*PROTECTED REGION ID(_QepB22k1EeGhB9497mGnHw k) ENABLED START*/
    /**
     * iataStationAirportCode
     */
    @Column(table="AGENCE", name="SIATA_STATION_AIRPORT_CODE")
    private String iataStationAirportCode;
    /*PROTECTED REGION END*/
    
    /*PROTECTED REGION ID(_QepB22k1EeGhB9497mGnHw g) ENABLED START*/
    /**
     * SFORCING_AIRPORT_CODE_UPDATE
     */
    @Column(table="AGENCE", name="SFORCING_AIRPORT_CODE_UPDATE")
    private Integer forcingUpdate;
    /*PROTECTED REGION END*/
    
            
    /**
     * lettreCompte
     */
    // 1 -> *
    @OneToMany()
    @JoinColumn(name="SGIN")
    @ForeignKey(name = "FK_LETTRE_COMPT_AGENCE")
    private List<LettreCompte> lettreCompte;
        
            
    /**
     * offices
     */
    // 1 <-> * 
    @OneToMany(mappedBy="agence")
    private Set<OfficeID> offices;
        
            
    /**
     * reseaux
     */
    // 1 <-> * 
    @OneToMany(mappedBy="agence")
    private Set<MembreReseau> reseaux;
        
    /*PROTECTED REGION ID(_QepBwGk1EeGhB9497mGnHw u var) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/
    
    /** 
     * default constructor 
     */
    public Agence() {
    }
        
    /** 
     * full constructor
     * @param pDateModifiSTAIATA dateModifiSTAIATA
     * @param pDateAgrement dateAgrement
     * @param pDateDebut dateDebut
     * @param pDateFin dateFin
     * @param pDateRadiation dateRadiation
     * @param pVersion version
     * @param pAgenceRA2 agenceRA2
     * @param pBsp bsp
     * @param pCible cible
     * @param pCodeVilleIso codeVilleIso
     * @param pCodeService codeService
     * @param pDomaine domaine
     * @param pEnvoieSI envoieSI
     * @param pExclusifGrdCpt exclusifGrdCpt
     * @param pGsa gsa
     * @param pInfra infra
     * @param pLocalisation localisation
     * @param pNumeroIATAMere numeroIATAMere
     * @param pObservation observation
     * @param pSousDomaine sousDomaine
     * @param pStatutIATA statutIATA
     * @param pType type
     * @param pTypeAgrement typeAgrement
     * @param pTypeVente typeVente
     * @param pZoneChalandise zoneChalandise
     */
    public Agence(Date pDateModifiSTAIATA, Date pDateAgrement, Date pDateDebut, Date pDateFin, Date pDateRadiation, Integer pVersion, String pAgenceRA2, String pBsp, String pCible, String pCodeVilleIso, String pCodeService, String pDomaine, String pEnvoieSI, String pExclusifGrdCpt, String pGsa, String pInfra, String pLocalisation, String pNumeroIATAMere, String pObservation, String pSousDomaine, String pStatutIATA, String pType, String pTypeAgrement, String pTypeVente, String pZoneChalandise) {
        this.dateModifiSTAIATA = pDateModifiSTAIATA;
        this.dateAgrement = pDateAgrement;
        this.dateDebut = pDateDebut;
        this.dateFin = pDateFin;
        this.dateRadiation = pDateRadiation;
        //this.version = pVersion;
        this.agenceRA2 = pAgenceRA2;
        this.bsp = pBsp;
        this.cible = pCible;
        this.codeVilleIso = pCodeVilleIso;
        this.codeService = pCodeService;
        this.domaine = pDomaine;
        this.envoieSI = pEnvoieSI;
        this.exclusifGrdCpt = pExclusifGrdCpt;
        this.gsa = pGsa;
        this.infra = pInfra;
        this.localisation = pLocalisation;
        this.numeroIATAMere = pNumeroIATAMere;
        this.observation = pObservation;
        this.sousDomaine = pSousDomaine;
        this.statutIATA = pStatutIATA;
        this.type = pType;
        this.typeAgrement = pTypeAgrement;
        this.typeVente = pTypeVente;
        this.zoneChalandise = pZoneChalandise;
    }

    /**
     *
     * @return agenceRA2
     */
    public String getAgenceRA2() {
        return this.agenceRA2;
    }

    /**
     *
     * @param pAgenceRA2 agenceRA2 value
     */
    public void setAgenceRA2(String pAgenceRA2) {
        this.agenceRA2 = pAgenceRA2;
    }

    /**
     *
     * @return bsp
     */
    public String getBsp() {
        return this.bsp;
    }

    /**
     *
     * @param pBsp bsp value
     */
    public void setBsp(String pBsp) {
        this.bsp = pBsp;
    }

    /**
     *
     * @return cible
     */
    public String getCible() {
        return this.cible;
    }

    /**
     *
     * @param pCible cible value
     */
    public void setCible(String pCible) {
        this.cible = pCible;
    }

    /**
     *
     * @return codeService
     */
    public String getCodeService() {
        return this.codeService;
    }

    /**
     *
     * @param pCodeService codeService value
     */
    public void setCodeService(String pCodeService) {
        this.codeService = pCodeService;
    }

    /**
     *
     * @return codeVilleIso
     */
    public String getCodeVilleIso() {
        return this.codeVilleIso;
    }

    /**
     *
     * @param pCodeVilleIso codeVilleIso value
     */
    public void setCodeVilleIso(String pCodeVilleIso) {
        this.codeVilleIso = pCodeVilleIso;
    }

    /**
     *
     * @return dateAgrement
     */
    public Date getDateAgrement() {
        return this.dateAgrement;
    }

    /**
     *
     * @param pDateAgrement dateAgrement value
     */
    public void setDateAgrement(Date pDateAgrement) {
        this.dateAgrement = pDateAgrement;
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
     * @return dateModifiSTAIATA
     */
    public Date getDateModifiSTAIATA() {
        return this.dateModifiSTAIATA;
    }

    /**
     *
     * @param pDateModifiSTAIATA dateModifiSTAIATA value
     */
    public void setDateModifiSTAIATA(Date pDateModifiSTAIATA) {
        this.dateModifiSTAIATA = pDateModifiSTAIATA;
    }

    /**
     *
     * @return dateRadiation
     */
    public Date getDateRadiation() {
        return this.dateRadiation;
    }

    /**
     *
     * @param pDateRadiation dateRadiation value
     */
    public void setDateRadiation(Date pDateRadiation) {
        this.dateRadiation = pDateRadiation;
    }

    /**
     *
     * @return domaine
     */
    public String getDomaine() {
        return this.domaine;
    }

    /**
     *
     * @param pDomaine domaine value
     */
    public void setDomaine(String pDomaine) {
        this.domaine = pDomaine;
    }

    /**
     *
     * @return envoieSI
     */
    public String getEnvoieSI() {
        return this.envoieSI;
    }

    /**
     *
     * @param pEnvoieSI envoieSI value
     */
    public void setEnvoieSI(String pEnvoieSI) {
        this.envoieSI = pEnvoieSI;
    }

    /**
     *
     * @return exclusifGrdCpt
     */
    public String getExclusifGrdCpt() {
        return this.exclusifGrdCpt;
    }

    /**
     *
     * @param pExclusifGrdCpt exclusifGrdCpt value
     */
    public void setExclusifGrdCpt(String pExclusifGrdCpt) {
        this.exclusifGrdCpt = pExclusifGrdCpt;
    }

    /**
     *
     * @return gsa
     */
    public String getGsa() {
        return this.gsa;
    }

    /**
     *
     * @param pGsa gsa value
     */
    public void setGsa(String pGsa) {
        this.gsa = pGsa;
    }

    /**
     *
     * @return infra
     */
    public String getInfra() {
        return this.infra;
    }

    /**
     *
     * @param pInfra infra value
     */
    public void setInfra(String pInfra) {
        this.infra = pInfra;
    }

    /**
     *
     * @return lettreCompte
     */
    public List<LettreCompte> getLettreCompte() {
        return this.lettreCompte;
    }

    /**
     *
     * @param pLettreCompte lettreCompte value
     */
    public void setLettreCompte(List<LettreCompte> pLettreCompte) {
        this.lettreCompte = pLettreCompte;
    }

    /**
     *
     * @return localisation
     */
    public String getLocalisation() {
        return this.localisation;
    }

    /**
     *
     * @param pLocalisation localisation value
     */
    public void setLocalisation(String pLocalisation) {
        this.localisation = pLocalisation;
    }

    /**
     *
     * @return numeroIATAMere
     */
    public String getNumeroIATAMere() {
        return this.numeroIATAMere;
    }

    /**
     *
     * @param pNumeroIATAMere numeroIATAMere value
     */
    public void setNumeroIATAMere(String pNumeroIATAMere) {
        this.numeroIATAMere = pNumeroIATAMere;
    }

    /**
     *
     * @return observation
     */
    public String getObservation() {
        return this.observation;
    }

    /**
     *
     * @param pObservation observation value
     */
    public void setObservation(String pObservation) {
        this.observation = pObservation;
    }

    /**
     *
     * @return offices
     */
    public Set<OfficeID> getOffices() {
        return this.offices;
    }

    /**
     *
     * @param pOffices offices value
     */
    public void setOffices(Set<OfficeID> pOffices) {
        this.offices = pOffices;
    }

    /**
     *
     * @return reseaux
     */
    public Set<MembreReseau> getReseaux() {
        return this.reseaux;
    }

    /**
     *
     * @param pReseaux reseaux value
     */
    public void setReseaux(Set<MembreReseau> pReseaux) {
        this.reseaux = pReseaux;
    }

    /**
     *
     * @return sousDomaine
     */
    public String getSousDomaine() {
        return this.sousDomaine;
    }

    /**
     *
     * @param pSousDomaine sousDomaine value
     */
    public void setSousDomaine(String pSousDomaine) {
        this.sousDomaine = pSousDomaine;
    }

    /**
     *
     * @return statutIATA
     */
    public String getStatutIATA() {
        return this.statutIATA;
    }

    /**
     *
     * @param pStatutIATA statutIATA value
     */
    public void setStatutIATA(String pStatutIATA) {
        this.statutIATA = pStatutIATA;
    }

    /**
     *
     * @return type
     */
    public String getType() {
        return this.type;
    }

    /**
     *
     * @param pType type value
     */
    public void setType(String pType) {
        this.type = pType;
    }

    /**
     *
     * @return typeAgrement
     */
    public String getTypeAgrement() {
        return this.typeAgrement;
    }

    /**
     *
     * @param pTypeAgrement typeAgrement value
     */
    public void setTypeAgrement(String pTypeAgrement) {
        this.typeAgrement = pTypeAgrement;
    }

    /**
     *
     * @return typeVente
     */
    public String getTypeVente() {
        return this.typeVente;
    }

    /**
     *
     * @param pTypeVente typeVente value
     */
    public void setTypeVente(String pTypeVente) {
        this.typeVente = pTypeVente;
    }

    /**
     *
     * @return zoneChalandise
     */
    public String getZoneChalandise() {
        return this.zoneChalandise;
    }

    /**
     *
     * @param pZoneChalandise zoneChalandise value
     */
    public void setZoneChalandise(String pZoneChalandise) {
        this.zoneChalandise = pZoneChalandise;
    }
    
    /**
	 * @return iataStationAirportCode
	 */
	public String getIataStationAirportCode() {
		return iataStationAirportCode;
	}
	

	/**
	 * @param iataStationAirportCode iataStationAirportCode à définir
	 */
	public void setIataStationAirportCode(String iataStationAirportCode) {
		this.iataStationAirportCode = iataStationAirportCode;
	}

	
	/**
	 * @return forcingUpdate
	 */
	public Integer getForcingUpdate() {
		return forcingUpdate;
	}

	
	/**
	 * @param forcingUpdate forcingUpdate à définir
	 */
	public void setForcingUpdate(Integer forcingUpdate) {
		this.forcingUpdate = forcingUpdate;
	}

	
	
	/**
     *
     * @return object as string
     */
    public String toString() {
        String result = null;
        /*PROTECTED REGION ID(toString_QepBwGk1EeGhB9497mGnHw) ENABLED START*/
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
        buffer.append("dateModifiSTAIATA=").append(getDateModifiSTAIATA());
        buffer.append(",");
        buffer.append("dateAgrement=").append(getDateAgrement());
        buffer.append(",");
        buffer.append("dateDebut=").append(getDateDebut());
        buffer.append(",");
        buffer.append("dateFin=").append(getDateFin());
        buffer.append(",");
        buffer.append("dateRadiation=").append(getDateRadiation());
        buffer.append(",");
        buffer.append("version=").append(getVersion());
        buffer.append(",");
        buffer.append("agenceRA2=").append(getAgenceRA2());
        buffer.append(",");
        buffer.append("bsp=").append(getBsp());
        buffer.append(",");
        buffer.append("cible=").append(getCible());
        buffer.append(",");
        buffer.append("codeVilleIso=").append(getCodeVilleIso());
        buffer.append(",");
        buffer.append("codeService=").append(getCodeService());
        buffer.append(",");
        buffer.append("domaine=").append(getDomaine());
        buffer.append(",");
        buffer.append("envoieSI=").append(getEnvoieSI());
        buffer.append(",");
        buffer.append("exclusifGrdCpt=").append(getExclusifGrdCpt());
        buffer.append(",");
        buffer.append("gsa=").append(getGsa());
        buffer.append(",");
        buffer.append("infra=").append(getInfra());
        buffer.append(",");
        buffer.append("localisation=").append(getLocalisation());
        buffer.append(",");
        buffer.append("numeroIATAMere=").append(getNumeroIATAMere());
        buffer.append(",");
        buffer.append("observation=").append(getObservation());
        buffer.append(",");
        buffer.append("sousDomaine=").append(getSousDomaine());
        buffer.append(",");
        buffer.append("statutIATA=").append(getStatutIATA());
        buffer.append(",");
        buffer.append("type=").append(getType());
        buffer.append(",");
        buffer.append("typeAgrement=").append(getTypeAgrement());
        buffer.append(",");
        buffer.append("typeVente=").append(getTypeVente());
        buffer.append(",");
        buffer.append("zoneChalandise=").append(getZoneChalandise());
        buffer.append(",");
        buffer.append("iataStationAirportCode=").append(getIataStationAirportCode());
        buffer.append(",");
        buffer.append("forcingUpdate=").append(getForcingUpdate());
        buffer.append("]");
        return buffer.toString();
    }

     
    
    /*PROTECTED REGION ID(equals hash _QepBwGk1EeGhB9497mGnHw) ENABLED START*/

    /**
     * {@inheritDoc}
     * @see Object#equals(Object)
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
        final Agence other = (Agence) obj;

        // TODO: writes or generates equals method

        return super.equals(other);
    }

    /**
     * {@inheritDoc}
     * @see Object#hashCode()
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

    /*PROTECTED REGION ID(_QepBwGk1EeGhB9497mGnHw u m) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/
    
}
