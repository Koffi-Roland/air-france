package com.airfrance.repind.entity.reference;

/*PROTECTED REGION ID(_uCxyYE4jEeS-eLH--0fARw i) ENABLED START*/

// add not generated imports here

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/*PROTECTED REGION END*/


/**
 * <p>Title : RefLanguage.java</p>
 * BO RefLanguage
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
@Entity


@Table(name="REF_HANDICAP_DATA_KEY")
public class RefHandicapDataKey implements Serializable {

/*PROTECTED REGION ID(serialUID _uCxyYE4jEeS-eLH--0fARw) ENABLED START*/
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
     * code
     */
    @Id
    @Column(name="SCODE", length=60)
    private String code;
        
            
    /**
     * labelFR
     */
    @Column(name="SLIBELLE", length=100)
    private String labelFR;
        
            
    /**
     * labelEN
     */
    @Column(name="SLIBELLE_EN", length=100)
    private String labelEN;
        
            
        
    /*PROTECTED REGION ID(_uCxyYE4jEeS-eLH--0fARw u var) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/
    
    /** 
     * default constructor 
     */
    public RefHandicapDataKey() {
    }
        
    /** 
     * full constructor
     * @param pLanguageCode languageCode
     * @param pLabelFR labelFR
     * @param pLabelEN labelEN
     * @param pIataCode iataCode
     */
    public RefHandicapDataKey(String pCode, String pLabelFR, String pLabelEN, String pIataCode) {
        this.code = pCode;
        this.labelFR = pLabelFR;
        this.labelEN = pLabelEN;
    }

    /**
     *
     * @return labelEN
     */
    public String getLabelEN() {
        return this.labelEN;
    }

    /**
     *
     * @param pLabelEN labelEN value
     */
    public void setLabelEN(String pLabelEN) {
        this.labelEN = pLabelEN;
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
     * @return languageCode
     */
    public String getCode() {
        return this.code;
    }

    /**
     *
     * @param pLanguageCode languageCode value
     */
    public void setCode(String pCode) {
        this.code = pCode;
    }

    /**
     *
     * @return object as string
     */
    public String toString() {
        String result = null;
        /*PROTECTED REGION ID(toString_uCxyYE4jEeS-eLH--0fARw) ENABLED START*/
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
        buffer.append("code=").append(getCode());
        buffer.append(",");
        buffer.append("labelFR=").append(getLabelFR());
        buffer.append(",");
        buffer.append("labelEN=").append(getLabelEN());
        buffer.append("]");
        return buffer.toString();
    }

     
    
    /*PROTECTED REGION ID(equals hash _uCxyYE4jEeS-eLH--0fARw) ENABLED START*/
    
    /**
     * {@inheritDoc}
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        //TODO Customize here if necessary
        return hashCodeImpl();
    }

    /**
     * {@inheritDoc}
     * @see java.lang.Object#equals(java.lang.Object)
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

    /*PROTECTED REGION ID(_uCxyYE4jEeS-eLH--0fARw u m) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/
    
}
