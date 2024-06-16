package com.afklm.rigui.entity.adresse;

/*PROTECTED REGION ID(_YbpmILgUEeSmGvH7BtiQjw i) ENABLED START*/

// add not generated imports here

import org.hibernate.annotations.ForeignKey;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/*PROTECTED REGION END*/


/**
 * <p>Title : PostalAddressToNormalize.java</p>
 * BO PostalAddressToNormalize
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
@Entity


@Table(name="ADR_TO_NORMALIZE")
public class PostalAddressToNormalize implements Serializable {

    /*PROTECTED REGION ID(serialUID _YbpmILgUEeSmGvH7BtiQjw) ENABLED START*/
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
    //@Column(name="SAIN", length=16, nullable=false)
    private String sain;


    /**
     * site
     */
    @Column(name="SSITE", length=10)
    private String site;


    /**
     * appliErrorCode
     */
    @Column(name="SAPPLI_ERROR_CODE", length=4)
    private String appliErrorCode;


    /**
     * dateLog
     */
    @Column(name="DDATE_LOG")
    private Date dateLog;


    /**
     * dateCreation
     */
    @Column(name="DDATE_CREATION")
    private Date dateCreation;


    /**
     * dateSend
     */
    @Column(name="DDATE_SEND")
    private Date dateSend;


    /**
     * dateRecep
     */
    @Column(name="DDATE_RECEP")
    private Date dateRecep;


    /**
     * status
     */
    @Column(name="SSTATUS", length=4)
    private String status;


    /**
     * postalAddress
     */
    // * <-> 1
    @ManyToOne()
    @JoinColumn(name="SAIN", nullable=false, updatable=false, insertable=false, foreignKey = @javax.persistence.ForeignKey(name = "FK_ADR_TO_N_ADR_POST"))
    private PostalAddress postalAddress;

    /*PROTECTED REGION ID(_YbpmILgUEeSmGvH7BtiQjw u var) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/

    /**
     * default constructor 
     */
    public PostalAddressToNormalize() {
    }

    /**
     * full constructor
     * @param pSain sain
     * @param pSite site
     * @param pAppliErrorCode appliErrorCode
     * @param pDateLog dateLog
     * @param pDateCreation dateCreation
     * @param pDateSend dateSend
     * @param pDateRecep dateRecep
     * @param pStatus status
     */
    public PostalAddressToNormalize(String pSain, String pSite, String pAppliErrorCode, Date pDateLog, Date pDateCreation, Date pDateSend, Date pDateRecep, String pStatus) {
        this.sain = pSain;
        this.site = pSite;
        this.appliErrorCode = pAppliErrorCode;
        this.dateLog = pDateLog;
        this.dateCreation = pDateCreation;
        this.dateSend = pDateSend;
        this.dateRecep = pDateRecep;
        this.status = pStatus;
    }

    /**
     *
     * @return appliErrorCode
     */
    public String getAppliErrorCode() {
        return this.appliErrorCode;
    }

    /**
     *
     * @param pAppliErrorCode appliErrorCode value
     */
    public void setAppliErrorCode(String pAppliErrorCode) {
        this.appliErrorCode = pAppliErrorCode;
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
     * @return dateLog
     */
    public Date getDateLog() {
        return this.dateLog;
    }

    /**
     *
     * @param pDateLog dateLog value
     */
    public void setDateLog(Date pDateLog) {
        this.dateLog = pDateLog;
    }

    /**
     *
     * @return dateRecep
     */
    public Date getDateRecep() {
        return this.dateRecep;
    }

    /**
     *
     * @param pDateRecep dateRecep value
     */
    public void setDateRecep(Date pDateRecep) {
        this.dateRecep = pDateRecep;
    }

    /**
     *
     * @return dateSend
     */
    public Date getDateSend() {
        return this.dateSend;
    }

    /**
     *
     * @param pDateSend dateSend value
     */
    public void setDateSend(Date pDateSend) {
        this.dateSend = pDateSend;
    }

    /**
     *
     * @return postalAddress
     */
    public PostalAddress getPostalAddress() {
        return this.postalAddress;
    }

    /**
     *
     * @param pPostalAddress postalAddress value
     */
    public void setPostalAddress(PostalAddress pPostalAddress) {
        this.postalAddress = pPostalAddress;
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
     * @return site
     */
    public String getSite() {
        return this.site;
    }

    /**
     *
     * @param pSite site value
     */
    public void setSite(String pSite) {
        this.site = pSite;
    }

    /**
     *
     * @return status
     */
    public String getStatus() {
        return this.status;
    }

    /**
     *
     * @param pStatus status value
     */
    public void setStatus(String pStatus) {
        this.status = pStatus;
    }

    /**
     *
     * @return object as string
     */
    public String toString() {
        String result = null;
        /*PROTECTED REGION ID(toString_YbpmILgUEeSmGvH7BtiQjw) ENABLED START*/
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
        buffer.append("site=").append(getSite());
        buffer.append(",");
        buffer.append("appliErrorCode=").append(getAppliErrorCode());
        buffer.append(",");
        buffer.append("dateLog=").append(getDateLog());
        buffer.append(",");
        buffer.append("dateCreation=").append(getDateCreation());
        buffer.append(",");
        buffer.append("dateSend=").append(getDateSend());
        buffer.append(",");
        buffer.append("dateRecep=").append(getDateRecep());
        buffer.append(",");
        buffer.append("status=").append(getStatus());
        buffer.append("]");
        return buffer.toString();
    }



    /*PROTECTED REGION ID(equals hash _YbpmILgUEeSmGvH7BtiQjw) ENABLED START*/

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

    /*PROTECTED REGION ID(_YbpmILgUEeSmGvH7BtiQjw u m) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/

}
