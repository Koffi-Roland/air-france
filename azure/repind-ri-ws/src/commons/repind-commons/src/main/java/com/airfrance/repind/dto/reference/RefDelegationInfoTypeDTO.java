package com.airfrance.repind.dto.reference;

/*PROTECTED REGION ID(_glmJUHBQEeeA-oB3G9fmBA i) ENABLED START*/

// add not generated imports here
import java.io.Serializable;


/*PROTECTED REGION END*/

/**
 * <p>Title : RefPreferenceTypeDTO</p>
 * 
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
	public  class RefDelegationInfoTypeDTO implements Serializable {


        
    /**
     * code
     */
    private String code;
        
        
    /**
     * libelleFR
     */
    private String libelleFR;
        
        
    /**
     * libelleEN
     */
    private String libelleEN;
        

    /*PROTECTED REGION ID(_glmJUHBQEeeA-oB3G9fmBA u var) ENABLED START*/
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
	    public RefDelegationInfoTypeDTO() {
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
     * @return libelleEN
     */
    public String getLibelleEN() {
        return this.libelleEN;
    }

    /**
     *
     * @param pLibelleEN libelleEN value
     */
    public void setLibelleEN(String pLibelleEN) {
        this.libelleEN = pLibelleEN;
    }

    /**
     *
     * @return libelleFR
     */
    public String getLibelleFR() {
        return this.libelleFR;
    }

    /**
     *
     * @param pLibelleFR libelleFR value
     */
    public void setLibelleFR(String pLibelleFR) {
        this.libelleFR = pLibelleFR;
    }

    /**
     *
     * @return object as string
     */
    public String toString() {
        String result = null;
        /*PROTECTED REGION ID(toString_glmJUHBQEeeA-oB3G9fmBA) ENABLED START*/
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
        buffer.append("libelleFR=").append(getLibelleFR());
        buffer.append(",");
        buffer.append("libelleEN=").append(getLibelleEN());
        buffer.append("]");
        return buffer.toString();
    }

    /*PROTECTED REGION ID(_glmJUHBQEeeA-oB3G9fmBA u m) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/

}
