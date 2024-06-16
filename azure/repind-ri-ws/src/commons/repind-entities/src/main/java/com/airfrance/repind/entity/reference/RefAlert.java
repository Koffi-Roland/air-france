package com.airfrance.repind.entity.reference;

/*PROTECTED REGION ID(_j96pEGqeEearcuraYqCMoA i) ENABLED START*/

// add not generated imports here

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/*PROTECTED REGION END*/


/**
 * <p>Title : RefAlert.java</p>
 * BO RefAlert
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
@Entity


@Table(name="REF_ALERT")
public class RefAlert implements Serializable {

/*PROTECTED REGION ID(serialUID _j96pEGqeEearcuraYqCMoA) ENABLED START*/
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
     * key
     */
    @Id
    @Column(name="KEY", length=100)
    private String key;
        
            
    /**
     * mandatory
     */
    @Column(name="MANDATORY", length=1)
    private String mandatory;
        
            
    /**
     * type
     */
    @Column(name="TYPE")
    private String type;
        
            
    /**
     * usage
     */
    @Column(name="USAGE", length=20)
    private String usage;
        
            
    /**
     * value
     */
    @Column(name="VALUE", length=200)
    private String value;
        
    /*PROTECTED REGION ID(_j96pEGqeEearcuraYqCMoA u var) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/
    
    /** 
     * default constructor 
     */
    public RefAlert() {
    	//empty constructor
    }
        
    /** 
     * full constructor
     * @param pKey key
     * @param pMandatory mandatory
     * @param pType type
     * @param pUsage usage
     * @param pValue value
     */
    public RefAlert(String pKey, String pMandatory, String pType, String pUsage, String pValue) {
        this.key = pKey;
        this.mandatory = pMandatory;
        this.type = pType;
        this.usage = pUsage;
        this.value = pValue;
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
     * @return mandatory
     */
    public String getMandatory() {
        return this.mandatory;
    }

    /**
     *
     * @param pMandatory mandatory value
     */
    public void setMandatory(String pMandatory) {
        this.mandatory = pMandatory;
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
     * @return usage
     */
    public String getUsage() {
        return this.usage;
    }

    /**
     *
     * @param pUsage usage value
     */
    public void setUsage(String pUsage) {
        this.usage = pUsage;
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
        /*PROTECTED REGION ID(toString_j96pEGqeEearcuraYqCMoA) ENABLED START*/
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
        buffer.append("key=").append(getKey());
        buffer.append(",");
        buffer.append("mandatory=").append(getMandatory());
        buffer.append(",");
        buffer.append("type=").append(getType());
        buffer.append(",");
        buffer.append("usage=").append(getUsage());
        buffer.append(",");
        buffer.append("value=").append(getValue());
        buffer.append("]");
        return buffer.toString();
    }

     
    
    /*PROTECTED REGION ID(equals hash _j96pEGqeEearcuraYqCMoA) ENABLED START*/
    
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

    /*PROTECTED REGION ID(_j96pEGqeEearcuraYqCMoA u m) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/
    
}
