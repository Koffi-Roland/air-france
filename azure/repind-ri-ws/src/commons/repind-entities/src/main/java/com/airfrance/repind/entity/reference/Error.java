package com.airfrance.repind.entity.reference;

/*PROTECTED REGION ID(_mzH18BeIEeKJFbgRY_ODIg i) ENABLED START*/

// add not generated imports here

import org.apache.commons.lang.builder.ToStringBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/*PROTECTED REGION END*/


/**
 * <p>Title : Error.java</p>
 * BO Error
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
@Entity


@Table(name="REF_ERREUR")
public class Error implements Serializable {

/*PROTECTED REGION ID(serialUID _mzH18BeIEeKJFbgRY_ODIg) ENABLED START*/
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
     * errorCode
     */
    @Id
    @Column(name="SCODE")
    private String errorCode;
        
            
    /**
     * labelFR
     */
    @Column(name="SLIBELLE", length=70)
    private String labelFR;
        
            
    /**
     * labelUK
     */
    @Column(name="SLIBELLE_EN", length=70)
    private String labelUK;
        
    /*PROTECTED REGION ID(_mzH18BeIEeKJFbgRY_ODIg u var) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/
    
    
    /** 
     * default constructor 
     */
    public Error() {
    }
    
        
    /** 
     * full constructor
     * @param pErrorCode errorCode
     * @param pLabelFR labelFR
     * @param pLabelUK labelUK
     */
    public Error(String pErrorCode, String pLabelFR, String pLabelUK) {
        this.errorCode = pErrorCode;
        this.labelFR = pLabelFR;
        this.labelUK = pLabelUK;
    }

    /**
     *
     * @return errorCode
     */
    public String getErrorCode() {
        return this.errorCode;
    }

    /**
     *
     * @param pErrorCode errorCode value
     */
    public void setErrorCode(String pErrorCode) {
        this.errorCode = pErrorCode;
    }

    /**
     *
     * @return labelFR
     */
    public String getLabelFR() {
        return this.labelFR;
    }

    /**
     *
     * @param pLabelFR labelFR value
     */
    public void setLabelFR(String pLabelFR) {
        this.labelFR = pLabelFR;
    }

    /**
     *
     * @return labelUK
     */
    public String getLabelUK() {
        return this.labelUK;
    }

    /**
     *
     * @param pLabelUK labelUK value
     */
    public void setLabelUK(String pLabelUK) {
        this.labelUK = pLabelUK;
    }

    /**
     *
     * @return object as string
     */
    public String toString() {
        String result = null;
        /*PROTECTED REGION ID(toString_mzH18BeIEeKJFbgRY_ODIg) ENABLED START*/
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
            .append("errorCode", getErrorCode())
            .append("labelFR", getLabelFR())
            .append("labelUK", getLabelUK())
            .toString();
    }

     
    
    /*PROTECTED REGION ID(equals hash _mzH18BeIEeKJFbgRY_ODIg) ENABLED START*/

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
        final Error other = (Error) obj;

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

    /*PROTECTED REGION ID(_mzH18BeIEeKJFbgRY_ODIg u m) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/
    
}
