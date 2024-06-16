package com.airfrance.repind.dto.firme;

/*PROTECTED REGION ID(_XN3RMLdcEeCrCZp8iGNNVw i) ENABLED START*/

// add not generated imports here
import java.io.Serializable;

/*PROTECTED REGION END*/

/**
 * <p>Title : EntrepriseDTO</p>
 * 
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
public class EntrepriseDTO extends PersonneMoraleDTO  implements Serializable {
        
    /**
	 * 
	 */
	private static final long serialVersionUID = -7463104786868916371L;
	/**
     * siren
     */
    private String siren;
        

    /*PROTECTED REGION ID(_XN3RMLdcEeCrCZp8iGNNVw u var) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/

    
    
    /** 
     * default constructor 
     */
    public EntrepriseDTO() {
    
    }
    
    
    /** 
     * full constructor
     * @param pSiren siren
     */
    public EntrepriseDTO(String pSiren) {
        this.siren = pSiren;
    }

    /**
     *
     * @return siren
     */
    public String getSiren() {
        return this.siren;
    }

    /**
     *
     * @param pSiren siren value
     */
    public void setSiren(String pSiren) {
        this.siren = pSiren;
    }

    /**
     *
     * @return object as string
     */
    public String toString() {
        String result = null;
        /*PROTECTED REGION ID(toString_XN3RMLdcEeCrCZp8iGNNVw) ENABLED START*/
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
        buffer.append("siren=").append(getSiren());
        buffer.append("]");
        return buffer.toString();
    }

    /*PROTECTED REGION ID(_XN3RMLdcEeCrCZp8iGNNVw u m) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/

}
