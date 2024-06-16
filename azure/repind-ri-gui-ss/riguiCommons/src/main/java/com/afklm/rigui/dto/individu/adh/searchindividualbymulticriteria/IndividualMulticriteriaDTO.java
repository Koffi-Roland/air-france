package com.afklm.rigui.dto.individu.adh.searchindividualbymulticriteria;

/*PROTECTED REGION ID(_vlMA8AWbEeegsNhfbw3UgQ i) ENABLED START*/

// add not generated imports here

import com.afklm.rigui.dto.individu.IndividuDTO;

import java.io.Serializable;

/*PROTECTED REGION END*/

/**
 * <p>Title : IndividualMulticriteriaDTO</p>
 * 
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
	public  class IndividualMulticriteriaDTO implements Serializable {


        
    /**
     * relevance
     */
    private String relevance;
        
        
    /**
     * individu
     */
    private IndividuDTO individu;
        

    /*PROTECTED REGION ID(_vlMA8AWbEeegsNhfbw3UgQ u var) ENABLED START*/
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
	    public IndividualMulticriteriaDTO() {
	    //empty constructor
	    }
	    
    /**
     *
     * @return individu
     */
    public IndividuDTO getIndividu() {
        return this.individu;
    }

    /**
     *
     * @param pIndividu individu value
     */
    public void setIndividu(IndividuDTO pIndividu) {
        this.individu = pIndividu;
    }

    /**
     *
     * @return relevance
     */
    public String getRelevance() {
        return this.relevance;
    }

    /**
     *
     * @param pRelevance relevance value
     */
    public void setRelevance(String pRelevance) {
        this.relevance = pRelevance;
    }

    /**
     *
     * @return object as string
     */
    public String toString() {
        String result = null;
        /*PROTECTED REGION ID(toString_vlMA8AWbEeegsNhfbw3UgQ) ENABLED START*/
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
        buffer.append("relevance=").append(getRelevance());
        buffer.append("]");
        return buffer.toString();
    }

    /*PROTECTED REGION ID(_vlMA8AWbEeegsNhfbw3UgQ u m) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/

}
