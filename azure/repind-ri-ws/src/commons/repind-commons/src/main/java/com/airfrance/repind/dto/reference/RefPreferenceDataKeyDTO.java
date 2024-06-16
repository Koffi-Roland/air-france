package com.airfrance.repind.dto.reference;

/*PROTECTED REGION ID(_BdyjYHz_EeeM7eE6bvH4Tw i) ENABLED START*/

// add not generated imports here
import java.io.Serializable;


/*PROTECTED REGION END*/

/**
 * <p>Title : RefPreferenceDataKeyDTO</p>
 * 
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
	public  class RefPreferenceDataKeyDTO implements Serializable {


        
    /**
     * code
     */
    private String code;
        
        
    /**
     * libelleFr
     */
    private String libelleFr;
        
        
    /**
     * libelleEn
     */
    private String libelleEn;
        
        
    /**
     * normalizedKey
     */
    private String normalizedKey;
        

    /*PROTECTED REGION ID(_BdyjYHz_EeeM7eE6bvH4Tw u var) ENABLED START*/
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
	    public RefPreferenceDataKeyDTO() {
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
     * @return libelleEn
     */
    public String getLibelleEn() {
        return this.libelleEn;
    }

    /**
     *
     * @param pLibelleEn libelleEn value
     */
    public void setLibelleEn(String pLibelleEn) {
        this.libelleEn = pLibelleEn;
    }

    /**
     *
     * @return libelleFr
     */
    public String getLibelleFr() {
        return this.libelleFr;
    }

    /**
     *
     * @param pLibelleFr libelleFr value
     */
    public void setLibelleFr(String pLibelleFr) {
        this.libelleFr = pLibelleFr;
    }

    /**
     *
     * @return normalizedKey
     */
    public String getNormalizedKey() {
        return this.normalizedKey;
    }

    /**
     *
     * @param pNormalizedKey normalizedKey value
     */
    public void setNormalizedKey(String pNormalizedKey) {
        this.normalizedKey = pNormalizedKey;
    }

    /**
     *
     * @return object as string
     */
    public String toString() {
        String result = null;
        /*PROTECTED REGION ID(toString_BdyjYHz_EeeM7eE6bvH4Tw) ENABLED START*/
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
        buffer.append("libelleFr=").append(getLibelleFr());
        buffer.append(",");
        buffer.append("libelleEn=").append(getLibelleEn());
        buffer.append(",");
        buffer.append("normalizedKey=").append(getNormalizedKey());
        buffer.append("]");
        return buffer.toString();
    }

    /*PROTECTED REGION ID(_BdyjYHz_EeeM7eE6bvH4Tw u m) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/

}
