package com.airfrance.repind.dto.reference;

/*PROTECTED REGION ID(_X3pCEAThEee_Y7_I5loOBA i) ENABLED START*/

// add not generated imports here
import java.io.Serializable;


/*PROTECTED REGION END*/

/**
 * <p>Title : RefTypeEventDTO</p>
 * 
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
	public  class RefTypeEventDTO implements Serializable {


        
    /**
     * code
     */
    private String code;
        
        
    /**
     * labelFR
     */
    private String labelFR;
        
        
    /**
     * labelEN
     */
    private String labelEN;
        

    /*PROTECTED REGION ID(_X3pCEAThEee_Y7_I5loOBA u var) ENABLED START*/
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
	    public RefTypeEventDTO() {
	    //empty constructor
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
     * @return object as string
     */
    public String toString() {
        String result = null;
        /*PROTECTED REGION ID(toString_X3pCEAThEee_Y7_I5loOBA) ENABLED START*/
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
        buffer.append("code=").append(getCode());
        buffer.append(",");
        buffer.append("labelFR=").append(getLabelFR());
        buffer.append(",");
        buffer.append("labelEN=").append(getLabelEN());
        buffer.append("]");
        return buffer.toString();
    }

    /*PROTECTED REGION ID(_X3pCEAThEee_Y7_I5loOBA u m) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/

}
