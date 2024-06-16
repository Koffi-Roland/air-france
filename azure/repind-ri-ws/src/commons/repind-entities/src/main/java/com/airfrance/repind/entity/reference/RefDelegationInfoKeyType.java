package com.airfrance.repind.entity.reference;

/*PROTECTED REGION ID(_RI4bYHILEeeRrZw0c1ut0g i) ENABLED START*/

// add not generated imports here

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/*PROTECTED REGION END*/


/**
 * <p>Title : RefDelegationInfoKeyType.java</p>
 * BO RefDelegationInfoKeyType
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
@Entity
@Table(name="REF_DELEGATION_INFO_KEY_TYPE")
public class RefDelegationInfoKeyType implements Serializable {

/*PROTECTED REGION ID(serialUID _RI4bYHILEeeRrZw0c1ut0g) ENABLED START*/
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
     * refId
     */
    @Id
    @Column(name="REFID")
    private Integer refId;
    
    /**
     * key
     */
    @Column(name="SKEY")
    private String key;
        
            
    /**
     * type
     */
    @Column(name="STYPE")
    private String type;
        
            
    /**
     * minLength
     */
    @Column(name="IMIN_LENGTH")
    private Integer minLength;
        
            
    /**
     * maxLength
     */
    @Column(name="IMAX_LENGTH")
    private Integer maxLength;
        
            
    /**
     * dataType
     */
    @Column(name="SDATA_TYPE")
    private String dataType;
        
            
    /**
     * condition
     */
    @Column(name="SCONDITION")
    private String condition;
        
    /*PROTECTED REGION ID(_RI4bYHILEeeRrZw0c1ut0g u var) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/
    
    /** 
     * default constructor 
     */
    public RefDelegationInfoKeyType() {
    	//empty constructor
    }
        
    /** 
     * full constructor
     * @param pRefId refId
     * @param pKey key
     * @param pType type
     * @param pMinLength minLength
     * @param pMaxLength maxLength
     * @param pDataType dataType
     * @param pCondition condition
     */
    public RefDelegationInfoKeyType(Integer pRefId, String pKey, String pType, Integer pMinLength, Integer pMaxLength, String pDataType, String pCondition) {
        this.refId = pRefId;
    	this.key = pKey;
        this.type = pType;
        this.minLength = pMinLength;
        this.maxLength = pMaxLength;
        this.dataType = pDataType;
        this.condition = pCondition;
    }

    /**
     *
     * @return condition
     */
    public String getCondition() {
        return this.condition;
    }

    /**
     *
     * @param pCondition condition value
     */
    public void setCondition(String pCondition) {
        this.condition = pCondition;
    }

    /**
     *
     * @return dataType
     */
    public String getDataType() {
        return this.dataType;
    }

    /**
     *
     * @param pDataType dataType value
     */
    public void setDataType(String pDataType) {
        this.dataType = pDataType;
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
     * @return maxLength
     */
    public Integer getMaxLength() {
        return this.maxLength;
    }

    /**
     *
     * @param pMaxLength maxLength value
     */
    public void setMaxLength(Integer pMaxLength) {
        this.maxLength = pMaxLength;
    }

    /**
     *
     * @return minLength
     */
    public Integer getMinLength() {
        return this.minLength;
    }

    /**
     *
     * @param pMinLength minLength value
     */
    public void setMinLength(Integer pMinLength) {
        this.minLength = pMinLength;
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
        /*PROTECTED REGION ID(toString_RI4bYHILEeeRrZw0c1ut0g) ENABLED START*/
        result = toStringImpl();
        /*PROTECTED REGION END*/
        return result;
    }
    
    
    
    public Integer getRefId() {
		return refId;
	}

	public void setRefId(Integer refId) {
		this.refId = refId;
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
        buffer.append("key=").append(getKey());
        buffer.append(",");
        buffer.append("type=").append(getType());
        buffer.append(",");
        buffer.append("minLength=").append(getMinLength());
        buffer.append(",");
        buffer.append("maxLength=").append(getMaxLength());
        buffer.append(",");
        buffer.append("dataType=").append(getDataType());
        buffer.append(",");
        buffer.append("condition=").append(getCondition());
        buffer.append("]");
        return buffer.toString();
    }

     
    
    /*PROTECTED REGION ID(equals hash _RI4bYHILEeeRrZw0c1ut0g) ENABLED START*/
    
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

    /*PROTECTED REGION ID(_RI4bYHILEeeRrZw0c1ut0g u m) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/
    
}
