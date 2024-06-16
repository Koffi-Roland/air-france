package com.airfrance.repind.dto.ws;

/*PROTECTED REGION ID(_d8dPcNW_Eeef5oRB6XPNlw i) ENABLED START*/

// add not generated imports here
import java.io.Serializable;


/*PROTECTED REGION END*/

/**
 * <p>Title : CommunicationPreferencesRequestDTO</p>
 * 
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
	public  class CommunicationPreferencesRequestDTO implements Serializable {


        
    /**
     * communicationPreferencesDTO
     */
    private CommunicationPreferencesDTO communicationPreferencesDTO;
        

    /*PROTECTED REGION ID(_d8dPcNW_Eeef5oRB6XPNlw u var) ENABLED START*/
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
     *
     * @return communicationPreferencesDTO
     */
    public CommunicationPreferencesDTO getCommunicationPreferencesDTO() {
        return this.communicationPreferencesDTO;
    }

    /**
     *
     * @param pCommunicationPreferencesDTO communicationPreferencesDTO value
     */
    public void setCommunicationPreferencesDTO(CommunicationPreferencesDTO pCommunicationPreferencesDTO) {
        this.communicationPreferencesDTO = pCommunicationPreferencesDTO;
    }

    /**
     *
     * @return object as string
     */
    public String toString() {
        String result = null;
        /*PROTECTED REGION ID(toString_d8dPcNW_Eeef5oRB6XPNlw) ENABLED START*/
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

    /*PROTECTED REGION ID(_d8dPcNW_Eeef5oRB6XPNlw u m) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/

}
