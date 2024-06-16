package com.airfrance.repind.dto.individu.provideginforuserid;

/*PROTECTED REGION ID(_mfDL0DXBEeCEFJUFQSNX7g i) ENABLED START*/

// add not generated imports here
import org.apache.commons.lang.builder.ToStringBuilder;


/*PROTECTED REGION END*/

/**
 * <p>Title : ProvideGinForUserIdResponseDTO</p>
 * 
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
public class ProvideGinForUserIdResponseDTO  {
        
    /**
     * gin
     */
    private String gin;
        
        
    /**
     * returnCode
     */
    private String returnCode;
        
        
    /**
     * foundIdentifier
     */
    private String foundIdentifier;
        

    /*PROTECTED REGION ID(_mfDL0DXBEeCEFJUFQSNX7g u var) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/

    
    
    /** 
     * default constructor 
     */
    public ProvideGinForUserIdResponseDTO() {
    
    }
    
    
    /** 
     * full constructor
     * @param pGin gin
     * @param pReturnCode returnCode
     * @param pFoundIdentifier foundIdentifier
     */
    public ProvideGinForUserIdResponseDTO(String pGin, String pReturnCode, String pFoundIdentifier) {
        this.gin = pGin;
        this.returnCode = pReturnCode;
        this.foundIdentifier = pFoundIdentifier;
    }

    /**
     *
     * @return foundIdentifier
     */
    public String getFoundIdentifier() {
        return this.foundIdentifier;
    }

    /**
     *
     * @param pFoundIdentifier foundIdentifier value
     */
    public void setFoundIdentifier(String pFoundIdentifier) {
        this.foundIdentifier = pFoundIdentifier;
    }

    /**
     *
     * @return gin
     */
    public String getGin() {
        return this.gin;
    }

    /**
     *
     * @param pGin gin value
     */
    public void setGin(String pGin) {
        this.gin = pGin;
    }

    /**
     *
     * @return returnCode
     */
    public String getReturnCode() {
        return this.returnCode;
    }

    /**
     *
     * @param pReturnCode returnCode value
     */
    public void setReturnCode(String pReturnCode) {
        this.returnCode = pReturnCode;
    }

    /**
     *
     * @return object as string
     */
    public String toString() {
        String result = null;
        /*PROTECTED REGION ID(toString_mfDL0DXBEeCEFJUFQSNX7g) ENABLED START*/
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
            .append("gin", getGin())
            .append("returnCode", getReturnCode())
            .append("foundIdentifier", getFoundIdentifier())
            .toString();
    }

    /*PROTECTED REGION ID(_mfDL0DXBEeCEFJUFQSNX7g u m) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/

}
