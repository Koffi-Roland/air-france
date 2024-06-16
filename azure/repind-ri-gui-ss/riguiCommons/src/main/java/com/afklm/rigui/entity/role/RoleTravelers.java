package com.afklm.rigui.entity.role;

/*PROTECTED REGION ID(_GWdWgEd0EeaNBc8l2dsFVA i) ENABLED START*/

// add not generated imports here

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/*PROTECTED REGION END*/


/**
 * <p>Title : RoleTravelers.java</p>
 * BO RoleTravelers
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
@Entity


@Table(name="ROLE_TRAVELERS")
public class RoleTravelers implements Serializable {


    /*PROTECTED REGION ID(serialUID _GWdWgEd0EeaNBc8l2dsFVA) ENABLED START*/
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

    private static final long serialVersionUID = -1685512493317850763L;

    /*PROTECTED REGION END*/


    /**
     * cleRole
     */
    @Id
    @GeneratedValue(generator = "foreignGeneratorTraveler")
    @GenericGenerator(name = "foreignGeneratorTraveler", strategy = "foreign", parameters = { @Parameter(name = "property", value = "businessRole") })
    @Column(name="ICLE_ROLE", length=10, nullable=false, unique=true)
    private Integer cleRole;


    /**
     * gin
     */
    @Column(name="SGIN", length=12, updatable=false, insertable=true)
    private String gin;


    /**
     * lastRecognitionDate
     */
    @Column(name="DLAST_RECOGNITION_DATE", nullable=false)
    private Date lastRecognitionDate;


    /**
     * matchingRecognitionCode
     */
    @Column(name="SMATCHING_RECOGNITION_CODE", length=4)
    private String matchingRecognitionCode;


    /**
     * dateCreation
     */
    @Column(name="DDATE_CREATION")
    private Date dateCreation;


    /**
     * signatureCreation
     */
    @Column(name="SSIGNATURE_CREATION", length=16)
    private String signatureCreation;


    /**
     * siteCreation
     */
    @Column(name="SSITE_CREATION", length=10)
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
    @OneToOne(mappedBy="roleTravelers", fetch=FetchType.LAZY)
    private BusinessRole businessRole;

    /*PROTECTED REGION ID(_GWdWgEd0EeaNBc8l2dsFVA u var) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/

    /**
     * default constructor 
     */
    public RoleTravelers() {
        //empty constructor
    }

    /**
     * full constructor
     * @param pCleRole cleRole
     * @param pGin gin
     * @param pLastRecognitionDate lastRecognitionDate
     * @param pMatchingRecognitionCode matchingRecognitionCode
     * @param pDateCreation dateCreation
     * @param pSignatureCreation signatureCreation
     * @param pSiteCreation siteCreation
     * @param pDateModification dateModification
     * @param pSignatureModification signatureModification
     * @param pSiteModification siteModification
     */
    public RoleTravelers(Integer pCleRole, String pGin, Date pLastRecognitionDate, String pMatchingRecognitionCode, Date pDateCreation, String pSignatureCreation, String pSiteCreation, Date pDateModification, String pSignatureModification, String pSiteModification) {
        this.cleRole = pCleRole;
        this.gin = pGin;
        this.lastRecognitionDate = pLastRecognitionDate;
        this.matchingRecognitionCode = pMatchingRecognitionCode;
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
     * @return gin
     */
    public String getGin() {
        return this.gin;
    }

    /**
     *
     * @param pGin gin value
     */
    public void setGin(String pGin) {
        this.gin = pGin;
    }

    /**
     *
     * @return lastRecognitionDate
     */
    public Date getLastRecognitionDate() {
        return this.lastRecognitionDate;
    }

    /**
     *
     * @param pLastRecognitionDate lastRecognitionDate value
     */
    public void setLastRecognitionDate(Date pLastRecognitionDate) {
        this.lastRecognitionDate = pLastRecognitionDate;
    }

    /**
     *
     * @return matchingRecognitionCode
     */
    public String getMatchingRecognitionCode() {
        return this.matchingRecognitionCode;
    }

    /**
     *
     * @param pMatchingRecognitionCode matchingRecognitionCode value
     */
    public void setMatchingRecognitionCode(String pMatchingRecognitionCode) {
        this.matchingRecognitionCode = pMatchingRecognitionCode;
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
     * @return object as string
     */
    public String toString() {
        String result = null;
        /*PROTECTED REGION ID(toString_GWdWgEd0EeaNBc8l2dsFVA) ENABLED START*/
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
        buffer.append("cleRole=").append(getCleRole());
        buffer.append(",");
        buffer.append("gin=").append(getGin());
        buffer.append(",");
        buffer.append("lastRecognitionDate=").append(getLastRecognitionDate());
        buffer.append(",");
        buffer.append("matchingRecognitionCode=").append(getMatchingRecognitionCode());
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



    /*PROTECTED REGION ID(equals hash _GWdWgEd0EeaNBc8l2dsFVA) ENABLED START*/

    /**
     * {@inheritDoc}
     * @see Object#hashCode()
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
        final RoleTravelers other = (RoleTravelers) obj;

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

    /*PROTECTED REGION ID(_GWdWgEd0EeaNBc8l2dsFVA u m) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/

}
