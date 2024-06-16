package com.afklm.rigui.entity.adresse;

/*PROTECTED REGION ID(_0uME4DXeEeCq6pHdxM8RnQ i) ENABLED START*/

// add not generated imports here

import com.afklm.rigui.util.Identifiable;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import java.io.Serializable;

/*PROTECTED REGION END*/


/**
 * <p>Title : Usage_medium.java</p>
 * BO Usage_medium
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
@Entity


@Table(name="USAGE_MEDIUMS")
public class Usage_medium implements Serializable,Identifiable {

    /*PROTECTED REGION ID(serialUID _0uME4DXeEeCq6pHdxM8RnQ) ENABLED START*/
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
     * srin
     */
    @Id
    @GenericGenerator(name="ISEQ_USAGE_MEDIUMS", strategy = "com.afklm.rigui.util.StringSequenceGenerator",
            parameters = {
                    @Parameter(name = "sequence_name", value = "ISEQ_USAGE_MEDIUMS")
            })
    @GeneratedValue(generator = "ISEQ_USAGE_MEDIUMS")
    @Column(name="SRIN", length=16)
    private String srin;


    /**
     * sain_tel
     */
    @Column(length=16)
    private String sain_tel;


    /**
     * sain_email
     */
    @Column(length=16)
    private String sain_email;


    /**
     * scode_application
     */
    @Column(length=4)
    private String scode_application;


    /**
     * sain_adr
     */
    @Column(name="SAIN_ADR", length=16)
    private String sain_adr;


    /**
     * inum
     */
    @Column(nullable=false, updatable=false)
    private Integer inum;


    /**
     * scon
     */
    @Column(length=1)
    private String scon;


    /**
     * srole1
     */
    @Column(length=1)
    private String srole1;


    /**
     * srole2
     */
    @Column(length=1)
    private String srole2;


    /**
     * srole3
     */
    @Column(length=1)
    private String srole3;


    /**
     * srole4
     */
    @Column(length=1)
    private String srole4;


    /**
     * srole5
     */
    @Column(length=1)
    private String srole5;

    /*PROTECTED REGION ID(_0uME4DXeEeCq6pHdxM8RnQ u var) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/


    /**
     * default constructor 
     */
    public Usage_medium() {
    }


    /**
     * full constructor
     * @param pSrin srin
     * @param pSain_tel sain_tel
     * @param pSain_email sain_email
     * @param pScode_application scode_application
     * @param pSain_adr sain_adr
     * @param pInum inum
     * @param pScon scon
     * @param pSrole1 srole1
     * @param pSrole2 srole2
     * @param pSrole3 srole3
     * @param pSrole4 srole4
     * @param pSrole5 srole5
     */
    public Usage_medium(String pSrin, String pSain_tel, String pSain_email, String pScode_application, String pSain_adr, Integer pInum, String pScon, String pSrole1, String pSrole2, String pSrole3, String pSrole4, String pSrole5) {
        this.srin = pSrin;
        this.sain_tel = pSain_tel;
        this.sain_email = pSain_email;
        this.scode_application = pScode_application;
        this.sain_adr = pSain_adr;
        this.inum = pInum;
        this.scon = pScon;
        this.srole1 = pSrole1;
        this.srole2 = pSrole2;
        this.srole3 = pSrole3;
        this.srole4 = pSrole4;
        this.srole5 = pSrole5;
    }

    /**
     *
     * @return inum
     */
    public Integer getInum() {
        return this.inum;
    }

    /**
     *
     * @param pInum inum value
     */
    public void setInum(Integer pInum) {
        this.inum = pInum;
    }

    /**
     *
     * @return sain_adr
     */
    public String getSain_adr() {
        return this.sain_adr;
    }

    /**
     *
     * @param pSain_adr sain_adr value
     */
    public void setSain_adr(String pSain_adr) {
        this.sain_adr = pSain_adr;
    }

    /**
     *
     * @return sain_email
     */
    public String getSain_email() {
        return this.sain_email;
    }

    /**
     *
     * @param pSain_email sain_email value
     */
    public void setSain_email(String pSain_email) {
        this.sain_email = pSain_email;
    }

    /**
     *
     * @return sain_tel
     */
    public String getSain_tel() {
        return this.sain_tel;
    }

    /**
     *
     * @param pSain_tel sain_tel value
     */
    public void setSain_tel(String pSain_tel) {
        this.sain_tel = pSain_tel;
    }

    /**
     *
     * @return scode_application
     */
    public String getScode_application() {
        return this.scode_application;
    }

    /**
     *
     * @param pScode_application scode_application value
     */
    public void setScode_application(String pScode_application) {
        this.scode_application = pScode_application;
    }

    /**
     *
     * @return scon
     */
    public String getScon() {
        return this.scon;
    }

    /**
     *
     * @param pScon scon value
     */
    public void setScon(String pScon) {
        this.scon = pScon;
    }

    /**
     *
     * @return srin
     */
    public String getSrin() {
        return this.srin;
    }

    /**
     *
     * @param pSrin srin value
     */
    public void setSrin(String pSrin) {
        this.srin = pSrin;
    }

    /**
     *
     * @return srole1
     */
    public String getSrole1() {
        return this.srole1;
    }

    /**
     *
     * @param pSrole1 srole1 value
     */
    public void setSrole1(String pSrole1) {
        this.srole1 = pSrole1;
    }

    /**
     *
     * @return srole2
     */
    public String getSrole2() {
        return this.srole2;
    }

    /**
     *
     * @param pSrole2 srole2 value
     */
    public void setSrole2(String pSrole2) {
        this.srole2 = pSrole2;
    }

    /**
     *
     * @return srole3
     */
    public String getSrole3() {
        return this.srole3;
    }

    /**
     *
     * @param pSrole3 srole3 value
     */
    public void setSrole3(String pSrole3) {
        this.srole3 = pSrole3;
    }

    /**
     *
     * @return srole4
     */
    public String getSrole4() {
        return this.srole4;
    }

    /**
     *
     * @param pSrole4 srole4 value
     */
    public void setSrole4(String pSrole4) {
        this.srole4 = pSrole4;
    }

    /**
     *
     * @return srole5
     */
    public String getSrole5() {
        return this.srole5;
    }

    /**
     *
     * @param pSrole5 srole5 value
     */
    public void setSrole5(String pSrole5) {
        this.srole5 = pSrole5;
    }

    /**
     *
     * @return object as string
     */
    public String toString() {
        String result = null;
        /*PROTECTED REGION ID(toString_0uME4DXeEeCq6pHdxM8RnQ) ENABLED START*/
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
                .append("srin", getSrin())
                .append("sain_tel", getSain_tel())
                .append("sain_email", getSain_email())
                .append("scode_application", getScode_application())
                .append("sain_adr", getSain_adr())
                .append("inum", getInum())
                .append("scon", getScon())
                .append("srole1", getSrole1())
                .append("srole2", getSrole2())
                .append("srole3", getSrole3())
                .append("srole4", getSrole4())
                .append("srole5", getSrole5())
                .toString();
    }

    /**
     * existeRole
     * @param role in String
     * @return The existeRole as <code>boolean</code>
     */
    public boolean existeRole(String role) {
        /*PROTECTED REGION ID(_AwTzgIH9EeCtut40RvtPWA) ENABLED START*/
        boolean result = false;
        if (role!=null && role.length()>0) {
            if (getSrole1().equalsIgnoreCase(role))
                result = true;
            else if (getSrole2().equalsIgnoreCase(role))
                result = true;
            else if (getSrole3().equalsIgnoreCase(role))
                result = true;
            else if (getSrole4().equalsIgnoreCase(role))
                result = true;
            else if (getSrole5().equalsIgnoreCase(role))
                result = true;
        }
        return result;
        /*PROTECTED REGION END*/
    }



    /*PROTECTED REGION ID(equals hash _0uME4DXeEeCq6pHdxM8RnQ) ENABLED START*/

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
        final Usage_medium other = (Usage_medium) obj;

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


    @Override
    public String getId() {
        return srin;
    }

}
