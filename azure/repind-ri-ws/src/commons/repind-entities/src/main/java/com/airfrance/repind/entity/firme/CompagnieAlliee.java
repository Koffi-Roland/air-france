package com.airfrance.repind.entity.firme;

/*PROTECTED REGION ID(_H_7pvWktEeGhB9497mGnHw i) ENABLED START*/

// add not generated imports here

import org.hibernate.annotations.ForeignKey;

import javax.persistence.*;
import java.io.Serializable;

/*PROTECTED REGION END*/


/**
 * <p>Title : CompagnieAlliee.java</p>
 * BO CompagnieAlliee
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
@Entity


@Table(name="CIE_ALLIEE")
@SequenceGenerator(
    name="ISEQ_CIE_ALLIEE",
    sequenceName="ISEQ_CIE_ALLIEE",    
    allocationSize=1

)
public class CompagnieAlliee implements Serializable {

/*PROTECTED REGION ID(serialUID _H_7pvWktEeGhB9497mGnHw) ENABLED START*/
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
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ISEQ_CIE_ALLIEE")    
    @Column(name="ICLE")
    private Integer cle;
        
            
    /**
     * codeCie
     */
    @Column(name="SCODE_CIE", length=3, nullable=false)
    private String codeCie;
        
            
    /**
     * personneMorale
     */
    // * <-> 1
    @ManyToOne()
    @JoinColumn(name="SGIN", nullable=false)
    @ForeignKey(name = "FK_PM_CIE_ALLIEE")
    private PersonneMorale personneMorale;
        
    /*PROTECTED REGION ID(_H_7pvWktEeGhB9497mGnHw u var) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/
    
    /** 
     * default constructor 
     */
    public CompagnieAlliee() {
    }
        
    /** 
     * full constructor
     * @param pCle cle
     * @param pCodeCie codeCie
     */
    public CompagnieAlliee(Integer pCle, String pCodeCie) {
        this.cle = pCle;
        this.codeCie = pCodeCie;
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
     * @return codeCie
     */
    public String getCodeCie() {
        return this.codeCie;
    }

    /**
     *
     * @param pCodeCie codeCie value
     */
    public void setCodeCie(String pCodeCie) {
        this.codeCie = pCodeCie;
    }

    /**
     *
     * @return personneMorale
     */
    public PersonneMorale getPersonneMorale() {
        return this.personneMorale;
    }

    /**
     *
     * @param pPersonneMorale personneMorale value
     */
    public void setPersonneMorale(PersonneMorale pPersonneMorale) {
        this.personneMorale = pPersonneMorale;
    }

    /**
     *
     * @return object as string
     */
    public String toString() {
        String result = null;
        /*PROTECTED REGION ID(toString_H_7pvWktEeGhB9497mGnHw) ENABLED START*/
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
        buffer.append("codeCie=").append(getCodeCie());
        buffer.append("]");
        return buffer.toString();
    }

     
    
    /*PROTECTED REGION ID(equals hash _H_7pvWktEeGhB9497mGnHw) ENABLED START*/

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
        final CompagnieAlliee other = (CompagnieAlliee) obj;

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

    /*PROTECTED REGION ID(_H_7pvWktEeGhB9497mGnHw u m) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/
    
}
