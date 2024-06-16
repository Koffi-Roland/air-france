package com.airfrance.repind.dto.zone;

/*PROTECTED REGION ID(_PsYf8LdgEeCrCZp8iGNNVw i) ENABLED START*/

// add not generated imports here

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/*PROTECTED REGION END*/

/**
 * <p>Title : ZoneDecoupDTO</p>
 * 
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
@JsonTypeInfo(use = Id.NAME, include = As.PROPERTY, property="type")
@JsonSubTypes({
    @JsonSubTypes.Type(value = ZoneCommDTO.class, name = "ZC"),
    @JsonSubTypes.Type(value = ZoneVenteDTO.class, name = "ZV"),
    @JsonSubTypes.Type(value = ZoneFinanciereDTO.class, name = "ZF")
})
public abstract class ZoneDecoupDTO implements Serializable {
        
    /**
	 * 
	 */
	private static final long serialVersionUID = 52893025617513590L;


	/**
     * gin
     */
    private Long gin;

    /**
     * statut
     */
    private String statut;
        
        
    /**
     * dateMaj
     */
    private Date dateMaj;
        
        
    /**
     * signatureMaj
     */
    private String signatureMaj;
        
        
    /**
     * nature
     */
    private String nature;
        
        
    /**
     * sousType
     */
    private String sousType;
        
        
    /**
     * dateOuverture
     */
    private Date dateOuverture;
        
        
    /**
     * dateFermeture
     */
    private Date dateFermeture;
        
        
    /**
     * liensIntCpZd
     */
    private Set<LienIntCpZdDTO> liensIntCpZd;
        
        
    /**
     * pmZones
     */
    private Set<PmZoneDTO> pmZones;
        

    /*PROTECTED REGION ID(_PsYf8LdgEeCrCZp8iGNNVw u var) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/

    
    
    /** 
     * default constructor 
     */
    public ZoneDecoupDTO() {
    
    }
    
    
    /** 
     * full constructor
     * @param pGin gin
     * @param pStatut statut
     * @param pDateMaj dateMaj
     * @param pSignatureMaj signatureMaj
     * @param pNature nature
     * @param pSousType sousType
     * @param pDateOuverture dateOuverture
     * @param pDateFermeture dateFermeture
     */
    public ZoneDecoupDTO(Long pGin, String pStatut, Date pDateMaj, String pSignatureMaj, String pNature, String pSousType, Date pDateOuverture, Date pDateFermeture) {
        this.gin = pGin;
        this.statut = pStatut;
        this.dateMaj = pDateMaj;
        this.signatureMaj = pSignatureMaj;
        this.nature = pNature;
        this.sousType = pSousType;
        this.dateOuverture = pDateOuverture;
        this.dateFermeture = pDateFermeture;
    }

	/**
     *
     * @return dateFermeture
     */
    public Date getDateFermeture() {
        return this.dateFermeture;
    }

    /**
     *
     * @param pDateFermeture dateFermeture value
     */
    public void setDateFermeture(Date pDateFermeture) {
        this.dateFermeture = pDateFermeture;
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
     * @return dateOuverture
     */
    public Date getDateOuverture() {
        return this.dateOuverture;
    }

    /**
     *
     * @param pDateOuverture dateOuverture value
     */
    public void setDateOuverture(Date pDateOuverture) {
        this.dateOuverture = pDateOuverture;
    }

    /**
     *
     * @return gin
     */
    public Long getGin() {
        return this.gin;
    }

    /**
     *
     * @param pGin gin value
     */
    public void setGin(Long pGin) {
        this.gin = pGin;
    }

    /**
     *
     * @return liensIntCpZd
     */
    public Set<LienIntCpZdDTO> getLiensIntCpZd() {
        return this.liensIntCpZd;
    }

    /**
     *
     * @param pLiensIntCpZd liensIntCpZd value
     */
    public void setLiensIntCpZd(Set<LienIntCpZdDTO> pLiensIntCpZd) {
        this.liensIntCpZd = pLiensIntCpZd;
    }

    /**
     *
     * @return nature
     */
    public String getNature() {
        return this.nature;
    }

    /**
     *
     * @param pNature nature value
     */
    public void setNature(String pNature) {
        this.nature = pNature;
    }

    /**
     *
     * @return pmZones
     */
    public Set<PmZoneDTO> getPmZones() {
        return this.pmZones;
    }

    /**
     *
     * @param pPmZones pmZones value
     */
    public void setPmZones(Set<PmZoneDTO> pPmZones) {
        this.pmZones = pPmZones;
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
     * @return sousType
     */
    public String getSousType() {
        return this.sousType;
    }

    /**
     *
     * @param pSousType sousType value
     */
    public void setSousType(String pSousType) {
        this.sousType = pSousType;
    }

    /**
     *
     * @return statut
     */
    public String getStatut() {
        return this.statut;
    }

    /**
     *
     * @param pStatut statut value
     */
    public void setStatut(String pStatut) {
        this.statut = pStatut;
    }

    /**
     *
     * @return object as string
     */
    public String toString() {
        String result = null;
        /*PROTECTED REGION ID(toString_PsYf8LdgEeCrCZp8iGNNVw) ENABLED START*/
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
        buffer.append("statut=").append(getStatut());
        buffer.append(",");
        buffer.append("dateMaj=").append(getDateMaj());
        buffer.append(",");
        buffer.append("signatureMaj=").append(getSignatureMaj());
        buffer.append(",");
        buffer.append("nature=").append(getNature());
        buffer.append(",");
        buffer.append("sousType=").append(getSousType());
        buffer.append(",");
        buffer.append("dateOuverture=").append(getDateOuverture());
        buffer.append(",");
        buffer.append("dateFermeture=").append(getDateFermeture());
        buffer.append("]");
        return buffer.toString();
    }

    /*PROTECTED REGION ID(_PsYf8LdgEeCrCZp8iGNNVw u m) ENABLED START*/
    public int hashCode() {
        return new HashCodeBuilder(17, 31). // two randomly chosen prime numbers
            // if deriving: appendSuper(super.hashCode()).
            append(gin).
            toHashCode();
    }

    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (obj == this)
            return true;
        if (obj.getClass() != getClass())
            return false;

        ZoneDecoupDTO rhs = (ZoneDecoupDTO) obj;
        return new EqualsBuilder().
            // if deriving: appendSuper(super.equals(obj)).
            append(gin, rhs.gin).
            isEquals();
    }
    /*PROTECTED REGION END*/

}
