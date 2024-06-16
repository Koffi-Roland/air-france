package com.afklm.rigui.dto.individu;

/*PROTECTED REGION ID(_sjMsoFiyEea7Yu0D-4113Q i) ENABLED START*/

// add not generated imports here
import java.io.Serializable;


/*PROTECTED REGION END*/

/**
 * <p>Title : AlertDataDTO</p>
 * 
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
	public  class AlertDataDTO implements Serializable {


        
    /**
     * AlertDataId
     */
    private Integer AlertDataId;
        
        
    /**
     * key
     */
    private String key;
        
        
    /**
     * value
     */
    private String value;
        

    /*PROTECTED REGION ID(_sjMsoFiyEea7Yu0D-4113Q u var) ENABLED START*/
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
    public AlertDataDTO() {
	    //empty constructor
    }
    /*PROTECTED REGION ID(toString_sjMsoFiyEea7Yu0D-4113Q) ENABLED START*/
    public AlertDataDTO(String key, String value) {
        /*PROTECTED REGION ID(AlertDataDTO_sjMsoFiyEea74FJu0D-4113Q) ENABLED START*/
    	this.key = key;
    	this.value = value;
        /*PROTECTED REGION END*/
    }    
    /*PROTECTED REGION END*/
 
    /**
     *
     * @return AlertDataId
     */
    public Integer getAlertDataId() {
        return this.AlertDataId;
    }

    /**
     *
     * @param pAlertDataId AlertDataId value
     */
    public void setAlertDataId(Integer pAlertDataId) {
        this.AlertDataId = pAlertDataId;
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
        /*PROTECTED REGION ID(toString_sjMsoFiyEea7Yu0D-4113Q) ENABLED START*/
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
        buffer.append("AlertDataId=").append(getAlertDataId());
        buffer.append(",");
        buffer.append("key=").append(getKey());
        buffer.append(",");
        buffer.append("value=").append(getValue());
        buffer.append("]");
        return buffer.toString();
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((key == null) ? 0 : key.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AlertDataDTO other = (AlertDataDTO) obj;
		if (key == null) {
			if (other.key != null)
				return false;
		} else if (!key.equals(other.key))
			return false;
		return true;
	}

    /*PROTECTED REGION ID(_sjMsoFiyEea7Yu0D-4113Q u m) ENABLED START*/
    // add your custom methods here if necessaryturn false;
    /*PROTECTED REGION END*/
    



}
