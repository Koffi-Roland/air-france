package com.airfrance.repind.dto.reference;

/*PROTECTED REGION ID(_x3sOcE4jEeS-eLH--0fARw i) ENABLED START*/

// add not generated imports here
import java.io.Serializable;


/*PROTECTED REGION END*/

/**
 * <p>Title : RefLanguageDTO</p>
 * 
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
public class RefLanguageDTO  implements Serializable {
        
    /**
	 * 
	 */
	private static final long serialVersionUID = 4549174260837869012L;


	/**
     * languageCode
     */
    private String languageCode;
        
        
    /**
     * labelFR
     */
    private String labelFR;
        
        
    /**
     * labelEN
     */
    private String labelEN;
        
        
    /**
     * iataCode
     */
    private String iataCode;
        

    /*PROTECTED REGION ID(_x3sOcE4jEeS-eLH--0fARw u var) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/

    
    
    /** 
     * default constructor 
     */
    public RefLanguageDTO() {
    
    }
    
    
    /** 
     * full constructor
     * @param pLanguageCode languageCode
     * @param pLabelFR labelFR
     * @param pLabelEN labelEN
     * @param pIataCode iataCode
     */
    public RefLanguageDTO(String pLanguageCode, String pLabelFR, String pLabelEN, String pIataCode) {
        this.languageCode = pLanguageCode;
        this.labelFR = pLabelFR;
        this.labelEN = pLabelEN;
        this.iataCode = pIataCode;
    }

    /**
     *
     * @return iataCode
     */
    public String getIataCode() {
        return this.iataCode;
    }

    /**
     *
     * @param pIataCode iataCode value
     */
    public void setIataCode(String pIataCode) {
        this.iataCode = pIataCode;
    }

    /**
     *
     * @return labelEN
     */
    public String getLabelEN() {
        return this.labelEN;
    }

    /**
     *
     * @param pLabelEN labelEN value
     */
    public void setLabelEN(String pLabelEN) {
        this.labelEN = pLabelEN;
    }

    /**
     *
     * @return labelFR
     */
    public String getLabelFR() {
        return this.labelFR;
    }

    /**
     *
     * @param pLabelFR labelFR value
     */
    public void setLabelFR(String pLabelFR) {
        this.labelFR = pLabelFR;
    }

    /**
     *
     * @return languageCode
     */
    public String getLanguageCode() {
        return this.languageCode;
    }

    /**
     *
     * @param pLanguageCode languageCode value
     */
    public void setLanguageCode(String pLanguageCode) {
        this.languageCode = pLanguageCode;
    }

    /**
     *
     * @return object as string
     */
    public String toString() {
        String result = null;
        /*PROTECTED REGION ID(toString_x3sOcE4jEeS-eLH--0fARw) ENABLED START*/
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
        buffer.append("languageCode=").append(getLanguageCode());
        buffer.append(",");
        buffer.append("labelFR=").append(getLabelFR());
        buffer.append(",");
        buffer.append("labelEN=").append(getLabelEN());
        buffer.append(",");
        buffer.append("iataCode=").append(getIataCode());
        buffer.append("]");
        return buffer.toString();
    }

    /*PROTECTED REGION ID(_x3sOcE4jEeS-eLH--0fARw u m) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/

}
