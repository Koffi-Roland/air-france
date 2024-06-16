package com.afklm.rigui.dto.reference;

/*PROTECTED REGION ID(_dKrScPRNEeaosIe0wwvjyw i) ENABLED START*/

// add not generated imports here
import java.io.Serializable;


/*PROTECTED REGION END*/

/**
 * <p>Title : RefGenericCodeLabelsTypeDTO</p>
 * 
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
	public  class RefGenericCodeLabelsTypeDTO implements Serializable {


        
    /**
     * code
     */
    private String code;
        
        
    /**
     * labelFr
     */
    private String labelFr;
        
        
    /**
     * labelEn
     */
    private String labelEn;
        

    /*PROTECTED REGION ID(_dKrScPRNEeaosIe0wwvjyw u var) ENABLED START*/
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
	    public RefGenericCodeLabelsTypeDTO() {
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
     * @return labelEn
     */
    public String getLabelEn() {
        return this.labelEn;
    }

    /**
     *
     * @param pLabelEn labelEn value
     */
    public void setLabelEn(String pLabelEn) {
        this.labelEn = pLabelEn;
    }

    /**
     *
     * @return labelFr
     */
    public String getLabelFr() {
        return this.labelFr;
    }

    /**
     *
     * @param pLabelFr labelFr value
     */
    public void setLabelFr(String pLabelFr) {
        this.labelFr = pLabelFr;
    }

    /**
     *
     * @return object as string
     */
    public String toString() {
        String result = null;
        /*PROTECTED REGION ID(toString_dKrScPRNEeaosIe0wwvjyw) ENABLED START*/
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
        buffer.append("labelFr=").append(getLabelFr());
        buffer.append(",");
        buffer.append("labelEn=").append(getLabelEn());
        buffer.append("]");
        return buffer.toString();
    }

    /*PROTECTED REGION ID(_dKrScPRNEeaosIe0wwvjyw u m) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/

}
