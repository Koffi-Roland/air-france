package com.airfrance.repind.entity.adresse;

/*PROTECTED REGION ID(_cDkn4IEiEeCtut40RvtPWA i) ENABLED START*/

// add not generated imports here

import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.ForeignKey;

import javax.persistence.*;
import java.io.Serializable;


/*PROTECTED REGION END*/


/**
 * <p>Title : FormalizedAdr.java</p>
 * BO FormalizedAdr
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
@Entity


@Table(name="FORMALIZED_ADR")
public class FormalizedAdr implements Serializable {

/*PROTECTED REGION ID(serialUID _cDkn4IEiEeCtut40RvtPWA) ENABLED START*/
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
     * sainAdr
     */
    @Id
    @Column(name="SAIN_ADR", length=16, nullable=false, updatable=false)
    private String sainAdr;
        
            
    /**
     * formatedLine1
     */
    @Column(name="SFORMATED_LINE_1", length=50)
    private String formatedLine1;
        
            
    /**
     * formatedLine2
     */
    @Column(name="SFORMATED_LINE_2", length=50)
    private String formatedLine2;
        
            
    /**
     * formatedLine3
     */
    @Column(name="SFORMATED_LINE_3", length=50)
    private String formatedLine3;
        
            
    /**
     * formatedLine4
     */
    @Column(name="SFORMATED_LINE_4", length=50)
    private String formatedLine4;
        
            
    /**
     * formatedLine5
     */
    @Column(name="SFORMATED_LINE_5", length=50)
    private String formatedLine5;
        
            
    /**
     * formatedLine6
     */
    @Column(name="SFORMATED_LINE_6", length=50)
    private String formatedLine6;
        
            
    /**
     * formatedLine7
     */
    @Column(name="SFORMATED_LINE_7", length=50)
    private String formatedLine7;
        
            
    /**
     * formatedLine8
     */
    @Column(name="SFORMATED_LINE_8", length=50)
    private String formatedLine8;
        
            
    /**
     * formatedLine9
     */
    @Column(name="SFORMATED_LINE_9", length=50)
    private String formatedLine9;
    
    /**
     * postalAddress
     */
    // * <-> 1
    @ManyToOne()
    @JoinColumn(name="SAIN_ADR", insertable=false, updatable=false)
    @ForeignKey(name = "FK_FORMALIZ_FK_FORMAL_ADR_POST")
    private PostalAddress postalAddress;
        
    /*PROTECTED REGION ID(_cDkn4IEiEeCtut40RvtPWA u var) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/
    
    
    /** 
     * default constructor 
     */
    public FormalizedAdr() {
    }
    
        
    /** 
     * full constructor
     * @param pSainAdr sainAdr
     * @param pFormatedLine1 formatedLine1
     * @param pFormatedLine2 formatedLine2
     * @param pFormatedLine3 formatedLine3
     * @param pFormatedLine4 formatedLine4
     * @param pFormatedLine5 formatedLine5
     * @param pFormatedLine6 formatedLine6
     * @param pFormatedLine7 formatedLine7
     * @param pFormatedLine8 formatedLine8
     * @param pFormatedLine9 formatedLine9
     */
    public FormalizedAdr(String pSainAdr, String pFormatedLine1, String pFormatedLine2, String pFormatedLine3, String pFormatedLine4, String pFormatedLine5, String pFormatedLine6, String pFormatedLine7, String pFormatedLine8, String pFormatedLine9) {
        this.sainAdr = pSainAdr;
        this.formatedLine1 = pFormatedLine1;
        this.formatedLine2 = pFormatedLine2;
        this.formatedLine3 = pFormatedLine3;
        this.formatedLine4 = pFormatedLine4;
        this.formatedLine5 = pFormatedLine5;
        this.formatedLine6 = pFormatedLine6;
        this.formatedLine7 = pFormatedLine7;
        this.formatedLine8 = pFormatedLine8;
        this.formatedLine9 = pFormatedLine9;
    }

    /**
     *
     * @return formatedLine1
     */
    public String getFormatedLine1() {
        return this.formatedLine1;
    }

    /**
     *
     * @param pFormatedLine1 formatedLine1 value
     */
    public void setFormatedLine1(String pFormatedLine1) {
        this.formatedLine1 = pFormatedLine1;
    }

    /**
     *
     * @return formatedLine2
     */
    public String getFormatedLine2() {
        return this.formatedLine2;
    }

    /**
     *
     * @param pFormatedLine2 formatedLine2 value
     */
    public void setFormatedLine2(String pFormatedLine2) {
        this.formatedLine2 = pFormatedLine2;
    }

    /**
     *
     * @return formatedLine3
     */
    public String getFormatedLine3() {
        return this.formatedLine3;
    }

    /**
     *
     * @param pFormatedLine3 formatedLine3 value
     */
    public void setFormatedLine3(String pFormatedLine3) {
        this.formatedLine3 = pFormatedLine3;
    }

    /**
     *
     * @return formatedLine4
     */
    public String getFormatedLine4() {
        return this.formatedLine4;
    }

    /**
     *
     * @param pFormatedLine4 formatedLine4 value
     */
    public void setFormatedLine4(String pFormatedLine4) {
        this.formatedLine4 = pFormatedLine4;
    }

    /**
     *
     * @return formatedLine5
     */
    public String getFormatedLine5() {
        return this.formatedLine5;
    }

    /**
     *
     * @param pFormatedLine5 formatedLine5 value
     */
    public void setFormatedLine5(String pFormatedLine5) {
        this.formatedLine5 = pFormatedLine5;
    }

    /**
     *
     * @return formatedLine6
     */
    public String getFormatedLine6() {
        return this.formatedLine6;
    }

    /**
     *
     * @param pFormatedLine6 formatedLine6 value
     */
    public void setFormatedLine6(String pFormatedLine6) {
        this.formatedLine6 = pFormatedLine6;
    }

    /**
     *
     * @return formatedLine7
     */
    public String getFormatedLine7() {
        return this.formatedLine7;
    }

    /**
     *
     * @param pFormatedLine7 formatedLine7 value
     */
    public void setFormatedLine7(String pFormatedLine7) {
        this.formatedLine7 = pFormatedLine7;
    }

    /**
     *
     * @return formatedLine8
     */
    public String getFormatedLine8() {
        return this.formatedLine8;
    }

    /**
     *
     * @param pFormatedLine8 formatedLine8 value
     */
    public void setFormatedLine8(String pFormatedLine8) {
        this.formatedLine8 = pFormatedLine8;
    }

    /**
     *
     * @return formatedLine9
     */
    public String getFormatedLine9() {
        return this.formatedLine9;
    }

    /**
     *
     * @param pFormatedLine9 formatedLine9 value
     */
    public void setFormatedLine9(String pFormatedLine9) {
        this.formatedLine9 = pFormatedLine9;
    }

    /**
     *
     * @return sainAdr
     */
    public String getSainAdr() {
        return this.sainAdr;
    }

    /**
     *
     * @param pSainAdr sainAdr value
     */
    public void setSainAdr(String pSainAdr) {
        this.sainAdr = pSainAdr;
    }

    /**
     *
     * @return object as string
     */
    public String toString() {
        String result = null;
        /*PROTECTED REGION ID(toString_cDkn4IEiEeCtut40RvtPWA) ENABLED START*/
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
            .append("sainAdr", getSainAdr())
            .append("formatedLine1", getFormatedLine1())
            .append("formatedLine2", getFormatedLine2())
            .append("formatedLine3", getFormatedLine3())
            .append("formatedLine4", getFormatedLine4())
            .append("formatedLine5", getFormatedLine5())
            .append("formatedLine6", getFormatedLine6())
            .append("formatedLine7", getFormatedLine7())
            .append("formatedLine8", getFormatedLine8())
            .append("formatedLine9", getFormatedLine9())
            .toString();
    }

     
    
    /*PROTECTED REGION ID(equals hash _cDkn4IEiEeCtut40RvtPWA) ENABLED START*/

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
        final FormalizedAdr other = (FormalizedAdr) obj;

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

    /*PROTECTED REGION ID(_cDkn4IEiEeCtut40RvtPWA u m) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/
    
}
