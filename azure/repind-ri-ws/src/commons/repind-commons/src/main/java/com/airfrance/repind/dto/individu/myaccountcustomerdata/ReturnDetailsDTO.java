package com.airfrance.repind.dto.individu.myaccountcustomerdata;

/*PROTECTED REGION ID(_THjHQKHBEeKw0ekjUdaFfg i) ENABLED START*/

// add not generated imports here
import org.apache.commons.lang.builder.ToStringBuilder;


/*PROTECTED REGION END*/

/**
 * <p>Title : ReturnDetailsDTO</p>
 * 
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
public class ReturnDetailsDTO  {
        
    /**
     * detailedCode
     */
    private String detailedCode;
        
        
    /**
     * erasePayment
     */
    private boolean erasePayment;
        
        
    /**
     * labelCode
     */
    private String labelCode;
        

    /*PROTECTED REGION ID(_THjHQKHBEeKw0ekjUdaFfg u var) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/

    
    
    /** 
     * default constructor 
     */
    public ReturnDetailsDTO() {
    
    }
    
    
    /** 
     * full constructor
     * @param pDetailedCode detailedCode
     * @param pErasePayment erasePayment
     * @param pLabelCode labelCode
     */
    public ReturnDetailsDTO(String pDetailedCode, boolean pErasePayment, String pLabelCode) {
        this.detailedCode = pDetailedCode;
        this.erasePayment = pErasePayment;
        this.labelCode = pLabelCode;
    }

    /**
     *
     * @return detailedCode
     */
    public String getDetailedCode() {
        return this.detailedCode;
    }

    /**
     *
     * @param pDetailedCode detailedCode value
     */
    public void setDetailedCode(String pDetailedCode) {
        this.detailedCode = pDetailedCode;
    }

    /**
     *
     * @return erasePayment
     */
    public boolean getErasePayment() {
        return this.erasePayment;
    }

    /**
     *
     * @param pErasePayment erasePayment value
     */
    public void setErasePayment(boolean pErasePayment) {
        this.erasePayment = pErasePayment;
    }

    /**
     *
     * @return labelCode
     */
    public String getLabelCode() {
        return this.labelCode;
    }

    /**
     *
     * @param pLabelCode labelCode value
     */
    public void setLabelCode(String pLabelCode) {
        this.labelCode = pLabelCode;
    }

    /**
     *
     * @return object as string
     */
    public String toString() {
        String result = null;
        /*PROTECTED REGION ID(toString_THjHQKHBEeKw0ekjUdaFfg) ENABLED START*/
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
            .append("detailedCode", getDetailedCode())
            .append("erasePayment", getErasePayment())
            .append("labelCode", getLabelCode())
            .toString();
    }

    /*PROTECTED REGION ID(_THjHQKHBEeKw0ekjUdaFfg u m) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/

}
