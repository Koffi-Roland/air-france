package com.afklm.rigui.dto.individu.adh.individualinformation;

/*PROTECTED REGION ID(_b1hJ0CMUEeCWJOBY8f-ONQ i) ENABLED START*/

// add not generated imports here
import org.apache.commons.lang.builder.ToStringBuilder;


/*PROTECTED REGION END*/

/**
 * <p>Title : ExceptionDTO</p>
 * 
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
public class ExceptionDTO  {
        
    /**
     * NoErr
     */
    private String NoErr;
        
        
    /**
     * LibErr
     */
    private String LibErr;
        

    /*PROTECTED REGION ID(_b1hJ0CMUEeCWJOBY8f-ONQ u var) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/

    
    
    /** 
     * default constructor 
     */
    public ExceptionDTO() {
    
    }
    
    
    /** 
     * full constructor
     * @param pNoErr NoErr
     * @param pLibErr LibErr
     */
    public ExceptionDTO(String pNoErr, String pLibErr) {
        this.NoErr = pNoErr;
        this.LibErr = pLibErr;
    }

    /**
     *
     * @return LibErr
     */
    public String getLibErr() {
        return this.LibErr;
    }

    /**
     *
     * @param pLibErr LibErr value
     */
    public void setLibErr(String pLibErr) {
        this.LibErr = pLibErr;
    }

    /**
     *
     * @return NoErr
     */
    public String getNoErr() {
        return this.NoErr;
    }

    /**
     *
     * @param pNoErr NoErr value
     */
    public void setNoErr(String pNoErr) {
        this.NoErr = pNoErr;
    }

    /**
     *
     * @return object as string
     */
    public String toString() {
        String result = null;
        /*PROTECTED REGION ID(toString_b1hJ0CMUEeCWJOBY8f-ONQ) ENABLED START*/
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
            .append("NoErr", getNoErr())
            .append("LibErr", getLibErr())
            .toString();
    }

    /*PROTECTED REGION ID(_b1hJ0CMUEeCWJOBY8f-ONQ u m) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/

}
