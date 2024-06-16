package com.airfrance.repind.dto.reference;

/*PROTECTED REGION ID(_q0ISUI-SEeamIv5tbprkCg i) ENABLED START*/

// add not generated imports here
import java.io.Serializable;


/*PROTECTED REGION END*/

/**
 * <p>Title : RefTypExtIDDTO</p>
 * 
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
	public  class RefTypExtIDDTO implements Serializable {


        
    /**
     * extID
     */
    private String extID;
        
        
    /**
     * libelle
     */
    private String libelle;
        
        
    /**
     * option
     */
    private String option;
        

    /*PROTECTED REGION ID(_q0ISUI-SEeamIv5tbprkCg u var) ENABLED START*/
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
	    public RefTypExtIDDTO() {
	    //empty constructor
	    }
	    
    /**
     *
     * @return extID
     */
    public String getExtID() {
        return this.extID;
    }

    /**
     *
     * @param pExtID extID value
     */
    public void setExtID(String pExtID) {
        this.extID = pExtID;
    }

    /**
     *
     * @return libelle
     */
    public String getLibelle() {
        return this.libelle;
    }

    /**
     *
     * @param pLibelle libelle value
     */
    public void setLibelle(String pLibelle) {
        this.libelle = pLibelle;
    }

    /**
     *
     * @return option
     */
    public String getOption() {
        return this.option;
    }

    /**
     *
     * @param pOption option value
     */
    public void setOption(String pOption) {
        this.option = pOption;
    }

    /**
     *
     * @return object as string
     */
    public String toString() {
        String result = null;
        /*PROTECTED REGION ID(toString_q0ISUI-SEeamIv5tbprkCg) ENABLED START*/
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
        buffer.append("extID=").append(getExtID());
        buffer.append(",");
        buffer.append("libelle=").append(getLibelle());
        buffer.append(",");
        buffer.append("option=").append(getOption());
        buffer.append("]");
        return buffer.toString();
    }

    /*PROTECTED REGION ID(_q0ISUI-SEeamIv5tbprkCg u m) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/

}
