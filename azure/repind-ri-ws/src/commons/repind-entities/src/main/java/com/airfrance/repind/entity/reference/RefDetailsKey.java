package com.airfrance.repind.entity.reference;

/*PROTECTED REGION ID(_mEkwAAQfEeeNs7K6Uzy58g i) ENABLED START*/

// add not generated imports here

import org.hibernate.annotations.ForeignKey;

import javax.persistence.*;
import java.io.Serializable;

/*PROTECTED REGION END*/


/**
 * <p>Title : RefDetailsKey.java</p>
 * BO RefDetailsKey
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
@Entity


@Table(name="REF_DETAILS_KEY")
public class RefDetailsKey implements Serializable {

/*PROTECTED REGION ID(serialUID _mEkwAAQfEeeNs7K6Uzy58g) ENABLED START*/
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
     * refDetailsKeyId
     */
    @Id
    @Column(name="IREF_DETAILS_KEY_ID")
    private Integer refDetailsKeyId;
        
            
    /**
     * code
     */
    @Column(name="SCODE", length=12)
    private String code;
        
            
    /**
     * labelFR
     */
    @Column(name="SLABEL_FR", length=255)
    private String labelFR;
        
            
    /**
     * labelEN
     */
    @Column(name="SLABEL_EN", length=255)
    private String labelEN;
        
            
    /**
     * detailsKeyID
     */
    @Column(name="IDETAILS_KEY_ID")
    private Integer detailsKeyID;
        
            
    /**
     * typeEvent
     */
    // 1 -> 1
    @OneToOne()
    @JoinColumn(name="STYPE_EVENT", nullable=false)
    @ForeignKey(name = "FK_REF_TYP_EVENT")
    private RefTypeEvent typeEvent;
        
    /*PROTECTED REGION ID(_mEkwAAQfEeeNs7K6Uzy58g u var) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/
    
    /** 
     * default constructor 
     */
    public RefDetailsKey() {
    	//empty constructor
    }
        
    /** 
     * full constructor
     * @param pRefDetailsKeyId refDetailsKeyId
     * @param pCode code
     * @param pLabelFR labelFR
     * @param pLabelEN labelEN
     * @param pDetailsKeyID detailsKeyID
     */
    public RefDetailsKey(Integer pRefDetailsKeyId, String pCode, String pLabelFR, String pLabelEN, Integer pDetailsKeyID) {
        this.refDetailsKeyId = pRefDetailsKeyId;
        this.code = pCode;
        this.labelFR = pLabelFR;
        this.labelEN = pLabelEN;
        this.detailsKeyID = pDetailsKeyID;
    }

    /**
     *
     * @return code
     */
    public String getCode() {
        return this.code;
    }

    /**
     *
     * @param pCode code value
     */
    public void setCode(String pCode) {
        this.code = pCode;
    }

    /**
     *
     * @return detailsKeyID
     */
    public Integer getDetailsKeyID() {
        return this.detailsKeyID;
    }

    /**
     *
     * @param pDetailsKeyID detailsKeyID value
     */
    public void setDetailsKeyID(Integer pDetailsKeyID) {
        this.detailsKeyID = pDetailsKeyID;
    }

    /**
     *
     * @return labelEN
     */
    public String getLabelEN() {
        return this.labelEN;
    }

    /**
     *
     * @param pLabelEN labelEN value
     */
    public void setLabelEN(String pLabelEN) {
        this.labelEN = pLabelEN;
    }

    /**
     *
     * @return labelFR
     */
    public String getLabelFR() {
        return this.labelFR;
    }

    /**
     *
     * @param pLabelFR labelFR value
     */
    public void setLabelFR(String pLabelFR) {
        this.labelFR = pLabelFR;
    }

    /**
     *
     * @return refDetailsKeyId
     */
    public Integer getRefDetailsKeyId() {
        return this.refDetailsKeyId;
    }

    /**
     *
     * @param pRefDetailsKeyId refDetailsKeyId value
     */
    public void setRefDetailsKeyId(Integer pRefDetailsKeyId) {
        this.refDetailsKeyId = pRefDetailsKeyId;
    }

    /**
     *
     * @return typeEvent
     */
    public RefTypeEvent getTypeEvent() {
        return this.typeEvent;
    }

    /**
     *
     * @param pTypeEvent typeEvent value
     */
    public void setTypeEvent(RefTypeEvent pTypeEvent) {
        this.typeEvent = pTypeEvent;
    }

    /**
     *
     * @return object as string
     */
    public String toString() {
        String result = null;
        /*PROTECTED REGION ID(toString_mEkwAAQfEeeNs7K6Uzy58g) ENABLED START*/
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
        buffer.append("refDetailsKeyId=").append(getRefDetailsKeyId());
        buffer.append(",");
        buffer.append("code=").append(getCode());
        buffer.append(",");
        buffer.append("labelFR=").append(getLabelFR());
        buffer.append(",");
        buffer.append("labelEN=").append(getLabelEN());
        buffer.append(",");
        buffer.append("detailsKeyID=").append(getDetailsKeyID());
        buffer.append("]");
        return buffer.toString();
    }

     
    
    /*PROTECTED REGION ID(equals hash _mEkwAAQfEeeNs7K6Uzy58g) ENABLED START*/
    
    /**
     * {@inheritDoc}
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        //TODO Customize here if necessary
        return hashCodeImpl();
    }

    /**
     * {@inheritDoc}
     * @see java.lang.Object#equals(java.lang.Object)
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

    /*PROTECTED REGION ID(_mEkwAAQfEeeNs7K6Uzy58g u m) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/
    
}
