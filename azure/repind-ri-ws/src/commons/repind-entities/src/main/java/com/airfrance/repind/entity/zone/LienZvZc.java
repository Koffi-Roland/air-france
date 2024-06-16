package com.airfrance.repind.entity.zone;

/*PROTECTED REGION ID(_hsaS8EfeEeSjFN6DwEJjiw i) ENABLED START*/

// add not generated imports here

import org.hibernate.annotations.ForeignKey;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/*PROTECTED REGION END*/


/**
 * <p>Title : LienZvZc.java</p>
 * BO LienZvZc
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
@Entity


@Table(name="ZD_ASSOCIEE")
public class LienZvZc implements Serializable {

/*PROTECTED REGION ID(serialUID _hsaS8EfeEeSjFN6DwEJjiw) ENABLED START*/
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
     * cle
     */
    @Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ISEQ_ZD_ASSOCIEE")
    @SequenceGenerator(name="ISEQ_ZD_ASSOCIEE", sequenceName = "ISEQ_ZD_ASSOCIEE", allocationSize = 1)
    @Column(length=10)
    private Integer icle;
        
            
    /**
     * type
     */
    @Column(name="TYPE", length=1)
    private String type;
        
            
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
     * zoneComm
     */
    // * <-> 1
    @ManyToOne()
    @JoinColumn(name="IGIN_ZONE_CIBLE", nullable=false)
    @ForeignKey(name = "ZD_ASSO_ZDECOUP_CIBLE_FK")
    private ZoneComm zoneComm;
        
            
    /**
     * zoneVente
     */
    // * <-> 1
    @ManyToOne()
    @JoinColumn(name="IGIN_ZONE_SOURCE", nullable=false)
    @ForeignKey(name = "ZD_ASSO_ZDECOUP_SOURCE_FK")
    private ZoneVente zoneVente;
        
    /*PROTECTED REGION ID(_hsaS8EfeEeSjFN6DwEJjiw u var) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/
    
    /** 
     * default constructor 
     */
    public LienZvZc() {
    }
        
    /** 
     * full constructor
     * @param pCle cle
     * @param pType type
     * @param pDateOuverture dateOuverture
     * @param pDateFermeture dateFermeture
     */
    public LienZvZc(Integer pCle, String pType, Date pDateOuverture, Date pDateFermeture) {
        this.icle = pCle;
        this.type = pType;
        this.dateOuverture = pDateOuverture;
        this.dateFermeture = pDateFermeture;
    }

    /**
     *
     * @return cle
     */
    public Integer getCle() {
        return this.icle;
    }

    /**
     *
     * @param pCle cle value
     */
    public void setCle(Integer pCle) {
        this.icle = pCle;
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
     * @return zoneComm
     */
    public ZoneComm getZoneComm() {
        return this.zoneComm;
    }

    /**
     *
     * @param pZoneComm zoneComm value
     */
    public void setZoneComm(ZoneComm pZoneComm) {
        this.zoneComm = pZoneComm;
    }

    /**
     *
     * @return zoneVente
     */
    public ZoneVente getZoneVente() {
        return this.zoneVente;
    }

    /**
     *
     * @param pZoneVente zoneVente value
     */
    public void setZoneVente(ZoneVente pZoneVente) {
        this.zoneVente = pZoneVente;
    }

    /**
     *
     * @return object as string
     */
    public String toString() {
        String result = null;
        /*PROTECTED REGION ID(toString_hsaS8EfeEeSjFN6DwEJjiw) ENABLED START*/
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
        buffer.append("type=").append(getType());
        buffer.append(",");
        buffer.append("dateOuverture=").append(getDateOuverture());
        buffer.append(",");
        buffer.append("dateFermeture=").append(getDateFermeture());
        buffer.append("]");
        return buffer.toString();
    }

     
    
    /*PROTECTED REGION ID(equals hash _hsaS8EfeEeSjFN6DwEJjiw) ENABLED START*/
    
    /**
     * {@inheritDoc}
     * @see Object#hashCode()
     */
    @Override
    public int hashCode() {
        //TODO Customize here if necessary
        return hashCodeImpl();
    }

    /**
     * {@inheritDoc}
     * @see Object#equals(Object)
     */
    @Override
    public boolean equals(Object obj) {
        //TODO Customize here if necessary
        return equalsImpl(obj);
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

    /*PROTECTED REGION ID(_hsaS8EfeEeSjFN6DwEJjiw u m) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/
    
}
