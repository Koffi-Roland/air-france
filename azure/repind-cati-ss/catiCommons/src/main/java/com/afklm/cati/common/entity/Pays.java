package com.afklm.cati.common.entity;

/*PROTECTED REGION ID(_s6164HvbEeCAmbGwtfTi3Q i) ENABLED START*/

// add not generated imports here

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/*PROTECTED REGION END*/


/**
 * <p>Title : Pays.java</p>
 * BO Pays
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
@Entity
@Table(name="PAYS")
public class Pays implements Serializable {

    /*PROTECTED REGION ID(serialUID _s6164HvbEeCAmbGwtfTi3Q) ENABLED START*/
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
     * codePays
     */
    @Id
    @Column(name="SCODE_PAYS")
    private String codePays;


    /**
     * libellePays
     */
    @Column(name="SLIBELLE_PAYS", length=30, nullable=false)
    private String libellePays;


    /**
     * codeIata
     */
    @Column(name="ICODE_IATA")
    private Integer codeIata;


    /**
     * libellePaysEn
     */
    @Column(name="SLIBELLE_PAYS_EN", length=30)
    private String libellePaysEn;


    /**
     * codeGestionCP
     */
    @Column(name="CODE_GESTION_CP", length=1)
    private String codeGestionCP;


    /**
     * codeCapitale
     */
    @Column(name="SCODE_CAPITALE", length=5)
    private String codeCapitale;


    /**
     * normalisable
     */
    @Column(name="NORMALISABLE", length=1, nullable=false)
    private String normalisable;


    /**
     * formatAdr
     */
    @Column(name="FORMAT_ADR", length=2)
    private String formatAdr;


    /**
     * iformatAdr
     */
    @Column(name="IFORMAT_ADR")
    private Integer iformatAdr;


    /**
     * forcage
     */
    @Column(name="SFORCAGE", length=1, nullable=false)
    private String forcage;

    /**
     * ISO3 code used by Experian for postal address normalization
     */
    @Column(name="SCODE_ISO3", length=3)
    private String iso3Code;

    /*PROTECTED REGION ID(_s6164HvbEeCAmbGwtfTi3Q u var) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/


    /**
     * default constructor
     */
    public Pays() {
    }


    /**
     * full constructor
     * @param pCodePays codePays
     * @param pLibellePays libellePays
     * @param pCodeIata codeIata
     * @param pLibellePaysEn libellePaysEn
     * @param pCodeGestionCP codeGestionCP
     * @param pCodeCapitale codeCapitale
     * @param pNormalisable normalisable
     * @param pFormatAdr formatAdr
     * @param pIformatAdr iformatAdr
     * @param pForcage forcage
     */
    public Pays(String pCodePays, String pLibellePays, Integer pCodeIata, String pLibellePaysEn, String pCodeGestionCP, String pCodeCapitale, String pNormalisable, String pFormatAdr, Integer pIformatAdr, String pForcage, String pIso3code) {
        this.codePays = pCodePays;
        this.libellePays = pLibellePays;
        this.codeIata = pCodeIata;
        this.libellePaysEn = pLibellePaysEn;
        this.codeGestionCP = pCodeGestionCP;
        this.codeCapitale = pCodeCapitale;
        this.normalisable = pNormalisable;
        this.formatAdr = pFormatAdr;
        this.iformatAdr = pIformatAdr;
        this.forcage = pForcage;
        this.iso3Code = pIso3code;
    }

    /**
     *
     * @return codeCapitale
     */
    public String getCodeCapitale() {
        return this.codeCapitale;
    }

    /**
     *
     * @param pCodeCapitale codeCapitale value
     */
    public void setCodeCapitale(String pCodeCapitale) {
        this.codeCapitale = pCodeCapitale;
    }

    /**
     *
     * @return codeGestionCP
     */
    public String getCodeGestionCP() {
        return this.codeGestionCP;
    }

    /**
     *
     * @param pCodeGestionCP codeGestionCP value
     */
    public void setCodeGestionCP(String pCodeGestionCP) {
        this.codeGestionCP = pCodeGestionCP;
    }

    /**
     *
     * @return codeIata
     */
    public Integer getCodeIata() {
        return this.codeIata;
    }

    /**
     *
     * @param pCodeIata codeIata value
     */
    public void setCodeIata(Integer pCodeIata) {
        this.codeIata = pCodeIata;
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
     * @return forcage
     */
    public String getForcage() {
        return this.forcage;
    }

    /**
     *
     * @param pForcage forcage value
     */
    public void setForcage(String pForcage) {
        this.forcage = pForcage;
    }

    /**
     *
     * @return formatAdr
     */
    public String getFormatAdr() {
        return this.formatAdr;
    }

    /**
     *
     * @param pFormatAdr formatAdr value
     */
    public void setFormatAdr(String pFormatAdr) {
        this.formatAdr = pFormatAdr;
    }

    /**
     *
     * @return iformatAdr
     */
    public Integer getIformatAdr() {
        return this.iformatAdr;
    }

    /**
     *
     * @param pIformatAdr iformatAdr value
     */
    public void setIformatAdr(Integer pIformatAdr) {
        this.iformatAdr = pIformatAdr;
    }

    /**
     *
     * @return libellePays
     */
    public String getLibellePays() {
        return this.libellePays;
    }

    /**
     *
     * @param pLibellePays libellePays value
     */
    public void setLibellePays(String pLibellePays) {
        this.libellePays = pLibellePays;
    }

    /**
     *
     * @return libellePaysEn
     */
    public String getLibellePaysEn() {
        return this.libellePaysEn;
    }

    /**
     *
     * @param pLibellePaysEn libellePaysEn value
     */
    public void setLibellePaysEn(String pLibellePaysEn) {
        this.libellePaysEn = pLibellePaysEn;
    }

    /**
     *
     * @return normalisable
     */
    public String getNormalisable() {
        return this.normalisable;
    }

    /**
     *
     * @param pNormalisable normalisable value
     */
    public void setNormalisable(String pNormalisable) {
        this.normalisable = pNormalisable;
    }

    /**
     *
     * @return object as string
     */
    public String toString() {
        String result = null;
        /*PROTECTED REGION ID(toString_s6164HvbEeCAmbGwtfTi3Q) ENABLED START*/
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
                .append("codePays", getCodePays())
                .append("libellePays", getLibellePays())
                .append("codeIata", getCodeIata())
                .append("libellePaysEn", getLibellePaysEn())
                .append("codeGestionCP", getCodeGestionCP())
                .append("codeCapitale", getCodeCapitale())
                .append("normalisable", getNormalisable())
                .append("formatAdr", getFormatAdr())
                .append("iformatAdr", getIformatAdr())
                .append("forcage", getForcage())
                .toString();
    }

    /**
     * isPaysNormalisable
     * @param codePays in String
     * @return The isPaysNormalisable as <code>Boolean</code>
     */
    public Boolean isPaysNormalisable(String codePays) {
        /*PROTECTED REGION ID(_h-pywHveEeCAmbGwtfTi3Q) ENABLED START*/
        return ("O".equalsIgnoreCase(this.normalisable));
        /*PROTECTED REGION END*/
    }



    /*PROTECTED REGION ID(equals hash _s6164HvbEeCAmbGwtfTi3Q) ENABLED START*/

    /**
     * {@inheritDoc}
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if(obj == null){
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Pays other = (Pays) obj;


        // if pks are both set, compare
        if (codePays != null) {
            if (other.codePays != null) {
                return new EqualsBuilder().append(codePays, other.codePays).isEquals();
            }
        }

        // Otherwise, compare significant fields

        return new  EqualsBuilder().
                append(codeIata, other.codeIata).
                append(codeCapitale, other.codeCapitale).
                isEquals();

    }

    /**
     * {@inheritDoc}
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        int result = new HashCodeBuilder(17, 31).
                append(codeIata).
                append(codeCapitale).
                toHashCode();

        return result;
    }

    /**
     *
     * @return iso3Code
     */
    public String getIso3Code() {
        return iso3Code;
    }

    /**
     *
     * @param pIso3Code iso3Code value
     */
    public void setIso3Code(String pIso3Code) {
        this.iso3Code = pIso3Code;
    }

    /*PROTECTED REGION END*/

    /*PROTECTED REGION ID(_s6164HvbEeCAmbGwtfTi3Q u m) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/

}
