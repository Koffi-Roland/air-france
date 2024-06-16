package com.airfrance.repind.entity.reference;

/*PROTECTED REGION ID(_3ISXIGksEeGhB9497mGnHw i) ENABLED START*/

// add not generated imports here

import org.apache.commons.lang.builder.ToStringBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/*PROTECTED REGION END*/


/**
 * <p>Title : CodeIndus.java</p>
 * BO CodeIndus
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
@Entity


@Table(name="CODE_INDUS")
public class CodeIndus implements Serializable {

/*PROTECTED REGION ID(serialUID _3ISXIGksEeGhB9497mGnHw) ENABLED START*/
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
     * scode
     */
    @Id
    @Column(name="SCODE")
    private String scode;
        
            
    /**
     * salias
     */
    @Column(name="SALIAS")
    private String salias;
        
            
    /**
     * slibelle
     */
    @Column(name="SLIBELLE")
    private String slibelle;
        
            
    /**
     * slibelleEn
     */
    @Column(name="SLIBELLE_EN")
    private String slibelleEn;
        
    /*PROTECTED REGION ID(_3ISXIGksEeGhB9497mGnHw u var) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/
    
    
    /** 
     * default constructor 
     */
    public CodeIndus() {
    }
    
        
    /** 
     * full constructor
     * @param pSalias salias
     * @param pScode scode
     * @param pSlibelle slibelle
     * @param pSlibelleEn slibelleEn
     */
    public CodeIndus(String pSalias, String pScode, String pSlibelle, String pSlibelleEn) {
        this.salias = pSalias;
        this.scode = pScode;
        this.slibelle = pSlibelle;
        this.slibelleEn = pSlibelleEn;
    }

    /**
     *
     * @return salias
     */
    public String getSalias() {
        return this.salias;
    }

    /**
     *
     * @param pSalias salias value
     */
    public void setSalias(String pSalias) {
        this.salias = pSalias;
    }

    /**
     *
     * @return scode
     */
    public String getScode() {
        return this.scode;
    }

    /**
     *
     * @param pScode scode value
     */
    public void setScode(String pScode) {
        this.scode = pScode;
    }

    /**
     *
     * @return slibelle
     */
    public String getSlibelle() {
        return this.slibelle;
    }

    /**
     *
     * @param pSlibelle slibelle value
     */
    public void setSlibelle(String pSlibelle) {
        this.slibelle = pSlibelle;
    }

    /**
     *
     * @return slibelleEn
     */
    public String getSlibelleEn() {
        return this.slibelleEn;
    }

    /**
     *
     * @param pSlibelleEn slibelleEn value
     */
    public void setSlibelleEn(String pSlibelleEn) {
        this.slibelleEn = pSlibelleEn;
    }

    /**
     *
     * @return object as string
     */
    public String toString() {
        String result = null;
        /*PROTECTED REGION ID(toString_3ISXIGksEeGhB9497mGnHw) ENABLED START*/
        result = toStringImpl();
        /*PROTECTED REGION END*/
        return result;
    }
    
    /**
     *
     * @return object as string
     */
    public String toStringImpl() {
        return new ToStringBuilder(this)
            .append("salias", getSalias())
            .append("scode", getScode())
            .append("slibelle", getSlibelle())
            .append("slibelleEn", getSlibelleEn())
            .toString();
    }

     
    
    /*PROTECTED REGION ID(equals hash _3ISXIGksEeGhB9497mGnHw) ENABLED START*/

    /**
     * {@inheritDoc}
     * @see java.lang.Object#equals(java.lang.Object)
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
        final CodeIndus other = (CodeIndus) obj;

        // TODO: writes or generates equals method

        return super.equals(other);
    }

    /**
     * {@inheritDoc}
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        int result = super.hashCode();

        // TODO: writes or generates hashcode method

        return result;
    }
    
    /*PROTECTED REGION END*/

    /*PROTECTED REGION ID(_3ISXIGksEeGhB9497mGnHw u m) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/
    
}
