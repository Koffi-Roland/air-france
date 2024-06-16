package com.airfrance.repind.dto.ws;

/*PROTECTED REGION ID(_vr-FgNW8Eeef5oRB6XPNlw i) ENABLED START*/

// add not generated imports here
import java.io.Serializable;


/*PROTECTED REGION END*/

/**
 * <p>Title : DelegatorDataDTO</p>
 * 
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
	public  class DelegatorDataDTO implements Serializable {


        
    /**
     * gin
     */
    private String gin;
        
        
    /**
     * delegationAction
     */
    private String delegationAction;
        
        
    /**
     * delegationType
     */
    private String delegationType;
        

    /*PROTECTED REGION ID(_vr-FgNW8Eeef5oRB6XPNlw u var) ENABLED START*/
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
	    public DelegatorDataDTO() {
	    //empty constructor
	    }
	    
    /**
     *
     * @return delegationAction
     */
    public String getDelegationAction() {
        return this.delegationAction;
    }

    /**
     *
     * @param pDelegationAction delegationAction value
     */
    public void setDelegationAction(String pDelegationAction) {
        this.delegationAction = pDelegationAction;
    }

    /**
     *
     * @return delegationType
     */
    public String getDelegationType() {
        return this.delegationType;
    }

    /**
     *
     * @param pDelegationType delegationType value
     */
    public void setDelegationType(String pDelegationType) {
        this.delegationType = pDelegationType;
    }

    /**
     *
     * @return gin
     */
    public String getGin() {
        return this.gin;
    }

    /**
     *
     * @param pGin gin value
     */
    public void setGin(String pGin) {
        this.gin = pGin;
    }

    /**
     *
     * @return object as string
     */
    public String toString() {
        String result = null;
        /*PROTECTED REGION ID(toString_vr-FgNW8Eeef5oRB6XPNlw) ENABLED START*/
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
        buffer.append("gin=").append(getGin());
        buffer.append(",");
        buffer.append("delegationAction=").append(getDelegationAction());
        buffer.append(",");
        buffer.append("delegationType=").append(getDelegationType());
        buffer.append("]");
        return buffer.toString();
    }

    /*PROTECTED REGION ID(_vr-FgNW8Eeef5oRB6XPNlw u m) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/

}
