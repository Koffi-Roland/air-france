package com.airfrance.repind.dto.reference;

/*PROTECTED REGION ID(_XUgy4BeIEeKJFbgRY_ODIg i) ENABLED START*/

// add not generated imports here
import org.apache.commons.lang.builder.ToStringBuilder;


/*PROTECTED REGION END*/

/**
 * <p>Title : ErrorDTO</p>
 * 
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
public class ErrorDTO  {
        
    /**
     * errorCode
     */
    private String errorCode;
        
        
    /**
     * labelFR
     */
    private String labelFR;
        
        
    /**
     * labelUK
     */
    private String labelUK;
        

    /*PROTECTED REGION ID(_XUgy4BeIEeKJFbgRY_ODIg u var) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/

    
    
    /** 
     * default constructor 
     */
    public ErrorDTO() {
    
    }
    
    
    /** 
     * full constructor
     * @param pErrorCode errorCode
     * @param pLabelFR labelFR
     * @param pLabelUK labelUK
     */
    public ErrorDTO(String pErrorCode, String pLabelFR, String pLabelUK) {
        this.errorCode = pErrorCode;
        this.labelFR = pLabelFR;
        this.labelUK = pLabelUK;
    }

    /**
     *
     * @return errorCode
     */
    public String getErrorCode() {
        return this.errorCode;
    }

    /**
     *
     * @param pErrorCode errorCode value
     */
    public void setErrorCode(String pErrorCode) {
        this.errorCode = pErrorCode;
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
     * @return labelUK
     */
    public String getLabelUK() {
        return this.labelUK;
    }

    /**
     *
     * @param pLabelUK labelUK value
     */
    public void setLabelUK(String pLabelUK) {
        this.labelUK = pLabelUK;
    }

    /**
     *
     * @return object as string
     */
    public String toString() {
        String result = null;
        /*PROTECTED REGION ID(toString_XUgy4BeIEeKJFbgRY_ODIg) ENABLED START*/
        result = toStringImpl();
        /*PROTECTED REGION END*/
        return result;
    }

    /**
     *
     * @return object as string
     */
    public String toStringImpl() {
        return new ToStringBuilder(this)
            .append("errorCode", getErrorCode())
            .append("labelFR", getLabelFR())
            .append("labelUK", getLabelUK())
            .toString();
    }

    /*PROTECTED REGION ID(_XUgy4BeIEeKJFbgRY_ODIg u m) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/

}
