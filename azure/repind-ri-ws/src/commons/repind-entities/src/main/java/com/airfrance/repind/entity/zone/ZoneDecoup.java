package com.airfrance.repind.entity.zone;

/*PROTECTED REGION ID(_8ViUYLbNEeCrCZp8iGNNVw i) ENABLED START*/

// add not generated imports here

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/*PROTECTED REGION END*/


/**
 * <p>Title : ZoneDecoup.java</p>
 * BO ZoneDecoup
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
@Entity

@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(
    name="STYPE",
    discriminatorType=DiscriminatorType.STRING,length=2)
@DiscriminatorValue("")

@Table(name="ZONE_DECOUP")
public abstract class ZoneDecoup implements Serializable {

/*PROTECTED REGION ID(serialUID _8ViUYLbNEeCrCZp8iGNNVw) ENABLED START*/
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
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ISEQ_ZONE_DECOUP")
    @SequenceGenerator(name="ISEQ_ZONE_DECOUP", sequenceName = "ISEQ_ZONE_DECOUP",
			allocationSize = 1)
    @Column(name="IGIN", length=12, nullable=false)
    private Long gin;


    /**
     * statut
     */
    @Column(name="SSTATUT", length=1, nullable=false)
    private String statut;
        
            
    /**
     * dateMaj
     */
    @Column(name="DDATE_MAJ", nullable=false)
    private Date dateMaj;
        
            
    /**
     * signatureMaj
     */
    @Column(name="SSIGNATURE_MAJ", length=16)
    private String signatureMaj;
        
            
    /**
     * nature
     */
    @Column(name="SNATURE", length=3)
    private String nature;
        
            
    /**
     * sousType
     */
    @Column(name="SSOUS_TYPE", length=2)
    private String sousType;
        
            
    /**
     * dateOuverture
     */
    @Column(name="DDATE_OUVERTURE")
    private Date dateOuverture;
        
            
    /**
     * dateFermeture
     */
    @Column(name="DDATE_FERMETURE")
    private Date dateFermeture;
        
            
    /**
     * liensIntCpZd
     */
    // 1 <-> * 
    @OneToMany(mappedBy="zoneDecoup")
    private Set<LienIntCpZd> liensIntCpZd;
        
            
    /**
     * pmZones
     */
    // 1 <-> * 
    @OneToMany(mappedBy="zoneDecoup")
    private Set<PmZone> pmZones;
        
    /*PROTECTED REGION ID(_8ViUYLbNEeCrCZp8iGNNVw u var) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/
    
    /** 
     * default constructor 
     */
    public ZoneDecoup() {
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
    public ZoneDecoup(Long pGin , String pStatut, Date pDateMaj, String pSignatureMaj, String pNature, String pSousType, Date pDateOuverture, Date pDateFermeture) {
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
    public Set<LienIntCpZd> getLiensIntCpZd() {
        return this.liensIntCpZd;
    }

    /**
     *
     * @param pLiensIntCpZd liensIntCpZd value
     */
    public void setLiensIntCpZd(Set<LienIntCpZd> pLiensIntCpZd) {
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
    public Set<PmZone> getPmZones() {
        return this.pmZones;
    }

    /**
     *
     * @param pPmZones pmZones value
     */
    public void setPmZones(Set<PmZone> pPmZones) {
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
        /*PROTECTED REGION ID(toString_8ViUYLbNEeCrCZp8iGNNVw) ENABLED START*/
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

     
    
    /*PROTECTED REGION ID(equals hash _8ViUYLbNEeCrCZp8iGNNVw) ENABLED START*/

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
        final ZoneDecoup other = (ZoneDecoup) obj;

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

    /*PROTECTED REGION ID(_8ViUYLbNEeCrCZp8iGNNVw u m) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/
    
}
