package com.afklm.rigui.entity.adresse;

/*PROTECTED REGION ID(_2rProMWJEd-Z0rWwhyE6jg i) ENABLED START*/

// add not generated imports here

import com.afklm.rigui.util.Identifiable;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.ParamDef;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


/*PROTECTED REGION END*/


/**
 * <p>Title : Email.java</p>
 * BO Email
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
@Entity


@Table(name="EMAILS")
@FilterDef(name="emailStatus", defaultCondition=":param = SSTATUT_MEDIUM", parameters = {@ParamDef(name="param", type ="string")})
public class Email implements Serializable, Identifiable {

    /*PROTECTED REGION ID(serialUID _2rProMWJEd-Z0rWwhyE6jg) ENABLED START*/
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
     * sain
     */
    @Id
    @GenericGenerator(name="ISEQ_EMAILS", strategy = "com.afklm.rigui.util.StringSequenceGenerator",
            parameters = {
                    @Parameter(name = "sequence_name", value = "ISEQ_EMAILS")
            })
    @GeneratedValue(generator = "ISEQ_EMAILS")
    @Column(name="SAIN", length=16, nullable=false, unique=true, updatable=false)
    private String sain;


    /**
     * sgin
     */
    @Column(name="SGIN", length=12, updatable=false, insertable=true)
    private String sgin;


    /**
     * version
     */
    @Version
    @Column(name="IVERSION", length=12, nullable=false)
    private Integer version;


    /**
     * codeMedium
     */
    @Column(name="SCODE_MEDIUM", length=1, nullable=false)
    private String codeMedium;


    /**
     * statutMedium
     */
    @Column(name="SSTATUT_MEDIUM", length=1, nullable=false)
    private String statutMedium;


    /**
     * email
     */
    @Column(name="SEMAIL", length=60, nullable=false)
    private String email;


    /**
     * descriptifComplementaire
     */
    @Column(name="SDESCRIPTIF_COMPLEMENTAIRE", length=30)
    private String descriptifComplementaire;


    /**
     * autorisationMailing
     */
    @Column(name="SAUTORISATION_MAILING", length=1, nullable=false)
    private String autorisationMailing;


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
     * dateModification
     */
    @Column(name="DDATE_MODIFICATION")
    private Date dateModification;


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
     * dateCreation
     */
    @Column(name="DDATE_CREATION")
    private Date dateCreation;


    /**
     * cleRole
     */
    @Column(name="ICLE_ROLE")
    private Integer cleRole;


    /**
     * cleTemp
     */
    @Column(name="IKEY_TEMP")
    private Integer cleTemp;




    /**
     * default constructor 
     */
    public Email() {
    }

    /**
     * full constructor
     * @param pSain sain
     * @param pSgin sgin
     * @param pVersion version
     * @param pCodeMedium codeMedium
     * @param pStatutMedium statutMedium
     * @param pEmail email
     * @param pDescriptifComplementaire descriptifComplementaire
     * @param pAutorisationMailing autorisationMailing
     * @param pSignatureModification signatureModification
     * @param pSiteModification siteModification
     * @param pDateModification dateModification
     * @param pSignatureCreation signatureCreation
     * @param pSiteCreation siteCreation
     * @param pDateCreation dateCreation
     * @param pCleRole cleRole
     * @param pCleTemp cleTemp
     */
    public Email(String pSain, String pSgin, Integer pVersion, String pCodeMedium, String pStatutMedium, String pEmail, String pDescriptifComplementaire, String pAutorisationMailing, String pSignatureModification, String pSiteModification, Date pDateModification, String pSignatureCreation, String pSiteCreation, Date pDateCreation, Integer pCleRole, Integer pCleTemp) {
        this.sain = pSain;
        this.sgin = pSgin;
        this.version = pVersion;
        this.codeMedium = pCodeMedium;
        this.statutMedium = pStatutMedium;
        this.email = pEmail;
        this.descriptifComplementaire = pDescriptifComplementaire;
        this.autorisationMailing = pAutorisationMailing;
        this.signatureModification = pSignatureModification;
        this.siteModification = pSiteModification;
        this.dateModification = pDateModification;
        this.signatureCreation = pSignatureCreation;
        this.siteCreation = pSiteCreation;
        this.dateCreation = pDateCreation;
        this.cleRole = pCleRole;
        this.cleTemp = pCleTemp;
    }

    /**
     *
     * @return autorisationMailing
     */
    public String getAutorisationMailing() {
        return this.autorisationMailing;
    }

    /**
     *
     * @param pAutorisationMailing autorisationMailing value
     */
    public void setAutorisationMailing(String pAutorisationMailing) {
        this.autorisationMailing = pAutorisationMailing;
    }

    /**
     *
     * @return cleRole
     */
    public Integer getCleRole() {
        return this.cleRole;
    }

    /**
     *
     * @param pCleRole cleRole value
     */
    public void setCleRole(Integer pCleRole) {
        this.cleRole = pCleRole;
    }

    /**
     *
     * @return cleTemp
     */
    public Integer getCleTemp() {
        return this.cleTemp;
    }

    /**
     *
     * @param pCleTemp cleTemp value
     */
    public void setCleTemp(Integer pCleTemp) {
        this.cleTemp = pCleTemp;
    }

    /**
     *
     * @return codeMedium
     */
    public String getCodeMedium() {
        return this.codeMedium;
    }

    /**
     *
     * @param pCodeMedium codeMedium value
     */
    public void setCodeMedium(String pCodeMedium) {
        this.codeMedium = pCodeMedium;
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
     * @return descriptifComplementaire
     */
    public String getDescriptifComplementaire() {
        return this.descriptifComplementaire;
    }

    /**
     *
     * @param pDescriptifComplementaire descriptifComplementaire value
     */
    public void setDescriptifComplementaire(String pDescriptifComplementaire) {
        this.descriptifComplementaire = pDescriptifComplementaire;
    }

    /**
     *
     * @return email
     */
    public String getEmail() {
        return this.email;
    }

    /**
     *
     * @param pEmail email value
     */
    public void setEmail(String pEmail) {
        this.email = pEmail;
    }








    /**
     *
     * @return sain
     */
    public String getSain() {
        return this.sain;
    }

    /**
     *
     * @param pSain sain value
     */
    public void setSain(String pSain) {
        this.sain = pSain;
    }

    /**
     *
     * @return sgin
     */
    public String getSgin() {
        return this.sgin;
    }

    /**
     *
     * @param pSgin sgin value
     */
    public void setSgin(String pSgin) {
        this.sgin = pSgin;
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
     * @return statutMedium
     */
    public String getStatutMedium() {
        return this.statutMedium;
    }

    /**
     *
     * @param pStatutMedium statutMedium value
     */
    public void setStatutMedium(String pStatutMedium) {
        this.statutMedium = pStatutMedium;
    }

    /**
     *
     * @return version
     */
    public Integer getVersion() {
        return this.version;
    }

    /**
     *
     * @param pVersion version value
     */
    public void setVersion(Integer pVersion) {
        this.version = pVersion;
    }

    /**
     *
     * @return object as string
     */
    public String toString() {
        String result = null;
        /*PROTECTED REGION ID(toString_2rProMWJEd-Z0rWwhyE6jg) ENABLED START*/
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
        buffer.append("sain=").append(getSain());
        buffer.append(",");
        buffer.append("sgin=").append(getSgin());
        buffer.append(",");
        buffer.append("version=").append(getVersion());
        buffer.append(",");
        buffer.append("codeMedium=").append(getCodeMedium());
        buffer.append(",");
        buffer.append("statutMedium=").append(getStatutMedium());
        buffer.append(",");
        buffer.append("email=").append(getEmail());
        buffer.append(",");
        buffer.append("descriptifComplementaire=").append(getDescriptifComplementaire());
        buffer.append(",");
        buffer.append("autorisationMailing=").append(getAutorisationMailing());
        buffer.append(",");
        buffer.append("signatureModification=").append(getSignatureModification());
        buffer.append(",");
        buffer.append("siteModification=").append(getSiteModification());
        buffer.append(",");
        buffer.append("dateModification=").append(getDateModification());
        buffer.append(",");
        buffer.append("signatureCreation=").append(getSignatureCreation());
        buffer.append(",");
        buffer.append("siteCreation=").append(getSiteCreation());
        buffer.append(",");
        buffer.append("dateCreation=").append(getDateCreation());
        buffer.append(",");
        buffer.append("cleRole=").append(getCleRole());
        buffer.append(",");
        buffer.append("cleTemp=").append(getCleTemp());
        buffer.append("]");
        return buffer.toString();
    }



    /*PROTECTED REGION ID(equals hash _2rProMWJEd-Z0rWwhyE6jg) ENABLED START*/

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
        final Email other = (Email) obj;

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

    @Override
    public String getId() {
        return sain;
    }
}
