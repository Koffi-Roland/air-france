package com.airfrance.repind.entity.agence;

import com.airfrance.repind.entity.firme.PersonneMoraleLight;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.SecondaryTable;
import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * Title : Agence.java
 * </p>
 * BO Agence
 * <p>
 * Copyright : Copyright (c) 2009
 * </p>
 * <p>
 * Company : AIRFRANCE
 * </p>
 */
@Entity

@DiscriminatorValue("A")
@SecondaryTable(name = "AGENCE")
public class AgenceLight extends PersonneMoraleLight implements Serializable {

	/**
	 * Determines if a de-serialized file is compatible with this class.
	 *
	 * Maintainers must change this value if and only if the new version of this
	 * class is not compatible with old versions. See Sun docs for <a
	 * href=http://java.sun.com/j2se/1.5.0/docs/guide/serialization
	 * /serialization/spec/class.html#4100> details. </a>
	 *
	 * Not necessary to include in first version of the class, but included here
	 * as a reminder of its importance.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * ddateModifiSTAIATA
	 */
	@Column(table = "AGENCE", name = "DDAT_MODIF_STA_IATA")
	private Date dateModifiSTAIATA;

	/**
	 * ddateAgrement
	 */
	@Column(table = "AGENCE", name = "DDATE_AGREMENT")
	private Date dateAgrement;
	/**
	 * ddateDebut
	 */
	@Column(table = "AGENCE", name = "DDATE_DEB")
	private Date dateDebut;
	/**
	 * ddateFin
	 */
	@Column(table = "AGENCE", name = "DDATE_FIN")
	private Date dateFin;
	/**
	 * ddateRadiation
	 */
	@Column(table = "AGENCE", name = "DDATE_RADIATION")
	private Date dateRadiation;
	/**
	 * sagenceRA2
	 */
	@Column(table = "AGENCE", name = "SAGENCE_RA2")
	private String agenceRA2;
	/**
	 * sbsp
	 */
	@Column(table = "AGENCE", name = "SBSP")
	private String bsp;
	/**
	 * scible
	 */
	@Column(table = "AGENCE", name = "SCIBLE")
	private String cible;
	/**
	 * scodeVilleIso
	 */
	@Column(table = "AGENCE", name = "SCOD_VIL_ISO")
	private String codeVilleIso;
	/**
	 * scodeService
	 */
	@Column(table = "AGENCE", name = "SCODE_SERVICE")
	private String codeService;
	/**
	 * sdomaine
	 */
	@Column(table = "AGENCE", name = "SDOMAINE")
	private String domaine;
	/**
	 * senvoieSI
	 */
	@Column(table = "AGENCE", name = "SENVOI_SI")
	private String envoieSI;
	/**
	 * sexclusifGrdCpt
	 */
	@Column(table = "AGENCE", name = "SEXCLUSIF_GRD_CPT")
	private String exclusifGrdCpt;
	/**
	 * sgsa
	 */
	@Column(table = "AGENCE", name = "SGSA")
	private String gsa;
	/**
	 * sinfra
	 */
	@Column(table = "AGENCE", name = "SINFRA")
	private String infra;
	/**
	 * slocalisation
	 */
	@Column(table = "AGENCE", name = "SLOCALISATION")
	private String localisation;
	/**
	 * snumeroIATAMere
	 */
	@Column(table = "AGENCE", name = "SNUMERO_IATA_MERE")
	private String numeroIATAMere;
	/**
	 * sobservation
	 */
	@Column(table = "AGENCE", name = "SOBSERVATION")
	private String observation;
	/**
	 * ssousDomaine
	 */
	@Column(table = "AGENCE", name = "SSOUS_DOMAINE")
	private String sousDomaine;
	/**
	 * sstatutIATA
	 */
	@Column(table = "AGENCE", name = "SSTATUT_IATA")
	private String statutIATA;
	/**
	 * stype
	 */
	@Column(table = "AGENCE", name = "STYPE")
	private String type;
	/**
	 * stypeAgrement
	 */
	@Column(table = "AGENCE", name = "STYPE_AGREMENT")
	private String typeAgrement;
	/**
	 * stypeVente
	 */
	@Column(table = "AGENCE", name = "STYPE_VENTE")
	private String typeVente;
	/**
	 * szoneChalandise
	 */
	@Column(table = "AGENCE", name = "SZONE_CHALANDISE")
	private String zoneChalandise;
	/**
	 * iataStationAirportCode
	 */
	@Column(table = "AGENCE", name = "SIATA_STATION_AIRPORT_CODE")
	private String iataStationAirportCode;
	/**
	 * SFORCING_AIRPORT_CODE_UPDATE
	 */
	@Column(table = "AGENCE", name = "SFORCING_AIRPORT_CODE_UPDATE")
	private Integer forcingUpdate;

	/**
	 * default constructor
	 */
	public AgenceLight() {
	}

	/**
	 * full constructor
	 * 
	 * @param pDateModifiSTAIATA
	 *            dateModifiSTAIATA
	 * @param pDateAgrement
	 *            dateAgrement
	 * @param pDateDebut
	 *            dateDebut
	 * @param pDateFin
	 *            dateFin
	 * @param pDateRadiation
	 *            dateRadiation
	 * @param pVersion
	 *            version
	 * @param pAgenceRA2
	 *            agenceRA2
	 * @param pBsp
	 *            bsp
	 * @param pCible
	 *            cible
	 * @param pCodeVilleIso
	 *            codeVilleIso
	 * @param pCodeService
	 *            codeService
	 * @param pDomaine
	 *            domaine
	 * @param pEnvoieSI
	 *            envoieSI
	 * @param pExclusifGrdCpt
	 *            exclusifGrdCpt
	 * @param pGsa
	 *            gsa
	 * @param pInfra
	 *            infra
	 * @param pLocalisation
	 *            localisation
	 * @param pNumeroIATAMere
	 *            numeroIATAMere
	 * @param pObservation
	 *            observation
	 * @param pSousDomaine
	 *            sousDomaine
	 * @param pStatutIATA
	 *            statutIATA
	 * @param pType
	 *            type
	 * @param pTypeAgrement
	 *            typeAgrement
	 * @param pTypeVente
	 *            typeVente
	 * @param pZoneChalandise
	 *            zoneChalandise
	 */
	public AgenceLight(Date pDateModifiSTAIATA, Date pDateAgrement, Date pDateDebut, Date pDateFin, Date pDateRadiation,
                       Integer pVersion, String pAgenceRA2, String pBsp, String pCible, String pCodeVilleIso, String pCodeService,
                       String pDomaine, String pEnvoieSI, String pExclusifGrdCpt, String pGsa, String pInfra, String pLocalisation,
                       String pNumeroIATAMere, String pObservation, String pSousDomaine, String pStatutIATA, String pType,
                       String pTypeAgrement, String pTypeVente, String pZoneChalandise) {
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
	 * @param pAgenceRA2
	 *            agenceRA2 value
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
	 * @param pBsp
	 *            bsp value
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
	 * @param pCible
	 *            cible value
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
	 * @param pCodeService
	 *            codeService value
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
	 * @param pCodeVilleIso
	 *            codeVilleIso value
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
	 * @param pDateAgrement
	 *            dateAgrement value
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
	 * @param pDateDebut
	 *            dateDebut value
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
	 * @param pDateFin
	 *            dateFin value
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
	 * @param pDateModifiSTAIATA
	 *            dateModifiSTAIATA value
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
	 * @param pDateRadiation
	 *            dateRadiation value
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
	 * @param pDomaine
	 *            domaine value
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
	 * @param pEnvoieSI
	 *            envoieSI value
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
	 * @param pExclusifGrdCpt
	 *            exclusifGrdCpt value
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
	 * @param pGsa
	 *            gsa value
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
	 * @param pInfra
	 *            infra value
	 */
	public void setInfra(String pInfra) {
		this.infra = pInfra;
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
	 * @param pLocalisation
	 *            localisation value
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
	 * @param pNumeroIATAMere
	 *            numeroIATAMere value
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
	 * @param pObservation
	 *            observation value
	 */
	public void setObservation(String pObservation) {
		this.observation = pObservation;
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
	 * @param pSousDomaine
	 *            sousDomaine value
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
	 * @param pStatutIATA
	 *            statutIATA value
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
	 * @param pType
	 *            type value
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
	 * @param pTypeAgrement
	 *            typeAgrement value
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
	 * @param pTypeVente
	 *            typeVente value
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
	 * @param pZoneChalandise
	 *            zoneChalandise value
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
	 * @param iataStationAirportCode
	 *            iataStationAirportCode à définir
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
	 * @param forcingUpdate
	 *            forcingUpdate à définir
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
		result = toStringImpl();
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

	/**
	 * {@inheritDoc}
	 * 
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
		final AgenceLight other = (AgenceLight) obj;

		return super.equals(other);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see Object#hashCode()
	 */
	@Override
	public int hashCode() {
		int result = super.hashCode();

		return result;
	}

}
