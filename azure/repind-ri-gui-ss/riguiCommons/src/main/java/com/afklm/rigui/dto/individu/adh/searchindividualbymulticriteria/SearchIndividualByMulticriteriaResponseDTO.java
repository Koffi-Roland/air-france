package com.afklm.rigui.dto.individu.adh.searchindividualbymulticriteria;

/*PROTECTED REGION ID(_zn-g8AWbEeegsNhfbw3UgQ i) ENABLED START*/

// add not generated imports here

import java.io.Serializable;
import java.util.Set;

/*PROTECTED REGION END*/

/**
 * <p>Title : SearchIndividualByMulticriteriaResponseDTO</p>
 * 
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
	public  class SearchIndividualByMulticriteriaResponseDTO implements Serializable {


        
    /**
     * visaKey
     */
    private String visaKey;
        
        
    /**
     * individuals
     */
    private Set<IndividualMulticriteriaDTO> individuals;
        

    /*PROTECTED REGION ID(_zn-g8AWbEeegsNhfbw3UgQ u var) ENABLED START*/
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
	    public SearchIndividualByMulticriteriaResponseDTO() {
	    //empty constructor
	    }
	    
    /**
     *
     * @return individuals
     */
    public Set<IndividualMulticriteriaDTO> getIndividuals() {
        return this.individuals;
    }

    /**
     *
     * @param pIndividuals individuals value
     */
    public void setIndividuals(Set<IndividualMulticriteriaDTO> pIndividuals) {
        this.individuals = pIndividuals;
    }

    /**
     *
     * @return visaKey
     */
    public String getVisaKey() {
        return this.visaKey;
    }

    /**
     *
     * @param pVisaKey visaKey value
     */
    public void setVisaKey(String pVisaKey) {
        this.visaKey = pVisaKey;
    }

    /**
     *
     * @return object as string
     */
    public String toString() {
        String result = null;
        /*PROTECTED REGION ID(toString_zn-g8AWbEeegsNhfbw3UgQ) ENABLED START*/
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
        buffer.append("visaKey=").append(getVisaKey());
        buffer.append("]");
        return buffer.toString();
    }

    /*PROTECTED REGION ID(_zn-g8AWbEeegsNhfbw3UgQ u m) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/

}
