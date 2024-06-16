package com.afklm.rigui.entity.profil;

/*PROTECTED REGION ID(_VBQ40DUdEeCq6pHdxM8RnQ i) ENABLED START*/

// add not generated imports here

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import java.io.Serializable;

/*PROTECTED REGION END*/


/**
 * <p>Title : Profil_af.java</p>
 * BO Profil_af
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
@Entity


@Table(name="PROFIL_AF")
public class Profil_af implements Serializable {

    /*PROTECTED REGION ID(serialUID _VBQ40DUdEeCq6pHdxM8RnQ) ENABLED START*/
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
     * icle_prf
     */
    @Id
    @Column(name="ICLE_PRF", length=10, nullable=false, unique=true)
    @GeneratedValue(generator = "foreignGeneratorPrf")
    @GenericGenerator(name = "foreignGeneratorPrf", strategy = "foreign", parameters = { @Parameter(name = "property", value = "profil_mere") })
    private Integer icle_prf;


    /**
     * smatricule
     */
    @Column(name="SMATRICULE", length=12)
    private String smatricule;


    /**
     * srang
     */
    @Column(name="SRANG", length=2)
    private String srang;


    /**
     * sadr_notes
     */
    @Column(name="SADR_NOTES", length=60)
    private String sadr_notes;


    /**
     * spasswrd
     */
    @Column(name="SPASSWRD", length=10)
    private String spasswrd;


    /**
     * snom_prf_hab
     */
    @Column(name="SNOM_PRF_HAB", length=30)
    private String snom_prf_hab;


    /**
     * sfonction
     */
    @Column(name="SFONCTION", length=3)
    private String sfonction;


    /**
     * sreference_r
     */
    @Column(name="SREFERENCE_R", length=7)
    private String sreference_r;


    /**
     * stypologie
     */
    @Column(name="STYPOLOGIE", length=4)
    private String stypologie;


    /**
     * scode_origine
     */
    @Column(name="SCODE_ORIGINE", length=6)
    private String scode_origine;


    /**
     * scode_cie
     */
    @Column(name="SCODE_CIE", length=3)
    private String scode_cie;


    /**
     * scode_status
     */
    @Column(name="SCODE_STATUT", length=6)
    private String scode_status;

    /**
     * profil_mere
     */
    // 1 <-> 1
    @OneToOne(mappedBy="profil_af", fetch=FetchType.LAZY)
    private Profil_mere profil_mere;

    /*PROTECTED REGION ID(_VBQ40DUdEeCq6pHdxM8RnQ u var) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/

    /**
     * default constructor 
     */
    public Profil_af() {
    }

    /**
     * full constructor
     * @param pIcle_prf icle_prf
     * @param pSmatricule smatricule
     * @param pSrang srang
     * @param pSadr_notes sadr_notes
     * @param pSpasswrd spasswrd
     * @param pSnom_prf_hab snom_prf_hab
     * @param pSfonction sfonction
     * @param pSreference_r sreference_r
     * @param pStypologie stypologie
     * @param pScode_origine scode_origine
     * @param pScode_cie scode_cie
     * @param pScode_status scode_status
     */
    public Profil_af(Integer pIcle_prf, String pSmatricule, String pSrang, String pSadr_notes, String pSpasswrd, String pSnom_prf_hab, String pSfonction, String pSreference_r, String pStypologie, String pScode_origine, String pScode_cie, String pScode_status) {
        this.icle_prf = pIcle_prf;
        this.smatricule = pSmatricule;
        this.srang = pSrang;
        this.sadr_notes = pSadr_notes;
        this.spasswrd = pSpasswrd;
        this.snom_prf_hab = pSnom_prf_hab;
        this.sfonction = pSfonction;
        this.sreference_r = pSreference_r;
        this.stypologie = pStypologie;
        this.scode_origine = pScode_origine;
        this.scode_cie = pScode_cie;
        this.scode_status = pScode_status;
    }

    /**
     *
     * @return icle_prf
     */
    public Integer getIcle_prf() {
        return this.icle_prf;
    }

    /**
     *
     * @param pIcle_prf icle_prf value
     */
    public void setIcle_prf(Integer pIcle_prf) {
        this.icle_prf = pIcle_prf;
    }

    /**
     *
     * @return sadr_notes
     */
    public String getSadr_notes() {
        return this.sadr_notes;
    }

    /**
     *
     * @param pSadr_notes sadr_notes value
     */
    public void setSadr_notes(String pSadr_notes) {
        this.sadr_notes = pSadr_notes;
    }

    /**
     *
     * @return scode_cie
     */
    public String getScode_cie() {
        return this.scode_cie;
    }

    /**
     *
     * @param pScode_cie scode_cie value
     */
    public void setScode_cie(String pScode_cie) {
        this.scode_cie = pScode_cie;
    }

    /**
     *
     * @return scode_origine
     */
    public String getScode_origine() {
        return this.scode_origine;
    }

    /**
     *
     * @param pScode_origine scode_origine value
     */
    public void setScode_origine(String pScode_origine) {
        this.scode_origine = pScode_origine;
    }

    /**
     *
     * @return scode_status
     */
    public String getScode_status() {
        return this.scode_status;
    }

    /**
     *
     * @param pScode_status scode_status value
     */
    public void setScode_status(String pScode_status) {
        this.scode_status = pScode_status;
    }

    /**
     *
     * @return sfonction
     */
    public String getSfonction() {
        return this.sfonction;
    }

    /**
     *
     * @param pSfonction sfonction value
     */
    public void setSfonction(String pSfonction) {
        this.sfonction = pSfonction;
    }

    /**
     *
     * @return smatricule
     */
    public String getSmatricule() {
        return this.smatricule;
    }

    /**
     *
     * @param pSmatricule smatricule value
     */
    public void setSmatricule(String pSmatricule) {
        this.smatricule = pSmatricule;
    }

    /**
     *
     * @return snom_prf_hab
     */
    public String getSnom_prf_hab() {
        return this.snom_prf_hab;
    }

    /**
     *
     * @param pSnom_prf_hab snom_prf_hab value
     */
    public void setSnom_prf_hab(String pSnom_prf_hab) {
        this.snom_prf_hab = pSnom_prf_hab;
    }

    /**
     *
     * @return spasswrd
     */
    public String getSpasswrd() {
        return this.spasswrd;
    }

    /**
     *
     * @param pSpasswrd spasswrd value
     */
    public void setSpasswrd(String pSpasswrd) {
        this.spasswrd = pSpasswrd;
    }

    /**
     *
     * @return srang
     */
    public String getSrang() {
        return this.srang;
    }

    /**
     *
     * @param pSrang srang value
     */
    public void setSrang(String pSrang) {
        this.srang = pSrang;
    }

    /**
     *
     * @return sreference_r
     */
    public String getSreference_r() {
        return this.sreference_r;
    }

    /**
     *
     * @param pSreference_r sreference_r value
     */
    public void setSreference_r(String pSreference_r) {
        this.sreference_r = pSreference_r;
    }

    /**
     *
     * @return stypologie
     */
    public String getStypologie() {
        return this.stypologie;
    }

    /**
     *
     * @param pStypologie stypologie value
     */
    public void setStypologie(String pStypologie) {
        this.stypologie = pStypologie;
    }

    /**
     * @return the profil_mere
     */
    public Profil_mere getProfil_mere() {
        return profil_mere;
    }

    /**
     * @param profil_mere the profil_mere to set
     */
    public void setProfil_mere(Profil_mere profil_mere) {
        this.profil_mere = profil_mere;
    }

    /**
     *
     * @return object as string
     */
    public String toString() {
        String result = null;
        /*PROTECTED REGION ID(toString_VBQ40DUdEeCq6pHdxM8RnQ) ENABLED START*/
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
        buffer.append("icle_prf=").append(getIcle_prf());
        buffer.append(",");
        buffer.append("smatricule=").append(getSmatricule());
        buffer.append(",");
        buffer.append("srang=").append(getSrang());
        buffer.append(",");
        buffer.append("sadr_notes=").append(getSadr_notes());
        buffer.append(",");
        buffer.append("spasswrd=").append(getSpasswrd());
        buffer.append(",");
        buffer.append("snom_prf_hab=").append(getSnom_prf_hab());
        buffer.append(",");
        buffer.append("sfonction=").append(getSfonction());
        buffer.append(",");
        buffer.append("sreference_r=").append(getSreference_r());
        buffer.append(",");
        buffer.append("stypologie=").append(getStypologie());
        buffer.append(",");
        buffer.append("scode_origine=").append(getScode_origine());
        buffer.append(",");
        buffer.append("scode_cie=").append(getScode_cie());
        buffer.append(",");
        buffer.append("scode_status=").append(getScode_status());
        buffer.append("]");
        return buffer.toString();
    }



    /*PROTECTED REGION ID(equals hash _VBQ40DUdEeCq6pHdxM8RnQ) ENABLED START*/

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
        final Profil_af other = (Profil_af) obj;

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

    /*PROTECTED REGION ID(_VBQ40DUdEeCq6pHdxM8RnQ u m) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/

}
