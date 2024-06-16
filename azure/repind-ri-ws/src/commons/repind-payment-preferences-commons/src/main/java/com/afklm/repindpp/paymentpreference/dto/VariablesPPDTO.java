package com.afklm.repindpp.paymentpreference.dto;

/*PROTECTED REGION ID(_raxtgFkoEeS7ba9Foqodfw i) ENABLED START*/

// add not generated imports here
import java.io.Serializable;


/*PROTECTED REGION END*/

/**
 * <p>Title : VariablesPPDTO</p>
 * 
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
public class VariablesPPDTO  implements Serializable {
        
    /**
	 * 
	 */
	private static final long serialVersionUID = 6443943822820996627L;


	/**
     * envKeyPP
     */
    private String envKeyPP;
        
        
    /**
     * envValuePP
     */
    private String envValuePP;
        

    /*PROTECTED REGION ID(_raxtgFkoEeS7ba9Foqodfw u var) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/

    
    
    /** 
     * default constructor 
     */
    public VariablesPPDTO() {
    
    }
    
    
    /** 
     * full constructor
     * @param pEnvKeyPP envKeyPP
     * @param pEnvValuePP envValuePP
     */
    public VariablesPPDTO(String pEnvKeyPP, String pEnvValuePP) {
        this.envKeyPP = pEnvKeyPP;
        this.envValuePP = pEnvValuePP;
    }

    /**
     *
     * @return envKeyPP
     */
    public String getEnvKeyPP() {
        return this.envKeyPP;
    }

    /**
     *
     * @param pEnvKeyPP envKeyPP value
     */
    public void setEnvKeyPP(String pEnvKeyPP) {
        this.envKeyPP = pEnvKeyPP;
    }

    /**
     *
     * @return envValuePP
     */
    public String getEnvValuePP() {
        return this.envValuePP;
    }

    /**
     *
     * @param pEnvValuePP envValuePP value
     */
    public void setEnvValuePP(String pEnvValuePP) {
        this.envValuePP = pEnvValuePP;
    }

    /**
     *
     * @return object as string
     */
    public String toString() {
        String result = null;
        /*PROTECTED REGION ID(toString_raxtgFkoEeS7ba9Foqodfw) ENABLED START*/
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
        buffer.append("envKeyPP=").append(getEnvKeyPP());
        buffer.append(",");
        buffer.append("envValuePP=").append(getEnvValuePP());
        buffer.append("]");
        return buffer.toString();
    }

    /*PROTECTED REGION ID(_raxtgFkoEeS7ba9Foqodfw u m) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/

}
