package com.afklm.rigui.entity.reference;

/*PROTECTED REGION ID(_72IX4H1vEeCAmbGwtfTi3Q i) ENABLED START*/

// add not generated imports here

import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.ForeignKey;

import javax.persistence.*;
import java.io.Serializable;

/*PROTECTED REGION END*/


/**
 * <p>Title : RefProvince.java</p>
 * BO RefProvince
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
@Entity


@Table(name="REF_PROVINCE")
public class RefProvince implements Serializable {

    /*PROTECTED REGION ID(serialUID _72IX4H1vEeCAmbGwtfTi3Q) ENABLED START*/
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
     * cle
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ISEQ_REF_PROVINCE")
    @SequenceGenerator(name="ISEQ_REF_PROVINCE", sequenceName = "ISEQ_REF_PROVINCE", allocationSize = 1)
    @Column(name="ICLE", nullable=false, unique=true, updatable=false)
    private Integer cle;


    /**
     * code
     */
    @Column(name="SCODE", nullable=false)
    private String code;


    /**
     * libelle
     */
    @Column(name="SLIBELLE")
    private String libelle;


    /**
     * libelleEn
     */
    @Column(name="SLIBELLE_EN")
    private String libelleEn;


    /**
     * codePays
     */
    @Column(name="SCODE_PAYS", nullable=false, updatable=false, insertable=false)
    private String codePays;


    /**
     * pays
     */
    // * -> 1
    @ManyToOne()
    @JoinColumn(name="SCODE_PAYS", nullable=false, foreignKey = @javax.persistence.ForeignKey(name = "FK_REF_PROVINCE_SCODE_PAYS"))
    private Pays pays;

    /*PROTECTED REGION ID(_72IX4H1vEeCAmbGwtfTi3Q u var) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/


    /**
     * default constructor 
     */
    public RefProvince() {
    }


    /**
     * full constructor
     * @param pCle cle
     * @param pCode code
     * @param pLibelle libelle
     * @param pLibelleEn libelleEn
     * @param pCodePays codePays
     */
    public RefProvince(Integer pCle, String pCode, String pLibelle, String pLibelleEn, String pCodePays) {
        this.cle = pCle;
        this.code = pCode;
        this.libelle = pLibelle;
        this.libelleEn = pLibelleEn;
        this.codePays = pCodePays;
    }

    /**
     *
     * @return cle
     */
    public Integer getCle() {
        return this.cle;
    }

    /**
     *
     * @param pCle cle value
     */
    public void setCle(Integer pCle) {
        this.cle = pCle;
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
     * @return codePays
     */
    public String getCodePays() {
        return this.codePays;
    }

    /**
     *
     * @param pCodePays codePays value
     */
    public void setCodePays(String pCodePays) {
        this.codePays = pCodePays;
    }

    /**
     *
     * @return libelle
     */
    public String getLibelle() {
        return this.libelle;
    }

    /**
     *
     * @param pLibelle libelle value
     */
    public void setLibelle(String pLibelle) {
        this.libelle = pLibelle;
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
     * @return pays
     */
    public Pays getPays() {
        return this.pays;
    }

    /**
     *
     * @param pPays pays value
     */
    public void setPays(Pays pPays) {
        this.pays = pPays;
    }

    /**
     *
     * @return object as string
     */
    public String toString() {
        String result = null;
        /*PROTECTED REGION ID(toString_72IX4H1vEeCAmbGwtfTi3Q) ENABLED START*/
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
                .append("cle", getCle())
                .append("code", getCode())
                .append("libelle", getLibelle())
                .append("libelleEn", getLibelleEn())
                .append("codePays", getCodePays())
                .toString();
    }



    /*PROTECTED REGION ID(equals hash _72IX4H1vEeCAmbGwtfTi3Q) ENABLED START*/

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
        final RefProvince other = (RefProvince) obj;

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

    /*PROTECTED REGION ID(_72IX4H1vEeCAmbGwtfTi3Q u m) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/

}
