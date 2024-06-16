package com.afklm.rigui.dto.reference;

/*PROTECTED REGION ID(_M5E2QHIPEeeRrZw0c1ut0g i) ENABLED START*/

// add not generated imports here
import java.io.Serializable;


/*PROTECTED REGION END*/

/**
 * <p>Title : RefPreferenceKeyTypeDTO</p>
 * 
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
	public  class RefPreferenceKeyTypeDTO implements Serializable {


        
    /**
     * refId
     */
    private Integer refId;
        
        
    /**
     * key
     */
    private String key;
        
        
    /**
     * type
     */
    private String type;
        
        
    /**
     * minLength
     */
    private Integer minLength;
        
        
    /**
     * maxLength
     */
    private Integer maxLength;
        
        
    /**
     * dataType
     */
    private String dataType;
        
        
    /**
     * condition
     */
    private String condition;
        

    /*PROTECTED REGION ID(_M5E2QHIPEeeRrZw0c1ut0g u var) ENABLED START*/
    // add your custom variables here if necessary
    
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
	     * default constructor 
	     */
	    public RefPreferenceKeyTypeDTO() {
	    //empty constructor
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
     * @return refId
     */
    public Integer getRefId() {
        return this.refId;
    }

    /**
     *
     * @param pRefId refId value
     */
    public void setRefId(Integer pRefId) {
        this.refId = pRefId;
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
        /*PROTECTED REGION ID(toString_M5E2QHIPEeeRrZw0c1ut0g) ENABLED START*/
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
        buffer.append("refId=").append(getRefId());
        buffer.append(",");
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

    /*PROTECTED REGION ID(_M5E2QHIPEeeRrZw0c1ut0g u m) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/

}
