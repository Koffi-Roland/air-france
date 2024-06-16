package com.airfrance.repind.dto.environnement;

/*PROTECTED REGION ID(_dtMF_jVcEeGby45oHEwUrg i) ENABLED START*/

// add not generated imports here
import org.apache.commons.lang.builder.ToStringBuilder;


/*PROTECTED REGION END*/

/**
 * <p>Title : VariablesDTO</p>
 * 
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
public class VariablesDTO  {
        
    /**
     * envKey
     */
    private String envKey;
        
        
    /**
     * envValue
     */
    private String envValue;
        

    /*PROTECTED REGION ID(_dtMF_jVcEeGby45oHEwUrg u var) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/

    
    
    /** 
     * default constructor 
     */
    public VariablesDTO() {
    
    }
    
    
    /** 
     * full constructor
     * @param pEnvKey envKey
     * @param pEnvValue envValue
     */
    public VariablesDTO(String pEnvKey, String pEnvValue) {
        this.envKey = pEnvKey;
        this.envValue = pEnvValue;
    }

    /**
     *
     * @return envKey
     */
    public String getEnvKey() {
        return this.envKey;
    }

    /**
     *
     * @param pEnvKey envKey value
     */
    public void setEnvKey(String pEnvKey) {
        this.envKey = pEnvKey;
    }

    /**
     *
     * @return envValue
     */
    public String getEnvValue() {
        return this.envValue;
    }

    /**
     *
     * @param pEnvValue envValue value
     */
    public void setEnvValue(String pEnvValue) {
        this.envValue = pEnvValue;
    }

    /**
     *
     * @return object as string
     */
    public String toString() {
        String result = null;
        /*PROTECTED REGION ID(toString_dtMF_jVcEeGby45oHEwUrg) ENABLED START*/
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
            .append("envKey", getEnvKey())
            .append("envValue", getEnvValue())
            .toString();
    }

    /*PROTECTED REGION ID(_dtMF_jVcEeGby45oHEwUrg u m) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/

}
