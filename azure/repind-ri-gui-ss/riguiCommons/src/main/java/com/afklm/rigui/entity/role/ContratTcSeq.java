package com.afklm.rigui.entity.role;

/*PROTECTED REGION ID(_7qWL8Ld5EeSM_NEE6QydtQ i) ENABLED START*/

// add not generated imports here

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/*PROTECTED REGION END*/


/**
 * <p>Title : ContratTcSeq.java</p>
 * BO ContratTcSeq
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
@Entity


@Table(name="TYPE_CONTRAT_TC_SEQ")
public class ContratTcSeq implements Serializable {

    /*PROTECTED REGION ID(serialUID _7qWL8Ld5EeSM_NEE6QydtQ) ENABLED START*/
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
     * numeroTc
     */
    @Id
    @Column(name="SNUMERO_TC", length=4, nullable=false)
    private String numeroTc;


    /**
     * codeType
     */
    @Column(name="SCODE_TYPE", length=2, nullable=false)
    private String codeType;


    /**
     * nomTc
     */
    @Column(name="SNOM_TC", length=50)
    private String nomTc;


    /**
     * dateDebut
     */
    @Column(name="DDATE_DEBUT")
    private Date dateDebut;


    /**
     * dateFin
     */
    @Column(name="DDATE_FIN")
    private Date dateFin;


    /**
     * rolesAgence
     */
    // 1 <-> * 
    @OneToMany(mappedBy="contratTcSeq")
    private Set<RoleAgence> rolesAgence;

    /*PROTECTED REGION ID(_7qWL8Ld5EeSM_NEE6QydtQ u var) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/

    /**
     * default constructor 
     */
    public ContratTcSeq() {
    }

    /**
     * full constructor
     * @param pNumeroTc numeroTc
     * @param pCodeType codeType
     * @param pNomTc nomTc
     * @param pDateDebut dateDebut
     * @param pDateFin dateFin
     */
    public ContratTcSeq(String pNumeroTc, String pCodeType, String pNomTc, Date pDateDebut, Date pDateFin) {
        this.numeroTc = pNumeroTc;
        this.codeType = pCodeType;
        this.nomTc = pNomTc;
        this.dateDebut = pDateDebut;
        this.dateFin = pDateFin;
    }

    /**
     *
     * @return codeType
     */
    public String getCodeType() {
        return this.codeType;
    }

    /**
     *
     * @param pCodeType codeType value
     */
    public void setCodeType(String pCodeType) {
        this.codeType = pCodeType;
    }

    /**
     *
     * @return dateDebut
     */
    public Date getDateDebut() {
        return this.dateDebut;
    }

    /**
     *
     * @param pDateDebut dateDebut value
     */
    public void setDateDebut(Date pDateDebut) {
        this.dateDebut = pDateDebut;
    }

    /**
     *
     * @return dateFin
     */
    public Date getDateFin() {
        return this.dateFin;
    }

    /**
     *
     * @param pDateFin dateFin value
     */
    public void setDateFin(Date pDateFin) {
        this.dateFin = pDateFin;
    }

    /**
     *
     * @return nomTc
     */
    public String getNomTc() {
        return this.nomTc;
    }

    /**
     *
     * @param pNomTc nomTc value
     */
    public void setNomTc(String pNomTc) {
        this.nomTc = pNomTc;
    }

    /**
     *
     * @return numeroTc
     */
    public String getNumeroTc() {
        return this.numeroTc;
    }

    /**
     *
     * @param pNumeroTc numeroTc value
     */
    public void setNumeroTc(String pNumeroTc) {
        this.numeroTc = pNumeroTc;
    }

    /**
     *
     * @return rolesAgence
     */
    public Set<RoleAgence> getRolesAgence() {
        return this.rolesAgence;
    }

    /**
     *
     * @param pRolesAgence rolesAgence value
     */
    public void setRolesAgence(Set<RoleAgence> pRolesAgence) {
        this.rolesAgence = pRolesAgence;
    }

    /**
     *
     * @return object as string
     */
    public String toString() {
        String result = null;
        /*PROTECTED REGION ID(toString_7qWL8Ld5EeSM_NEE6QydtQ) ENABLED START*/
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
        buffer.append("numeroTc=").append(getNumeroTc());
        buffer.append(",");
        buffer.append("codeType=").append(getCodeType());
        buffer.append(",");
        buffer.append("nomTc=").append(getNomTc());
        buffer.append(",");
        buffer.append("dateDebut=").append(getDateDebut());
        buffer.append(",");
        buffer.append("dateFin=").append(getDateFin());
        buffer.append("]");
        return buffer.toString();
    }



    /*PROTECTED REGION ID(equals hash _7qWL8Ld5EeSM_NEE6QydtQ) ENABLED START*/

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

    /*PROTECTED REGION ID(_7qWL8Ld5EeSM_NEE6QydtQ u m) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/

}
