package com.airfrance.repind.dto.ws;

/*PROTECTED REGION ID(_s67G8NXMEeef5oRB6XPNlw i) ENABLED START*/

// add not generated imports here
import java.io.Serializable;
import java.util.List;


/*PROTECTED REGION END*/

/**
 * <p>Title : AlertDTO</p>
 * 
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
	public  class AlertDTO implements Serializable {


        
    /**
     * alertId
     */
    private int alertId;
        
        
    /**
     * type
     */
    private String type;
        
        
    /**
     * optin
     */
    private String optin;
        
        
    /**
     * alertDataDTO
     */
    private List<AlertDataDTO> alertDataDTO;
        

    /*PROTECTED REGION ID(_s67G8NXMEeef5oRB6XPNlw u var) ENABLED START*/
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
	    public AlertDTO() {
	    //empty constructor
	    }
	    
    /**
     *
     * @return alertDataDTO
     */
    public List<AlertDataDTO> getAlertDataDTO() {
        return this.alertDataDTO;
    }

    /**
     *
     * @param pAlertDataDTO alertDataDTO value
     */
    public void setAlertDataDTO(List<AlertDataDTO> pAlertDataDTO) {
        this.alertDataDTO = pAlertDataDTO;
    }

    /**
     *
     * @return alertId
     */
    public int getAlertId() {
        return this.alertId;
    }

    /**
     *
     * @param pAlertId alertId value
     */
    public void setAlertId(int pAlertId) {
        this.alertId = pAlertId;
    }

    /**
     *
     * @return optin
     */
    public String getOptin() {
        return this.optin;
    }

    /**
     *
     * @param pOptin optin value
     */
    public void setOptin(String pOptin) {
        this.optin = pOptin;
    }

    /**
     *
     * @return type
     */
    public String getType() {
        return this.type;
    }

    /**
     *
     * @param pType type value
     */
    public void setType(String pType) {
        this.type = pType;
    }

    /**
     *
     * @return object as string
     */
    public String toString() {
        String result = null;
        /*PROTECTED REGION ID(toString_s67G8NXMEeef5oRB6XPNlw) ENABLED START*/
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
        buffer.append("alertId=").append(getAlertId());
        buffer.append(",");
        buffer.append("type=").append(getType());
        buffer.append(",");
        buffer.append("optin=").append(getOptin());
        buffer.append("]");
        return buffer.toString();
    }

    /*PROTECTED REGION ID(_s67G8NXMEeef5oRB6XPNlw u m) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/

}
