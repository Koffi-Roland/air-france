package com.afklm.rigui.dto.ws;

/*PROTECTED REGION ID(_-JdXINUqEeef5oRB6XPNlw i) ENABLED START*/

// add not generated imports here
import java.io.Serializable;


/*PROTECTED REGION END*/

/**
 * <p>Title : IndividualRequestDTO</p>
 * 
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
	public  class IndividualRequestDTO implements Serializable {


        
    /**
     * titleCode
     */
    private String titleCode;
        
        
    /**
     * individualInformationsDTO
     */
    private IndividualInformationsDTO individualInformationsDTO;
        
        
    /**
     * individualProfilDTO
     */
    private IndividualProfilDTO individualProfilDTO;
        

    /*PROTECTED REGION ID(_-JdXINUqEeef5oRB6XPNlw u var) ENABLED START*/
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
	    public IndividualRequestDTO() {
	    //empty constructor
	    }
	    
    /**
     *
     * @return individualInformationsDTO
     */
    public IndividualInformationsDTO getIndividualInformationsDTO() {
        return this.individualInformationsDTO;
    }

    /**
     *
     * @param pIndividualInformationsDTO individualInformationsDTO value
     */
    public void setIndividualInformationsDTO(IndividualInformationsDTO pIndividualInformationsDTO) {
        this.individualInformationsDTO = pIndividualInformationsDTO;
    }

    /**
     *
     * @return individualProfilDTO
     */
    public IndividualProfilDTO getIndividualProfilDTO() {
        return this.individualProfilDTO;
    }

    /**
     *
     * @param pIndividualProfilDTO individualProfilDTO value
     */
    public void setIndividualProfilDTO(IndividualProfilDTO pIndividualProfilDTO) {
        this.individualProfilDTO = pIndividualProfilDTO;
    }

    /**
     *
     * @return titleCode
     */
    public String getTitleCode() {
        return this.titleCode;
    }

    /**
     *
     * @param pTitleCode titleCode value
     */
    public void setTitleCode(String pTitleCode) {
        this.titleCode = pTitleCode;
    }

    /**
     *
     * @return object as string
     */
    public String toString() {
        String result = null;
        /*PROTECTED REGION ID(toString_-JdXINUqEeef5oRB6XPNlw) ENABLED START*/
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
        buffer.append("titleCode=").append(getTitleCode());
        buffer.append("]");
        return buffer.toString();
    }

    /*PROTECTED REGION ID(_-JdXINUqEeef5oRB6XPNlw u m) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/

}
