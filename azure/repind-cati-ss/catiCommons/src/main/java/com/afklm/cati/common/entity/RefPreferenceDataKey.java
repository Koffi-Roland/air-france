package com.afklm.cati.common.entity;

/*PROTECTED REGION ID(_W89e4Hz4EeeM7eE6bvH4Tw i) ENABLED START*/

// add not generated imports here

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/*PROTECTED REGION END*/


/**
 * <p>Title : RefPreferenceDataKey.java</p>
 * BO RefPreferenceDataKey
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
@Entity


@Table(name="REF_PREFERENCE_DATA_KEY")
public class RefPreferenceDataKey implements Serializable {

    /*PROTECTED REGION ID(serialUID _W89e4Hz4EeeM7eE6bvH4Tw) ENABLED START*/
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
    @Column(name="SCODE")
    private String code;


    /**
     * libelleFr
     */
    @Column(name="SLIBELLE_FR")
    private String libelleFr;


    /**
     * libelleEn
     */
    @Column(name="SLIBELLE_EN")
    private String libelleEn;


    /**
     * normalizedKey
     */
    @Column(name="NORMALIZED_KEY")
    private String normalizedKey;

    /*PROTECTED REGION ID(_W89e4Hz4EeeM7eE6bvH4Tw u var) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/

    /**
     * default constructor
     */
    public RefPreferenceDataKey() {
        //empty constructor
    }

    /**
     * full constructor
     * @param pCode code
     * @param pLibelleFr libelleFr
     * @param pLibelleEn libelleEn
     * @param pNormalizedKey normalizedKey
     */
    public RefPreferenceDataKey(String pCode, String pLibelleFr, String pLibelleEn, String pNormalizedKey) {
        this.code = pCode;
        this.libelleFr = pLibelleFr;
        this.libelleEn = pLibelleEn;
        this.normalizedKey = pNormalizedKey;
    }

    /**
     *
     * @return code
     */
    public String getCode() {
        return this.code;
    }

    /**
     *
     * @param pCode code value
     */
    public void setCode(String pCode) {
        this.code = pCode;
    }

    /**
     *
     * @return libelleEn
     */
    public String getLibelleEn() {
        return this.libelleEn;
    }

    /**
     *
     * @param pLibelleEn libelleEn value
     */
    public void setLibelleEn(String pLibelleEn) {
        this.libelleEn = pLibelleEn;
    }

    /**
     *
     * @return libelleFr
     */
    public String getLibelleFr() {
        return this.libelleFr;
    }

    /**
     *
     * @param pLibelleFr libelleFr value
     */
    public void setLibelleFr(String pLibelleFr) {
        this.libelleFr = pLibelleFr;
    }

    /**
     *
     * @return normalizedKey
     */
    public String getNormalizedKey() {
        return this.normalizedKey;
    }

    /**
     *
     * @param pNormalizedKey normalizedKey value
     */
    public void setNormalizedKey(String pNormalizedKey) {
        this.normalizedKey = pNormalizedKey;
    }

    /**
     *
     * @return object as string
     */
    public String toString() {
        String result = null;
        /*PROTECTED REGION ID(toString_W89e4Hz4EeeM7eE6bvH4Tw) ENABLED START*/
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
        buffer.append("libelleFr=").append(getLibelleFr());
        buffer.append(",");
        buffer.append("libelleEn=").append(getLibelleEn());
        buffer.append(",");
        buffer.append("normalizedKey=").append(getNormalizedKey());
        buffer.append("]");
        return buffer.toString();
    }



    /*PROTECTED REGION ID(equals hash _W89e4Hz4EeeM7eE6bvH4Tw) ENABLED START*/

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

    /*PROTECTED REGION ID(_W89e4Hz4EeeM7eE6bvH4Tw u m) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/

}
