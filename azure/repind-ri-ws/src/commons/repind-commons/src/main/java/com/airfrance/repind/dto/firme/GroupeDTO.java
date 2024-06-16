package com.airfrance.repind.dto.firme;

/*PROTECTED REGION ID(_Z8W3wLdcEeCrCZp8iGNNVw i) ENABLED START*/

// add not generated imports here
import java.io.Serializable;

/*PROTECTED REGION END*/

/**
 * <p>Title : GroupeDTO</p>
 * 
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
public class GroupeDTO extends PersonneMoraleDTO  implements Serializable {
        
    /**
	 * 
	 */
	private static final long serialVersionUID = -3346355415483740744L;
	/**
     * code
     */
    private String code;
        

    /*PROTECTED REGION ID(_Z8W3wLdcEeCrCZp8iGNNVw u var) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/

    
    
    /** 
     * default constructor 
     */
    public GroupeDTO() {
    
    }
    
    
    /** 
     * full constructor
     * @param pCode code
     */
    public GroupeDTO(String pCode) {
        this.code = pCode;
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
     * @return object as string
     */
    public String toString() {
        String result = null;
        /*PROTECTED REGION ID(toString_Z8W3wLdcEeCrCZp8iGNNVw) ENABLED START*/
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
        buffer.append("]");
        return buffer.toString();
    }

    /*PROTECTED REGION ID(_Z8W3wLdcEeCrCZp8iGNNVw u m) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/

}
