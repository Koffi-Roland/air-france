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
public class RefHandicapCodeDTO  implements Serializable {
        
    /**
	 * 
	 */
	private static final long serialVersionUID = 4549174260837869012L;


	/**
     * code
     */
    private String code;
        
        
    /**
     * labelFR
     */
    private String labelFR;
        
        
    /**
     * labelEN
     */
    private String labelEN;
               

    /*PROTECTED REGION ID(_x3sOcE4jEeS-eLH--0fARw u var) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/

    
    
    /** 
     * default constructor 
     */
    public RefHandicapCodeDTO() {
    
    }
    
    
    /** 
     * full constructor
     * @param pLanguageCode languageCode
     * @param pLabelFR labelFR
     * @param pLabelEN labelEN
     * @param pIataCode iataCode
     */
    public RefHandicapCodeDTO(String pCode, String pLabelFR, String pLabelEN, String pIataCode) {
        this.code = pCode;
        this.labelFR = pLabelFR;
        this.labelEN = pLabelEN;
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
    public String getCode() {
        return this.code;
    }

    /**
     *
     * @param pLanguageCode languageCode value
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
        buffer.append("code=").append(getCode());
        buffer.append(",");
        buffer.append("labelFR=").append(getLabelFR());
        buffer.append(",");
        buffer.append("labelEN=").append(getLabelEN());
        buffer.append("]");
        return buffer.toString();
    }

    /*PROTECTED REGION ID(_x3sOcE4jEeS-eLH--0fARw u m) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/

}
