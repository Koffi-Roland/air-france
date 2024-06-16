package com.airfrance.repind.dto.individu.adh.connexiondata;

/*PROTECTED REGION ID(_4dKtyC3rEeC7hbdMKof7lA i) ENABLED START*/

// add not generated imports here
import org.apache.commons.lang.builder.ToStringBuilder;


/*PROTECTED REGION END*/

/**
 * <p>Title : ConnexionDataResponseDTO</p>
 * 
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
public class ConnexionDataResponseDTO  {
        
    /**
     * gin
     */
    private String gin;
        
        
    /**
     * returnCode
     */
    private String returnCode;
        

    /*PROTECTED REGION ID(_4dKtyC3rEeC7hbdMKof7lA u var) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/

    
    
    /** 
     * default constructor 
     */
    public ConnexionDataResponseDTO() {
    
    }
    
    
    /** 
     * full constructor
     * @param pGin gin
     * @param pReturnCode returnCode
     */
    public ConnexionDataResponseDTO(String pGin, String pReturnCode) {
        this.gin = pGin;
        this.returnCode = pReturnCode;
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
        /*PROTECTED REGION ID(toString_4dKtyC3rEeC7hbdMKof7lA) ENABLED START*/
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
            .toString();
    }

    /*PROTECTED REGION ID(_4dKtyC3rEeC7hbdMKof7lA u m) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/

}
