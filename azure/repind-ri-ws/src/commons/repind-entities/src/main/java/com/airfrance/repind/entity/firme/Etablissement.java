package com.airfrance.repind.entity.firme;

/*PROTECTED REGION ID(_aP9XMLbCEeCrCZp8iGNNVw i) ENABLED START*/

// add not generated imports here

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.SecondaryTable;
import java.io.Serializable;

/*PROTECTED REGION END*/


/**
 * <p>Title : Etablissement.java</p>
 * BO Etablissement
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
@Entity


@DiscriminatorValue("T")
@SecondaryTable(name="ETABLISSEMENT")
public class Etablissement extends PersonneMorale implements Serializable {

/*PROTECTED REGION ID(serialUID _aP9XMLbCEeCrCZp8iGNNVw) ENABLED START*/
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

            
    /*PROTECTED REGION ID(_aP9XM7bCEeCrCZp8iGNNVw p) ENABLED START*/
    /**
     * type
     */
    @Column(table="ETABLISSEMENT", name="STYPE", length=2, nullable=false)
    private String type;
    /*PROTECTED REGION END*/
        
            
    /*PROTECTED REGION ID(_aP9XNLbCEeCrCZp8iGNNVw p) ENABLED START*/
    /**
     * sginAgence
     */
    @Column(table="ETABLISSEMENT", name="SGIN_AGENCE", length=12)
    private String ginAgence;
    /*PROTECTED REGION END*/
        
            
    /*PROTECTED REGION ID(_aP9XNbbCEeCrCZp8iGNNVw p) ENABLED START*/
    /**
     * siret
     */
    @Column(table="ETABLISSEMENT", name="SSIRET", length=14)
    private String siret;
    /*PROTECTED REGION END*/
        
            
    /*PROTECTED REGION ID(_aP9XNrbCEeCrCZp8iGNNVw p) ENABLED START*/
    /**
     * siegeSocial
     */
    @Column(table="ETABLISSEMENT", name="SSIEGE_SOCIAL", length=1, nullable=false)
    private String siegeSocial;
    /*PROTECTED REGION END*/
        
            
    /*PROTECTED REGION ID(_aP9XN7bCEeCrCZp8iGNNVw p) ENABLED START*/
    /**
     * sce
     */
    @Column(table="ETABLISSEMENT", name="SCE", length=1)
    private String ce;
    /*PROTECTED REGION END*/
        
            
    /*PROTECTED REGION ID(_aP9XOLbCEeCrCZp8iGNNVw p) ENABLED START*/
    /**
     * srem
     */
    @Column(table="ETABLISSEMENT", name="SREM", length=225)
    private String rem;
    /*PROTECTED REGION END*/
        
    /*PROTECTED REGION ID(_aP9XMLbCEeCrCZp8iGNNVw u var) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/
    
    /** 
     * default constructor 
     */
    public Etablissement() {
    }
        
    /** 
     * full constructor
     * @param pType type
     * @param pGinAgence ginAgence
     * @param pSiret siret
     * @param pSiegeSocial siegeSocial
     * @param pCe ce
     * @param pRem rem
     */
    public Etablissement(String pType, String pGinAgence, String pSiret, String pSiegeSocial, String pCe, String pRem) {
        this.type = pType;
        this.ginAgence = pGinAgence;
        this.siret = pSiret;
        this.siegeSocial = pSiegeSocial;
        this.ce = pCe;
        this.rem = pRem;
    }

    /**
     *
     * @return ce
     */
    public String getCe() {
        return this.ce;
    }

    /**
     *
     * @param pCe ce value
     */
    public void setCe(String pCe) {
        this.ce = pCe;
    }

    /**
     *
     * @return ginAgence
     */
    public String getGinAgence() {
        return this.ginAgence;
    }

    /**
     *
     * @param pGinAgence ginAgence value
     */
    public void setGinAgence(String pGinAgence) {
        this.ginAgence = pGinAgence;
    }

    /**
     *
     * @return rem
     */
    public String getRem() {
        return this.rem;
    }

    /**
     *
     * @param pRem rem value
     */
    public void setRem(String pRem) {
        this.rem = pRem;
    }

    /**
     *
     * @return siegeSocial
     */
    public String getSiegeSocial() {
        return this.siegeSocial;
    }

    /**
     *
     * @param pSiegeSocial siegeSocial value
     */
    public void setSiegeSocial(String pSiegeSocial) {
        this.siegeSocial = pSiegeSocial;
    }

    /**
     *
     * @return siret
     */
    public String getSiret() {
        return this.siret;
    }

    /**
     *
     * @param pSiret siret value
     */
    public void setSiret(String pSiret) {
        this.siret = pSiret;
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
     * @return object as string
     */
    public String toString() {
        String result = null;
        /*PROTECTED REGION ID(toString_aP9XMLbCEeCrCZp8iGNNVw) ENABLED START*/
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
        buffer.append("type=").append(getType());
        buffer.append(",");
        buffer.append("ginAgence=").append(getGinAgence());
        buffer.append(",");
        buffer.append("siret=").append(getSiret());
        buffer.append(",");
        buffer.append("siegeSocial=").append(getSiegeSocial());
        buffer.append(",");
        buffer.append("ce=").append(getCe());
        buffer.append(",");
        buffer.append("rem=").append(getRem());
        buffer.append("]");
        return buffer.toString();
    }

     
    
    /*PROTECTED REGION ID(equals hash _aP9XMLbCEeCrCZp8iGNNVw) ENABLED START*/

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
        final Etablissement other = (Etablissement) obj;

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

    /*PROTECTED REGION ID(_aP9XMLbCEeCrCZp8iGNNVw u m) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/
    
}
