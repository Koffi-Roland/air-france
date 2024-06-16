package com.airfrance.repind.dto.agence;

/*PROTECTED REGION ID(_0VRt4Gk1EeGhB9497mGnHw i) ENABLED START*/

// add not generated imports here

import com.airfrance.repind.dto.firme.PersonneMoraleDTO;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import java.io.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

/*PROTECTED REGION END*/

/**
 * <p>Title : AgenceDTO</p>
 * 
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
public class AgenceDTO extends PersonneMoraleDTO  implements Serializable {
        
    /**
	 * 
	 */
	private static final long serialVersionUID = 7586699334170928653L;


	/**
     * dateModifiSTAIATA
     */
    private Date dateModifiSTAIATA;
        
        
    /**
     * dateAgrement
     */
    private Date dateAgrement;
        
        
    /**
     * dateDebut
     */
    private Date dateDebut;
        
        
    /**
     * dateFin
     */
    private Date dateFin;
        
        
    /**
     * dateRadiation
     */
    private Date dateRadiation;
        
        
    /**
     * agenceRA2
     */
    private String agenceRA2;
        
        
    /**
     * bsp
     */
    private String bsp;
        
        
    /**
     * cible
     */
    private String cible;
        
        
    /**
     * codeVilleIso
     */
    private String codeVilleIso;
        
        
    /**
     * codeService
     */
    private String codeService;
        
        
    /**
     * domaine
     */
    private String domaine;
        
        
    /**
     * envoieSI
     */
    private String envoieSI;
        
        
    /**
     * exclusifGrdCpt
     */
    private String exclusifGrdCpt;
        
        
    /**
     * ginAgenceMere
     */
    private String ginAgenceMere;
        
        
    /**
     * gsa
     */
    private String gsa;
        
        
    /**
     * infra
     */
    private String infra;
        
        
    /**
     * localisation
     */
    private String localisation;
        
        
    /**
     * numeroIATAMere
     */
    private String numeroIATAMere;
        
        
    /**
     * observation
     */
    private String observation;
        
        
    /**
     * sousDomaine
     */
    private String sousDomaine;
        
        
    /**
     * statutIATA
     */
    private String statutIATA;
        
        
    /**
     * type
     */
    private String type;
        
        
    /**
     * typeAgrement
     */
    private String typeAgrement;
        
        
    /**
     * typeVente
     */
    private String typeVente;
        
        
    /**
     * zoneChalandise
     */
    private String zoneChalandise;
        
        
    /**
     * lettreCompte
     */
    private List<LettreCompteDTO> lettreCompte;
        
        
    /**
     * offices
     */
    private Set<OfficeIDDTO> offices;
        
        
    /**
     * reseaux
     */
    private Set<MembreReseauDTO> reseaux;
        
    /**
     * code IATA aéroport
     */
    private String iataStationAirportCode;

    /**
     * forcage mise à jour code aéroport
     */
    private Integer forcingUpdate;
    
    /*PROTECTED REGION ID(_0VRt4Gk1EeGhB9497mGnHw u var) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/

    
    
    /** 
     * default constructor 
     */
    public AgenceDTO() {
    
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
     * @param pGinAgenceMere ginAgenceMere
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
    public AgenceDTO(Date pDateModifiSTAIATA, Date pDateAgrement, Date pDateDebut, Date pDateFin, Date pDateRadiation, Integer pVersion, String pAgenceRA2, String pBsp, String pCible, String pCodeVilleIso, String pCodeService, String pDomaine, String pEnvoieSI, String pExclusifGrdCpt, String pGinAgenceMere, String pGsa, String pInfra, String pLocalisation, String pNumeroIATAMere, String pObservation, String pSousDomaine, String pStatutIATA, String pType, String pTypeAgrement, String pTypeVente, String pZoneChalandise) {
        this.dateModifiSTAIATA = pDateModifiSTAIATA;
        this.dateAgrement = pDateAgrement;
        this.dateDebut = pDateDebut;
        this.dateFin = pDateFin;
        this.dateRadiation = pDateRadiation;
        this.agenceRA2 = pAgenceRA2;
        this.bsp = pBsp;
        this.cible = pCible;
        this.codeVilleIso = pCodeVilleIso;
        this.codeService = pCodeService;
        this.domaine = pDomaine;
        this.envoieSI = pEnvoieSI;
        this.exclusifGrdCpt = pExclusifGrdCpt;
        this.ginAgenceMere = pGinAgenceMere;
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
     * @return ginAgenceMere
     */
    public String getGinAgenceMere() {
        return this.ginAgenceMere;
    }

    /**
     *
     * @param pGinAgenceMere ginAgenceMere value
     */
    public void setGinAgenceMere(String pGinAgenceMere) {
        this.ginAgenceMere = pGinAgenceMere;
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
    public List<LettreCompteDTO> getLettreCompte() {
        return this.lettreCompte;
    }

    /**
     *
     * @param pLettreCompte lettreCompte value
     */
    public void setLettreCompte(List<LettreCompteDTO> pLettreCompte) {
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
    public Set<OfficeIDDTO> getOffices() {
        return this.offices;
    }

    /**
     *
     * @param pOffices offices value
     */
    public void setOffices(Set<OfficeIDDTO> pOffices) {
        this.offices = pOffices;
    }

    /**
     *
     * @return reseaux
     */
    public Set<MembreReseauDTO> getReseaux() {
        return this.reseaux;
    }

    /**
     *
     * @param pReseaux reseaux value
     */
    public void setReseaux(Set<MembreReseauDTO> pReseaux) {
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
        /*PROTECTED REGION ID(toString_0VRt4Gk1EeGhB9497mGnHw) ENABLED START*/
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
        buffer.append("ginAgenceMere=").append(getGinAgenceMere());
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

    /*PROTECTED REGION ID(_0VRt4Gk1EeGhB9497mGnHw u m) ENABLED START*/
    public int hashCode() {
        return new HashCodeBuilder(17, 31). // two randomly chosen prime numbers
            // if deriving: appendSuper(super.hashCode()).
            append(this.getGin()).
            toHashCode();
    }

    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (obj == this)
            return true;
        if (obj.getClass() != getClass())
            return false;

        AgenceDTO rhs = (AgenceDTO) obj;
        return new EqualsBuilder().
            // if deriving: appendSuper(super.equals(obj)).
            append(this.getGin(), rhs.getGin()).
            isEquals();
    }

    public Object deepCopy(Object oldObj) throws IOException, ClassNotFoundException {
        ObjectOutputStream oos = null;
        ObjectInputStream ois = null;
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(bos);
            // serialize and pass the object
            oos.writeObject(oldObj);
            oos.flush();
            ByteArrayInputStream bin = new ByteArrayInputStream(bos.toByteArray());
            ois = new ObjectInputStream(bin);
            // return the new object
            return ois.readObject();

        } finally {
            oos.close();
            ois.close();
        }
    }
}
