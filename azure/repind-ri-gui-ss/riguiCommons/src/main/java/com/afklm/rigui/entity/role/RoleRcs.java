package com.afklm.rigui.entity.role;

/*PROTECTED REGION ID(_q-trwAz5EeSYv7JdnOTzPw i) ENABLED START*/

// add not generated imports here

import org.hibernate.annotations.ForeignKey;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/*PROTECTED REGION END*/


/**
 * <p>Title : RoleRcs.java</p>
 * BO RoleRcs
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
@Entity


@Table(name="ROLE_RCS")
public class RoleRcs implements Serializable {

    /*PROTECTED REGION ID(serialUID _q-trwAz5EeSYv7JdnOTzPw) ENABLED START*/
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
    @GenericGenerator(name="ISEQ_ROLE_RCS", strategy = "com.afklm.rigui.util.StringSequenceGenerator",
            parameters = {
                    @Parameter(name = "sequence_name", value = "ISEQ_ROLE_RCS")
            })
    @GeneratedValue(generator = "ISEQ_ROLE_RCS")
    @Column(name="ICLE", nullable=false)
    private String cle;


    /**
     * type
     */
    @Column(name="STYPE", length=2)
    private String type;


    /**
     * sousType
     */
    @Column(name="SSOUS_TYPE", length=1)
    private String sousType;


    /**
     * familleTraitement
     */
    @Column(name="SFAMILLE_TRAITEMENT", length=1)
    private String familleTraitement;


    /**
     * cause
     */
    @Column(name="SCAUSE", length=12)
    private String cause;


    /**
     * numeroAffaire
     */
    @Column(name="SNUMERO_AFFAIRE", length=12, nullable=false, unique=true)
    private String numeroAffaire;


    /**
     * dateOuverture
     */
    @Column(name="DDATE_OUVERTURE")
    private Date dateOuverture;


    /**
     * dateFermeture
     */
    @Column(name="DDATE_FERMETURE")
    private Date dateFermeture;


    /**
     * dateCreation
     */
    @Column(name="DDATE_CREATION", nullable=false)
    private Date dateCreation;


    /**
     * signatureCreation
     */
    @Column(name="SSIGNATURE_CREATION", length=16, nullable=false)
    private String signatureCreation;


    /**
     * siteCreation
     */
    @Column(name="SSITE_CREATION", length=10, nullable=false)
    private String siteCreation;


    /**
     * dateModification
     */
    @Column(name="DDATE_MODIFICATION")
    private Date dateModification;


    /**
     * signatureModification
     */
    @Column(name="SSIGNATURE_MODIFICATION", length=16)
    private String signatureModification;


    /**
     * siteModification
     */
    @Column(name="SSITE_MODIFICATION", length=10)
    private String siteModification;


    /**
     * businessRole
     */
    // 1 <-> 1
    @OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="ICLE_ROLE", nullable=false, foreignKey = @javax.persistence.ForeignKey(name = "FK_BR_RCS"))
    private BusinessRole businessRole;

    /*PROTECTED REGION ID(_q-trwAz5EeSYv7JdnOTzPw u var) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/

    /**
     * default constructor 
     */
    public RoleRcs() {
    }

    /**
     * full constructor
     * @param pCle cle
     * @param pType type
     * @param pSousType sousType
     * @param pFamilleTraitement familleTraitement
     * @param pCause cause
     * @param pNumeroAffaire numeroAffaire
     * @param pDateOuverture dateOuverture
     * @param pDateFermeture dateFermeture
     * @param pDateCreation dateCreation
     * @param pSignatureCreation signatureCreation
     * @param pSiteCreation siteCreation
     * @param pDateModification dateModification
     * @param pSignatureModification signatureModification
     * @param pSiteModification siteModification
     */
    public RoleRcs(String pCle, String pType, String pSousType, String pFamilleTraitement, String pCause, String pNumeroAffaire, Date pDateOuverture, Date pDateFermeture, Date pDateCreation, String pSignatureCreation, String pSiteCreation, Date pDateModification, String pSignatureModification, String pSiteModification) {
        this.cle = pCle;
        this.type = pType;
        this.sousType = pSousType;
        this.familleTraitement = pFamilleTraitement;
        this.cause = pCause;
        this.numeroAffaire = pNumeroAffaire;
        this.dateOuverture = pDateOuverture;
        this.dateFermeture = pDateFermeture;
        this.dateCreation = pDateCreation;
        this.signatureCreation = pSignatureCreation;
        this.siteCreation = pSiteCreation;
        this.dateModification = pDateModification;
        this.signatureModification = pSignatureModification;
        this.siteModification = pSiteModification;
    }

    /**
     *
     * @return businessRole
     */
    public BusinessRole getBusinessRole() {
        return this.businessRole;
    }

    /**
     *
     * @param pBusinessRole businessRole value
     */
    public void setBusinessRole(BusinessRole pBusinessRole) {
        this.businessRole = pBusinessRole;
    }

    /**
     *
     * @return cause
     */
    public String getCause() {
        return this.cause;
    }

    /**
     *
     * @param pCause cause value
     */
    public void setCause(String pCause) {
        this.cause = pCause;
    }

    /**
     *
     * @return cle
     */
    public String getCle() {
        return this.cle;
    }

    /**
     *
     * @param pCle cle value
     */
    public void setCle(String pCle) {
        this.cle = pCle;
    }

    /**
     *
     * @return dateCreation
     */
    public Date getDateCreation() {
        return this.dateCreation;
    }

    /**
     *
     * @param pDateCreation dateCreation value
     */
    public void setDateCreation(Date pDateCreation) {
        this.dateCreation = pDateCreation;
    }

    /**
     *
     * @return dateFermeture
     */
    public Date getDateFermeture() {
        return this.dateFermeture;
    }

    /**
     *
     * @param pDateFermeture dateFermeture value
     */
    public void setDateFermeture(Date pDateFermeture) {
        this.dateFermeture = pDateFermeture;
    }

    /**
     *
     * @return dateModification
     */
    public Date getDateModification() {
        return this.dateModification;
    }

    /**
     *
     * @param pDateModification dateModification value
     */
    public void setDateModification(Date pDateModification) {
        this.dateModification = pDateModification;
    }

    /**
     *
     * @return dateOuverture
     */
    public Date getDateOuverture() {
        return this.dateOuverture;
    }

    /**
     *
     * @param pDateOuverture dateOuverture value
     */
    public void setDateOuverture(Date pDateOuverture) {
        this.dateOuverture = pDateOuverture;
    }

    /**
     *
     * @return familleTraitement
     */
    public String getFamilleTraitement() {
        return this.familleTraitement;
    }

    /**
     *
     * @param pFamilleTraitement familleTraitement value
     */
    public void setFamilleTraitement(String pFamilleTraitement) {
        this.familleTraitement = pFamilleTraitement;
    }

    /**
     *
     * @return numeroAffaire
     */
    public String getNumeroAffaire() {
        return this.numeroAffaire;
    }

    /**
     *
     * @param pNumeroAffaire numeroAffaire value
     */
    public void setNumeroAffaire(String pNumeroAffaire) {
        this.numeroAffaire = pNumeroAffaire;
    }

    /**
     *
     * @return signatureCreation
     */
    public String getSignatureCreation() {
        return this.signatureCreation;
    }

    /**
     *
     * @param pSignatureCreation signatureCreation value
     */
    public void setSignatureCreation(String pSignatureCreation) {
        this.signatureCreation = pSignatureCreation;
    }

    /**
     *
     * @return signatureModification
     */
    public String getSignatureModification() {
        return this.signatureModification;
    }

    /**
     *
     * @param pSignatureModification signatureModification value
     */
    public void setSignatureModification(String pSignatureModification) {
        this.signatureModification = pSignatureModification;
    }

    /**
     *
     * @return siteCreation
     */
    public String getSiteCreation() {
        return this.siteCreation;
    }

    /**
     *
     * @param pSiteCreation siteCreation value
     */
    public void setSiteCreation(String pSiteCreation) {
        this.siteCreation = pSiteCreation;
    }

    /**
     *
     * @return siteModification
     */
    public String getSiteModification() {
        return this.siteModification;
    }

    /**
     *
     * @param pSiteModification siteModification value
     */
    public void setSiteModification(String pSiteModification) {
        this.siteModification = pSiteModification;
    }

    /**
     *
     * @return sousType
     */
    public String getSousType() {
        return this.sousType;
    }

    /**
     *
     * @param pSousType sousType value
     */
    public void setSousType(String pSousType) {
        this.sousType = pSousType;
    }

    /**
     *
     * @return type
     */
    public String getType() {
        return this.type;
    }

    /**
     *
     * @param pType type value
     */
    public void setType(String pType) {
        this.type = pType;
    }

    /**
     *
     * @return object as string
     */
    public String toString() {
        String result = null;
        /*PROTECTED REGION ID(toString_q-trwAz5EeSYv7JdnOTzPw) ENABLED START*/
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
        buffer.append("cle=").append(getCle());
        buffer.append(",");
        buffer.append("type=").append(getType());
        buffer.append(",");
        buffer.append("sousType=").append(getSousType());
        buffer.append(",");
        buffer.append("familleTraitement=").append(getFamilleTraitement());
        buffer.append(",");
        buffer.append("cause=").append(getCause());
        buffer.append(",");
        buffer.append("numeroAffaire=").append(getNumeroAffaire());
        buffer.append(",");
        buffer.append("dateOuverture=").append(getDateOuverture());
        buffer.append(",");
        buffer.append("dateFermeture=").append(getDateFermeture());
        buffer.append(",");
        buffer.append("dateCreation=").append(getDateCreation());
        buffer.append(",");
        buffer.append("signatureCreation=").append(getSignatureCreation());
        buffer.append(",");
        buffer.append("siteCreation=").append(getSiteCreation());
        buffer.append(",");
        buffer.append("dateModification=").append(getDateModification());
        buffer.append(",");
        buffer.append("signatureModification=").append(getSignatureModification());
        buffer.append(",");
        buffer.append("siteModification=").append(getSiteModification());
        buffer.append("]");
        return buffer.toString();
    }



    /*PROTECTED REGION ID(equals hash _q-trwAz5EeSYv7JdnOTzPw) ENABLED START*/

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

    /*PROTECTED REGION ID(_q-trwAz5EeSYv7JdnOTzPw u m) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/

}
