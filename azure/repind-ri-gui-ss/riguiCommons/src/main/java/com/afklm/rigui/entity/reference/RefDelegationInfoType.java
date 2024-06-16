package com.afklm.rigui.entity.reference;

/*PROTECTED REGION ID(_RI4bYHILEeeRrZw0c1ut0g i) ENABLED START*/

// add not generated imports here

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/*PROTECTED REGION END*/


/**
 * <p>Title : RefDelegationInfoType.java</p>
 * BO RefDelegationInfoType
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
@Entity


@Table(name="REF_DELEGATION_INFO_TYPE")
public class RefDelegationInfoType implements Serializable {

    /*PROTECTED REGION ID(serialUID _RI4bYHILEeeRrZw0c1ut0g) ENABLED START*/
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
     * libelle FR
     */
    @Column(name="SLIBELLE_FR")
    private String libelleFr;


    /**
     * libelle EN
     */
    @Column(name="SLIBELLE_EN")
    private String libelleEn;

    /*PROTECTED REGION ID(_RI4bYHILEeeRrZw0c1ut0g u var) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/

    /**
     * default constructor 
     */
    public RefDelegationInfoType() {
        //empty constructor
    }

    /**
     * full constructor
     */
    public RefDelegationInfoType(String code, String libelleFr, String libelleEn) {
        super();
        this.code = code;
        this.libelleFr = libelleFr;
        this.libelleEn = libelleEn;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLibelleFr() {
        return libelleFr;
    }

    public void setLibelleFr(String libelleFr) {
        this.libelleFr = libelleFr;
    }

    public String getLibelleEn() {
        return libelleEn;
    }

    public void setLibelleEn(String libelleEn) {
        this.libelleEn = libelleEn;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    /*PROTECTED REGION ID(equals hash _RI4bYHILEeeRrZw0c1ut0g) ENABLED START*/

    @Override
    public String toString() {
        return "RefDelegationInfoType [code=" + code + ", libelleFr=" + libelleFr + ", libelleEn=" + libelleEn + "]";
    }

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

    /*PROTECTED REGION ID(_RI4bYHILEeeRrZw0c1ut0g u m) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/

}
