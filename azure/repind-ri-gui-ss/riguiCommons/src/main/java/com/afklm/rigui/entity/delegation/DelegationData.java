package com.afklm.rigui.entity.delegation;

/*PROTECTED REGION ID(_c6JwcJSKEeOwS89XbJiNOw i) ENABLED START*/

// add not generated imports here

import com.afklm.rigui.entity.individu.Individu;
import org.hibernate.annotations.ForeignKey;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/*PROTECTED REGION END*/


/**
 * <p>Title : DelegationData.java</p>
 * BO DelegationData
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
@Entity


@Table(name="DELEGATION_DATA")
public class DelegationData implements Serializable {

    /*PROTECTED REGION ID(serialUID _c6JwcJSKEeOwS89XbJiNOw) ENABLED START*/
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
     * delegationId
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_DELEGATION_DATA")
    @SequenceGenerator(name="SEQ_DELEGATION_DATA", sequenceName = "SEQ_DELEGATION_DATA",
            allocationSize = 1)
    @Column(name="DELEGATION_DATA_ID")
    private Integer delegationId;


    /**
     * status
     */
    @Column(name="STATUS")
    private String status;


    /**
     * type
     */
    @Column(name="STYPE")
    private String type;


    /**
     * creationSite
     */
    @Column(name="CREATION_SITE")
    private String creationSite;


    /**
     * creationDate
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="CREATION_DATE")
    private Date creationDate;


    /**
     * creationSignature
     */
    @Column(name="CREATION_SIGNATURE")
    private String creationSignature;


    /**
     * modificationSite
     */
    @Column(name="MODIFICATION_SITE")
    private String modificationSite;


    /**
     * modificationSignature
     */
    @Column(name="MODIFICATION_SIGNATURE")
    private String modificationSignature;


    /**
     * modificationDate
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="MODIFICATION_DATE")
    private Date modificationDate;


    /**
     * sender
     */
    @Column(name="SENDER")
    private String sender;


    /**
     * delegate
     */
    // * <-> 1
    @ManyToOne()
    @JoinColumn(name="SGIN_DELEGATE", nullable=false, foreignKey = @javax.persistence.ForeignKey(name = "FK_DELEGATION_DATA_SGIN_DELEGATE_SGIN"))
    private Individu delegate;


    /**
     * delegator
     */
    // * <-> 1
    @ManyToOne()
    @JoinColumn(name="SGIN_DELEGATOR",nullable=false, foreignKey = @javax.persistence.ForeignKey(name = "FK_DELEGATION_DATA_SGIN_DELEGATOR_SGIN"))
    private Individu delegator;


    /**
     * delegationDataInfo
     */
    // 1 <-> * 
    @OneToMany(mappedBy="delegationData", cascade=CascadeType.ALL, fetch=FetchType.LAZY)
    private Set<DelegationDataInfo> delegationDataInfo;


    /**
     * default constructor 
     */
    public DelegationData() {
    }

    /**
     * full constructor
     * @param pDelegationId delegationId
     * @param pStatus status
     * @param pType type
     * @param pCreationSite creationSite
     * @param pCreationDate creationDate
     * @param pCreationSignature creationSignature
     * @param pModificationSite modificationSite
     * @param pModificationSignature modificationSignature
     * @param pModificationDate modificationDate
     * @param pSender sender
     */
    public DelegationData(Integer pDelegationId, String pStatus, String pType, String pCreationSite, Date pCreationDate, String pCreationSignature, String pModificationSite, String pModificationSignature, Date pModificationDate, String pSender) {
        this.delegationId = pDelegationId;
        this.status = pStatus;
        this.type = pType;
        this.creationSite = pCreationSite;
        this.creationDate = pCreationDate;
        this.creationSignature = pCreationSignature;
        this.modificationSite = pModificationSite;
        this.modificationSignature = pModificationSignature;
        this.modificationDate = pModificationDate;
        this.sender = pSender;
    }

    /**
     *
     * @return creationDate
     */
    public Date getCreationDate() {
        return this.creationDate;
    }

    /**
     *
     * @param pCreationDate creationDate value
     */
    public void setCreationDate(Date pCreationDate) {
        this.creationDate = pCreationDate;
    }

    /**
     *
     * @return creationSignature
     */
    public String getCreationSignature() {
        return this.creationSignature;
    }

    /**
     *
     * @param pCreationSignature creationSignature value
     */
    public void setCreationSignature(String pCreationSignature) {
        this.creationSignature = pCreationSignature;
    }

    /**
     *
     * @return creationSite
     */
    public String getCreationSite() {
        return this.creationSite;
    }

    /**
     *
     * @param pCreationSite creationSite value
     */
    public void setCreationSite(String pCreationSite) {
        this.creationSite = pCreationSite;
    }

    /**
     *
     * @return delegate
     */
    public Individu getDelegate() {
        return this.delegate;
    }

    /**
     *
     * @param pDelegate delegate value
     */
    public void setDelegate(Individu pDelegate) {
        this.delegate = pDelegate;
    }

    /**
     *
     * @return delegationId
     */
    public Integer getDelegationId() {
        return this.delegationId;
    }

    /**
     *
     * @param pDelegationId delegationId value
     */
    public void setDelegationId(Integer pDelegationId) {
        this.delegationId = pDelegationId;
    }

    /**
     *
     * @return delegator
     */
    public Individu getDelegator() {
        return this.delegator;
    }

    /**
     *
     * @param pDelegator delegator value
     */
    public void setDelegator(Individu pDelegator) {
        this.delegator = pDelegator;
    }

    /**
     *
     * @return modificationDate
     */
    public Date getModificationDate() {
        return this.modificationDate;
    }

    /**
     *
     * @param pModificationDate modificationDate value
     */
    public void setModificationDate(Date pModificationDate) {
        this.modificationDate = pModificationDate;
    }

    /**
     *
     * @return modificationSignature
     */
    public String getModificationSignature() {
        return this.modificationSignature;
    }

    /**
     *
     * @param pModificationSignature modificationSignature value
     */
    public void setModificationSignature(String pModificationSignature) {
        this.modificationSignature = pModificationSignature;
    }

    /**
     *
     * @return modificationSite
     */
    public String getModificationSite() {
        return this.modificationSite;
    }

    /**
     *
     * @param pModificationSite modificationSite value
     */
    public void setModificationSite(String pModificationSite) {
        this.modificationSite = pModificationSite;
    }

    /**
     *
     * @return sender
     */
    public String getSender() {
        return this.sender;
    }

    /**
     *
     * @param pSender sender value
     */
    public void setSender(String pSender) {
        this.sender = pSender;
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
     * @return delegationDataInfo
     */
    public Set<DelegationDataInfo> getDelegationDataInfo() {
        return this.delegationDataInfo;
    }

    /**
     *
     * @param pDelegationDataInfo delegationDataInfo value
     */
    public void setDelegationDataInfo(Set<DelegationDataInfo> pDelegationDataInfo) {
        this.delegationDataInfo = pDelegationDataInfo;
    }

    /**
     *
     * @return object as string
     */
    public String toString() {
        String result = null;
        /*PROTECTED REGION ID(toString_c6JwcJSKEeOwS89XbJiNOw) ENABLED START*/
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
        buffer.append("delegationId=").append(getDelegationId());
        buffer.append(",");
        buffer.append("status=").append(getStatus());
        buffer.append(",");
        buffer.append("type=").append(getType());
        buffer.append(",");
        buffer.append("creationSite=").append(getCreationSite());
        buffer.append(",");
        buffer.append("creationDate=").append(getCreationDate());
        buffer.append(",");
        buffer.append("creationSignature=").append(getCreationSignature());
        buffer.append(",");
        buffer.append("modificationSite=").append(getModificationSite());
        buffer.append(",");
        buffer.append("modificationSignature=").append(getModificationSignature());
        buffer.append(",");
        buffer.append("modificationDate=").append(getModificationDate());
        buffer.append(",");
        buffer.append("sender=").append(getSender());
        buffer.append("]");
        return buffer.toString();
    }



    /*PROTECTED REGION ID(equals hash _c6JwcJSKEeOwS89XbJiNOw) ENABLED START*/

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

    /*PROTECTED REGION ID(_c6JwcJSKEeOwS89XbJiNOw u m) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/

}
