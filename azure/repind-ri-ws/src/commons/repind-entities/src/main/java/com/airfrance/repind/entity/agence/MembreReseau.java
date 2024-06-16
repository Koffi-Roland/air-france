package com.airfrance.repind.entity.agence;

/*PROTECTED REGION ID(_QeyLsGk1EeGhB9497mGnHw i) ENABLED START*/

// add not generated imports here

import com.airfrance.repind.entity.reference.Reseau;
import org.hibernate.annotations.ForeignKey;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/*PROTECTED REGION END*/


/**
 * <p>Title : MembreReseau.java</p>
 * BO MembreReseau	
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
@Entity


@Table(name="MEMBRE_RESEAU")
public class MembreReseau implements Serializable {

/*PROTECTED REGION ID(serialUID _QeyLsGk1EeGhB9497mGnHw) ENABLED START*/
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
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ISEQ_MEMBRE_RESEAU")
    @SequenceGenerator(name="ISEQ_MEMBRE_RESEAU", sequenceName = "ISEQ_MEMBRE_RESEAU", allocationSize = 1)
    @Column(name="ICLE_MBR")
    private Integer cle;
        
            
    /**
     * dateDebut
     */
    @Column(name="DDATE_DEBUT", nullable=false)
    private Date dateDebut;
        
            
    /**
     * dateFin
     */
    @Column(name="DDATE_FIN")
    private Date dateFin;
        
            
    /**
     * agence
     */
    // * <-> 1
    @ManyToOne()
    @JoinColumn(name="SGIN", nullable=false)
    @ForeignKey(name = "FK_MBR_AG")
    private Agence agence;
        
            
    /**
     * reseau
     */
    // * <-> 1
    @ManyToOne()
    @JoinColumn(name="SCODE", nullable=false)
    @ForeignKey(name = "FK_MBR_RES")
    private Reseau reseau;
        
    /*PROTECTED REGION ID(_QeyLsGk1EeGhB9497mGnHw u var) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/
    
    /** 
     * default constructor 
     */
    public MembreReseau() {
    }
        
    /** 
     * full constructor
     * @param pDateDebut dateDebut
     * @param pDateFin dateFin
     * @param pCle cle
     */
    public MembreReseau(Date pDateDebut, Date pDateFin, Integer pCle) {
        this.dateDebut = pDateDebut;
        this.dateFin = pDateFin;
        this.cle = pCle;
    }

    /**
     *
     * @return agence
     */
    public Agence getAgence() {
        return this.agence;
    }

    /**
     *
     * @param pAgence agence value
     */
    public void setAgence(Agence pAgence) {
        this.agence = pAgence;
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
     * @return reseau
     */
    public Reseau getReseau() {
        return this.reseau;
    }

    /**
     *
     * @param pReseau reseau value
     */
    public void setReseau(Reseau pReseau) {
        this.reseau = pReseau;
    }

    /**
     *
     * @return object as string
     */
    public String toString() {
        String result = null;
        /*PROTECTED REGION ID(toString_QeyLsGk1EeGhB9497mGnHw) ENABLED START*/
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
        buffer.append("dateDebut=").append(getDateDebut());
        buffer.append(",");
        buffer.append("dateFin=").append(getDateFin());
        buffer.append(",");
        buffer.append("cle=").append(getCle());
        buffer.append("]");
        return buffer.toString();
    }

     
    
    /*PROTECTED REGION ID(equals hash _QeyLsGk1EeGhB9497mGnHw) ENABLED START*/

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
        final MembreReseau other = (MembreReseau) obj;

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

    /*PROTECTED REGION ID(_QeyLsGk1EeGhB9497mGnHw u m) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/
    
}
