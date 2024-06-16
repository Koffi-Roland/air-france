package com.airfrance.repind.dto.ws;

/*PROTECTED REGION ID(_oT99MNW8Eeef5oRB6XPNlw i) ENABLED START*/

// add not generated imports here
import java.io.Serializable;
import java.util.List;


/*PROTECTED REGION END*/

/**
 * <p>Title : AccountDelegationDataDTO</p>
 * 
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
	public  class AccountDelegationDataDTO implements Serializable {
        
        
    /**
     * delegateDTO
     */
    private List<DelegateDTO> delegateDTO;
        
        
    /**
     * delegatorDTO
     */
    private List<DelegatorDTO> delegatorDTO;
        

    /*PROTECTED REGION ID(_oT99MNW8Eeef5oRB6XPNlw u var) ENABLED START*/
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
     * @return delegateDTO
     */
    public List<DelegateDTO> getDelegateDTO() {
        return this.delegateDTO;
    }

    /**
     *
     * @param pDelegateDTO delegateDTO value
     */
    public void setDelegateDTO(List<DelegateDTO> pDelegateDTO) {
        this.delegateDTO = pDelegateDTO;
    }

    /**
     *
     * @return delegatorDTO
     */
    public List<DelegatorDTO> getDelegatorDTO() {
        return this.delegatorDTO;
    }

    /**
     *
     * @param pDelegatorDTO delegatorDTO value
     */
    public void setDelegatorDTO(List<DelegatorDTO> pDelegatorDTO) {
        this.delegatorDTO = pDelegatorDTO;
    }

    /**
     *
     * @return object as string
     */
    public String toString() {
        String result = null;
        /*PROTECTED REGION ID(toString_oT99MNW8Eeef5oRB6XPNlw) ENABLED START*/
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

    /*PROTECTED REGION ID(_oT99MNW8Eeef5oRB6XPNlw u m) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/

}
