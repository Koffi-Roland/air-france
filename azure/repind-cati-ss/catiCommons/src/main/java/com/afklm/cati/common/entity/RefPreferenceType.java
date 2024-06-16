package com.afklm.cati.common.entity;

/*PROTECTED REGION ID(_3j-R8HBCEeeA-oB3G9fmBA i) ENABLED START*/

// add not generated imports here

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/*PROTECTED REGION END*/


/**
 * <p>Title : RefPreferenceType.java</p>
 * BO RefPreferenceType
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
@Entity


@Table(name="REF_PREFERENCE_TYPE")
public class RefPreferenceType implements Serializable {

    /*PROTECTED REGION ID(serialUID _3j-R8HBCEeeA-oB3G9fmBA) ENABLED START*/
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
    @Column(name="SCODE", length=3)
    private String code;


    /**
     * libelleFR
     */
    @Column(name="SLIBELLE_FR", length=25)
    private String libelleFR;


    /**
     * libelleEN
     */
    @Column(name="SLIBELLE_EN", length=25)
    private String libelleEN;

    /*PROTECTED REGION ID(_3j-R8HBCEeeA-oB3G9fmBA u var) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/

    /**
     * default constructor
     */
    public RefPreferenceType() {
        //empty constructor
    }

    /**
     * full constructor
     * @param pCode code
     * @param pLibelleFR libelleFR
     * @param pLibelleEN libelleEN
     */
    public RefPreferenceType(String pCode, String pLibelleFR, String pLibelleEN) {
        this.code = pCode;
        this.libelleFR = pLibelleFR;
        this.libelleEN = pLibelleEN;
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
     * @return libelleEN
     */
    public String getLibelleEN() {
        return this.libelleEN;
    }

    /**
     *
     * @param pLibelleEN libelleEN value
     */
    public void setLibelleEN(String pLibelleEN) {
        this.libelleEN = pLibelleEN;
    }

    /**
     *
     * @return libelleFR
     */
    public String getLibelleFR() {
        return this.libelleFR;
    }

    /**
     *
     * @param pLibelleFR libelleFR value
     */
    public void setLibelleFR(String pLibelleFR) {
        this.libelleFR = pLibelleFR;
    }

    /**
     *
     * @return object as string
     */
    public String toString() {
        String result = null;
        /*PROTECTED REGION ID(toString_3j-R8HBCEeeA-oB3G9fmBA) ENABLED START*/
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
        buffer.append("libelleFR=").append(getLibelleFR());
        buffer.append(",");
        buffer.append("libelleEN=").append(getLibelleEN());
        buffer.append("]");
        return buffer.toString();
    }



    /*PROTECTED REGION ID(equals hash _3j-R8HBCEeeA-oB3G9fmBA) ENABLED START*/

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

    /*PROTECTED REGION ID(_3j-R8HBCEeeA-oB3G9fmBA u m) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/
}
