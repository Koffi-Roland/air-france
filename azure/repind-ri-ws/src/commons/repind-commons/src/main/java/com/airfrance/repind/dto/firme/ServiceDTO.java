package com.airfrance.repind.dto.firme;

/*PROTECTED REGION ID(_tHH6kC02EeSfSooroMx0yQ i) ENABLED START*/

// add not generated imports here
import java.io.Serializable;


/*PROTECTED REGION END*/

/**
 * <p>Title : ServiceDTO</p>
 * 
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
public class ServiceDTO extends PersonneMoraleDTO  implements Serializable {

    /*PROTECTED REGION ID(_tHH6kC02EeSfSooroMx0yQ u var) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/

    
    
    
    /**
	 * 
	 */
	private static final long serialVersionUID = -5962187000866934931L;

	/** 
     * full constructor
     */
    public ServiceDTO() {
    }

    /**
     *
     * @return object as string
     */
    public String toString() {
        String result = null;
        /*PROTECTED REGION ID(toString_tHH6kC02EeSfSooroMx0yQ) ENABLED START*/
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
        buffer.append("]");
        return buffer.toString();
    }

    /*PROTECTED REGION ID(_tHH6kC02EeSfSooroMx0yQ u m) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/

}
