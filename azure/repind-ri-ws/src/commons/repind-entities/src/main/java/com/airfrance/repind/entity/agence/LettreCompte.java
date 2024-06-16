package com.airfrance.repind.entity.agence;

/*PROTECTED REGION ID(_QepB5Wk1EeGhB9497mGnHw i) ENABLED START*/

// add not generated imports here

import javax.persistence.*;
import java.io.Serializable;

/*PROTECTED REGION END*/


/**
 * <p>Title : LettreCompte.java</p>
 * BO LettreCompte
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
@Entity


@Table(name="LETTRE_COMPT")
public class LettreCompte implements Serializable {

/*PROTECTED REGION ID(serialUID _QepB5Wk1EeGhB9497mGnHw) ENABLED START*/
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
     * icle
     */
    @Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ISEQ_LETTRE_COMPT")
    @SequenceGenerator(name="ISEQ_LETTRE_COMPT", sequenceName = "ISEQ_LETTRE_COMPT", allocationSize = 1)
    @Column(name="ICLE")
    private Integer icle;
        
            
    /**
     * sgin
     */
    @Column(name="SGIN")
    private String sgin;
        
            
    /**
     * slettreComptoir
     */
    @Column(name="SLETTRE_COMPTOIR")
    private String slettreComptoir;
        
    /*PROTECTED REGION ID(_QepB5Wk1EeGhB9497mGnHw u var) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/
    
    /** 
     * default constructor 
     */
    public LettreCompte() {
    }
        
    /** 
     * full constructor
     * @param pIcle icle
     * @param pSgin sgin
     * @param pSlettreComptoir slettreComptoir
     */
    public LettreCompte(Integer pIcle, String pSgin, String pSlettreComptoir) {
        this.icle = pIcle;
        this.sgin = pSgin;
        this.slettreComptoir = pSlettreComptoir;
    }

    /**
     *
     * @return icle
     */
    public Integer getIcle() {
        return this.icle;
    }

    /**
     *
     * @param pIcle icle value
     */
    public void setIcle(Integer pIcle) {
        this.icle = pIcle;
    }

    /**
     *
     * @return sgin
     */
    public String getSgin() {
        return this.sgin;
    }

    /**
     *
     * @param pSgin sgin value
     */
    public void setSgin(String pSgin) {
        this.sgin = pSgin;
    }

    /**
     *
     * @return slettreComptoir
     */
    public String getSlettreComptoir() {
        return this.slettreComptoir;
    }

    /**
     *
     * @param pSlettreComptoir slettreComptoir value
     */
    public void setSlettreComptoir(String pSlettreComptoir) {
        this.slettreComptoir = pSlettreComptoir;
    }

    /**
     *
     * @return object as string
     */
    public String toString() {
        String result = null;
        /*PROTECTED REGION ID(toString_QepB5Wk1EeGhB9497mGnHw) ENABLED START*/
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
        buffer.append("icle=").append(getIcle());
        buffer.append(",");
        buffer.append("sgin=").append(getSgin());
        buffer.append(",");
        buffer.append("slettreComptoir=").append(getSlettreComptoir());
        buffer.append("]");
        return buffer.toString();
    }

     
    
    /*PROTECTED REGION ID(equals hash _QepB5Wk1EeGhB9497mGnHw) ENABLED START*/

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
        final LettreCompte other = (LettreCompte) obj;

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

    /*PROTECTED REGION ID(_QepB5Wk1EeGhB9497mGnHw u m) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/
    
}
