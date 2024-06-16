package com.afklm.rigui.entity.reference;

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


@Table(name="LANGUES")
public class RefLanguage implements Serializable {

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
     * languageCode
     */
    @Id
    @Column(name="SCODE_LANGUE", length=2)
    private String languageCode;


    /**
     * labelFR
     */
    @Column(name="SLIBELLE_LANGUE", length=20)
    private String labelFR;


    /**
     * labelEN
     */
    @Column(name="SLIBELLE_LANGUE_EN", length=20)
    private String labelEN;


    /**
     * iataCode
     */
    @Column(name="SCODE_IATA", length=3)
    private String iataCode;

    /*PROTECTED REGION ID(_uCxyYE4jEeS-eLH--0fARw u var) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/

    /**
     * default constructor 
     */
    public RefLanguage() {
    }

    /**
     * full constructor
     * @param pLanguageCode languageCode
     * @param pLabelFR labelFR
     * @param pLabelEN labelEN
     * @param pIataCode iataCode
     */
    public RefLanguage(String pLanguageCode, String pLabelFR, String pLabelEN, String pIataCode) {
        this.languageCode = pLanguageCode;
        this.labelFR = pLabelFR;
        this.labelEN = pLabelEN;
        this.iataCode = pIataCode;
    }

    /**
     *
     * @return iataCode
     */
    public String getIataCode() {
        return this.iataCode;
    }

    /**
     *
     * @param pIataCode iataCode value
     */
    public void setIataCode(String pIataCode) {
        this.iataCode = pIataCode;
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
    public String getLanguageCode() {
        return this.languageCode;
    }

    /**
     *
     * @param pLanguageCode languageCode value
     */
    public void setLanguageCode(String pLanguageCode) {
        this.languageCode = pLanguageCode;
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
        buffer.append("languageCode=").append(getLanguageCode());
        buffer.append(",");
        buffer.append("labelFR=").append(getLabelFR());
        buffer.append(",");
        buffer.append("labelEN=").append(getLabelEN());
        buffer.append(",");
        buffer.append("iataCode=").append(getIataCode());
        buffer.append("]");
        return buffer.toString();
    }



    /*PROTECTED REGION ID(equals hash _uCxyYE4jEeS-eLH--0fARw) ENABLED START*/

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

    /*PROTECTED REGION ID(_uCxyYE4jEeS-eLH--0fARw u m) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/

}
