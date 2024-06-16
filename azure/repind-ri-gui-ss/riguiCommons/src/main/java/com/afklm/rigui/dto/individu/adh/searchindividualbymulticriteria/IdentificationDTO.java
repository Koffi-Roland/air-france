package com.afklm.rigui.dto.individu.adh.searchindividualbymulticriteria;

/*PROTECTED REGION ID(_nJpVoAfYEeegsNhfbw3UgQ i) ENABLED START*/

// add not generated imports here
import java.io.Serializable;


/*PROTECTED REGION END*/

/**
 * <p>Title : IdentificationDTO</p>
 * 
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
	public  class IdentificationDTO implements Serializable {


        
    /**
     * identificationType
     */
    private String identificationType;
        
        
    /**
     * identificationValue
     */
    private String identificationValue;
        

    /*PROTECTED REGION ID(_nJpVoAfYEeegsNhfbw3UgQ u var) ENABLED START*/
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
	    public IdentificationDTO() {
	    //empty constructor
	    }
	    
    /**
     *
     * @return identificationType
     */
    public String getIdentificationType() {
        return this.identificationType;
    }

    /**
     *
     * @param pIdentificationType identificationType value
     */
    public void setIdentificationType(String pIdentificationType) {
        this.identificationType = pIdentificationType;
    }

    /**
     *
     * @return identificationValue
     */
    public String getIdentificationValue() {
        return this.identificationValue;
    }

    /**
     *
     * @param pIdentificationValue identificationValue value
     */
    public void setIdentificationValue(String pIdentificationValue) {
        this.identificationValue = pIdentificationValue;
    }

    /**
     *
     * @return object as string
     */
    public String toString() {
        String result = null;
        /*PROTECTED REGION ID(toString_nJpVoAfYEeegsNhfbw3UgQ) ENABLED START*/
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
        buffer.append("identificationType=").append(getIdentificationType());
        buffer.append(",");
        buffer.append("identificationValue=").append(getIdentificationValue());
        buffer.append("]");
        return buffer.toString();
    }

    /*PROTECTED REGION ID(_nJpVoAfYEeegsNhfbw3UgQ u m) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/

}
