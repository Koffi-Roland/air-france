package com.afklm.rigui.entity.delegation;

/*PROTECTED REGION ID(_UNJ_YOZkEee2NuY-gHh1Ow i) ENABLED START*/

// add not generated imports here

import org.hibernate.annotations.ForeignKey;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/*PROTECTED REGION END*/


/**
 * <p>Title : DelegationDataInfo.java</p>
 * BO DelegationDataInfo
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
@Entity


@Table(name="DELEGATION_DATA_INFO")
public class DelegationDataInfo implements Serializable {

    /*PROTECTED REGION ID(serialUID _UNJ_YOZkEee2NuY-gHh1Ow) ENABLED START*/
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
     * delegationDataInfoId
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_DELEGATION_DATA_INFO")
    @SequenceGenerator(name="SEQ_DELEGATION_DATA_INFO", sequenceName = "SEQ_DELEGATION_DATA_INFO",
            allocationSize = 1)
    @Column(name="DELEGATION_DATA_INFO_ID", nullable=false)
    private Integer delegationDataInfoId;


    /**
     * type
     */
    @Column(name="STYPE", nullable=false)
    private String type;


    /**
     * typeGroupId
     */
    @Column(name="TYPE_GROUP_ID")
    private Integer typeGroupId;


    /**
     * key
     */
    @Column(name="SKEY", nullable=false)
    private String key;


    /**
     * value
     */
    @Column(name="SVALUE", nullable=false)
    private String value;


    /**
     * dateCreation
     */
    @Column(name="DDATE_CREATION")
    private Date dateCreation;


    /**
     * siteCreation
     */
    @Column(name="SSITE_CREATION")
    private String siteCreation;


    /**
     * signatureCreation
     */
    @Column(name="SSIGNATURE_CREATION")
    private String signatureCreation;


    /**
     * dateModification
     */
    @Column(name="DDATE_MODIFICATION")
    private Date dateModification;


    /**
     * siteModification
     */
    @Column(name="SSITE_MODIFICATION")
    private String siteModification;


    /**
     * signatureModification
     */
    @Column(name="SSIGNATURE_MODIFICATION")
    private String signatureModification;


    /**
     * delegationData
     */
    // * <-> 1
    @ManyToOne()
    @JoinColumn(name="DELEGATION_DATA_ID", nullable=false, foreignKey = @javax.persistence.ForeignKey(name = "FK_DELEGATION_DATA_ID"))
    private DelegationData delegationData;

    /*PROTECTED REGION ID(_UNJ_YOZkEee2NuY-gHh1Ow u var) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/

    /**
     * default constructor 
     */
    public DelegationDataInfo() {
        //empty constructor
    }

    /**
     * full constructor
     * @param pDelegationDataInfoId delegationDataInfoId
     * @param pType type
     * @param pTypeGroupId typeGroupId
     * @param pKey key
     * @param pValue value
     * @param pDateCreation dateCreation
     * @param pSiteCreation siteCreation
     * @param pSignatureCreation signatureCreation
     * @param pDateModification dateModification
     * @param pSiteModification siteModification
     * @param pSignatureModification signatureModification
     */
    public DelegationDataInfo(Integer pDelegationDataInfoId, String pType, Integer pTypeGroupId, String pKey, String pValue, Date pDateCreation, String pSiteCreation, String pSignatureCreation, Date pDateModification, String pSiteModification, String pSignatureModification) {
        this.delegationDataInfoId = pDelegationDataInfoId;
        this.type = pType;
        this.typeGroupId = pTypeGroupId;
        this.key = pKey;
        this.value = pValue;
        this.dateCreation = pDateCreation;
        this.siteCreation = pSiteCreation;
        this.signatureCreation = pSignatureCreation;
        this.dateModification = pDateModification;
        this.siteModification = pSiteModification;
        this.signatureModification = pSignatureModification;
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
     * @return delegationData
     */
    public DelegationData getDelegationData() {
        return this.delegationData;
    }

    /**
     *
     * @param pDelegationData delegationData value
     */
    public void setDelegationData(DelegationData pDelegationData) {
        this.delegationData = pDelegationData;
    }

    /**
     *
     * @return delegationDataInfoId
     */
    public Integer getDelegationDataInfoId() {
        return this.delegationDataInfoId;
    }

    /**
     *
     * @param pDelegationDataInfoId delegationDataInfoId value
     */
    public void setDelegationDataInfoId(Integer pDelegationDataInfoId) {
        this.delegationDataInfoId = pDelegationDataInfoId;
    }

    /**
     *
     * @return key
     */
    public String getKey() {
        return this.key;
    }

    /**
     *
     * @param pKey key value
     */
    public void setKey(String pKey) {
        this.key = pKey;
    }

    /**
     *
     * @return typeGroupId
     */
    public Integer getTypeGroupId() {
        return this.typeGroupId;
    }

    /**
     *
     * @param pTypeGroupId typeGroupId value
     */
    public void setTypeGroupId(Integer pTypeGroupId) {
        this.typeGroupId = pTypeGroupId;
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
     * @return value
     */
    public String getValue() {
        return this.value;
    }

    /**
     *
     * @param pValue value value
     */
    public void setValue(String pValue) {
        this.value = pValue;
    }

    /**
     *
     * @return object as string
     */
    public String toString() {
        String result = null;
        /*PROTECTED REGION ID(toString_UNJ_YOZkEee2NuY-gHh1Ow) ENABLED START*/
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
        buffer.append("delegationDataInfoId=").append(getDelegationDataInfoId());
        buffer.append(",");
        buffer.append("type=").append(getType());
        buffer.append(",");
        buffer.append("typeGroupId=").append(getTypeGroupId());
        buffer.append(",");
        buffer.append("key=").append(getKey());
        buffer.append(",");
        buffer.append("value=").append(getValue());
        buffer.append(",");
        buffer.append("dateCreation=").append(getDateCreation());
        buffer.append(",");
        buffer.append("siteCreation=").append(getSiteCreation());
        buffer.append(",");
        buffer.append("signatureCreation=").append(getSignatureCreation());
        buffer.append(",");
        buffer.append("dateModification=").append(getDateModification());
        buffer.append(",");
        buffer.append("siteModification=").append(getSiteModification());
        buffer.append(",");
        buffer.append("signatureModification=").append(getSignatureModification());
        buffer.append("]");
        return buffer.toString();
    }



    /*PROTECTED REGION ID(equals hash _UNJ_YOZkEee2NuY-gHh1Ow) ENABLED START*/

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

    /*PROTECTED REGION ID(_UNJ_YOZkEee2NuY-gHh1Ow u m) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/

}
